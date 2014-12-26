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
	private String id,name,statusName,templateName;
	private Integer status,categoriesCount,examsCount,productsCount;
	private Integer[] templates;
	private String[] categoryId,categoryName,examId,examName,productId,productName;
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
	 * 获取模版值。
	 * @return 模版值。
	 */
	public Integer[] getTemplates() {
		return templates;
	}
	/**
	 * 设置模版值。
	 * @param template 
	 *	  模版值。
	 */
	public void setTemplates(Integer[] templates) {
		this.templates = templates;
	}
	/**
	 * 获取模版名称。
	 * @return 模版名称。
	 */
	public String getTemplateName() {
		return templateName;
	}
	/**
	 * 设置模版名称。
	 * @param templateName 
	 *	  模版名称。
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	/**
	 * 获取考试类别ID集合。
	 * @return 考试类别ID集合。
	 */
	public String[] getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置考试类别ID集合。
	 * @param categoryId 
	 *	  考试类别ID集合。
	 */
	public void setCategoryId(String[] categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取考试类别名称集合。
	 * @return 考试类别名称集合。
	 */
	public String[] getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置考试类别名称集合。
	 * @param categoryName 
	 *	  考试类别名称集合。
	 */
	public void setCategoryName(String[] categoryName) {
		this.categoryName = categoryName;
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
	 * 获取考试ID集合。
	 * @return 考试ID集合。
	 */
	public String[] getExamId() {
		return examId;
	}
	/**
	 * 设置考试ID集合。
	 * @param examId 
	 *	  考试ID集合。
	 */
	public void setExamId(String[] examId) {
		this.examId = examId;
	}
	/**
	 * 获取考试名称集合。
	 * @return 考试名称集合。
	 */
	public String[] getExamName() {
		return examName;
	}
	/**
	 * 设置考试名称集合。
	 * @param examName 
	 *	  考试名称集合。
	 */
	public void setExamName(String[] examName) {
		this.examName = examName;
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
	 * 获取产品ID集合。
	 * @return 产品ID集合。
	 */
	public String[] getProductId() {
		return productId;
	}
	/**
	 * 设置产品ID集合。
	 * @param productId 
	 *	  产品ID集合。
	 */
	public void setProductId(String[] productId) {
		this.productId = productId;
	}
	/**
	 * 获取产品名称集合。
	 * @return 产品名称集合。
	 */
	public String[] getProductName() {
		return productName;
	}
	/**
	 * 设置产品名称集合。
	 * @param productName 
	 *	  产品名称集合。
	 */
	public void setProductName(String[] productName) {
		this.productName = productName;
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