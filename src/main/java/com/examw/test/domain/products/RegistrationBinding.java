package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 注册码绑定。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class RegistrationBinding implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,machine;
	private Integer times;
	private Registration registration;
	private SoftwareType softwareType;
	private ProductUser user;
	private Date createTime,lastTime;
	/**
	 * 构造函数。
	 */
	public RegistrationBinding(){
		this.setId(UUID.randomUUID().toString());
		this.setTimes(1);
		this.setCreateTime(new Date());
		this.setLastTime(new Date());
	}
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
	 * 获取设备机器码。
	 * @return 设备机器码。
	 */
	public String getMachine() {
		return machine;
	}
	/**
	 * 设置设备机器码。
	 * @param machine 
	 *	  设备机器码。
	 */
	public void setMachine(String machine) {
		this.machine = machine;
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
	 * 获取所属产品用户。
	 * @return 所属产品用户。
	 */
	public ProductUser getUser() {
		return user;
	}
	/**
	 * 设置所属产品用户。
	 * @param user 
	 *	  所属产品用户。
	 */
	public void setUser(ProductUser user) {
		this.user = user;
	}
	/**
	 * 获取重复绑定次数。
	 * @return 重复绑定次数。
	 */
	public Integer getTimes() {
		return times;
	}
	/**
	 * 设置重复绑定次数。
	 * @param times 
	 *	  重复绑定次数。
	 */
	public void setTimes(Integer times) {
		this.times = times;
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