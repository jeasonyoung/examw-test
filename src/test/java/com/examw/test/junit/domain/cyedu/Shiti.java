package com.examw.test.junit.domain.cyedu;

import java.io.Serializable;
/**
 * 试题实体类。
 * @author lq.
 * @since 2014-09-01.
 */
public class Shiti implements Serializable{
	private static final long serialVersionUID = 1L;
	private int ID,ExamID,ClassId,DiquID,TypeID,ExamYaer;
	private String content,Analysis,Answer;
	/**
	 * 获取试题ID。
	 * @return 试题ID。
	 */
	public int getID() {
		return ID;
	}
	/**
	 * 设置试题ID。
	 * @param iD
	 * 试题ID。
	 */
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * 获取试题ID。
	 * @return
	 * 试题ID。
	 */
	public int getExamID() {
		return ExamID;
	}
	/**
	 * 设置试题ID。
	 * @param iD
	 * 试题ID。
	 */
	public void setExamID(int examID) {
		ExamID = examID;
	}
	/**
	 * 获取科目ID。
	 * @return 科目ID。
	 */
	public int getClassId() {
		return ClassId;
	}
	/**
	 * 设置科目ID。
	 * @param classId
	 * 科目ID。
	 */
	public void setClassId(int classId) {
		ClassId = classId;
	}
	/**
	 * 获取地区ID。
	 * @return 地区ID。
	 */
	public int getDiquID() {
		return DiquID;
	}
	/**
	 * 设置地区ID。
	 * @param diquID
	 * 地区ID。
	 */
	public void setDiquID(int diquID) {
		DiquID = diquID;
	}
	/**
	 * 获取试题类型。
	 * @return
	 * 试题类型。
	 */
	public int getTypeID() {
		return TypeID;
	}
	/**
	 * 设置试题类型。
	 * @param typeID
	 * 试题类型。
	 */
	public void setTypeID(int typeID) {
		TypeID = typeID;
	}
	/**
	 * 获取试题日期。
	 * @return
	 * 试题日期。
	 */
	public int getExamYaer() {
		return ExamYaer;
	}
	/**
	 * 设置试题日期。
	 * @param examYaer
	 * 试题日期。
	 */
	public void setExamYaer(int examYaer) {
		ExamYaer = examYaer;
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
		return Analysis;
	}
	/**
	 * 设置答案解析。
	 * @param analysis
	 * 答案解析。
	 */
	public void setAnalysis(String analysis) {
		Analysis = analysis;
	}
	/**
	 * 获取答案。
	 * @return
	 * 答案。
	 */
	public String getAnswer() {
		return Answer;
	}
	/**
	 * 设置答案。
	 * @param answer
	 * 答案。
	 */
	public void setAnswer(String answer) {
		Answer = answer;
	}
}