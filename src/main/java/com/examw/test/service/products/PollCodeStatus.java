package com.examw.test.service.products;
/**
 * 注册码状态枚举。
 * 
 * @author yangyong
 * @since 2014年12月13日
 */
public enum PollCodeStatus {
	/**
	 * 未激活。
	 */
	NONE(0),
	/**
	 * 已激活。
	 */
	ACTIVE(1),
	/**
	 * 禁用。
	 */
	DISABLE(-1),
	/**
	 * 注销。
	 */
	LOGOFF(-2);
	
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private PollCodeStatus(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return 枚举值。
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 枚举值转换。
	 * @param value
	 * @return
	 */
	public static PollCodeStatus convert(Integer value){
		if(value != null){
			for(PollCodeStatus status : PollCodeStatus.values()){
				if(status.getValue() == value) return status;
			}
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}