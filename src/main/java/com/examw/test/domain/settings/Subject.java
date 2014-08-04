package com.examw.test.domain.settings;

import java.io.Serializable;

/**
 * 考试科目。
 * @author yangyong.
 * @since 2014-08-02.
 */
public class Subject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,code,name;
	private Exam exam;
	private Area area;
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
	 * 获取所属考试。
	 * @return 所属考试。
	 */
	public Exam getExam() {
		return exam;
	}
	/**
	 * 设置所属考试。
	 * @param exam
	 * 所属考试。
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	/**
	 * 获取所属地区。
	 * @return 所属地区。
	 */
	public Area getArea() {
		return area;
	}
	/**
	 * 设置所属地区。
	 * @param area
	 * 所属地区。
	 */
	public void setArea(Area area) {
		this.area = area;
	}
}