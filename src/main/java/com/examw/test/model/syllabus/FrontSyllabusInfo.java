package com.examw.test.model.syllabus;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 前台大纲显示
 * @author fengwei.
 * @since 2015年3月4日 上午8:42:58.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class FrontSyllabusInfo extends SyllabusInfo{
	private static final long serialVersionUID = 1L;
	private Integer itemSum; //该章节的总题目数
	private Integer userDoneItemSum; //该章节的用户已做题数
	private Integer userErrorItemSum; //该章节的用户错题数
	/**
	 * 获取 总题目数
	 * @return itemSum
	 * 总题目数
	 */
	public Integer getItemSum() {
		return itemSum;
	}
	/**
	 * 设置 总题目数
	 * @param itemSum
	 * 总题目数
	 */
	public void setItemSum(Integer itemSum) {
		this.itemSum = itemSum;
	}
	/**
	 * 获取 用户错题数
	 * @return userErrorItemSum
	 * 用户错题数
	 */
	public Integer getUserErrorItemSum() {
		return userErrorItemSum;
	}
	/**
	 * 设置 用户错题数
	 * @param userErrorItemSum
	 * 用户错题数
	 */
	public void setUserErrorItemSum(Integer userErrorItemSum) {
		this.userErrorItemSum = userErrorItemSum;
	}
	/**
	 * 获取 用户已做题数
	 * @return userDoneItemSum
	 * 用户已做题数
	 */
	public Integer getUserDoneItemSum() {
		return userDoneItemSum;
	}
	/**
	 * 设置 用户已做题数
	 * @param userDoneItemSum
	 * 用户已做题数
	 */
	public void setUserDoneItemSum(Integer userDoneItemSum) {
		this.userDoneItemSum = userDoneItemSum;
	}
}
