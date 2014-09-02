package com.examw.test.junit.domain.cyedu;

import java.io.Serializable;
/**
 * 地区实体类。
 * @author lq.
 * @since 2014-09-01.
 */
public class Diqu implements Serializable{
	private static final long serialVersionUID = 1L;
	private int ID,DID;
	private String DCName,DEName;
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
	 * 获取ID。
	 * @return
	 * ID。
	 */
	public int getDID() {
		return DID;
	}
	/**
	 * 设置ID。
	 * @param dID
	 * ID。
	 */
	public void setDID(int dID) {
		DID = dID;
	}
	/**
	 * 获取地区名称。
	 * @return 地区名称。
	 */
	public String getDCName() {
		return DCName;
	}
	/**
	 * 设置地区名称。
	 * @param dCName 地区名称。
	 */
	public void setDCName(String dCName) {
		DCName = dCName;
	}
	/**
	 * 获取英文名称。
	 * @return 英文名称。
	 */
	public String getDEName() {
		return DEName;
	}
	/**
	 * 设置英文名称。
	 * @param dEName
	 * 英文名称。
	 */
	public void setDEName(String dEName) {
		DEName = dEName;
	}
}