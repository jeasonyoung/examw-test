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
	private Integer type,orderNo;
	private BigDecimal score;
	private Paper paper;
	private Structure parent;
	private Set<Structure> children;
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
	 * 获取分数。
	 * @return 分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置分数。
	 * @param score
	 * 分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
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
	 * 获取上级结构。
	 * @return 上级结构。
	 */
	public Structure getParent() {
		return parent;
	}
	/**
	 * 设置上级结构。
	 * @param parent
	 * 上级结构。
	 */
	public void setParent(Structure parent) {
		this.parent = parent;
	}
	/**
	 * 获取子结构集合。
	 * @return 子结构集合。
	 */
	public Set<Structure> getChildren() {
		return children;
	}
	/**
	 * 设置子结构集合。
	 * @param children
	 * 子结构集合。
	 */
	public void setChildren(Set<Structure> children) {
		this.children = children;
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