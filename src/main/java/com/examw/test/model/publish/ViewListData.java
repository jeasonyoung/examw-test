package com.examw.test.model.publish;

import java.io.Serializable;

/**
 * 显示列表数据。
 * 
 * @author yangyong
 * @since 2015年1月3日
 */
public class ViewListData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,text;
	private Integer total;
	/**
	 * 构造函数。
	 * @param id
	 * 数据ID。
	 * @param text
	 * 显示内容。
	 * @param total
	 * 数据统计。
	 */
	public ViewListData(String id, String text, Integer total){
		this.setId(id);
		this.setText(text);
		this.setTotal(total);
	}
	/**
	 * 构造函数。
	 * @param id
	 * 数据ID。
	 * @param text
	 * 显示内容。
	 */
	public ViewListData(String id, String text){
		this(id, text, 0);
	}
	/**
	 * 获取数据ID。
	 * @return 数据ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置数据ID。
	 * @param id 
	 *	  数据ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取显示内容。
	 * @return 显示内容。
	 */
	public String getText() {
		return text;
	}
	/**
	 * 设置显示内容。
	 * @param text 
	 *	  显示内容。
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 获取统计数值。
	 * @return 统计数值。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置统计数值。
	 * @param total 
	 *	  统计数值。
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
}