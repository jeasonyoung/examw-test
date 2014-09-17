package com.examw.test.model.products;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;


/**
 * 软件限制信息
 * @author fengwei.
 * @since 2014年8月25日 上午8:21:48.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SoftwareTypeLimitInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id;
	private String typeId,typeName;
	private String registrationId,registrationCode;
	private Integer time;
	/**
	 * 获取 ID
	 * @return id
	 * ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 软件类型ID
	 * @return typeId
	 * 软件类型ID
	 */
	public String getTypeId() {
		return typeId;
	}
	/**
	 * 设置 软件类型ID
	 * @param typeId
	 * 软件类型ID
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	/**
	 * 获取 软件类型名称
	 * @return typeName
	 * 软件类型名称
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置 软件类型名称
	 * @param typeName
	 * 软件类型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取 注册码ID
	 * @return registrationId
	 * 注册码ID
	 */
	public String getRegistrationId() {
		return registrationId;
	}
	/**
	 * 设置 注册码ID
	 * @param registrationId
	 * 注册码ID
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	/**
	 * 获取 注册码
	 * @return registrationCode
	 * 注册码
	 */
	public String getRegistrationCode() {
		return registrationCode;
	}
	/**
	 * 设置 注册码
	 * @param registrationCode
	 * 注册码
	 */
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	/**
	 * 获取 绑定次数
	 * @return time
	 * 绑定次数
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置 绑定次数
	 * @param time
	 * 绑定次数
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
}
