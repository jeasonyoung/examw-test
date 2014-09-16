package com.examw.test.service.library;

import com.examw.test.domain.library.Paper;

/**
 * 试卷类型枚举
 * @author yangyong
 * @since 2014年8月7日
 */
public enum PaperType {
	/**
	 * 真题。
	 */
	REAL(Paper.TYPE_REAL),
	/**
	 * 模拟题。
	 */
	SIMU(Paper.TYPE_SIMU),
	/**
	 * 预测题。
	 */
	FORECAS(Paper.TYPE_FORECAST),
	/**
	 * 练习题。
	 */
	PRACTICE(Paper.TYPE_PRACTICE);
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
			case Paper.TYPE_REAL : return PaperType.REAL;
			//模拟题
			case Paper.TYPE_SIMU: return PaperType.SIMU;
			//预测题
			case Paper.TYPE_FORECAST: return PaperType.FORECAS;
			//练习题
			case Paper.TYPE_PRACTICE: return PaperType.PRACTICE;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}