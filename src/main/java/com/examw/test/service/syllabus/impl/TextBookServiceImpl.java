package com.examw.test.service.syllabus.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.dao.syllabus.IPressDao;
import com.examw.test.dao.syllabus.ITextBookDao;
import com.examw.test.domain.settings.Subject;
import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.domain.syllabus.Press;
import com.examw.test.domain.syllabus.TextBook;
import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.model.syllabus.TextBookInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.IKnowledgeService;
import com.examw.test.service.syllabus.ITextBookService;
/**
 * 教材服务接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class TextBookServiceImpl extends BaseDataServiceImpl<TextBook, TextBookInfo> implements ITextBookService {
	private static final Logger logger = Logger.getLogger(TextBookServiceImpl.class);
	private ITextBookDao textBookDao;
	private ISubjectDao subjectDao;
	private IPressDao pressDao;
	private IKnowledgeService knowledgeService;
	/**
	 * 设置教材数据接口。
	 * @param textBookDao
	 * 教材数据接口。
	 */
	public void setTextBookDao(ITextBookDao textBookDao) {
		if(logger.isDebugEnabled())logger.debug("注入教材数据接口...");
		this.textBookDao = textBookDao;
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
	/**
	 * 设置知识点服务接口。
	 * @param knowledgeService 
	 *	  knowledgeService
	 */
	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<TextBook> find(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return this.textBookDao.findTextBooks(info);
	}
	/*
	 * 统计数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		return this.textBookDao.total(info);
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
		BeanUtils.copyProperties(data, info);
		if(data.getPress() != null){
			info.setPressId(data.getPress().getId());
			info.setPressName(data.getPress().getName());
		}
		if(data.getSubject() != null){
			info.setSubId(data.getSubject().getId());
			info.setSubName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		//知识点的类型转换。
		info.setKnowledges(this.changeModel(data.getKnowledges()));
		return info;
	}
	//数据模型集合转换。
	private Set<KnowledgeInfo> changeModel(Set<Knowledge> knowledges){
		if(logger.isDebugEnabled()) logger.debug("数据模型集合转换 Set<Knowledge> => Set<KnowledgeInfo> ...");
		if(knowledges == null || knowledges.size() == 0) return null;
		Set<KnowledgeInfo> knowledgeInfos = new TreeSet<>();
		for(Knowledge knowledge : knowledges){
			if(knowledge == null) continue;
			KnowledgeInfo info = this.knowledgeService.conversion(knowledge);
			if(info == null){
				knowledgeInfos.add(info);
			}
		}
		return knowledgeInfos;
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
		TextBook data = StringUtils.isEmpty(info.getId()) ? null : this.textBookDao.load(TextBook.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new TextBook();
		}
		BeanUtils.copyProperties(info, data);
		//出版社
		data.setPress(StringUtils.isEmpty(info.getPressId()) ?  null  : this.pressDao.load(Press.class, info.getPressId()));
		//科目
		data.setSubject(StringUtils.isEmpty(info.getSubId()) ? null  : this.subjectDao.load(Subject.class, info.getSubId()));
		if(isAdded)this.textBookDao.save(data);
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
			TextBook data = this.textBookDao.load(TextBook.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除数据［"+ ids[i]+"］");
				this.textBookDao.delete(data);
			} 
		}	
	}
}