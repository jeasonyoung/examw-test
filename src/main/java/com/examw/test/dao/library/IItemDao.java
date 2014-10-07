package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.Item;
import com.examw.test.model.library.ItemInfo;

/**
 * 题目数据操作接口。
 * 
 * @author yangyong
 * @since 2014年8月8日
 */
public interface IItemDao extends IBaseDao<Item> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<Item> findItems(ItemInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(ItemInfo info);
	/**
	 * 根据校验码加载试题。
	 * @param checkCode
	 * 校验码。
	 * @return
	 * 题目对象。
	 */
	Item loadItemByCode(String checkCode);
	/**
	 * 加载试题。
	 * @param itemId
	 * 试题ID。
	 * @return
	 */
	Item loadItem(String itemId);
	/**
	 * 查询是否包含真题
	 * @param subjectIds
	 * @return
	 */
	boolean hasRealItem(String[] subjectIds);
}