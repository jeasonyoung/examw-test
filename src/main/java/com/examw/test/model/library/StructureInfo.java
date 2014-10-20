package com.examw.test.model.library;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 试卷结构信息基类。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class StructureInfo implements Serializable,Comparable<StructureInfo> {
	private static final long serialVersionUID = 1L;
	private String id,title,description,typeName;
	private Integer type,total,orderNo;
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
	 * 获取描述信息。
	 * @return 描述信息。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述信息。
	 * @param description 
	 *	  描述信息。
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * 获取题型名称。
	 * @return 题型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置题型名称。
	 * @param typeName 
	 *	  题型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * 获取试题集合。
	 * @return 试题集合。
	 */
	public Set<StructureItemInfo> getItems() {
		return items;
	}
	/**
	 * 设置试题集合。
	 * @param items 
	 *	  试题集合。
	 */
	public void setItems(Set<StructureItemInfo> items) {
		this.items = items;
	}
	/*
	 * 重载排序比较.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(StructureInfo o) {
		if(this == o) return 0;
		int index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getTitle().compareToIgnoreCase(o.getTitle());
			if(index == 0){
				index = this.getType() - o.getType();
				if(index == 0){
					index = this.getId().compareToIgnoreCase(o.getId());
				}
			}
		}
		return index;
	}
}