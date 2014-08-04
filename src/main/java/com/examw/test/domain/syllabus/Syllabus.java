package com.examw.test.domain.syllabus;

import java.io.Serializable;
import java.util.Set;

import com.examw.test.domain.settings.Subject;

/**
 * 考试大纲。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Syllabus implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code,title;
	private Subject subject;
	private Syllabus parent;
	private Set<Syllabus> children;
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
	 *	要点ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取要点代码。
	 * @return 要点代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置要点代码。
	 * @param code 
	 *	 要点代码。
	 */
	public void setCode(String code) {
		this.code = code;
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
	 *	要点。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取所属科目。
	 * @return 所属科目。
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置所属科目。
	 * @param subject 
	 *	所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取上级要点。
	 * @return 上级要点。
	 */
	public Syllabus getParent() {
		return parent;
	}
	/**
	 * 设置上级要点。
	 * @param parent 
	 *	上级要点。
	 */
	public void setParent(Syllabus parent) {
		this.parent = parent;
	}
	/**
	 * 获取子要点集合。
	 * @return 子要点集合。
	 */
	public Set<Syllabus> getChildren() {
		return children;
	}
	/**
	 * 设置子要点集合。
	 * @param children 
	 *	子要点集合。
	 */
	public void setChildren(Set<Syllabus> children) {
		this.children = children;
	}
}