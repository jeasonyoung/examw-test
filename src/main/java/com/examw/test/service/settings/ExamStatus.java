package com.examw.test.service.settings;
/**
 * 考试状态。
 * 
 * @author yangyong
 * @since 2014年10月25日
 */
public enum ExamStatus {
	/**
	 * 启用。
	 */
	ENABLE(0x01),
	/**
	 * 禁用。
	 */
	DISABLE(0x00);
	private int value;
	//私有构造函数。
	private ExamStatus(int value){
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
	public static ExamStatus convert(int value){
		switch(value){
			//禁用
			case 0x00 : return ExamStatus.DISABLE;
			//启用
			case 0x01: return ExamStatus.ENABLE;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}