package com.examw.test.model.products;

import java.math.BigDecimal;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 注册码信息基础抽象类。
 * 
 * @author yangyong
 * @since 2014年12月18日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public abstract class BaseRegistrationInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String categoryId,examId,productId,channelId;
	private Integer limits,status;
	private BigDecimal price;
	private Set<RegistrationLimitInfo> typeLimits;
	/**
	 * 获取所属考试类别ID。
	 * @return 所属考试类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param categoryId 
	 *	  所属考试类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId 
	 *	  所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取产品ID。
	 * @return 产品ID。
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置产品ID。
	 * @param productId 
	 *	  产品ID。
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * 获取注册码软件类型限制集合。
	 * @return 注册码软件类型限制集合。
	 */
	public Set<RegistrationLimitInfo> getTypeLimits() {
		return typeLimits;
	}
	/**
	 * 设置注册码软件类型限制集合。
	 * @param typeLimits 
	 *	  注册码软件类型限制集合。
	 */
	public void setTypeLimits(Set<RegistrationLimitInfo> typeLimits) {
		this.typeLimits = typeLimits;
	}
}