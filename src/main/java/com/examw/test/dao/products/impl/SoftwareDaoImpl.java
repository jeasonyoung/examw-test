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
	 * 查询数据
	 * @see com.examw.test.dao.products.ISoftwareDao#findSoftwares(com.examw.test.model.products.SoftwareInfo)
	 */
	@Override
	public List<Software> findSoftwares(SoftwareInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[产品软件]数据...");
		String hql = "from Software s where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by s." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.ISoftwareDao#total(com.examw.test.model.products.SoftwareInfo)
	 */
	@Override
	public Long total(SoftwareInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[产品软件]数据统计...");
		String hql = "select count(*) from Software s where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}

	// 添加查询条件到HQL。
	private String addWhere(SoftwareInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (s.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		if (!StringUtils.isEmpty(info.getChannelId())) {
			hql += " and (s.channel.id = :channelId)";
			parameters.put("channelId", info.getChannelId());
		}
		if (!StringUtils.isEmpty(info.getProductId())) {
			hql += " and (s.product.id = :productId)";
			parameters.put("productId", info.getProductId());
		}
		if (!StringUtils.isEmpty(info.getExamId())) {
			hql += " and (s.product.exam.id = :examId)";
			parameters.put("examId", info.getExamId());
		}
		if (!StringUtils.isEmpty(info.getSoftTypeId())) {
			hql += " and (s.type = :type)";
			parameters.put("type", info.getSoftTypeId());
		}
		if (!StringUtils.isEmpty(info.getStatus())) {
			hql += " and (s.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	
}
