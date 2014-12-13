package com.examw.test.model.products;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 软件类型限制信息。
 * @author fengwei.
 * @since 2014年8月25日 上午8:21:48.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SoftwareTypeLimitInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,softwareTypeId,softwareTypeName,registerId,registerCode;
	private Integer times;
	private Date createTime,lastTime;
	/**
	 * 获取限制ID。
	 * @return 限制ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置限制ID。
	 * @param id 
	 *	  限制ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取所属软件类型ID。
	 * @return 所属软件类型ID。
	 */
	public String getSoftwareTypeId() {
		return softwareTypeId;
	}
	/**
	 * 设置所属软件类型ID。
	 * @param softwareTypeId 
	 *	  所属软件类型ID。
	 */
	public void setSoftwareTypeId(String softwareTypeId) {
		this.softwareTypeId = softwareTypeId;
	}
	/**
	 * 获取所属软件类型名称。
	 * @return 所属软件类型名称。
	 */
	public String getSoftwareTypeName() {
		return softwareTypeName;
	}
	/**
	 * 设置所属软件类型名称。
	 * @param softwareTypeName 
	 *	  所属软件类型名称。
	 */
	public void setSoftwareTypeName(String softwareTypeName) {
		this.softwareTypeName = softwareTypeName;
	}
	/**
	 * 获取注册码ID。
	 * @return 注册码ID。
	 */
	public String getRegisterId() {
		return registerId;
	}
	/**
	 * 设置注册码ID。
	 * @param registerId 
	 *	  注册码ID。
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	/**
	 * 获取注册码。
	 * @return 注册码。
	 */
	public String getRegisterCode() {
		return registerCode;
	}
	/**
	 * 设置注册码。
	 * @param registerCode 
	 *	  注册码。
	 */
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
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
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
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