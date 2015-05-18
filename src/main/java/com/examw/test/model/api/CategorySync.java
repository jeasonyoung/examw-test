package com.examw.test.model.api;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * 考试类别同步数据模型。
 * 
 * @author jeasonyoung
 * @since 2015年5月18日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CategorySync implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr;
	private Integer code;
	private List<ExamSync> exams;
	/**
	 * 获取考试类别ID。
	 * @return 考试类别ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试类别ID。
	 * @param id 
	 *	  考试类别ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试类别代码。
	 * @return 考试类别代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置考试类别代码。
	 * @param code 
	 *	  考试类别代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取考试类别名称。
	 * @return 考试类别名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试类别名称。
	 * @param name 
	 *	  考试类别名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取考试类别简称。
	 * @return 考试类别简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置考试类别简称。
	 * @param abbr 
	 *	  考试类别简称。
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取所属考试集合。
	 * @return 所属考试集合。
	 */
	public List<ExamSync> getExams() {
		return exams;
	}
	/**
	 * 设置所属考试集合。
	 * @param exams 
	 *	  所属考试集合。
	 */
	public void setExams(List<ExamSync> exams) {
		this.exams = exams;
	}
	/*
	 * 重载。
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31 + (this.getId() ==  null ? 0 : this.getId().hashCode());
	}
	/*
	 * 重载
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof CategorySync){
			CategorySync other = (CategorySync)obj;
			if(this.getId() == null){
				return other.getId() == null;
			}
			return this.getId().equals(other.getId());
		}
		return false;
	}
}