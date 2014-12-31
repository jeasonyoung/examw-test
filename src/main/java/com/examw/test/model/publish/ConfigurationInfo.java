package com.examw.test.model.publish;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 发布配置信息。
 * 
 * @author yangyong
 * @since 2014年12月23日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfigurationInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,statusName,templateName;
	private Integer status;
	private Integer [] templates;
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
	 * 获取发布状态名称。
	 * @return 发布状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置发布状态名称。
	 * @param statusName 
	 *	  发布状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取模版值。
	 * @return 模版值。
	 */
	public Integer[] getTemplates() {
		return templates;
	}
	/**
	 * 设置模版值。
	 * @param template 
	 *	  模版值。
	 */
	public void setTemplates(Integer[] templates) {
		this.templates = templates;
	}
	/**
	 * 获取模版名称。
	 * @return 模版名称。
	 */
	public String getTemplateName() {
		return templateName;
	}
	/**
	 * 设置模版名称。
	 * @param templateName 
	 *	  模版名称。
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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