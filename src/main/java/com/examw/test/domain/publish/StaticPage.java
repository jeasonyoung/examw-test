package com.examw.test.domain.publish;

import java.io.Serializable;
import java.util.Date;

/**
 * 静态页面。
 * 
 * @author yangyong
 * @since 2014年12月24日
 */
public class StaticPage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,path,content;
	private PublishRecord publish;
	private Date createTime,lastTime;
	private Integer status;
	/**
	 * 获取页面ID。
	 * @return 页面ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置页面ID。
	 * @param id 
	 *	  页面ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取页面路径。
	 * @return 页面路径。
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置页面路径。
	 * @param path 
	 *	  页面路径。
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取页面内容。
	 * @return 页面内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置页面内容。
	 * @param content 
	 *	  页面内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取所属发布记录。
	 * @return 所属发布记录。
	 */
	public PublishRecord getPublish() {
		return publish;
	}
	/**
	 * 设置所属发布记录。
	 * @param publish 
	 *	  所属发布记录。
	 */
	public void setPublish(PublishRecord publish) {
		this.publish = publish;
	}
	/**
	 * 获取状态。
	 * @return 状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态。
	 * @param status 
	 *	  状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 获取修改时间。
	 * @return 修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置修改时间。
	 * @param lastTime 
	 *	  修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}