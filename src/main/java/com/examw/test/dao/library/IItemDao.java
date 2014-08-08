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
	 * 根据校验码加载题目。
	 * @param checkCode
	 * 校验码。
	 * @return
	 * 题目对象。
	 */
	Item loadItem(String checkCode);
}