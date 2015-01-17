package com.examw.test.service.publish;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布配置模版枚举
 * 
 * @author yangyong
 * @since 2014年12月28日
 */
public enum ConfigurationTemplateType {
	/**
	 * 空。
	 */
	NONE(0x0000),
	/**
	 * 首页。
	 */
	HOME(0x0001),
	/**
	 * 考试页面。
	 */
	EXAM(0x0002),
	/**
	 * 产品页面。
	 */
	PRODUCT(0x0004),
	/**
	 * 试卷列表页面。
	 */
	PAPERLIST(0x0008),
	/**
	 * 试卷详情页面。
	 */
	PAPERDETAIL(0x0010),
	/**
	 * 常用问题列表页面。
	 */
	QUESTIONLIST(0x0020),
	/**
	 * 常用问题编辑页面。
	 */
	QUESTIONDETAIL(0x0040),
	/**
	 * 最新试卷列表页面。
	 */
	NEWSPAPERLIST(0x0080),
	/**
	 * 最热试卷列表页面。
	 */
	HOTSPAPERLIST(0x0100);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 * 枚举值。
	 */
	private ConfigurationTemplateType(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return 枚举值。
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 枚举转换。
	 * @param value
	 * 枚举值。
	 * @return 枚举对象。
	 */
	public static final ConfigurationTemplateType[] convert(int value){
		List<ConfigurationTemplateType> list = new ArrayList<>();
		for(ConfigurationTemplateType type : ConfigurationTemplateType.values()){
			if(type == ConfigurationTemplateType.NONE) continue;
			if((type.getValue() & value) == type.getValue()){
				list.add(type);
			}
		}
		return list.toArray(new ConfigurationTemplateType[0]);
	}
}