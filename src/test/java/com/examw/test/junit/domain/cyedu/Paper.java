package com.examw.test.junit.domain.cyedu;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 试卷实体类。
 * @author lq.
 * @since 2014-09-01.
 */
public class Paper implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id,PaperID,ClassID,ExamTime,Soure,PaperType,Yaer;
	private String PaperName;
	private BigDecimal price;
	/**
	 * 获取ID。
	 * @return ID。
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置ID。
	 * @param id
	 * ID。
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取试卷ID。
	 * @return 试卷ID。
	 */
	public int getPaperID() {
		return PaperID;
	}
	/**
	 * 设置试卷ID。
	 * @param paperID
	 * 试卷ID。
	 */
	public void setPaperID(int paperID) {
		PaperID = paperID;
	}
	/**
	 * 获取科目ID。
	 * @return 科目ID。
	 */
	public int getClassID() {
		return ClassID;
	}
	/**
	 * 设置科目ID。
	 * @param classID
	 * 科目ID。
	 */
	public void setClassID(int classID) {
		ClassID = classID;
	}
	/**
	 * 获取试卷时间。
	 * @return 试卷时间。
	 */
	public int getExamTime() {
		return ExamTime;
	}
	/**
	 * 设置试卷时间。
	 * @param examTime
	 * 试卷时间。
	 */
	public void setExamTime(int examTime) {
		ExamTime = examTime;
	}
	/**
	 * 获取分数。
	 * @return 分数。
	 */
	public int getSoure() {
		return Soure;
	}
	/**
	 * 设置分数。
	 * @param soure
	 * 分数。
	 */
	public void setSoure(int soure) {
		Soure = soure;
	}
	/**
	 * 获取试卷类型。
	 * @return 试卷类型。
	 */
	public int getPaperType() {
		return PaperType;
	}
	/**
	 * 设置试卷类型。
	 * @param paperType
	 * 试卷类型。
	 */
	public void setPaperType(int paperType) {
		PaperType = paperType;
	}
	/**
	 * 获取日期。
	 * @return 日期。
	 */
	public int getYaer() {
		return Yaer;
	}
	/**
	 * 设置日期。
	 * @param yaer
	 * 日期。
	 */
	public void setYaer(int yaer) {
		Yaer = yaer;
	}
	/**
	 * 获取试卷名称。
	 * @return 试卷名称。
	 */
	public String getPaperName() {
		return PaperName;
	}
	/**
	 * 设置试卷名称。
	 * @param paperName
	 * 试卷名称。
	 */
	public void setPaperName(String paperName) {
		PaperName = paperName;
	}
	/**
	 * 获取价格。
	 * @return 价格。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置价格。
	 * @param price
	 * 价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}