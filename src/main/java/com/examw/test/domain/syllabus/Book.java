package com.examw.test.domain.syllabus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;

/**
 * 教材课本。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description,lastUserId,lastUserName;
	private Press press;
	private BigDecimal price,discount;
	private Subject subject;
	private Set<Area> areas;
	private Set<BookChapter> chapters;
	private Integer status,orderNo;
	private Date createTime,lastTime;
	//所采用的考试大纲 Add by FW 2014.11.20 
	private Syllabus syllabus;
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
	 *	教材ID。
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
	 *	教材名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取描述信息。
	 * @return 描述信息。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述信息。
	 * @param description 
	 *	  描述信息。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取所属出版社。
	 * @return 所属出版社。
	 */
	public Press getPress() {
		return press;
	}
	/**
	 * 设置所属出版社。
	 * @param press 
	 *	  所属出版社。
	 */
	public void setPress(Press press) {
		this.press = press;
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
	 *	 教材价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取优惠价格。
	 * @return 优惠价格。
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	/**
	 * 设置优惠价格。
	 * @param discount 
	 *	  优惠价格。
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	/**
	 * 获取所属科目。
	 * @return 所属科目。
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置所属科目。
	 * @param subject 
	 *	 所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取所属地区集合。
	 * @return 所属地区集合。
	 */
	public Set<Area> getAreas() {
		return areas;
	}
	/**
	 * 设置所属地区集合。
	 * @param areas 
	 *	  所属地区集合。
	 */
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}
	/**
	 * 获取章节集合。
	 * @return 章节集合。
	 */
	public Set<BookChapter> getChapters() {
		return chapters;
	}
	/**
	 * 设置章节集合。
	 * @param chapters 
	 *	  章节集合。
	 */
	public void setChapters(Set<BookChapter> chapters) {
		this.chapters = chapters;
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
	 * 获取最后修改用户ID。
	 * @return 最后修改用户ID。
	 */
	public String getLastUserId() {
		return lastUserId;
	}
	/**
	 * 设置最后修改用户ID。
	 * @param lastUserId 
	 *	  最后修改用户ID。
	 */
	public void setLastUserId(String lastUserId) {
		this.lastUserId = lastUserId;
	}
	/**
	 * 获取最后修改用户名。
	 * @return 最后修改用户名。
	 */
	public String getLastUserName() {
		return lastUserName;
	}
	/**
	 * 设置最后修改用户名。
	 * @param lastUserName 
	 *	  最后修改用户名。
	 */
	public void setLastUserName(String lastUserName) {
		this.lastUserName = lastUserName;
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
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取 所采用的考试大纲
	 * @return syllabus
	 * 所采用的考试大纲
	 */
	public Syllabus getSyllabus() {
		return syllabus;
	}
	/**
	 * 设置 所采用的考试大纲
	 * @param syllabus
	 * 所采用的考试大纲
	 */
	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}
	
	
}