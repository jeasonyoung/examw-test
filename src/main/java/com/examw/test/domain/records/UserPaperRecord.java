package com.examw.test.domain.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.test.domain.library.Paper;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.products.SoftwareType;

/**
 * 用户试卷记录
 * @author fengwei.
 * @since 2014年9月17日 下午3:06:11.
 */
public class UserPaperRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private ProductUser user;
	private Paper paper;
	private Product product;
	private SoftwareType terminal;
	private Date createTime,lastTime;
	private Integer status,rightNum;
	private Long usedTime;
	private BigDecimal score;
	private Set<UserItemRecord> items;
	/**
	 * 获取用户试卷记录ID。
	 * @return 用户试卷记录ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置用户试卷记录ID。
	 * @param id 
	 *	  用户试卷记录ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取所属用户。
	 * @return 所属用户。
	 */
	public ProductUser getUser() {
		return user;
	}
	/**
	 * 设置所属用户。
	 * @param user 
	 *	  所属用户。
	 */
	public void setUser(ProductUser user) {
		this.user = user;
	}
	/**
	 * 获取所属试卷。
	 * @return 所属试卷。
	 */
	public Paper getPaper() {
		return paper;
	}
	/**
	 * 设置所属试卷。
	 * @param paper 
	 *	  所属试卷。
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * 获取所属产品。
	 * @return 所属产品。
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * 设置所属产品。
	 * @param product 
	 *	  所属产品。
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * 获取终端类型。
	 * @return 终端类型。
	 */
	public SoftwareType getTerminal() {
		return terminal;
	}
	/**
	 * 设置终端类型。
	 * @param terminal 
	 *	  终端类型。
	 */
	public void setTerminal(SoftwareType terminal) {
		this.terminal = terminal;
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
	/**
	 * 获取状态。
	 * @return 状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态。
	 * @param status 
	 *	  状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取使用时间（秒）。
	 * @return 使用时间（秒）。
	 */
	public Long getUsedTime() {
		return usedTime;
	}
	/**
	 * 设置使用时间（秒）。
	 * @param usedTime 
	 *	  使用时间（秒）。
	 */
	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}
	/**
	 * 获取考试得分。
	 * @return 考试得分。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置考试得分。
	 * @param score 
	 *	  考试得分。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取试题记录集合。
	 * @return 试题记录集合。
	 */
	public Set<UserItemRecord> getItems() {
		return items;
	}
	/**
	 * 设置试题记录集合。
	 * @param items 
	 *	  试题记录集合。
	 */
	public void setItems(Set<UserItemRecord> items) {
		this.items = items;
	}
	/**
	 * 获取 做对的题目个数
	 * @return rightNum
	 * 做对的题目个数
	 */
	public Integer getRightNum() {
		return rightNum;
	}
	/**
	 * 设置 做对的题目个数
	 * @param rightNum
	 * 做对的题目个数
	 */
	public void setRightNum(Integer rightNum) {
		this.rightNum = rightNum;
	}
	
}