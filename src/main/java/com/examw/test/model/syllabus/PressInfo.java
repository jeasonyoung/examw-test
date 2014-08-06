package com.examw.test.model.syllabus;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 出版社信息。
 * @author lq.
 * @since 2014-08-06.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PressInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name;
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
}