package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Set;

/**
 * 销售渠道。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Channel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer code;
	private Set<Software> softwares;
	private Set<Registration> registrations;
	/**
	 * 获取渠道ID。
	 * @return 渠道ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置渠道ID。
	 * @param id 
	 *	 渠道ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取渠道代码。
	 * @return 渠道代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置渠道代码。
	 * @param code 
	 *	  渠道代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取渠道名称。
	 * @return 渠道名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置渠道名称。
	 * @param name 
	 *	 渠道名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取关联的软件集合。
	 * @return 关联的软件集合。
	 */
	public Set<Software> getSoftwares() {
		return softwares;
	}
	/**
	 * 设置关联的软件集合。
	 * @param softwares 
	 *	  关联的软件集合。
	 */
	public void setSoftwares(Set<Software> softwares) {
		this.softwares = softwares;
	}
	/**
	 * 获取关联的注册码集合。
	 * @return 关联的注册码集合。
	 */
	public Set<Registration> getRegistrations() {
		return registrations;
	}
	/**
	 * 设置关联的注册码集合。
	 * @param registrations 
	 *	  关联的注册码集合。
	 */
	public void setRegistrations(Set<Registration> registrations) {
		this.registrations = registrations;
	}
}