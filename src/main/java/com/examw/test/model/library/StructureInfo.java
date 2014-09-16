package com.examw.test.model.library;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * 试卷结构信息。
 * @author yangyong.
 * @since 2014-08-07.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class StructureInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title;
	private Integer total,type,orderNo;
	private BigDecimal score,min;
	private Set<StructureItemInfo> items;
	/**
	 * 获取结构ID。
	 * @return 结构ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置结构ID。
	 * @param id
	 * 结构ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取结构名称。
	 * @return 结构名称。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置结构名称。
	 * @param title
	 * 结构名称。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取题目数。
	 * @return total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置题目数。
	 * @param total 
	 *	  题目数。
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * 获取题型。
	 * @return 题型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置题型。
	 * @param type
	 * 题型。
	 */
	public void setType(Integer type) {
		this.type = type;
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
	 * 排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取题目分数。
	 * @return 题目分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置题目分数。
	 * @param score
	 * 题目分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取题目最小分数。
	 * @return 题目最小分数。
	 */
	public BigDecimal getMin() {
		return min;
	}
	/**
	 * 设置题目最小分数。
	 * @param min 
	 *	  题目最小分数。
	 */
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	/**
	 * 获取结构上试题信息集合。
	 * @return 结构上试题信息集合。
	 */
	public Set<StructureItemInfo> getItems() {
		return items;
	}
	/**
	 * 设置结构上试题信息集合。
	 * @param items 
	 *	  结构上试题信息集合。
	 */
	public void setItems(Set<StructureItemInfo> items) {
		this.items = items;
	}
	
	/*前台需要数据 题目的总个数  2014-09-15 by FW.*/
	private Integer total; //总的题目数
	/**
	 * 获取 题目总数
	 * @return total
	 * 
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置 题目总数
	 * @param total
	 * 
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
}
