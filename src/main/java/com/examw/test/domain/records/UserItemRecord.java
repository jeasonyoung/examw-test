package com.examw.test.domain.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.examw.test.domain.library.Structure;
import com.examw.test.domain.products.SoftwareType;

/**
 * 用户试题记录。
 * @author fengwei.
 * @since 2014年9月17日 下午3:35:58.
 */
public class UserItemRecord implements Serializable,Comparable<UserItemRecord> {
	private static final long serialVersionUID = 1L;
	private String id,itemId,itemContent,answer;
	private UserPaperRecord paperRecord;
	private Structure structure;
	private SoftwareType terminal;
	private Integer status;
	private Long usedTime;
	private BigDecimal score;
	private Date createTime,lastTime;
	/**
	 * 获取试题记录ID。
	 * @return 试题记录ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试题记录ID。
	 * @param id 
	 *	  试题记录ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试题ID。
	 * @return 试题ID。
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置试题ID。
	 * @param itemId 
	 *	  试题ID。
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取试题内容JSON。
	 * @return 试题内容JSON。
	 */
	public String getItemContent() {
		return itemContent;
	}
	/**
	 * 设置试题内容JSON。
	 * @param itemContent 
	 *	  试题内容JSON。
	 */
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	/**
	 * 获取用户答案。
	 * @return 用户答案。
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置用户答案。
	 * @param answer 
	 *	  用户答案。
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取所属试卷记录。
	 * @return 所属试卷记录。
	 */
	public UserPaperRecord getPaperRecord() {
		return paperRecord;
	}
	/**
	 * 设置所属试卷记录。
	 * @param paperRecord
	 *	  所属试卷记录。
	 */
	public void setPaperRecord(UserPaperRecord paperRecord) {
		this.paperRecord = paperRecord;
	}
	/**
	 * 获取所属试卷结构。
	 * @return 所属试卷结构。
	 */
	public Structure getStructure() {
		return structure;
	}
	/**
	 * 设置所属试卷结构。
	 * @param structure 
	 *	  所属试卷结构。
	 */
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	/**
	 * 获取所属终端类型。
	 * @return 所属终端类型。
	 */
	public SoftwareType getTerminal() {
		return terminal;
	}
	/**
	 * 设置所属终端类型。
	 * @param terminal 
	 *	  所属终端类型。
	 */
	public void setTerminal(SoftwareType terminal) {
		this.terminal = terminal;
	}
	/**
	 * 获取试题状态。
	 * @return 试题状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置试题状态。
	 * @param status 
	 *	  试题状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取答题用时（秒）。
	 * @return 答题用时（秒）。
	 */
	public Long getUsedTime() {
		return usedTime;
	}
	/**
	 * 设置答题用时（秒）。
	 * @param usedTime 
	 *	  答题用时（秒）。
	 */
	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}
	/**
	 * 获取得分。
	 * @return 得分。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置得分。
	 * @param score 
	 *	  得分。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
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
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime 
	 *	  最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(UserItemRecord o) {
		if(this == o) return 0;
		int index = 0;
		if(this.getLastTime() != null && o.getLastTime() != null){
			index = (int)(this.getLastTime().getTime() - o.getLastTime().getTime());
		}
		if((index == 0) && (this.getCreateTime() != null && o.getCreateTime() != null)){
			index = (int)(this.getCreateTime().getTime() - o.getCreateTime().getTime());
		}
		if(index == 0){
			index = this.getId().compareToIgnoreCase(o.getId());
		}
		return index;
	}
}