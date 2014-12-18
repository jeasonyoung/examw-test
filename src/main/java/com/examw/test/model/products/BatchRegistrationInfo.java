package com.examw.test.model.products;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 批量注册码信息。
 * 
 * @author yangyong
 * @since 2014年12月18日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class BatchRegistrationInfo extends BaseRegistrationInfo {
	private static final long serialVersionUID = 1L;
	private Integer count;
	/**
	 * 获取创建注册码个数。
	 * @return 创建注册码个数。
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置创建注册码个数。
	 * @param count 
	 *	  创建注册码个数。
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
}