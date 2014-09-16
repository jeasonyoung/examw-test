package com.examw.test.domain.library;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 试卷结构。
 * @author yangyong.
 * @since 2014-08-02.
 */
public class Structure implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title;
	private Integer type,total,orderNo;
	private BigDecimal score,min;
	private Paper paper;
	private Set<StructureItem> items;
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
	 * 获取题目总数。
	 * @return 题目总数。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置题目总数。
	 * @param total 
	 *	  题目总数。
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
	 * 获取题目最少分数。
	 * @return 题目最少分数。
	 */
	public BigDecimal getMin() {
		return min;
	}
	/**
	 * 设置题目最少分数。
	 * @param min 
	 *	  题目最少分数。
	 */
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	/**
	 * 获取所属试卷。
	 * @return 所属试卷。
	 */
	public Paper getPaper() {
		return paper;
	}
	/**
	 * 设置所属试卷。
	 * @param paper
	 * 所属试卷。
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * 获取结构下题目集合。
	 * @return 结构下题目集合。
	 */
	public Set<StructureItem> getItems() {
		return items;
	}
	/**
	 * 设置结构下题目集合。
	 * @param items 
	 *	  结构下题目集合。
	 */
	public void setItems(Set<StructureItem> items) {
		this.items = items;
	}
}