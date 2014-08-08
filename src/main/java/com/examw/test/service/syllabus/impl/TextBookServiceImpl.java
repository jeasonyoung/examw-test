package com.examw.test.service.syllabus.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.dao.syllabus.IPressDao;
import com.examw.test.dao.syllabus.ITextBookDao;
import com.examw.test.domain.settings.Subject;
import com.examw.test.domain.syllabus.Press;
import com.examw.test.domain.syllabus.TextBook;
import com.examw.test.model.syllabus.TextBookInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.ITextBookService;
/**
 * 教材服务接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class TextBookServiceImpl extends BaseDataServiceImpl<TextBook, TextBookInfo> implements ITextBookService {
	private static final Logger logger = Logger.getLogger(TextBookServiceImpl.class);
	private ITextBookDao bookDao;
	private ISubjectDao subjectDao;
	private IPressDao pressDao;
	/**
	 * 设置教材数据接口。
	 * @param bookDao
	 * 教材数据接口。
	 */
	public void setBookDao(ITextBookDao bookDao) {
		if(logger.isDebugEnabled())logger.debug("注入教材数据接口...");
		this.bookDao = bookDao;
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
	 * 设置出版社数据接口。
	 * @param pressDao
	 * 出版社数据接口。
	 */
	public void setPressDao(IPressDao pressDao) {
		if(logger.isDebugEnabled())logger.debug("注入出版社数据接口...");
		this.pressDao = pressDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<TextBook> find(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return this.bookDao.findTextBooks(info);
	}
	/*
	 * 统计数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		return this.bookDao.total(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected TextBookInfo changeModel(TextBook data) {
		if(logger.isDebugEnabled())logger.debug("类型转换...");
		if(data == null) return null;
		TextBookInfo info = new TextBookInfo();
		BeanUtils.copyProperties(data, info, new String[]{"type"});
		if(data.getPress() != null){
			info.setPressId(data.getPress().getId());
			info.setPressName(data.getPress().getName());
		}
		if(data.getSubject() != null){
			info.setSubId(data.getSubject().getId());
			info.setSubName(data.getSubject().getName());
		}
		return info;
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public TextBookInfo update(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		Boolean isAdded = false;
		TextBook data = StringUtils.isEmpty(info.getId()) ? null : this.bookDao.load(TextBook.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new TextBook();
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getPressId()) && (data.getPress() == null || !data.getPress().getId().equalsIgnoreCase(info.getPressId()))){
			Press p = this.pressDao.load(Press.class, info.getPressId());
			if(p != null) data.setPress(p);
		}
		if(!StringUtils.isEmpty(info.getSubId()) && (data.getSubject() == null || !data.getSubject().getId().equalsIgnoreCase(info.getSubId()))){
			Subject s = this.subjectDao.load(Subject.class, info.getSubId());
			if(s != null) data.setSubject(s);
		}
		if(data.getPress() != null){
			info.setPressId(data.getPress().getId());
			info.setPressName(data.getPress().getName());
		}
		if(data.getSubject() != null){
			info.setSubId(data.getSubject().getId());
			info.setSubName(data.getSubject().getName());
		}
		if(isAdded)this.bookDao.save(data);
		return info;
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
			TextBook data = this.bookDao.load(TextBook.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除数据［"+ ids[i]+"］");
				this.bookDao.delete(data);
			} 
		}	
	}
}