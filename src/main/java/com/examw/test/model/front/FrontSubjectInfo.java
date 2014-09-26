package com.examw.test.model.front;

import com.examw.test.model.settings.SubjectInfo;

/**
 * 前台科目信息
 * @author fengwei.
 * @since 2014年9月25日 上午11:32:32.
 */
public class FrontSubjectInfo extends SubjectInfo{
	private static final long serialVersionUID = 1L;
	private Integer userCollectionNum; //用户收藏题目个数
	/**
	 * 获取 用户收藏题目个数
	 * @return userCollectionNum
	 * 用户收藏题目个数
	 */
	public Integer getUserCollectionNum() {
		return userCollectionNum;
	}
	/**
	 * 设置 用户收藏题目个数
	 * @param userCollectionNum
	 * 用户收藏题目个数
	 */
	public void setUserCollectionNum(Integer userCollectionNum) {
		this.userCollectionNum = userCollectionNum;
	}
	
}
