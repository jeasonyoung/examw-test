package com.examw.test.domain.settings;

import java.io.Serializable;
import java.util.Set;

import com.examw.test.domain.products.Product;

/**
 * 考试科目。
 * @author yangyong.
 * @since 2014-08-02.
 */
public class Subject implements Serializable,Comparable<Subject>{
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer code;
	private Exam exam;
	private Set<Area> areas;
	private Set<Product> products;
	//Add by FW,2014.11.10 将科目改为树形结构 适应类似3+x考试
	private Subject parent;
	private Set<Subject> children;
	/**
	 * 获取科目ID。
	 * @return 科目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置科目ID。
	 * @param id
	 * 科目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取科目代码。
	 * @return 科目代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置科目代码。
	 * @param code
	 * 科目代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取科目名称。
	 * @return 科目名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置科目名称。
	 * @param name
	 * 科目名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取所属考试。
	 * @return 所属考试。
	 */
	public Exam getExam() {
		return exam;
	}
	/**
	 * 设置所属考试。
	 * @param exam
	 * 所属考试。
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	/**
	 * 获取所属地区集合。
	 * @return 所属地区集合。
	 */
	public Set<Area> getAreas() {
		return areas;
	}
	/**
	 * 设置所属地区。
	 * @param area
	 * 所属地区。
	 */
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}
	/**
	 * 获取关联的产品集合。
	 * @return 关联的产品集合。
	 */
	public Set<Product> getProducts() {
		return products;
	}
	/**
	 * 设置关联的产品集合。
	 * @param products 
	 *	  关联的产品集合。
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	/**
	 * 获取上级科目。
	 * @return 上级科目。
	 */
	public Subject getParent() {
		return parent;
	}
	/**
	 * 设置上级科目。
	 * @param parent
	 * 上级科目。
	 */
	public void setParent(Subject parent) {
		this.parent = parent;
	}
	/**
	 * 获取子科目集合。
	 * @return 子科目集合。
	 */
	public Set<Subject> getChildren() {
		return children;
	}
	/**
	 * 设置子科目集合。
	 * @param children
	 * 子科目集合。
	 */
	public void setChildren(Set<Subject> children) {
		this.children = children;
	}
	/*
	 * 对象字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("id=%1$s,code=%2$s,name=%3$s", this.getId(), this.getCode(), this.getName());
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Subject o) {
		int index = 0;
		if(this == o) return index; 
		index = this.getCode() - o.getCode();
		if(index == 0){
			index = this.getName().compareToIgnoreCase(o.getName());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}