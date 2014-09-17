package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.ISourceDao;
import com.examw.test.domain.library.Source;
import com.examw.test.model.library.SourceInfo;

/**
 * 来源数据接口实现类。
 * @author yangyong.
 * @since 2014-08-06.
 */
public class SourceDaoImpl extends BaseDaoImpl<Source> implements ISourceDao {
	private static final Logger logger = Logger.getLogger(SourceDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.library.ISourceDao#findSources(com.examw.test.model.library.SourceInfo)
	 */
	@Override
	public List<Source> findSources(SourceInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Source s where 1=1 "; 
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by s." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计。
	 * @see com.examw.test.dao.library.ISourceDao#total(com.examw.test.model.library.SourceInfo)
	 */
	@Override
	public Long total(SourceInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Source s where 1=1 "; 
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//查询条件。
	private String addWhere(SourceInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (s.code like :name or s.name like :name) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
}