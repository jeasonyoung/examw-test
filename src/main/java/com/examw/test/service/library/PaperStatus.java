package com.examw.test.service.library;

import com.examw.test.domain.library.Paper;

/**
 * 试卷状态枚举类。
 * @author yangyong.
 * @since 2014-08-07.
 */
public enum PaperStatus {
	/**
	 * 未审核。
	 */
	NONE(Paper.STATUS_NONE),
	/**
	 * 已审核。
	 */
	AUDIT(Paper.STATUS_AUDIT),
	/**
	 * 已发布。
	 */
	PUBLISH(Paper.STATUS_PUBLISH);
	private int value;
	//构造函数。
	private PaperStatus(int value){
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
	public static PaperStatus convert(Integer value){
		PaperStatus result = PaperStatus.NONE;
		switch(value){
			case Paper.STATUS_AUDIT: 
				result = PaperStatus.AUDIT;
			break;
			case Paper.STATUS_PUBLISH:
				result = PaperStatus.PUBLISH;
			break;
			default:break;
		}
		return result;
	}
}