package com.examw.test.dao.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.settings.ICategoryDao;
import com.examw.test.domain.settings.Category;
import com.examw.test.model.settings.CategoryInfo;

/**
 * 考试分类数据接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午12:02:19.
 */
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements ICategoryDao{
	private static final Logger logger = Logger.getLogger(CategoryDaoImpl.class);
	/*
	 * 查询考试分类数据
	 * @see com.examw.test.dao.settings.ICategoryDao#findAreas(com.examw.test.model.settings.CategoryInfo)
	 */
	@Override
	public List<Category> findCategorys(CategoryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[考试分类]数据...");
		String hql = "from Category c where c.parent = null and 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by c." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.settings.ICategoryDao#total(com.examw.test.model.settings.CategoryInfo)
	 */
	@Override
	public Long total(CategoryInfo info) {
		String hql = "select count(*) from Category c where c.parent = null and 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	
	// 添加查询条件到HQL。
	private String addWhere(CategoryInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (c.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
}
