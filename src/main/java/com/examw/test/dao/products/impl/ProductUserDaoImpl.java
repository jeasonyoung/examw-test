package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.IProductUserDao;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.model.products.ProductUserInfo;

/**
 * 产品用户数据接口实现类
 * @author fengwei.
 * @since 2014年8月11日 下午4:02:13.
 */
public class ProductUserDaoImpl extends BaseDaoImpl<ProductUser> implements IProductUserDao{
	private static final Logger logger = Logger.getLogger(ProductUserDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.products.IProductUserDao#findProductUsers(com.examw.test.model.products.ProductUserInfo)
	 */
	@Override
	public List<ProductUser> findProductUsers(ProductUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[产品用户]数据...");
		String hql = "from ProductUser pu where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by pu." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.IProductUserDao#total(com.examw.test.model.products.ProductUserInfo)
	 */
	@Override
	public Long total(ProductUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[产品用户]数据统计...");
		String hql = "select count(*) from ProductUser pu where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(ProductUserInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (pu.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		if (!StringUtils.isEmpty(info.getMobile())) {
			hql += " and (pu.mobile like :mobile)";
			parameters.put("mobile", "%" + info.getMobile() + "%");
		}
		if (!StringUtils.isEmpty(info.getStatus())) {
			hql += " and (pu.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 根据用户代码加载数据。
	 * @see com.examw.test.dao.products.IProductUserDao#loadUserByCode(java.lang.String)
	 */
	@Override
	public ProductUser loadUserByCode(String code) {
		if(logger.isDebugEnabled()) logger.debug(String.format("用户代码［code = %s］加载数据...", code));
		final String hql = "from ProductUser pu where pu.code = :code order by pu.lastTime desc,pu.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("code", code);
		List<ProductUser> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
}