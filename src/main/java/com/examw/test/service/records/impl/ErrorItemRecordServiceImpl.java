package com.examw.test.service.records.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.examw.test.dao.records.IErrorItemRecordDao;
import com.examw.test.domain.records.ErrorItemRecord;
import com.examw.test.service.records.IErrorItemRecordService;

/**
 * 错题记录服务接口实现类
 * @author fengwei.
 * @since 2014年9月18日 下午4:04:48.
 */
public class ErrorItemRecordServiceImpl implements IErrorItemRecordService {
	private static final Logger logger = Logger.getLogger(ErrorItemRecordServiceImpl.class);
	private IErrorItemRecordDao errorItemRecordDao;
	
	/**
	 * 设置 错题记录数据接口实现类
	 * @param errorItemRecordDao
	 * 
	 */
	public void setErrorItemRecordDao(IErrorItemRecordDao errorItemRecordDao) {
		this.errorItemRecordDao = errorItemRecordDao;
	}


	@Override
	public boolean insertRecord(ErrorItemRecord data) {
		if(logger.isDebugEnabled()) logger.debug(String.format("插入或更新一条错题记录[userId=%1$s,itemId=%2$s]",data.getUserId(),data.getItemId()));
		if(data == null) return false;
		//错一次一条记录
		data.setId(UUID.randomUUID().toString());
		data.setCreateTime(new Date());
		this.errorItemRecordDao.save(data);
		return true;
	}

}
