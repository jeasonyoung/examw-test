package com.examw.test.service.library.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IStructureDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.model.library.BasePaperInfo;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.library.IPaperItemService;
import com.examw.test.service.library.IPaperPreviewService;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.IPaperStructureService;

/**
 * 试卷预览服务实现类。
 * 
 * @author yangyong
 * @since 2014年9月23日
 */
public class PaperPreviewServiceImpl implements IPaperPreviewService {
	private static final Logger logger = Logger.getLogger(PaperPreviewServiceImpl.class);
	private IStructureDao structureDao;
	private IPaperService paperService;
	private IPaperStructureService paperStructureService;
	private IPaperItemService paperItemService;
	/**
	 * 设置试卷结构数据接口。
	 * @param structureDao 
	 *	  试卷结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		this.structureDao = structureDao;
	}
	/**
	 * 设置试卷服务接口。
	 * @param paperService 
	 *	  试卷服务接口。
	 */
	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
	}
	/**
	 * 设置试卷结构服务接口。
	 * @param paperStructureService 
	 *	  试卷结构服务接口。
	 */
	public void setPaperStructureService(IPaperStructureService paperStructureService) {
		this.paperStructureService = paperStructureService;
	}
	/**
	 * 设置试卷试题服务接口。
	 * @param paperItemService 
	 *	  试卷试题服务接口。
	 */
	public void setPaperItemService(IPaperItemService paperItemService) {
		this.paperItemService = paperItemService;
	}
	/*
	 * 加载试卷预览对象。
	 * @see com.examw.test.service.library.IPaperPreviewService#loadPaperPreview(java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperPreview(String paperId) {
		if (logger.isDebugEnabled()) logger.debug(String.format("加载试卷［id = %s］预览...", paperId));
		if (StringUtils.isEmpty(paperId)) return null;
		Paper paper = this.paperService.loadPaper(paperId);
		if (paper == null) {
			String msg =  null;
			logger.debug(msg = String.format("试卷［id = %s］不存在！", paperId));
			throw new RuntimeException(msg);
		}
		PaperPreview paperPreview = (PaperPreview)((BasePaperInfo)this.paperService.conversion(paper));
		Integer count = 0;
		paperPreview.setStructures(this.changeModel(this.structureDao.loadStructures(paperId), count));
		paperPreview.setTotal(count);
		return paperPreview;
	}
	//数据模型集合转换。
	private List<StructureInfo> changeModel(List<Structure> structures, Integer count){
		if(logger.isDebugEnabled()) logger.debug("开始数据模型集合转换...");
		if(structures == null || structures.size() == 0) return null;
		List<StructureInfo> list = new ArrayList<>();
		for(Structure structure : structures){
			if(structure == null)continue;
			StructureInfo info = this.changeModel(structure);
			if(info != null){
				if(count == null) count = 0;
				count += (info.getTotal() == null ? 0 : info.getTotal());
				list.add(info);
			}
		}
		if(list.size() > 0){
			Collections.sort(list);
		}
		return null;
	}
	//数据模型转换 Structure => StructureInfo。
	private StructureInfo changeModel(Structure structure){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换[Structure => StructureInfo]...");
		if(structure == null) return null;
		StructureInfo structureInfo =  this.paperStructureService.conversion(structure);
		if(structureInfo == null) return null;
		structureInfo.setItems(this.changeModelItems(structure.getItems()));
		return structureInfo;
	}
	//数据模型转换 Set<StructureItem> => Set<StructureItemInfo>。
	private Set<StructureItemInfo> changeModelItems(Set<StructureItem> structureItems){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Set<StructureItem> =>  Set<StructureItemInfo>");
		Set<StructureItemInfo> items = new TreeSet<>();
		for(StructureItem item : structureItems){
			if(item == null) continue;
			StructureItemInfo info = this.paperItemService.conversion(item);
			if(info != null){
				items.add(info);
			}
		}
		return items;
	}
}