package com.examw.test.service.library.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.library.IPaperItemService;
import com.examw.test.service.library.IPaperPreviewService;

/**
 * 试卷预览服务实现类。
 * 
 * @author yangyong
 * @since 2014年9月23日
 */
public class PaperPreviewServiceImpl implements IPaperPreviewService {
	private static final Logger logger = Logger.getLogger(PaperPreviewServiceImpl.class);
	private IPaperDao paperDao;
	private IStructureDao structureDao;
	private IPaperItemService paperItemService;
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		this.paperDao = paperDao;
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
		this.paperDao.evict(Paper.class);
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if (paper == null) {
			if (logger.isDebugEnabled())logger.debug(String.format("试卷［id = %s］不存在！", paperId));
			return null;
		}
		PaperPreview paperPreview = new PaperPreview();
		BeanUtils.copyProperties(paper, paperPreview);
		Integer count = 0;
		List<StructureInfo> structures = this.changeModel(this.structureDao.finaStructures(paperId), count);
		if(structures != null && structures.size() > 0){
			Collections.sort(structures, new Comparator<StructureInfo>() {
				@Override
				public int compare(StructureInfo o1, StructureInfo o2) {
					 return o1.getOrderNo() - o1.getOrderNo();
				}
			});
		}
		paperPreview.setStructures(structures);
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
		return list.size() > 0 ? list : null;
	}
	//数据模型转换。
	private StructureInfo changeModel(Structure structure){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换[Structure => StructureInfo]...");
		if(structure == null) return null;
		StructureInfo structureInfo = new StructureInfo();
		BeanUtils.copyProperties(structure, structureInfo, new String[]{"items"});
		if(structure.getItems() != null){
			Set<StructureItemInfo> items = new TreeSet<StructureItemInfo>();
			for(StructureItem structureItem : structure.getItems()){
				StructureItemInfo info = this.paperItemService.conversion(structureItem);
				if(info != null){
					items.add(info);
				}
			}
			structureInfo.setItems(items.size() > 0 ? items : null);
		}
		return structureInfo;
	}
}