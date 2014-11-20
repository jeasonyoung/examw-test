package com.examw.test.model.syllabus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 教材信息。
 * @author lq.
 * @since 2014-08-07.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class BookInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String  id,name,description,pressId,pressName,examId,examName,subjectId,subjectName,statusName,lastUserId,lastUserName;
	private String[] areaId,areaName;
	private BigDecimal price,discount;
	private Integer status,orderNo;
	private Date createTime,lastTime;
	private Set<BookChapterInfo> chapters;
	//所属大纲信息 [2014.11.20]
	private String syllabusId,syllabusName;
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
	 *	  教材ID。
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
	 *	  教材名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取教材描述。
	 * @return 教材描述。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置教材描述。
	 * @param description 
	 *	  教材描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取所属出版社ID。
	 * @return 所属出版社ID。
	 */
	public String getPressId() {
		return pressId;
	}
	/**
	 * 设置所属出版社ID。
	 * @param pressId 
	 *	  所属出版社ID。
	 */
	public void setPressId(String pressId) {
		this.pressId = pressId;
	}
	/**
	 * 获取所属出版社名称。
	 * @return 所属出版社名称。
	 */
	public String getPressName() {
		return pressName;
	}
	/**
	 * 设置所属出版社名称。
	 * @param pressName 
	 *	  所属出版社名称。
	 */
	public void setPressName(String pressName) {
		this.pressName = pressName;
	}
	/**
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId 
	 *	  所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属考试名称。
	 * @return 所属考试名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属考试名称。
	 * @param examName 
	 *	  所属考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置所属科目ID。
	 * @param subjectId 
	 *	  所属科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取所属科目名称。
	 * @return 所属科目名称。
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置所属科目名称。
	 * @param subjectName 
	 *	  所属科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	 * 获取所属地区ID。
	 * @return 所属地区ID。
	 */
	public String[] getAreaId() {
		return areaId;
	}
	/**
	 * 设置所属地区ID。
	 * @param areaId 
	 *	  所属地区ID。
	 */
	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取所属地区名称。
	 * @return 所属地区名称。
	 */
	public String[] getAreaName() {
		return areaName;
	}
	/**
	 * 设置所属地区名称。
	 * @param areaName
	 *	  所属地区名称。
	 */
	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
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
	 *	  教材价格。
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
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param 状态名称。 
	 *	  statusName
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	 * 获取章节集合。
	 * @return 章节集合。
	 */
	public Set<BookChapterInfo> getChapters() {
		return chapters;
	}
	/**
	 * 设置章节集合。
	 * @param chapters 
	 *	  章节集合。
	 */
	public void setChapters(Set<BookChapterInfo> chapters) {
		this.chapters = chapters;
	}
	/**
	 * 获取 大纲ID
	 * @return syllabusId
	 * 大纲ID
	 */
	public String getSyllabusId() {
		return syllabusId;
	}
	/**
	 * 设置 大纲ID
	 * @param syllabusId
	 * 大纲ID
	 */
	public void setSyllabusId(String syllabusId) {
		this.syllabusId = syllabusId;
	}
	/**
	 * 获取 大纲名称
	 * @return syllabusName
	 * 大纲名称
	 */
	public String getSyllabusName() {
		return syllabusName;
	}
	/**
	 * 设置 大纲名称
	 * @param syllabusName
	 * 大纲名称
	 */
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}
	
}