package com.examw.test.model.records;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;
/**
 * 用户试卷记录信息。
 * 
 * @author yangyong
 * @since 2014年10月10日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserPaperRecordInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,userId,paperId,productId;
	private Integer status,terminalCode;
	private Long usedTime;
	private BigDecimal score;
	private Date createTime,lastTime;
	private Set<UserItemRecordInfo> items;
	/**
	 * 获取记录ID。
	 * @return 记录ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置记录ID。
	 * @param id 
	 *	 记录ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID。
	 * @param userId 
	 *	  用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取试卷ID。
	 * @return 试卷ID。
	 */
	public String getPaperId() {
		return paperId;
	}
	/**
	 * 设置试卷ID。
	 * @param paperId 
	 *	  试卷ID。
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	/**
	 * 获取所属产品ID。
	 * @return 所属产品ID。
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置所属产品ID。
	 * @param productId 
	 *	  所属产品ID。
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取用户终端类型代码。
	 * @return 用户终端类型代码。
	 */
	public Integer getTerminalCode() {
		return terminalCode;
	}
	/**
	 * 设置用户终端类型代码。
	 * @param terminalCode 
	 *	  用户终端类型代码。
	 */
	public void setTerminalCode(Integer terminalCode) {
		this.terminalCode = terminalCode;
	}
	/**
	 * 获取用户试卷状态。
	 * @return 用户试卷状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置用户试卷状态。
	 * @param status 
	 *	  用户试卷状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取用户试卷用时（秒）。
	 * @return 用户试卷用时（秒）。
	 */
	public Long getUsedTime() {
		return usedTime;
	}
	/**
	 * 设置用户试卷用时（秒）。
	 * @param usedTime 
	 *	  用户试卷用时（秒）。
	 */
	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}
	/**
	 * 获取用户得分。
	 * @return 用户得分。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置用户得分。
	 * @param score 
	 *	  用户得分。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using=CustomDateSerializer.LongDate.class)
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
	@JsonSerialize(using=CustomDateSerializer.LongDate.class)
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
	/**
	 * 获取试题记录集合。
	 * @return 试题记录集合。
	 */
	public Set<UserItemRecordInfo> getItems() {
		return items;
	}
	/**
	 * 设置试题记录集合。
	 * @param items 
	 *	  试题记录集合。
	 */
	public void setItems(Set<UserItemRecordInfo> items) {
		this.items = items;
	}
}