package com.examw.test.model.publish;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 发布记录信息。
 * 
 * @author yangyong
 * @since 2014年12月24日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PublishRecordInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,statusName,configurationId,configurationName;
	private Integer status;
	private Date startTime,endTime;
	/**
	 * 获取发布ID。
	 * @return 发布ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置发布ID。
	 * @param id 
	 *	  发布ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取发布名称。
	 * @return 发布名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置发布名称。
	 * @param name 
	 *	  发布名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取所属发布配置ID。
	 * @return 所属发布配置ID。
	 */
	public String getConfigurationId() {
		return configurationId;
	}
	/**
	 * 设置所属发布配置ID。
	 * @param configurationId 
	 *	  所属发布配置ID。
	 */
	public void setConfigurationId(String configurationId) {
		this.configurationId = configurationId;
	}
	/**
	 * 获取所属发布配置名称。
	 * @return 所属发布配置名称。
	 */
	public String getConfigurationName() {
		return configurationName;
	}
	/**
	 * 设置所属发布配置名称。
	 * @param configurationName 
	 *	  所属发布配置名称。
	 */
	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
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
	 * 获取发布开始时间。
	 * @return 发布开始时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置发布开始时间。
	 * @param startTime 
	 *	  发布开始时间。
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取发布结束时间。
	 * @return 发布结束时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置发布结束时间。
	 * @param endTime 
	 *	  发布结束时间。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}