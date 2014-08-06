package com.examw.test.domain.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 产品注册码。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Registration implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code;
	private Set<RelationProduct> products;
	private Channel channel;
	private ProductUser user;
	private Set<SoftwareTypeLimit> typeLimits;
	private Integer limitTime,status;
	private BigDecimal price,discount;
	private Date createTime,lastTime,startTime,endTime; 
	private Set<RegistrationBindRecord> bindRecords;
	/**
	 * 状态－未激活。
	 */
	public static final int STATUS_NONE = 0;
	/**
	 * 状态－激活。
	 */
	public static final int STATUS_ACTIVATION = 1; 
	/**
	 *  状态－停用。
	 */
	public static final int STATUS_STOP = -1; 
	/**
	 * 状态－注销。
	 */
	public static final int STATUS_CANCLE = -2;
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
	 * 获取关联产品集合。
	 * @return 关联产品集合。
	 */
	public Set<RelationProduct> getProducts() {
		return products;
	}
	/**
	 * 设置关联产品集合。
	 * @param products 
	 *	  关联产品集合。
	 */
	public void setProducts(Set<RelationProduct> products) {
		this.products = products;
	}
	/**
	 * 获取所属渠道。
	 * @return 所属渠道。
	 */
	public Channel getChannel() {
		return channel;
	}
	/**
	 * 设置所属渠道。
	 * @param channel 
	 *	  所属渠道。
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	/**
	 * 获取关联用户。
	 * @return 关联用户。
	 */
	public ProductUser getUser() {
		return user;
	}
	/**
	 * 设置关联用户。
	 * @param user 
	 *	  关联用户。
	 */
	public void setUser(ProductUser user) {
		this.user = user;
	}
	/**
	 * 获取软件类型限制集合。
	 * @return 软件类型限制集合。
	 */
	public Set<SoftwareTypeLimit> getTypeLimits() {
		return typeLimits;
	}
	/**
	 * 设置软件类型限制集合。
	 * @param typeLimits 
	 *	  软件类型限制集合。
	 */
	public void setTypeLimits(Set<SoftwareTypeLimit> typeLimits) {
		this.typeLimits = typeLimits;
	}
	/**
	 * 获取使用期限。
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
	 * 获取激活时间。
	 * @return 激活时间。
	 */
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
	 * 获取绑定记录集合。
	 * @return 绑定记录集合。
	 */
	public Set<RegistrationBindRecord> getBindRecords() {
		return bindRecords;
	}
	/**
	 * 设置绑定记录集合。
	 * @param bindRecords 
	 *	 绑定记录集合。
	 */
	public void setBindRecords(Set<RegistrationBindRecord> bindRecords) {
		this.bindRecords = bindRecords;
	}
}