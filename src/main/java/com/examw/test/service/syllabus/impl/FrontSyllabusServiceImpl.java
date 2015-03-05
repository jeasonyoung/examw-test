package com.examw.test.service.syllabus.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.records.IUserItemRecordDao;
import com.examw.test.dao.syllabus.ISyllabusDao;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.FrontSyllabusInfo;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.records.UserItemRecordStatus;
import com.examw.test.service.syllabus.IFrontSyllabusService;

/**
 * 前台大纲服务接口实现类
 * @author fengwei.
 * @since 2015年3月4日 上午10:20:31.
 */
public class FrontSyllabusServiceImpl implements IFrontSyllabusService{
	private static final Logger logger = Logger.getLogger(SyllabusServiceImpl.class);
	private ISyllabusDao syllabusDao;
	/**
	 * 设置 大纲数据接口
	 * @param syllabusDao
	 * 
	 */
	public void setSyllabusDao(ISyllabusDao syllabusDao) {
		this.syllabusDao = syllabusDao;
	}
	
	private IUserItemRecordDao userItemRecordDao;
	
	/**
	 * 设置 用户做题记录数据接口
	 * @param userItemRecordDao
	 * 
	 */
	public void setUserItemRecordDao(IUserItemRecordDao userItemRecordDao) {
		this.userItemRecordDao = userItemRecordDao;
	}
	@Override
	public List<FrontSyllabusInfo> loadLastSyllabuses(String subjectId,String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［subjectId = %s］下最新的大纲要点集合... ", subjectId));
		List<FrontSyllabusInfo> list = new ArrayList<>();
		if(StringUtils.isEmpty(subjectId)) return list;
		Syllabus syllabus = this.syllabusDao.loadSyllabussLast(subjectId);
		if(syllabus == null || (syllabus.getChildren() == null || syllabus.getChildren().size() == 0)) return list;
		for(Syllabus child :  syllabus.getChildren()){
			if(child == null) continue;
			FrontSyllabusInfo info =  this.conversion(child,userId);
			if(info != null){
				list.add(info);
			}
		}
		if(list.size() > 0) {
			Collections.sort(list);
		}
		return list;
	}
	/*
	 * 数据模型转换。
	 */
	public FrontSyllabusInfo conversion(Syllabus syllabus,String userId) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Syllabus => SyllabusInfo ...");
		return this.changeModel(syllabus,userId,0, true);
	}
	//数据模型转换。
	private FrontSyllabusInfo changeModel(Syllabus source,String userId,Integer level,boolean hasChild){
		if(source == null) return null;
		FrontSyllabusInfo target = new FrontSyllabusInfo();
		BeanUtils.copyProperties(source, target, new String[]{"children"}); 
		if(source.getSubject() != null){
			target.setSubjectId(source.getSubject().getId());
			target.setSubjectName(source.getSubject().getName());
			if(source.getSubject().getExam() != null){
				target.setExamId(source.getSubject().getExam().getId());
				target.setExamName(source.getSubject().getExam().getName());
			}
		}
		target.setLevel(level);
		//查询该大纲条目下的题目数量
		if(source.getItems()!=null)
			target.setItemSum(source.getItems().size());
		else
			target.setItemSum(0);
		//查询用户的记录数量
		if(!StringUtils.isEmpty(userId))
		{
			target.setUserDoneItemSum(this.userItemRecordDao.totalUserSyllabusItemRecord(userId, source.getId(),
					UserItemRecordStatus.RIGHT.getValue(),UserItemRecordStatus.LESS.getValue(),UserItemRecordStatus.WRONG.getValue()).intValue());
			//错题数量
			target.setUserErrorItemSum(this.userItemRecordDao.totalUserSyllabusItemRecord(userId, source.getId(),UserItemRecordStatus.WRONG.getValue()).intValue());
		}
		if(!hasChild){	//不查子类就是顶级
			if(source.getAreas() != null && source.getAreas().size() > 0){
				List<String> list_ids = new ArrayList<>(), list_names = new ArrayList<>();
				for(Area area : source.getAreas()){
					if(area == null) continue;
					list_ids.add(area.getId());
					list_names.add(area.getName());
				}
				target.setAreaId(list_ids.toArray(new String[0]));
				target.setAreaName(list_names.toArray(new String[0]));
			}
		}
		if(hasChild && (source.getChildren() != null && source.getChildren().size() > 0)){
			Set<SyllabusInfo> children = new TreeSet<>();
			//算一次数量
			Integer itemSum = target.getItemSum(),itemDoneSum = target.getUserDoneItemSum(),itemErrorSum = target.getUserErrorItemSum();
			for(Syllabus syllabus : source.getChildren()){
				if(syllabus == null) continue;
				FrontSyllabusInfo e = this.changeModel(syllabus,userId,target.getLevel()+1, hasChild);
				if(e != null){
					e.setPid(target.getId());
					itemSum += e.getItemSum();
					itemDoneSum += e.getUserDoneItemSum();
					itemErrorSum += e.getUserErrorItemSum();
					children.add(e);
				}
			}
			if(children.size() > 0){
				target.setChildren(children);
				target.setItemSum(itemSum);
				target.setUserDoneItemSum(itemDoneSum);
				target.setUserErrorItemSum(itemErrorSum);
			}
		}
		return target;
	}
}
