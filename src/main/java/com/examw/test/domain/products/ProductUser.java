package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品用户。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class ProductUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code,name,mobile;
	private Integer status;
	private Date createTime,lastTime;
	/**
	 * 状态－启用。
	 */
	public static final int STATUS_ENABLE = 1;
	/**
	 * 状态－禁用。
	 */
	public static final int STATUS_DISENABLE = 0;
	/**
	 * 状态－删除。
	 */
	public static final int STATUS_DELETE = -1;
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置用户ID。
	 * @param id 
	 *	  用户ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取用户代码。
	 * @return 用户代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置用户代码。
	 * @param code 
	 *	  用户代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取用户名称。
	 * @return 用户名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置用户名称。
	 * @param name 
	 *	  用户名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取手机号码。
	 * @return 手机号码。
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置手机号码。
	 * @param mobile 
	 *	  手机号码。
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取用户状态。
	 * @return 用户状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置用户状态。
	 * @param status 
	 *	  用户状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime 
	 *	  最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}