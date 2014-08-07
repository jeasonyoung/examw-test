package com.examw.test.model.settings;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 科目信息
 * @author fengwei.
 * @since 2014年8月6日 下午3:05:25.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubjectInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,code,name;
	private String examId,examName;
	private String areaId,areaName;
	private String categoryId,categoryName;
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
	public String getCode() {
		return code;
	}
	/**
	 * 设置科目代码。
	 * @param code
	 * 科目代码。
	 */
	public void setCode(String code) {
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
	 * 获取 所属考试ID
	 * @return examId
	 * 所属考试ID
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置 所属考试ID
	 * @param examId
	 * 所属考试ID
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取 所属考试名称
	 * @return examName
	 * 所属考试名称
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置 所属考试名称
	 * @param examName
	 * 所属考试名称
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取 地区ID
	 * @return areaId
	 * 地区ID
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * 设置 地区ID
	 * @param areaId
	 * 地区ID
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取 地区名称
	 * @return areaName
	 * 地区名称
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * 设置 地区名称
	 * @param areaName
	 * 地区名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取 所属分类ID
	 * @return categoryId
	 * 所属分类ID
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置 所属分类ID
	 * @param categoryId
	 * 所属分类ID
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取 所属分类名称
	 * @return categoryName
	 * 所属分类名称
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置 所属分类名称
	 * @param categoryName
	 * 所属分类名称
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
