package com.examw.test.service.library.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.dao.library.ISourceDao;
import com.examw.test.dao.settings.IAreaDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.Source;
import com.examw.test.domain.library.StructureShareItemScore;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.IItemDuplicateCheck;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemParser;
import com.examw.test.service.library.ItemStatus;
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
	private IAreaDao areaDao;
	private IItemDuplicateCheck itemDuplicateCheck;
	private Map<Integer, ItemParser> itemParsers;
	private Map<Integer, String> statusMap,optMap,judgeAnswerMap;
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
	 * 设置地区数据接口。
	 * @param areaDao 
	 *	  地区数据接口。
	 */
	public void setAreaDao(IAreaDao areaDao) {
		this.areaDao = areaDao;
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
	 * 设置题型解析集合。
	 * @param itemParsers 
	 *	  itemParsers
	 */
	public void setItemParsers(Map<Integer, ItemParser> itemParsers) {
		if(logger.isDebugEnabled()) logger.debug("注入题型解析集合...");
		this.itemParsers = itemParsers;
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
		if(type == null || this.itemParsers == null) return null;
		ItemParser parser = this.itemParsers.get(type);
		return (parser == null) ? null : parser.getTypeName();
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
		ItemParser parser = this.itemParsers.get(data.getType());
		if(parser == null){
			String err = String.format("未能找到题型［%d］的解析器！", data.getType());
			logger.error(err);
			throw new RuntimeException(err);
		}
		parser.setItemDao(this.itemDao);
		ItemInfo info = parser.parser(data);
		if(info == null){
			String err = String.format("题型［%d］解析器［%s］未能正确解析!", data.getType(), parser.getClass());
			logger.error(err);
			throw new RuntimeException(err);
		}
		if(info.getType() != null) info.setTypeName(this.loadTypeName(info.getType()));
		if(info.getStatus() != null) info.setStatusName(this.loadStatusName(info.getStatus()));
		if(info.getOpt() != null) info.setOptName(this.loadOptName(info.getOpt()));
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
		return this.changeModel(this.updateItem(info,null));
	}
	/*
	 * 更新题目。
	 * @see com.examw.test.service.library.IItemService#updateItem(com.examw.test.model.library.ItemInfo)
	 */
	@Override
	public Item updateItem(BaseItemInfo<?> info,Set<StructureShareItemScore> shareItemScores) {
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
			this.itemDao.merge(data);
		}
		info.setCheckCode(checkCode);
		info.setLastTime(new Date());
		//所属科目
		data.setSubject(StringUtils.isEmpty(info.getSubjectId()) ? null : this.subjectDao.load(Subject.class, info.getSubjectId()));
		//所属来源
		data.setSource(StringUtils.isEmpty(info.getSourceId()) ?  null : this.sourceDao.load(Source.class, info.getSourceId()));
		//所属地区
		data.setArea(StringUtils.isEmpty(info.getAreaId()) ? null : this.areaDao.load(Area.class, info.getSourceId()));
		
		ItemParser parser = this.itemParsers.get(data.getType());
		if(parser == null){
			String err = String.format("未能找到题型［%d］的解析器！", data.getType());
			logger.error(err);
			throw new RuntimeException(err);
		}
		parser.setItemDao(this.itemDao);
		parser.parser(info, shareItemScores, data);
		this.itemDao.saveOrUpdate(data);
		return data;
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