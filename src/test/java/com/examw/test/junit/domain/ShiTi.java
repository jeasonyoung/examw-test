package com.examw.test.junit.domain;

/**
 * 试题实体类。
 * @author lq.
 * @since 2014-09-01.
 */
public class ShiTi {
	private Integer type;
	private String id,content,analysis,answer,classId,examId,analyId;
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
	 * 获取试题题目。
	 * @return  试题题目。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置试题题目。
	 * @param content
	 * 试题题目。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取试题解析。
	 * @return 试题解析。
	 */ 
	public String getAnalysis() {
		return analysis;
	}
	/**
	 * 设置试题解析。
	 * @param analysis
	 * 试题解析。
	 */
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	/**
	 * 获取试题答案。
	 * @return 试题答案。
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置试题答案。
	 * @param answer
	 * 试题答案。
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取试题ID.
	 * @return 试题ID.
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试题ID.
	 * @param id
	 * 试题ID.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取科目ID.
	 * @return
	 * 科目ID.
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置科目ID.
	 * @param classId
	 * 科目ID.
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getAnalyId() {
		return analyId;
	}
	public void setAnalyId(String analyId) {
		this.analyId = analyId;
	}
	
}