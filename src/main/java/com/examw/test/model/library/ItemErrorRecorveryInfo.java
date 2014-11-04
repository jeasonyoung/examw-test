package com.examw.test.model.library;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 试题纠错信息
 * @author fengwei.
 * @since 2014年11月4日 下午1:33:43.
 */
public class ItemErrorRecorveryInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,content,remarks,adminUserId,adminUserName;
	private String userId,userName;
	private String itemId,itemContent;
	private Integer status,itemType,errorType;
	private String statusName,itemTypeName,errorTypeName;
	private Integer terminalCode;
	private Date createTime,dealTime;
	private String subjectId,examId;
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
	 * 获取 错误描述内容
	 * @return content
	 * 错误描述内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 错误描述内容
	 * @param content
	 * 错误描述内容
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
	 * 设置 处理人ID
	 * @param adminUserId
	 * 处理人ID
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	/**
	 * 获取 处理人用户名
	 * @return adminUserName
	 * 处理人用户名
	 */
	public String getAdminUserName() {
		return adminUserName;
	}
	/**
	 * 设置 处理人用户名
	 * @param adminUserName
	 * 处理人用户名
	 */
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
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
	 * 获取  用户名
	 * @return userName
	 *  用户名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置 用户名
	 * @param userName
	 *  用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取 试题ID
	 * @return itemId
	 * 试题ID
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置 试题ID
	 * @param itemId
	 * 试题ID
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取 试题内容
	 * @return itemContent
	 * 试题内容
	 */
	public String getItemContent() {
		return itemContent;
	}
	/**
	 * 设置 试题内容
	 * @param itemContent
	 * 试题内容
	 */
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	/**
	 * 获取状态 
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
	 * 获取 试题类型
	 * @return itemType
	 * 试题类型
	 */
	public Integer getItemType() {
		return itemType;
	}
	/**
	 * 设置 试题类型
	 * @param itemType
	 * 试题类型
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
	 * 获取 状态名称
	 * @return statusName
	 * 状态名称
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 状态名称
	 * @param statusName
	 * 状态名称
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取 试题类型名称
	 * @return itemTypeName
	 * 试题类型名称
	 */
	public String getItemTypeName() {
		return itemTypeName;
	}
	/**
	 * 设置 试题类型名称
	 * @param itemTypeName
	 * 试题类型名称
	 */
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	/**
	 * 获取 错误类型名称
	 * @return errorTypeName
	 * 错误类型名称
	 */
	public String getErrorTypeName() {
		return errorTypeName;
	}
	/**
	 * 设置 错误类型名称
	 * @param errorTypeName
	 * 错误类型名称
	 */
	public void setErrorTypeName(String errorTypeName) {
		this.errorTypeName = errorTypeName;
	}
	/**
	 * 获取 终端代码
	 * @return terminalCode
	 * 终端代码
	 */
	public Integer getTerminalCode() {
		return terminalCode;
	}
	/**
	 * 设置 终端代码
	 * @param terminalCode
	 * 终端代码
	 */
	public void setTerminalCode(Integer terminalCode) {
		this.terminalCode = terminalCode;
	}
	/**
	 * 获取 提出时间
	 * @return createTime
	 * 提出时间
	 */
	@JsonSerialize(using=CustomDateSerializer.LongDate.class)
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
	@JsonSerialize(using=CustomDateSerializer.LongDate.class)
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
	/**
	 * 获取 科目ID
	 * @return subjectId
	 * 科目ID
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置 科目ID
	 * @param subjectId
	 * 科目ID
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取 考试ID
	 * @return examId
	 * 考试ID
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置 考试ID
	 * @param examId
	 * 考试ID
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
}
