package com.examw.test.domain.products;

import java.io.Serializable;

/**
 *  软件类型限制。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class SoftwareTypeLimit implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private SoftwareType type;
	private Registration registration;
	private Integer time;
	/**
	 * 获取限制ID。
	 * @return 限制ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置限制ID。
	 * @param id 
	 *	  限制ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取软件类型。
	 * @return 软件类型。
	 */
	public SoftwareType getType() {
		return type;
	}
	/**
	 * 设置软件类型。
	 * @param type 
	 *	  软件类型。
	 */
	public void setType(SoftwareType type) {
		this.type = type;
	}
	/**
	 * 获取关联注册码。
	 * @return 关联注册码。
	 */
	public Registration getRegistration() {
		return registration;
	}
	/**
	 * 设置关联注册码。
	 * @param registration 
	 *	  关联注册码。
	 */
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	/**
	 * 获取绑定次数。
	 * @return 绑定次数。
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置绑定次数。
	 * @param time 
	 *	  绑定次数。
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
}