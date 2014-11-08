package com.examw.test.service.library.impl;

import org.apache.log4j.Logger;

import com.examw.test.domain.library.Item;
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemType;

/**
 * 共享提干题默认解析器。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultShareTitleItemParser extends DefaultItemParser {
	private static final Logger logger = Logger.getLogger(DefaultShareTitleItemParser.class);
	private IItemService itemService;
	/**
	 * 构造函数。
	 * @param typeName
	 * 题型名称。
	 */
	public DefaultShareTitleItemParser(String typeName) {
		super(typeName);
	}
	/**
	 * 设置试题服务接口。
	 * @param itemService 
	 *	  试题服务接口。
	 */
	public void setItemService(IItemService itemService) {
		if(logger.isDebugEnabled()) logger.debug("注入试题服务接口...");
		this.itemService = itemService;
	}
	/*
	 * 计算试题数目。
	 * @see com.examw.test.service.library.impl.DefaultItemParser#calculationCount(com.examw.test.model.library.BaseItemInfo)
	 */
	@Override
	public Integer calculationCount(BaseItemInfo<?> source) {
		if(logger.isDebugEnabled()) logger.debug("计算试题数目...");
		if(source == null || source.getChildren() == null){
			return super.calculationCount(source);
		}
		return source.getChildren().size();
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.library.impl.DefaultItemParser#conversion(com.examw.test.domain.library.Item, com.examw.test.model.library.BaseItemInfo, boolean)
	 */
	@Override
	public void conversion(Item source, BaseItemInfo<?> target,boolean isAll) {
		if(logger.isDebugEnabled()) logger.debug(String.format("题型数据模型转换［%s］Item =>  BaseItemInfo<?>  ...", isAll));
		super.conversion(source, target,isAll);
		if(isAll && target.getType().equals(ItemType.SHARE_TITLE.getValue()) && target.getChildren() != null && target.getChildren().size() > 0){
			for(BaseItemInfo<?> info : target.getChildren()){
				if(info == null || info.getType() == null) continue;
				info.setTypeName(this.itemService.loadTypeName(info.getType()));
			}
		}
	}
}