package com.examw.test.domain.records;

import java.io.Serializable;
import java.util.Date;

import com.examw.test.domain.products.SoftwareType;

/**
 * 意见反馈
 * @author fengwei.
 * @since 2015年1月12日 下午4:41:04.
 */
public class FeedBack implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,content,username;
	private Date createTime;
	private SoftwareType terminal;
	/**
	 * 获取 ID
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 反馈内容
	 * @return content
	 * 
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 反馈内容
	 * @param content
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 用户名
	 * @return username
	 * 
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置 用户名
	 * @param username
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取 创建时间
	 * @return createTime
	 * 
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param createTime
	 * 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 终端类型
	 * @return terminal
	 * 
	 */
	public SoftwareType getTerminal() {
		return terminal;
	}
	/**
	 * 设置 终端类型
	 * @param terminal
	 * 
	 */
	public void setTerminal(SoftwareType terminal) {
		this.terminal = terminal;
	}
	
}
