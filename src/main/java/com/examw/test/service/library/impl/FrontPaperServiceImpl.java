package com.examw.test.service.library.impl;

import org.apache.log4j.Logger;

import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.service.library.IFrontPaperService;

/**
 * 试卷前端服务接口。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public class FrontPaperServiceImpl implements IFrontPaperService  {
	private static final Logger logger = Logger.getLogger(FrontPaperServiceImpl.class);
	private IPaperReleaseDao paperReleaseDao;
	/**
	 * 设置试卷发布数据接口。
	 * @param paperReleaseDao 
	 *	  试卷发布数据接口。
	 */
	public void setPaperReleaseDao(IPaperReleaseDao paperReleaseDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷发布数据接口...");
		this.paperReleaseDao = paperReleaseDao;
	}
	/*
	 * 加载科目下的试卷数量。
	 * @see com.examw.test.service.library.IFrontPaperService#loadPapersCount(java.lang.String[])
	 */
	@Override
	public Integer loadPapersCount(String[] subjetsId) {
		if(logger.isDebugEnabled()) logger.debug("科目下的试卷数量...");
		return this.paperReleaseDao.loadPapersCount(subjetsId);
	}
	/*
	 * 加载科目下试题数量。
	 * @see com.examw.test.service.library.IFrontPaperService#loadItemsCount(java.lang.String[])
	 */
	@Override
	public Integer loadItemsCount(String[] subjectsId) {
		if(logger.isDebugEnabled()) logger.debug("加载科目下试题数量...");
		return this.paperReleaseDao.loadItemsCount(subjectsId);
	}
	/*
	 * 加载科目下是否有真题。
	 * @see com.examw.test.service.library.IFrontPaperService#hasRealItem(java.lang.String[])
	 */
	@Override
	public boolean hasRealItem(String[] subjectsId) {
		if(logger.isDebugEnabled()) logger.debug("加载科目下是否有真题...");
		return this.paperReleaseDao.hasRealItem(subjectsId);
	}

}