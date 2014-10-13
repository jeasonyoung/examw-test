package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IUserItemFavoriteDao;
import com.examw.test.domain.records.UserItemFavorite;
import com.examw.test.model.records.UserItemFavoriteInfo;

/**
 * 用户试题收藏数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public class UserItemFavoriteDaoImpl extends BaseDaoImpl<UserItemFavorite> implements IUserItemFavoriteDao {
	private static final Logger logger = Logger.getLogger(UserItemFavoriteDaoImpl.class);
	/*
	 * 查询收藏数据。
	 * @see com.examw.test.dao.records.IUserItemFavoriteDao#findFavorites(com.examw.test.model.records.UserItemFavoriteInfo)
	 */
	@Override
	public List<UserItemFavorite> findFavorites(UserItemFavoriteInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询收藏数据...");
		String hql = "from UserItemFavorite u where (1=1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by u." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询收藏数据统计。
	 * @see com.examw.test.dao.records.IUserItemFavoriteDao#total(com.examw.test.model.records.UserItemFavoriteInfo)
	 */
	@Override
	public Long total(UserItemFavoriteInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询收藏数据统计...");
		String hql = "select count(*) from UserItemFavorite u where (1=1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled())logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(UserItemFavoriteInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getUserId())){
			hql += " and (u.user.id = :userId) ";
			parameters.put("userId", info.getUserId());
		}
		if(!StringUtils.isEmpty(info.getItemId())){
			hql += " and (u.itemId = :itemId) ";
			parameters.put("itemId", info.getItemId());
		}
		if(info.getItemType() != null){
			hql += " and (u.itemType = :itemType)";
			parameters.put("itemType", info.getItemType());
		}
		if(!StringUtils.isEmpty(info.getSubjectId())){
			hql += " and (u.subject.id = :subjectId) ";
			parameters.put("subjectId", info.getSubjectId());
		}
		if(info.getTerminalCode() != null){
			hql += " and (u.terminal.code = :terminalCode) ";
			parameters.put("terminalCode", info.getTerminalCode());
		}
		return hql;
	}
	/*
	 * 判断是否收藏试题。
	 * @see com.examw.test.dao.records.IUserItemFavoriteDao#exists(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean exists(String userId, String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("判断用户［userId = %1$s］是否收藏试题［itemId = %2$s］...", userId,itemId));
		final String hql = "select count(*) from UserItemFavorite u where (u.user.id = :userId) and (u.itemId = :itemId)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("itemId", itemId);
		return this.count(hql, parameters) > 0;
	}
	/*
	 * 统计试题被收藏次数。
	 * @see com.examw.test.dao.records.IUserItemFavoriteDao#totalItemFavorites(java.lang.String)
	 */
	@Override
	public Long totalItemFavorites(String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计试题［itemId = %s］被收藏次数...", itemId));
		final String hql = "select count(*) from UserItemFavorite u where (u.itemId = :itemId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("itemId", itemId);
		return this.count(hql, parameters);
	}
	/*
	 * 统计用户收藏试题数量。
	 * @see com.examw.test.dao.records.IUserItemFavoriteDao#totalUserFavorites(java.lang.String)
	 */
	@Override
	public Long totalUserFavorites(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计用户［userId = %s］收藏试题数量...", userId));
		final String hql = "select count(*) from UserItemFavorite u where (u.user.id = :userId)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		return this.count(hql, parameters);
	}
	/*
	 * 查询收藏试题
	 * @see com.examw.test.dao.records.IUserItemFavoriteDao#load(java.lang.String, java.lang.String)
	 */
	@Override
	public UserItemFavorite load(String userId, String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("判断用户［userId = %1$s］是否收藏试题［itemId = %2$s］...", userId,itemId));
		final String hql = "from UserItemFavorite u where (u.user.id = :userId) and (u.itemId = :itemId)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("itemId", itemId);
		List<UserItemFavorite> list = this.find(hql, parameters, null, null);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
}