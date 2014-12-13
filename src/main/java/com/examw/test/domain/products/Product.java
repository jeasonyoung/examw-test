package com.examw.test.domain.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;

/**
 * 产品。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,image,content;
	private Exam exam;
	private Area area;
	private BigDecimal price,discount;
	private Integer orderNo,status,analysisType,realType,paperTotal,itemTotal;
	private Set<Subject> subjects;
	private Set<Software> softwares;
	private Set<Registration> registrations;
	private Date createTime,lastTime;
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
	 * 获取宣传图片URL。
	 * @return 宣传图片URL。
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置宣传图片URL。
	 * @param image 
	 *	  宣传图片URL。
	 */
	public void setImage(String image) {
		this.image = image;
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
	 * 获取所属地区。
	 * @return 所属地区。
	 */
	public Area getArea() {
		return area;
	}
	/**
	 * 设置所属地区。
	 * @param area 
	 *	  所属地区。
	 */
	public void setArea(Area area) {
		this.area = area;
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
	 * 获取答案解析类型。
	 * @return 答案解析类型。
	 */
	public Integer getAnalysisType() {
		return analysisType;
	}
	/**
	 * 设置答案解析类型。
	 * @param analysisType 
	 *	  答案解析类型。
	 */
	public void setAnalysisType(Integer analysisType) {
		this.analysisType = analysisType;
	}
	/**
	 * 获取真题类型。
	 * @return 真题类型。
	 */
	public Integer getRealType() {
		return realType;
	}
	/**
	 * 设置真题类型。
	 * @param realType 
	 *	  真题类型。
	 */
	public void setRealType(Integer realType) {
		this.realType = realType;
	}
	/**
	 * 获取试卷总数。
	 * @return 试卷总数。
	 */
	public Integer getPaperTotal() {
		return paperTotal;
	}
	/**
	 * 设置试卷总数。
	 * @param paperTotal 
	 *	  试卷总数。
	 */
	public void setPaperTotal(Integer paperTotal) {
		this.paperTotal = paperTotal;
	}
	/**
	 * 获取试题总数。
	 * @return 试题总数。
	 */
	public Integer getItemTotal() {
		return itemTotal;
	}
	/**
	 * 设置试题总数。
	 * @param itemTotal 
	 *	  试题总数。
	 */
	public void setItemTotal(Integer itemTotal) {
		this.itemTotal = itemTotal;
	}
	/**
	 * 获取关联的软件集合。
	 * @return 关联的软件集合。
	 */
	public Set<Software> getSoftwares() {
		return softwares;
	}
	/**
	 * 设置关联的软件集合。
	 * @param softwares 
	 *	  关联的软件集合。
	 */
	public void setSoftwares(Set<Software> softwares) {
		this.softwares = softwares;
	}
	/**
	 * 获取关联的注册码集合。
	 * @return 关联的注册码集合。
	 */
	public Set<Registration> getRegistrations() {
		return registrations;
	}
	/**
	 * 设置关联的注册码集合。
	 * @param registrations 
	 *	  关联的注册码集合。
	 */
	public void setRegistrations(Set<Registration> registrations) {
		this.registrations = registrations;
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
}