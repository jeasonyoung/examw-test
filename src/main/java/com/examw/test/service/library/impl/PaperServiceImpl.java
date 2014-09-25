package com.examw.test.service.library.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.ISourceDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.dao.settings.IAreaDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Source;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.ItemStatus;
import com.examw.test.service.library.PaperStatus;
/**
 * 试卷服务接口实现类。
 * 
 * @author yangyong.
 * @since 2014-08-06.
 */
public class PaperServiceImpl extends BaseDataServiceImpl<Paper, PaperInfo> implements IPaperService {
	private static final Logger logger = Logger.getLogger(PaperServiceImpl.class);
	private IPaperDao paperDao;
	private ISourceDao sourceDao;
	private ISubjectDao subjectDao;
	private IStructureDao structureDao;
	private IAreaDao areaDao;
	private Map<Integer, String> typeMap, statusMap;
	/**
	 * 设置试卷数据接口。
	 * @param paperDao
	 * 试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if (logger.isDebugEnabled())logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/**
	 * 设置来源数据接口。
	 * @param sourceDao
	 * 来源数据接口。
	 */
	public void setSourceDao(ISourceDao sourceDao) {
		if(logger.isDebugEnabled())logger.debug("注入来源数据接口...");
		this.sourceDao = sourceDao;
	}
	/**
	 * 设置所属科目数据接口。
	 * @param subjectDao
	 * 所属科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled())logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置试卷结构数据接口。
	 * @param structureDao
	 * 试卷结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		if(logger.isDebugEnabled())logger.debug("注入试卷数据接口...");
		this.structureDao = structureDao;
	}
	/**
	 * 设置所属地区数据接口。
	 * @param areaDao 
	 *	  所属地区数据接口。
	 */
	public void setAreaDao(IAreaDao areaDao) {
		this.areaDao = areaDao;
	}
	/**
	 * 设置试卷类型集合。 
	 * @param typeMap
	 * 试卷类型集合。
	 */
	public void setTypeMap(Map<Integer, String> typeMap) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷类型集合...");
		this.typeMap = typeMap;
	}
	/**
	 * 设置试卷状态集合。
	 * @param statusMap
	 * 试卷状态集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled())logger.debug("注入试卷状态集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载试卷类型名称。
	 * @see  com.examw.test.service.library.IPaperService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(logger.isDebugEnabled())logger.debug(String.format("加载试卷类型[type=%d]名称...", type));
		if (type == null || this.typeMap == null || this.typeMap.size() == 0)return null;
		return this.typeMap.get(type);
	}
	/*
	 * 加载试卷状态名称。
	 * @see com.examw.test.service.library.IPaperService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled())logger.debug(String.format("加载试卷状态[status=%d]名称...", status));
		if (status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询试卷数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Paper> find(PaperInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询数据...");
		return this.paperDao.findPapers(info);
	}
	/*
	 * 试卷模型类型转换。
	 * @see  com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PaperInfo changeModel(Paper data) {
		if (logger.isDebugEnabled())logger.debug("试卷模型类型转换...");
		if (data == null) return null;
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
		if(data.getArea() != null){
			info.setAreaId(data.getArea().getId());
			info.setAreaName(data.getArea().getName());
		}
		if (!StringUtils.isEmpty(data.getType())) info.setTypeName(this.loadTypeName(data.getType()));
		if (!StringUtils.isEmpty(data.getStatus())) info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询试卷数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PaperInfo info) {
		if (logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.paperDao.total(info);
	}
	/*
	 * 加载试卷基本信息。
	 * @see com.examw.test.service.library.IPaperService#loadPaperInfo(java.lang.String)
	 */
	@Override
	public PaperInfo loadPaperInfo(String paperId) {
		if(logger.isDebugEnabled())logger.debug(String.format("加载试卷[%s]基本信息...", paperId));
		if(StringUtils.isEmpty(paperId)) return null;
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null) return null;
		return this.changeModel(paper);
	}
	/*
	 * 更新数据.
	 * @see  com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PaperInfo update(PaperInfo info) {
		if (logger.isDebugEnabled()) logger.debug("更新数据...");
		if (info == null) return null;
		boolean isAdded = false;
		Paper data = StringUtils.isEmpty(info.getId()) ? null : this.paperDao.load(Paper.class, info.getId());
		if (isAdded = (data == null)) {
			if (StringUtils.isEmpty(info.getId()))info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			info.setStatus(PaperStatus.NONE.getValue());
			data = new Paper();
		}
		if (!isAdded) {
			if (data.getStatus() == Paper.STATUS_AUDIT)throw new RuntimeException("试卷已审核，不能被修改！");
			if (data.getStatus() == Paper.STATUS_PUBLISH)throw new RuntimeException("试卷已发布，不能被修改！");
			info.setStatus(data.getStatus());
			info.setCreateTime(data.getCreateTime());
			info.setPublishTime(data.getPublishTime());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		//科目。
		if (!StringUtils.isEmpty(info.getSubjectId())) {
			data.setSubject(this.subjectDao.load(Subject.class,info.getSubjectId()));
		}
		//来源。
		if (!StringUtils.isEmpty(info.getSourceId())) {
			data.setSource(this.sourceDao.load(Source.class, info.getSourceId()));
		}else {
			data.setSource(null);
		}
		//地区
		if(!StringUtils.isEmpty(info.getAreaId())){
			data.setArea(this.areaDao.load(Area.class, info.getAreaId()));
		}else {
			data.setArea(null);
		}
		if (isAdded)this.paperDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())logger.debug("删除数据....");
		if (ids == null || ids.length == 0) return;
		for (int i = 0; i < ids.length; i++) {
			Paper data = this.paperDao.load(Paper.class, ids[i]);
			if (data != null) {
				if (logger.isDebugEnabled())logger.debug(String.format("删除［%s］...", ids[i]));
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
		if (logger.isDebugEnabled())logger.debug(String.format("更新状态：%1$s -> %2$s", id, status));
		String msg = null;
		if (StringUtils.isEmpty(id)) {
			msg = "试卷ID为空！";
			if (logger.isDebugEnabled())logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if (status == null)status = PaperStatus.NONE;
		Paper data = this.paperDao.load(Paper.class, id);
		if (data == null) {
			msg = String.format("试卷［id=%s］不存在！", id);
		}
		data.setStatus(status.getValue());
		if (status == PaperStatus.AUDIT) {
			// 将试卷下未审核的题目批量审核。
			List<Structure> structures = this.structureDao.finaStructures(data.getId());
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
		if (logger.isDebugEnabled())logger.debug(String.format("更新状态［%1$s,%2$s］%3$s ＝> %4$s",data.getId(), data.getName(), PaperStatus.convert(data.getStatus()), status));
	}
	// 审核试卷下的题目。
	private void auditPaperItems(Structure structure) {
		if (structure == null)return;
		if (structure.getItems() != null && structure.getItems().size() > 0) {
			for (StructureItem structureItem : structure.getItems()) {
				if (structureItem == null)continue;
				if (structureItem.getItem() != null && structureItem.getItem().getStatus() != ItemStatus.AUDIT.getValue()) {
					structureItem.getItem().setStatus(ItemStatus.AUDIT.getValue());
				}
			}
		}
	}


//	

 
 
 

//	/********************************************************
//	 * 前台调用方法
//	 ********************************************************/
//	/*
//	 * 加载试卷基本信息
//	 * 
//	 * @see
//	 * com.examw.test.service.library.IPaperService#loadPaperInfo(java.lang.
//	 * String)
//	 */
//	@Override
//	public PaperPreview loadPaperInfo(String paperId) {
//		if (logger.isDebugEnabled())
//			logger.debug(String.format("加载试卷［id = %s］预览...", paperId));
//		if (StringUtils.isEmpty(paperId))
//			return null;
//		this.paperDao.evict(Paper.class);
//		Paper paper = this.paperDao.load(Paper.class, paperId);
//		if (paper == null) {
//			if (logger.isDebugEnabled())
//				logger.debug(String.format("试卷［id = %s］不存在！", paperId));
//			return null;
//		}
//		PaperPreview paperPreview = this.changeModel(paper);
//		if (paperPreview == null)
//			return null;
//		List<StructureInfo> structures = new ArrayList<>();
//		List<Structure> list = this.structureDao.finaStructures(paperId);
//		int total = 0; // 总题数
//		if (list != null && list.size() > 0) {
//			for (Structure s : list) {
//				StructureInfo info = new StructureInfo();
//				if (this.changeModel(s, info, false)) { // 不加载题目
//					structures.add(info);
//					total += info.getTotal(); // 计算题目的总数
//				}
//			}
//		}
//		paperPreview.setStructures(structures);
//		paperPreview.setTotal(total); // 设置题目的总数
//		return paperPreview;
//	}

//	/*
//	 * 加载试卷详情并添加考试记录
//	 * 
//	 * @see
//	 * com.examw.test.service.library.IPaperService#loadPaperPreviewAndAddRecord
//	 * (java.lang.String, java.lang.String)
//	 */
//	@Override
//	public PaperPreview loadPaperPreviewAndAddRecord(final String paperId, final
//			String userId) {
//		PaperPreview paperPreview = this.loadPaperPreview(paperId);
//		if (paperPreview != null)
//		{
//			paperPreview.setLeftTime(60*paperPreview.getTime());
//			PaperRecord	record = this.paperRecordDao.load(paperId, userId);
//			if(record != null && record.getStatus().equals(PaperRecord.STATUS_UNDONE)){
//				final Date time = record.getLastTime();
//				//没做完 加上用户答案
//				List<ItemRecord> list = this.itemRecordDao.findItemRecords(new ItemRecord(){
//					private static final long serialVersionUID = 1L;
//					@Override
//					public String getPaperId() {return paperId;}
//					@Override
//					public String getUserId() {return userId;}
//					@Override
//					public Date getLastTime() {return time;}
//				});
//				setUserAnswer(paperPreview,list);
//				paperPreview.setLeftTime(paperPreview.getLeftTime() - (record.getUsedTime()==null?0:record.getUsedTime()));
//			}
//			savePaperRecord(paperId, userId); // 保存考试记录
//		}
//		return paperPreview;
//	}
//	/*
//	 * 以前做过的题目附上用户答案
//	 */
//	private void setUserAnswer(PaperPreview paperPreview,List<ItemRecord> list){
//		if(list == null || list.size()==0) return;
//		List<StructureInfo> structures = paperPreview.getStructures();
//		if(structures == null || structures.size()==0) return ;
//		for(StructureInfo s:structures){
//			Set<StructureItemInfo> items = s.getItems();
//			if(items == null || items.size()==0) continue;
//			for(StructureItemInfo item : items){
//				if(item.getItem() == null) continue;
//				//为每题设置用户答案
//				setUserAnswer(item,list);
//			}
//		}
//	}
//	/*
//	 * 设置用户答案
//	 */
//	private void setUserAnswer(StructureItemInfo item,List<ItemRecord> list){
//		if(item.getType().equals(Item.TYPE_SHARE_ANSWER)||item.getType().equals(Item.TYPE_SHARE_TITLE)){
//			Set<ItemScoreInfo> set = item.getItem().getChildren();
//			for(ItemScoreInfo info :set){
//				for(ItemRecord record:list){
//					if((item.getId()+"#"+info.getId()).equals(record.getStructureItemId())){
//						info.setUserAnswer(record.getAnswer());
//						info.setUserScore(record.getScore());
//						info.setAnswerStatus(record.getStatus());
//						break;
//					}
//				}
//			}
//		}else{
//			for(ItemRecord record:list){
//				if(item.getId().equals(record.getStructureItemId())){
//					item.getItem().setUserAnswer(record.getAnswer());
//					item.getItem().setUserScore(record.getScore());
//					item.getItem().setAnswerStatus(record.getStatus());
//					break;
//				}
//			}
//		}
//	}
//	/*
//	 * 保存考试记录
//	 * @param paperId
//	 * @param userId
//	 */
//	private void savePaperRecord(String paperId, String userId) {
//		//做一次保存一次考试记录
//		PaperRecord	record = new PaperRecord();
//		record.setId(UUID.randomUUID().toString());
//		record.setPaperId(paperId);
//		record.setUserId(userId);
//		record.setLastTime(new Date());
//		record.setStatus(PaperRecord.STATUS_UNDONE);
//		paperRecordDao.save(record);
//	}
//
//	/*
//	 * 查询前台数据
//	 * @see
//	 * com.examw.test.service.library.IPaperService#loadPaperFrontInfo(com.examw
//	 * .test.model.library.PaperInfo, java.lang.String)
//	 */
//	@Override
//	public List<PaperFrontInfo> loadPaperFrontInfo(PaperInfo info, String userId) {
//		List<Paper> list = this.paperDao.findPapers(info);
//		List<PaperFrontInfo> result = new ArrayList<PaperFrontInfo>();
//		if (list == null || list.size() == 0)
//			return result;
//		for (Paper paper : list) {
//			PaperFrontInfo target = this.changeFrontModel(paper, userId);
//			if (target != null)
//				result.add(target);
//		}
//		return result;
//	}
//
//	/*
//	 * 模型转换
//	 */
//	private PaperFrontInfo changeFrontModel(Paper data, String userId) {
//		PaperInfo paperInfo = this.changeModel(data);
//		if (paperInfo == null)
//			return null;
//		paperInfo.setTotal(getTotalNum(data));
//		PaperFrontInfo info = new PaperFrontInfo();
//		BeanUtils.copyProperties(paperInfo, info);
//		info.setMaxScore(this.paperRecordDao.findMaxScore(data.getId()));
//		info.setUserSum(this.paperRecordDao.findUserSum(data.getId())
//				.intValue());
//		if (!StringUtils.isEmpty(userId)) {
//			PaperRecord record = this.paperRecordDao.load(data.getId(), userId);
//			if (record != null) {
//				info.setUserStatus(record.getStatus());
//				if (record.getStatus().equals(PaperRecord.STATUS_DONE)) {
//					info.setUserScore(record.getScore());
//					info.setUserTime(record.getTime());
//					info.setUserExamTime(record.getLastTime());
//				}
//			}
//		}
//		return info;
//	}
//
//	/*
//	 * 计算总题数
//	 */
//	private Integer getTotalNum(Paper data) {
//		List<Structure> list = this.structureDao.finaStructures(data.getId());
//		int total = 0; // 总题数
//		if (list != null && list.size() > 0) {
//			for (Structure s : list) {
//				StructureInfo info = new StructureInfo();
//				if (this.changeModel(s, info, false)) { // 不加载题目
//					total += info.getTotal(); // 计算题目的总数
//				}
//			}
//		}
//		return total;
//	}
//
//	/*
//	 * 查询统计
//	 * @see
//	 * com.examw.test.service.library.IPaperService#totalPaperFrontInfo(com.
//	 * examw.test.model.library.PaperInfo)
//	 */
//	@Override
//	public Long totalPaperFrontInfo(PaperInfo info) {
//		return this.paperDao.total(info);
//	}
//
//	@Override
//	public Json submitPaper(Integer limitTime, String chooseAnswers,
//			String textAnswers, Integer model, String paperId, String userId) {
//		Json json = new Json();
//		PaperRecord record = this.paperRecordDao.load(paperId, userId);
//		Paper paper = this.paperDao.load(Paper.class, paperId);
//		record.setStatus(model);
//		record.setUsedTime(paper.getTime() * 60 - limitTime); // 使用时间
//		List<ItemRecord> itemRecords = new ArrayList<ItemRecord>();
//		if (!StringUtils.isEmpty(chooseAnswers)) {
//			try {
//				chooseAnswers = URLDecoder.decode(chooseAnswers, "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			String[] answers = chooseAnswers.split("&");// 拆分题目与答案
//			for (String s : answers) {
//				ItemRecord itemRecord = new ItemRecord();
//				itemRecord.setId(UUID.randomUUID().toString());
//				itemRecord.setPaperId(paperId);
//				itemRecord.setUserId(userId);
//				itemRecord.setLastTime(new Date());
//				String[] arr = s.split("=");
//				itemRecord.setStructureItemId(arr[0]);
//				if (arr.length > 1) { // 没有答案
//					itemRecord.setAnswer(arr[1]);
//				} 
//				itemRecords.add(itemRecord);
//			}
//		}
//		if (!StringUtils.isEmpty(textAnswers)) {
//
//		}
//		this.itemRecordDao.insertItemRecordList(itemRecords);
//		if (model.equals(PaperRecord.STATUS_DONE)) {
//			PaperPreview preview = judgePaper(record, paperId, itemRecords);
//			json.setData(preview);
//		}
//		json.setSuccess(true);
//		json.setMsg("提交成功");
//		return json;
//	}
//
//	private PaperPreview judgePaper(PaperRecord record, String paperId,
//			List<ItemRecord> itemRecordList) {
//		PaperPreview paper = this.loadPaperPreview(paperId);
//		List<StructureInfo> structures = paper.getStructures();
//		if (structures == null || structures.size() == 0)
//			return null;
//		BigDecimal paperTotalScore = BigDecimal.ZERO; // 试卷总分
//		List<ErrorItemRecord> errorRecords = new ArrayList<ErrorItemRecord>();
//		for (StructureInfo s : structures) {
//			Set<StructureItemInfo> items = s.getItems();
//			if (items == null || items.size() == 0)
//				continue;
//			// 评分规则
//			BigDecimal min = s.getMin(); // 最低得分
//			BigDecimal per = s.getScore(); // 每题得分
//			BigDecimal actualRuleTotal = BigDecimal.ZERO;
//			for (StructureItemInfo item : items) { // 结构题目
//				for (ItemRecord itemRecord : itemRecordList) {
//					if (!itemRecord.getStructureItemId().contains("#")) { // 不包含#,是单题
//						if (item.getId()
//								.equals(itemRecord.getStructureItemId())) {
//							// 判断对错
//							if(!judgeItemIsRight(item, itemRecord, min, per))
//								errorRecords.add(createErrorRecord(item.getItem().getId(),itemRecord));
//							actualRuleTotal = actualRuleTotal.add(itemRecord
//									.getScore());
//							break;
//						}
//					} else { // 复合题
//						Set<ItemScoreInfo> children = item.getItem()
//								.getChildren();
//						for (ItemScoreInfo child : children) {
//							if ((item.getId() + "#" + child.getId())
//									.equals(itemRecord.getStructureItemId())) {
//								// 判断对错
//								if(!judgeItemIsRight(item, itemRecord, min, per))
//									errorRecords.add(createErrorRecord(item.getItem().getId(),itemRecord));
//								actualRuleTotal = actualRuleTotal
//										.add(itemRecord.getScore());
//								break;
//							}
//						}
//					}
//				}
//			}
//			if (actualRuleTotal.compareTo(BigDecimal.ZERO) == -1) {
//				actualRuleTotal = BigDecimal.ZERO;
//			}
//			paperTotalScore = paperTotalScore.add(actualRuleTotal); // 试卷总分
//		}
//		record.setScore(paperTotalScore);
//		this.errorItemRecordDao.insertRecordList(errorRecords); 	//保存错题记录
//		return paper;
//	}
//	private ErrorItemRecord createErrorRecord(String itemId,ItemRecord itemRecord){
//		ErrorItemRecord error = new ErrorItemRecord();
//		error.setCreateTime(new Date());
//		error.setId(UUID.randomUUID().toString());
//		error.setStructureItemId(itemRecord.getStructureItemId());
//		error.setItemId(itemId);
//		error.setHistoryAnswers(itemRecord.getAnswer());
//		error.setUserId(itemRecord.getUserId());
//		return error;
//	}
//	/*
//	 * 判断题目是对是错
//	 */
//	private boolean judgeItemIsRight(StructureItemInfo item,
//			ItemRecord itemRecord, BigDecimal min, BigDecimal per) {
//		if (StringUtils.isEmpty(itemRecord.getAnswer())) {
//			itemRecord.setScore(min == null ? BigDecimal.ZERO : min); // 得0分或者负分
//			itemRecord.setStatus(ItemRecord.STATUS_NULL); // 没有作答
//			return false;
//		}
//		switch (item.getType()) {
//		case Item.TYPE_SINGLE: // 单选
//		case Item.TYPE_JUDGE: // 判断
//			if (item.getItem().getAnswer().equals(itemRecord.getAnswer())) // 答对
//			{
//				itemRecord.setScore(per); // 得标准分
//				itemRecord.setStatus(ItemRecord.STATUS_RIGHT); // 答对
//				return true;
//			} else {
//				itemRecord.setScore((min == null || min
//						.compareTo(BigDecimal.ZERO) == 1) ? BigDecimal.ZERO
//						: min); // 得0分或者负分
//				itemRecord.setStatus(ItemRecord.STATUS_WRONG); // 答错
//				return false;
//			}
//		case Item.TYPE_MULTY: // 多选
//		case Item.TYPE_UNCERTAIN: // 不定项
//			String[] arr = itemRecord.getAnswer().split(",");
//			String answer = item.getItem().getAnswer();
//			int total = 0;
//			for (String a : arr) {
//				if (answer.indexOf(a) == -1) { // 包含有错误答案
//					itemRecord.setScore((min == null || min
//							.compareTo(BigDecimal.ZERO) == 1) ? BigDecimal.ZERO
//							: min); // 得0分或者负分
//					itemRecord.setStatus(ItemRecord.STATUS_WRONG); // 答错
//					return false;
//				} else {
//					total++;
//				}
//			}
//			if (total == answer.split(",").length) { // 全对,得满分
//				itemRecord.setScore(per); // 得标准分
//				itemRecord.setStatus(ItemRecord.STATUS_RIGHT); // 答对
//				return true;
//			} else {
//				itemRecord.setScore((min == null || min
//						.compareTo(BigDecimal.ZERO) == -1) ? BigDecimal.ZERO
//						: min.multiply(new BigDecimal(total))); // 得0分或者负分
//				itemRecord.setStatus(ItemRecord.STATUS_LESS); // 少选
//				return false;
//			}
//		default:
//			return true;
//		}
//	}
}