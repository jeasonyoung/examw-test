package com.examw.test.service.records.impl;


/**
 * 试卷记录状态枚举
 * @author fengwei.
 * @since 2014年11月4日 上午11:45:59.
 */
public enum PaperRecordStatus {
	/**
	 * 未完成
	 */
	UNDONE(0x00),
	/**
	 * 已完成
	 */
	DONE(0x01);
	
	private int value;
	//构造函数。
	private PaperRecordStatus(int value){
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
	public static PaperRecordStatus convert(Integer value){
		PaperRecordStatus result = PaperRecordStatus.UNDONE;
		switch(value){
			case 0x01:
				result = PaperRecordStatus.DONE;
			break;
			default:break;
		}
		return result;
	}
}
