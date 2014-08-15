package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.IRegistrationLogDao;
import com.examw.test.domain.products.RegistrationLog;
import com.examw.test.model.products.RegistrationLogInfo;

/**
 * 注册码日志数据接口实现类
 * @author fengwei.
 * @since 2014年8月14日 上午11:58:20.
 */
public class RegistrationLogDaoImpl extends BaseDaoImpl<RegistrationLog> implements IRegistrationLogDao{
	private static final Logger logger = Logger.getLogger(RegistrationLogDaoImpl.class);

	/*
	 * 查询数据
	 * @see com.examw.test.dao.products.IRegistrationLogDao#findRegistrationLogs(com.examw.test.model.products.RegistrationLogInfo)
	 */
	@Override
	public List<RegistrationLog> findRegistrationLogs(RegistrationLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[注册码日志]数据...");
		String hql = "from RegistrationLog rl where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by rl." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.IRegistrationLogDao#total(com.examw.test.model.products.RegistrationLogInfo)
	 */
	@Override
	public Long total(RegistrationLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[注册码日志]数据统计...");
		String hql = "select count(*) from RegistrationLog rl where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}

	// 添加查询条件到HQL。
	private String addWhere(RegistrationLogInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getRegistrationId())) {
			hql += " and (rl.registration.id like :registrationId)";
			parameters.put("registrationId", info.getRegistrationId());
		}
		if (!StringUtils.isEmpty(info.getRegistrationCode())) {
			hql += " and (rl.registration.code like :code)";
			parameters.put("code", "%" + info.getRegistrationCode() + "%");
		}
		if (!StringUtils.isEmpty(info.getType())) {
			hql += " and (rl.softwareType.id = :type)";
			parameters.put("type", info.getType());
		}
		return hql;
	}
	
}
