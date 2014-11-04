package com.examw.test.service.library;
/**
 * 
 * @author fengwei.
 * @since 2014年11月4日 下午2:33:27.
 */
public enum ErrorRecorveryStatus {
	/**
	 * 未处理。
	 */
	NONE(0x00),
	/**
	 * 已处理。
	 */
	WORKED(0x01);
	
	private int value;
	//私有构造函数。
	private ErrorRecorveryStatus(int value){
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
	public static ErrorRecorveryStatus convert(int value){
		switch(value){
			//未审核
			case 0x00 : return ErrorRecorveryStatus.NONE;
			//已审核
			case 0x01: return ErrorRecorveryStatus.WORKED;
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}
