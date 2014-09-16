package com.examw.test.junittest;

/**
 * 材料分析题实体类
 * @author lq.
 * @since 2014-09-01.
 */
public class CaiLianFenXi {
	private String id,classId,content,cexamId;
	/**
	 * 获取ID.
	 * @return ID.
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置ID.
	 * @param id
	 * ID.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取科目ID.
	 * @return 科目ID.
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
	/**
	 * 获取题目。
	 * @return 题目。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置题目。
	 * @param content
	 * 题目。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取考试ID.
	 * @return 考试ID.
	 */
	public String getCexamId() {
		return cexamId;
	}
	/**
	 * 设置考试ID.
	 * @param cexamId
	 * 考试ID.
	 */
	public void setCexamId(String cexamId) {
		this.cexamId = cexamId;
	}
}