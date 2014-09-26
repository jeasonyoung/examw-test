package com.examw.test.model.front;

import java.util.List;

import com.examw.test.model.products.ProductInfo;

/**
 * 前台产品信息
 * @author fengwei.
 * @since 2014年9月25日 上午11:31:14.
 */
public class FrontProductInfo extends ProductInfo{
	private static final long serialVersionUID = 1L;
	private List<FrontSubjectInfo> subjectList;
	/**
	 * 获取 包含科目集合
	 * @return subjectList
	 * 包含科目集合
	 */
	public List<FrontSubjectInfo> getSubjectList() {
		return subjectList;
	}
	/**
	 * 设置 包含科目集合
	 * @param subjectList
	 * 包含科目集合
	 */
	public void setSubjectList(List<FrontSubjectInfo> subjectList) {
		this.subjectList = subjectList;
	}
	
}
