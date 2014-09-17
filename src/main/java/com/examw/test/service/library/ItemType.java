package com.examw.test.service.library;

import com.examw.test.domain.library.Item;
/**
 * 题型枚举。
 * 
 * @author yangyong
 * @since 2014年8月7日
 */
public enum ItemType {
	/**
	 * 单选。
	 */
	SINGLE(Item.TYPE_SINGLE),
	/**
	 * 多选。
	 */
	MULTY(Item.TYPE_MULTY),
	/**
	 * 不定向选。
	 */
	UNCERTAIN(Item.TYPE_UNCERTAIN),
	/**
	 * 判断题。
	 */
	JUDGE(Item.TYPE_JUDGE),
	/**
	 * 问答题。
	 */
	QANDA(Item.TYPE_QANDA),
	/**
	 * 共享题干题。
	 */
	SHARE_TITLE(Item.TYPE_SHARE_TITLE),
	/**
	 * 共享答案题。
	 */
	SHARE_ANSWER(Item.TYPE_SHARE_ANSWER);
	private int value;
	//私有构造函数。
	private ItemType(int value){
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
	public static ItemType convert(int value){
		switch(value){
			//单选
			case Item.TYPE_SINGLE : return ItemType.SINGLE;
			//多选
			case Item.TYPE_MULTY: return ItemType.MULTY;
			//不定向选
			case Item.TYPE_UNCERTAIN:return ItemType.UNCERTAIN;
			//判断题目
			case Item.TYPE_JUDGE:return ItemType.JUDGE;
			//问答题
			case Item.TYPE_QANDA:return ItemType.QANDA;
			//共享题干题
			case Item.TYPE_SHARE_TITLE:return ItemType.SHARE_TITLE;
			//共享答案题
			case Item.TYPE_SHARE_ANSWER:return ItemType.SHARE_ANSWER;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}