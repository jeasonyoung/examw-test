package com.examw.test.domain.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;

/**
 * 产品。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code,name,content;
	private Exam exam;
	private Set<Subject> subjects;
	private BigDecimal price,discount;
	private Date createTime,lastTime;
	private Integer status;
	private Set<RelationProduct> relations;
	/**
	 * 状态－正常。
	 */
	public static final int STATUS_NONE = 0;
	/**
	 * 状态－删除。
	 */
	public static final int STATUS_DELETE = -1;
	/**
	 * 获取产品ID。
	 * @return 产品ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置产品ID。
	 * @param id 
	 *	 产品ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取产品代码。
	 * @return 产品代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置产品代码。
	 * @param code 
	 *	  产品代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取产品名称。
	 * @return 产品名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置产品名称。
	 * @param name 
	 *	  产品名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取产品介绍。
	 * @return 产品介绍。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置产品介绍。
	 * @param content 
	 *	  产品介绍。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取所属考试。
	 * @return 所属考试。
	 */
	public Exam getExam() {
		return exam;
	}
	/**
	 * 设置所属考试。
	 * @param exam 
	 *	  所属考试。
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	/**
	 * 获取包含科目集合。
	 * @return 包含科目集合。
	 */
	public Set<Subject> getSubjects() {
		return subjects;
	}
	/**
	 * 设置包含科目集合。
	 * @param subjects 
	 *	  包含科目集合。
	 */
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	/**
	 * 获取定价。
	 * @return 定价。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置定价。
	 * @param price 
	 *	 定价。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取优惠价。
	 * @return 优惠价。
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	/**
	 * 设置优惠价。
	 * @param discount 
	 *	  优惠价。
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
	 * 获取更新时间。
	 * @return 更新时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置更新时间。
	 * @param lastTime 
	 *	  更新时间。
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
	 * 获取被关联集合。
	 * @return 被关联集合。
	 */
	public Set<RelationProduct> getRelations() {
		return relations;
	}
	/**
	 * 设置被关联集合。
	 * @param relations 
	 *	  被关联集合。
	 */
	public void setRelations(Set<RelationProduct> relations) {
		this.relations = relations;
	}
}