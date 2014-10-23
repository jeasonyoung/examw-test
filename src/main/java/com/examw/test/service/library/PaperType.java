package com.examw.test.service.library;
/**
 * 试卷类型枚举
 * @author yangyong
 * @since 2014年8月7日
 */
public enum PaperType {
	/**
	 * 真题。
	 */
	REAL(0x01),
	/**
	 * 模拟题。
	 */
	SIMU(0x02),
	/**
	 * 预测题。
	 */
	FORECAS(0x03),
	/**
	 * 练习题。
	 */
	PRACTICE(0x04),
	/**
	 * 章节练习。
	 */
	CHAPTER(0x05),
	/**
	 * 每日一练。
	 */
	DAILY(0x06);
	
	private int value;
	//构造函数。
	private PaperType(int value){
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
	 * 枚举转换。
	 * @param value
	 * 枚举值。
	 * @return
	 * 枚举对象。
	 */
	public static PaperType convert(Integer value){
		switch(value){
			//真题
			case 0x01 : return PaperType.REAL;
			//模拟题
			case 0x02: return PaperType.SIMU;
			//预测题
			case 0x03: return PaperType.FORECAS;
			//练习题
			case 0x04: return PaperType.PRACTICE;
			//章节练习
			case 0x05: return PaperType.CHAPTER;
			//每日一练
			case 0x06: return PaperType.DAILY;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}