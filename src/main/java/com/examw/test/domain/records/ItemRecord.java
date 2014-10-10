package com.examw.test.domain.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 试题做题的记录
 * @author fengwei.
 * @since 2014年9月17日 下午3:35:58.
 */
public class ItemRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,paperId,itemId,userId,productId;
	private Date lastTime;
	private BigDecimal score;
	private Integer time,terminal,status;
	private String answer,ip;
	
	public static final int STATUS_NULL = 0; //未答
	public static final int STATUS_WRONG = -1; //答错
	public static final int STATUS_RIGHT = 1; //答对
	public static final int STATUS_LESS = 2; //少选
	/**
	 * 获取 ID
	 * @return id
	 * ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 试卷ID
	 * @return paperId
	 * 试卷ID
	 */
	public String getPaperId() {
		return paperId;
	}
	/**
	 * 设置 试卷ID
	 * @param paperId
	 * 试卷ID
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	/**
	 * 获取 结构题目ID
	 * @return structureItemId
	 * 结构题目ID
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置 结构题目ID
	 * @param structureItemId
	 * 结构题目ID
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取 用户ID
	 * @return userId
	 * 用户ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 用户ID
	 * @param userId
	 * 用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 上一次记录时间
	 * @return lastTime
	 * 上一次记录时间
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置 上一次记录时间
	 * @param lastTime
	 * 上一次记录时间
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取 得分
	 * @return score
	 * 得分
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置 得分
	 * @param score
	 * 得分
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取 答题时间
	 * @return time
	 * 答题时间
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置 答题时间
	 * @param time
	 * 答题时间
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取 终端
	 * @return terminal
	 * 终端
	 */
	public Integer getTerminal() {
		return terminal;
	}
	/**
	 * 设置 终端
	 * @param terminal
	 * 终端
	 */
	public void setTerminal(Integer terminal) {
		this.terminal = terminal;
	}
	/**
	 * 获取	用户答案 
	 * @return answer
	 * 用户答案 
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置 用户答案 
	 * @param answer
	 * 用户答案 
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取 IP
	 * @return ip
	 * IP
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置 IP
	 * @param ip
	 * IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取 回答状态
	 * @return status
	 * 
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置 回答状态
	 * @param status
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取 产品ID
	 * @return productId
	 * 
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置 产品ID
	 * @param productId
	 * 
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
