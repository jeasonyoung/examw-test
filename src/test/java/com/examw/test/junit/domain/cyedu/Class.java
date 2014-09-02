package com.examw.test.junit.domain.cyedu;

import java.io.Serializable;
/**
 * 科目实体类。
 * @author lq.
 * @since 2014-09-01.
 */
public class Class implements Serializable{
	private static final long serialVersionUID = 1L;
	private int ID,ClassID;
	private String ClassName,ClassEName,ParentID;
	/**
	 * 获取ID。
	 * @return ID。
	 */
	public int getID() {
		return ID;
	}
	/**
	 * 设置ID。
	 * @param iD
	 * ID。
	 */
	public void setID(int iD) {
		ID = iD;
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
	 * 获取科目名称。
	 * @return  科目名称。
	 */
	public String getClassName() {
		return ClassName;
	}
	/**
	 * 设置科目名称。
	 * @param className 
	 * 科目名称。
	 */
	public void setClassName(String className) {
		ClassName = className;
	}
	/**
	 * 获取科目简称。
	 * @return 科目简称。
	 */
	public String getClassEName() {
		return ClassEName;
	}
	/**
	 * 设置科目简称。
	 * @param classEName
	 * 科目简称。
	 */
	public void setClassEName(String classEName) {
		ClassEName = classEName;
	}
	/**
	 * 获取上级ID。
	 * @return 上级ID。
	 */
	public String getParentID() {
		return ParentID;
	}
	/**
	 * 设置上级ID。
	 * @param parentID
	 * 上级ID。
	 */
	public void setParentID(String parentID) {
		ParentID = parentID;
	}
}