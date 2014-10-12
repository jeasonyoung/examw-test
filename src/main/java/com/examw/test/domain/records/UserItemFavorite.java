package com.examw.test.domain.records;

import java.io.Serializable;
import java.util.Date;

import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.settings.Subject;

/**
 * 用户试题收藏
 * @author fengwei.
 * @since 2014年9月17日 下午3:58:23.
 */
public class UserItemFavorite implements Serializable,Comparable<UserItemFavorite> {
	private static final long serialVersionUID = 1L;
	private String id,itemId,itemContent,remarks;
	private ProductUser user;
	private Subject subject;
	private Integer itemType;
	private SoftwareType terminal;
	private Date createTime;
	/**
	 * 获取收藏ID。
	 * @return 收藏ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置收藏ID。
	 * @param id 
	 *	  收藏ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试题ID。
	 * @return 试题ID。
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置试题ID。
	 * @param itemId 
	 *	  试题ID。
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取试题内容JSON。
	 * @return 试题内容JSON。
	 */
	public String getItemContent() {
		return itemContent;
	}
	/**
	 * 设置试题内容JSON。
	 * @param itemContent 
	 *	  试题内容JSON。
	 */
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	/**
	 * 获取备注信息。
	 * @return 备注信息。
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置备注信息。
	 * @param remarks 
	 *	  备注信息。
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取所属用户。
	 * @return 所属用户。
	 */
	public ProductUser getUser() {
		return user;
	}
	/**
	 * 设置所属用户。
	 * @param user 
	 *	  所属用户。
	 */
	public void setUser(ProductUser user) {
		this.user = user;
	}
	/**
	 * 获取所属科目。
	 * @return 所属科目。
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置所属科目。
	 * @param subject 
	 *	  所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取试题题型。
	 * @return 试题题型。
	 */
	public Integer getItemType() {
		return itemType;
	}
	/**
	 * 设置试题题型。
	 * @param itemType 
	 *	  试题题型。
	 */
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	/**
	 * 获取用户终端。
	 * @return 用户终端。
	 */
	public SoftwareType getTerminal() {
		return terminal;
	}
	/**
	 * 设置用户终端。
	 * @param terminal 
	 *	  用户终端。
	 */
	public void setTerminal(SoftwareType terminal) {
		this.terminal = terminal;
	}
	/**
	 * 获取收藏时间。
	 * @return 收藏时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置收藏时间。
	 * @param createTime 
	 *	  收藏时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(UserItemFavorite o) {
		if(this == o) return 0;
		int index = 0;
		if(this.getCreateTime() != null && o.getCreateTime() != null){
			index = (int)(this.getCreateTime().getTime() - o.getCreateTime().getTime());
		}
		if(index == 0 && (this.getUser() != null && o.getUser() != null)){
			 index = this.getUser().getId().compareToIgnoreCase(o.getUser().getId());
		}
		if(index == 0){
			index = this.getItemId().compareToIgnoreCase(o.getItemId());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}