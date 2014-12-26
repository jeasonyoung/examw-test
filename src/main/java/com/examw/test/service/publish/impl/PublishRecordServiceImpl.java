package com.examw.test.service.publish.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.publish.IConfigurationDao;
import com.examw.test.dao.publish.IPublishRecordDao;
import com.examw.test.domain.publish.Configuration;
import com.examw.test.domain.publish.PublishRecord;
import com.examw.test.model.publish.PublishRecordInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.publish.IPublishRecordService;

/**
 * 发布记录服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月26日
 */
public class PublishRecordServiceImpl extends BaseDataServiceImpl<PublishRecord, PublishRecordInfo> implements IPublishRecordService {
	private static final Logger logger = Logger.getLogger(PublishRecordServiceImpl.class);
	private IPublishRecordDao publishRecordDao;
	private IConfigurationDao configurationDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置发布记录数据接口。
	 * @param publishRecordDao 
	 *	  发布记录数据接口。
	 */
	public void setPublishRecordDao(IPublishRecordDao publishRecordDao) {
		if(logger.isDebugEnabled()) logger.debug("注入发布记录数据接口...");
		this.publishRecordDao = publishRecordDao;
	}
	/**
	 * 设置发布配置接口。
	 * @param configurationDao 
	 *	  发布配置接口。
	 */
	public void setConfigurationDao(IConfigurationDao configurationDao) {
		if(logger.isDebugEnabled()) logger.debug("注入发布配置接口...");
		this.configurationDao = configurationDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.test.service.publish.IPublishRecordService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<PublishRecord> find(PublishRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.publishRecordDao.findRecords(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PublishRecordInfo changeModel(PublishRecord data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 PublishRecord => PublishRecordInfo ...");
		PublishRecordInfo info = new PublishRecordInfo();
		BeanUtils.copyProperties(data, info);
		Configuration configuration = null;
		if((configuration = data.getConfiguration()) != null){
			info.setConfigurationId(configuration.getId());
			info.setConfigurationName(configuration.getName());
		}
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PublishRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询统计...");
		return this.publishRecordDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PublishRecordInfo update(PublishRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		PublishRecord data = StringUtils.isEmpty(info.getId()) ? null : this.publishRecordDao.load(PublishRecord.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setStartTime(new Date());
			data = new PublishRecord();
		}else {
			info.setStartTime(data.getStartTime());
			if(info.getStartTime() == null) info.setStartTime(new Date());
		}
		BeanUtils.copyProperties(info, data);
		
		data.setConfiguration(StringUtils.isEmpty(info.getConfigurationId()) ?  null : this.configurationDao.load(Configuration.class, info.getConfigurationId()));
		
		if(isAdded) this.publishRecordDao.save(data);
		
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			PublishRecord data = StringUtils.isEmpty(ids[i]) ? null : this.publishRecordDao.load(PublishRecord.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", ids[i]));
				this.publishRecordDao.delete(data);
			}
		}
	}
}