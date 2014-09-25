package com.examw.test.service.library;

import java.util.Set;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.StructureShareItemScore;
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.model.library.ItemInfo;

/**
 * 试题解析器接口。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public interface ItemParser {
	/**
	 * 获取题型名。
	 * @return 题型名。
	 */
	String getTypeName();
	/**
	 * 设置试题数据访问接口。
	 * @param itemDao
	 */
	void setItemDao(IItemDao itemDao);
	/**
	 * 解析试题。
	 * @param source
	 * 解析源。
	 * @param shareItemScores
	 * 共享题信息。
	 * @param target
	 * 解析目标。
	 */
	void parser(BaseItemInfo<?> source,Set<StructureShareItemScore> shareItemScores, Item target);
	/**
	 * 解析试题。
	 * @param source
	 * @return
	 */
	ItemInfo parser(Item source);
}