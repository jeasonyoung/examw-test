package com.examw.test.service.library.impl;

import com.examw.test.domain.library.Item;
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemType;

/**
 * 共享答案题解析器。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultShareAnswerItemParser extends DefaultItemParser {
	private IItemService itemService;
	/**
	 * 构造函数。
	 * @param typeName
	 */
	public DefaultShareAnswerItemParser(String typeName) {
		super(typeName);
	}
	/**
	 * 设置试题服务接口。
	 * @param itemService 
	 *	  试题服务接口。
	 */
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
	/*
	 * 计算试题数据。
	 * @see com.examw.test.service.library.impl.DefaultShareTitleItemParser#calculationCount(com.examw.test.model.library.BaseItemInfo)
	 */
	@Override
	public Integer calculationCount(BaseItemInfo<?> source) {
		if(source == null || source.getChildren() == null) return super.calculationCount(source);
		int max_order = 0, count = 1;
		for(BaseItemInfo<?> info : source.getChildren()){
			if(info == null) continue;
			if(info.getOrderNo() > max_order){
				max_order = info.getOrderNo();
				count = (info.getChildren() == null ? count : info.getChildren().size());
			}
		}
		return count;
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.library.impl.DefaultItemParser#conversion(com.examw.test.domain.library.Item, com.examw.test.model.library.BaseItemInfo)
	 */
	@Override
	public void conversion(Item source, BaseItemInfo<?> target) {
		super.conversion(source, target);
		if(target.getType() == ItemType.SHARE_ANSWER.getValue() && target.getChildren() != null && target.getChildren().size() > 0){
			int max_order = 0;
			BaseItemInfo<?> itemInfo = null;
			for(BaseItemInfo<?> info : target.getChildren()){
				if(info == null) continue;
				if(info.getOrderNo() > max_order){
					max_order = info.getOrderNo();
					 itemInfo = info;
				}
			}
			if(itemInfo != null && itemInfo.getChildren() != null && itemInfo.getChildren().size() > 0){
				for(BaseItemInfo<?> info : itemInfo.getChildren()){
					if(info == null || info.getType() == null) continue;
					info.setTypeName(this.itemService.loadTypeName(info.getType()));
				}
			}
		}
	}
}