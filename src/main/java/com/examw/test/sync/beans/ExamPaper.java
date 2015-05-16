package com.examw.test.sync.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 考试试卷。
 * (用于数据同步)
 * @author jeasonyoung
 * @since 2015年5月16日
 */
public class ExamPaper implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,examId,viewUrl,realUrl,normalUrl;
	private float price;
	private Date time;
	/**
	 * 获取id
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 id
	 * @param id 
	 *	  id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 name
	 * @param name 
	 *	  name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取examId
	 * @return examId
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置 examId
	 * @param examId 
	 *	  examId
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取viewUrl
	 * @return viewUrl
	 */
	public String getViewUrl() {
		return viewUrl;
	}
	/**
	 * 设置 viewUrl
	 * @param viewUrl 
	 *	  viewUrl
	 */
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	/**
	 * 获取realUrl
	 * @return realUrl
	 */
	public String getRealUrl() {
		return realUrl;
	}
	/**
	 * 设置 realUrl
	 * @param realUrl 
	 *	  realUrl
	 */
	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}
	/**
	 * 获取normalUrl
	 * @return normalUrl
	 */
	public String getNormalUrl() {
		return normalUrl;
	}
	/**
	 * 设置 normalUrl
	 * @param normalUrl 
	 *	  normalUrl
	 */
	public void setNormalUrl(String normalUrl) {
		this.normalUrl = normalUrl;
	}
	/**
	 * 获取price
	 * @return price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * 设置 price
	 * @param price 
	 *	  price
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * 获取time
	 * @return time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * 设置 time
	 * @param time 
	 *	  time
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}