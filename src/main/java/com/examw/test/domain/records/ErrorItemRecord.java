package com.examw.test.domain.records;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户错题记录
 * @author fengwei.
 * @since 2014年9月17日 下午3:46:05.
 */
public class ErrorItemRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,structureItemId,userId,itemId,productId;
	private Date createTime;
	private Integer errorTimes;
	private String historyAnswers;
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
	 * 获取 结构题目ID
	 * @return structureItemId
	 * 结构题目ID
	 */
	public String getStructureItemId() {
		return structureItemId;
	}
	/**
	 * 设置 结构题目ID
	 * @param structureItemId
	 * 结构题目ID
	 */
	public void setStructureItemId(String structureItemId) {
		this.structureItemId = structureItemId;
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
	 * 获取 创建时间
	 * @return createTime
	 * 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 获取 题目ID
	 * @return itemId
	 * 题目ID
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置 题目ID
	 * @param itemId
	 * 题目ID
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 设置 创建时间
	 * @param createTime
	 * 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 错误次数
	 * @return errorTimes
	 *  错误次数
	 */
	public Integer getErrorTimes() {
		return errorTimes;
	}
	/**
	 * 设置  错误次数
	 * @param errorTimes
	 *  错误次数
	 */
	public void setErrorTimes(Integer errorTimes) {
		this.errorTimes = errorTimes;
	}
	/**
	 * 获取 历史答案
	 * @return historyAnswers
	 * 历史答案
	 */
	public String getHistoryAnswers() {
		return historyAnswers;
	}
	/**
	 * 设置 历史答案
	 * @param historyAnswers
	 * 历史答案
	 */
	public void setHistoryAnswers(String historyAnswers) {
		this.historyAnswers = historyAnswers;
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
