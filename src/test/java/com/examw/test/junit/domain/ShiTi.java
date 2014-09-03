package com.examw.test.junit.domain;

import java.io.Serializable;
/**
 * 试题实体类。
 * @author lq.
 * @since 2014-09-01.
 */
public class ShiTi implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id,type,classId;
	private String content,analysis,answer;
	/**
	 * 获取试题ID。
	 * @return 试题ID。
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置试题ID。
	 * @param id
	 * 试题ID。
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取试题类型。
	 * @return 试题类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置试题类型。
	 * @param type
	 * 试题类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取科目ID。
	 * @return
	 * 科目ID。
	 */
	public Integer getClassId() {
		return classId;
	}
	/**
	 * 设置科目ID。
	 * @param classId
	 * 科目ID。
	 */
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	/**
	 * 获取试题内容。
	 * @return
	 * 试题内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置试题内容。
	 * @param content
	 * 试题内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取答案解析。
	 * @return
	 * 答案解析。
	 */
	public String getAnalysis() {
		return analysis;
	}
	/**
	 * 设置答案解析。
	 * @param analysis
	 * 答案解析。
	 */
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	/**
	 * 获取答案。
	 * @return
	 * 答案。
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置答案。
	 * @param answer
	 * 答案。
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}

