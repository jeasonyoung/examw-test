package com.examw.test.service.library;

import com.examw.test.domain.library.Item;

/**
 * 题目状态枚举。
 * 
 * @author yangyong
 * @since 2014年8月7日
 */
public enum ItemStatus {
	/**
	 * 未审核。
	 */
	NONE(Item.STATUS_NONE),
	/**
	 * 已审核。
	 */
	AUDIT(Item.STATUS_AUDIT);
	private int value;
	//私有构造函数。
	private ItemStatus(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return 枚举值。
	 */
	public Integer getValue(){
		return this.value;
	}
	/**
	 * 枚举值转换。
	 * @param value
	 * @return
	 */
	public static ItemStatus convert(int value){
		switch(value){
			//未审核
			case Item.STATUS_NONE : return ItemStatus.NONE;
			//已审核
			case Item.STATUS_AUDIT: return ItemStatus.AUDIT;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}