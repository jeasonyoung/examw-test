package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.domain.products.Product;
import com.examw.test.model.products.ProductInfo;
/**
 * 产品数据接口实现类
 * @author fengwei.
 * @since 2014年8月12日 下午3:21:33.
 */
public class ProductDaoImpl extends BaseDaoImpl<Product> implements IProductDao{
	private static final Logger logger = Logger.getLogger(ProductDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.products.IProductDao#findProducts(com.examw.test.model.products.ProductInfo)
	 */
	@Override
	public List<Product> findProducts(ProductInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[产品]数据...");
		String hql = "from Product p where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("examName")){
				info.setSort("exam.name");
			}else if(info.getSort().equalsIgnoreCase("areaName")){
				info.setSort("area.name");
			}else if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("status");
			}
			hql += " order by p." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.IProductDao#total(com.examw.test.model.products.ProductInfo)
	 */
	@Override
	public Long total(ProductInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[产品]数据统计...");
		String hql = "select count(*) from Product p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(ProductInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (p.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		if (!StringUtils.isEmpty(info.getExamId())) {
			hql += " and (p.exam.id in (:examId))";
			parameters.put("examId", info.getExamId().split(","));
		}
		if(info.getStatus() != null){
			hql += " and (p.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.test.dao.products.IProductDao#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String examId) {
		if(logger.isDebugEnabled()) logger.debug("加载最大排序号...");
		final String hql = "select max(p.orderNo) from Product p where (p.exam.id = :examId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("examId", examId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (int)obj;
	}
	/*
	 * 加载考试下产品数据集合。
	 * @see com.examw.test.dao.products.IProductDao#loadProducts(java.lang.String)
	 */
	@Override
	public List<Product> loadProducts(String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［examId = %s］下产品数据集合...", examId));
		final String hql = "from Product p where (p.exam.id = :examId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("examId", examId);
		return this.find(hql, parameters, null, null);
	}
}