package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Date;
/**
 * 注册码日志。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class RegistrationLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,machineCode,detail,ip;
	private Registration registration;
	private Software software;
	private Date createTime;
	private Long time;
	private Integer type;
	/**
	 * 类型－绑定注册码。
	 */
	public static final  int TYPE_BIND = 1;
	/**
	 * 类型－更新数据。
	 */
	public static final  int TYPE_UPDATE_DATA = 2;
	/**
	 * 类型－软件更新。
	 */
	public static final  int TYPE_SOFT_UPDATE = 3;
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
	 * 获取所属软件。
	 * @return 所属软件。
	 */
	public Software getSoftware() {
		return software;
	}
	/**
	 * 设置所属软件。
	 * @param software 
	 *	  所属软件。
	 */
	public void setSoftware(Software software) {
		this.software = software;
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
}