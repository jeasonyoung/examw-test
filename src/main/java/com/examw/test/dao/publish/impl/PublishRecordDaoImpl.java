package com.examw.test.dao.publish.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.publish.IPublishRecordDao;
import com.examw.test.domain.publish.PublishRecord;
import com.examw.test.model.publish.PublishRecordInfo;

/**
 * 发布记录数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月25日
 */
public class PublishRecordDaoImpl extends BaseDaoImpl<PublishRecord> implements IPublishRecordDao {
	private static final Logger logger = Logger.getLogger(PublishRecordDaoImpl.class);
	/*
	 * 查询发布记录数据。
	 * @see com.examw.test.dao.publish.IPublishRecordDao#findRecords(com.examw.test.model.publish.PublishRecordInfo)
	 */
	@Override
	public List<PublishRecord> findRecords(PublishRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询发布记录数据...");
		String hql = "from PublishRecord pr where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql  = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "statusName"://状态
					info.setSort("status");
					break;
				case "configurationName"://发布配置名称。
					info.setSort("configuration.name");
					break;
			}
			hql += String.format("order by pr.%1$s %2$s", info.getSort(), info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询发布记录数据统计。
	 * @see com.examw.test.dao.publish.IPublishRecordDao#total(com.examw.test.model.publish.PublishRecordInfo)
	 */
	@Override
	public Long total(PublishRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询发布记录数据统计...");
		String hql = "select count(*) from PublishRecord pr where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql  = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(PublishRecordInfo info, String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		if(info.getStatus() != null){//状态
			hql += " and (pr.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getConfigurationId())){//所属发布配置ID
			hql += " and (pr.configuration.id = :configurationId) ";
			parameters.put("configurationId", info.getConfigurationId());
		}
		if(!StringUtils.isEmpty(info.getName())){//名称
			hql += " and (pr.name like :name) ";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
}