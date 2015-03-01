package com.examw.test.dao.library.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.service.library.ItemStatus;
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
	public List<PaperRelease> loadReleases(Integer[] paperType,String[] subjectsId, String[] areasId,Date createTime,Integer page,Integer rows){
		if(logger.isDebugEnabled()) logger.debug(String.format("按试卷类型［%1$s］科目［%2$s］地区［%3$s］加载已发布的试卷集合［page=%4$d,rows=%5$d］...", paperType, subjectsId,areasId,page,rows));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select new PaperRelease(p.id,p.title,p.paper,p.content, p.createTime,p.total) from PaperRelease p where (1=1) ");
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
		if(createTime !=null){	//增加时间的条件查询
			hqlBuilder.append(" and  (p.createTime > :createTime) ");
			parameters.put("createTime", createTime);
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
	public Integer loadPapersCount(Integer[] paperType, String[] subjectsId,String areaId) {
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
		if(!StringUtils.isEmpty(areaId)){
			hqlBuilder.append("  and (p.paper.area.id in (:areaId)) ");
			parameters.put("areaId", areaId);
		}
		Object obj = this.uniqueResult(hqlBuilder.toString(), parameters);
		return obj == null ? 0 : (int)((long)obj);
	}
	/*
	 * 加载科目下的试题数量。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#loadItemsCount(java.lang.Integer[], java.lang.String[])
	 */
	@Override
	public Integer loadItemsCount(Integer[] paperType, String[] subjectsId,String areaId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［%1$s］科目［%2$s］下的试题数量...",paperType,subjectsId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select sum(i.count) from Item i left join i.area a where (i.parent is null) ");
		Map<String, Object> parameters = new HashMap<>();
		if(subjectsId != null && subjectsId.length > 0){
			hqlBuilder.append(" and (i.subject.id in (:subjectsId)) ");
			parameters.put("subjectsId", subjectsId);
		}
		if(!StringUtils.isEmpty(areaId)){
			hqlBuilder.append("  and ((a is null) or (a.code = 1) or (a.id = :areaId)) ");
			parameters.put("areaId", areaId);
		}
		hqlBuilder.append("  and i.status = :status");
		parameters.put("status", ItemStatus.AUDIT.getValue());
		Object obj = this.uniqueResult(hqlBuilder.toString(), parameters);
		return obj == null ? 0 : (int)((long)obj);
	}
	/*
	 * 加载试卷下的试题数目。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#loadItemsCount(java.lang.String)
	 */
	@Override
	public Integer loadItemsCount(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［%s］下的试题数目...", paperId));
		if(!StringUtils.isEmpty(paperId)){
			final String hql = "from PaperRelease p where p.paper.id = :paperId order by p.createTime desc ";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paperId", paperId);
			List<PaperRelease> paperReleases = this.find(hql, parameters, 0, 1);
			if(paperReleases != null && paperReleases.size() > 0){
				return paperReleases.get(0).getTotal();
			}
		}
		return 0;
	}
	/*
	 * 加载科目是否有真题。[前台使用]
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
	/*
	 * 加载最新试卷集合。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#loadNewsReleases(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<PaperRelease> loadNewsReleases(final String examId,final Integer top) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载最新试卷集合:［%1$s - %2$d］...", examId, top));
		if(top == null) return null;
		return this.findPaperReleases(new PaperInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getExamId() { return examId;}
			@Override
			public String getSort() { return "createTime";}
			@Override
			public String getOrder() { return "desc";}
			@Override
			public Integer getPage() { return 0;}
			@Override
			public Integer getRows() { return top;}
		});
	}
	/*
	 * 查询试卷发布数据。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#findPaperReleases(com.examw.test.model.library.PaperInfo)
	 */
	@Override
	public List<PaperRelease> findPaperReleases(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询试卷发布数据...");
		String hql = "from PaperRelease p where (1 = 1 ) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += String.format(" order by p.%1$s %2$s", info.getSort(), info.getOrder());
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	//添加查询条件
	private String addWhere(PaperInfo info, String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		//试卷类型
		hql += " and (p.paper.type <= :type) ";
		parameters.put("type", PaperType.FORECAS.getValue());
		
		if(!StringUtils.isEmpty(info.getExamId())){//所属考试ID
			hql += " and (p.paper.subject.exam.id = :examId) ";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getSubjectId())){//所属科目ID
			hql += " and (p.paper.subject.id = :subjectId) ";
			parameters.put("subjectId", info.getSubjectId());
		}
		return hql;
	}
	/*
	 *  查询试卷发布数据统计。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#totalPaperReleases(com.examw.test.model.library.PaperInfo)
	 */
	@Override
	public Long totalPaperReleases(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询试卷发布数据统计...");
		String hql = "select count(*) from PaperRelease p where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
}