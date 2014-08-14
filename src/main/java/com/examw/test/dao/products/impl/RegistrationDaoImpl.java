package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.IRegistrationDao;
import com.examw.test.domain.products.Registration;
import com.examw.test.model.products.RegistrationInfo;

/**
 * 注册码数据接口实现类
 * @author fengwei.
 * @since 2014年8月14日 上午10:29:37.
 */
public class RegistrationDaoImpl extends BaseDaoImpl<Registration> implements IRegistrationDao{
	private static final Logger logger = Logger.getLogger(RegistrationDaoImpl.class);

	/*
	 * 查询数据
	 * @see com.examw.test.dao.products.IRegistrationDao#findRegistrations(com.examw.test.model.products.RegistrationInfo)
	 */
	@Override
	public List<Registration> findRegistrations(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[注册码]数据...");
		String hql = "from Registration r where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by r." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.IRegistrationDao#total(com.examw.test.model.products.RegistrationInfo)
	 */
	@Override
	public Long total(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[注册码]数据统计...");
		String hql = "select count(*) from Registration r where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}

	// 添加查询条件到HQL。
	private String addWhere(RegistrationInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getStatus())) {
			hql += " and (r.status = :status)";
			parameters.put("status", info.getStatus());
		}
		if (!StringUtils.isEmpty(info.getCode())) {
			hql += " and (r.code like :code)";
			parameters.put("code", "%" + info.getCode() + "%");
		}
		return hql;
	}
}
