package com.examw.test.service.library.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperStructureService;

/**
 * 试卷结构服务实现类。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
public class PaperStructureServiceImpl implements IPaperStructureService {
	private static final Logger logger = Logger.getLogger(PaperStructureServiceImpl.class);
	private IStructureDao structureDao;
	private IPaperDao paperDao;
	private IItemService itemService;
	/**
	 * 设置试卷结构数据接口。
	 * @param structureDao 
	 *	试卷结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		if(logger.isDebugEnabled())logger.debug("注入试卷结构数据接口...");
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
	 * 设置试卷服务接口。
	 * @param itemService 
	 *	  试卷服务接口。
	 */
	public void setItemService(IItemService itemService) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷服务接口...");
		this.itemService = itemService;
	}
	/*
	 * 加载结构数据。
	 * @see com.examw.test.service.library.IPaperStructureService#loadStructures(java.lang.String)
	 */
	@Override
	public List<StructureInfo> loadStructures(String paperId) {
		if(logger.isDebugEnabled())logger.debug(String.format("加载结构[paperId=%s]数据...", paperId));
		String msg = null;
		if (StringUtils.isEmpty(paperId)) {
			logger.error(msg = "试卷ID不存在！");
			throw new RuntimeException(msg);
		}
		return this.changeModel(this.structureDao.loadStructures(paperId));
	}
	/**
	 * 数据模型集合转换。
	 * @param structures
	 * @return
	 */
	protected List<StructureInfo> changeModel(List<Structure> structures){
		if(logger.isDebugEnabled()) logger.debug("数据模型集合转换 List<Structure> => List<StructureInfo> ...");
		List<StructureInfo> list = new ArrayList<>();
		if(structures != null && structures.size() > 0){
			for(Structure structure : structures){
				if(structure == null) continue;
				StructureInfo info = this.changeModel(structure);
				if(info != null){
					list.add(info);
				}
			}
			if(list.size() > 0) Collections.sort(list);
		}
		return list;
	}
	//数据模型转换。
	protected StructureInfo changeModel(Structure data){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Structure => StructureInfo ...");
		if(data == null) return null;
		StructureInfo info = new StructureInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getType() != null && this.itemService != null){
			info.setTypeName(this.itemService.loadTypeName(data.getType()));
		}
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.library.IPaperStructureService#conversion(com.examw.test.domain.library.Structure)
	 */
	@Override
	public StructureInfo conversion(Structure structure) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换...");
		return this.changeModel(structure);
	}
	/*
	 * 更新试卷结构。
	 * @see com.examw.test.service.library.IPaperStructureService#updateStructure(java.lang.String, com.examw.test.model.library.PaperStructureInfo)
	 */
	@Override
	public void updateStructure(String paperId, StructureInfo info) {
		if (logger.isDebugEnabled())logger.debug("更新试卷［paperId = " + paperId + "］结构...");
		String msg = null;
		if (info == null) {
			logger.error(String.format("更新的试卷［paperId = %s］结构不存在！", paperId));
			throw new RuntimeException(msg);
		}
		boolean isAdded = false;
		Structure data = StringUtils.isEmpty(info.getId()) ? null : this.structureDao.load(Structure.class, info.getId());
		if(isAdded = (data == null)){
			if (StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			if (StringUtils.isEmpty(paperId)) {
				logger.error(msg = "所属试卷ID为空！");
				throw new RuntimeException(msg);
			}
			Paper paper = this.paperDao.load(Paper.class, paperId);
			if(paper == null){
				logger.error(msg = String.format("所属试卷［paperId = %s］不存在!", paperId));
				throw new RuntimeException(msg);
			}
			data = new Structure();
			data.setPaper(paper);
		}
		BeanUtils.copyProperties(info, data);
		if (isAdded) this.structureDao.save(data);
	}
	/*
	 * 删除试卷结构。
	 * @see com.examw.test.service.library.IPaperStructureService#deleteStructure(java.lang.String[])
	 */
	@Override
	public void deleteStructure(String[] structureIds) {
		if (logger.isDebugEnabled())logger.debug("删除试卷结构...");
		if (structureIds == null || structureIds.length == 0)return;
		String msg = null;
		Integer count = 0;
		for (int i = 0; i < structureIds.length; i++) {
			if (logger.isDebugEnabled())logger.debug(String.format("删除结构［%s］数据...", structureIds[i]));
			Structure data = this.structureDao.load(Structure.class,structureIds[i]);
			if (data != null){
				if(data.getItems() != null && (count = data.getItems().size()) > 0){
					msg= String.format("试卷结构[%1$s]下有试题[%2$d]!", data.getTitle(), count);
					if(logger.isDebugEnabled())logger.debug(msg);
					throw new RuntimeException(msg);
				}
				this.structureDao.delete(data);
			}
		}
	}
}