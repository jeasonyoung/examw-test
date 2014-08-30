package com.examw.test.service.library.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.dao.library.ISourceDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.Source;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.domain.library.StructureShareItemScore;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.model.library.ItemScoreInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.IItemDuplicateCheck;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemStatus;
import com.examw.utils.MD5Util;

/**
 * 题目服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月8日
 */
public class ItemServiceImpl extends BaseDataServiceImpl<Item, ItemInfo> implements IItemService {
	private static final Logger logger = Logger.getLogger(ItemServiceImpl.class);
	private IItemDao itemDao;
	private ISubjectDao subjectDao;
	private ISourceDao sourceDao;
	private IItemDuplicateCheck itemDuplicateCheck;
	private Map<Integer, String> typeMap,statusMap,optMap,judgeAnswerMap;
	/**
	 * 设置题目数据访问接口。
	 * @param itemDao 
	 *	  题目数据访问接口。
	 */
	public void setItemDao(IItemDao itemDao) {
		if(logger.isDebugEnabled()) logger.debug("注入题目数据访问接口...");
		this.itemDao = itemDao;
	}
	/**
	 * 设置考试科目数据接口。
	 * @param subjectDao 
	 *	  考试科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置来源数据接口。
	 * @param sourceDao 
	 *	 来源数据接口。
	 */
	public void setSourceDao(ISourceDao sourceDao) {
		this.sourceDao = sourceDao;
	}
	/**
	 * 设置题目去重复校验码生成算法接口。
	 * @param itemDuplicateCheck 
	 *	  题目去重复校验码生成算法接口。
	 */
	public void setItemDuplicateCheck(IItemDuplicateCheck itemDuplicateCheck) {
		this.itemDuplicateCheck = itemDuplicateCheck;
	}
	/**
	 * 设置题型名称集合。
	 * @param typeMap 
	 *	  题型名称集合。
	 */
	public void setTypeMap(Map<Integer, String> typeMap) {
		if(logger.isDebugEnabled()) logger.debug("注入题型名称集合...");
		this.typeMap = typeMap;
	}
	/**
	 * 设置状态名称集合。
	 * @param statusMap 
	 *	  statusMap
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态名称集合...");
		this.statusMap = statusMap;
	}
	/**
	 * 设置类型名称集合。
	 * @param optMap 
	 *	  类型名称集合。
	 */
	public void setOptMap(Map<Integer, String> optMap) {
		if(logger.isDebugEnabled()) logger.debug("注入类型名称集合...");
		this.optMap = optMap;
	}
	/**
	 * 设置判断题答案名称集合。
	 * @param judgeAnswerMap 
	 *	  判断题答案名称集合。
	 */
	public void setJudgeAnswerMap(Map<Integer, String> judgeAnswerMap) {
		this.judgeAnswerMap = judgeAnswerMap;
	}
	/*
	 * 加载题型名称。
	 * @see com.examw.test.service.library.IItemService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载题型［type = %d］名称...", type));
		if(type == null || this.typeMap == null) return null;
		return this.typeMap.get(type);
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.test.service.library.IItemService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态［status = %d］名称...", status));
		if(status == null || this.statusMap == null) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 加载类型名称。
	 * @see com.examw.test.service.library.IItemService#loadOptName(java.lang.Integer)
	 */
	@Override
	public String loadOptName(Integer opt) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载类型［opt = %d］名称...", opt));
		if(opt == null || this.optMap == null) return null;
		return this.optMap.get(opt);
	}
	/*
	 * 加载判断题答案名称。
	 * @see com.examw.test.service.library.IItemService#loadAnswerJudgeName(java.lang.Integer)
	 */
	@Override
	public String loadJudgeAnswerName(Integer answer) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载判断题答案［answer = %d］名称...", answer));
		if(answer == null || this.judgeAnswerMap == null) return null;
		return this.judgeAnswerMap.get(answer);
	}
	/*
	 * 更新题目状态。
	 * @see com.examw.test.service.library.IItemService#updateStatus(com.examw.test.service.library.ItemStatus)
	 */
	@Override
	public void updateStatus(String itemId,ItemStatus status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("更新题目［％1$s］状态为［status = %2$s］..", itemId, status));
		Item item = this.itemDao.load(Item.class, itemId);
		if(item != null){
			if(item.getStatus() != status.getValue()) {
				item.setStatus(status.getValue());
				if(logger.isDebugEnabled()) logger.debug(String.format("%1$d => %2$d ", item.getStatus(), status.getValue()));
			}
		}
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Item> find(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.itemDao.findItems(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ItemInfo changeModel(Item data) {
		if(logger.isDebugEnabled()) logger.debug("类型转换...");
		if(data == null) return null;
		ItemInfo info = new ItemInfo();
		BeanUtils.copyProperties(data, info, new String[]{"children"});
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if(data.getSource() != null){
			info.setSourceId(data.getSource().getId());
			info.setSourceName(data.getSource().getName());
		}
		if(data.getType() != null) info.setTypeName(this.loadTypeName(data.getType()));
		if(data.getStatus() != null) info.setStatusName(this.loadStatusName(data.getStatus()));
		if(data.getOpt() != null) info.setOptName(this.loadOptName(data.getOpt()));
		
		if(data.getChildren() != null && data.getChildren().size() > 0){
			Set<ItemInfo> children = new TreeSet<ItemInfo>(new Comparator<ItemInfo>(){
				@Override
				public int compare(ItemInfo o1, ItemInfo o2) {
					return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(Item item : data.getChildren()){
				ItemInfo itemInfo = this.changeModel(item);
				if(itemInfo != null){
					itemInfo.setPid(info.getId());
					children.add(itemInfo);
				}
			}
			if(children.size() > 0) info.setChildren(children);
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.itemDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ItemInfo update(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		return this.changeModel(this.updateItem(info, null, null));
	}
	/*
	 * 更新题目。
	 * @see com.examw.test.service.library.IItemService#updateItem(com.examw.test.model.library.ItemInfo)
	 */
	@Override
	public Item updateItem(BaseItemInfo<?> info,StructureItem structureItem,Set<StructureShareItemScore> shareItemScores) {
		if(logger.isDebugEnabled()) logger.debug("更新题目...");
		if(info == null) return null;
		boolean isAdded = false;
		Item data = StringUtils.isEmpty(info.getId()) ? null : this.itemDao.load(Item.class, info.getId());
		String checkCode = this.itemDuplicateCheck.computeCheckCode(info);
		if(StringUtils.isEmpty(checkCode)){
			String msg = "计算验证码为空！更新试题终止！";
			if(logger.isDebugEnabled())logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(data == null) data = this.itemDao.loadItem(checkCode);
		if(data != null && data.getStatus() == ItemStatus.AUDIT.getValue()){
			return data;
		}
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			info.setStatus(ItemStatus.NONE.getValue());
			data = new Item();
		}
		if(!isAdded){
			info.setId(data.getId());
			info.setCreateTime(data.getCreateTime());
		}
		info.setCheckCode(checkCode);
		info.setLastTime(new Date());
		this.changeModel(info, data,structureItem,shareItemScores);
		if(isAdded)this.itemDao.save(data);
		return data;
	}
	//类型转换(ItemInfo => Item)。
	private boolean changeModel(BaseItemInfo<?> source,Item target,StructureItem structureItem,Set<StructureShareItemScore> shareItemScores){
		if(source == null || target == null) return false;
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(!StringUtils.isEmpty(source.getSubjectId())){
			target.setSubject(this.subjectDao.load(Subject.class, source.getSubjectId()));
		}
		if(!StringUtils.isEmpty(source.getSourceId())){
			target.setSource(this.sourceDao.load(Source.class, source.getSourceId()));
		}
		if((source instanceof ItemScoreInfo) && structureItem != null && shareItemScores != null){
			ItemScoreInfo itemScoreInfo  = (ItemScoreInfo)source;
			if(!StringUtils.isEmpty(itemScoreInfo.getSerial())){
				StructureShareItemScore shareItemScore = new StructureShareItemScore();
				shareItemScore.setId(MD5Util.MD5(String.format("%1$s-%2$s", structureItem.getId(), target.getId())));
				shareItemScore.setSerial(itemScoreInfo.getSerial());
				shareItemScore.setScore(itemScoreInfo.getScore());
				shareItemScore.setStructureItem(structureItem);
				shareItemScore.setSubItem(target);
				shareItemScores.add(shareItemScore);
			}
		}
		if(target.getChildren() != null) target.getChildren().clear();
		if(source.getChildren() != null && source.getChildren().size() > 0){
			if(target.getChildren()== null) target.setChildren(new HashSet<Item>());
			for(BaseItemInfo<?> info : source.getChildren()){
				if(info == null) continue;
				Item item = StringUtils.isEmpty(info.getId()) ?  null : this.itemDao.load(Item.class, info.getId());
				if(item != null) {
					if(item.getChildren() != null) item.getChildren().clear();
				}
				if(item == null){
					if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
					item = new Item();
				}
				item.setParent(target);
				if(this.changeModel(info, item, structureItem, shareItemScores)){
					target.getChildren().add(item);
				}
			}
		}
		return true;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Item data = this.itemDao.load(Item.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据［%s］", ids[i]));
				this.itemDao.delete(data);
			}
		}
	}
	/*
	 * 加载试题预览。
	 * @see com.examw.test.service.library.IItemService#loadItemPreview(java.lang.String)
	 */
	@Override
	public ItemInfo loadItemPreview(String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题［id = %s］预览....", itemId));
		if(StringUtils.isEmpty(itemId)) return null;
		Item item = this.itemDao.load(Item.class, itemId);
		if(item == null){
			if(logger.isDebugEnabled()) logger.debug(String.format("试题［id = %s］不存在！", itemId));
			return null;
		}
		return this.changeModel(item);
	}
}