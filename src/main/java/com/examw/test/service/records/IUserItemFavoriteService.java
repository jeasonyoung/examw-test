package com.examw.test.service.records;

import java.util.List;

import com.examw.model.Json;
import com.examw.service.IDataService;
import com.examw.test.model.records.UserItemFavoriteInfo;
import com.examw.test.model.settings.FrontSubjectInfo;

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
	/**
	 * 根据条件统计用户收藏试题数量
	 * @param info
	 * @return
	 */
	Long totalUserFavorites(UserItemFavoriteInfo info);
	/**
	 * 收藏或者取消收藏
	 */
	Json favorOrCancel(UserItemFavoriteInfo info);
	/**
	 * 带收藏个数的科目信息
	 * @param productId
	 * @return
	 */
	List<FrontSubjectInfo> loadProductFrontSubjects(String productId,String userId);
	/**
	 * 从终端上传收藏
	 * @param favors
	 */
	void batchAddFavors(UserItemFavoriteInfo[] favors);
}