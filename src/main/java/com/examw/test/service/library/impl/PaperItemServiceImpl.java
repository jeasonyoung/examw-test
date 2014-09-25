package com.examw.test.service.library.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.dao.library.IStructureItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.domain.library.StructureShareItemScore;
import com.examw.test.model.library.ItemScoreInfo;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperItemService;
import com.examw.test.service.library.ItemType;
import com.examw.test.service.library.PaperStatus;
import com.examw.utils.MD5Util;

/**
 * 试卷题目服务实现类。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
public class PaperItemServiceImpl extends BaseDataServiceImpl<StructureItem,StructureItemInfo> implements IPaperItemService {
	private static final Logger logger = Logger.getLogger(PaperItemServiceImpl.class);
	private IStructureItemDao  structureItemDao;
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
	 * 设置试卷结构试题数据接口。
	 * @param structureItemDao 
	 *	  structureItemDao
	 */
	public void setStructureItemDao(IStructureItemDao structureItemDao) {
		if(logger.isDebugEnabled())logger.debug("注入试卷结构试题数据接口...");
		this.structureItemDao = structureItemDao;
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
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/*
	 * 加载试卷结构下最大的排序号。
	 * @see com.examw.test.service.library.IPaperItemService#loadStructureItemMaxOrderNo(java.lang.String)
	 */
	@Override
	public Long loadStructureItemMaxOrderNo(String structureId) {
		if (logger.isDebugEnabled())logger.debug("加载试卷结构下最大的排序号...");
		return this.structureItemDao.loadMaxOrderNo(structureId);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<StructureItem> find(StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.structureItemDao.findStructureItems(info);
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
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected StructureItemInfo changeModel(StructureItem source) {
		if(logger.isDebugEnabled())logger.debug("数据模型转换...");
		if (source == null || source.getItem() == null)return null;
		StructureItemInfo target = new StructureItemInfo();
		BeanUtils.copyProperties(source, target, new String[] { "item" });
		if (source.getStructure() != null)target.setStructureId(source.getStructure().getId());
		target.setItem(this.changeModel(source.getItem(),source.getShareItemScores()));
		if (!StringUtils.isEmpty(source.getSerial())) {
			target.getItem().setSerial(source.getSerial());
		}
		if (source.getScore() != null) {
			target.getItem().setScore(source.getScore());
		}
		return target;
	}
	// 类型转换（item => ItemScoreInfo ）。
	private ItemScoreInfo changeModel(Item item,Set<StructureShareItemScore> shareItemScores) {
		if (item == null)return null;
		ItemScoreInfo info = new ItemScoreInfo();
		// 试题信息。
		BeanUtils.copyProperties(item, info, new String[] { "children" });
		if (item.getSubject() != null) {
			info.setSubjectId(item.getSubject().getId());
			info.setSubjectName(item.getSubject().getName());
			if (item.getSubject().getExam() != null) {
				info.setExamId(item.getSubject().getExam().getId());
				info.setExamName(item.getSubject().getExam().getName());
			}
		}
		if (item.getSource() != null) {
			info.setSourceId(item.getSource().getId());
			info.setSourceName(item.getSource().getName());
		}
		if (item.getType() != null)info.setTypeName(this.itemService.loadTypeName(item.getType()));
		if (item.getStatus() != null)info.setStatusName(this.itemService.loadStatusName(item.getStatus()));
		if (item.getOpt() != null) info.setOptName(this.itemService.loadOptName(item.getOpt()));
		// 结构试题信息。
		if (shareItemScores != null && shareItemScores.size() > 0) {
			StructureShareItemScore structureShareItemScore = null;
			for (StructureShareItemScore itemScore : shareItemScores) {
				if (itemScore == null || itemScore.getSubItem() == null)continue;
				if (!StringUtils.isEmpty(item.getId()) && itemScore.getSubItem().getId().equalsIgnoreCase(item.getId())) {
					structureShareItemScore = itemScore;
					break;
				}
			}
			if (structureShareItemScore != null) {
				info.setSerial(structureShareItemScore.getSerial());
				info.setScore(structureShareItemScore.getScore());
			}
		}
		if (item.getChildren() != null && item.getChildren().size() > 0) {
			Set<ItemScoreInfo> children = new TreeSet<ItemScoreInfo>();
			for (Item e : item.getChildren()) {
				ItemScoreInfo itemScoreInfo = this.changeModel(e,shareItemScores);
				if (itemScoreInfo != null) {
					itemScoreInfo.setPid(info.getId());
					children.add(itemScoreInfo);
				}
			}
			if (children.size() > 0)
				info.setChildren(children);
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
		return this.structureItemDao.total(info);
	}
	/*
	 * 更新试卷试题。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public StructureItemInfo update(StructureItemInfo info) {
		if (logger.isDebugEnabled())logger.debug("更新试卷试题...");
		if (info == null || StringUtils.isEmpty(info.getPaperId()) || info.getItem() == null )return null;
		String msg = null;
		Paper paper = this.paperDao.load(Paper.class, info.getPaperId());
		if (paper == null) {
			msg = String.format("试卷［id=%s］不存在！", info.getPaperId());
			if (logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if (paper.getStatus() != PaperStatus.NONE.getValue()) {
			msg = String.format("试卷［％1$s］状态［%2$d］不允许更新！", paper.getName(), paper.getStatus());
			if (logger.isDebugEnabled())logger.debug(msg);
			throw new RuntimeException(msg);
		}
		Structure structure = StringUtils.isEmpty(info.getStructureId()) ? null : this.structureDao.load(Structure.class, info.getStructureId());
		if (structure == null) {
			msg = String.format("试卷［%1$s］下未找到结构［structureId = %2$s］！", paper.getName(), info.getStructureId());
			if (logger.isDebugEnabled())logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if (structure.getTotal() > 0) {
			Long count = this.structureItemDao.totalItems(info.getStructureId());
			if (count != null && count >= structure.getTotal()) {
				msg = String.format("试卷［%1$s］结构下［structureId = %2$s］题目数量已满［total = %3$d］［count = %4$d］！",paper.getName(), info.getStructureId(),structure.getTotal(), count);
				if (logger.isDebugEnabled())logger.debug(msg);
				throw new RuntimeException(msg);
			}
		}
		if (info.getItem() != null) {
			if (StringUtils.isEmpty(info.getItem().getOpt()) && paper.getType() != null) {// 试卷类型
				info.getItem().setOpt(paper.getType());
			}
			if (paper.getSubject() != null) {
				if (StringUtils.isEmpty(info.getItem().getSubjectId())) {// 所属科目。
					info.getItem().setSubjectId(paper.getSubject().getId());
				}
				if (StringUtils.isEmpty(info.getItem().getExamId()) && paper.getSubject().getExam() != null) { // 所属考试
					info.getItem().setExamId(paper.getSubject().getExam().getId());
				}
			}
			if (StringUtils.isEmpty(info.getItem().getSourceId()) && paper.getSource() != null) {// 试卷来源
				info.getItem().setSourceId(paper.getSource().getId());
			}
			if (structure != null && structure.getScore() != null && structure.getScore().compareTo(BigDecimal.ZERO) == 1) {// 题目分数
				info.getItem().setScore(structure.getScore());
			}
		}
		Set<StructureShareItemScore> shareItemScores = null;
		if (info.getItem().getType() == ItemType.SHARE_TITLE.getValue() || info.getItem().getType() == ItemType.SHARE_ANSWER.getValue()) {
			shareItemScores = new HashSet<>();
		}
		Item item = this.itemService.updateItem(info.getItem(), shareItemScores);
		if (item == null) {
			msg = "更新试题内容失败！";
			if (logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		StructureItem data = StringUtils.isEmpty(info.getId()) ? null : this.structureItemDao.load(StructureItem.class, info.getId());
		boolean isAdded = false;
		if (isAdded = (data == null)) {
			info.setCreateTime(new Date());
			info.setId(MD5Util.MD5(String.format("%1$s-%2$s", structure.getId(), item.getId())));
			data = this.structureItemDao.load(StructureItem.class, info.getId());
			if (isAdded = (data == null))data = new StructureItem();
		}
		if (!isAdded)info.setCreateTime(data.getCreateTime());
		data.setStructure(structure);
		BeanUtils.copyProperties(info, data, new String[] { "item" });
		data.setSerial(info.getItem().getSerial());
		data.setScore(info.getItem().getScore());
		data.setItem(item);
		if (shareItemScores != null && shareItemScores.size() > 0) {
			for (StructureShareItemScore structureShareItemScore : shareItemScores) {
				if (structureShareItemScore == null)continue;
				structureShareItemScore.setId(MD5Util.MD5(String.format("%1$s-%2$s", data.getId(),structureShareItemScore.getId())));
				structureShareItemScore.setStructureItem(data);
			}
		}
		data.setShareItemScores(shareItemScores);
		if (isAdded) this.structureItemDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除试卷试题。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())logger.debug("删除试卷下试题数据...");
		if (ids == null || ids.length == 0)return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))continue;
			if (logger.isDebugEnabled()) logger.debug("删除试卷下试题：" + ids[i]);
			StructureItem data = this.structureItemDao.load(StructureItem.class, ids[i]);
			if (data != null)this.structureItemDao.delete(data);
		}
	}
}