package com.examw.test.service.library;

import com.examw.test.domain.library.Item;

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
	RIGTH(Item.ANSWER_JUDGE_RIGTH),
	/**
	 * 错误。
	 */
	WRONG(Item.ANSWER_JUDGE_WRONG);
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
	public static ItemJudgeAnswer convert(int value){
		switch(value){
			//正确
			case Item.ANSWER_JUDGE_RIGTH:return ItemJudgeAnswer.RIGTH;
			//错误
			case Item.ANSWER_JUDGE_WRONG: return ItemJudgeAnswer.WRONG;
		}
		throw new RuntimeException("不存在！");
	}
}