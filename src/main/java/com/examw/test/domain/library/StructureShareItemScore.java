package com.examw.test.domain.library;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *共享题型分数。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class StructureShareItemScore implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,serial;
	private BigDecimal score;
	private StructureItem structureItem;
	private Item subItem;
	/**
	 * 获取共享题ID。
	 * @return 共享题ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置共享题ID。
	 * @param id 
	 *	 共享题ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取共享题序号。
	 * @return 共享题序号。
	 */
	public String getSerial() {
		return serial;
	}
	/**
	 * 设置共享题序号。
	 * @param serial 
	 *	  共享题序号。
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	/**
	 * 获取共享题分数。
	 * @return 共享题分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置共享题分数。
	 * @param score 
	 *	  共享题分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取所属结构题目。
	 * @return 所属结构题目。
	 */
	public StructureItem getStructureItem() {
		return structureItem;
	}
	/**
	 * 设置所属结构题目。
	 * @param structureItem 
	 *	  所属结构题目。
	 */
	public void setStructureItem(StructureItem structureItem) {
		this.structureItem = structureItem;
	}
	/**
	 * 获取共享子题目。
	 * @return 共享子题目。
	 */
	public Item getSubItem() {
		return subItem;
	}
	/**
	 * 设置共享子题目。
	 * @param subItem 
	 *	  共享子题目。
	 */
	public void setSubItem(Item subItem) {
		this.subItem = subItem;
	}
}