package com.examw.test.dao.publish.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.publish.IStaticPageDao;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.model.publish.StaticPageInfo;

/**
 * 静态页面数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月25日
 */
public class StaticPageDaoImpl extends BaseDaoImpl<StaticPage> implements IStaticPageDao {
	private static final Logger logger = Logger.getLogger(StaticPageDaoImpl.class);
	/*
	 * 查询页面数据。
	 * @see com.examw.test.dao.publish.IStaticPageDao#findPages(com.examw.test.model.publish.StaticPageInfo)
	 */
	@Override
	public List<StaticPage> findPages(StaticPageInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询页面数据统计...");
		String hql = "from StaticPage sp where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "publishName":
					info.setSort("publish.name");
					break;
			}
			hql += String.format(" sp.%1$s %2$s", info.getSort(), info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询页面数据统计。
	 * @see com.examw.test.dao.publish.IStaticPageDao#total(com.examw.test.model.publish.StaticPageInfo)
	 */
	@Override
	public Long total(StaticPageInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询页面数据统计...");
		String hql = "from StaticPage sp where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(StaticPageInfo info, String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getId())){//ID
			hql += " and (sp.id = :id) ";
			parameters.put("id", info.getId());
		}
		if(!StringUtils.isEmpty(info.getPath())){//页面路径
			hql += " and (sp.path like :path) ";
			parameters.put("path", "%" + info.getPath() + "%");
		}
		if(!StringUtils.isEmpty(info.getPublishId())){//所属发布记录ID
			hql += " and (sp.publish.id = :publishId) ";
			parameters.put("publishId", info.getPublishId());
		}
		if(!StringUtils.isEmpty(info.getPublishName())){//所属发布记录名称
			hql += " and (sp.publish.name like :publishName) ";
			parameters.put("publishName", "%" + info.getPublishName() + "%");
		}
		return hql;
	}
}