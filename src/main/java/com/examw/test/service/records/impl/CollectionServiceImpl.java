package com.examw.test.service.records.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.examw.model.Json;
import com.examw.test.dao.products.IProductDao;
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
	/**
	 * 设置 产品数据接口
	 * @param productDao
	 * 
	 */
	public void setProductDao(IProductDao productDao) {
		//this.productDao = productDao;
	}

	/*
	 * 插入一条收藏记录
	 * @see com.examw.test.service.records.ICollectionService#insertCollection(com.examw.test.domain.records.Collection)
	 */
	@Override
	public boolean insertCollection(Collection data) {
		if(logger.isDebugEnabled()) logger.debug(String.format("插入一条收藏记录[userId=%1$s,itemId=%2$s]",data.getUserId(),data.getItemId()));
		if(data == null) return false;
		//写一次一条记录
		data.setId(UUID.randomUUID().toString());
		data.setCreateTime(new Date());
		this.collectionDao.save(data);
		return true;
	}
	
	@Override
	public boolean isCollected(String itemId, String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询是否有收藏记录[userId=%1$s,itemId=%2$s]",userId,itemId));
		return this.collectionDao.loadCollection(itemId, userId) != null;
	}
	
	@Override
	public boolean deleteCollection(String itemId, String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询是否有收藏记录[userId=%1$s,itemId=%2$s]",userId,itemId));
		Collection data = this.collectionDao.loadCollection(itemId, userId);
		if(data == null){
			if(logger.isDebugEnabled()) logger.debug(String.format("此收藏记录[userId=%1$s,itemId=%2$s]不存在",userId,itemId));
			return false;
		}
		this.collectionDao.delete(data);
		return true;
	}
	@Override
	public Json collectOrCancel(String itemId,String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("收藏或取消收藏[userId=%1$s,itemId=%2$s]",userId,itemId));
		Collection data = this.collectionDao.loadCollection(itemId, userId);
		Json json = new Json();
		if(data == null){
			if(logger.isDebugEnabled()) logger.debug(String.format("收藏记录[userId=%1$s,itemId=%2$s]",userId,itemId));
			data = new Collection();
			data.setCreateTime(new Date());
			data.setId(UUID.randomUUID().toString());
			data.setItemId(itemId);
			data.setItemId(itemId);
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