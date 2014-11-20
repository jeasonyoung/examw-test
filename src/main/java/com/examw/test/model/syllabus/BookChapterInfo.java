package com.examw.test.model.syllabus;

import java.io.Serializable;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 教材章节信息。
 * 
 * @author yangyong
 * @since 2014年10月13日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class BookChapterInfo implements Serializable,Comparable<BookChapterInfo> {
	private static final long serialVersionUID = 1L;
	private String pid,id,title,content,bookId,bookName,syllabusId;
	private Integer orderNo;
	private Set<BookChapterInfo> children;
	/**
	 * 获取父章节ID。
	 * @return 父章节ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置父章节ID。
	 * @param pid 
	 *	  父章节ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取章节ID。
	 * @return 章节ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置章节ID。
	 * @param id 
	 *	  章节ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取章节标题。
	 * @return 章节标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置章节标题。
	 * @param title 
	 *	  章节标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取章节内容。
	 * @return 章节内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置章节内容。
	 * @param content 
	 *	  章节内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取所属教材ID。
	 * @return 所属教材ID。
	 */
	public String getBookId() {
		return bookId;
	}
	/**
	 * 设置所属教材ID。
	 * @param bookId 
	 *	  所属教材ID。
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	/**
	 * 获取所属教材名称。
	 * @return 所属教材名称。
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * 设置所属教材名称。
	 * @param bookName 
	 *	  所属教材名称。
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
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
	 * 获取子章节集合。
	 * @return 子章节集合。
	 */
	public Set<BookChapterInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子章节集合。
	 * @param children 
	 *	  子章节集合。
	 */
	public void setChildren(Set<BookChapterInfo> children) {
		this.children = children;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(BookChapterInfo o) {
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
	/**
	 * 获取 大纲ID
	 * @return syllabusId
	 * 大纲ID
	 */
	public String getSyllabusId() {
		return syllabusId;
	}
	/**
	 * 设置 大纲ID
	 * @param syllabusId
	 * 大纲ID
	 */
	public void setSyllabusId(String syllabusId) {
		this.syllabusId = syllabusId;
	}
}