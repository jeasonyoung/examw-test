package com.examw.test.service.syllabus.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.syllabus.IBookChapterDao;
import com.examw.test.dao.syllabus.IBookDao;
import com.examw.test.domain.syllabus.Book;
import com.examw.test.domain.syllabus.BookChapter;
import com.examw.test.model.syllabus.BookChapterInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.IBookChapterService;

/**
 * 教材章节服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月13日
 */
public class BookChapterServiceImpl extends BaseDataServiceImpl<BookChapter, BookChapterInfo> implements IBookChapterService {
	private static final Logger logger = Logger.getLogger(BookChapterServiceImpl.class);
	private IBookChapterDao bookChapterDao;
	private IBookDao bookDao;
	/**
	 * 设置教材章节数据接口。
	 * @param bookChapterDao 
	 *	  bookChapterDao
	 */
	public void setBookChapterDao(IBookChapterDao bookChapterDao) {
		if(logger.isDebugEnabled()) logger.debug("设置教材章节数据接口...");
		this.bookChapterDao = bookChapterDao;
	}
	/**
	 * 设置教材数据接口。
	 * @param bookDao 
	 *	  教材数据接口。
	 */
	public void setBookDao(IBookDao bookDao) {
		if(logger.isDebugEnabled()) logger.debug("教材数据接口...");
		this.bookDao = bookDao;
	}
	/*
	 * 加载教材下的章节集合。
	 * @see com.examw.test.service.syllabus.IBookChapterService#loadChapters(java.lang.String)
	 */
	@Override
	public List<BookChapterInfo> loadChapters(String bookId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教材［bookId = %s］下的章节集合...", bookId));
		return this.changeModel(this.bookChapterDao.findFirstChapters(bookId));
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.test.service.syllabus.IBookChapterService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String parentChapterId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载最大排序号［parentChapterId = %s］...", parentChapterId));
		return this.bookChapterDao.loadMaxOrder(parentChapterId);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected BookChapterInfo changeModel(BookChapter data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 BookChapter => BookChapterInfo ...");
		return this.changeModel(data, true);
	}
	//数据模型转换。
	private BookChapterInfo changeModel(BookChapter data,boolean hasChild){
		if(data == null) return null;
		BookChapterInfo info = new BookChapterInfo();
		BeanUtils.copyProperties(data, info, new String[]{"children"});
		if(data.getBook() != null){
			info.setBookId(data.getBook().getId());
			info.setBookName(data.getBook().getName());
		}
		if(hasChild && (data.getChildren() != null && data.getChildren().size() > 0)){
			Set<BookChapterInfo> children = new TreeSet<>();
			for(BookChapter chapter : data.getChildren()){
				if(chapter == null) continue;
				BookChapterInfo e = this.changeModel(chapter, hasChild);
				if(e != null){
					e.setPid(info.getId());
					children.add(e);
				}
			}
			if(children.size() > 0){
				info.setChildren(children);
			}
		}
		return info;
	}
	/*
	 *  数据模型转换。
	 * @see com.examw.test.service.syllabus.IBookChapterService#conversion(com.examw.test.domain.syllabus.BookChapter)
	 */
	@Override
	public BookChapterInfo conversion(BookChapter chapter) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 BookChapter => BookChapterInfo...");
		return this.changeModel(chapter, true);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<BookChapter> find(BookChapterInfo info) {
		throw new RuntimeException("未实现！");
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(BookChapterInfo info) {
		throw new RuntimeException("未实现！");
	}
	/*
	 * 数据更新。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public BookChapterInfo update(BookChapterInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据更新...");
		boolean isAdded = false;
		BookChapter data = StringUtils.isEmpty(info.getId()) ? null : this.bookChapterDao.load(BookChapter.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new BookChapter();
		}
		BeanUtils.copyProperties(info, data, new String[]{"children"});
		if(StringUtils.isEmpty(info.getPid())){
			data.setParent(null);
		}else if (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid())) {
			BookChapter parent = this.bookChapterDao.load(BookChapter.class, info.getPid());
			if(parent != null && !parent.getId().equalsIgnoreCase(data.getId())) {
				data.setParent(parent);
			}
		}
		data.setBook(StringUtils.isEmpty(info.getBookId()) ? null : this.bookDao.load(Book.class, info.getBookId()));
		if(isAdded) this.bookChapterDao.save(data);
		return this.changeModel(data,false);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(String id : ids){
			if(StringUtils.isEmpty(id)) continue;
			BookChapter data = this.bookChapterDao.load(BookChapter.class, id);
			if(data != null) this.bookChapterDao.delete(data);
		}
	}

}