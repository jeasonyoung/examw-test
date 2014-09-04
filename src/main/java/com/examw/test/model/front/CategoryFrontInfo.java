package com.examw.test.model.front;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.test.model.settings.ExamInfo;

/**
 * 考试类别前台模型
 * @author fengwei.
 * @since 2014年9月4日 下午12:03:56.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryFrontInfo {	
	private String id,name,abbr;
	private List<ExamInfo> exams;
	private String fullName;
	private Integer code;
	/**
	 * 获取 ID
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 名称
	 * @return name
	 * 
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 名称
	 * @param name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 英文简称
	 * @return abbr
	 * 
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置 英文简称
	 * @param abbr
	 * 
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取 下属考试集合
	 * @return exams
	 * 
	 */
	public List<ExamInfo> getExams() {
		return exams;
	}
	/**
	 * 设置  下属考试集合
	 * @param exams
	 * 
	 */
	public void setExams(List<ExamInfo> exams) {
		this.exams = exams;
	}
	/**
	 * 获取 全称
	 * @return fullName
	 * 
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * 设置  全称
	 * @param fullName
	 * 
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * 获取 代码
	 * @return code
	 * 
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置 代码
	 * @param code
	 * 
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
}
