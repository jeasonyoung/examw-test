package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Set;

/**
 * 软件类型。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class SoftwareType implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer code;
	private Set<Software> softwares;
	private Set<SoftwareTypeLimit> softwareTypeLimits;
	private Set<RegistrationBinding> bindings;
	/**
	 * 获取类型ID。
	 * @return 类型ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置类型ID。
	 * @param id 
	 *	 类型ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取类型代码。
	 * @return 类型代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置类型代码。
	 * @param code 
	 *	  类型代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取类型名称。
	 * @return 类型名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置类型名称。
	 * @param name 
	 *	  类型名称。
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
	 * 获取关联限制集合。
	 * @return 关联限制集合。
	 */
	public Set<SoftwareTypeLimit> getSoftwareTypeLimits() {
		return softwareTypeLimits;
	}
	/**
	 * 设置关联限制集合。
	 * @param softwareTypeLimits 
	 *	  关联限制集合。
	 */
	public void setSoftwareTypeLimits(Set<SoftwareTypeLimit> softwareTypeLimits) {
		this.softwareTypeLimits = softwareTypeLimits;
	}
	/**
	 * 获取关联注册码绑定集合。
	 * @return 关联注册码绑定集合。
	 */
	public Set<RegistrationBinding> getBindings() {
		return bindings;
	}
	/**
	 * 设置关联注册码绑定集合。
	 * @param bindings 
	 *	  关联注册码绑定集合。
	 */
	public void setBindings(Set<RegistrationBinding> bindings) {
		this.bindings = bindings;
	}
}