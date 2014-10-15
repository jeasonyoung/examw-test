package com.examw.test.model.syllabus;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 章节知识点信息。
 * @author lq.
 * @since 2014-08-07.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ChapterKnowledgeInfo extends Paging implements Comparable<ChapterKnowledgeInfo> {
	private static final long serialVersionUID = 1L;
	private String id,title,description,chapterId,chapterName,topSyllabusId,syllabusId,syllabusName,lastUserId,lastUserName;
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
	 * 获取所属章节ID。
	 * @return 所属章节ID。
	 */
	public String getChapterId() {
		return chapterId;
	}
	/**
	 * 设置所属章节ID。
	 * @param chapterId 
	 *	  所属章节ID。
	 */
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}
	/**
	 * 获取所属章节名称。
	 * @return 所属章节名称。
	 */
	public String getChapterName() {
		return chapterName;
	}
	/**
	 * 设置所属章节名称。
	 * @param chapterName 
	 *	  所属章节名称。
	 */
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	/**
	 * 获取所属考试大纲ID。
	 * @return 所属考试大纲ID。
	 */
	public String getTopSyllabusId() {
		return topSyllabusId;
	}
	/**
	 * 设置所属考试大纲ID。
	 * @param topSyllabusId 
	 *	  所属考试大纲ID。
	 */
	public void setTopSyllabusId(String topSyllabusId) {
		this.topSyllabusId = topSyllabusId;
	}
	/**
	 * 获取大纲要点ID。
	 * @return 大纲要点ID。
	 */
	public String getSyllabusId() {
		return syllabusId;
	}
	/**
	 * 设置大纲要点ID。
	 * @param syllabusId 
	 *	  大纲要点ID。
	 */
	public void setSyllabusId(String syllabusId) {
		this.syllabusId = syllabusId;
	}
	/**
	 * 获取大纲要点名称。
	 * @return 大纲要点名称。
	 */
	public String getSyllabusName() {
		return syllabusName;
	}
	/**
	 * 设置大纲要点名称。
	 * @param syllabusName 
	 *	  大纲要点名称。
	 */
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}
	/**
	 * 获取最后修改用户ID。
	 * @return 最后修改用户ID。
	 */
	public String getLastUserId() {
		return lastUserId;
	}
	/**
	 * 设置最后修改用户ID。
	 * @param lastUserId 
	 *	  最后修改用户ID。
	 */
	public void setLastUserId(String lastUserId) {
		this.lastUserId = lastUserId;
	}
	/**
	 * 获取最后修改用户名。
	 * @return 最后修改用户名。
	 */
	public String getLastUserName() {
		return lastUserName;
	}
	/**
	 * 设置最后修改用户名。
	 * @param lastUserName 
	 *	  最后修改用户名。
	 */
	public void setLastUserName(String lastUserName) {
		this.lastUserName = lastUserName;
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
	public int compareTo(ChapterKnowledgeInfo o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getTitle().compareToIgnoreCase(o.getTitle());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}