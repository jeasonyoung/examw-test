package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Date;

/**
 *  软件类型限制。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class SoftwareTypeLimit implements Serializable {
	private static final long serialVersionUID = 1L;
	private SoftwareType softwareType;
	private Registration register;
	private Integer times;
	private Date createTime;
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
	 * 获取关联注册码。
	 * @return 关联注册码。
	 */
	public Registration getRegister() {
		return register;
	}
	/**
	 * 设置关联注册码。
	 * @param registration 
	 *	  关联注册码。
	 */
	public void setRegister(Registration register) {
		this.register = register;
	}
	/**
	 * 获取绑定次数。
	 * @return 绑定次数。
	 */
	public Integer getTimes() {
		return times;
	}
	/**
	 * 设置绑定次数。
	 * @param times 
	 *	  绑定次数。
	 */
	public void setTimes(Integer times) {
		this.times = times;
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
}