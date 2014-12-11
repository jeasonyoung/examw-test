package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.ISoftwareDao;
import com.examw.test.domain.products.Software;
import com.examw.test.model.products.SoftwareInfo;

/**
 * 产品软件数据接口实现类
 * @author fengwei.
 * @since 2014年8月13日 上午11:32:33.
 */
public class SoftwareDaoImpl extends BaseDaoImpl<Software> implements ISoftwareDao{
	private static final Logger logger = Logger.getLogger(SoftwareDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.products.ISoftwareDao#findSoftwares(com.examw.test.model.products.SoftwareInfo)
	 */
	@Override
	public List<Software> findSoftwares(SoftwareInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Software s where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "softTypeName"://软件类型
					info.setSort("type.name");
					break;
				case "channelName"://渠道
					info.setSort("channel.name");
					break;
				case "productName"://所属产品
					info.setSort("product.name");
					break;
				case "examName"://所属考试
					info.setSort("product.exam.name");
					break;
				case "statusName":
					info.setSort("status");
					break;
			}
			
			hql += String.format(" order by s.%1$s %2$s",info.getSort(),info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.products.ISoftwareDao#total(com.examw.test.model.products.SoftwareInfo)
	 */
	@Override
	public Long total(SoftwareInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Software s where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(SoftwareInfo info, String hql,Map<String, Object> parameters) {
		if(!StringUtils.isEmpty(info.getCategoryId())){//考试类别
			hql += " and (s.product.exam.category.id = :categoryId) ";
			parameters.put("categoryId", info.getCategoryId());
		}
		if (!StringUtils.isEmpty(info.getExamId())) {//考试
			hql += " and (s.product.exam.id = :examId)";
			parameters.put("examId", info.getExamId());
		}
		if (!StringUtils.isEmpty(info.getProductId())) {//产品
			hql += " and (s.product.id = :productId)";
			parameters.put("productId", info.getProductId());
		}
		if (!StringUtils.isEmpty(info.getSoftTypeId())) {//类型
			hql += " and (s.type.id = :type)";
			parameters.put("type", info.getSoftTypeId());
		}
		if (!StringUtils.isEmpty(info.getChannelId())) {//渠道
			hql += " and (s.channel.id = :channelId)";
			parameters.put("channelId", info.getChannelId());
		}
		if (info.getStatus() != null) {//状态
			hql += " and (s.status = :status)";
			parameters.put("status", info.getStatus());
		}
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (s.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.test.dao.products.ISoftwareDao#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品［%s］下的最大排序号...", productId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append(" select max(s.orderNo) from Software s ");
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(productId)){
			hqlBuilder.append(" where (s.product.id = :productId) ");
			parameters.put("productId", productId);
		}
		hqlBuilder.append(" order by s.orderNo desc ");
		String hql = hqlBuilder.toString();
		if(logger.isDebugEnabled()) logger.debug(hql);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (int)obj;
	}
}