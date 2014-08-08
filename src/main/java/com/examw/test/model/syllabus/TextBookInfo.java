package com.examw.test.model.syllabus;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 教材信息。
 * @author lq.
 * @since 2014-08-07.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TextBookInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,subId,subName,pressId,pressName,examId,examName;
	private BigDecimal price;
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
	 * 教材ID。
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
	 * 教材名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getSubId() {
		return subId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subId
	 * 所属科目ID。
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}
	/**
	 * 获取所属科目名称。
	 * @return 所属科目名称。
	 */
	public String getSubName() {
		return subName;
	}
	/**
	 * 设置所属科目名称。
	 * @param subName
	 * 所属科目名称。
	 */
	public void setSubName(String subName) {
		this.subName = subName;
	}
	/**
	 * 获取出版社ID。
	 * @return 出版社ID。
	 */
	public String getPressId() {
		return pressId;
	}
	/**
	 * 设置出版社ID。
	 * @param pressId
	 * 出版社ID。
	 */
	public void setPressId(String pressId) {
		this.pressId = pressId;
	}
	/**
	 * 获取出版社名称。
	 * @return
	 * 出版社名称。
	 */
	public String getPressName() {
		return pressName;
	}
	/**
	 * 设置出版社名称。
	 * @param pressName
	 * 出版社名称。
	 */
	public void setPressName(String pressName) {
		this.pressName = pressName;
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
	 * 教材价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取所属考试设置ID。
	 * @return 所属考试设置ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试设置ID。
	 * @param examId
	 * 所属考试设置ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属考试设置名称。 
	 * @return 所属考试设置名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属考试设置名称。
	 * @param examName
	 * 所属考试设置名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
}