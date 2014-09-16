package com.examw.test.model.products;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 注册码绑定记录信息
 * @author fengwei.
 * @since 2014年8月12日 下午2:52:45.
 */
public class RegistrationBindRecordInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,machineCode,detail;
	private String registrationId,registrationCode;
	private String softwareTypeId,softwareTypeName;
	private Date createTime;
	/**
	 * 获取绑定ID。
	 * @return 绑定ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置绑定ID。
	 * @param id 
	 *	  绑定ID。
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
	 * 获取 注册码ID
	 * @return registrationId
	 * 注册码ID
	 */
	public String getRegistrationId() {
		return registrationId;
	}
	/**
	 * 设置 注册码ID
	 * @param registrationId
	 * 注册码ID
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	/**
	 * 获取 注册码
	 * @return registrationCode
	 * 注册码
	 */
	public String getRegistrationCode() {
		return registrationCode;
	}
	/**
	 * 设置 注册码
	 * @param registrationCode
	 * 注册码
	 */
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	/**
	 * 获取 软件类型ID
	 * @return softwareTypeId
	 * 软件类型ID
	 */
	public String getSoftwareTypeId() {
		return softwareTypeId;
	}
	/**
	 * 设置 软件类型ID
	 * @param softwareTypeId
	 * 软件类型ID
	 */
	public void setSoftwareTypeId(String softwareTypeId) {
		this.softwareTypeId = softwareTypeId;
	}
	/**
	 * 获取 软件类型名称
	 * @return softwareTypeName
	 * 软件类型名称
	 */
	public String getSoftwareTypeName() {
		return softwareTypeName;
	}
	/**
	 * 设置 软件类型名称
	 * @param softwareTypeName
	 * 软件类型名称
	 */
	public void setSoftwareTypeName(String softwareTypeName) {
		this.softwareTypeName = softwareTypeName;
	}
	/**
	 * 获取绑定时间。
	 * @return 绑定时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置绑定时间。
	 * @param createTime 
	 *	 绑定时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
