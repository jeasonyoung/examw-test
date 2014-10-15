package com.examw.test.service.syllabus;
/**
 * 教材状态枚举。
 * 
 * @author yangyong
 * @since 2014年10月13日
 */
public enum BookStatus {
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
	private BookStatus(int value){
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
	public static BookStatus convert(int value){
		switch(value){
			//禁用
			case 0x00 : return BookStatus.DISABLE;
			//启用
			case 0x01: return BookStatus.ENABLE;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}