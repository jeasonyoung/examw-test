package com.examw.test.domain.library;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.examw.test.domain.settings.Subject;

/**
 * 试卷。
 * @author yangyong.
 * @since 2014-08-02.
 */
public class Paper implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description;
	private Integer type,status,price,time,year;
	private BigDecimal score;
	private Subject subject;
	private Source source;
	private Date createTime,lastTime,publishTime;
	//private Set<Structure> structures;
	/**
	 * 类型－真题。
	 */
	public static final int TYPE_REAL = 1;
	/**
	 * 类型－模拟题。
	 */
	public static final int TYPE_SIMU = 2;
	/**
	 * 类型－预测题。
	 */
	public static final int TYPE_FORECAST = 3;
	/**
	 * 类型－练习题。
	 */
	public static final int TYPE_PRACTICE = 4;
	 
	/**
	 * 状态－未审核。
	 */
	public static final int STATUS_NONE = 0;
	/**
	 * 状态－已审核。
	 */
	public static final int STATUS_AUDIT = 1;
	/**
	 * 状态－已发布。
	 */
	public static final int STATUS_PUBLISH = 2;
	/**
	 * 获取试卷ID。
	 * @return 试卷ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置试卷ID。
	 * @param id
	 * 试卷ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取试卷名称。
	 * @return 试卷名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置试卷名称。
	 * @param name
	 * 试卷名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取试卷描述。
	 * @return 试卷描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置试卷描述。
	 * @param description 
	 *	  试卷描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取试卷类型。
	 * @return 试卷类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置试卷类型。
	 * @param type
	 * 试卷类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取试卷状态。
	 * @return 试卷状态。
	 */ 
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置试卷状态。
	 * @param status
	 * 试卷状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取试卷价格。
	 * @return
	 */
	public Integer getPrice() {
		return price;
	}
	/**
	 * 设置试卷价格。
	 * @param price
	 * 试卷价格。
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}
	/**
	 * 获取试卷总分。
	 * @return 试卷总分。
	 */
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置试卷总分。
	 * @param score
	 * 试卷总分。
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取考试时长。
	 * @return 考试时长。
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置考试时长。
	 * @param time
	 * 考试时长。
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取使用年份。
	 * @return 使用年份。
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * 设置使用年份。
	 * @param year
	 * 使用年份。
	 */
	public void setYear(Integer year) {
		this.year = year;
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
	 * 所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/**
	 * 获取试卷来源。
	 * @return 试卷来源。
	 */
	public Source getSource() {
		return source;
	}
	/**
	 * 设置试卷来源。
	 * @param source
	 * 试卷来源。
	 */
	public void setSource(Source source) {
		this.source = source;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 *  设置创建时间。
	 * @param createTime
	 * 创建时间。
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
	 *  设置最后修改时间。
	 * @param lastTime
	 * 最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取发布时间。
	 * @return 发布时间。
	 */
	public Date getPublishTime() {
		return publishTime;
	}
	/**
	 * 设置发布时间。
	 * @param publishTime
	 * 发布时间。
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}