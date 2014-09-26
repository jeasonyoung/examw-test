package com.examw.test.domain.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 试卷考试记录
 * @author fengwei.
 * @since 2014年9月17日 下午3:06:11.
 */
public class PaperRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,paperId,userId,productId;
	private Date lastTime;
	private Integer status,time,usedTime;
	private BigDecimal score;
	public static final int STATUS_DONE = 1;	//没有完成
	public static final int STATUS_UNDONE = 0;	//已经完成
	/**
	 * 获取 ID
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 试卷ID
	 * @return paperId
	 * 
	 */
	public String getPaperId() {
		return paperId;
	}
	/**
	 * 设置 试卷ID
	 * @param paperId
	 * 
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	/**
	 * 获取 用户ID
	 * @return userId
	 * 
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 用户ID
	 * @param userId
	 * 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 上次考试时间
	 * @return lastTime
	 * 
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置 上次考试时间
	 * @param lastTime
	 * 
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取 上次得分
	 * @return score
	 * 
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置 上次得分
	 * @param score
	 * 
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取 状态
	 * @return status
	 * 
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置 状态
	 * @param status
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取 考试使用时间
	 * @return time
	 * 
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置 考试使用时间
	 * @param time
	 * 
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取 已经使用时间
	 * @return usedTime
	 * 
	 */
	public Integer getUsedTime() {
		return usedTime;
	}
	/**
	 * 设置 已经使用时间
	 * @param usedTime
	 * 
	 */
	public void setUsedTime(Integer usedTime) {
		this.usedTime = usedTime;
	}
	/**
	 * 获取 产品ID
	 * @return productId
	 * 产品ID
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置 产品ID
	 * @param productId
	 * 产品ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
