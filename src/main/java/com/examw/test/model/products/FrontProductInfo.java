package com.examw.test.model.products;
/**
 * 前端产品信息。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public class FrontProductInfo extends ProductInfo {
	private static final long serialVersionUID = 1L;
	private Integer paperCount,itemCount;
	private Boolean hasRealItem; //是否有真题
	/**
	 * 获取试卷数量。
	 * @return 试卷数量。
	 */
	public Integer getPaperCount() {
		return paperCount;
	}
	/**
	 * 设置试卷数量。
	 * @param paperCount 
	 *	  试卷数量。
	 */
	public void setPaperCount(Integer paperCount) {
		this.paperCount = paperCount;
	}
	/**
	 * 获取试题数量。
	 * @return 试题数量。
	 */
	public Integer getItemCount() {
		return itemCount;
	}
	/**
	 * 设置试题数量。
	 * @param itemCount 
	 *	  试题数量。
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	/**
	 * 获取是否有真题。
	 * @return 是否有真题。
	 */
	public Boolean getHasRealItem() {
		return hasRealItem;
	}
	/**
	 * 设置是否有真题。
	 * @param hasRealItem 
	 *	  是否有真题。
	 */
	public void setHasRealItem(Boolean hasRealItem) {
		this.hasRealItem = hasRealItem;
	}
}