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
			switch(info.getSort()){
				case "examName"://考试名称
					info.setSort("exam.name");
					break;
				case "areaName"://地区名称
					info.setSort("area.name");
					break;
				case "statusName"://状态名称
					info.setSort("status");
					break;
				case "analysisTypeName"://解题思路
					 info.setSort("analysisType");
					break;
				case "realTypeName"://历年真题
					info.setSort("realType");
					break;
			}
			hql += String.format(" order by p.%1$s %2$s",info.getSort(),info.getOrder());
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
		if(info.getStatus() != null){//状态
			hql += " and (p.status = :status)";
			parameters.put("status", info.getStatus());
		}
		if (!StringUtils.isEmpty(info.getCategoryId())) {//考试类别
			hql += " and (p.exam.category.id = :categoryId)";
			parameters.put("categoryId", info.getCategoryId());
		}
		if (!StringUtils.isEmpty(info.getExamId())) {//考试
			if(info.getExamId().indexOf(",") > -1){
				hql += " and (p.exam.id in (:examId))";
				parameters.put("examId", info.getExamId().split(","));
			}else{
				hql += " and (p.exam.id = :examId)";
				parameters.put("examId", info.getExamId());
			}
		}
		if (!StringUtils.isEmpty(info.getName())) {//名称
			hql += " and (p.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
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
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select max(p.orderNo) from Product p ");
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(examId)){
			hqlBuilder.append(" where (p.exam.id = :examId) ");
			parameters.put("examId", examId);
		}
		hqlBuilder.append(" order by p.orderNo desc ");
		String hql = hqlBuilder.toString();
		if(logger.isDebugEnabled()) logger.debug(hql);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (int)obj;
	}
	/*
	 * 重载数据删除。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Product data) {
		if(logger.isDebugEnabled()) logger.debug("重载数据删除...");
		if(data == null) return;
		int count = 0;
		if(data.getSoftwares() != null && (count = data.getSoftwares().size()) > 0){
			throw new RuntimeException(String.format("产品［%1$s］关联［%2$d］软件，暂不能删除！", data.getName(), count));
		}
		if(data.getRegistrations() != null && (count = data.getRegistrations().size()) > 0){
			throw new RuntimeException(String.format("产品［%1$s］关联［%2$d］注册码，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
}