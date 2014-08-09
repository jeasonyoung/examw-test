package com.examw.test.model.syllabus;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import com.examw.model.Paging;
/**
 * 知识点信息。
 * @author lq.
 * @since 2014-08-07.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class KnowledgeInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,code,content,bookId,syllId,syllName,cateId,cateName;
	/**
	 * 设置知识点ID。
	 * @return 知识点ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 获取知识点ID。
	 * @param id
	 * 知识点ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取知识的代码。
	 * @return 知识的代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置知识的代码。
	 * @param code
	 * 知识的代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取知识点内容。
	 * @return
	 * 知识点内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置知识点内容。
	 * @param content
	 * 知识点内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取所属教材ID。
	 * @return 所属教材ID。
	 */
	public String getBookId() {
		return bookId;
	}
	/**
	 * 设置所属教材ID。
	 * @param booId
	 * 所属教材ID。
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	/**
	 * 获取所属大纲ID。
	 * @return 所属大纲ID。
	 */
	public String getSyllId() {
		return syllId;
	}
	/**
	 * 设置所属大纲ID。
	 * @param syllId
	 * 所属大纲ID。
	 */
	public void setSyllId(String syllId) {
		this.syllId = syllId;
	}
	/**
	 * 获取大纲名称。
	 * @return 大纲名称。
	 */
	public String getSyllName() {
		return syllName;
	}
	/**
	 * 设置大纲名称。
	 * @param syllName
	 * 大纲名称。
	 */
	public void setSyllName(String syllName) {
		this.syllName = syllName;
	}
	/**
	 * 所属考试类别ID。
	 * @return 考试类别ID。
	 */
	public String getCateId() {
		return cateId;
	}
	/**
	 * 设置考试类别ID。
	 * @param cateId
	 * 考试类别ID。
	 */
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	/**
	 * 获取考试类别名称。
	 * @return 考试类别名称。
	 * 
	 */
	public String getCateName() {
		return cateName;
	}
	/**
	 * 设置考试类别名称。
	 * @param cateName
	 * 考试类别名称。
	 */
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}	
}