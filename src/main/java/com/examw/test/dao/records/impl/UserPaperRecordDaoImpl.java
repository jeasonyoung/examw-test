package com.examw.test.dao.records.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IUserPaperRecordDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.records.UserPaperRecord;
import com.examw.test.model.records.UserPaperRecordInfo;
import com.examw.test.service.library.PaperType;

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
		//[Add by FW 2014.10.27 增加按类型查找记录的条件]
		if(info.getPaperType()!=null){
			hql += " and (u.paper.type = :type) ";
			parameters.put("type", info.getPaperType());
		}
		if(!StringUtils.isEmpty(info.getPaperName())){
			hql += " and (u.paper.name like :paperName)";
			parameters.put("paperName", "%" + info.getPaperName() + "%");
		}
		if(!StringUtils.isEmpty(info.getUserId())){
			hql += " and (u.user.id = :userId) ";
			parameters.put("userId", info.getUserId());
		}
		if(!StringUtils.isEmpty(info.getUserName())){
			hql += " and (u.user.name like :userName)";
			parameters.put("userName", "%" + info.getUserName() + "%");
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
		if(StringUtils.isEmpty(paperId)) return 0L;
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
	public List<UserPaperRecord> findLastedPaperRecordsOfProduct(String userId, String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId ＝ %1$s］产品［ productId= %2$s］的最新记录集合...",userId,productId));
		final String hql = "from UserPaperRecord u where (u.user.id = :userId) and (u.product.id = :productId) order by u.lastTime desc,u.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("productId", productId);
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 查询某产品下用户当天不同每日一练记录的总数
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#findTotalUserDailyPaperRecords(java.lang.String, java.lang.String)
	 */
	@Override
	public Long findTotalUserDailyPaperRecords(String userId, String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId ＝ %1$s］产品［ productId= %2$s］当天不同每日一练记录的总数...",userId,productId));
		final String hql = "select count(distinct u.id) from UserPaperRecord u where (u.user.id = :userId) and (u.product.id = :productId) and (u.paper.type = :type) and (u.createTime >= :createTime)";
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("productId", productId);
		parameters.put("type", PaperType.DAILY.getValue());
		parameters.put("createTime", calendar.getTime());
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (long)obj;
	}
	/*
	 * 查询终端上最新的考试记录时间
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#findRecordLastTime(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Date findRecordLastTime(String productId, String userId, Integer terminalId) {
		final String hql = "select max(u.createTime) from UserPaperRecord u where (u.user.id = :userId) and (u.product.id = :productId) and (u.terminal.code = :terminalId)order by u.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("productId", productId);
		parameters.put("terminalId", terminalId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (Date)obj;
	}
	/*
	 * 加载最热的试卷集合。
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#loadHotsPapers(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Paper> loadHotsPapers(String examId, Integer page, Integer rows) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载最热的［%1$s - %2$d - %3$d］试卷集合...", examId, page, rows));
		if(rows == null) return  null;
		 
		Map<String, Object> parameters = new HashMap<>();
		String hql =  this.addHotsPapersWhere(" select u.paper ", parameters, examId); 
		
		return this.query(hql, parameters, page, rows);
	}
	//
	private String addHotsPapersWhere(String hql, Map<String, Object> parameters, String examId){
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append(hql).append(" ");
		hqlBuilder.append(" from UserPaperRecord u ");
		if(!StringUtils.isEmpty(examId)){
			 hqlBuilder.append(" where ")
			 				.append(" (u.paper.id in (select p.id from Paper p where p.subject.exam.id = :examId)) ");
			 parameters.put("examId", examId);
		}
		hqlBuilder.append(" group by u.paper.id ").append(" order by count(u.id) desc ");
		if(logger.isDebugEnabled()) logger.debug(hqlBuilder);
		return hqlBuilder.toString();
	}
	/*
	 * 统计最热的试卷集合。
	 * @see com.examw.test.dao.records.IUserPaperRecordDao#totalHotsPapers(java.lang.String)
	 */
	@Override
	public Long totalHotsPapers(String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计最热的试卷集合:-考试:%s", examId));
		Map<String, Object> parameters = new HashMap<>();
		String hql = this.addHotsPapersWhere(" select count(u.paper.id) ", parameters, examId);
		return this.count(hql, parameters);
	}
}