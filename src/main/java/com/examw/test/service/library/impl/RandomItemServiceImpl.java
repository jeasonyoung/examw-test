package com.examw.test.service.library.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.service.library.IRandomItemService;
import com.examw.test.service.library.ItemType;
import com.examw.test.service.library.PaperStatus;

/**
 *  试卷随机添加试题服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月20日
 */
public class RandomItemServiceImpl implements IRandomItemService {
	private static final Logger logger = Logger.getLogger(RandomItemServiceImpl.class);
	private static final int push_item_random_for_count = 3;//抽题随机循环次数。
	private IPaperDao paperDao;
	private IStructureDao structureDao;
	private IItemDao itemDao;
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/**
	 * 设置试卷结构数据接口。
	 * @param structureDao 
	 *	  试卷结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷结构数据接口...");
		this.structureDao = structureDao;
	}
	/**
	 * 设置试题数据接口。
	 * @param itemDao 
	 *	  试题数据接口。
	 */
	public void setItemDao(IItemDao itemDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试题数据接口...");
		this.itemDao = itemDao;
	}
	/*
	 * 随机添加试题到试卷结构。
	 * @see com.examw.test.service.library.IRandomItemService#addRandomItem(java.lang.String)
	 */
	@Override
	public void addRandomItem(String structureId)  throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("随机添加试题到试卷结构［structureId ＝%s］....", structureId));
		if(StringUtils.isEmpty(structureId)) return;
		String msg = null;
		Structure structure = this.structureDao.load(Structure.class, structureId);
		if(structure == null){
			logger.error(msg = String.format("试卷结构［%s］不存在！", structureId));
			throw new Exception(msg);
		}
		if(structure.getPaper() == null){
			logger.error(msg = String.format("试卷结构［%s］关联的试卷不存在！", structureId));
			throw new Exception(msg);
		}
		if(structure.getPaper().getStatus() != PaperStatus.NONE.getValue()){
			logger.error(msg = String.format("试卷状态为［%s］，不允许导入试题！", PaperStatus.convert(structure.getPaper().getStatus())));
			throw new Exception(msg);
		}
		Integer total = structure.getTotal();
		if(total == null || total <= 0){
			logger.error(msg = String.format("试卷结构未设置试题数目［%d］!", total));
			throw new Exception(msg);
		}
		Map<String, Item> itemsMap = new HashMap<String, Item>();
		if(structure.getItems() != null && structure.getItems().size() > 0){
			int count = 0;
			for(StructureItem si : structure.getItems()){
				count += si.getItem().getCount();
				itemsMap.put(si.getItem().getId(), null);
			}
			if(count >= total){
				logger.error(msg = String.format("试卷结构中已存在［%1$d］道题 大于或等于结构设置的试题数目［%2$d］上限，暂不能导入！", count, total));
				throw new Exception(msg);
			}
		}
		ItemType itemType =  (structure.getType() == null) ?  null : ItemType.convert(structure.getType());
		if(itemType == null){
			logger.error(msg = String.format("试卷结构的试题类型［%d］不存在或不能被解析！", structure.getType()));
			throw new Exception(msg);
		}
		Subject subject = (structure.getPaper() == null || structure.getPaper().getSubject() == null) ? null : structure.getPaper().getSubject();
		if(subject == null){
			logger.error(msg =  "试卷所属科目未设置！");
			throw new Exception(msg);
		}
		Area area = (structure.getPaper() == null || structure.getPaper().getArea() == null) ?  null : structure.getPaper().getArea();
		List<Item> pools = this.itemDao.loadItems(subject, itemType, area);
		if(pools == null || pools.size() == 0){
			logger.error(msg = String.format("题库中没有满足条件［%1$s］［%2$s］［%3$s］的试题！",subject.getName(), itemType, (area == null ? "" : area.getName())));
			throw new Exception(msg);
		}
		if(pools.size() < total){
			logger.error(msg = String.format("从题库中只能查找出符合条件的试题［%1$d］，不满足试卷结构［%2$d］的要求！", pools.size(), total));
			throw new Exception(msg);
		}
		int total_index = total,index = 0;//结构需要的试题总数。
		while(total_index > 0){
			if(pools == null || pools.size() == 0) break;
			Item item = this.pushItemHandler(pools);
			if(item == null) break;
			if(!itemsMap.containsKey(item.getId())){
				pools.remove(item);//从随机池移除被选中的试题。
				itemsMap.put(item.getId(), item);
				total_index -= item.getCount();
				index = 0;
				continue;
			}
			if(index > push_item_random_for_count){
				break;
			}
			index++;
			pools.remove(item);//从随机池移除被选中的试题。
		}
		if(total_index != 0){
			logger.error(msg = String.format("随机抽取的试题尾数为［%d］，请再次随机抽取！ ", total_index));
			throw new Exception(msg);
		}
		//添加题目到结构
		int order = 0;
		if(structure.getItems() == null) structure.setItems(new HashSet<StructureItem>());
		for(Item item : itemsMap.values()){
			if(item == null) continue;
			structure.getItems().add(new StructureItem(structure, item, ++order));
		}
		//保存数据。
		if(order > 0) this.structureDao.saveOrUpdate(structure);
	}
	//抽题处理。
	private Item pushItemHandler(List<Item> sources){
		Map<Long, Item> map = new TreeMap<Long, Item>(new Comparator<Long>() {
			@Override public int compare(Long o1, Long o2) { return (int)(o1 - o2); }
		});
		int index = push_item_random_for_count;//随机次数。
		while(index > 0){
			Item item = this.randomSelected(sources);
			if(item == null) break;
			Long count =  this.structureDao.totalItemCount(item.getId());
			if(count == 0){//如果随机出的试题未被试卷关联过将作为首要目标。
				return item;
			}
			map.put(count, item);
			//随机次数递减。
			index --;
		}
		if(map.size() == 0) return null;
		//将关联次数最少的试题作为目标试题。
		for(Entry<Long, Item> entry : map.entrySet()){
			if(entry != null) return entry.getValue();
		}
		return null;
	}
	//随机选题。
	private Item randomSelected(List<Item> sources){
		int size = 0;
		if(sources == null || (size = sources.size()) == 0) return null; 
		Random random = new Random(System.currentTimeMillis() * size);
	    int index = Math.abs((random.nextInt() *  random.nextInt(push_item_random_for_count))) % size; 
	    return sources.get(index);
	}
	/*
	 *  整理试卷下试题的排序号。
	 * @see com.examw.test.service.library.IRandomItemService#updateItemOrder(java.lang.String)
	 */
	@Override
	public void updateItemOrder(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("整理试卷［paperId = %s］的排序号...", paperId));
		String msg =  null;
		if(StringUtils.isEmpty(paperId)){
			logger.error(msg = "试卷ID为空！");
			throw new RuntimeException(msg);
		}
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null){
			logger.error(msg = String.format("试卷［%s］不存在！", paperId));
			throw new RuntimeException(msg);
		}
		if(paper.getStatus() != PaperStatus.NONE.getValue()){
			logger.error(msg = String.format("试卷状态［%s］，不允许操作！", PaperStatus.convert(paper.getStatus())));
			throw new RuntimeException(msg);
		}
		List<Structure> structures = this.structureDao.loadStructures(paperId);
		if(structures == null || structures.size() == 0){
			logger.error(msg = String.format("试卷［%s］没有试卷结构！", paperId));
			throw new RuntimeException(msg);
		}
		int index = 0;
		for(Structure structure : structures){
			if(structure == null || structure.getItems() == null || structure.getItems().size() == 0) continue;
			for(StructureItem item : structure.getItems()){
				if(item == null || item.getItem() == null) continue;
				item.setOrderNo(index + 1);
				index +=  item.getItem().getCount();
			}
		}
	}
}