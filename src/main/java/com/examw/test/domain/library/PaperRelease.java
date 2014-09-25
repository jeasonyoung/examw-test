package com.examw.test.domain.library;

import java.io.Serializable;
import java.util.Date;

/**
 * 试卷发布。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public class PaperRelease implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title,content;
	private Paper paper;
	private Date createTime;
	private Integer total;
	/**
	 * 获取试卷发布ID。
	 * @return 试卷发布ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试卷发布ID。
	 * @param id 
	 *	  试卷发布ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试卷名称。
	 * @return 试卷名称。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置试卷名称。
	 * @param title 
	 *	  试卷名称。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取试卷试题数。
	 * @return 试卷试题数。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置试卷试题数。
	 * @param total 
	 *	  试卷试题数。
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * 获取试卷内容（JSON格式）。
	 * @return 试卷内容（JSON格式）。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置试卷内容（JSON格式）。
	 * @param content 
	 *	  试卷内容（JSON格式）。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取所属试卷。
	 * @return 所属试卷。
	 */
	public Paper getPaper() {
		return paper;
	}
	/**
	 * 设置所属试卷。
	 * @param paper 
	 *	  所属试卷。
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * 获取发布时间。
	 * @return 发布时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置发布时间。
	 * @param createTime 
	 *	  发布时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}