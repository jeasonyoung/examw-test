package com.examw.test.domain.syllabus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import com.examw.test.domain.settings.Subject;

/**
 * 教材课本。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class TextBook implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private BigDecimal price;
	private Subject subject;
	private Press press;
	private Set<Knowledge> knowledges;
	/**
	 * 获取教材ID。
	 * @return 教材ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置教材ID。
	 * @param id 
	 *	教材ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取教材名称。
	 * @return 教材名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置教材名称。
	 * @param name 
	 *	教材名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取教材价格。
	 * @return 教材价格。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置教材价格。
	 * @param price 
	 *	 教材价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
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
	 *	 所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取所属出版社。
	 * @return 所属出版社。
	 */
	public Press getPress() {
		return press;
	}
	/**
	 * 设置所属出版社。
	 * @param press 
	 *	  所属出版社。
	 */
	public void setPress(Press press) {
		this.press = press;
	}
	/**
	 * 获取知识点集合。
	 * @return 知识点集合。
	 */
	public Set<Knowledge> getKnowledges() {
		return knowledges;
	}
	/**
	 * 设置知识点集合。
	 * @param knowledges 
	 *	  知识点集合。
	 */
	public void setKnowledges(Set<Knowledge> knowledges) {
		this.knowledges = knowledges;
	}
}