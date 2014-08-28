package com.examw.test.model.products;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 软件类型信息
 * @author fengwei.
 * @since 2014年8月11日 下午3:46:46.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SoftwareTypeInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer code;
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
}
