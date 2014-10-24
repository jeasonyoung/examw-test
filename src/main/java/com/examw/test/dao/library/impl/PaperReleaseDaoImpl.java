package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.service.library.PaperType;

/**
 * 试卷发布数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public class PaperReleaseDaoImpl extends BaseDaoImpl<PaperRelease> implements IPaperReleaseDao {
	private static final Logger logger = Logger.getLogger(PaperReleaseDaoImpl.class);
	/*
	 * 按试卷类型科目地区加载已发布的试卷集合。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#loadReleases(java.lang.Integer[], java.lang.String[], java.lang.String[], java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PaperRelease> loadReleases(Integer[] paperType,String[] subjectsId, String[] areasId,Integer page,Integer rows){
		if(logger.isDebugEnabled()) logger.debug(String.format("按试卷类型［%1$s］科目［%2$s］地区［%3$s］加载已发布的试卷集合［page=%4$d,rows=%5$d］...", paperType, subjectsId,areasId,page,rows));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select new PaperRelease(p.id,p.title,p.paper,p.createTime,p.total) from PaperRelease p where (1=1) ");
		Map<String, Object> parameters = new HashMap<>();
		if(paperType != null && paperType.length > 0){
			hqlBuilder.append(" and (p.paper.type in (:paperType)) ");
			parameters.put("paperType", paperType);
		}
		if(subjectsId != null && subjectsId.length > 0){
			hqlBuilder.append(" and (p.paper.subject.id in (:subjectId)) ");
			parameters.put("subjectId", subjectsId);
		}
		if(areasId != null && areasId.length > 0){
			hqlBuilder.append(" and  (p.paper.area.id in (:areaId)) ");
			parameters.put("areaId", areasId);
		}
		hqlBuilder.append(" order by p.createTime desc");
		return this.query(hqlBuilder.toString(), parameters, page, rows);
	}
	/*
	 * 试卷是否已发布。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#hasRelease(java.lang.String)
	 */
	@Override
	public boolean hasRelease(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("试卷[%s]是否已发布", paperId));
		final String hql = "select count(*) from PaperRelease p where p.paper.id = :paperId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("paperId", paperId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? false : ((long)obj > 0);
	}
	/*
	 * 删除试卷发布。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#deleteRelease(java.lang.String)
	 */
	@Override
	public void deleteRelease(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷[%s]发布...", paperId));
		final String hql = "from PaperRelease p where p.paper.id = :paperId order by p.createTime";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("paperId", paperId);
		List<PaperRelease> paperReleases = this.find(hql, parameters, null, null);
		if(paperReleases != null && paperReleases.size() > 0){
			if(logger.isDebugEnabled())logger.debug(String.format("须删除［%d］分试卷...", paperReleases.size()));
			int index = 0;
			for(PaperRelease paperRelease : paperReleases){
				if(paperRelease == null) continue;
				if(logger.isDebugEnabled()) logger.debug(String.format("［%1$d］.删除试卷:[%2$s][%3$s]", ++index, paperRelease.getId(), paperRelease.getTitle()));
				this.delete(paperRelease);
			}
		}
	}
	/*
	 * 加载科目下的试卷数量。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#loadPapersCount(java.lang.Integer[], java.lang.String[])
	 */
	@Override
	public Integer loadPapersCount(Integer[] paperType, String[] subjectsId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷类型［%1$s］科目［%2$s］下的试卷数量...", paperType, subjectsId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select count(*) from PaperRelease p where (1=1) ");
		Map<String, Object> parameters = new HashMap<>();
		if(paperType != null && paperType.length > 0){
			hqlBuilder.append(" and (p.paper.type in (:paperType)) ");
			parameters.put("paperType", paperType);
		}
		if(subjectsId != null && subjectsId.length > 0){
			hqlBuilder.append("  and (p.paper.subject.id in (:subjectsId)) ");
			parameters.put("subjectsId", subjectsId);
		}
		Object obj = this.uniqueResult(hqlBuilder.toString(), parameters);
		return obj == null ? 0 : (int)((long)obj);
	}
	/*
	 * 加载科目下的试题数量。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#loadItemsCount(java.lang.Integer[], java.lang.String[])
	 */
	@Override
	public Integer loadItemsCount(Integer[] paperType, String[] subjectsId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［%1$s］科目［%2$s］下的试题数量...",paperType,subjectsId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select sum(p.total) from PaperRelease p where (1=1) ");
		Map<String, Object> parameters = new HashMap<>();
		if(paperType != null && paperType.length > 0){
			hqlBuilder.append(" and (p.paper.type in (:paperType)) ");
			parameters.put("paperType", paperType);
		}
		if(subjectsId != null && subjectsId.length > 0){
			hqlBuilder.append(" and (p.paper.subject.id in (:subjectsId)) ");
			parameters.put("subjectsId", subjectsId);
		}
		Object obj = this.uniqueResult(hqlBuilder.toString(), parameters);
		return obj == null ? 0 : (int)((long)obj);
	}
	/*
	 * 加载科目是否有真题。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#hasRealItem(java.lang.String[])
	 */
	@Override
	public boolean hasRealItem(String[] subjectsId) {
		if(logger.isDebugEnabled()) logger.debug("加载科目[" + subjectsId + "]下是否有真题...");
		if(subjectsId == null || subjectsId.length == 0) return false;
		final String hql = "select count(*) from PaperRelease p where (p.paper.type = :paperType) and (p.paper.subject.id in (:subjectsId))";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("paperType", PaperType.REAL.getValue());
		parameters.put("subjectsId", subjectsId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? false : (long)obj > 0;
	}
	/*
	 * 清理数据。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#clearPelease()
	 */
	@Override
	public void clearRelease() {
		if(logger.isDebugEnabled()) logger.debug("清理发布数据...");
		final String hql = "delete from PaperRelease p where (p.paper is null)";
		int total = this.execuateUpdate(hql, null);
		logger.debug(String.format("清理试卷已被删除的发布［%d］条记录", total));
	}
}