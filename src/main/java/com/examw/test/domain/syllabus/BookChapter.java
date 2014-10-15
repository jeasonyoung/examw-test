package com.examw.test.domain.syllabus;

import java.io.Serializable;
import java.util.Set;

/**
 * 教材章节。
 * 
 * @author yangyong
 * @since 2014年10月13日
 */
public class BookChapter implements Serializable,Comparable<BookChapter> {
	private static final long serialVersionUID = 1L;
	private String id,title,content;
	private Book book;
	private Set<ChapterKnowledge> knowledges;
	private BookChapter parent;
	private Set<BookChapter> children;
	private Integer orderNo;
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
	 * 获取所属教材。
	 * @return 所属教材。
	 */
	public Book getBook() {
		return book;
	}
	/**
	 * 设置所属教材。
	 * @param book 
	 *	  所属教材。
	 */
	public void setBook(Book book) {
		this.book = book;
	}
	/**
	 * 获取知识点集合。
	 * @return 知识点集合。
	 */
	public Set<ChapterKnowledge> getKnowledges() {
		return knowledges;
	}
	/**
	 * 设置知识点集合。
	 * @param knowledges 
	 *	  知识点集合。
	 */
	public void setKnowledges(Set<ChapterKnowledge> knowledges) {
		this.knowledges = knowledges;
	}
	/**
	 * 获取父章节。
	 * @return 父章节。
	 */
	public BookChapter getParent() {
		return parent;
	}
	/**
	 * 设置父章节。
	 * @param parent 
	 *	  父章节。
	 */
	public void setParent(BookChapter parent) {
		this.parent = parent;
	}
	/**
	 * 获取子章节集合。
	 * @return 子章节集合。
	 */
	public Set<BookChapter> getChildren() {
		return children;
	}
	/**
	 * 设置子章节集合。
	 * @param children 
	 *	  子章节集合。
	 */
	public void setChildren(Set<BookChapter> children) {
		this.children = children;
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
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(BookChapter o) {
		int index  = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getTitle().compareToIgnoreCase(o.getTitle());
			if(index == 0)
			{
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}