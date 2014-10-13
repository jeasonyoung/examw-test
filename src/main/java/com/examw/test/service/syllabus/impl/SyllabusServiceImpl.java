package com.examw.test.service.syllabus.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.dao.syllabus.ISyllabusDao;
import com.examw.test.domain.settings.Subject;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.ISyllabusService;
/**
 * 大纲服务接口实现类。
 * @author lq.
 * @since 2014-08-06.
 */
public class SyllabusServiceImpl extends BaseDataServiceImpl<Syllabus, SyllabusInfo> implements ISyllabusService {
	private static final Logger logger = Logger.getLogger(SyllabusServiceImpl.class);
	private ISyllabusDao syllabusDao;
	private ISubjectDao subjectDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置大纲数据接口。
	 * @param syllabusDao
	 * 大纲数据接口。
	 */
	public void setSyllabusDao(ISyllabusDao syllabusDao) {
		if(logger.isDebugEnabled())logger.debug("注入大纲数据接口...");
		this.syllabusDao = syllabusDao;
	}
	/**
	 * 设置科目数据接口。
	 * @param subjectDao
	 * 科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled()) logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置状态值和名称集合。
	 * @param statusMap 
	 *	  设置状态值和名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入设置状态值和名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.test.service.syllabus.ISyllabusService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态［status = %d］名称...", status));
		if(status == null) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Syllabus> find(SyllabusInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return this.syllabusDao.findSyllabuss(info);
	}
	/*
	 * 数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SyllabusInfo info) {
		if(logger.isDebugEnabled())logger.debug("数据统计...");
		return this.syllabusDao.total(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SyllabusInfo changeModel(Syllabus data) {
		if(logger.isDebugEnabled()) logger.debug(" 数据模型转换 Syllabus => SyllabusInfo ...");
		return this.changeModel(data, false);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.syllabus.ISyllabusService#conversion(com.examw.test.domain.syllabus.Syllabus)
	 */
	@Override
	public SyllabusInfo conversion(Syllabus syllabus) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Syllabus => SyllabusInfo ...");
		return this.changeModel(syllabus, true);
	}
	//数据模型转换。
	private SyllabusInfo changeModel(Syllabus source,boolean hasChild){
		if(source == null) return null;
		SyllabusInfo target = new SyllabusInfo();
		BeanUtils.copyProperties(source, target, new String[]{"children"}); 
		if(target.getStatus() != null){
			target.setStatusName(this.loadStatusName(target.getStatus()));
		}
		if(source.getSubject() != null){
			target.setSubjectId(source.getSubject().getId());
			target.setSubjectName(source.getSubject().getName());
			if(source.getSubject().getExam() != null){
				target.setExamId(source.getSubject().getExam().getId());
				target.setExamName(source.getSubject().getExam().getName());
			}
		}
		if(hasChild && (source.getChildren() != null && source.getChildren().size() > 0)){
			Set<SyllabusInfo> children = new TreeSet<>();
			for(Syllabus syllabus : source.getChildren()){
				if(syllabus == null) continue;
				SyllabusInfo e = this.changeModel(syllabus, hasChild);
				if(e != null){
					e.setPid(target.getId());
					children.add(e);
				}
			}
			if(children.size() > 0){
				target.setChildren(children);
			}
		}
		return target;
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SyllabusInfo update(SyllabusInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Syllabus data = StringUtils.isEmpty(info.getId()) ?  null : this.syllabusDao.load(Syllabus.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Syllabus();
		}
		BeanUtils.copyProperties(info, data, new String[]{"children"});
		if(StringUtils.isEmpty(info.getPid())) {
			data.setParent(null);
		}else if (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid())) {
			Syllabus parent = this.syllabusDao.load(Syllabus.class, info.getPid());
			if(parent != null && !parent.getId().equalsIgnoreCase(data.getId())) {
				data.setParent(parent);
			}
		}
		data.setSubject(StringUtils.isEmpty(info.getSubjectId()) ? null : this.subjectDao.load(Subject.class, info.getSubjectId()));
		if(isAdded) this.syllabusDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length;i++){
			Syllabus data = this.syllabusDao.load(Syllabus.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除数据［"+ ids[i]+"］");
				this.syllabusDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大的代码值。
	 * @see com.examw.oa.service.check.ICatalogService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxOrder(String parentSyllabusId) {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.syllabusDao.loadMaxOrder(parentSyllabusId);
	}
	/*
	 *  加载科目下的考试大纲。
	 * @see com.examw.test.service.syllabus.ISyllabusService#loadSyllabuses(java.lang.String)
	 */
	@Override
	public List<SyllabusInfo> loadSyllabuses(String subjectId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［subjectId = %s］的考试大纲... ", subjectId));
		List<SyllabusInfo> list = new ArrayList<>();
		if(StringUtils.isEmpty(subjectId)) return list;
		Syllabus syllabus = this.syllabusDao.loadSyllabussLast(subjectId);
		if(syllabus == null || (syllabus.getChildren() == null || syllabus.getChildren().size() == 0)) return list;
		for(Syllabus child :  syllabus.getChildren()){
			if(child == null) continue;
			SyllabusInfo info =  this.conversion(child);
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
	 * 加载考试大纲数据。
	 * @see com.examw.test.service.syllabus.ISyllabusService#loadSyllabus(java.lang.String)
	 */
	@Override
	public Syllabus loadSyllabus(String syllabusId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲[syllabusId = %s]数据...", syllabusId));
		if(StringUtils.isEmpty(syllabusId)) return null;
		return this.syllabusDao.load(Syllabus.class, syllabusId);
	}
}