package com.examw.test.dao.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.settings.SubjectInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年8月6日 下午1:45:49.
 */
public class SubjectDaoImpl extends BaseDaoImpl<Subject> implements ISubjectDao{
	private static final Logger logger = Logger.getLogger(SubjectDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.settings.ISubjectDao#findSubjects(com.examw.test.model.settings.SubjectInfo)
	 */
	@Override
	public List<Subject> findSubjects(SubjectInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[科目]数据...");
		String hql = "from Subject s where 1=1 ";
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
	 * @see com.examw.test.dao.settings.ISubjectDao#total(com.examw.test.model.settings.SubjectInfo)
	 */
	@Override
	public Long total(SubjectInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[科目]数据统计...");
		String hql = "select count(*) from Subject s where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	
	// 添加查询条件到HQL。
	private String addWhere(SubjectInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (s.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		if (!StringUtils.isEmpty(info.getExamId())) {
			hql += " and (s.exam.id = :examId)";
			parameters.put("examId", info.getExamId());
		}
		if (!StringUtils.isEmpty(info.getCategoryId())) {
			hql += " and (s.exam.category.id = :categoryId)";
			parameters.put("categoryId", info.getCategoryId());
		}
		if (!StringUtils.isEmpty(info.getAreaId())) {
			hql += " and (s.area.id = :areaId)";
			parameters.put("areaId", info.getAreaId());
		}
		return hql;
	}
}