package com.examw.test.service.library.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
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
import com.examw.model.Json;
import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.ISourceDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.dao.library.IStructureItemDao;
import com.examw.test.dao.records.IErrorItemRecordDao;
import com.examw.test.dao.records.IItemRecordDao;
import com.examw.test.dao.records.IPaperRecordDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Source;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.domain.library.StructureShareItemScore;
import com.examw.test.domain.records.ErrorItemRecord;
import com.examw.test.domain.records.ItemRecord;
import com.examw.test.domain.records.PaperRecord;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.front.PaperFrontInfo;
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
import com.examw.utils.MD5Util;

/**
 * 试卷服务接口实现类。
 * 
 * @author yangyong.
 * @since 2014-08-06.
 */
public class PaperServiceImpl extends BaseDataServiceImpl<Paper, PaperInfo>
		implements IPaperService {
	private static final Logger logger = Logger
			.getLogger(PaperServiceImpl.class);
	private IPaperDao paperDao;
	private ISourceDao sourceDao;
	private ISubjectDao subjectDao;
	private IStructureDao structureDao;
	private IStructureItemDao structureItemDao;
	private IItemService itemService;
	private Map<Integer, String> typeMap, statusMap;
	private IPaperRecordDao paperRecordDao;
	private IItemRecordDao itemRecordDao;
	private IErrorItemRecordDao errorItemRecordDao;

	/**
	 * 设置试卷数据接口。
	 * 
	 * @param paperDao
	 *            试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if (logger.isDebugEnabled())
			logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}

	/**
	 * 设置来源数据接口。
	 * 
	 * @param sourceDao
	 *            来源数据接口。
	 */
	public void setSourceDao(ISourceDao sourceDao) {
		this.sourceDao = sourceDao;
	}

	/**
	 * 设置所属科目数据接口。
	 * 
	 * @param subjectDao
	 *            所属科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	/**
	 * 设置试卷结构数据接口。
	 * 
	 * @param structureDao
	 *            试卷结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		this.structureDao = structureDao;
	}

	/**
	 * 设置试卷结构下试题数据接口。
	 * 
	 * @param structureItemDao
	 *            试卷结构下试题数据接口。
	 */
	public void setStructureItemDao(IStructureItemDao structureItemDao) {
		this.structureItemDao = structureItemDao;
	}

	/**
	 * 设置试题服务接口。
	 * 
	 * @param itemService
	 *            试题服务接口。
	 */
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * 设置试卷类型集合。
	 * 
	 * @param typeMap
	 *            试卷类型集合。
	 */
	public void setTypeMap(Map<Integer, String> typeMap) {
		this.typeMap = typeMap;
	}

	/**
	 * 设置试卷状态集合。
	 * 
	 * @param statusMap
	 *            试卷状态集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}

	/**
	 * 设置 试卷考试记录
	 * 
	 * @param paperRecordDao
	 * 
	 */
	public void setPaperRecordDao(IPaperRecordDao paperRecordDao) {
		this.paperRecordDao = paperRecordDao;
	}

	/**
	 * 设置 做题记录数据接口
	 * 
	 * @param itemRecordDao
	 * 
	 */
	public void setItemRecordDao(IItemRecordDao itemRecordDao) {
		this.itemRecordDao = itemRecordDao;
	}

	/**
	 * 设置 错题记录数据接口
	 * 
	 * @param errorItemRecordDao
	 * 
	 */
	public void setErrorItemRecordDao(IErrorItemRecordDao errorItemRecordDao) {
		this.errorItemRecordDao = errorItemRecordDao;
	}

	/*
	 * 加载试卷类型名称。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#loadTypeName(java.lang.Integer
	 * )
	 */
	@Override
	public String loadTypeName(Integer type) {
		if (type == null || this.typeMap == null || this.typeMap.size() == 0)
			return null;
		return this.typeMap.get(type);
	}

	/*
	 * 加载试卷状态名称。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#loadStatusName(java.lang
	 * .Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if (status == null || this.statusMap == null
				|| this.statusMap.size() == 0)
			return null;
		return this.statusMap.get(status);
	}

	/*
	 * 查询数据。
	 * 
	 * @see
	 * com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Paper> find(PaperInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询数据...");
		return this.paperDao.findPapers(info);
	}

	/*
	 * 类型转换。
	 * 
	 * @see
	 * com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang
	 * .Object)
	 */
	@Override
	protected PaperInfo changeModel(Paper data) {
		if (logger.isDebugEnabled())
			logger.debug("类型转换...");
		if (data == null)
			return null;
		PaperInfo info = new PaperInfo();
		BeanUtils.copyProperties(data, info, new String[] { "structures" });
		if (data.getSubject() != null) {
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if (data.getSubject().getExam() != null) {
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if (data.getSource() != null) {
			info.setSourceId(data.getSource().getId());
			info.setSourceName(data.getSource().getName());
		}
		if (!StringUtils.isEmpty(data.getType()))
			info.setTypeName(this.loadTypeName(data.getType()));
		if (!StringUtils.isEmpty(data.getStatus()))
			info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}

	/*
	 * 查询数据统计。
	 * 
	 * @see
	 * com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PaperInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询数据统计...");
		return this.paperDao.total(info);
	}

	/*
	 * 更新数据.
	 * 
	 * @see
	 * com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PaperInfo update(PaperInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("更新数据...");
		if (info == null)
			return null;
		boolean isAdded = false;
		Paper data = StringUtils.isEmpty(info.getId()) ? null : this.paperDao
				.load(Paper.class, info.getId());
		if (isAdded = (data == null)) {
			if (StringUtils.isEmpty(info.getId()))
				info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			info.setStatus(PaperStatus.NONE.getValue());
			data = new Paper();
		}
		if (!StringUtils.isEmpty(data.getStatus())) {
			if (data.getStatus() == Paper.STATUS_AUDIT)
				throw new RuntimeException("试卷已审核，不能被修改！");
			if (data.getStatus() == Paper.STATUS_PUBLISH)
				throw new RuntimeException("试卷已发布，不能被修改！");
		}
		if (!isAdded) {
			info.setStatus(data.getStatus());
			info.setCreateTime(data.getCreateTime());
			info.setPublishTime(data.getPublishTime());
		}
		info.setLastTime(new Date());

		BeanUtils.copyProperties(info, data, new String[] { "structures" });

		if (!StringUtils.isEmpty(info.getSubjectId())
				&& (data.getSubject() == null || !data.getSubject().getId()
						.equalsIgnoreCase(info.getSubjectId()))) {
			data.setSubject(this.subjectDao.load(Subject.class,
					info.getSubjectId()));
		}
		if (data.getSubject() != null) {
			info.setSubjectName(data.getSubject().getName());
			if (data.getSubject().getExam() != null) {
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if (!StringUtils.isEmpty(info.getSourceId())
				&& (data.getSource() == null || !data.getSource().getId()
						.equalsIgnoreCase(info.getSourceId()))) {
			data.setSource(this.sourceDao.load(Source.class, info.getSourceId()));
		}
		if (data.getSource() != null) {
			info.setSourceName(data.getSource().getName());
		}
		if (!StringUtils.isEmpty(data.getType()))
			info.setTypeName(this.loadTypeName(data.getType()));
		if (!StringUtils.isEmpty(data.getStatus()))
			info.setStatusName(this.loadStatusName(data.getStatus()));
		if (isAdded)
			this.paperDao.save(data);
		return info;
	}

	/*
	 * 删除数据。
	 * 
	 * @see
	 * com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String
	 * [])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())
			logger.debug("删除数据....");
		if (ids == null || ids.length == 0)
			return;
		for (int i = 0; i < ids.length; i++) {
			Paper data = this.paperDao.load(Paper.class, ids[i]);
			if (data != null) {
				if (logger.isDebugEnabled())
					logger.debug(String.format("删除［%s］...", ids[i]));
				this.paperDao.delete(data);
			}
		}
	}

	/*
	 * 更新数据状态。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#updateStatus(java.lang.String
	 * , java.lang.Integer)
	 */
	@Override
	public void updateStatus(String id, PaperStatus status) {
		if (logger.isDebugEnabled())
			logger.debug(String.format("更新状态：%1$s -> %2$s", id, status));
		String msg = null;
		if (StringUtils.isEmpty(id)) {
			msg = "试卷ID为空！";
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if (status == null)
			status = PaperStatus.NONE;
		Paper data = this.paperDao.load(Paper.class, id);
		if (data == null) {
			msg = String.format("试卷［id=%s］不存在！", id);
		}
		data.setStatus(status.getValue());
		if (status == PaperStatus.AUDIT) {
			// 将试卷下未审核的题目批量审核。
			List<Structure> structures = this.structureDao.finaStructures(data
					.getId());
			if (structures != null && structures.size() > 0) {
				for (Structure structure : structures) {
					this.auditPaperItems(structure);
				}
			}
		}
		if (status == PaperStatus.PUBLISH) {
			data.setPublishTime(new Date());
		}
		data.setLastTime(new Date());
		if (logger.isDebugEnabled())
			logger.debug(String.format("更新状态［%1$s,%2$s］%3$s ＝> %4$s",
					data.getId(), data.getName(),
					PaperStatus.convert(data.getStatus()), status));
	}

	// 审核试卷下的题目。
	private void auditPaperItems(Structure structure) {
		if (structure == null)
			return;
		if (structure.getItems() != null && structure.getItems().size() > 0) {
			for (StructureItem structureItem : structure.getItems()) {
				if (structureItem == null)
					continue;
				if (structureItem.getItem() != null
						&& structureItem.getItem().getStatus() != ItemStatus.AUDIT
								.getValue()) {
					structureItem.getItem().setStatus(
							ItemStatus.AUDIT.getValue());
				}
			}
		}
	}

	/*
	 * 加载试卷结构集合。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#loadStructures(java.lang
	 * .String)
	 */
	@Override
	public List<StructureInfo> loadStructures(String paperId) {
		if (logger.isDebugEnabled())
			logger.debug(String.format("加载试卷结构集合［paperId = %s］...", paperId));
		String msg = null;
		if (StringUtils.isEmpty(paperId)) {
			msg = "试卷ID不存在！";
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		List<StructureInfo> results = new ArrayList<>();
		List<Structure> list = this.structureDao.finaStructures(paperId);
		if (list != null && list.size() > 0) {
			for (Structure s : list) {
				StructureInfo info = new StructureInfo();
				if (this.changeModel(s, info, false)) {
					results.add(info);
				}
			}
		}
		return results;
	}

	// 试卷结构类型转换。
	private boolean changeModel(Structure source, StructureInfo target,
			boolean isChangeItems) {
		if (source == null || target == null)
			return false;
		BeanUtils.copyProperties(source, target, new String[] { "items" });
		if (isChangeItems && source.getItems() != null
				&& source.getItems().size() > 0) {
			Set<StructureItemInfo> items = new TreeSet<StructureItemInfo>(
					new Comparator<StructureItemInfo>() {
						@Override
						public int compare(StructureItemInfo o1,
								StructureItemInfo o2) {
							return o1.getOrderNo() - o2.getOrderNo();
						}
					});
			for (StructureItem structureItem : source.getItems()) {
				if (structureItem == null)
					continue;
				StructureItemInfo structureItemInfo = this
						.changeModel(structureItem);
				if (structureItemInfo != null) {
					items.add(structureItemInfo);
				}
			}
			target.setItems(items);
		}
		return true;
	}

	/*
	 * 更新试卷结构。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#updateStructure(java.lang
	 * .String, com.examw.test.model.library.StructureInfo)
	 */
	@Override
	public void updateStructure(String paperId, StructureInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("更新试卷［paperId = " + paperId + "］结构...");
		String msg = null;
		if (StringUtils.isEmpty(paperId)) {
			msg = "所属试卷ID为空！";
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if (info == null) {
			msg = String.format("更新的试卷［paperId = %s］结构不存在！", paperId);
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if (paper == null) {
			msg = String.format("所属试卷［paperId = %s］不存在！", paperId);
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		boolean isAdded = false;
		Structure data = StringUtils.isEmpty(info.getId()) ? null
				: this.structureDao.load(Structure.class, info.getId());
		if (isAdded = (data == null)) {
			if (StringUtils.isEmpty(info.getId()))
				info.setId(UUID.randomUUID().toString());
			data = new Structure();
			data.setPaper(paper);
		}
		BeanUtils.copyProperties(info, data, new String[] { "items" });
		if (isAdded)
			this.structureDao.save(data);
	}

	/*
	 * 删除试卷结构。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#deleteStructure(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public void deleteStructure(String[] structureIds) {
		if (logger.isDebugEnabled())
			logger.debug("删除试卷结构...");
		if (structureIds == null || structureIds.length == 0)
			return;
		for (int i = 0; i < structureIds.length; i++) {
			if (logger.isDebugEnabled())
				logger.debug(String.format("删除结构［%s］数据...", structureIds[i]));
			Structure data = this.structureDao.load(Structure.class,
					structureIds[i]);
			if (data != null)
				this.structureDao.delete(data);
		}
	}

	/*
	 * 加载试卷结构下最大的排序号。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#loadStructureItemMaxOrderNo
	 * (java.lang.String)
	 */
	@Override
	public Long loadStructureItemMaxOrderNo(String structureId) {
		if (logger.isDebugEnabled())
			logger.debug("加载试卷结构下最大的排序号...");
		return this.structureItemDao.loadMaxOrderNo(structureId);
	}

	/*
	 * 查询试卷结构试题查询。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#loadStructureItems(java.
	 * lang.String, com.examw.test.model.library.StructureItemInfo)
	 */
	@Override
	public DataGrid<StructureItemInfo> loadStructureItems(String paperId,
			StructureItemInfo info) {
		if (logger.isDebugEnabled())
			logger.debug(String.format("查询试卷［paper = %s］结构试题查询...", paperId));
		DataGrid<StructureItemInfo> grid = new DataGrid<StructureItemInfo>();
		List<StructureItemInfo> rows = new ArrayList<>();
		List<StructureItem> list = this.structureItemDao.findStructureItems(
				paperId, info);
		if (list != null && list.size() > 0) {
			for (StructureItem structureItem : list) {
				StructureItemInfo target = this.changeModel(structureItem);
				if (target != null)
					rows.add(target);
			}
		}
		grid.setRows(rows);
		grid.setTotal(this.structureItemDao.total(paperId, info));
		return grid;
	}

	// 类型转换（StructureItem => StructureItemInfo）。
	private StructureItemInfo changeModel(StructureItem source) {
		if (source == null || source.getItem() == null)
			return null;
		StructureItemInfo target = new StructureItemInfo();
		BeanUtils.copyProperties(source, target, new String[] { "item" });
		if (source.getStructure() != null)
			target.setStructureId(source.getStructure().getId());
		target.setItem(this.changeModel(source.getItem(),
				source.getShareItemScores()));
		if (!StringUtils.isEmpty(source.getSerial())) {
			target.getItem().setSerial(source.getSerial());
		}
		if (source.getScore() != null) {
			target.getItem().setScore(source.getScore());
		}
		return target;
	}

	// 类型转换（item => ItemScoreInfo ）。
	private ItemScoreInfo changeModel(Item item,
			Set<StructureShareItemScore> shareItemScores) {
		if (item == null)
			return null;
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
		if (item.getType() != null)
			info.setTypeName(this.itemService.loadTypeName(item.getType()));
		if (item.getStatus() != null)
			info.setStatusName(this.itemService.loadStatusName(item.getStatus()));
		if (item.getOpt() != null)
			info.setOptName(this.itemService.loadOptName(item.getOpt()));
		// 结构试题信息。
		if (shareItemScores != null && shareItemScores.size() > 0) {
			StructureShareItemScore structureShareItemScore = null;
			for (StructureShareItemScore itemScore : shareItemScores) {
				if (itemScore == null || itemScore.getSubItem() == null)
					continue;
				if (!StringUtils.isEmpty(item.getId())
						&& itemScore.getSubItem().getId()
								.equalsIgnoreCase(item.getId())) {
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
			Set<ItemScoreInfo> children = new TreeSet<ItemScoreInfo>(
					new Comparator<ItemScoreInfo>() {
						@Override
						public int compare(ItemScoreInfo o1, ItemScoreInfo o2) {
							return o1.getOrderNo() - o2.getOrderNo();
						}
					});
			for (Item e : item.getChildren()) {
				ItemScoreInfo itemScoreInfo = this.changeModel(e,
						shareItemScores);
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
	 * 更新试卷结构下试题。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#updateStructureItem(java
	 * .lang.String, com.examw.test.model.library.StructureItemInfo)
	 */
	@Override
	public StructureItemInfo updateStructureItem(String paperId,
			StructureItemInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("更新试卷结构下试题...");
		if (StringUtils.isEmpty(paperId) || info == null
				|| info.getItem() == null)
			return null;
		String msg = null;
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if (paper == null) {
			msg = String.format("试卷［id=%s］不存在！", paperId);
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if (paper.getStatus() != PaperStatus.NONE.getValue()) {
			msg = String.format("试卷［％1$s］状态［%2$s］不允许更新！", paper.getName(),
					this.loadStatusName(paper.getStatus()));
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		Structure structure = StringUtils.isEmpty(info.getStructureId()) ? null
				: this.structureDao
						.load(Structure.class, info.getStructureId());
		if (structure == null) {
			msg = String.format("试卷［%1$s］下未找到结构［structureId = %2$s］！",
					paper.getName(), info.getStructureId());
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if (structure.getTotal() > 0) {
			Long count = this.structureItemDao
					.totalItems(info.getStructureId());
			if (count != null && count >= structure.getTotal()) {
				msg = String
						.format("试卷［%1$s］结构下［structureId = %2$s］题目数量已满［total = %3$d］［count = %4$d］！",
								paper.getName(), info.getStructureId(),
								structure.getTotal(), count);
				if (logger.isDebugEnabled())
					logger.debug(msg);
				throw new RuntimeException(msg);
			}
		}
		if (info.getItem() != null) {
			if (StringUtils.isEmpty(info.getItem().getOpt())
					&& paper.getType() != null) {// 试卷类型
				info.getItem().setOpt(paper.getType());
			}
			if (paper.getSubject() != null) {
				if (StringUtils.isEmpty(info.getItem().getSubjectId())) {// 所属科目。
					info.getItem().setSubjectId(paper.getSubject().getId());
				}
				if (StringUtils.isEmpty(info.getItem().getExamId())
						&& paper.getSubject().getExam() != null) { // 所属考试
					info.getItem().setExamId(
							paper.getSubject().getExam().getId());
				}
			}
			if (StringUtils.isEmpty(info.getItem().getSourceId())
					&& paper.getSource() != null) {// 试卷来源
				info.getItem().setSourceId(paper.getSource().getId());
			}
			if (structure != null && structure.getScore() != null
					&& structure.getScore().compareTo(BigDecimal.ZERO) == 1) {// 题目分数
				info.getItem().setScore(structure.getScore());
			}
		}
		Set<StructureShareItemScore> shareItemScores = null;
		if (info.getItem().getType() == ItemType.SHARE_TITLE.getValue()
				|| info.getItem().getType() == ItemType.SHARE_ANSWER.getValue()) {
			shareItemScores = new HashSet<>();
		}
		Item item = this.itemService
				.updateItem(info.getItem(), shareItemScores);
		if (item == null) {
			msg = "更新试题内容失败！";
			if (logger.isDebugEnabled())
				logger.debug(msg);
			throw new RuntimeException(msg);
		}
		StructureItem data = StringUtils.isEmpty(info.getId()) ? null
				: this.structureItemDao.load(StructureItem.class, info.getId());
		boolean isAdded = false;
		if (isAdded = (data == null)) {
			info.setCreateTime(new Date());
			info.setId(MD5Util.MD5(String.format("%1$s-%2$s",
					structure.getId(), item.getId())));
			data = this.structureItemDao
					.load(StructureItem.class, info.getId());
			if (isAdded = (data == null))
				data = new StructureItem();
		}
		if (!isAdded)
			info.setCreateTime(data.getCreateTime());
		data.setStructure(structure);
		BeanUtils.copyProperties(info, data, new String[] { "item" });
		data.setSerial(info.getItem().getSerial());
		data.setScore(info.getItem().getScore());
		data.setItem(item);
		if (shareItemScores != null && shareItemScores.size() > 0) {
			for (StructureShareItemScore structureShareItemScore : shareItemScores) {
				if (structureShareItemScore == null)
					continue;
				structureShareItemScore.setId(MD5Util.MD5(String.format(
						"%1$s-%2$s", data.getId(),
						structureShareItemScore.getId())));
				structureShareItemScore.setStructureItem(data);
			}
		}
		data.setShareItemScores(shareItemScores);
		if (isAdded)
			this.structureItemDao.save(data);
		return this.changeModel(data);
	}

	/*
	 * 删除试卷结构下的数据。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#deleteStructureItem(java
	 * .lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public void deleteStructureItem(String[] ids) {
		if (logger.isDebugEnabled())
			logger.debug("删除试卷下试题数据...");
		if (ids == null || ids.length == 0)
			return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))
				continue;
			if (logger.isDebugEnabled())
				logger.debug("删除试卷下试题：" + ids[i]);
			StructureItem data = this.structureItemDao.load(
					StructureItem.class, ids[i]);
			if (data != null)
				this.structureItemDao.delete(data);
		}
	}

	/*
	 * 加载试卷预览。
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#loadPaperPreview(java.lang
	 * .String)
	 */
	@Override
	public PaperPreview loadPaperPreview(String paperId) {
		if (logger.isDebugEnabled())
			logger.debug(String.format("加载试卷［id = %s］预览...", paperId));
		if (StringUtils.isEmpty(paperId))
			return null;
		this.paperDao.evict(Paper.class);
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if (paper == null) {
			if (logger.isDebugEnabled())
				logger.debug(String.format("试卷［id = %s］不存在！", paperId));
			return null;
		}
		PaperPreview paperPreview = this.changeModel(paper);
		if (paperPreview == null)
			return null;
		List<StructureInfo> structures = new ArrayList<>();
		List<Structure> list = this.structureDao.finaStructures(paperId);
		int total = 0; // 总题数
		if (list != null && list.size() > 0) {
			for (Structure s : list) {
				StructureInfo info = new StructureInfo();
				if (this.changeModel(s, info, true)) {
					structures.add(info);
					total += info.getTotal(); // 计算题目的总数
				}
			}
		}
		paperPreview.setStructures(structures);
		paperPreview.setTotal(total); // 设置题目的总数
		return paperPreview;
	}

	/********************************************************
	 * 前台调用方法
	 ********************************************************/
	/*
	 * 加载试卷基本信息
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#loadPaperInfo(java.lang.
	 * String)
	 */
	@Override
	public PaperPreview loadPaperInfo(String paperId) {
		if (logger.isDebugEnabled())
			logger.debug(String.format("加载试卷［id = %s］预览...", paperId));
		if (StringUtils.isEmpty(paperId))
			return null;
		this.paperDao.evict(Paper.class);
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if (paper == null) {
			if (logger.isDebugEnabled())
				logger.debug(String.format("试卷［id = %s］不存在！", paperId));
			return null;
		}
		PaperPreview paperPreview = this.changeModel(paper);
		if (paperPreview == null)
			return null;
		List<StructureInfo> structures = new ArrayList<>();
		List<Structure> list = this.structureDao.finaStructures(paperId);
		int total = 0; // 总题数
		if (list != null && list.size() > 0) {
			for (Structure s : list) {
				StructureInfo info = new StructureInfo();
				if (this.changeModel(s, info, false)) { // 不加载题目
					structures.add(info);
					total += info.getTotal(); // 计算题目的总数
				}
			}
		}
		paperPreview.setStructures(structures);
		paperPreview.setTotal(total); // 设置题目的总数
		return paperPreview;
	}

	/*
	 * 加载试卷详情并添加考试记录
	 * 
	 * @see
	 * com.examw.test.service.library.IPaperService#loadPaperPreviewAndAddRecord
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperPreviewAndAddRecord(final String paperId, final
			String userId) {
		PaperPreview paperPreview = this.loadPaperPreview(paperId);
		if (paperPreview != null)
		{
			paperPreview.setLeftTime(60*paperPreview.getTime());
			PaperRecord	record = this.paperRecordDao.load(paperId, userId);
			if(record != null && record.getStatus().equals(PaperRecord.STATUS_UNDONE)){
				final Date time = record.getLastTime();
				//没做完 加上用户答案
				List<ItemRecord> list = this.itemRecordDao.findItemRecords(new ItemRecord(){
					private static final long serialVersionUID = 1L;
					@Override
					public String getPaperId() {return paperId;}
					@Override
					public String getUserId() {return userId;}
					@Override
					public Date getLastTime() {return time;}
				});
				setUserAnswer(paperPreview,list);
				paperPreview.setLeftTime(paperPreview.getLeftTime() - (record.getUsedTime()==null?0:record.getUsedTime()));
			}
			savePaperRecord(paperId, userId); // 保存考试记录
		}
		return paperPreview;
	}
	/*
	 * 以前做过的题目附上用户答案
	 */
	private void setUserAnswer(PaperPreview paperPreview,List<ItemRecord> list){
		if(list == null || list.size()==0) return;
		List<StructureInfo> structures = paperPreview.getStructures();
		if(structures == null || structures.size()==0) return ;
		for(StructureInfo s:structures){
			Set<StructureItemInfo> items = s.getItems();
			if(items == null || items.size()==0) continue;
			for(StructureItemInfo item : items){
				if(item.getItem() == null) continue;
				//为每题设置用户答案
				setUserAnswer(item,list);
			}
		}
	}
	/*
	 * 设置用户答案
	 */
	private void setUserAnswer(StructureItemInfo item,List<ItemRecord> list){
		if(item.getType().equals(Item.TYPE_SHARE_ANSWER)||item.getType().equals(Item.TYPE_SHARE_TITLE)){
			Set<ItemScoreInfo> set = item.getItem().getChildren();
			for(ItemScoreInfo info :set){
				for(ItemRecord record:list){
					if((item.getId()+"#"+info.getId()).equals(record.getStructureItemId())){
						info.setUserAnswer(record.getAnswer());
						info.setUserScore(record.getScore());
						info.setAnswerStatus(record.getStatus());
						break;
					}
				}
			}
		}else{
			for(ItemRecord record:list){
				if(item.getId().equals(record.getStructureItemId())){
					item.getItem().setUserAnswer(record.getAnswer());
					item.getItem().setUserScore(record.getScore());
					item.getItem().setAnswerStatus(record.getStatus());
					break;
				}
			}
		}
	}
	/*
	 * 保存考试记录
	 * @param paperId
	 * @param userId
	 */
	private void savePaperRecord(String paperId, String userId) {
		//做一次保存一次考试记录
		PaperRecord	record = new PaperRecord();
		record.setId(UUID.randomUUID().toString());
		record.setPaperId(paperId);
		record.setUserId(userId);
		record.setLastTime(new Date());
		record.setStatus(PaperRecord.STATUS_UNDONE);
		paperRecordDao.save(record);
	}

	/*
	 * 查询前台数据
	 * @see
	 * com.examw.test.service.library.IPaperService#loadPaperFrontInfo(com.examw
	 * .test.model.library.PaperInfo, java.lang.String)
	 */
	@Override
	public List<PaperFrontInfo> loadPaperFrontInfo(PaperInfo info, String userId) {
		List<Paper> list = this.paperDao.findPapers(info);
		List<PaperFrontInfo> result = new ArrayList<PaperFrontInfo>();
		if (list == null || list.size() == 0)
			return result;
		for (Paper paper : list) {
			PaperFrontInfo target = this.changeFrontModel(paper, userId);
			if (target != null)
				result.add(target);
		}
		return result;
	}

	/*
	 * 模型转换
	 */
	private PaperFrontInfo changeFrontModel(Paper data, String userId) {
		PaperInfo paperInfo = this.changeModel(data);
		if (paperInfo == null)
			return null;
		paperInfo.setTotal(getTotalNum(data));
		PaperFrontInfo info = new PaperFrontInfo();
		BeanUtils.copyProperties(paperInfo, info);
		info.setMaxScore(this.paperRecordDao.findMaxScore(data.getId()));
		info.setUserSum(this.paperRecordDao.findUserSum(data.getId())
				.intValue());
		if (!StringUtils.isEmpty(userId)) {
			PaperRecord record = this.paperRecordDao.load(data.getId(), userId);
			if (record != null) {
				info.setUserStatus(record.getStatus());
				if (record.getStatus().equals(PaperRecord.STATUS_DONE)) {
					info.setUserScore(record.getScore());
					info.setUserTime(record.getTime());
					info.setUserExamTime(record.getLastTime());
				}
			}
		}
		return info;
	}

	/*
	 * 计算总题数
	 */
	private Integer getTotalNum(Paper data) {
		List<Structure> list = this.structureDao.finaStructures(data.getId());
		int total = 0; // 总题数
		if (list != null && list.size() > 0) {
			for (Structure s : list) {
				StructureInfo info = new StructureInfo();
				if (this.changeModel(s, info, false)) { // 不加载题目
					total += info.getTotal(); // 计算题目的总数
				}
			}
		}
		return total;
	}

	/*
	 * 查询统计
	 * @see
	 * com.examw.test.service.library.IPaperService#totalPaperFrontInfo(com.
	 * examw.test.model.library.PaperInfo)
	 */
	@Override
	public Long totalPaperFrontInfo(PaperInfo info) {
		return this.paperDao.total(info);
	}

	@Override
	public Json submitPaper(Integer limitTime, String chooseAnswers,
			String textAnswers, Integer model, String paperId, String userId) {
		Json json = new Json();
		PaperRecord record = this.paperRecordDao.load(paperId, userId);
		Paper paper = this.paperDao.load(Paper.class, paperId);
		record.setStatus(model);
		record.setUsedTime(paper.getTime() * 60 - limitTime); // 使用时间
		List<ItemRecord> itemRecords = new ArrayList<ItemRecord>();
		if (!StringUtils.isEmpty(chooseAnswers)) {
			try {
				chooseAnswers = URLDecoder.decode(chooseAnswers, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String[] answers = chooseAnswers.split("&");// 拆分题目与答案
			for (String s : answers) {
				ItemRecord itemRecord = new ItemRecord();
				itemRecord.setId(UUID.randomUUID().toString());
				itemRecord.setPaperId(paperId);
				itemRecord.setUserId(userId);
				itemRecord.setLastTime(new Date());
				String[] arr = s.split("=");
				itemRecord.setStructureItemId(arr[0]);
				if (arr.length > 1) { // 没有答案
					itemRecord.setAnswer(arr[1]);
				} 
				itemRecords.add(itemRecord);
			}
		}
		if (!StringUtils.isEmpty(textAnswers)) {

		}
		if(logger.isDebugEnabled())	logger.debug("log ==== "+model);
		if (model.equals(PaperRecord.STATUS_DONE)) {
			if(logger.isDebugEnabled())	logger.debug("进行试卷的评判分");
			PaperPreview preview = judgePaper(record, paperId, itemRecords);
			json.setData(preview);
		}
		this.itemRecordDao.insertItemRecordList(itemRecords);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}

	private PaperPreview judgePaper(PaperRecord record, String paperId,
			List<ItemRecord> itemRecordList) {
		
		PaperPreview paper = this.loadPaperPreview(paperId);
		List<StructureInfo> structures = paper.getStructures();
		if (structures == null || structures.size() == 0)
			return null;
		BigDecimal paperTotalScore = BigDecimal.ZERO; // 试卷总分
		List<ErrorItemRecord> errorRecords = new ArrayList<ErrorItemRecord>();
		for (StructureInfo s : structures) {
			Set<StructureItemInfo> items = s.getItems();
			if (items == null || items.size() == 0)
				continue;
			// 评分规则
			BigDecimal min = s.getMin(); // 最低得分
			BigDecimal per = s.getScore(); // 每题得分
			BigDecimal actualRuleTotal = BigDecimal.ZERO;
			for (StructureItemInfo item : items) { // 结构题目
				for (ItemRecord itemRecord : itemRecordList) {
					if (!itemRecord.getStructureItemId().contains("#")) { // 不包含#,是单题
						if (item.getId()
								.equals(itemRecord.getStructureItemId())) {
							// 判断对错
							if(!judgeItemIsRight(item.getItem(), itemRecord, min, per))
								errorRecords.add(createErrorRecord(item.getItem().getId(),itemRecord));
							actualRuleTotal = actualRuleTotal.add(itemRecord.getScore());
							break;
						}
					} else { // 复合题
						Set<ItemScoreInfo> children = item.getItem().getChildren();
						for (ItemScoreInfo child : children) {
							if ((item.getId() + "#" + child.getId()).equals(itemRecord.getStructureItemId())) {
								// 判断对错
								if(!judgeItemIsRight(child, itemRecord, min, per))
									errorRecords.add(createErrorRecord(item.getItem().getId(),itemRecord));
								actualRuleTotal = actualRuleTotal.add(itemRecord.getScore());
								break;
							}
						}
					}
				}
			}
			if (actualRuleTotal.compareTo(BigDecimal.ZERO) == -1) {
				actualRuleTotal = BigDecimal.ZERO;
			}
			paperTotalScore = paperTotalScore.add(actualRuleTotal); // 试卷总分
		}
		record.setScore(paperTotalScore);
		record.setTime(record.getUsedTime());
		this.errorItemRecordDao.insertRecordList(errorRecords); 	//保存错题记录
		return paper;
	}
	private ErrorItemRecord createErrorRecord(String itemId,ItemRecord itemRecord){
		ErrorItemRecord error = new ErrorItemRecord();
		error.setCreateTime(new Date());
		error.setId(UUID.randomUUID().toString());
		error.setStructureItemId(itemRecord.getStructureItemId());
		error.setItemId(itemId);
		error.setHistoryAnswers(itemRecord.getAnswer());
		error.setUserId(itemRecord.getUserId());
		return error;
	}
	/*
	 * 判断题目是对是错
	 */
	private boolean judgeItemIsRight(ItemScoreInfo item,
			ItemRecord itemRecord, BigDecimal min, BigDecimal per) {
		if (StringUtils.isEmpty(itemRecord.getAnswer())) {
			itemRecord.setScore(min == null ? BigDecimal.ZERO : min); // 得0分或者负分
			itemRecord.setStatus(ItemRecord.STATUS_NULL); // 没有作答
			return false;
		}
		switch (item.getType()) {
		case Item.TYPE_SINGLE: // 单选
		case Item.TYPE_JUDGE: // 判断
			if (item.getAnswer().equals(itemRecord.getAnswer())) // 答对
			{
				itemRecord.setScore(per); // 得标准分
				itemRecord.setStatus(ItemRecord.STATUS_RIGHT); // 答对
				return true;
			} else {
				itemRecord.setScore((min == null || min
						.compareTo(BigDecimal.ZERO) == 1) ? BigDecimal.ZERO
						: min); // 得0分或者负分
				itemRecord.setStatus(ItemRecord.STATUS_WRONG); // 答错
				return false;
			}
		case Item.TYPE_MULTY: // 多选
		case Item.TYPE_UNCERTAIN: // 不定项
			String[] arr = itemRecord.getAnswer().split(",");
			String answer = item.getAnswer();
			int total = 0;
			for (String a : arr) {
				if (answer.indexOf(a) == -1) { // 包含有错误答案
					itemRecord.setScore((min == null || min
							.compareTo(BigDecimal.ZERO) == 1) ? BigDecimal.ZERO
							: min); // 得0分或者负分
					itemRecord.setStatus(ItemRecord.STATUS_WRONG); // 答错
					return false;
				} else {
					total++;
				}
			}
			if (total == answer.split(",").length) { // 全对,得满分
				itemRecord.setScore(per); // 得标准分
				itemRecord.setStatus(ItemRecord.STATUS_RIGHT); // 答对
				return true;
			} else {
				itemRecord.setScore((min == null || min
						.compareTo(BigDecimal.ZERO) == -1) ? BigDecimal.ZERO
						: min.multiply(new BigDecimal(total))); // 得0分或者负分
				itemRecord.setStatus(ItemRecord.STATUS_LESS); // 少选
				return false;
			}
		default:
			return true;
		}
	}
	/*
	 * 加载试卷考试记录详情
	 * @see com.examw.test.service.library.IPaperService#loadPaperRecordDetail(java.lang.String, java.lang.String)
	 */
	@Override
	public PaperFrontInfo loadPaperRecordDetail(final String paperId,final String userId) {
		PaperFrontInfo paperFrontInfo = null;
		PaperPreview paperPreview =	this.loadPaperPreview(paperId);
		if (paperPreview != null)
		{
			paperFrontInfo = new PaperFrontInfo();
			BeanUtils.copyProperties(paperPreview, paperFrontInfo);
			PaperRecord	record = this.paperRecordDao.load(paperId, userId);
			if(record != null && record.getStatus().equals(PaperRecord.STATUS_UNDONE)){
				return paperFrontInfo;
			}else if(record!=null && record.getStatus().equals(PaperRecord.STATUS_DONE)){
				paperFrontInfo.setUserTime(record.getTime());
				paperFrontInfo.setUserScore(record.getScore());
				final Date time = record.getLastTime();
				//没做完 加上用户答案
				List<ItemRecord> list = this.itemRecordDao.findItemRecords(new ItemRecord(){
					private static final long serialVersionUID = 1L;
					@Override
					public String getPaperId() {return paperId;}
					@Override
					public String getUserId() {return userId;}
					@Override
					public Date getLastTime() {return time;}
				});
				setUserAnswer(paperPreview,list);
			}
		}
		return paperFrontInfo;
	}
}
