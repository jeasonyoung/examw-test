package com.examw.test.model.products;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 注册码绑定记录信息
 * @author fengwei.
 * @since 2014年8月12日 下午2:52:45.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RegistrationBindingInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,machine,registrationId,registrationCode,softwareTypeId,softwareTypeName,userId,userCode,userName,userMobile;
	private Integer times;
	private Date createTime,lastTime;
	/**
	 * 获取绑定ID。
	 * @return 绑定ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置绑定ID。
	 * @param id 
	 *	  绑定ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取注册码ID。
	 * @return 注册码ID。
	 */
	public String getRegistrationId() {
		return registrationId;
	}
	/**
	 * 设置注册码ID。
	 * @param registrationId
	 * 注册码ID。
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	/**
	 * 获取注册码代码。
	 * @return 注册码代码。
	 */
	public String getRegistrationCode() {
		return registrationCode;
	}
	/**
	 * 设置注册码代码。
	 * @param registrationCode
	 * 注册码代码。
	 */
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
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
	 * 软件类型ID。
	 */
	public void setSoftwareTypeId(String softwareTypeId) {
		this.softwareTypeId = softwareTypeId;
	}
	/**
	 * 获取软件类型名称。
	 * @return 软件类型名称。
	 */
	public String getSoftwareTypeName() {
		return softwareTypeName;
	}
	/**
	 * 设置软件类型名称。
	 * @param softwareTypeName
	 * 	软件类型名称。
	 */
	public void setSoftwareTypeName(String softwareTypeName) {
		this.softwareTypeName = softwareTypeName;
	}
	/**
	 * 获取所属产品用户ID。
	 * @return 所属产品用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置所属产品用户ID。
	 * @param userId 
	 *	  所属产品用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取所属产品用户代码。
	 * @return 产品用户代码。
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * 设置产品用户代码。
	 * @param userCode 
	 *	  产品用户代码。
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * 获取所属产品用户名称。
	 * @return 所属产品用户名称。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置所属产品用户名称。
	 * @param userName 
	 *	  所属产品用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取所属产品用户电话。
	 * @return 所属产品用户电话。
	 */
	public String getUserMobile() {
		return userMobile;
	}
	/**
	 * 设置所属产品用户电话。
	 * @param userMobile 
	 *	  所属产品用户电话。
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	/**
	 * 获取设备机器码。
	 * @return 设备机器码。
	 */
	public String getMachine() {
		return machine;
	}
	/**
	 * 设置设备机器码。
	 * @param machineCode 
	 *	  设备机器码。
	 */
	public void setMachine(String machine) {
		this.machine = machine;
	}
	/**
	 * 获取绑定次数。
	 * @return 绑定次数。
	 */
	public Integer getTimes() {
		return times;
	}
	/**
	 * 设置绑定次数。
	 * @param times 
	 *	  绑定次数。
	 */
	public void setTimes(Integer times) {
		this.times = times;
	}
	/**
	 * 获取绑定时间。
	 * @return 绑定时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置绑定时间。
	 * @param createTime 
	 *	 绑定时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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