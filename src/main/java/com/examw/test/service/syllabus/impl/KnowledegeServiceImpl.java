package com.examw.test.service.syllabus.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.examw.test.dao.syllabus.IKnowledgeDao;
import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.IKnowledgeService;
/**
 * 知识点服务接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class KnowledegeServiceImpl extends BaseDataServiceImpl<Knowledge, KnowledgeInfo> implements IKnowledgeService {
	private static final Logger logger = Logger.getLogger(KnowledegeServiceImpl.class);
	private IKnowledgeDao knowDao;
	//private ISyllabusDao syllabusDao;
	//private ITextBookDao bookDao;
	/**
	 * 设置知识点数据接口。
	 * @param knowDao
	 * 知识点数据接口。
	 */
	public void setKnowDao(IKnowledgeDao knowDao) {
		if(logger.isDebugEnabled())logger.debug("注入知识点数据接口...");
		this.knowDao = knowDao;
	}
	/**
	 * 设置大纲要点数据接口。
	 * @param syllabusDao
	 * 大纲要点数据接口。
	 */
//	public void setSyllabusDao(ISyllabusDao syllabusDao) {
//		if(logger.isDebugEnabled())logger.debug("注入大纲要点数据接口...");
//		this.syllabusDao = syllabusDao;
//	}
	/**
	 * 设置教材数据接口。
	 * @param bookDao
	 * 教材数据接口。
	 */
//	public void setBookDao(ITextBookDao bookDao) {
//		if(logger.isDebugEnabled())logger.debug("注入教材数据接口...");
//		this.bookDao = bookDao;
//	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Knowledge> find(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return this.knowDao.findKnowledges(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected KnowledgeInfo changeModel(Knowledge data) {
		if(logger.isDebugEnabled())logger.debug("类型转换...");
		return null;
	}
	/*
	 * 统计数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		return this.knowDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public KnowledgeInfo update(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		return null;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
	}
}