package com.examw.test.model.syllabus;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 大纲信息。
 * @author lq.
 * @since 2014-08-06.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SyllabusInfo extends Paging implements Comparable<SyllabusInfo> {
	private static final long serialVersionUID = 1L;
	private String pid,id,title,subjectId,subjectName,examId,examName,statusName;
	private Set<SyllabusInfo> children;
	private Integer status, orderNo;
	/**
	 * 获取上级要点ID。
	 * @return 上级要点ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级要点ID。
	 * @param pid
	 * 上级要点ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取要点ID。
	 * @return 要点ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置要点ID。
	 * @param id
	 * 要点ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取要点。
	 * @return 要点。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置要点。
	 * @param title
	 * 要点。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subjectId
	 * 所属科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取所属科目名称。
	 * @return 所属科目名称。
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置所属科目名称。
	 * @param subjectName
	 * 所属科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取子要点集合。
	 * @return 子要点集合。
	 */
	public Set<SyllabusInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子要点集合。
	 * @param children
	 * 	子要点集合。
	 */
	public void setChildren(Set<SyllabusInfo> children) {
		this.children = children;
	}
	/**
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId
	 * 所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属考试名称。
	 * @return 所属考试名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属考试名称。
	 * @param examName
	 * 所属考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取状态（1-启用，0-停用）。
	 * @return 状态（1-启用，0-停用）。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态（1-启用，0-停用）。
	 * @param status 
	 *	  状态（1-启用，0-停用）。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName 
	 *	  状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	 * 	排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SyllabusInfo o) {
		if(this == o) return 0;
		int index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getTitle().compareToIgnoreCase(o.getTitle());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}