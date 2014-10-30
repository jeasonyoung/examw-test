package com.examw.test.service.library.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.service.library.IExercisesDailyService;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IRandomItemService;
import com.examw.test.service.library.ItemType;
import com.examw.test.service.library.PaperStatus;
import com.examw.test.service.library.PaperType;

/**
 * 每日一练服务接口现实类。
 * 
 * @author yangyong
 * @since 2014年10月23日
 */
public class ExercisesDailyServiceImpl implements IExercisesDailyService {
	private static final Logger logger = Logger.getLogger(ExercisesDailyServiceImpl.class);
	private static final int avg_structure_items_count = 2;
	private IItemDao itemDao;
	private IPaperDao paperDao;
	private ISubjectDao subjectDao;
	private IItemService itemService;
	private IRandomItemService randomItemService;
	private Integer avgStructureItemsCount;
	/**
	 * 设置试题数据接口。
	 * @param itemDao 
	 *	  试题数据接口。
	 */
	public void setItemDao(IItemDao itemDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试题数据接口...");
		this.itemDao = itemDao;
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
	/**
	 * 设置科目数据接口。
	 * @param subjectDao 
	 *	  科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled()) logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置试题服务接口。
	 * @param itemService 
	 *	  试题服务接口。
	 */
	public void setItemService(IItemService itemService) {
		if(logger.isDebugEnabled()) logger.debug("注入试题服务接口...");
		this.itemService = itemService;
	}
	/**
	 * 设置随机抽题服务接口。
	 * @param randomItemService 
	 *	  随机抽题服务接口。
	 */
	public void setRandomItemService(IRandomItemService randomItemService) {
		if(logger.isDebugEnabled()) logger.debug("注入随机抽题服务接口...");
		this.randomItemService = randomItemService;
	}
	/**
	 * 设置试卷结构试题数量。
	 * @param avgStructureItemsCount 
	 *	  试卷结构试题数量。
	 */
	public void setAvgStructureItemsCount(Integer avgStructureItemsCount) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷结构试题数量...");
		this.avgStructureItemsCount = avgStructureItemsCount;
	}
	/*
	 * 创建科目的每日一练试卷。
	 * @see com.examw.test.service.library.IExercisesDailyService#createPaper(com.examw.test.domain.settings.Subject)
	 */
	@Override
	public void addPaper(Subject subject) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("创建科目［%s］的每日一练试卷...", subject.getName()));
		String msg = null;
		List<ItemType> listItemTypes =  this.itemDao.loadItemTypes(subject);
		if(listItemTypes == null || listItemTypes.size() == 0){
			logger.error(msg = String.format("科目［%s］在题库中没有满足条件的试题类型！", subject));
			throw new Exception(msg);
		}
		ItemType[] itemTypes = listItemTypes.toArray(new ItemType[0]);
        Arrays.sort(itemTypes, new Comparator<ItemType>(){
			@Override
			public int compare(ItemType o1, ItemType o2) { return o1.getValue() - o2.getValue(); }
        });
		Paper[] papers = this.buildPaper(subject);
		if(papers == null || papers.length == 0){
			logger.error(msg = String.format("科目［%s］下生成试卷对象失败！", subject));
			throw new Exception(msg);
		}
		for(int i = 0; i < papers.length; i++){
			if(papers[i] == null) continue;
			try{
				if(logger.isDebugEnabled()) logger.debug(String.format("［%1$d］开始生成试卷［%2$s］内容....",  (i+1), papers[i]));
				this.addDailyPapers(papers[i], itemTypes);
			}catch(Exception e){
				logger.error(msg = String.format("生成试卷［%s］内容失败！", papers[i]));
				e.printStackTrace();
			}
		} 
	}
	//添加每日一练试卷内容
	private void addDailyPapers(Paper paper, ItemType[] itemTypes) throws Exception{
		if(paper == null || itemTypes == null || itemTypes.length == 0) return;
		String msg = null;
		for(int i = 0; i < itemTypes.length; i++){
			//构建试卷结构。
			Structure structure = new Structure(paper);
			structure.setTitle(this.itemService.loadTypeName(itemTypes[i].getValue()));
			structure.setType(itemTypes[i].getValue());
			structure.setTotal((this.avgStructureItemsCount == null  || this.avgStructureItemsCount <= 0 )? avg_structure_items_count : this.avgStructureItemsCount);
			structure.setOrderNo(i+1); 
			structure.setScore(BigDecimal.ONE);//每题一分 用来计数
			//随机选题。
			int total = this.randomItemService.addRandomItem(structure, false);
			if(total <= 0){
				logger.error(msg = String.format("试卷结构［%s］下未能成功随机选择出试题！", structure));
				throw new Exception(msg);
			}
			if(total != structure.getTotal()) structure.setTotal(total);
			paper.getStructures().add(structure);
		}
		//试卷试题重新排序
		this.randomItemService.updateItemOrder(paper);
		paper.setStatus(PaperStatus.AUDIT.getValue());
		this.paperDao.save(paper);
	}
	//构建试卷对象。
	private Paper[] buildPaper(Subject subject){
		if(logger.isDebugEnabled()) logger.debug("构建试卷对象...");
		if(subject == null) return null;
		if(subject.getAreas() == null || subject.getAreas().size() == 0){
			return new Paper[]{ this.buildPaper(subject, null) };
		}
		List<Paper> papers = new ArrayList<>();
		for(Area area : subject.getAreas()){
			if(area == null) continue;
			papers.add(this.buildPaper(subject, area));
		}
		return papers.toArray(new Paper[0]);
	}
	//构建试卷对象。
	private Paper buildPaper(Subject subject, Area area){
		Paper paper = new Paper();
		String examName = subject.getExam().getName();
		if(!StringUtils.isEmpty(examName) && !examName.endsWith("考试")){
			examName = String.format("%s考试", examName);
		}
		paper.setName(String.format("%1$s《%2$s》%3$s每日一练[%4$s]", examName, subject.getName(),
				(area == null ? "" : "[" + area.getName() + "]"),
				new SimpleDateFormat("yyyy-MM-dd").format(paper.getCreateTime())));
		paper.setSubject(subject);
		paper.setType(PaperType.DAILY.getValue());
		paper.setScore(null);
		paper.setTime(null);
		paper.setYear(Integer.parseInt(new SimpleDateFormat("yyyy").format(paper.getCreateTime())));
		paper.setArea(area);
		paper.setStructures(new TreeSet<Structure>());
		return paper;
	}
	/*
	 * 自动创建每日一练。
	 * @see com.examw.test.service.library.IExercisesDailyService#autoDailyPapers()
	 */
	@Override
	public void addAutoDailyPapers() {
		if(logger.isDebugEnabled()) logger.debug("自动创建每日一练...");
		List<Subject> subjects = this.subjectDao.findSubjects(new SubjectInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() { return "code";}
			@Override
			public String getOrder() { return "desc";}
		});
		if(subjects == null || subjects.size() == 0) return;
		for(final Subject subject : subjects){
			if(subject == null) continue;
			try{
				if(logger.isDebugEnabled()) logger.debug(String.format("准备开始创建科目［%1$s,%2$s］", subject.getId(),subject.getName()));
				long total = this.itemDao.total(new ItemInfo(){
					private static final long serialVersionUID = 1L;
					@Override
					public String getSubjectId() {return subject.getId();}
					@Override
					public String getAreaId() {
						 if(subject.getAreas() != null && subject.getAreas().size() > 0){
							 StringBuilder builder = new StringBuilder();
							 for(Area area : subject.getAreas()){
								 if(area == null) continue;
								 builder.append(",").append(area.getId());
							 }
							 if(builder.length() > 0) return builder.substring(1);
						 }
						return null;
					}
				});
				if(total < ((this.avgStructureItemsCount == null || this.avgStructureItemsCount < 0) ? avg_structure_items_count : this.avgStructureItemsCount)){
					if(logger.isDebugEnabled()) logger.debug(String.format("科目［%1$s,%2$s］下试题量［%3$d］太小！", subject.getId(),subject.getName(), total));
					continue;
				}
				this.addPaper(subject);
			}catch(Exception e){
				logger.error(String.format("创建科目［%1$s,%2$s］每日一练时，发生异常：%3$s", subject.getId(), subject.getName(), e.getMessage()), e);
				e.printStackTrace();
			}
		}
	}
}
