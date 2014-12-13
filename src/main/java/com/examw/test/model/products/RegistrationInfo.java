package com.examw.test.model.products;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;
/**
 * 注册码信息。
 * 
 * @author yangyong
 * @since 2014年12月13日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RegistrationInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,code,statusName,channelId,channelName;
	private String[] productId,productName;
	private Integer limits,status,count;
	private BigDecimal price;
	private Date createTime,lastTime,startTime,endTime;
	/**
	 * 获取注册码ID。
	 * @return 注册码ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置注册码ID。
	 * @param id 
	 *	  注册码ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取注册码。
	 * @return 注册码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置注册码。
	 * @param code 
	 *	  注册码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取产品ID集合。
	 * @return 产品ID集合。
	 */
	public String[] getProductId() {
		return productId;
	}
	/**
	 * 设置产品ID集合。
	 * @param productId 
	 *	  产品ID集合。
	 */
	public void setProductId(String[] productId) {
		this.productId = productId;
	}
	/**
	 * 获取产品名称集合。
	 * @return 产品名称集合。
	 */
	public String[] getProductName() {
		return productName;
	}
	/**
	 * 设置产品名称集合。
	 * @param productName 
	 *	  产品名称集合。
	 */
	public void setProductName(String[] productName) {
		this.productName = productName;
	}
	/**
	 * 获取所属渠道ID。
	 * @return 所属渠道ID。
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * 设置所属渠道ID。
	 * @param channelId 
	 *	  所属渠道ID。
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	/**
	 * 获取所属渠道名称。
	 * @return 所属渠道名称。
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * 设置所属渠道名称。
	 * @param channelName 
	 *	  所属渠道名称。
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * 获取价格。
	 * @return 价格。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置价格。
	 * @param price 
	 *	  价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取期限。
	 * @return 期限。
	 */
	public Integer getLimits() {
		return limits;
	}
	/**
	 * 设置期限。
	 * @param limits 
	 *	  期限。
	 */
	public void setLimits(Integer limits) {
		this.limits = limits;
	}
	/**
	 * 获取激活时间。
	 * @return 激活时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置激活时间。
	 * @param startTime 
	 *	  激活时间。
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取过期时间。
	 * @return 过期时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置过期时间。
	 * @param endTime 
	 *	  过期时间。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取状态。
	 * @return 状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态。
	 * @param status 
	 *	  状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName 
	 *	  状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	/**
	 * 获取创建注册码总数。
	 * @return 创建注册码总数。
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置创建注册码总数。
	 * @param count 
	 *	  创建注册码总数。
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
}