package com.examw.test.service.records.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.records.IPaperRecordDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.records.PaperRecord;
import com.examw.test.model.records.PaperRecordInfo;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.records.IPaperRecordService;

/**
 * 试卷考试记录服务接口实现类
 * @author fengwei.
 * @since 2014年9月18日 上午10:41:19.
 */
public class PaperRecordServiceImpl implements IPaperRecordService{
	private static final Logger logger = Logger.getLogger(PaperRecordServiceImpl.class);
	private IPaperRecordDao paperRecordDao;
	private IPaperDao paperDao;
	private IPaperService paperService;
	/**
	 * 设置 试卷记录接口
	 * @param paperRecordDao
	 * 
	 */
	public void setPaperRecordDao(IPaperRecordDao paperRecordDao) {
		this.paperRecordDao = paperRecordDao;
	}
	
	/**
	 * 设置 试卷数据接口
	 * @param paperDao
	 * 
	 */
	public void setPaperDao(IPaperDao paperDao) {
		this.paperDao = paperDao;
	}
	
	
	/**
	 * 设置 试卷服务
	 * @param paperService
	 * 
	 */
	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
	}

	/*
	 * 查找考试记录
	 * @see com.examw.test.service.records.IPaperRecordService#findPaperRecords(com.examw.test.model.records.PaperRecordInfo)
	 */
	@Override
	public List<PaperRecordInfo> findPaperRecords(PaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查找考试记录...");
		List<PaperRecord> records = this.paperRecordDao.findPaperRecords(info);
		List<PaperRecordInfo> result = new ArrayList<PaperRecordInfo>();
		if(records == null || records.size()==0) return result;
		for(PaperRecord record: records){
			Paper paper = this.paperDao.load(Paper.class, record.getPaperId());
			if(paper == null) continue;
			PaperRecordInfo target = new PaperRecordInfo();
			BeanUtils.copyProperties(record, target);
			target.setPaperName(paper.getName());
			target.setType(paper.getType());
			target.setTypeName(this.paperService.loadTypeName(paper.getType()));
			result.add(target);
		}
		return result;
	}
	
	/*
	 * 查找单个考试记录
	 * @see com.examw.test.service.records.IPaperRecordService#findRecord(com.examw.test.model.records.PaperRecordInfo)
	 */
	@Override
	public PaperRecordInfo findRecord(PaperRecordInfo info) {
		if(info == null) return null;
		List<PaperRecord> list = this.paperRecordDao.findPaperRecords(info);
		if(list == null || list.size() == 0) return null;
		PaperRecordInfo result = new PaperRecordInfo();
		PaperRecord data = list.get(0);
		Paper paper = this.paperDao.load(Paper.class, data.getPaperId());
		if(paper == null) return null;
		BeanUtils.copyProperties(data, result);
		result.setPaperName(paper.getName());
		result.setType(paper.getType());
		result.setTypeName(this.paperService.loadTypeName(paper.getType()));
		return result;
	}
	
}
