package com.examw.test.domain.syllabus;

import java.io.Serializable;
import java.util.Set;
/**
 * 出版社。
 * @author yangyong.
 * @see 2014-08-04.
 */
public class Press implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Set<Book> books;
	/**
	 * 获取出版社ID。
	 * @return 出版社ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置出版社ID。
	 * @param id 
	 *	出版社ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取出版社名称。
	 * @return 出版社名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置出版社名称。
	 * @param name 
	 *	出版社名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取教材集合。
	 * @return 教材集合。
	 */
	public Set<Book> getBooks() {
		return books;
	}
	/**
	 * 设置教材集合。
	 * @param books 
	 *	  教材集合。
	 */
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
}