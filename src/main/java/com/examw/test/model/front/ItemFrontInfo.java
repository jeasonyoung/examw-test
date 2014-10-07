package com.examw.test.model.front;

import java.io.Serializable;


/**
 * 试题前台数据信息
 * @author fengwei.
 * @since 2014年9月24日 上午11:55:52.
 */
public class ItemFrontInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Boolean isCollected;	//是否被收藏
	private Integer totalNoteNum;	//笔记总数
	private Integer userNoteNum;	//用户笔记数
	/**
	 * 获取 是否被收藏
	 * @return isCollected
	 * 是否被收藏
	 */
	public Boolean getIsCollected() {
		return isCollected;
	}
	/**
	 * 设置 是否被收藏
	 * @param isCollected
	 * 是否被收藏
	 */
	public void setIsCollected(Boolean isCollected) {
		this.isCollected = isCollected;
	}
	/**
	 * 获取 笔记总数
	 * @return totalNoteNum
	 * 笔记总数
	 */
	public Integer getTotalNoteNum() {
		return totalNoteNum;
	}
	/**
	 * 设置 笔记总数
	 * @param totalNoteNum
	 * 笔记总数
	 */
	public void setTotalNoteNum(Integer totalNoteNum) {
		this.totalNoteNum = totalNoteNum;
	}
	/**
	 * 获取 用户笔记总数
	 * @return userNoteNum
	 * 用户笔记总数
	 */
	public Integer getUserNoteNum() {
		return userNoteNum;
	}
	/**
	 * 设置 用户笔记总数
	 * @param userNoteNum
	 * 用户笔记总数
	 */
	public void setUserNoteNum(Integer userNoteNum) {
		this.userNoteNum = userNoteNum;
	}
}	
