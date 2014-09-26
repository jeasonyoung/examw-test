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
			if("examId".equals(info.getSort()))
			{
				info.setSort("exam.id");
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
		if(info.getStatus()!=null){
			hql += " and (p.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 加载最大代码值。
	 * @see com.examw.test.dao.products.IProductDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		final String hql = "select max(p.code) from Product p";
		Object obj = this.uniqueResult(hql, null);
		return obj == null ? null : (int)obj;
	}
}