package com.examw.test.model.library;

import java.math.BigDecimal;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * 题目分数信息。
 * <pre>
 *  继承于题目信息，用于结构题目下。
 * </pre>
 * @author yangyong
 * @since 2014年8月13日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ItemScoreInfo extends BaseItemInfo<ItemScoreInfo> {
	private static final long serialVersionUID = 1L;
	private String serial;
	private BigDecimal score;
	private Set<ItemScoreInfo> children;
	/**
	 * 获取题号。
	 * @return 题号。
	 */
	public String getSerial() {
		return serial;
	}
	/**
	 * 设置题号。
	 * @param serial 
	 *	  题号。
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	/**
	 * 获取试题分数。
	 * @return 试题分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置试题分数。
	 * @param score 
	 *	  试题分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/*
	 * 获取子题目集合。
	 * @see com.examw.test.model.library.BaseItemInfo#getChildren()
	 */
	@Override
	public Set<ItemScoreInfo> getChildren() {
		return this.children;
	}
	/*
	 * 设置子题目集合。
	 * @see com.examw.test.model.library.BaseItemInfo#setChildren(java.util.Set)
	 */
	@Override
	public void setChildren(Set<ItemScoreInfo> children) {
		this.children = children;
	}
}