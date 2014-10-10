package com.examw.test.service.records.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.records.IItemRecordDao;
import com.examw.test.domain.records.ItemRecord;
import com.examw.test.service.records.IItemRecordService;

/**
 * 做题记录服务接口实现类
 * @author fengwei.
 * @since 2014年9月18日 下午2:42:02.
 */
public class ItemRecordServiceImpl implements IItemRecordService{
	private static final Logger logger = Logger.getLogger(ItemRecordServiceImpl.class);
	private IItemRecordDao itemRecordDao;
	
	/**
	 * 设置 做题记录数据接口
	 * @param itemRecordDao
	 * 
	 */
	public void setItemRecordDao(IItemRecordDao itemRecordDao) {
		this.itemRecordDao = itemRecordDao;
	}
	/*
	 * 插入一条记录
	 * @see com.examw.test.service.records.IItemRecordService#insertRecord(com.examw.test.domain.records.ItemRecord)
	 */
	@Override
	public boolean insertRecord(ItemRecord data) {
		if(logger.isDebugEnabled()) logger.debug(String.format("插入一条做题记录[userId=%1$s,itemId=%2$s]",data.getUserId(),data.getItemId()));
		if(data == null) return false;
		//做一次一条记录
		if(StringUtils.isEmpty(data.getId()))
			data.setId(UUID.randomUUID().toString());
		data.setLastTime(new Date());
		this.itemRecordDao.save(data);
		return true;
	}
	/*
	 * 插入多条记录
	 * @see com.examw.test.service.records.IItemRecordService#insertRecords(com.examw.test.domain.records.ItemRecord)
	 */
	@Override
	public Integer insertRecords(List<ItemRecord> list) {
		if(list == null || list.size() == 0 )
		return null;
		this.itemRecordDao.insertItemRecordList(list);
		return list.size();
	}
	
	
}
