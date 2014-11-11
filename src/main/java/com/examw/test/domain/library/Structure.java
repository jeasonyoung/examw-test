package com.examw.test.domain.library;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import com.examw.test.domain.settings.Subject;

/**
 * 试卷结构。
 * @author yangyong.
 * @since 2014-08-02.
 */
public class Structure implements Serializable,Comparable<Structure> {
	private static final long serialVersionUID = 1L;
	private String id,title,description;
	private Integer type,total,orderNo;
	private BigDecimal score,min,ratio;
	private Paper paper;
	private Set<StructureItem> items;
	//add by fw 2011.11.10
	private Structure parent;
	private Set<Structure> children;
	private Subject subject;
	/**
	 * 构造函数。
	 */
	public Structure(){
		this.setId(UUID.randomUUID().toString());
	}
	/**
	 * 构造函数。
	 * @param paper
	 * 所属试卷。
	 */
	public Structure(Paper paper){
		this();
		this.setPaper(paper);
	}
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
	 * 获取试题总数。
	 * @return 试题总数。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置试题总数。
	 * @param total 
	 *	  试题总数。
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
	 * 获取试题分数。
	 * @return 试题分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置试题分数。
	 * @param score
	 * 试题分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取试题最少分数。
	 * @return 试题最少分数。
	 */
	public BigDecimal getMin() {
		return min;
	}
	/**
	 * 设置试题最少分数。
	 * @param min 
	 *	  试题最少分数。
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
	 * 获取结构下试题集合。
	 * @return 结构下试题集合。
	 */
	public Set<StructureItem> getItems() {
		return items;
	}
	/**
	 * 设置结构下试题集合。
	 * @param items 
	 *	  结构下试题集合。
	 */
	public void setItems(Set<StructureItem> items) {
		this.items = items;
	}
	/**
	 * 获取 父结构
	 * @return parent
	 * 父结构
	 */
	public Structure getParent() {
		return parent;
	}
	/**
	 * 设置 父结构
	 * @param parent
	 * 父结构
	 */
	public void setParent(Structure parent) {
		this.parent = parent;
	}
	/**
	 * 获取 子结构集合
	 * @return children
	 * 子结构集合
	 */
	public Set<Structure> getChildren() {
		return children;
	}
	/**
	 * 设置 子结构集合
	 * @param children
	 * 子结构集合
	 */
	public void setChildren(Set<Structure> children) {
		this.children = children;
	}
	
	/**
	 * 获取 分数比例
	 * @return ratio
	 * 分数比例
	 */
	public BigDecimal getRatio() {
		return ratio;
	}
	/**
	 * 设置 分数比例
	 * @param ratio
	 * 分数比例
	 */
	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
	
	/**
	 * 获取 所属科目
	 * @return subject
	 * 所属科目
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置 所属科目
	 * @param subject
	 * 所属科目
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/*
	 * 比较排序。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Structure o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getTitle().compareToIgnoreCase(o.getTitle());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
	/*
	 * 对象字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("id=%1$s,title=%2$s,type=%3$s,total=%4$s,orderNo=%5$s", this.getId(),this.getTitle(),this.getType(),this.getTotal(),this.getOrderNo());
	}
}