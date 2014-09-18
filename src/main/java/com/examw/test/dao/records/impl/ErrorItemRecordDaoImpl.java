package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IErrorItemRecordDao;
import com.examw.test.domain.records.ErrorItemRecord;

/**
 * 错题记录数据接口实现类
 * @author fengwei.
 * @since 2014年9月18日 上午9:42:07.
 */
public class ErrorItemRecordDaoImpl extends BaseDaoImpl<ErrorItemRecord> implements IErrorItemRecordDao{
	private static final Logger logger = Logger.getLogger(ErrorItemRecordDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.records.IErrorItemRecordDao#findErrorItemRecords(com.examw.test.domain.records.ErrorItemRecord)
	 */
	@Override
	public List<ErrorItemRecord> findErrorItemRecords(ErrorItemRecord info) {
		if(logger.isDebugEnabled()) logger.debug("查询[错题记录]数据...");
		String hql = "from ErrorItemRecord eir where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
//		if(!StringUtils.isEmpty(info.getSort())){
//			hql += " order by pr." + info.getSort() + " " + info.getOrder();
//		}
		hql += " order by eir.createTime desc";
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, null, null);
	}
	
	/*
	 * 查询统计
	 * @see com.examw.test.dao.records.IErrorItemRecordDao#total(com.examw.test.domain.records.ErrorItemRecord)
	 */
	@Override
	public Long total(ErrorItemRecord info) {
		if(logger.isDebugEnabled()) logger.debug("查询[错题]数据统计...");
		String hql = "select count(*) from ErrorItemRecord eir where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	
	// 添加查询条件到HQL。
	private String addWhere(ErrorItemRecord info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getStructureItemId())) {
			hql += " and (eir.structureItemId = :structureItemId)";
			parameters.put("structureItemId", info.getStructureItemId());
		}
		if (!StringUtils.isEmpty(info.getItemId())) {
			hql += " and (eir.itemId = :itemId)";
			parameters.put("itemId", info.getUserId());
		}
		if (!StringUtils.isEmpty(info.getUserId())) {
			hql += " and (eir.userId = :userId)";
			parameters.put("userId", info.getUserId());
		}
		return hql;
	}
//	/*
//	 * 根据题目ID和用户ID加载错题记录
//	 * @see com.examw.test.dao.records.IErrorItemRecordDao#load(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public ErrorItemRecord load(String itemId, String userId) {
//		String hql = "from ErrorItemRecord eir where eir.itemId = :itemId and eir.userId = :userId";
//		Query query = this.getCurrentSession().createQuery(hql);
//		query.setParameter("itemId", itemId);
//		query.setParameter("userId", userId);
//		@SuppressWarnings("unchecked")
//		List<ErrorItemRecord> list = query.list();
//		if(list == null || list.size() == 0) 
//		return null;
//		return list.get(0);
//	}
	/*
	 * 批量插入
	 * @see com.examw.test.dao.records.IErrorItemRecordDao#insertRecordList(java.util.List)
	 */
	@Override
	public void insertRecordList(List<ErrorItemRecord> list) {
		Session session = this.getCurrentSession();
		for (int i=0; i<list.size();i++) {
			session.saveOrUpdate(list.get(i));
			if (i % 20 == 0) {
				//20个对象后才清理缓存，写入数据库
				session.flush();
				session.clear();
			}
				
		}
		session.flush();
		session.clear();
	}
	
}
