package com.examw.test.domain.library;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.domain.syllabus.Syllabus;

/**
 * 试题。
 * @author yangyong.
 * @since 2014-08-02.
 */
public class Item implements Serializable,Comparable<Item> {
	private static final long serialVersionUID = 1L;
	private String id,content,answer,analysis,checkCode,userId,userName;
	private Integer type,level,year,opt,status,orderNo,count;
	private Subject subject;
	private Source source;
	private Area area;
	private Date createTime,lastTime;
	private Item parent;
	private Set<Item> children;
	private Set<Syllabus> syllabuses;
	private Set<StructureItem> structures;
	/**
	 * 获取题目ID。
	 * @return 题目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置题目ID。
	 * @param id 
	 * 	题目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取内容。
	 * @return 内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置内容。
	 * @param content 
	 * 内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取试题数目。
	 * @return 试题数目。
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置试题数目。
	 * @param count 
	 *	  试题数目。
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取答案。
	 * @return 答案。
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置答案。
	 * @param answer
	 * 答案。
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取分析。
	 * @return 分析。
	 */
	public String getAnalysis() {
		return analysis;
	}
	/**
	 * 设置分析。
	 * @param analysis 
	 * 分析。
	 */
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	/**
	 * 获取验证码。
	 * @return 验证码。
	 */
	public String getCheckCode() {
		return checkCode;
	}
	/**
	 * 设置验证码。
	 * @param checkCode 
	 * 验证码。
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	/**
	 * 获取题型。
	 * @return 题型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置题型。
	 * @param type 
	 * 题型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取难度值。
	 * @return 难度值。
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置难度值。
	 * @param level 
	 * 难度值。
	 */
	public void setLevel(Integer level) {
		this.level = level;
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
	 * 获取类型。
	 * @return 类型。
	 */
	public Integer getOpt() {
		return opt;
	}
	/**
	 * 设置类型。
	 * @param opt 
	 * 类型。
	 */
	public void setOpt(Integer opt) {
		this.opt = opt;
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
	 * 状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 获取题目来源。
	 * @return 题目来源。
	 */
	public Source getSource() {
		return source;
	}
	/**
	 * 设置题目来源。
	 * @param source
	 * 题目来源。
	 */
	public void setSource(Source source) {
		this.source = source;
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
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
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
	 * 设置最后修改时间。
	 * @param lastTime 
	 * 最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取父题目。
	 * @return 父题目。
	 */
	public Item getParent() {
		return parent;
	}
	/**
	 * 设置父题目。
	 * @param parent 
	 * 父题目。
	 */
	public void setParent(Item parent) {
		this.parent = parent;
	}
	/**
	 * 获取子题目集合。
	 * @return 子题目集合。
	 */
	public Set<Item> getChildren() {
		return children;
	}
	/**
	 * 设置子题目集合。
	 * @param children 
	 * 子题目集合。
	 */
	public void setChildren(Set<Item> children) {
		this.children = children;
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
	 * 排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取所属大纲要点集合。
	 * @return 所属大纲要点集合。
	 */
	public Set<Syllabus> getSyllabuses() {
		return syllabuses;
	}
	/**
	 * 设置所属大纲要点集合。
	 * @param syllabuses 
	 *	  所属大纲要点集合。
	 */
	public void setSyllabuses(Set<Syllabus> syllabuses) {
		this.syllabuses = syllabuses;
	}
	/**
	 * 获取所属的结构集合。
	 * @return 所属的结构集合。
	 */
	public Set<StructureItem> getStructures() {
		return structures;
	}
	/**
	 * 设置所属的结构集合。
	 * @param structures 
	 *	  所属的结构集合。
	 */
	public void setStructures(Set<StructureItem> structures) {
		this.structures = structures;
	}
	/**
	 * 获取所属用户ID。
	 * @return 所属用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置所属用户ID。
	 * @param userId 
	 *	  所属用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取所属用户名称。
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置所属用户名称。
	 * @param userName 
	 *	  所属用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*
	 * 重载排序
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Item o) {
		if(o == null) return 0;
		return this.getOrderNo() - o.getOrderNo();
	}
}