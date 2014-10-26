package com.examw.test.service.settings.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.settings.IAreaDao;
import com.examw.test.dao.settings.IExamDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.settings.AreaInfo;
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.settings.IAreaService;
import com.examw.test.service.settings.ISubjectService;

/**
 * 科目服务接口实现类
 * @author fengwei.
 * @since 2014年8月7日 上午10:27:40.
 */
public class SubjectServiceImpl extends BaseDataServiceImpl<Subject, SubjectInfo> implements ISubjectService {
	private static final Logger logger = Logger.getLogger(SubjectServiceImpl.class);
	private IExamDao examDao;
	private ISubjectDao subjectDao;
	private IAreaDao areaDao;
	private IAreaService areaService;
	/**
	 * 设置考试数据接口。
	 * @param examDao
	 * 考试数据接口。
	 */
	public void setExamDao(IExamDao examDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试数据接口...");
		this.examDao = examDao;
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
	 * 设置地区数据接口。
	 * @param areaDao
	 * 地区数据接口。
	 */
	public void setAreaDao(IAreaDao areaDao) {
		if(logger.isDebugEnabled()) logger.debug("注入地区数据接口...");
		this.areaDao = areaDao;
	}
	/**
	 * 设置地区服务接口。
	 * @param areaService 
	 *	  地区服务接口。
	 */
	public void setAreaService(IAreaService areaService) {
		if(logger.isDebugEnabled()) logger.debug("注入地区服务接口...");
		this.areaService = areaService;
	}
	/*
	 * 加载最大代码。
	 * @see com.examw.test.service.settings.ISubjectService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.subjectDao.loadMaxCode();
	}
	/*
	 * 加载考试下全部科目集合。
	 * @see com.examw.test.service.settings.ISubjectService#loadAllSubjects(java.lang.String)
	 */
	@Override
	public List<SubjectInfo> loadAllSubjects(String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试[examId=%s]下全部科目集合...", examId));
		List<SubjectInfo> results = new ArrayList<>();
		List<Subject> list = this.subjectDao.loadAllSubjects(examId);
		if(list != null && list.size() > 0){
			for(Subject subject : list){
				if(subject == null) continue;
				SubjectInfo info = this.changeModel(subject);
				if(info != null){
					results.add(info);
				}
			}
		}
		return results;
	}
	/*
	 * 加载科目下所有地区集合。
	 * @see com.examw.test.service.settings.ISubjectService#loadSubjectAreas(java.lang.String)
	 */
	@Override
	public List<AreaInfo> loadSubjectAreas(String subjectId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［subjectId = %s］下所有地区集合...", subjectId));
		List<AreaInfo> list = new ArrayList<>();
		if(!StringUtils.isEmpty(subjectId)){
			Subject subject = this.subjectDao.load(Subject.class, subjectId);
			if(subject != null && subject.getAreas() != null && subject.getAreas().size() > 0){
				for(Area area : subject.getAreas()){
					if(area == null) continue;
					list.add(this.areaService.conversion(area));
				}
			}
		}
		return list;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Subject> find(SubjectInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return this.subjectDao.findSubjects(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SubjectInfo changeModel(Subject data) {
		if(logger.isDebugEnabled())logger.debug("数据模型转换...");
		if(data == null) return null;
		SubjectInfo info = new SubjectInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getExam() != null){
			info.setExamId(data.getExam().getId());
			info.setExamName(data.getExam().getName());
			if(data.getExam().getCategory() != null){
				info.setCategoryId(data.getExam().getCategory().getId());
				info.setCategoryName(data.getExam().getCategory().getName());
			}
		}
		if(data.getAreas() != null && data.getAreas().size() > 0){
			List<String> list_ids = new ArrayList<>(), list_names = new ArrayList<>();
			for(Area area : data.getAreas()){
				if(area == null) continue;
				list_ids.add(area.getId());
				list_names.add(area.getName());
			}
			info.setAreaId(list_ids.toArray(new String[0]));
			info.setAreaName(list_names.toArray(new String[0]));
		}
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.settings.ISubjectService#conversion(com.examw.test.domain.settings.Subject)
	 */
	@Override
	public SubjectInfo conversion(Subject subject) {
		if(logger.isDebugEnabled())logger.debug("数据模型集合转换...");
		return this.changeModel(subject);
	}
	/*
	 * 查询统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SubjectInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询统计...");
		return this.subjectDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SubjectInfo update(SubjectInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Subject data = StringUtils.isEmpty(info.getId()) ? null : this.subjectDao.load(Subject.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Subject();
		}
		BeanUtils.copyProperties(info, data);
		//考试
		if(!StringUtils.isEmpty(info.getExamId()) && (data.getExam() == null || !data.getExam().getId().equalsIgnoreCase(info.getExamId()))){
			Exam exam = this.examDao.load(Exam.class, info.getExamId());
			if(exam != null) data.setExam(exam);
		}
		//地区
		Set<Area> areas = null;
		if(info.getAreaId() != null && info.getAreaId().length > 0){
			areas = new HashSet<>();
			for(String areaId : info.getAreaId()){
				if(StringUtils.isEmpty(areaId)) continue;
				Area area = this.areaDao.load(Area.class, areaId);
				if(area != null){
					areas.add(area);
				}
			}
		}
		data.setAreas(areas);
		if(isAdded)this.subjectDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0)return;
		for(int i = 0; i < ids.length;i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Subject data = this.subjectDao.load(Subject.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("［%1$d］.删除数据［%2$s］...", i+1, data));
				this.subjectDao.delete(data);
			}
		}
	}
}