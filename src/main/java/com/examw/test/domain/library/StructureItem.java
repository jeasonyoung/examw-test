package com.examw.test.domain.library;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 结构下的题目。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class StructureItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,serial;
	private BigDecimal score;
	private Integer orderNo;
	private Structure structure;
	private Item item;
	private Set<StructureShareItemScore> shareItemScores;
	private Date createTime;
	/**
	 * 获取结构题目ID。
	 * @return 结构题目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置结构题目ID。
	 * @param id 
	 *	 结构题目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取结构题目序号。
	 * @return 结构题目序号。
	 */
	public String getSerial() {
		return serial;
	}
	/**
	 * 设置结构题目序号。
	 * @param serial 
	 *	  结构题目序号。
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	/**
	 * 获取结构题目分数。
	 * @return 结构题目分数。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置结构题目分数。
	 * @param score 
	 *	结构题目分数。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取排序号
	 * @return 排序号
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号
	 * @param orderNo 
	 *	排序号
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取所属结构。
	 * @return 所属结构。
	 */
	public Structure getStructure() {
		return structure;
	}
	/**
	 * 设置所属结构。
	 * @param structure 
	 *	 所属结构。
	 */
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	/**
	 * 获取所属题目。
	 * @return 所属题目。
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * 设置所属题目。
	 * @param item 
	 *	所属题目。
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	/**
	 * 获取共享题子题目分数集合。
	 * @return 共享题子题目分数集合。
	 */
	public Set<StructureShareItemScore> getShareItemScores() {
		return shareItemScores;
	}
	/**
	 * 设置共享题子题目分数集合。
	 * @param shareItemScores 
	 *	共享题子题目分数集合。
	 */
	public void setShareItemScores(Set<StructureShareItemScore> shareItemScores) {
		this.shareItemScores = shareItemScores;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime 
	 *	  创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}