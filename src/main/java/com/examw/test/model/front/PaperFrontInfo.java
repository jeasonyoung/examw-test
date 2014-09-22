package com.examw.test.model.front;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.support.CustomDateSerializer;
import com.examw.test.model.library.PaperInfo;

/**
 * 试卷前台信息
 * @author fengwei.
 * @since 2014年9月19日 上午9:19:15.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PaperFrontInfo extends PaperInfo{
	private static final long serialVersionUID = 1L;
	private BigDecimal maxScore;	//最高分
	private Integer userSum;	//参考人数
	private BigDecimal userScore; //当前用户得分
	private Integer userTime;//当前用户用时
	private Date userExamTime;//当前用户参考时间
	private Integer userStatus; //当前用户参加该考试的状态[完成|未完成]
	/**
	 * 获取 最高分
	 * @return maxScore
	 * 最高分
	 */
	public BigDecimal getMaxScore() {
		return maxScore;
	}
	/**
	 * 设置 最高分
	 * @param maxScore
	 * 最高分
	 */
	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}
	/**
	 * 获取 参考人数
	 * @return userSum
	 * 参考人数
	 */
	public Integer getUserSum() {
		return userSum;
	}
	/**
	 * 设置 参考人数
	 * @param userSum
	 * 参考人数
	 */
	public void setUserSum(Integer userSum) {
		this.userSum = userSum;
	}
	/**
	 * 获取 当前用户得分
	 * @return userScore
	 * 当前用户得分
	 */
	public BigDecimal getUserScore() {
		return userScore;
	}
	/**
	 * 设置 当前用户得分
	 * @param userScore
	 * 当前用户得分
	 */
	public void setUserScore(BigDecimal userScore) {
		this.userScore = userScore;
	}
	
	/**
	 * 获取 当前用户考试用时
	 * @return userTime
	 * 当前用户考试用时
	 */
	public Integer getUserTime() {
		return userTime;
	}
	/**
	 * 设置 当前用户考试用时
	 * @param userTime
	 * 当前用户考试用时
	 */
	public void setUserTime(Integer userTime) {
		this.userTime = userTime;
	}
	/**
	 * 获取 当前用户参考时间
	 * @return userExamTime
	 * 当前用户参考时间
	 */
	@JsonSerialize(using=CustomDateSerializer.ShortDate.class)
	public Date getUserExamTime() {
		return userExamTime;
	}
	/**
	 * 设置 当前用户参考时间
	 * @param userExamTime
	 * 当前用户参考时间
	 */
	public void setUserExamTime(Date userExamTime) {
		this.userExamTime = userExamTime;
	}
	/**
	 * 获取 当前用户参加该考试的状态[完成|未完成]
	 * @return status
	 * 当前用户参加该考试的状态[完成|未完成]
	 */
	public Integer getUserStatus() {
		return userStatus;
	}
	/**
	 * 设置 当前用户参加该考试的状态[完成|未完成]
	 * @param status
	 * 当前用户参加该考试的状态[完成|未完成]
	 */
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
}
