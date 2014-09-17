package com.examw.test.domain.records;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户题目收藏
 * @author fengwei.
 * @since 2014年9月17日 下午3:58:23.
 */
public class Collection implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,structureItemId,userId;
	private Date createTime;
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
}
