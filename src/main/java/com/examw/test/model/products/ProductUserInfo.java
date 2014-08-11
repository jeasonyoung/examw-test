package com.examw.test.model.products;

import com.examw.model.IPaging;
import com.examw.test.domain.products.ProductUser;

/**
 * 产品用户信息
 * @author fengwei.
 * @since 2014年8月11日 下午3:25:08.
 */
public class ProductUserInfo extends ProductUser implements IPaging{
	private static final long serialVersionUID = 1L;
	private String statusName;
	
	/**
	 * 获取 状态名称
	 * @return statusName
	 * 
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 状态名称
	 * @param statusName
	 * 
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/*
	 * 获取每页数据量
	 * @see com.examw.model.IPaging#getRows()
	 */
	@Override
	public Integer getRows() {
		
		return null;
	}
	/*
	 * 设置每页数据量。
	 * @see com.examw.model.IPaging#setRows(java.lang.Integer)
	 */
	@Override
	public void setRows(Integer rows) {
	}
	
	/*
	 * 获取页码。
	 * @see com.examw.model.IPaging#getPage()
	 */
	@Override
	public Integer getPage() {
		
		return null;
	}
	/*
	 * 设置页码。
	 * @see com.examw.model.IPaging#setPage(java.lang.Integer)
	 */
	@Override
	public void setPage(Integer page) {
	}
	
	/*
	 * 获取排序字段名称。
	 * @see com.examw.model.IPaging#getSort()
	 */
	@Override
	public String getSort() {
		
		return null;
	}
	
	/*
	 * 设置排序字段名称。
	 * @see com.examw.model.IPaging#setSort(java.lang.String)
	 */
	@Override
	public void setSort(String sort) {
	}
	
	/*
	 * 获取排序方式。
	 * @see com.examw.model.IPaging#getOrder()
	 */
	@Override
	public String getOrder() {
		
		return null;
	}
	
	/*
	 * 设置排序方式。
	 * @see com.examw.model.IPaging#setOrder(java.lang.String)
	 */
	@Override
	public void setOrder(String order) {
	}

}
