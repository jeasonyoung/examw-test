package com.examw.test.model.products;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 产品用户信息
 * @author fengwei.
 * @since 2014年8月11日 下午3:25:08.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductUserInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,code,name,mobile,statusName;
	private Integer status;
	private Date createTime,lastTime;
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
	 * 获取用户名。
	 * @return 用户名。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置用户名。
	 * @param name 
	 *	  用户名。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取用户手机号码。
	 * @return 用户手机号码。
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置用户手机号码。
	 * @param mobile 
	 *	  用户手机号码。
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
	 * 获取状态名称。
	 * @return  状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName
	 *  状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */ 
	@JsonSerialize(using=CustomDateSerializer.LongDate.class)
	public Date getCreateTime() {
		return  this.createTime;
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
	@JsonSerialize(using=CustomDateSerializer.LongDate.class)
	public Date getLastTime() {
		return this.lastTime;
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