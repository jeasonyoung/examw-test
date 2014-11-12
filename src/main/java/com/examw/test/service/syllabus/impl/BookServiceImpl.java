package com.examw.test.service.syllabus.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.settings.IAreaDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.dao.syllabus.IPressDao;
import com.examw.test.dao.syllabus.IBookDao;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.domain.syllabus.Press;
import com.examw.test.domain.syllabus.Book;
import com.examw.test.model.syllabus.BookInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.IBookService;
/**
 * 教材服务接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class BookServiceImpl extends BaseDataServiceImpl<Book, BookInfo> implements IBookService {
	private static final Logger logger = Logger.getLogger(BookServiceImpl.class);
	private IBookDao bookDao;
	private IPressDao pressDao;
	private ISubjectDao subjectDao;
	private IAreaDao areaDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置教材数据接口。
	 * @param bookDao
	 * 教材数据接口。
	 */
	public void setBookDao(IBookDao bookDao) {
		if(logger.isDebugEnabled())logger.debug("注入教材数据接口...");
		this.bookDao = bookDao;
	}
	/**
	 * 设置出版社数据接口。
	 * @param pressDao
	 * 出版社数据接口。
	 */
	public void setPressDao(IPressDao pressDao) {
		if(logger.isDebugEnabled())logger.debug("注入出版社数据接口...");
		this.pressDao = pressDao;
	}
	/**
	 * 设置科目数据接口。
	 * @param subjectDao
	 * 科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled())logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置地区数据接口。
	 * @param areaDao 
	 *	  地区数据接口。
	 */
	public void setAreaDao(IAreaDao areaDao) {
		if(logger.isDebugEnabled()) logger.debug("地区数据接口...");
		this.areaDao = areaDao;
	}
	/**
	 * 设置状态值和名称集合。
	 * @param statusMap 
	 *	  状态值和名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("状态值和名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载教材信息。
	 * @see com.examw.test.service.syllabus.IBookService#loadBook(java.lang.String)
	 */
	@Override
	public BookInfo loadBook(String bookId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教材［bookId = %s］信息...", bookId));
		if(StringUtils.isEmpty(bookId)) return null;
		return this.changeModel(this.bookDao.load(Book.class, bookId));
	}
	@Override
	public List<BookInfo> loadBookList(String subjectId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［bookId = %s］下教材信息...", subjectId));
		return this.changeModel(this.bookDao.loadBookList(subjectId));
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.test.service.syllabus.IBookService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态［status ＝ %d］名称...", status));
		if(status == null) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Book> find(BookInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return this.bookDao.findBooks(info);
	}
	/*
	 * 统计数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(BookInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		return this.bookDao.total(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected BookInfo changeModel(Book data) {
		if(logger.isDebugEnabled())logger.debug("类型转换...");
		if(data == null) return null;
		BookInfo info = new BookInfo();
		BeanUtils.copyProperties(data, info, new String[]{"chapters"});
		if(data.getPress() != null){
			info.setPressId(data.getPress().getId());
			info.setPressName(data.getPress().getName());
		}
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if(data.getAreas() != null && data.getAreas().size() > 0){
			List<String> areaIdList = new ArrayList<>(),areaNameList = new ArrayList<>();
			for(Area area : data.getAreas()){
				if(area == null) continue;
				areaIdList.add(area.getId());
				areaNameList.add(area.getName());
			}
			info.setAreaId(areaIdList.toArray(new String[0]));
			info.setAreaName(areaNameList.toArray(new String[0]));
		}
		if(info.getStatus() != null){
			info.setStatusName(this.loadStatusName(info.getStatus()));
		}
		return info;
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public BookInfo update(BookInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		Boolean isAdded = false;
		Book data = StringUtils.isEmpty(info.getId()) ? null : this.bookDao.load(Book.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Book();
			info.setCreateTime(new Date());
		}else {
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		BeanUtils.copyProperties(info, data, new String[]{"chapters"});
		//出版社
		data.setPress(StringUtils.isEmpty(info.getPressId()) ?  null  : this.pressDao.load(Press.class, info.getPressId()));
		//科目
		data.setSubject(StringUtils.isEmpty(info.getSubjectId()) ? null  : this.subjectDao.load(Subject.class, info.getSubjectId()));
		//地区
		Set<Area> areas = new HashSet<>();
		if(info.getAreaId() != null && info.getAreaId().length > 0){
			for(String id : info.getAreaId()){
				if(StringUtils.isEmpty(id)) continue;
				Area area = this.areaDao.load(Area.class, id);
				if(area != null) areas.add(area);
			}
		}
		data.setAreas((areas == null || areas.size() == 0) ? null : areas);
		if(isAdded)this.bookDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Book data = this.bookDao.load(Book.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除数据［"+ ids[i]+"］");
				this.bookDao.delete(data);
			} 
		}	
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.test.service.syllabus.IBookService#loadMaxOrder()
	 */
	@Override
	public Integer loadMaxOrder() {
		if(logger.isDebugEnabled()) logger.debug("加载最大排序号...");
		return this.bookDao.loadMaxOrder();
	}
}