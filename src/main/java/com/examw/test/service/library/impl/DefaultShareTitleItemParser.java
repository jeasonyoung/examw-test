package com.examw.test.service.library.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.StructureShareItemScore;
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.model.library.ItemScoreInfo;

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
	protected Integer calculationCount(BaseItemInfo<?> source) {
		if(source == null || source.getChildren() == null) return super.calculationCount(source);
		return source.getChildren().size();
	}
	/*
	 * 试题解析。
	 * @see com.examw.test.service.library.impl.DefaultItemParser#changeModel(com.examw.test.model.library.BaseItemInfo, java.util.Set, com.examw.test.domain.library.Item)
	 */
	@Override
	protected boolean changeModel(BaseItemInfo<?> source,Set<StructureShareItemScore> shareItemScores, Item target) {
		if(source == null || target == null) return false;
		if(target.getChildren() != null && target.getChildren().size() > 0){
			target.getChildren().clear();
		}
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if((source instanceof ItemScoreInfo) && shareItemScores != null){
			ItemScoreInfo itemScoreInfo  = (ItemScoreInfo)source;
			if(!StringUtils.isEmpty(itemScoreInfo.getSerial())){
				StructureShareItemScore shareItemScore = new StructureShareItemScore();
				//shareItemScore.setId(MD5Util.MD5(String.format("%1$s-%2$s", structureItem.getId(), target.getId())));
				shareItemScore.setId( target.getId());
				shareItemScore.setSerial(itemScoreInfo.getSerial());
				shareItemScore.setScore(itemScoreInfo.getScore());
				//shareItemScore.setStructureItem(structureItem);
				shareItemScore.setSubItem(target);
				shareItemScores.add(shareItemScore);
			}
		}
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
}