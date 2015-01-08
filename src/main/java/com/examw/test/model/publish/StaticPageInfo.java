package com.examw.test.model.publish;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 页面静态化存储信息。
 * 
 * @author yangyong
 * @since 2014年12月25日
 */
public class StaticPageInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,path,content,publishId,publishName;
	private Date createTime,lastTime;
	/**
	 * 获取页面ID。
	 * @return 获取页面ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置获取页面ID。
	 * @param id 
	 *	  获取页面ID。
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
	 * 获取所属发布ID。
	 * @return 所属发布ID。
	 */
	public String getPublishId() {
		return publishId;
	}
	/**
	 * 设置所属发布ID。
	 * @param publishId 
	 *	  所属发布ID。
	 */
	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}
	/**
	 * 获取所属发布名称。
	 * @return 所属发布名称。
	 */
	public String getPublishName() {
		return publishName;
	}
	/**
	 * 设置所属发布名称。
	 * @param publishName 
	 *	  所属发布名称。
	 */
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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
}