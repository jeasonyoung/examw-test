package com.examw.test.domain.settings;

import java.io.Serializable;
import java.util.Set;

/**
 * 考试。
 * @author yangyong.
 * @since 2014-08-01
 */
public class Exam implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code,name,abbr;
	private Category category;
	private Area area;
	private Set<Subject> subjects;
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试ID。
	 * @param id
	 * 考试ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试代码。
	 * @return 考试代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置考试代码。
	 * @param code
	 * 考试代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取考试名称。
	 * @return 考试名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试名称。
	 * @param name
	 * 考试名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取EN简称。
	 * @return EN简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置EN简称。
	 * @param abbr
	 * EN简称。
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取所属类别。
	 * @return 所属类别。
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * 设置所属类别。
	 * @param category
	 * 所属类别。
	 */
	public void setCategory(Category category) {
		this.category = category;
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
	/**
	 * 获取科目集合。
	 * @return 科目集合。
	 */
	public Set<Subject> getSubjects() {
		return subjects;
	}
	/**
	 * 设置科目集合。
	 * @param subjects
	 * 科目集合。
	 */
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
}