package com.examw.test.service.library.impl;

import com.examw.test.model.library.BaseItemInfo;

/**
 * 共享答案题解析器。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultShareAnswerItemParser extends DefaultShareTitleItemParser {
	/**
	 * 构造函数。
	 * @param typeName
	 */
	public DefaultShareAnswerItemParser(String typeName) {
		super(typeName);
	}
	/*
	 * 计算试题数据。
	 * @see com.examw.test.service.library.impl.DefaultShareTitleItemParser#calculationCount(com.examw.test.model.library.BaseItemInfo)
	 */
	@Override
	protected Integer calculationCount(BaseItemInfo<?> source) {
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
}