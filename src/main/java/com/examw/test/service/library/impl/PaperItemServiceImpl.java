package com.examw.test.service.library.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.beans.BeanUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperItemService;
import com.examw.test.service.library.PaperStatus;
/**
 * 试卷试题服务实现类。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
public class PaperItemServiceImpl extends BaseDataServiceImpl<StructureItem,StructureItemInfo> implements IPaperItemService {
	private static final Logger logger = Logger.getLogger(PaperItemServiceImpl.class);
	private IStructureDao structureDao;
	private IPaperDao paperDao;
	private IItemService itemService;
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
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if(logger.isDebugEnabled())logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/**
	 * 设置试题服务接口。
	 * @param itemService 
	 *	  试题服务接口。
	 */
	public void setItemService(IItemService itemService) {
		if(logger.isDebugEnabled())logger.debug("注入试题服务接口...");
		this.itemService = itemService;
	}
	/*
	 * 加载试卷结构最大题序号。
	 * @see com.examw.test.service.library.IPaperItemService#loadMaxOrderNo(java.lang.String)
	 */
	public Integer loadMaxOrder(String structureId){
		if (logger.isDebugEnabled())logger.debug("加载试卷结构下最大的排序号...");
		return this.structureDao.loadItemMaxOrder(structureId);
	}
	/*
	 * 加载试卷结构。
	 * @see com.examw.test.service.library.IPaperItemService#loadStructure(java.lang.String)
	 */
	@Override
	public Structure loadStructure(String structureId) {
		 if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷结构［%s］...", structureId));
		 if(StringUtils.isEmpty(structureId)) throw new RuntimeException("试卷结构ID为空！");
		 return this.structureDao.load(Structure.class, structureId);
	}
	/*
	 * 加载试卷试题。
	 * @see com.examw.test.service.library.IPaperItemService#loadPaperItem(java.lang.String, java.lang.String)
	 */
	@Override
	public StructureItemInfo loadPaperItem(String structureId, String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷试题［structureId = %1$s,itemId = %2$s］...", structureId,itemId));
		if(StringUtils.isEmpty(structureId) || StringUtils.isEmpty(itemId)) return null;
		return this.conversion(this.structureDao.loadStructureItem(structureId, itemId), true);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<StructureItem> find(StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.structureDao.findItems(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected StructureItemInfo changeModel(StructureItem source) {
		return this.conversion(source, false);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.library.IPaperItemService#conversion(com.examw.test.domain.library.StructureItem, boolean)
	 */
	@Override
	public StructureItemInfo conversion(StructureItem source, boolean isAll) {
		if(logger.isDebugEnabled())logger.debug("数据模型转换[StructureItem => StructureItemInfo]...");
		if(source == null || source.getItem() == null) return null;
		StructureItemInfo info = new StructureItemInfo();
		this.itemService.conversion(source.getItem(), info, isAll);
		info.setOrderNo(source.getOrderNo());
		if(source.getStructure() != null){
			info.setStructureId(source.getStructure().getId());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug(" 查询数据统计..."); 
		return this.structureDao.totalItems(info);
	}
	/*
	 * 更新试卷试题。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public StructureItemInfo update(StructureItemInfo info) {
		if (logger.isDebugEnabled())logger.debug("更新试卷试题...");
		String msg = null;
		if (info == null || StringUtils.isEmpty(info.getStructureId())) {
			logger.error(msg = String.format("试卷试题为null或者试题所属结构ID［structureId = %s］为空！", info == null ? "" : info.getStructureId()));
			throw new RuntimeException(msg);
		}
		Structure structure = this.structureDao.load(Structure.class, info.getStructureId());
		if(structure == null){
			logger.error(msg = String.format("试卷结构［structureId = %s］不存在！", info.getStructureId()));
			throw new RuntimeException(msg);
		}
		Paper paper = this.paperDao.load(Paper.class,info.getPaperId());
		if((paper == null) || (paper.getStatus() != PaperStatus.NONE.getValue())){
			logger.error(msg =  (paper == null ? "试卷不存在！" : String.format("试卷［%1$s］状态［%2$s］不允许更新！",paper.getName(),PaperStatus.convert(paper.getStatus()))));
			throw new RuntimeException(msg);
		}
		if (StringUtils.isEmpty(info.getId()) && structure.getTotal() > 0) {
			Long has_count = this.structureDao.totalStructureItems(info.getStructureId());
			Integer item_count = this.itemService.calculationCount(info);
			Long count =  (has_count == null ? 0 : has_count)  + (item_count == null ?  0 : item_count);
			if (count != null && count > structure.getTotal()) {
				msg = String.format("试卷［%1$s］结构下［structureId = %2$s］题目数量已满［total = %3$d］［count = %4$d］！",
						paper.getName(), info.getStructureId(), structure.getTotal(), count);
				logger.error(msg);
				throw new RuntimeException(msg);
			}
		}
		if(paper != null){
			info.setType(structure.getType());//题型。
			info.setOpt(paper.getType());//试卷类型。
			info.setYear(paper.getYear());//使用年份。
			//设置考试科目
			if(StringUtils.isEmpty(info.getSubjectId()))
			{
				String subject_id =  null;
				Structure parentStructure = structure;
				Set<Subject> subjects = null;
				while(parentStructure != null && ((subjects = parentStructure.getSubjects()) == null || subjects.size() == 0)){
					Structure p = null;
					if((p = parentStructure.getParent()) == null) break;
					parentStructure = p;
				}
				int count = 0;
				if(subjects != null && (count = subjects.size()) > 0){
					if(count > 1) throw new RuntimeException(String.format("结构［%1$s］有［%2$d］科目!", structure.getTitle(), count));
					subject_id = subjects.iterator().next().getId();
				}else {
					Subject subject = null;
					if((subject = paper.getSubject()) == null) throw new RuntimeException(String.format("试卷［%s］未设置所属科目！", paper.getName()));
					subject_id = subject.getId();
				}
				if(!StringUtils.isEmpty(subject_id)){
					info.setSubjectId(subject_id);
				}
			}
			//设置考试
			if(paper.getSubject().getExam() != null){
				info.setExamId(paper.getSubject().getExam().getId());
			}
			//试卷来源
			if(paper.getSource() != null){
				info.setSourceId(paper.getSource().getId());
			}
			//所属地区
			if(paper.getArea() != null){
				info.setAreaId(paper.getArea().getId());
			}
		}
		StructureItem data = new StructureItem();
		data.setStructure(structure);
		data.setOrderNo(info.getOrderNo());
		info.setOrderNo(0);
		data.setItem(this.itemService.updateItem(info));
		if(data.getItem() == null){
			logger.error(msg = "更新试题内容失败！");
			throw new RuntimeException(msg);
		}
		if(structure.getItems() == null){
			structure.setItems(new HashSet<StructureItem>());
		} 
//		structure.getItems().add(data);
		//2014.12.30 修改序号不起作用的问题
		updateStructureItem(structure, data);
		return this.changeModel(data);
	}
	/**
	 * 修改更新structureItem
	 * @param structure
	 * @param item
	 */
	private void updateStructureItem(Structure structure,StructureItem item)
	{
		Set<StructureItem> items = structure.getItems();
		if(items.size() == 0) items.add(item);
		for(StructureItem si :items)
		{
			if(si.getItem().getId().equals(item.getItem().getId()))
			{
				BeanUtils.copyProperties(item,si);
				break;
			}
		}
		structure.setItems(items);
	}
	/*
	 * 删除试卷试题。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		throw new RuntimeException("该方法未实现，请调用 delete(String structureId, String[] itemIds) ");
	}
	/*
	 * 删除试卷结构与试题的关联。
	 * @see com.examw.test.service.library.IPaperItemService#delete(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void delete(String structureId, String itemId,boolean isForced) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷结构［structureId = %1$s］下的试题［itemIds = %2$s］...", structureId, itemId));
		if(StringUtils.isEmpty(structureId) || StringUtils.isEmpty(itemId)) return;
		if(this.structureDao.deleteStructureItems(structureId, itemId) > 0 && isForced){
			this.itemService.deleteStructureForced(itemId);
		}
	}
	/*
	 * 保存导入试卷结构下试题集合。
	 * @see com.examw.test.service.library.IPaperItemService#saveImports(java.lang.String, java.lang.String[])
	 */
	@Override
	public void saveImports(String structureId, String[] itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("保存导入试卷结构［%1$s］下试题［%2$s］集合...", structureId, Arrays.toString(itemId)));
		if(StringUtils.isEmpty(structureId) || itemId == null || itemId.length == 0) return;
		Structure structure = this.structureDao.load(Structure.class,structureId);
		if(structure == null) throw new RuntimeException(String.format("试卷结构［%s］不存在！", structureId));
		if(structure.getItems() == null){
			structure.setItems(new HashSet<StructureItem>());
		}
		if(structure.getTotal() ==  null || structure.getTotal() <= 0) throw new RuntimeException("试卷结构下未设置试题量，暂不能导入试题！");
		int index = structure.getItems().size(),count = structure.getTotal() - index;
		if(count < 0) throw new RuntimeException(String.format("试题数量超过结构设置的试题量［%1$d］，暂不能导入试题！", Math.abs(count)));
		for(int  i = 0; i < itemId.length; i++){
			if(StringUtils.isEmpty(itemId[i])) continue;
			if(count <= 0) throw new RuntimeException("已到达试卷结构设置的试题量！");
			Item item = this.itemService.loadItem(itemId[i]);
			if(item != null){
				structure.getItems().add(new StructureItem(structure, item, index + i + 1));
				count--;
			}
		}
		this.structureDao.update(structure);
	}
}