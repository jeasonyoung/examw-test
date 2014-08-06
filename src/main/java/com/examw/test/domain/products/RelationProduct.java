package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Date;

/**
 * 关联产品。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class RelationProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Product product;
	private Registration registration;
	private Date createTime;
	/**
	 * 获取关联ID。
	 * @return 关联ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置关联ID。
	 * @param id 
	 *	 关联ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取关联产品名称。
	 * @return 关联产品名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置关联产品名称。
	 * @param name 
	 *	  关联产品名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取关联产品。
	 * @return 关联产品。
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * 设置关联产品。
	 * @param product 
	 *	  关联产品。
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * 获取所属注册码。
	 * @return 所属注册码。
	 */
	public Registration getRegistration() {
		return registration;
	}
	/**
	 * 设置所属注册码。
	 * @param registration 
	 *	  所属注册码。
	 */
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	/**
	 * 获取关联时间。
	 * @return 关联时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置关联时间。
	 * @param createTime 
	 *	  关联时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}