package com.examw.test.domain.publish;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.examw.service.Status;
/**
 *  发布记录。
 * 
 * @author yangyong
 * @since 2014年12月23日
 */
public class PublishRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Configuration configuration;
	private Integer status;
	private Date startTime,endTime;
	private Set<StaticPage> pages;
	/**
	 * 构造函数。
	 */
	public PublishRecord(){}
	/**
	 * 构造函数。
	 * @param configuration
	 */
	public PublishRecord(Configuration configuration, Status status){
		this.setId(UUID.randomUUID().toString());
		this.setConfiguration(configuration);
		this.setStartTime(new Date());
		if(status != null){
			this.setStatus(status.getValue());
		}
		this.setName(String.format("%1$s-%2$s", configuration.getName(), new SimpleDateFormat("yyyyMMddHHmmss").format(this.getStartTime())));
	}
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
	 * 获取所属发布配置。
	 * @return 所属发布配置。
	 */
	public Configuration getConfiguration() {
		return configuration;
	}
	/**
	 * 设置所属发布配置。
	 * @param configuration 
	 *	  所属发布配置。
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
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
	 * 获取发布开始时间。
	 * @return 发布开始时间。
	 */
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
	 * 获取发布开始时间。
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置发布开始时间。
	 * @param endTime 
	 *	  发布开始时间。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取静态页面集合。
	 * @return 静态页面集合。
	 */
	public Set<StaticPage> getPages() {
		return pages;
	}
	/**
	 * 设置静态页面集合。
	 * @param pages 
	 *	  静态页面集合。
	 */
	public void setPages(Set<StaticPage> pages) {
		this.pages = pages;
	}	
}