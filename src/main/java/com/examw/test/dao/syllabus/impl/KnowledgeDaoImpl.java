package com.examw.test.dao.syllabus.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.syllabus.IKnowledgeDao;
import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.model.syllabus.KnowledgeInfo;
/**
 * 知识点数据接口实现类。
 * @author lq.
 * @since2014-08-07.
 */
public class KnowledgeDaoImpl extends BaseDaoImpl<Knowledge> implements IKnowledgeDao {
	private static final Logger logger = Logger.getLogger(KnowledgeDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#findKnowledges(com.examw.test.model.syllabus.KnowledgeInfo)
	 */
	@Override
	public List<Knowledge> findKnowledges(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		String hql = "from Knowledge k where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("subjectName")){
				info.setSort("subject.name");
			}
			hql += " order by k." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 统计数据。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#total(com.examw.test.model.syllabus.KnowledgeInfo)
	 */
	@Override
	public Long total(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		String hql = "select count(*) from Knowledge k where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加条件查询。
	private String addWhere(KnowledgeInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getBookId())){
			hql += "  and (k.book.id = :bookId) ";
			parameters.put("bookId", info.getBookId());
		}
		if(!StringUtils.isEmpty(info.getSyllabusName())){
			hql += " and (k.syllabus.title like :syllabusName)";
			parameters.put("syllabusName", "%"+ info.getSyllabusName() +"%");
		}
		if(!StringUtils.isEmpty(info.getSyllabusId())){
			hql += " and (k.syllabus.id = :syllabusId) ";
			parameters.put("syllabusId", info.getSyllabusId());
		}
		return hql;
	}
	/*
	 * 加载考试大纲下的知识点集合。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#loadSyllabusKnowledge(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Knowledge> loadSyllabusKnowledge(String syllabusId,String textBookId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲[syllabusId = %1$s][textBookId = %2$s]下的知识点集合...", syllabusId, textBookId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("from Knowledge k ");
		Map<String, Object> parameters = new HashMap<>();
		if(!StringUtils.isEmpty(syllabusId)){
			hqlBuilder.append(" where ").append("k.syllabus.id").append(" = ").append(" :syllabusId");
			parameters.put("syllabusId", syllabusId);
		}
		if(!StringUtils.isEmpty(textBookId)){
			hqlBuilder.append(StringUtils.isEmpty(syllabusId) ? " where " : " and ");
			hqlBuilder.append(" k.book.id").append(" = ").append(" :textBookId ");
			parameters.put("textBookId", textBookId);
		}
		hqlBuilder.append(" order by k.code");
		return this.find(hqlBuilder.toString(), parameters, null, null);
	}
	/*
	 * 加载最大代码值。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值 ...");
		final String hql = "select max(k.code) from Knowledge k ";
		Object obj = this.uniqueResult(hql, null);
		return obj == null ? null : (int)obj;
	}
}