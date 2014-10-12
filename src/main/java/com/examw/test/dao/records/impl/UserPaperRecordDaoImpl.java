package com.examw.test.dao.records.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IUserPaperRecordDao;
import com.examw.test.domain.records.UserPaperRecord;
import com.examw.test.model.records.UserPaperRecordInfo;

/**
 * 用户试卷记录数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public class UserPaperRecordDaoImpl extends BaseDaoImpl<UserPaperRecord> implements IUserPaperRecordDao {
	private static final Logger logger = Logger.getLogger(UserPaperRecordDaoImpl.class);
	/*
	 * 查询用户试卷记录数据。
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#findPaperRecords(com.examw.test.model.records.UserPaperRecordInfo)
	 */
	@Override
	public List<UserPaperRecord> findPaperRecords(UserPaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询用户试卷记录数据集合...");
		String hql = "from UserPaperRecord u where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by u." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询用户试卷记录数据统计。
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#total(com.examw.test.model.records.UserPaperRecordInfo)
	 */
	@Override
	public Long total(UserPaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询用户试卷记录数据统计...");
		String hql = "select count(*) from UserPaperRecord u where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled())logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(UserPaperRecordInfo info, String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getProductId())){
			hql += " and (u.product.id = :productId) ";
			parameters.put("productId", info.getProductId());
		}
		if(!StringUtils.isEmpty(info.getPaperId())){
			hql += " and (u.paper.id = :paperId) ";
			parameters.put("paperId", info.getPaperId());
		}
		if(!StringUtils.isEmpty(info.getUserId())){
			hql += " and (u.user.id = :userId) ";
			parameters.put("userId", info.getUserId());
		}
		if(info.getTerminalCode() != null){
			hql += " and (u.terminal.code = :terminalCode)";
			parameters.put("terminalCode", info.getTerminalCode());
		}
		if(info.getStatus() != null){
			hql += " and (u.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 查询某套试卷最高得分。
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#findMaxScore(java.lang.String)
	 */
	@Override
	public BigDecimal findMaxScore(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询试卷［paperId = %s］最高得分...", paperId));
		final String hql = "select max(u.score) from UserPaperRecord u where u.paper.id = :paperId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("paperId", paperId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? new BigDecimal(0) : (BigDecimal)obj;
	}
	/*
	 * 查询某套试卷的参考人次。
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#findUsersTotal(java.lang.String)
	 */
	@Override
	public Long findUsersTotal(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询试卷［paperId = %s］的参考人次...", paperId));
		final String hql = "select count(*) from UserPaperRecord u where u.paper.id = :paperId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("paperId", paperId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? 0 : (long)obj;
	}
	/*
	 * 加载用户的试卷记录。
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#load(java.lang.String, java.lang.String)
	 */
	@Override
	public UserPaperRecord load(String userId,String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId ＝ %1$s］试卷［ paperId= %2$s］的最新记录...",userId,paperId));
		final String hql = "from UserPaperRecord u where (u.user.id = :userId) and (u.paper.id = :paperId) order by u.lastTime desc,u.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("paperId", paperId);
		List<UserPaperRecord> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
	/*
	 * 加载某产品下用户不同试卷的最新试卷记录
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#findLastedPaperRecordsOfProduct(java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserPaperRecord> findLastedPaperRecordsOfProduct(String userId,
			String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId ＝ %1$s］产品［ productId= %2$s］的最新记录集合...",userId,productId));
		final String hql = "from UserPaperRecord u where (u.user.id = :userId) and (u.product.id = :productId) group by u.paper.id order by u.lastTime desc,u.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("productId", productId);
		return this.find(hql, parameters, null, null);
	}
}