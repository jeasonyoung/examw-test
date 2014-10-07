package com.examw.test.model.library;

import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 题目信息基类。
 * 
 * @author yangyong
 * @since 2014年8月30日
 */
public abstract class BaseItemInfo<T extends BaseItemInfo<T>>  extends Paging implements Comparable<BaseItemInfo<T>> {
	private static final long serialVersionUID = 1L;
	private String pid,id,typeName,statusName,optName,examId,examName,subjectId,subjectName, 
			content,answer,analysis,checkCode,sourceId,sourceName,areaId,areaName;
	private Integer type,level,year,opt,status,orderNo,count;
	private Date createTime,lastTime;
	/**
	 * 获取父题目ID。
	 * @return 父题目ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置父题目ID。
	 * @param pid 
	 *	  父题目ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
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
	 * 获取题型名称。
	 * @return 题型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置题型名称。
	 * @param typeName 
	 *	  题型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName 
	 *	  状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取所属考试科目ID。
	 * @return 所属考试科目ID。
	 */
	public String getSubjectId() {
		return subjectId;
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
	 * 设置所属考试科目ID。
	 * @param subjectId 
	 *	  所属考试科目ID。
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取所属考试科目名称。
	 * @return 所属考试科目名称
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置所属考试科目名称。
	 * @param subjectName 
	 *	  所属考试科目名称。
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	 * 获取来源ID。
	 * @return 来源ID。
	 */
	public String getSourceId() {
		return sourceId;
	}
	/**
	 * 设置来源ID。
	 * @param sourceId 
	 *	  来源ID。
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	/**
	 * 获取来源名称。
	 * @return 来源名称。
	 */
	public String getSourceName() {
		return sourceName;
	}
	/**
	 * 设置来源名称。
	 * @param sourceName 
	 *	  来源名称。
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	/**
	 * 获取所属地区ID。
	 * @return 所属地区ID。
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * 设置所属地区ID。
	 * @param areaId 
	 *	  所属地区ID。
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取所属地区名称。
	 * @return 所属地区名称
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * 设置所属地区名称
	 * @param areaName 
	 *	  所属地区名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	 * 获取类型名称。
	 * @return 类型名称。
	 */
	public String getOptName() {
		return optName;
	}
	/**
	 * 设置类型名称。
	 * @param optName 
	 *	  类型名称。
	 */
	public void setOptName(String optName) {
		this.optName = optName;
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
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
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
	@JsonSerialize(using = CustomDateSerializer.class)
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
	 * 获取子题目集合。
	 * @return 子题目集合。
	 */
	public abstract Set<T> getChildren();
	/**
	 * 设置子题目集合。
	 * @param children 
	 *	  子题目集合。
	 */
	public abstract void setChildren(Set<T> children);
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(BaseItemInfo<T> o) {
		if(this == o) return 0;
		int index = this.getOrderNo() - o.getOrderNo();
		if(index == 0){
			index = this.getContent().compareTo(o.getContent());
			if(index == 0){
				index = this.getCheckCode().compareTo(o.getCheckCode());
				if(index == 0){
					index = this.getId().compareTo(o.getId());
				}
			}
		}
		return index;
	}
}