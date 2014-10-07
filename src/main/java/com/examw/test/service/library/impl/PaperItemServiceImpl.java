package com.examw.test.service.library.impl;

import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IStructureDao;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
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
	public Integer loadMaxOrderNo(String structureId){
		if (logger.isDebugEnabled())logger.debug("加载试卷结构下最大的排序号...");
		return this.structureDao.loadItemMaxOrderNo(structureId);
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
		if(logger.isDebugEnabled())logger.debug("数据模型转换[StructureItem => StructureItemInfo]...");
		if(source == null || source.getItem() == null) return null;
		StructureItemInfo info = new StructureItemInfo();
		this.itemService.conversion(source.getItem(), info);
		info.setOrderNo(source.getOrderNo());
		if(source.getStructure() != null){
			info.setStructureId(source.getStructure().getId());
		}
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.library.IPaperItemService#conversion(com.examw.test.domain.library.StructureItem)
	 */
	@Override
	public StructureItemInfo conversion(StructureItem source) {
		return this.changeModel(source);
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
		if((structure.getPaper() == null) || (structure.getPaper().getStatus() != PaperStatus.NONE.getValue())){
			logger.error(msg =  (structure.getPaper() == null ? "试卷不存在！" : String.format("试卷［％1$s］状态［%2$d］不允许更新！", 
																																			structure.getPaper().getName(), 
																																			structure.getPaper().getStatus())));
			
			throw new RuntimeException(msg);
		}
		if (structure.getTotal() > 0) {
			Long count = this.structureDao.totalStructureItems(info.getStructureId()) + this.itemService.calculationCount(info);
			if (count != null && count >= structure.getTotal()) {
				msg = String.format("试卷［%1$s］结构下［structureId = %2$s］题目数量已满［total = %3$d］［count = %4$d］！",
						structure.getPaper().getName(), info.getStructureId(), structure.getTotal(), count);
				logger.error(msg);
				throw new RuntimeException(msg);
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
		structure.getItems().add(data);
		return this.changeModel(data);
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
	 * 删除试卷试题。
	 * @see com.examw.test.service.library.IPaperItemService#delete(java.lang.String, java.lang.String[])
	 */
	@Override
	public void delete(String structureId, String[] itemIds) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷结构［structureId = %1$s］下的试题［itemIds = %2$s］...", structureId, itemIds));
		Integer count = this.structureDao.deleteStructrureItems(structureId, itemIds);
		if(logger.isDebugEnabled()) logger.debug(String.format("成功删除［%d］条数据!", count));
	}
}