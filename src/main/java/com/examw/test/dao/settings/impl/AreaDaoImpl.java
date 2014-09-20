package com.examw.test.dao.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.settings.IAreaDao;
import com.examw.test.domain.settings.Area;
import com.examw.test.model.settings.AreaInfo;

/**
 * 行政地区数据接口实现类
 * @author fengwei.
 * @since 2014年8月6日 上午11:55:00.
 */
public class AreaDaoImpl extends BaseDaoImpl<Area> implements IAreaDao {
	private static final Logger logger = Logger.getLogger(AreaDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.settings.IAreaDao#findAreas(com.examw.test.model.settings.AreaInfo)
	 */
	@Override
	public List<Area> findAreas(AreaInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[地区]数据...");
		String hql = "from Area a where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.settings.IAreaDao#total(com.examw.test.model.settings.AreaInfo)
	 */
	@Override
	public Long total(AreaInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[地区]数据统计...");
		String hql = "select count(*) from Area a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(AreaInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (a.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 加载最大的代码值.
	 * @see com.examw.test.dao.settings.IAreaDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		final String hql = "select max(a.code) from Area a ";
		Object obj = this.uniqueResult(hql, null);
		return obj == null ? null : (int)obj;
	}
}