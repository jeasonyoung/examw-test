package com.examw.test.service.library;
/**
 * 题目状态枚举。
 * 
 * @author yangyong
 * @since 2014年8月7日
 */
public enum ItemStatus {
	/**
	 * 未审核。
	 */
	NONE(0x00),
	/**
	 * 已审核。
	 */
	AUDIT(0x01);
	
	private int value;
	//私有构造函数。
	private ItemStatus(int value){
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
	public static ItemStatus convert(Integer value){
		if(value != null){
			for(ItemStatus status : ItemStatus.values()){
				if(status.getValue() == value) return status;
			}
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}