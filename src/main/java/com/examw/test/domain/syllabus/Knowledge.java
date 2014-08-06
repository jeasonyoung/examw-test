package com.examw.test.domain.syllabus;

import java.io.Serializable;

/**
 * 知识点管理。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Knowledge implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code,content;
	private TextBook book;
	private Syllabus syllabus;
	/**
	 * 获取知识点ID。
	 * @return 知识点ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置知识点ID。
	 * @param id 
	 *	  知识点ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取知识点代码。
	 * @return 知识点代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置知识点代码。
	 * @param code 
	 *	  知识点代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取知识点内容。
	 * @return 知识点内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置知识点内容。
	 * @param content 
	 *	  知识点内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取所属教材。
	 * @return 所属教材。
	 */
	public TextBook getBook() {
		return book;
	}
	/**
	 * 设置所属教材。
	 * @param book 
	 *	 所属教材。
	 */
	public void setBook(TextBook book) {
		this.book = book;
	}
	/**
	 * 获取所属要点。
	 * @return 所属要点。
	 */
	public Syllabus getSyllabus() {
		return syllabus;
	}
	/**
	 * 设置所属要点。
	 * @param syllabus 
	 *	  所属要点。
	 */
	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}
}