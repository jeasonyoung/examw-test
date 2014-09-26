package com.examw.test.model.library;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 前端试卷摘要信息。
 * 
 * @author yangyong
 * @since 2014年9月26日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class FrontPaperInfo extends BasePaperInfo {
	private static final long serialVersionUID = 1L;
	private Integer total;
	/**
	 * 获取试题总数。
	 * @return 试题总数。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置试题总数。
	 * @param total 
	 *	  试题总数。
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
}