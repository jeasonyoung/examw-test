package com.examw.test.service.products;

/**
 * 产品答案解析类型枚举。
 * 
 * @author yangyong
 * @since 2014年12月9日
 */
public enum ProductAnalysisType {
	/**
	 * 没有。
	 */
	NONE(0),
	/**
	 * 部分有。
	 */
	PART(1),
	/**
	 * 有。
	 */
	ALL(2);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private ProductAnalysisType(int value){
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
	public static ProductAnalysisType convert(Integer value){
		if(value != null){
			for(ProductAnalysisType type : ProductAnalysisType.values()){
				if(type.getValue() == value) return type;
			}
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}