package com.examw.test.sync.beans;

import java.io.Serializable;
/**
 * 考试产品。
 * (用户数据同步服务)
 * @author jeasonyoung
 * @since 2015年5月16日
 */
public class ExamProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,examId,viewUrl,enterUrl;
	private float originalPrice,discountPrice;
	private int items,orderNo;
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
	 *	 产品ID。
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
	 * 获取产品展示URL。
	 * @return 产品展示URL。
	 */
	public String getViewUrl() {
		return viewUrl;
	}
	/**
	 * 设置产品展示URL。
	 * @param viewUrl 
	 *	  产品展示URL。
	 */
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	/**
	 * 获取产品入口URL。
	 * @return 产品入口URL。
	 */
	public String getEnterUrl() {
		return enterUrl;
	}
	/**
	 * 设置产品入口URL。
	 * @param enterUrl 
	 *	  产品入口URL。
	 */
	public void setEnterUrl(String enterUrl) {
		this.enterUrl = enterUrl;
	}
	/**
	 * 获取原价。
	 * @return 原价。
	 */
	public float getOriginalPrice() {
		return originalPrice;
	}
	/**
	 * 设置原价。
	 * @param originalPrice 
	 *	  原价。
	 */
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	/**
	 * 获取优惠价。
	 * @return 优惠价。
	 */
	public float getDiscountPrice() {
		return discountPrice;
	}
	/**
	 * 设置优惠价。
	 * @param discountPrice 
	 *	  优惠价。
	 */
	public void setDiscountPrice(float discountPrice) {
		this.discountPrice = discountPrice;
	}
	/**
	 * 获取试题数。
	 * @return 试题数。
	 */
	public int getItems() {
		return items;
	}
	/**
	 * 设置试题数。
	 * @param items 
	 *	  试题数。
	 */
	public void setItems(int items) {
		this.items = items;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public int getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	/*
	 * 重载。
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31*2 + (this.getId() == null ? 0 : this.getId().hashCode());
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(obj instanceof ExamProduct){
			String otherId = ((ExamProduct)obj).getId();
			if(this.getId() == null){
				return otherId == null;
			}
			return this.getId().equals(otherId);
		}
		return false;
	}
}
