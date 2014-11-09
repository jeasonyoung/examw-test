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
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.library.ItemParser;
import com.examw.test.service.library.ItemType;
/**
 * 试题解析基础类。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public class DefaultItemParser implements ItemParser {
	private static final Logger logger = Logger.getLogger(DefaultItemParser.class);
	protected IItemDao itemDao;
	private String typeName;
	/**
	 * 构造函数。
	 * @param typeName
	 * 题型名称。
	 */
	public DefaultItemParser(String typeName){
		this.typeName = typeName;
	}
	/**
	 * 设置试题数据接口。
	 * @param itemDao 
	 *	  试题数据接口。
	 */
	public void setItemDao(IItemDao itemDao) {
		this.itemDao = itemDao;
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
	 * 试题解析。
	 * @see com.examw.test.service.library.ItemParser#parser(com.examw.test.model.library.BaseItemInfo, java.util.Set, com.examw.test.domain.library.Item)
	 */
	@Override
	public void parser(BaseItemInfo<?> source,Item target) {
		if(logger.isDebugEnabled()) logger.debug("试题解析：BaseItemInfo => Item ...");
		if(source == null || target == null) return;
		source.setCount(this.calculationCount(source));
		this.changeModel(source,target);
		this.checkItemAnswers(target);
	}
	/**
	 * 检查试题答案。
	 * @param target
	 */
	protected void checkItemAnswers(Item target){
		if(target == null) return;
		ItemType itemType = ItemType.convert(target.getType());
		if((itemType == ItemType.SHARE_TITLE) || (itemType == ItemType.SHARE_ANSWER)){
			return;
		}
		if(StringUtils.isEmpty(target.getAnswer())){
			throw new RuntimeException("试题未有答案！");
		}
		if((itemType == ItemType.SINGLE) &&  (target.getAnswer().indexOf(",") > -1)){
			throw new RuntimeException("单选题答案只能有一个答案！");
		}
		if((itemType == ItemType.MULTY) && (target.getAnswer().indexOf(",") == -1)){
			throw new RuntimeException("多选题答案应有不只一个答案！");
		}
	}
	/**
	 * 计算包含的试题数量。
	 * @param source
	 * @return
	 */
	public Integer calculationCount(BaseItemInfo<?> source){
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
	protected boolean changeModel(BaseItemInfo<?> source,Item target){
		if(source == null || target == null) return false;
		if(target.getChildren() != null && target.getChildren().size() > 0){
			target.getChildren().clear();
		}
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(source.getChildren() != null && source.getChildren().size() > 0){
			if(target.getChildren() == null) target.setChildren(new HashSet<Item>());
			for(BaseItemInfo<?> info : source.getChildren()){
				if(info == null) continue;
				Item item = StringUtils.isEmpty(info.getId()) ? null : this.itemDao.load(Item.class, info.getId());
				if(item == null){
					if(StringUtils.isEmpty(info.getId())){
						info.setId(UUID.randomUUID().toString());
					}
					item = new Item();
				}
				item.setParent(target);
				if(this.changeModel(info, item)){
					target.getChildren().add(item);
				}
			}
		}
		return true;
	}
	/*
	 * 试题数据模型转换。
	 * @see com.examw.test.service.library.ItemParser#conversion(com.examw.test.domain.library.Item, com.examw.test.model.library.BaseItemInfo, boolean)
	 */
	@Override
	public void conversion(Item source, BaseItemInfo<?> target,boolean isAll){
		if(logger.isDebugEnabled()) logger.debug(String.format("解析试题:Item => BaseItemInfo<?> ［%s］ ...", target == null ? "" : target.getClass()));
		if(source == null || target == null) return;
		this.conversionModel(source, target, isAll);
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
	}
	/**
	 * 模型转换辅助方法。
	 * @param source
	 * @param target
	 * @param isAll
	 */
	protected void conversionModel(Item source, BaseItemInfo<?> target,boolean isAll) {
		try {
			if(target instanceof ItemInfo){
				changeModel(source, ((ItemInfo)target), isAll);
			}else if(target instanceof StructureItemInfo) {
				changeModel(source, ((StructureItemInfo)target), isAll);
			}
		} catch (Exception e) {
			String err = String.format("试题数据模型转换［%1$s => %2$s］异常：%3$s", source.getClass(), target.getClass(), e.getMessage());
			logger.error(err);
			e.printStackTrace();
			throw new RuntimeException(err, e);
		}
	}
	/**
	 * 数据模型转换。
	 * @param source
	 * @param target
	 * @param isAll
	 */
	@SuppressWarnings("unchecked")
	protected <T extends BaseItemInfo<T>> void changeModel(Item source, T target, boolean isAll) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Item => T...");
		if(source == null || target == null) return;
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(isAll && source.getChildren() != null && source.getChildren().size() > 0){
			Set<T> children = new TreeSet<>();
			for(Item item : source.getChildren()){
				if(item == null) continue;
				T info = (T)target.getClass().newInstance();
				if(info != null){
					this.changeModel(item, info, isAll);
					info.setPid(target.getId());
					children.add(info);
				}
			}
			if(children.size() > 0) target.setChildren(children);
		}
	}
	
}