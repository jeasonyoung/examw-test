package com.examw.test.model.products;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 
 * @author fengwei.
 * @since 2014年8月12日 下午2:52:26.
 */
public class RegistrationLogInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,machineCode,detail,ip;
	private String registrationId,registrationCode;
	private String softwareId,softwareName;
	private Date createTime;
	private Long time;
	private Integer type;
	private String typeName;
	/**
	 * 获取日志ID。
	 * @return 日志ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置日志ID。
	 * @param id 
	 *	  日志ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取机器码。
	 * @return 机器码。
	 */
	public String getMachineCode() {
		return machineCode;
	}
	/**
	 * 设置机器码。
	 * @param machineCode 
	 *	  机器码。
	 */
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	/**
	 * 获取机器信息。
	 * @return 机器信息。
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * 设置机器信息。
	 * @param detail 
	 *	  机器信息。
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * 获取IP地址。
	 * @return IP地址。
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置IP地址。
	 * @param ip 
	 *	  IP地址。
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * 获取 注册码ID
	 * @return registrationId
	 * 
	 */
	public String getRegistrationId() {
		return registrationId;
	}
	/**
	 * 设置 注册码ID
	 * @param registrationId
	 * 
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	/**
	 * 获取 注册码
	 * @return registrationCode
	 * 
	 */
	public String getRegistrationCode() {
		return registrationCode;
	}
	/**
	 * 设置 注册码
	 * @param registrationCode
	 * 
	 */
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	/**
	 * 获取 软件ID
	 * @return softwareId
	 * 
	 */
	public String getSoftwareId() {
		return softwareId;
	}
	/**
	 * 设置 软件ID
	 * @param softwareId
	 * 
	 */
	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}
	/**
	 * 获取 软件名称
	 * @return softwareName
	 * 
	 */
	public String getSoftwareName() {
		return softwareName;
	}
	/**
	 * 设置 软件名称
	 * @param softwareName
	 * 
	 */
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
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
	 * 获取同步次数。
	 * @return 同步次数。
	 */
	public Long getTime() {
		return time;
	}
	/**
	 * 设置同步次数。
	 * @param time 
	 *	 同步次数。
	 */
	public void setTime(Long time) {
		this.time = time;
	}
	/**
	 * 获取日志类型。
	 * @return 日志类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置日志类型。
	 * @param type 
	 *	 日志类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取 类型名称
	 * @return typeName
	 * 
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置 类型名称
	 * @param typeName
	 * 
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}	
