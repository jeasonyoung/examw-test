package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品注册绑定。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class RegistrationBindRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,machineCode,detail;
	private Registration registration;
	private SoftwareType softwareType;
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
	 * 获取所属注册码。
	 * @return 所属注册码。
	 */
	public Registration getRegistration() {
		return registration;
	}
	/**
	 * 设置所属注册码。
	 * @param registration 
	 *	  所属注册码。
	 */
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	/**
	 * 获取软件类型。
	 * @return 软件类型。
	 */
	public SoftwareType getSoftwareType() {
		return softwareType;
	}
	/**
	 * 设置软件类型。
	 * @param softwareType 
	 *	  软件类型。
	 */
	public void setSoftwareType(SoftwareType softwareType) {
		this.softwareType = softwareType;
	}
	/**
	 * 获取绑定时间。
	 * @return 绑定时间。
	 */
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