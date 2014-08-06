package com.examw.test.domain.settings;

import java.io.Serializable;

/**
 * 行政地区。
 * @author yangyong.
 * @since 2014-08-01.
 */
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code,name,abbr;
	/**
	 * 获取地区ID。
	 * @return 地区ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置地区ID。
	 * @param id
	 * 地区ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取地区代码。
	 * @return 地区代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置地区代码。
	 * @param code
	 * 地区代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取地区名称。
	 * @return 地区名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置地区名称。
	 * @param name
	 * 地区名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取EN简称。
	 * @return EN简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置EN简称。
	 * @param abbr
	 * EN简称。
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
}