package com.examw.test.domain.syllabus;

import java.io.Serializable;
import java.util.Date;
/**
 * 章节下知识点。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class ChapterKnowledge implements Serializable,Comparable<ChapterKnowledge> {
	private static final long serialVersionUID = 1L;
	private String id,title,description,lastUserId,lastUserName;
	private BookChapter chapter;
	private Syllabus syllabus;
	private Integer orderNo;
	private Date createTime,lastTime;
	/**
	 * 获取知识点ID。
	 * @return 知识点ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置知识点ID。
	 * @param id 
	 *	  知识点ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取知识点标题。
	 * @return 知识点标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置知识点标题。
	 * @param title 
	 *	  知识点标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取知识点描述。
	 * @return 知识点描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置知识点描述。
	 * @param description 
	 *	  知识点描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取最后操作用户ID。
	 * @return lastUserId
	 */
	public String getLastUserId() {
		return lastUserId;
	}
	/**
	 * 设置最后操作用户ID。
	 * @param lastUserId 
	 *	  最后操作用户ID。
	 */
	public void setLastUserId(String lastUserId) {
		this.lastUserId = lastUserId;
	}
	/**
	 * 获取最后操作用户名。
	 * @return 最后操作用户名。
	 */
	public String getLastUserName() {
		return lastUserName;
	}
	/**
	 * 设置最后操作用户名。
	 * @param lastUserName 
	 *	  最后操作用户名。
	 */
	public void setLastUserName(String lastUserName) {
		this.lastUserName = lastUserName;
	}
	/**
	 * 获取所属章节。
	 * @return 所属章节。
	 */
	public BookChapter getChapter() {
		return chapter;
	}
	/**
	 * 设置所属章节。
	 * @param chapter 
	 *	  所属章节。
	 */
	public void setChapter(BookChapter chapter) {
		this.chapter = chapter;
	}
	/**
	 * 获取所属大纲要点。
	 * @return 所属大纲要点。
	 */
	public Syllabus getSyllabus() {
		return syllabus;
	}
	/**
	 * 设置所属大纲要点。
	 * @param syllabus 
	 *	  所属大纲要点。
	 */
	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime 
	 *	  创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime 
	 *	  最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ChapterKnowledge o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			if(this.getChapter() != null && o.getChapter() != null){
				index = this.getChapter().getOrderNo() - o.getChapter().getOrderNo();
			}
			if(index == 0){
				index = this.getTitle().compareToIgnoreCase(o.getTitle());
				if(index == 0){
					index = this.getId().compareToIgnoreCase(o.getId());
				}
			}
		}
		return index;
	}
}