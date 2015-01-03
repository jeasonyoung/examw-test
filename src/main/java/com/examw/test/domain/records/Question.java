package com.examw.test.domain.records;

import java.io.Serializable;
import java.util.Date;
/**
 * 常见问题。
 * 
 * @author yangyong
 * @since 2015年1月3日
 */
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title,content;
	private Date createTime;
	/**
	 * 获取常见问题ID。
	 * @return 常见问题ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置常见问题ID。
	 * @param id 
	 *	  常见问题ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取常见问题标题。
	 * @return 常见问题标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置常见问题标题。
	 * @param title 
	 *	  常见问题标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取常见问题内容。
	 * @return 常见问题内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置常见问题内容。
	 * @param content 
	 *	  常见问题内容。
	 */
	public void setContent(String content) {
		this.content = content;
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
}