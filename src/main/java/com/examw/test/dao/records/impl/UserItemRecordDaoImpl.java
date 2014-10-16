package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IUserItemRecordDao;
import com.examw.test.domain.records.UserItemRecord;
import com.examw.test.service.records.UserItemRecordStatus;

/**
 * 用户试题记录数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public class UserItemRecordDaoImpl extends BaseDaoImpl<UserItemRecord> implements IUserItemRecordDao {
	private static final Logger logger = Logger.getLogger(UserItemRecordDaoImpl.class);
	/*
	 * 加载用户错题记录集合。
	 * @see com.examw.test.dao.records.IUserItemRecordDao#loadUserErrorItems(java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserItemRecord> loadUserErrorItems(String userId, String paperId,String subjectId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］错题记录集合...", userId, paperId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("from UserItemRecord u ")
						.append(" where ").append(" (").append("u.status").append("=").append(":status ").append(") ")
						.append(" and ").append("(").append("u.paperRecord.user.id").append("=").append(":userId ").append(") ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", UserItemRecordStatus.WRONG.getValue());
		parameters.put("userId", userId);
		if(!StringUtils.isEmpty(paperId)){
			hqlBuilder.append(" and ").append("(").append("u.paperRecord.paper.id").append("=").append(":paperId").append(") ");
			parameters.put("paperId", paperId);
		}
		if(!StringUtils.isEmpty(subjectId)){
			if(!subjectId.contains(","))
			{
				hqlBuilder.append(" and ").append("(").append("u.paperRecord.paper.subject.id").append("=").append(":subjectId").append(") ");
				parameters.put("subjectId", subjectId);
			}else{
				String[] subjectIds = subjectId.split(",");
				hqlBuilder.append(" and ").append("(").append("u.paperRecord.paper.subject.id").append(" in ").append("(:subjectId)").append(") ");
				parameters.put("subjectId", subjectIds);
			}
		}
		hqlBuilder.append(" group by u.itemId");
		hqlBuilder.append(" order by ").append("u.lastTime desc").append(",").append("u.createTime desc");
		return this.find(hqlBuilder.toString(), parameters, null, null);
	}
	/*
	 * 加载用户试卷的最新试题记录。
	 * @see com.examw.test.dao.records.IUserItemRecordDao#loadUserPaperLastRecord(java.lang.String, java.lang.String)
	 */
	@Override
	public UserItemRecord loadUserPaperLastRecord(String userId, String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］的最新试题记录...", userId, paperId));
		return this.loadUserPaperLastRecord(userId, paperId, null);
	}
	/*
	 * 加载用户试卷下试题的最新记录。
	 * @see com.examw.test.dao.records.IUserItemRecordDao#loadUserPaperLastRecord(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public UserItemRecord loadUserPaperLastRecord(String userId,String paperId, String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］试题［itemId = %3$s］的最新记录...", userId, paperId, itemId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("from UserItemRecord u ")
						.append(" where ").append("(").append("u.paperRecord.user.id").append("=").append(":userId ").append(") ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", userId);
		if(!StringUtils.isEmpty(paperId)){
			hqlBuilder.append(" and ").append("(").append("u.paperRecord.paper.id").append("=").append(":paperId").append(") ");
			parameters.put("paperId", paperId);
		}
		if(!StringUtils.isEmpty(itemId)){
			hqlBuilder.append(" and ").append("(").append("u.itemId").append("=").append(":itemId").append(")");
			parameters.put("itemId", itemId);
		}
		hqlBuilder.append(" order by ").append("u.lastTime desc").append(",").append("u.createTime desc");
		List<UserItemRecord> list = this.find(hqlBuilder.toString(), parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
	/*
	 * 批量添加用户试题记录。
	 * @see com.examw.test.dao.records.IUserItemRecordDao#batchSaveItemRecord(com.examw.test.domain.records.UserItemRecord[])
	 */
	@Override
	public void batchSaveItemRecord(UserItemRecord[] itemRecords) {
		if(logger.isDebugEnabled()) logger.debug("批量添加用户试题记录...");
		if(itemRecords == null || itemRecords.length == 0) return;
		Session session = null;
		try {
			session = this.getCurrentSession();
			session.beginTransaction();
			UserItemRecord record = null;
			for(int i = 0; i < itemRecords.length; i++){
				record = itemRecords[i];
				if(record == null) continue;
				session.saveOrUpdate(record);
				if(i%10 == 0){
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();//提交事务
		} catch (Exception e) {
			session.getTransaction().rollback();
			logger.error("发生异常：" + e.getMessage(), e);
			throw e;
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}
}