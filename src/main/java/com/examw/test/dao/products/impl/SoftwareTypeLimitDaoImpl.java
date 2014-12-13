package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.ISoftwareTypeLimitDao;
import com.examw.test.domain.products.SoftwareTypeLimit;
import com.examw.test.model.products.SoftwareTypeLimitInfo;

/**
 * 软件类型限制数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月13日
 */
public class SoftwareTypeLimitDaoImpl extends BaseDaoImpl<SoftwareTypeLimit> implements ISoftwareTypeLimitDao {
	private static final Logger logger = Logger.getLogger(SoftwareTypeLimitDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.products.ISoftwareTypeLimitDao#findSoftwareTypeLimits(com.examw.test.model.products.SoftwareTypeLimitInfo)
	 */
	@Override
	public List<SoftwareTypeLimit> findSoftwareTypeLimits(SoftwareTypeLimitInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from SoftwareTypeLimit s where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += String.format(" order by s.%1$s %2$s", info.getSort(), info.getOrder());
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.products.ISoftwareTypeLimitDao#total(com.examw.test.model.products.SoftwareTypeLimitInfo)
	 */
	@Override
	public Long total(SoftwareTypeLimitInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from SoftwareTypeLimit s where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(SoftwareTypeLimitInfo info,String hql,Map<String, Object> parameters){
		if(info != null){
			if(!StringUtils.isEmpty(info.getSoftwareTypeId())){//软件类型ID。
				hql += " and (s.softwareType.id = :softwareTypeId) ";
				parameters.put("softwareTypeId", info.getSoftwareTypeId());
			}
			if(!StringUtils.isEmpty(info.getRegisterId())){//注册码ID。
				hql += " and (s.register.id = :registerId) ";
				parameters.put("registerId", info.getRegisterId());
			}
		}
		return hql;
	}
	/*
	 * 加载软件类型限制对象。
	 * @see com.examw.test.dao.products.ISoftwareTypeLimitDao#loadSoftwareTypeLimit(java.lang.String, java.lang.String)
	 */
	@Override
	public SoftwareTypeLimit loadSoftwareTypeLimit(String softwareTypeId,String registerId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载软件类型［%1$s］注册码［%2$s］限制对象...", softwareTypeId, registerId));
		if(StringUtils.isEmpty(softwareTypeId) || StringUtils.isEmpty(registerId)) return null;
		final String hql = "from SoftwareTypeLimit s where (s.softwareType.id = :softwareTypeId) and (s.register.id = :registerId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("softwareTypeId", softwareTypeId);
		parameters.put("registerId", registerId);
		List<SoftwareTypeLimit> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

}