package com.examw.test.domain.records;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author fengwei.
 * @since 2014年9月17日 下午3:58:58.
 */
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,structureItemId,userId;
	private Date createTime;
	private String content;
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
	 * 设置 创建时间
	 * @param createTime
	 * 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 笔记内容
	 * @return content
	 * 笔记内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 笔记内容
	 * @param content
	 * 笔记内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
