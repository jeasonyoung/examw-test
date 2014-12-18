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
 * 注册码数据接口实现类。
 * @author fengwei.
 * @since 2014年8月14日 上午10:29:37.
 */
public class RegistrationDaoImpl extends BaseDaoImpl<Registration> implements IRegistrationDao{
	private static final Logger logger = Logger.getLogger(RegistrationDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.products.IRegistrationDao#findRegistrations(com.examw.test.model.products.RegistrationInfo)
	 */
	@Override
	public List<Registration> findRegistrations(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Registration r where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "examName"://考试
					info.setSort("product.exam.name");
					break;
				case "productName"://产品
					info.setSort("product.name");
					break;
				case "channelName"://渠道
					info.setSort("channel.name");
					break;
				case "statusName"://状态
					info.setSort("status");
					break;
			}
			hql += String.format(" order by r.%1$s %2$s", info.getSort(), info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.products.IRegistrationDao#total(com.examw.test.model.products.RegistrationInfo)
	 */
	@Override
	public Long total(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Registration r where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(RegistrationInfo info, String hql,Map<String, Object> parameters) {
		if (info.getStatus() != null) {//状态
			hql += " and (r.status = :status)";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getChannelId())){//渠道
			hql += " and (r.channel.id = :channelId) ";
			parameters.put("channelId", info.getChannelId());
		}
		if (!StringUtils.isEmpty(info.getCode())) {//注册码
			hql += " and (r.code like :code)";
			parameters.put("code", "%" + info.getCode() + "%");
		}
		return hql;
	}
	/*
	 * 加载注册码对象。
	 * @see com.examw.test.dao.products.IRegistrationDao#loadRegistration(java.lang.String)
	 */
	@Override
	public Registration loadRegistration(String code) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载注册码［%s］对象...", code));
		if(StringUtils.isEmpty(code)) return null;
		final String hql = "from Registration r where r.code = :code order by r.lastTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("code", code);
		List<Registration> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
	/*
	 * 判断注册码是否存在。
	 * @see com.examw.test.dao.products.IRegistrationDao#existCode(java.lang.String)
	 */
	@Override
	public boolean existCode(String code) {
		if(logger.isDebugEnabled()) logger.debug(String.format("判断注册码［%s］是否存在...", code));
		if(StringUtils.isEmpty(code)) return false;
		final String hql = "select count(*) from Registration r where r.code = :code";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("code", code);
		Object obj = this.uniqueResult(hql, parameters);
		return (obj == null) ? false : ((long)obj) > 0;
	}
}