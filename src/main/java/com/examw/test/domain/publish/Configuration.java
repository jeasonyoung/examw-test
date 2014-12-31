package com.examw.test.domain.publish;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 发布配置。
 * 
 * @author yangyong
 * @since 2014年12月23日
 */
public class Configuration implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Set<PublishRecord> records;
	private Integer status,template;
	private Date createTime,lastTime;
	/**
	 * 获取发布配置ID。
	 * @return 发布配置ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置发布配置ID。
	 * @param id 
	 *	  发布配置ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取发布配置名称。
	 * @return 发布配置名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置发布配置名称。
	 * @param name 
	 *	  发布配置名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取关联的发布记录集合。
	 * @return 关联的发布记录集合。
	 */
	public Set<PublishRecord> getRecords() {
		return records;
	}
	/**
	 * 设置关联的发布记录集合。
	 * @param records 
	 *	  关联的发布记录集合。
	 */
	public void setRecords(Set<PublishRecord> records) {
		this.records = records;
	}
	/**
	 * 获取模版值。
	 * @return 模版值。
	 */
	public Integer getTemplate() {
		return template;
	}
	/**
	 * 设置模版值。
	 * @param template 
	 *	  模版值。
	 */
	public void setTemplate(Integer template) {
		this.template = template;
	}
	/**
	 * 获取发布状态。
	 * @return 发布状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置发布状态。
	 * @param status 
	 *	  发布状态。
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
}