package com.examw.test.dao.library.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IPaperDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.service.library.PaperStatus;

/**
 * 试卷数据接口实现类。
 * @author yangyong。
 * @since 2014-08-06.
 */
public class PaperDaoImpl extends BaseDaoImpl<Paper> implements IPaperDao {
	private static final Logger logger = Logger.getLogger(PaperDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.library.IPaperDao#findPapers(com.examw.test.model.library.PaperInfo)
	 */
	@Override
	public List<Paper> findPapers(PaperInfo info,Integer[] paperTypes) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Paper p where 1=1 "; 
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters,paperTypes);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("examName")){
				info.setSort("subject.exam.name");
			}else if(info.getSort().equalsIgnoreCase("subjectName")){
				info.setSort("subject.name");
			}else if(info.getSort().equalsIgnoreCase("areaName")){
				info.setSort("area.name");
			}else if(info.getSort().equalsIgnoreCase("sourceName")){
				info.setSort("source.name");
			}else if(info.getSort().equalsIgnoreCase("typeName")){
				info.setSort("type");
			}else if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("status");
			}
			hql += " order by p." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.library.IPaperDao#total(com.examw.test.model.library.PaperInfo)
	 */
	@Override
	public Long total(PaperInfo info,Integer[] paperTypes) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Paper p where 1=1 "; 
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters,paperTypes);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(PaperInfo info,String hql,Map<String, Object> parameters,Integer[] paperTypes){
		if(info.getStatus() != null && info.getStatus() > -1){
			hql += " and (p.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		//年份
		if(info.getYear()!=null){
			hql += " and (p.year = :year) ";
			parameters.put("year", info.getYear());
		}
		//类型
		if(paperTypes != null && paperTypes.length > 0){
			hql += " and (p.type in (:type)) ";
			parameters.put("type", paperTypes);
		}
		if(info.getType() != null){
			hql += " and (p.type = :type) ";
			parameters.put("type", info.getType());
		}
		//考试
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and ((p.subject.exam.id = :examId) or (p.subject.exam.category.id in (select c.id  from Category c where (c.parent.id = :examId or c.id = :examId))))";
			parameters.put("examId", info.getExamId());
		}
		//科目
		if(!StringUtils.isEmpty(info.getSubjectId())){
			if(info.getSubjectId().indexOf(",") > -1){
				hql += " and (p.subject.id in (:subjectId)) ";
				parameters.put("subjectId", info.getSubjectId().split(","));
			}else {
				hql += " and (p.subject.id = :subjectId) ";
				parameters.put("subjectId", info.getSubjectId());
			}
		}
		//名称
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (p.name like :name) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Paper data) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(data == null) return;
		String msg = null;
		if(!StringUtils.isEmpty(data.getStatus()) && (data.getStatus() != PaperStatus.NONE.getValue())){
			msg = String.format("数据［paperId = %1$s, paperName = %2$s］状态为［%3$s］不允许删除！",data.getId(),data.getName(),PaperStatus.convert(data.getStatus()));
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		int count = 0;
		if(data.getStructures() != null && (count = data.getStructures().size()) > 0){
			msg = String.format("试卷［%1$s］下存在［%2$d］试卷结构，暂不能删除！", data.getName(), count);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(data.getReleases() != null && data.getReleases().size() > 0){
			 for(PaperRelease release : data.getReleases()){
				 if(release == null) continue;
				 release.setPaper(null);
			 }
		}
		super.delete(data);
	}
	/*
	 * 加载已审核的试卷总数。
	 * @see com.examw.test.dao.library.IPaperDao#totalAudit()
	 */
	@Override
	public Long totalAudit() {
		if(logger.isDebugEnabled()) logger.debug("加载已审核的试卷总数...");
		final String hql = "select count(*) from Paper p where p.status = :status "; 
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", PaperStatus.AUDIT.getValue());
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (long)obj;
	}
	/*
	 * 加载已审核的试卷。
	 * @see com.examw.test.dao.library.IPaperDao#findTopAuditPapers(java.lang.Integer)
	 */
	@Override
	public List<Paper> findTopAuditPapers(Integer top) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载已审核的试卷:%d...", top));
		final String hql = "from Paper p where p.status = :status order by p.lastTime,p.createTime";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", PaperStatus.AUDIT.getValue());
		return this.find(hql, parameters, 0, top);
	}
	/*
	 *查找试卷状态为已发布但发布表中没有数据的试卷
	 * @see com.examw.test.dao.library.IPaperDao#findTopPublishAndNotReleasePapers(java.lang.Integer)
	 */
	@Override
	public List<Paper> findTopPublishAndNotReleasePapers(Integer top) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查找试卷状态为已发布但发布表中没有数据的试卷:%d...", top));
		final String hql = "from Paper p where p.status = :status  and (p.id not in (select pr.paper.id from PaperRelease pr)) order by p.lastTime,p.createTime";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", PaperStatus.PUBLISH.getValue());
		return this.find(hql, parameters, 0, top);
	}
	/*
	 * 统计科目地区下的试卷总数。
	 * @see com.examw.test.dao.library.IPaperDao#totalPapers(java.lang.String[], java.lang.String)
	 */
	@Override
	public Integer totalPapers(String[] subjectIds, String areaId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计科目［%1$s］地区［%2$s］下的试卷总数...", Arrays.toString(subjectIds), areaId));
		if(subjectIds != null && subjectIds.length > 0){
			StringBuilder hqlBuilder = new StringBuilder();
			hqlBuilder.append("select count(*) from Paper p where (p.subject.id in (:subjectId)) ");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("subjectId", subjectIds);
			if(!StringUtils.isEmpty(areaId)){
				hqlBuilder.append(" and  ((p.area is null) or (p.area.code = 1) or (p.area.id = :areaId)) ");
				parameters.put("areaId", areaId);
			}
			String hql = hqlBuilder.toString();
			if(logger.isDebugEnabled()) logger.debug(hql);
			Object obj = this.uniqueResult(hql, parameters);
			return (obj == null) ? null : (int)((long)obj);
		}
		return null;
	}
}