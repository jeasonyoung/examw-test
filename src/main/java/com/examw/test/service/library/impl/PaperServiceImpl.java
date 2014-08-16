package com.examw.test.service.library.impl;

import java.util.ArrayList;
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

import com.examw.model.DataGrid;
import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.ISourceDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.dao.library.IStructureItemDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Source;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.domain.library.StructureShareItemScore;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.model.library.ItemScoreInfo;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.ItemStatus;
import com.examw.test.service.library.ItemType;
import com.examw.test.service.library.PaperStatus;

/**
 * 试卷服务接口实现类。
 * @author yangyong.
 * @since 2014-08-06.
 */
public class PaperServiceImpl extends BaseDataServiceImpl<Paper, PaperInfo> implements IPaperService {
	private static final Logger logger = Logger.getLogger(PaperServiceImpl.class);
	private IPaperDao paperDao;
	private ISourceDao sourceDao;
	private ISubjectDao subjectDao;
	private IStructureDao structureDao;
	private IStructureItemDao structureItemDao;
	private IItemService itemService;
	private Map<Integer,String> typeMap,statusMap;
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
	 * 设置来源数据接口。
	 * @param sourceDao 
	 *	  来源数据接口。
	 */
	public void setSourceDao(ISourceDao sourceDao) {
		this.sourceDao = sourceDao;
	}
	/**
	 * 设置所属科目数据接口。
	 * @param subjectDao 
	 *	  所属科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置试卷结构数据接口。
	 * @param structureDao 
	 *	  试卷结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		this.structureDao = structureDao;
	}
	/**
	 * 设置试卷结构下试题数据接口。
	 * @param structureItemDao 
	 *	  试卷结构下试题数据接口。
	 */
	public void setStructureItemDao(IStructureItemDao structureItemDao) {
		this.structureItemDao = structureItemDao;
	}
	/**
	 * 设置试题服务接口。
	 * @param itemService 
	 *	  试题服务接口。
	 */
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
	/**
	 * 设置试卷类型集合。
	 * @param typeMap 
	 *	  试卷类型集合。
	 */
	public void setTypeMap(Map<Integer,String> typeMap) {
		this.typeMap = typeMap;
	}
	/**
	 * 设置试卷状态集合。
	 * @param statusMap 
	 *	  试卷状态集合。
	 */
	public void setStatusMap(Map<Integer,String> statusMap) {
		this.statusMap = statusMap;
	}
	/*
	 * 加载试卷类型名称。
	 * @see com.examw.test.service.library.IPaperService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(type == null || this.typeMap == null || this.typeMap.size() == 0) return null;
		return this.typeMap.get(type);
	}
	/*
	 * 加载试卷状态名称。
	 * @see com.examw.test.service.library.IPaperService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}

	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Paper> find(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.paperDao.findPapers(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PaperInfo changeModel(Paper data) {
		if(logger.isDebugEnabled()) logger.debug("类型转换...");
		if(data == null) return null;
		PaperInfo info = new PaperInfo();
		BeanUtils.copyProperties(data, info, new String[]{"structures"});
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
		if(!StringUtils.isEmpty(data.getType())) info.setTypeName(this.loadTypeName(data.getType()));
		if(!StringUtils.isEmpty(data.getStatus())) info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		 return this.paperDao.total(info);
	}
	/*
	 * 更新数据.
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PaperInfo update(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Paper data = StringUtils.isEmpty(info.getId()) ?  null : this.paperDao.load(Paper.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			info.setStatus(PaperStatus.NONE.getValue());
			data = new Paper();
		}
		if(!StringUtils.isEmpty(data.getStatus())){
			if(data.getStatus() == Paper.STATUS_AUDIT) throw new RuntimeException("试卷已审核，不能被修改！");
			if(data.getStatus() == Paper.STATUS_PUBLISH) throw new RuntimeException("试卷已发布，不能被修改！");
		}
		if(!isAdded){
			info.setStatus(data.getStatus());
			info.setCreateTime(data.getCreateTime());
			info.setPublishTime(data.getPublishTime());
		}
		info.setLastTime(new Date());
		
		BeanUtils.copyProperties(info, data,new String[]{"structures"});
		
		if(!StringUtils.isEmpty(info.getSubjectId()) && (data.getSubject() == null || !data.getSubject().getId().equalsIgnoreCase(info.getSubjectId()))){
			data.setSubject(this.subjectDao.load(Subject.class, info.getSubjectId()));
		}
		if(data.getSubject() != null){
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if(!StringUtils.isEmpty(info.getSourceId()) && (data.getSource() == null || !data.getSource().getId().equalsIgnoreCase(info.getSourceId()))){
			data.setSource(this.sourceDao.load(Source.class, info.getSourceId()));
		}
		if(data.getSource() != null){
			info.setSourceName(data.getSource().getName());
		}
		if(!StringUtils.isEmpty(data.getType())) info.setTypeName(this.loadTypeName(data.getType()));
		if(!StringUtils.isEmpty(data.getStatus())) info.setStatusName(this.loadStatusName(data.getStatus()));
		if(isAdded) this.paperDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据....");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Paper data = this.paperDao.load(Paper.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除［%s］...", ids[i]));
				this.paperDao.delete(data);
			}
		}
	}
	/*
	 * 更新数据状态。
	 * @see com.examw.test.service.library.IPaperService#updateStatus(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void updateStatus(String id, PaperStatus status) {
		if(logger.isDebugEnabled())logger.debug(String.format("更新状态：%1$s -> %2$s", id, status));
		String msg = null;
		if(StringUtils.isEmpty(id)){
			msg = "试卷ID为空！";
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(status == null) status = PaperStatus.NONE;
		Paper data = this.paperDao.load(Paper.class, id);
		if(data == null){
			msg = String.format("试卷［id=%s］不存在！", id);
		}
		data.setStatus(status.getValue());
		if(status == PaperStatus.AUDIT){
			//将试卷下未审核的题目批量审核。
			this.auditPaperItems(data.getStructures());
		}
		if(status == PaperStatus.PUBLISH){
			data.setPublishTime(new Date());
		}
		data.setLastTime(new Date());
		if(logger.isDebugEnabled()) logger.debug(String.format("更新状态［%1$s,%2$s］%3$s ＝> %4$s", data.getId(),data.getName(), PaperStatus.convert(data.getStatus()),  status));
	}
	//审核试卷下的题目。
	private void auditPaperItems(Set<Structure> structures){
		if(structures == null || structures.size() == 0) return;
		for(Structure structure : structures){
			if(structure == null) continue;
			if(structure.getItems() != null && structure.getItems().size() > 0){
				for(StructureItem structureItem : structure.getItems()){
					if(structureItem == null) continue;
					if(structureItem.getItem() != null && structureItem.getItem().getStatus() != ItemStatus.AUDIT.getValue()){
						structureItem.getItem().setStatus(ItemStatus.AUDIT.getValue());
					}
				}
			}
			this.auditPaperItems(structure.getChildren());
		}
	}
	/*
	 * 加载试卷结构集合。
	 * @see com.examw.test.service.library.IPaperService#loadStructures(java.lang.String)
	 */
	@Override
	public List<StructureInfo> loadStructures(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷结构集合［paperId = %s］...", paperId));
		String msg =  null;
		if(StringUtils.isEmpty(paperId)){
			msg = "试卷ID不存在！";
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		List<StructureInfo> results = new ArrayList<>();
		List<Structure> list = this.structureDao.finaStructures(paperId);
		if(list != null && list.size() > 0){
			for(Structure s : list){
				StructureInfo info = new StructureInfo();
				if(this.changeModel(s, info, false)){
					results.add(info);
				}
			}
		}
		return results;
	}
	//试卷结构类型转换。
	private boolean changeModel(Structure source, StructureInfo target, boolean isChangeItems){
		if(source == null || target == null) return false;
		BeanUtils.copyProperties(source, target, new String[]{"items","children"});
		if(isChangeItems && source.getItems() != null && source.getItems().size() > 0){
			Set<StructureItemInfo> items = new TreeSet<StructureItemInfo>(new Comparator<StructureItemInfo>(){
				@Override
				public int compare(StructureItemInfo o1, StructureItemInfo o2) {
					return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(StructureItem structureItem : source.getItems()){
				 if(structureItem == null) continue;
				 StructureItemInfo structureItemInfo = this.changeModel(structureItem);
				 if(structureItemInfo != null) items.add(structureItemInfo);
			}
			target.setItems(items);
		}
		if(source.getChildren() != null && source.getChildren().size() > 0){
			Set<StructureInfo> children = new TreeSet<StructureInfo>(new Comparator<StructureInfo>(){
				@Override
				public int compare(StructureInfo o1, StructureInfo o2) {
					return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(Structure s : source.getChildren()){
				if(s == null) continue;
				 StructureInfo e = new StructureInfo();
				 e.setPid(target.getId());
				 if(this.changeModel(s, e, isChangeItems)){
					 children.add(e);
				 }
			}
			if(children.size() > 0) target.setChildren(children);
		}
		return true;
	}
	/*
	 * 更新试卷结构。
	 * @see com.examw.test.service.library.IPaperService#updateStructure(java.lang.String, com.examw.test.model.library.StructureInfo)
	 */
	@Override
	public void updateStructure(String paperId, StructureInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新试卷［paperId = "+ paperId +"］结构...");
		String msg = null;
		if(StringUtils.isEmpty(paperId)){
			msg = "所属试卷ID为空！";
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(info == null){
			msg = String.format("更新的试卷［paperId = %s］结构不存在！", paperId);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null){
			msg = String.format("所属试卷［paperId = %s］不存在！", paperId);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		boolean isAdded = false;
		Structure data = StringUtils.isEmpty(info.getId()) ?  null : this.structureDao.load(Structure.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Structure();
			data.setPaper(paper);
		}
		data.setParent(StringUtils.isEmpty(info.getPid()) ? null : this.structureDao.load(Structure.class, info.getPid()));
		BeanUtils.copyProperties(info, data, new String[]{"children"});
		if(isAdded) this.structureDao.save(data);
	}
	/*
	 * 删除试卷结构。
	 * @see com.examw.test.service.library.IPaperService#deleteStructure(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteStructure(String[] structureIds) {
		if(logger.isDebugEnabled()) logger.debug("删除试卷结构...");
		if(structureIds == null || structureIds.length == 0) return;
		for(int i = 0; i< structureIds.length; i++){
			if(logger.isDebugEnabled()) logger.debug(String.format("删除结构［%s］数据...", structureIds[i]));
			Structure data = this.structureDao.load(Structure.class, structureIds[i]);
			if(data != null) this.structureDao.delete(data);
		}
	}
	/*
	 * 加载试卷结构下最大的排序号。
	 * @see com.examw.test.service.library.IPaperService#loadStructureItemMaxOrderNo(java.lang.String)
	 */
	@Override
	public Long loadStructureItemMaxOrderNo(String structureId) {
		if(logger.isDebugEnabled()) logger.debug("加载试卷结构下最大的排序号...");
		return this.structureItemDao.loadMaxOrderNo(structureId);
	}
	/*
	 * 查询试卷结构试题查询。
	 * @see com.examw.test.service.library.IPaperService#loadStructureItems(java.lang.String, com.examw.test.model.library.StructureItemInfo)
	 */
	@Override
	public DataGrid<StructureItemInfo> loadStructureItems(String paperId,StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询试卷［paper = %s］结构试题查询...", paperId));
		DataGrid<StructureItemInfo> grid = new DataGrid<StructureItemInfo>();
		List<StructureItemInfo> rows = new ArrayList<>();
		List<StructureItem> list = this.structureItemDao.findStructureItems(paperId, info);
		if(list != null && list.size() > 0){
			for(StructureItem structureItem : list){
				StructureItemInfo target = this.changeModel(structureItem);
				if(target != null) rows.add(target);
			}
		}
		grid.setRows(rows);
		grid.setTotal(this.structureItemDao.total(paperId, info));
		return grid;
	}
	//类型转换（StructureItem => StructureItemInfo）。
	private StructureItemInfo changeModel(StructureItem source){
		if(source == null || source.getItem() == null) return null;
		StructureItemInfo target = new StructureItemInfo();
		BeanUtils.copyProperties(source, target, new String[]{"item"});
		if(source.getStructure() != null) target.setStructureId(source.getStructure().getId());
		target.setItem(this.changeModel(source.getItem(), source.getShareItemScores()));
		if(!StringUtils.isEmpty(source.getSerial())){
			target.getItem().setSerial(source.getSerial());
		}
		if(source.getScore() != null){
			target.getItem().setScore(source.getScore());
		}
		return target;
	}
	//类型转换（item => ItemScoreInfo ）。
	private ItemScoreInfo changeModel(Item item,Set<StructureShareItemScore> shareItemScores ){
		if(item == null) return null;
		ItemScoreInfo info = new ItemScoreInfo();
		//试题信息。
		BeanUtils.copyProperties(item, info, new String[]{"children"});
		if(item.getSubject() != null){
			info.setSubjectId(item.getSubject().getId());
			info.setSubjectName(item.getSubject().getName());
			if(item.getSubject().getExam() != null){
				info.setExamId(item.getSubject().getExam().getId());
				info.setExamName(item.getSubject().getExam().getName());
			}
		}
		if(item.getSource() != null){
			info.setSourceId(item.getSource().getId());
			info.setSourceName(item.getSource().getName());
		}
		if(item.getType() != null) info.setTypeName(this.itemService.loadTypeName(item.getType()));
		if(item.getStatus() != null) info.setStatusName(this.itemService.loadStatusName(item.getStatus()));
		if(item.getOpt() != null) info.setOptName(this.itemService.loadOptName(item.getOpt()));
		//结构试题信息。
		if(shareItemScores != null && shareItemScores.size() > 0){
			StructureShareItemScore structureShareItemScore = null; 
			for(StructureShareItemScore itemScore : shareItemScores){
				 if(itemScore == null || itemScore.getSubItem() == null) continue;
				 if(!StringUtils.isEmpty(item.getId()) && itemScore.getSubItem().getId().equalsIgnoreCase(item.getId())){
					 structureShareItemScore = itemScore;
					 break;
				 }
			 }
			if(structureShareItemScore != null){
				info.setSerial(structureShareItemScore.getSerial());
				info.setScore(structureShareItemScore.getScore());
			}
		}
		if(item.getChildren() != null && item.getChildren().size() > 0){
			Set<ItemInfo> children = new TreeSet<ItemInfo>(new Comparator<ItemInfo>(){
				@Override
				public int compare(ItemInfo o1, ItemInfo o2) {
					return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(Item e : item.getChildren()){
				ItemInfo itemInfo = this.changeModel(e, shareItemScores);
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
	 * 更新试卷结构下试题。
	 * @see com.examw.test.service.library.IPaperService#updateStructureItem(java.lang.String, com.examw.test.model.library.StructureItemInfo)
	 */
	@Override
	public StructureItemInfo updateStructureItem(String paperId,StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新试卷结构下试题...");
		if(StringUtils.isEmpty(paperId) || info == null || info.getItem() == null) return null;
		String msg = null;
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null){
			msg = String.format("试卷［id=%s］不存在！", paperId);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(paper.getStatus() != PaperStatus.NONE.getValue()){
			msg = String.format("试卷［％1$s］状态［%2$s］不允许更新！", paper.getName(),this.loadStatusName(paper.getStatus()));
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		boolean isAdded = false;
		StructureItem data = StringUtils.isEmpty(info.getId()) ? null : this.structureItemDao.load(StructureItem.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new StructureItem(); 
		}
		if(!isAdded) info.setCreateTime(data.getCreateTime());
		Structure structure = StringUtils.isEmpty(info.getStructureId()) ? null : this.structureDao.load(Structure.class, info.getStructureId());
		if(structure == null){
			msg = String.format("试卷［%1$s］下未找到结构［structureId = %2$s］！", paper.getName(), info.getStructureId());
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		data.setStructure(structure);
		BeanUtils.copyProperties(info, data, new String[]{"item"});
		data.setSerial(info.getItem().getSerial());
		data.setScore(info.getItem().getScore());
		Set<StructureShareItemScore> shareItemScores = null;
		if(info.getItem().getType() == ItemType.SHARE_TITLE.getValue() || info.getItem().getType() == ItemType.SHARE_ANSWER.getValue()){
			shareItemScores = new HashSet<>();
		}
		Item item = this.itemService.updateItem(info.getItem(), data, shareItemScores);
		if(item == null){
			msg = "更新试题内容失败！";
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		data.setItem(item);
		data.setShareItemScores(shareItemScores);
		if(isAdded)this.structureItemDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除试卷结构下的数据。
	 * @see com.examw.test.service.library.IPaperService#deleteStructureItem(java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public void deleteStructureItem(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除试卷下试题数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			if(logger.isDebugEnabled()) logger.debug("删除试卷下试题：" + ids[i]);
			StructureItem data = this.structureItemDao.load(StructureItem.class, ids[i]);
			if(data != null) this.structureItemDao.delete(data);
		}
	}
	/*
	 * 加载试卷预览。
	 * @see com.examw.test.service.library.IPaperService#loadPaperPreview(java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperPreview(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［id = %s］预览...", paperId));
		if(StringUtils.isEmpty(paperId)) return null;
		this.paperDao.evict(Paper.class);
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null) {
			if(logger.isDebugEnabled()) logger.debug(String.format("试卷［id = %s］不存在！", paperId));
			return null;
		}
		PaperPreview paperPreview = this.changeModel(paper);
		if(paperPreview == null) return null;
		List<StructureInfo> structures = new ArrayList<>();
		List<Structure> list = this.structureDao.finaStructures(paperId);
		if(list != null && list.size() > 0){
			for(Structure s : list){
				StructureInfo info = new StructureInfo();
				if(this.changeModel(s, info, true)){
					structures.add(info);
				}
			}
		}
		paperPreview.setStructures(structures);
		return paperPreview;
	}
}