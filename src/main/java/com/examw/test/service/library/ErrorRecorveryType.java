package com.examw.test.service.library;
/**
 * 纠错类型枚举
 * @author fengwei.
 * @since 2014年11月4日 下午2:13:57.
 */
public enum ErrorRecorveryType {
	/**
	 * 错别字
	 */
	WRONG_WORDS(0x01),
	/**
	 * 答案错误
	 */
	WRONG_ANSWER(0x02),
	/**
	 * 解析错误
	 */
	WRONG_ANALYSIS(0x03),
	/**
	 * 题目不完整
	 */
	ITEM_UNCOMPLETED(0x04),
	/**
	 * 图片不存在
	 */
	IMAGE_MISSING(0x05),
	/**
	 * 其他
	 */
	OTHERS(0x06);
	
	private int value;
	//构造函数。
	private ErrorRecorveryType(int value){
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
	public static ErrorRecorveryType convert(Integer value){
		if(value != null){
			for(ErrorRecorveryType type : ErrorRecorveryType.values()){
				if(type.getValue() == value) return type;
			}
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}
