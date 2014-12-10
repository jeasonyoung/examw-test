package com.examw.test.service.library;
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
	SINGLE(0x01),
	/**
	 * 多选。
	 */
	MULTY(0x02),
	/**
	 * 不定向选。
	 */
	UNCERTAIN(0x03),
	/**
	 * 判断题。
	 */
	JUDGE(0x04),
	/**
	 * 问答题。
	 */
	QANDA(0x05),
	/**
	 * 共享题干题。
	 */
	SHARE_TITLE(0x06),
	/**
	 * 共享答案题。
	 */
	SHARE_ANSWER(0x07);
	
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
	public static ItemType convert(Integer value){
		if(value != null){
			for(ItemType type : ItemType.values()){
				if(type.getValue() == value) return type;
			}
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}