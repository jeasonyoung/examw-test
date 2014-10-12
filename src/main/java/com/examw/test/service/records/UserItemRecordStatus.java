package com.examw.test.service.records;
/**
 * 用户试题记录状态。
 * 
 * @author yangyong
 * @since 2014年10月10日
 */
public enum UserItemRecordStatus {
	/**
	 * 答错。
	 */
	WRONG(-0x01),
	/**
	 * 未答。
	 */
	NONE(0x00),
	/**
	 * 答对。
	 */
	RIGHT(0x01),
	/**
	 * 少选。
	 */
	LESS(0x02);
	private int value;
	//私有构造函数。
	private UserItemRecordStatus(int value){
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
	public static UserItemRecordStatus convert(int value){
		switch(value){
			//答错
			case -0x01 : return UserItemRecordStatus.WRONG;
			//未答
			case 0x00 : return UserItemRecordStatus.NONE;
			//答对
			case 0x01 : return UserItemRecordStatus.RIGHT;
			//少选
			case 0x02 : return UserItemRecordStatus.LESS;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}