package com.examw.test.model.products;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 注册码限制。
 * 
 * @author yangyong
 * @since 2014年12月16日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RegistrationLimitInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String softwareTypeId;
	private Integer times;
	/**
	 * 获取软件类型ID。
	 * @return 软件类型ID。
	 */
	public String getSoftwareTypeId() {
		return softwareTypeId;
	}
	/**
	 * 设置软件类型ID。
	 * @param softwareTypeId 
	 *	  软件类型ID。
	 */
	public void setSoftwareTypeId(String softwareTypeId) {
		this.softwareTypeId = softwareTypeId;
	}
	/**
	 * 获取绑定设备数目。
	 * @return 绑定设备数目。
	 */
	public Integer getTimes() {
		return times;
	}
	/**
	 * 设置绑定设备数目。
	 * @param times 
	 *	  绑定设备数目。
	 */
	public void setTimes(Integer times) {
		this.times = times;
	}
}