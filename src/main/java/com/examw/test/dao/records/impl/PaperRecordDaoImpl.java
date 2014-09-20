package com.examw.test.dao.records.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IPaperRecordDao;
import com.examw.test.domain.records.PaperRecord;
import com.examw.test.model.records.PaperRecordInfo;

/**
 * 试卷记录数据接口实现类
 * @author fengwei.
 * @since 2014年9月17日 下午5:38:47.
 */
public class PaperRecordDaoImpl extends BaseDaoImpl<PaperRecord> implements IPaperRecordDao {
	private static final Logger logger = Logger.getLogger(PaperRecordDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.records.IPaperRecordDao#findPaperRecords(com.examw.test.model.records.PaperRecordInfo)
	 */
	@Override
	public List<PaperRecord> findPaperRecords(PaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[试卷记录]数据...");
		String hql = "from PaperRecord pr where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by pr." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计
	 * @see com.examw.test.dao.records.IPaperRecordDao#total(com.examw.test.model.records.PaperRecordInfo)
	 */
	@Override
	public Long total(PaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[试卷记录]数据统计...");
		String hql = "select count(*) from PaperRecord pr where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}

	// 添加查询条件到HQL。
	private String addWhere(PaperRecordInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getPaperId())) {
			hql += " and (pr.paperId = :paperId)";
			parameters.put("paperId", info.getPaperId());
		}
		if (!StringUtils.isEmpty(info.getUserId())) {
			hql += " and (pr.userId = :userId)";
			parameters.put("userId", info.getUserId());
		}
		if (info.getStatus() != null){
			hql += " and (pr.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 查询某试卷最高得分
	 * @see com.examw.test.dao.records.IPaperRecordDao#findMaxScore(java.lang.String)
	 */
	@Override
	public BigDecimal findMaxScore(String paperId) {
		String hql = "select MAX(pr.score)  from PaperRecord pr where paperId = :paperId";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("paperId", paperId);
		Object  obj = query.uniqueResult();
		return obj == null ? null : (BigDecimal)obj;
	}
	/*
	 * 查找参考人数
	 * @see com.examw.test.dao.records.IPaperRecordDao#findUserSum(java.lang.String)
	 */
	@Override
	public Long findUserSum(String paperId) {
		String hql = "select count(*)  from PaperRecord pr where paperId = :paperId";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("paperId", paperId);
		Object  obj = query.uniqueResult();
		return obj == null ? null : (long)obj;
	}
	/*
	 * 加载试卷
	 * @see com.examw.test.dao.records.IPaperRecordDao#load(java.lang.String, java.lang.String)
	 */
	@Override
	public PaperRecord load(String paperId, String userId) {
		String hql = "from PaperRecord pr where pr.paperId = :paperId and pr.userId = :userId order by pr.lastTime desc";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("paperId", paperId);
		query.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<PaperRecord> list = query.list();
		if(list == null || list.size() == 0) 
		return null;
		return list.get(0);
	}
}
