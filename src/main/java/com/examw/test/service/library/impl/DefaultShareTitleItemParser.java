package com.examw.test.service.library.impl;

import com.examw.test.model.library.BaseItemInfo;

/**
 * 共享提干题默认解析器。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultShareTitleItemParser extends DefaultItemParser {
	/**
	 * 构造函数。
	 * @param typeName
	 */
	public DefaultShareTitleItemParser(String typeName) {
		super(typeName);
	}
	/*
	 * 计算题目数。
	 * @see com.examw.test.service.library.impl.DefaultItemParser#calculationCount(com.examw.test.model.library.BaseItemInfo)
	 */
	@Override
	public Integer calculationCount(BaseItemInfo<?> source) {
		if(source == null || source.getChildren() == null) return super.calculationCount(source);
		return source.getChildren().size();
	}
}