package com.examw.test.service.records;

import com.examw.model.Json;
import com.examw.test.domain.records.Collection;

/**
 * 试题收藏服务接口
 * @author fengwei.
 * @since 2014年9月18日 下午3:55:23.
 */
public interface ICollectionService {
	/**
	 * 插入一条做题记录
	 * @param data
	 * @return
	 */
	boolean insertCollection(Collection data);
	/**
	 * 判断用户有没有收藏题目
	 * @param structureItemId	题目ID
	 * @param userId			用户ID
	 * @return
	 */
	boolean isCollected(String structureItemId,String userId);
	/**
	 * 取消收藏
	 * @param structureItemId
	 * @param userId
	 * @return
	 */
	boolean deleteCollection(String structureItemId,String userId);
	/**
	 * 取消或添加收藏
	 * @param structureItemId
	 * @param userId
	 * @return
	 */
	Json collectOrCancel(String structureItemId,String itemId, String userId);
//	/**
//	 * 加载我的收藏产品分类
//	 * @return
//	 */
//	FrontProductInfo loadCollectionClassify(String userId);
}
