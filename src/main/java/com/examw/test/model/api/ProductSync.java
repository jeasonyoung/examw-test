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
	private String id,name,areaName,examDate;
	private Integer orderNo,paperTotal,itemTotal;
	private BigDecimal price,discount;
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
	 * 获取所属地区。
	 * @return 所属地区。
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * 设置所属地区。
	 * @param areaName 
	 *	  所属地区。
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取考试日期。
	 * @return 考试日期。
	 */
	public String getExamDate() {
		return examDate;
	}
	/**
	 * 设置考试日期。
	 * @param examDate 
	 *	  考试日期。
	 */
	public void setExamDate(String examDate) {
		this.examDate = examDate;
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
	 * 获取试卷总数。
	 * @return 试卷总数。
	 */
	public Integer getPaperTotal() {
		return paperTotal;
	}
	/**
	 * 设置试卷总数。
	 * @param paperTotal 
	 *	  试卷总数。
	 */
	public void setPaperTotal(Integer paperTotal) {
		this.paperTotal = paperTotal;
	}
	/**
	 * 获取试题总数。
	 * @return 试题总数。
	 */
	public Integer getItemTotal() {
		return itemTotal;
	}
	/**
	 * 设置试题总数。
	 * @param itemTotal 
	 *	  试题总数。
	 */
	public void setItemTotal(Integer itemTotal) {
		this.itemTotal = itemTotal;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}