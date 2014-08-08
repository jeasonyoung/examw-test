package com.examw.test.dao.syllabus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.syllabus.ISyllabusDao;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.SyllabusInfo;
/**
 * 大纲数据接口实现类。
 * @author lq.
 * @since 2014-08-06.
 */
public class SyllabusDaoImpl extends BaseDaoImpl<Syllabus> implements ISyllabusDao {
	private static final Logger logger = Logger.getLogger(SyllabusDaoImpl.class);
	/*
	 * 加载一级大纲。
	 * @see com.examw.test.dao.syllabus.ISyllabusDao#loadFristSyllabuss(java.lang.String)
	 */
	@Override
	public List<Syllabus> loadFristSyllabuss(String subId) {
		if(logger.isDebugEnabled()) logger.debug("加载一级大纲数据［deptId="+subId+"］集合...");
		String hql = "from Syllabus s where (s.parent is null) ";
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(subId)){
			hql +=" and (s.subject.id = :subId)";
			parameters.put("subId", subId);
		}
		if(logger.isDebugEnabled())logger.debug(hql);
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.syllabus.ISyllabusDao#findSyllabuss(com.examw.test.model.syllabus.SyllabusInfo)
	 */
	@Override
	public List<Syllabus> findSyllabuss(SyllabusInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		String hql = "from Syllabus s where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("subName")){
				info.setSort("subject.name");
			}
			hql += " order by s." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 数据统计
	 * @see com.examw.test.dao.syllabus.ISyllabusDao#total(com.examw.test.model.syllabus.SyllabusInfo)
	 */
	@Override
	public Long total(SyllabusInfo info) {
		if(logger.isDebugEnabled())logger.debug("数据统计...");
		String hql = "select count(*) from Syllabus s where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//查询条件
	private String addWhere(SyllabusInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getSubId())){
			hql += " and (s.subject.id = :subId) ";
			parameters.put("subId", info.getSubId());
		}
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (s.subject.exam.id = :examId) ";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getTitle())){
			hql += " and (s.title like :title) ";
			parameters.put("title", "%"+ info.getTitle() +"%");
		}
		return hql;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Syllabus data){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(data == null) return;
		if(data.getChildren() != null){
			for(Syllabus p : data.getChildren()){
				if(p == null) continue;
				if(logger.isDebugEnabled()) logger.debug("删除大纲［"+p.getId()+"］");
				this.delete(p);
			}
		}
		super.delete(data);
	}
}