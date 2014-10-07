package com.examw.test.service.library;

import com.examw.test.domain.library.Item;
import com.examw.test.model.library.BaseItemInfo;
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
	 * 计算包含的试题数量。
	 * @param source
	 * @return
	 */
	Integer calculationCount(BaseItemInfo<?> source);
	/**
	 * 解析试题。
	 * @param source
	 * 解析源。
	 * @param target
	 * 解析目标。
	 */
	void parser(BaseItemInfo<?> source, Item target);
	/**
	 * 试题数据模型转换。
	 * @param source
	 * @param target
	 */
	void conversion(Item source, BaseItemInfo<?> target);
}