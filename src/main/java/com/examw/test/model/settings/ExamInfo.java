package com.examw.test.model.settings;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 考试设置信息
 * @author fengwei.
 * @since 2014年8月6日 下午3:05:16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ExamInfo extends FrontExamInfo{
	private static final long serialVersionUID = 1L;
	private String statusName;
	private Integer status;
	/**
	 * 获取考试状态。
	 * @return 考试状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置考试状态。
	 * @param status 
	 *	  考试状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取考试状态名称。
	 * @return 考试状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置考试状态名称。
	 * @param statusName 
	 *	  考试状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}