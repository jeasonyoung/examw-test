package com.examw.test.domain.records;

import java.io.Serializable;
import java.util.Date;

import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.products.SoftwareType;

/**
 * 用户纠错
 * @author fengwei.
 * @since 2014年11月3日 下午5:01:18.
 */
public class UserItemErrorRecovery implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,itemId,content,remarks,adminUserId,adminUserName;
	private ProductUser user;
	private Integer status,itemType,errorType;
	private SoftwareType terminal;
	private Date createTime,dealTime;
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
	 * 获取 错误内容描述
	 * @return content
	 * 错误内容描述
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 错误内容描述
	 * @param content
	 * 错误内容描述
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 处理备注
	 * @return remarks
	 * 处理备注
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置 处理备注
	 * @param remarks
	 * 处理备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取 处理人ID
	 * @return adminUserId
	 * 处理人ID
	 */
	public String getAdminUserId() {
		return adminUserId;
	}
	/**
	 * 设置  处理人ID
	 * @param adminUserId
	 *  处理人ID
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	/**
	 * 获取  处理人用户名
	 * @return adminUserName
	 *  处理人用户名
	 */
	public String getAdminUserName() {
		return adminUserName;
	}
	/**
	 * 设置 处理人用户名
	 * @param adminUserName
	 *  处理人用户名
	 */
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	/**
	 * 获取 提出用户
	 * @return user
	 * 提出用户
	 */
	public ProductUser getUser() {
		return user;
	}
	/**
	 * 设置 提出用户
	 * @param user
	 * 提出用户
	 */
	public void setUser(ProductUser user) {
		this.user = user;
	}
	/**
	 * 获取 状态 [未处理,已处理]
	 * @return status
	 * 状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置 状态
	 * @param status
	 * 状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取 题型
	 * @return itemType
	 * 题型
	 */
	public Integer getItemType() {
		return itemType;
	}
	/**
	 * 设置 题型
	 * @param itemType
	 * 题型
	 */
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	/**
	 * 获取 错误类型
	 * @return errorType
	 * 错误类型
	 */
	public Integer getErrorType() {
		return errorType;
	}
	/**
	 * 设置 错误类型
	 * @param errorType
	 * 错误类型
	 */
	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}
	/**
	 * 获取 终端
	 * @return terminal
	 * 终端
	 */
	public SoftwareType getTerminal() {
		return terminal;
	}
	/**
	 * 设置 终端
	 * @param terminal
	 * 终端
	 */
	public void setTerminal(SoftwareType terminal) {
		this.terminal = terminal;
	}
	/**
	 * 获取 提出时间
	 * @return createTime
	 * 提出时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 提出时间
	 * @param createTime
	 * 提出时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 处理时间
	 * @return dealTime
	 * 处理时间
	 */
	public Date getDealTime() {
		return dealTime;
	}
	/**
	 * 设置 处理时间
	 * @param dealTime
	 * 处理时间
	 */
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	
}
