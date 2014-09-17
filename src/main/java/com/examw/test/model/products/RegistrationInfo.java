package com.examw.test.model.products;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 注册码信息
 * @author fengwei.
 * @since 2014年8月12日 下午2:20:05.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RegistrationInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,code;
	private String channelId,channelName;
	private String userId,userName;
	private Integer limitTime,status;
	private BigDecimal price,discount;
	private Date createTime,lastTime;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;
	private String statusName;
	private String[] productId;
	private String productName;
	private Set<SoftwareTypeLimitInfo> limit;
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
	 * 获取使用期限(月份)
	 * @return 使用期限。
	 */
	public Integer getLimitTime() {
		return limitTime;
	}
	/**
	 * 设置使用期限。
	 * @param limitTime 
	 *	  使用期限。
	 */
	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
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
	 * 获取原价。
	 * @return 原价。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置原价。
	 * @param price 
	 *	  原价。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取优惠价。
	 * @return 优惠价。
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	/**
	 * 设置优惠价。
	 * @param discount 
	 *	  优惠价。
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
	@JsonSerialize(using = CustomDateSerializer.ShortDate.class)
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
	 * 获取 渠道ID
	 * @return channelId
	 * 
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * 设置 渠道ID
	 * @param channelId
	 * 
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	/**
	 * 获取 渠道名称
	 * @return channelName
	 * 
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * 设置 渠道名称
	 * @param channelName
	 * 
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * 获取 使用用户ID
	 * @return userId
	 * 
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 使用用户ID
	 * @param userId
	 * 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 使用用户名称
	 * @return userName
	 * 
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置 使用用户名称
	 * @param userName
	 * 
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取 状态名称
	 * @return statusName
	 * 
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 状态名称
	 * @param statusName
	 * 
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取 关联产品ID
	 * @return relationProductId
	 * 
	 */
	public String[] getProductId() {
		return productId;
	}
	/**
	 * 设置 关联产品ID
	 * @param productId
	 * 
	 */
	public void setProductId(String[] productId) {
		this.productId = productId;
	}
	/**
	 * 获取 关联产品名称
	 * @return 关联产品名称
	 * 
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置 关联产品名称
	 * @param productName
	 * 
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取 软件类型限制
	 * @return limit
	 * 
	 */
	public Set<SoftwareTypeLimitInfo> getLimit() {
		return limit;
	}
	/**
	 * 设置 软件类型限制
	 * @param limit
	 * 
	 */
	public void setLimit(Set<SoftwareTypeLimitInfo> limit) {
		this.limit = limit;
	}
	
}
