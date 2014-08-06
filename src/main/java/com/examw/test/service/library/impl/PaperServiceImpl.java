package com.examw.test.service.library.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.IPaperService;

/**
 * 试卷服务接口实现类。
 * @author yangyong.
 * @since 2014-08-06.
 */
public class PaperServiceImpl extends BaseDataServiceImpl<Paper, PaperInfo> implements IPaperService {
	private static final Logger logger = Logger.getLogger(PaperServiceImpl.class);
	private IPaperDao paperDao;
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Paper> find(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.paperDao.findPapers(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PaperInfo changeModel(Paper data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Long total(PaperInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaperInfo update(PaperInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		
	}

}