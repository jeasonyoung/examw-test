package com.examw.test.model.syllabus;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 大纲信息。
 * 
 * @author lq.
 * @since 2014-08-06.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SyllabusInfo extends BaseSyllabusInfo implements Comparable<SyllabusInfo> {
	private static final long serialVersionUID = 1L;
	private String subjectId, subjectName, examId, examName, statusName;
	private Set<SyllabusInfo> children;
	private Integer status, orderNo;
	private Integer year;
	private String[] areaId, areaName;

	/**
	 * 获取所属科目ID。
	 * 
	 * @return 所属科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * 设置所属科目ID。
	 * 
	 * @param subjectId
	 *            所属科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * 获取所属科目名称。
	 * 
	 * @return 所属科目名称。
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * 设置所属科目名称。
	 * 
	 * @param subjectName
	 *            所属科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * 获取子要点集合。
	 * 
	 * @return 子要点集合。
	 */
	public Set<SyllabusInfo> getChildren() {
		return children;
	}

	/**
	 * 设置子要点集合。
	 * 
	 * @param children
	 *            子要点集合。
	 */
	public void setChildren(Set<SyllabusInfo> children) {
		this.children = children;
	}

	/**
	 * 获取所属考试ID。
	 * 
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * 设置所属考试ID。
	 * 
	 * @param examId
	 *            所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	/**
	 * 获取所属考试名称。
	 * 
	 * @return 所属考试名称。
	 */
	public String getExamName() {
		return examName;
	}

	/**
	 * 设置所属考试名称。
	 * 
	 * @param examName
	 *            所属考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}

	/**
	 * 获取状态（1-启用，0-停用）。
	 * 
	 * @return 状态（1-启用，0-停用）。
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置状态（1-启用，0-停用）。
	 * 
	 * @param status
	 *            状态（1-启用，0-停用）。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取状态名称。
	 * 
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * 设置状态名称。
	 * 
	 * @param statusName
	 *            状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * 获取排序号。
	 * 
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}

	/**
	 * 设置排序号。
	 * 
	 * @param orderNo
	 *            排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	/*
	 * 排序比较。
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SyllabusInfo o) {
		if (this == o)
			return 0;
		int index = this.getOrderNo() - o.getOrderNo();
		if (index == 0) {
			index = this.getTitle().compareToIgnoreCase(o.getTitle());
			if (index == 0) {
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}

	/**
	 * 获取 年份
	 * 
	 * @return year 年份
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * 设置 年份
	 * 
	 * @param year
	 *            年份
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * 获取 地区ID
	 * 
	 * @return areaId 地区ID
	 */
	public String[] getAreaId() {
		return areaId;
	}

	/**
	 * 设置 地区ID
	 * 
	 * @param areaId
	 *            地区ID
	 */
	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}

	/**
	 * 获取 地区名称
	 * 
	 * @return areaName 地区名称
	 */
	public String[] getAreaName() {
		return areaName;
	}

	/**
	 * 设置 地区名称
	 * 
	 * @param areaName
	 *            地区名称
	 */
	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}

	// 2015.02.11 增加一个层级属性,用于前台展现
	private Integer level;

	/**
	 * 获取 层级
	 * 
	 * @return level
	 * 
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 设置 层级
	 * 
	 * @param level
	 * 
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	// 2015.03.06 添加一个内容字段
	private String description; // 描述
	/**
	 * 获取 描述
	 * 
	 * @return description 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 描述
	 * 
	 * @param description
	 * 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}