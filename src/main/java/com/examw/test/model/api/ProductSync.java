package com.examw.test.model.api;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 产品同步数据。
 * 
 * @author jeasonyoung
 * @since 2015年4月3日
 */
public class ProductSync implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,area;
	private BigDecimal price,discount;
	private Integer papers,items,order;
	/**
	 * 获取产品ID。
	 * @return 产品ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置产品ID。
	 * @param id 
	 *	  产品ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取产品名称。
	 * @return 产品名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置产品名称。
	 * @param name 
	 *	  产品名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取area
	 * @return area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * 设置 area
	 * @param area 
	 *	  area
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * 获取产品原价。
	 * @return 产品原价。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置产品原价。
	 * @param price 
	 *	  产品原价。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取产品优惠价。
	 * @return 产品优惠价。
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	/**
	 * 设置产品优惠价。
	 * @param discount 
	 *	  产品优惠价。
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	/**
	 * 获取试卷数。
	 * @return 试卷数。
	 */
	public Integer getPapers() {
		return papers;
	}
	/**
	 * 设置试卷数。
	 * @param papers 
	 *	  试卷数。
	 */
	public void setPapers(Integer papers) {
		this.papers = papers;
	}
	/**
	 * 获取试题数。
	 * @return 试题数。
	 */
	public Integer getItems() {
		return items;
	}
	/**
	 * 设置试题数。
	 * @param items 
	 *	  试题数。
	 */
	public void setItems(Integer items) {
		this.items = items;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * 设置排序号。
	 * @param order 
	 *	  排序号。
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/*
	 * 重载。
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31 + (this.getId() == null ? 0 : this.getId().hashCode());
	}
	/*
	 * 重载比较。
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof ProductSync){
			ProductSync other = (ProductSync)obj;
			if(this.getId() == null){
				return other.getId() == null;
			}
			return this.getId().equals(other.getId());
		}
		return false;
	}
}