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
	private Integer limits,status;
	private BigDecimal price;
	private Product product;
	private Channel channel;
	private Set<SoftwareTypeLimit> softwareTypeLimits;
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
	 * 获取注册码价格。
	 * @return 注册码价格。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置注册码价格。
	 * @param price 
	 *	  注册码价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取所属产品。
	 * @return 所属产品。
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * 设置所属产品。
	 * @param products 
	 *	  所属产品。
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * 获取营销渠道。
	 * @return 营销渠道。
	 */
	public Channel getChannel() {
		return channel;
	}
	/**
	 * 设置营销渠道。
	 * @param channel 
	 *	  营销渠道。
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	/**
	 * 获取软件类型限制集合。
	 * @return 软件类型限制集合。
	 */
	public Set<SoftwareTypeLimit> getSoftwareTypeLimits() {
		return softwareTypeLimits;
	}
	/**
	 * 设置软件类型限制集合。
	 * @param softwareTypeLimits 
	 *	  软件类型限制集合。
	 */
	public void setSoftwareTypeLimits(Set<SoftwareTypeLimit> softwareTypeLimits) {
		this.softwareTypeLimits = softwareTypeLimits;
	}
	/**
	 * 获取有效期限(月)。
	 * @return 有效期限(月)。
	 */
	public Integer getLimits() {
		return limits;
	}
	/**
	 * 设置有效期限(月)。
	 * @param limits 
	 *	  有效期限(月)。
	 */
	public void setLimits(Integer limits) {
		this.limits = limits;
	}
	/**
	 * 获取首次激活时间。
	 * @return 首次激活时间。
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置首次激活时间。
	 * @param startTime 
	 *	  首次激活时间。
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