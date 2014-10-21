package com.examw.test.domain.library;

import java.io.Serializable;
import java.util.Date;
/**
 *  试卷结构下的试题。
 * 
 * @author yangyong
 * @since 2014年9月27日
 */
public class StructureItem implements Serializable,Comparable<StructureItem> {
	private static final long serialVersionUID = 1L;
	private Structure structure;
	private Item item;
	private Integer orderNo;
	private Date createTime;
	/**
	 * 构造函数。
	 */
	public StructureItem(){
		this.setCreateTime(new Date());
	}
	/**
	 * 构造函数。
	 * @param structure
	 * 所属结构。
	 * @param item
	 * 所属试题。
	 * @param orderNo
	 * 排序号。
	 */
	public StructureItem(Structure structure, Item item, Integer orderNo){
		this();
		this.setStructure(structure);
		this.setItem(item);
		this.setOrderNo(orderNo);
	}
	/**
	 * 获取试卷结构。
	 * @return 试卷结构。
	 */
	public Structure getStructure() {
		return structure;
	}
	/**
	 * 设置试卷结构。
	 * @param structure 
	 *	  试卷结构。
	 */
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	/**
	 * 获取试题。
	 * @return 试题。
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * 设置试题。
	 * @param item 
	 *	  试题。
	 */
	public void setItem(Item item) {
		this.item = item;
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
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime 
	 *	  创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/* 重构hasCode。
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((structure == null) ? 0 : structure.hashCode());
		return result;
	}
	/* 重构equals
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		StructureItem other = (StructureItem) obj;
		if (item == null) {
			if (other.item != null) return false;
		} else if (!item.equals(other.item)){
			return false;
		}
		if (structure == null) {
			if (other.structure != null) return false;
		} else if (!structure.equals(other.structure)){
			return false;
		}
		return true;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(StructureItem o) {
		if(this == o) return 0;
		int index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getCreateTime().compareTo(o.getCreateTime());
			if(index == 0){
				index = this.hashCode() - o.hashCode();
			}
		}
		return index;
	}
}