package com.examw.test.model.api;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 考试同步数据。
 * 
 * @author yangyong
 * @since 2015年2月27日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExamSync implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr;
	private Integer code;
	private List<SubjectSync> subjects;
	private List<ProductSync> products;
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试ID。
	 * @param id 
	 *	  考试ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试代码。
	 * @return 考试代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置考试代码。
	 * @param code 
	 *	  考试代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取考试名称。
	 * @return 考试名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试名称。
	 * @param name 
	 *	  考试名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取考试简称
	 * @return 考试简称
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置考试简称
	 * @param abbr 
	 *	  考试简称
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取科目集合。
	 * @return 科目集合。
	 */
	public List<SubjectSync> getSubjects() {
		return subjects;
	}
	/**
	 * 设置科目集合。
	 * @param subjects 
	 *	  科目集合。
	 */
	public void setSubjects(List<SubjectSync> subjects) {
		this.subjects = subjects;
	}
	/**
	 * 获取产品集合。
	 * @return 产品集合。
	 */
	public List<ProductSync> getProducts() {
		return products;
	}
	/**
	 * 设置产品集合。
	 * @param products 
	 *	  产品集合。
	 */
	public void setProducts(List<ProductSync> products) {
		this.products = products;
	}
	/*
	 * 重载。
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31 + (this.getId() == null ? 0 : this.getId().hashCode());
	}
	/*
	 * 重载比较。
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof ExamSync){
			ExamSync other = (ExamSync)obj;
			if(this.getId() == null){
				return other == null;
			}
			return this.getId().equals(other.getId());
		} 
		return false;
	}
}