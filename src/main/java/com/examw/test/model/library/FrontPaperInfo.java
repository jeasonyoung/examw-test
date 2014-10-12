package com.examw.test.model.library;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 前端试卷摘要信息。
 * 
 * @author yangyong
 * @since 2014年9月26日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class FrontPaperInfo extends BasePaperInfo {
	private static final long serialVersionUID = 1L;
	private Integer total;
	//2014.10.12增加属性
	private Long userTotal; //参考人次
	private BigDecimal maxScore;	//最高得分
	/**
	 * 获取试题总数。
	 * @return 试题总数。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置试题总数。
	 * @param total 
	 *	  试题总数。
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * 获取 参考人次
	 * @return userTotal
	 * 参考人次
	 */
	public Long getUserTotal() {
		return userTotal;
	}
	/**
	 * 设置 参考人次
	 * @param userTotal
	 * 参考人次
	 */
	public void setUserTotal(Long userTotal) {
		this.userTotal = userTotal;
	}
	/**
	 * 获取 最高得分
	 * @return maxScore
	 * 最高得分
	 */
	public BigDecimal getMaxScore() {
		return maxScore;
	}
	/**
	 * 设置 最高得分
	 * @param maxScore
	 * 最高得分
	 */
	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}
	
}