package com.examw.test.service.library;
/**
 * 题目判断题答案枚举。
 * 
 * @author yangyong
 * @since 2014年8月7日
 */
public enum ItemJudgeAnswer {
	/**
	 * 正确。
	 */
	RIGTH(0x01),
	/**
	 * 错误。
	 */
	WRONG(0x00);
	
	private int value;
	//私有构造函数。
	private ItemJudgeAnswer(int value){
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
	public static ItemJudgeAnswer convert(Integer value){
		if(value != null){
			for(ItemJudgeAnswer answer : ItemJudgeAnswer.values()){
				if(answer.getValue() == value) return answer;
			}
		}
		throw new RuntimeException("不存在［value="+value+"］！");
	}
}