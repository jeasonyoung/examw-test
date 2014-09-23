package com.examw.test.service.records.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.examw.model.Json;
import com.examw.test.dao.records.ICollectionDao;
import com.examw.test.domain.records.Collection;
import com.examw.test.service.records.ICollectionService;

/**
 * 收藏服务接口实现类
 * @author fengwei.
 * @since 2014年9月18日 下午4:04:11.
 */
public class CollectionServiceImpl implements ICollectionService{
	private static final Logger logger = Logger.getLogger(CollectionServiceImpl.class);
	private ICollectionDao collectionDao;
	
	/**
	 * 设置 收藏数据接口
	 * @param collectionDao
	 * 
	 */
	public void setCollectionDao(ICollectionDao collectionDao) {
		this.collectionDao = collectionDao;
	}

	/*
	 * 插入一条收藏记录
	 * @see com.examw.test.service.records.ICollectionService#insertCollection(com.examw.test.domain.records.Collection)
	 */
	@Override
	public boolean insertCollection(Collection data) {
		if(logger.isDebugEnabled()) logger.debug(String.format("插入一条收藏记录[userId=%1$s,structureItemId=%2$s]",data.getUserId(),data.getStructureItemId()));
		if(data == null) return false;
		//写一次一条记录
		data.setId(UUID.randomUUID().toString());
		data.setCreateTime(new Date());
		this.collectionDao.save(data);
		return true;
	}
	
	@Override
	public boolean isCollected(String structureItemId, String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询是否有收藏记录[userId=%1$s,structureItemId=%2$s]",userId,structureItemId));
		return this.collectionDao.loadCollection(structureItemId, userId) != null;
	}
	
	@Override
	public boolean deleteCollection(String structureItemId, String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询是否有收藏记录[userId=%1$s,structureItemId=%2$s]",userId,structureItemId));
		Collection data = this.collectionDao.loadCollection(structureItemId, userId);
		if(data == null){
			if(logger.isDebugEnabled()) logger.debug(String.format("此收藏记录[userId=%1$s,structureItemId=%2$s]不存在",userId,structureItemId));
			return false;
		}
		this.collectionDao.delete(data);
		return true;
	}
	@Override
	public Json collectOrCancel(String structureItemId, String itemId,String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("收藏或取消收藏[userId=%1$s,structureItemId=%2$s]",userId,structureItemId));
		Collection data = this.collectionDao.loadCollection(structureItemId, userId);
		Json json = new Json();
		if(data == null){
			if(logger.isDebugEnabled()) logger.debug(String.format("收藏记录[userId=%1$s,structureItemId=%2$s]",userId,structureItemId));
			data = new Collection();
			data.setCreateTime(new Date());
			data.setId(UUID.randomUUID().toString());
			data.setItemId(itemId);
			data.setStructureItemId(structureItemId);
			data.setUserId(userId);
			this.collectionDao.save(data);
			json.setSuccess(true);
			json.setMsg("收藏成功");
			json.setData(1);
		}else{
			this.collectionDao.delete(data);
			json.setSuccess(true);
			json.setMsg("取消收藏成功");
			json.setData(0);
		}
		return json;
	}
}
