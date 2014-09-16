package com.examw.test.domain.library;

import java.io.Serializable;

/**
 * 来源。
 * @author yangyong.
 * @since 2014-08-02.
 */
public class Source implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer code;
	/**
	 * 获取来源ID。
	 * @return 来源ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置来源ID。
	 * @param id
	 * 来源ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取来源代码。
	 * @return 来源代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置来源代码。
	 * @param code
	 * 来源代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取来源名称。
	 * @return 来源名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置来源名称。
	 * @param name
	 * 来源名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
}