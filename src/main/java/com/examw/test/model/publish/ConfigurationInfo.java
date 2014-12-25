package com.examw.test.model.publish;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;

/**
 * 发布配置信息。
 * 
 * @author yangyong
 * @since 2014年12月23日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfigurationInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,statusName;
	private Integer status,categoriesCount,examsCount,productsCount;
	private Date createTime,lastTime;
	/**
	 * 获取发布配置ID。
	 * @return 发布配置ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置发布配置ID。
	 * @param id 
	 *	  发布配置ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取发布配置名称。
	 * @return 发布配置名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置发布配置名称。
	 * @param name 
	 *	  发布配置名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取发布状态。
	 * @return 发布状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置发布状态。
	 * @param status 
	 *	  发布状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取发布状态名称。
	 * @return 发布状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置发布状态名称。
	 * @param statusName 
	 *	  发布状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取考试类别数量。
	 * @return 考试类别数量。
	 */
	public Integer getCategoriesCount() {
		return categoriesCount;
	}
	/**
	 * 设置考试类别数量。
	 * @param categoriesCount 
	 *	  考试类别数量。
	 */
	public void setCategoriesCount(Integer categoriesCount) {
		this.categoriesCount = categoriesCount;
	}
	/**
	 * 获取考试数量。
	 * @return 考试数量。
	 */
	public Integer getExamsCount() {
		return examsCount;
	}
	/**
	 * 设置考试数量。
	 * @param examsCount 
	 *	  考试数量。
	 */
	public void setExamsCount(Integer examsCount) {
		this.examsCount = examsCount;
	}
	/**
	 * 获取产品数量。
	 * @return 产品数量。
	 */
	public Integer getProductsCount() {
		return productsCount;
	}
	/**
	 * 设置产品数量。
	 * @param productsCount 
	 *	  产品数量。
	 */
	public void setProductsCount(Integer productsCount) {
		this.productsCount = productsCount;
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
}