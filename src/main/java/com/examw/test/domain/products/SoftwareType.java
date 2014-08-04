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
	private String id,code,name;
	private Set<SoftwareTypeLimit> limits;
	private Set<RegistrationBindRecord> records;
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
	public String getCode() {
		return code;
	}
	/**
	 * 设置类型代码。
	 * @param code 
	 *	  类型代码。
	 */
	public void setCode(String code) {
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
	 * 获取关联限制集合。
	 * @return 关联限制集合。
	 */
	public Set<SoftwareTypeLimit> getLimits() {
		return limits;
	}
	/**
	 * 设置关联限制集合。
	 * @param limits 
	 *	  关联限制集合。
	 */
	public void setLimits(Set<SoftwareTypeLimit> limits) {
		this.limits = limits;
	}
	/**
	 * 获取绑定记录集合。
	 * @return 绑定记录集合。
	 */
	public Set<RegistrationBindRecord> getRecords() {
		return records;
	}
	/**
	 * 设置绑定记录集合。
	 * @param records 
	 *	  绑定记录集合。
	 */
	public void setRecords(Set<RegistrationBindRecord> records) {
		this.records = records;
	}
}