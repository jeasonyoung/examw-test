package com.examw.test.service.records;
/**
 * 试卷记录状态。
 * 
 * @author yangyong
 * @since 2014年10月10日
 */
public enum UserPaperRecordStatus {
	/**
	 * 未完成。
	 */
	NONE(0x00),
	/**
	 * 已完成。
	 */
	FINISH(0x01);
	private int value;
	//私有构造函数。
	private UserPaperRecordStatus(int value){
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
	public static UserPaperRecordStatus convert(int value){
		switch(value){
			//未完成
			case 0x00 : return UserPaperRecordStatus.NONE;
			//已完成
			case 0x01: return UserPaperRecordStatus.FINISH;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}