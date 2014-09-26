package com.examw.test.service.library.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.StructureShareItemScore;
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.ItemParser;
/**
 * 试题解析基础类。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultItemParser implements ItemParser {
	private static final Logger logger = Logger.getLogger(DefaultItemParser.class);
	private String typeName;
	private IItemDao itemDao;
	/**
	 * 构造函数。
	 * @param typeName
	 * 题型名称。
	 */
	public DefaultItemParser(String typeName){
		this.typeName = typeName;
	}
	/*
	 *  获取题型名称。
	 * @see com.examw.test.service.library.ItemParser#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return this.typeName;
	}
	/*
	 * 设置试题数据接口。
	 * @see com.examw.test.service.library.ItemParser#setItemDao(com.examw.test.dao.library.IItemDao)
	 */
	@Override
	public void setItemDao(IItemDao itemDao) {
		this.itemDao = itemDao;
	}
	/**
	 * 获取试题数据接口。
	 * @return
	 */
	protected IItemDao getItemDao(){
		return this.itemDao;
	}
	/*
	 * 试题解析。
	 * @see com.examw.test.service.library.ItemParser#parser(com.examw.test.model.library.BaseItemInfo, java.util.Set, com.examw.test.domain.library.Item)
	 */
	@Override
	public void parser(BaseItemInfo<?> source,Set<StructureShareItemScore> shareItemScores, Item target) {
		if(logger.isDebugEnabled()) logger.debug("试题解析：BaseItemInfo => Item ...");
		if(source == null || target == null) return;
		source.setCount(this.calculationCount(source));
		this.changeModel(source,shareItemScores,target);
	}
	/**
	 * 计算试题数量。
	 * @param source
	 * @return
	 */
	protected Integer calculationCount(BaseItemInfo<?> source){
		return 1;
	}
	/**
	 * 试题数据模型转换。
	 * @param source
	 * 源数据模型。
	 * @param shareItemScores
	 * 共享题集合。
	 * @param target
	 * 目标数据模型。
	 * @return
	 */
	protected boolean changeModel(BaseItemInfo<?> source,Set<StructureShareItemScore> shareItemScores,Item target){
		if(source == null || target == null) return false;
		if(target.getChildren() != null && target.getChildren().size() > 0){
			target.getChildren().clear();
		}
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(source.getChildren() != null && source.getChildren().size() > 0){
			if(target.getChildren()== null) target.setChildren(new HashSet<Item>());
			for(BaseItemInfo<?> info : source.getChildren()){
				if(info == null) continue;
				Item item = (StringUtils.isEmpty(info.getId()) || this.getItemDao() == null) ? null : this.getItemDao().load(Item.class, info.getId());
				if(item == null){
					item = new Item();
					item.setId(UUID.randomUUID().toString());
				}
				item.setParent(target);
				if(this.changeModel(info, shareItemScores,item)){
					target.getChildren().add(item);
				}
			}
		}
		return true;
	}
	/*
	 * 解析试题。
	 * @see com.examw.test.service.library.ItemParser#parser(com.examw.test.domain.library.Item, com.examw.test.model.library.BaseItemInfo)
	 */
	@Override
	public ItemInfo parser(Item source) {
		if(logger.isDebugEnabled()) logger.debug("解析试题:Item => ItemInfo ...");
		if(source == null) return null;
		ItemInfo target = this.changeModel(source);
		if(target == null) return null;
		//所属科目
		if(source.getSubject() != null){
			target.setSubjectId(source.getSubject().getId());
			target.setSubjectName(source.getSubject().getName());
			//所属考试
			if(source.getSubject().getExam() != null){
				target.setExamId(source.getSubject().getExam().getId());
				target.setExamName(source.getSubject().getExam().getName());
			}
		}
		//所属来源
		if(source.getSource() != null){
			target.setSourceId(source.getSource().getId());
			target.setSourceName(source.getSource().getName());
		}
		//所属地区
		if(source.getArea() != null){
			target.setAreaId(source.getArea().getId());
			target.setAreaName(source.getArea().getName());
		}
		return target;
	}
	/**
	 * 数据模型转换。
	 * @param source
	 * @return
	 */
	protected ItemInfo changeModel(Item source){
		if(source == null) return null;
		ItemInfo target = new ItemInfo();
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(source.getChildren() != null && source.getChildren().size() > 0){
			Set<ItemInfo> children = new TreeSet<>();
			for(Item item : source.getChildren()){
				ItemInfo info = this.changeModel(item);
				if(info != null){
					info.setPid(target.getId());
					children.add(info);
				}
			}
			if(children.size() > 0) target.setChildren(children);
		}
		return target;
	}
}