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
import com.examw.test.service.syllabus.SyllabusStatus;
/**
 * 大纲数据接口实现类。
 * @author lq.
 * @since 2014-08-06.
 */
public class SyllabusDaoImpl extends BaseDaoImpl<Syllabus> implements ISyllabusDao {
	private static final Logger logger = Logger.getLogger(SyllabusDaoImpl.class);
	/*
	 *  加载科目下的最新考试大纲。
	 * @see com.examw.test.dao.syllabus.ISyllabusDao#loadSyllabussLast(java.lang.String)
	 */
	@Override
	public Syllabus loadSyllabussLast(String subjectId) {
		if(logger.isDebugEnabled()) logger.debug(String.format(" 加载科目［subjectId = %s］下的最新考试大纲...", subjectId));
		String hql = "from Syllabus s where (s.parent is null)  and (s.status = :status) and (s.subject.id = :subjectId) order by s.orderNo desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", SyllabusStatus.ENABLE.getValue());
		parameters.put("subjectId", subjectId);
		if(logger.isDebugEnabled())logger.debug(hql);
		List<Syllabus> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.syllabus.ISyllabusDao#findSyllabuss(com.examw.test.model.syllabus.SyllabusInfo)
	 */
	@Override
	public List<Syllabus> findSyllabuses(SyllabusInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		String hql = "from Syllabus s where (s.parent is null) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("subjectName")){
				info.setSort("subject.name");
			}else if (info.getSort().equalsIgnoreCase("examName")) {
				info.setSort("subject.exam.name");
			}else if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("status");
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
		String hql = "select count(*) from Syllabus s where (s.parent is null) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//查询条件
	private String addWhere(SyllabusInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (s.subject.exam.id = :examId)";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getSubjectId())){
			hql += " and (s.subject.id = :subjectId) ";
			parameters.put("subId", info.getSubjectId());
		}
		if(!StringUtils.isEmpty(info.getTitle())){
			hql += " and (s.title like :title) ";
			parameters.put("title", "%"+ info.getTitle() +"%");
		}
		return hql;
	}
	/*
	 * 加载科目下的大纲集合。
	 * @see com.examw.test.dao.syllabus.ISyllabusDao#findSyllabusesBySubject(java.lang.String)
	 */
	@Override
	public List<Syllabus> findSyllabusesBySubject(String subjectId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［subjectId = %s］下的考试大纲集合...", subjectId));
		final String hql = "from Syllabus s where (s.parent is null) and  (s.subject.id = :subjectId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("subjectId", subjectId);
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Syllabus data){
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(data == null) return;
		int size = 0;
		if(data.getKnowledges() != null && (size = data.getKnowledges().size()) > 0){
			throw new RuntimeException(String.format("关联了［%d］知识点，暂不能删除！", size));
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			for(Syllabus p : data.getChildren()){
				if(p == null) continue;
				if(logger.isDebugEnabled()) logger.debug("删除大纲［"+p.getId()+"］");
				this.delete(p);
			}
		}
		super.delete(data);
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.test.dao.syllabus.ISyllabusDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxOrder(String parentSyllabusId) {
		if(logger.isDebugEnabled()) logger.debug("加载考试大纲最大代码值...");
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select max(s.orderNo) ").append(" from Syllabus s ");
		Map<String, Object> parameters = new HashMap<>();
		if(StringUtils.isEmpty(parentSyllabusId)){
			hqlBuilder.append(" where (s.parent is null) ");
		}else {
			hqlBuilder.append(" where (s.parent.id = :pid) ");
			parameters.put("pid", parentSyllabusId);
		}
		Object obj = this.uniqueResult(hqlBuilder.toString(), parameters);
		return obj == null ? null : (int)obj;
	}
}