package com.examw.test.dao.records;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.UserItemFavorite;
import com.examw.test.model.records.UserItemFavoriteInfo;

/**
 * 用户试题收藏数据接口。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public interface IUserItemFavoriteDao extends IBaseDao<UserItemFavorite> {
	/**
	 * 查询收藏数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 收藏数据集合。
	 */
	List<UserItemFavorite> findFavorites(UserItemFavoriteInfo info);
	/**
	 * 查询收藏数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(UserItemFavoriteInfo info);
	/**
	 * 判断是否收藏试题。
	 * @param userId
	 * 用户ID。
	 * @param itemId
	 * 试题ID。
	 * @return
	 * 是否收藏。
	 */
	boolean exists(String userId,String itemId);
	/**
	 * 统计试题被收藏次数。
	 * @param itemId
	 * 试题ID。
	 * @return
	 */
	Long totalItemFavorites(String itemId);
	/**
	 * 统计用户收藏试题数量。
	 * @param userId
	 * 用户ID。
	 * @return
	 */
	Long totalUserFavorites(String userId);
}