package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.INoteDao;
import com.examw.test.domain.records.Note;
import com.examw.test.model.records.NoteInfo;

/**
 * 笔记数据接口实现类
 * @author fengwei.
 * @since 2014年9月18日 上午9:50:15.
 */
public class NoteDaoImpl extends BaseDaoImpl<Note> implements INoteDao{
	private static final Logger logger = Logger.getLogger(ErrorItemRecordDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.records.INoteDao#findErrorItemRecords(com.examw.test.domain.records.Note)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Note> findNotes(NoteInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[试题笔记]数据...");
		String hql = "from Note n where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
//		if(!StringUtils.isEmpty(info.getSort())){
//			hql += " order by pr." + info.getSort() + " " + info.getOrder();
//		}
		hql += " order by n.createTime desc";
		Query query = this.getCurrentSession().createQuery(hql);
		Integer page = info.getPage();
		Integer rows = info.getRows();
		if(query != null){
			this.addParameters(query, parameters);
			if((page == null) && (rows == null))return query.list();
			if((page == null) || (page < 1)) page = 1;
			if((rows == null) || (rows < 2)) rows = 10;
			return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		}
		return null;
	}
	/*
	 * 查询统计
	 * @see com.examw.test.dao.records.INoteDao#total(com.examw.test.domain.records.Note)
	 */
	@Override
	public Long total(NoteInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[错题]数据统计...");
		String hql = "select count(*) from Note n where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	
	// 添加查询条件到HQL。
	private String addWhere(Note info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getItemId())) {
			hql += " and (n.itemId = :itemId)";
			parameters.put("itemId", info.getItemId());
		}
		if (!StringUtils.isEmpty(info.getUserId())) {
			hql += " and (n.userId = :userId)";
			parameters.put("userId", info.getUserId());
		}
		return hql;
	}
}
