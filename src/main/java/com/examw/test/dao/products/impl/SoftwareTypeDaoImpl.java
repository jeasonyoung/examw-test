package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.model.products.SoftwareTypeInfo;

/**
 * 软件类型数据接口实现类
 * @author fengwei.
 * @since 2014年8月11日 下午4:09:14.
 */
public class SoftwareTypeDaoImpl extends BaseDaoImpl<SoftwareType> implements ISoftwareTypeDao{
	private static final Logger logger = Logger.getLogger(SoftwareTypeDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.products.ISoftwareTypeDao#findSoftwareTypes(com.examw.test.model.products.SoftwareTypeInfo)
	 */
	@Override
	public List<SoftwareType> findSoftwareTypes(SoftwareTypeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[软件类型]数据...");
		String hql = "from SoftwareType st where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by st." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.ISoftwareTypeDao#total(com.examw.test.model.products.SoftwareTypeInfo)
	 */
	@Override
	public Long total(SoftwareTypeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[软件类型]数据统计...");
		String hql = "select count(*) from SoftwareType st where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}

	// 添加查询条件到HQL。
	private String addWhere(SoftwareTypeInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (st.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
}

