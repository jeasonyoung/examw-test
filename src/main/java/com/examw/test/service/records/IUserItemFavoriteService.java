package com.examw.test.service.records;

import com.examw.service.IDataService;
import com.examw.test.model.records.UserItemFavoriteInfo;

/**
 * 用户试题收藏服务接口。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public interface IUserItemFavoriteService extends IDataService<UserItemFavoriteInfo> {
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