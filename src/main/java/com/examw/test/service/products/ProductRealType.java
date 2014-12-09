package com.examw.test.service.products;
/**
 * 产品真题类型。
 * 
 * @author yangyong
 * @since 2014年12月9日
 */
public enum ProductRealType {
	/**
	 * 没有。
	 */
	NONE(0),
	/**
	 * 有。
	 */
	EXIST(1);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private ProductRealType(int value){
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
	public static ProductRealType convert(Integer value){
		if(value != null){
			for(ProductRealType type : ProductRealType.values()){
				if(type.getValue() == value) return type;
			}
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}