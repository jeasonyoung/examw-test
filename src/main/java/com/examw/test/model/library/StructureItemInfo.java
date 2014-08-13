package com.examw.test.model.library;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.util.StringUtils;

import com.examw.model.Paging;
/**
 * 结构下题目信息。
 * 
 * @author yangyong
 * @since 2014年8月8日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class StructureItemInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,serial,structureId,content;
	private BigDecimal score;
	private Integer orderNo;
	private ItemScoreInfo item;
	private Date createTime;
	/**
	 * 获取结构下题目ID。
	 * @return 结构下题目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置结构下题目ID。
	 * @param id 
	 *	  结构下题目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试题题型。
	 * @return 试题类型。
	 */
	public Integer getType(){
		return (this.getItem() == null) ? null : this.getItem().getType();
	}
	/**
	 * 获取试题题型名称。
	 * @return
	 */
	public String getTypeName(){
		return (this.getItem() == null) ? null : this.getItem().getTypeName();
	}
	/**
	 * 获取所属结构ID。
	 * @return 所属结构ID。
	 */
	public String getStructureId() {
		return structureId;
	}
	/**
	 * 设置所属结构ID。
	 * @param structureId 
	 *	  所属结构ID。
	 */
	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}
	/**
	 * 获取结构题目序号。
	 * @return 结构题目序号。
	 */
	public String getSerial() {
		return this.serial;
	}
	/**
	 * 设置结构题目序号。
	 * @param serial
	 * 结构题目序号。
	 */
	public void setSerial(String serial){
		this.serial = serial;
	}
	/**
	 * 获取题目内容。
	 * @return 题目内容。
	 */
	public String getContent() {
		if(!StringUtils.isEmpty(this.content)) return this.content;
		if(this.item == null) return null;
		return this.item.getContent();
	}
	/**
	 * 设置题目内容。
	 * @param content
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取题目分数。
	 * @return 题目分数。
	 */
	public BigDecimal getScore() {
		return this.score;
	}
	/**
	 * 设置题目分数。
	 * @param score
	 * 题目分数。
	 */
	public void setScore(BigDecimal score){
		this.score = score;
	}
	/**
	 * 获取试题排序号。
	 * @return 试题排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置试题排序号。
	 * @param orderNo 
	 *	  试题排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取所属试题。
	 * @return 所属试题。
	 */
	public ItemScoreInfo getItem() {
		return item;
	}
	/**
	 * 设置所属试题。
	 * @param item 
	 *	  所属试题。
	 */
	public void setItem(ItemScoreInfo item) {
		this.item = item;
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