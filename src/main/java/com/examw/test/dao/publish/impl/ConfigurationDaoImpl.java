package com.examw.test.dao.publish.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.service.Status;
import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.publish.IConfigurationDao;
import com.examw.test.domain.publish.Configuration;
import com.examw.test.model.publish.ConfigurationInfo;

/**
 * 发布配置数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月25日
 */
public class ConfigurationDaoImpl extends BaseDaoImpl<Configuration> implements IConfigurationDao {
	private static final Logger logger = Logger.getLogger(ConfigurationDaoImpl.class);
	/*
	 * 查询发布配置数据。
	 * @see com.examw.test.dao.publish.IConfigurationDao#findConfigurations(com.examw.test.model.publish.ConfigurationInfo)
	 */
	@Override
	public List<Configuration> findConfigurations(ConfigurationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询发布配置数据...");
		String hql = "from Configuration c where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "statusName"://状态名称
					info.setSort("status");
					break;
			}
			hql += String.format("order by c.%1$s %2$s", info.getSort(), info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询发布配置数据统计。
	 * @see com.examw.test.dao.publish.IConfigurationDao#total(com.examw.test.model.publish.ConfigurationInfo)
	 */
	@Override
	public Long total(ConfigurationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询发布配置数据统计...");
		String hql = "select count(*) from Configuration c where (1=1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(ConfigurationInfo info,String hql,Map<String, Object> parameters){
		if(info == null) return hql;
		if(info.getStatus() != null){//状态
			hql += " and (c.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getName())){//发布配置名称
			hql += " and (c.name like :name) ";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 重载删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Configuration data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0;
		if(data.getRecords() != null && (count = data.getRecords().size()) > 0){
			throw new RuntimeException(String.format("发布配置［%1$s］关联有［%2$d］发布记录，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
	/*
	 * 加载当前可用发布配置。
	 * @see com.examw.test.dao.publish.IConfigurationDao#loadTopConfiguration()
	 */
	@Override
	public Configuration loadTopConfiguration() {
		if(logger.isDebugEnabled()) logger.debug("加载当前可用发布配置...");
		final String hql = "from Configuration c where c.status = :status order by c.lastTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", Status.ENABLED.getValue());
		List<Configuration> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
}