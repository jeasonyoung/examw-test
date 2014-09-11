package com.examw.test.service.settings.impl;

import java.util.ArrayList;
import java.util.List;
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
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.settings.ISubjectService;

/**
 * 科目服务接口实现类
 * @author fengwei.
 * @since 2014年8月7日 上午10:27:40.
 */
public class SubjectServiceImpl extends BaseDataServiceImpl<Subject, SubjectInfo> implements ISubjectService {
	private static final Logger logger = Logger.getLogger(SubjectServiceImpl.class);
	private IExamDao examDao;
	private IAreaDao areaDao;
	private ISubjectDao subjectDao;
	
	/**
	 * 设置 考试数据接口
	 * @param examDao
	 * 
	 */
	public void setExamDao(IExamDao examDao) {
		this.examDao = examDao;
	}
	/**
	 * 设置 地区数据接口
	 * @param areaDao
	 * 
	 */
	public void setAreaDao(IAreaDao areaDao) {
		this.areaDao = areaDao;
	}
	/**
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Subject> find(SubjectInfo info) {
		return this.subjectDao.findSubjects(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SubjectInfo changeModel(Subject data) {
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
		if(data.getArea()!=null){
			info.setAreaId(data.getArea().getId());
			info.setAreaName(data.getArea().getName());
		}
		return info;
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SubjectInfo info) {
		return this.subjectDao.total(info);
	}
	/*
	 * 更新或插入数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SubjectInfo update(SubjectInfo info) {
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
		//考试设置
		if(!StringUtils.isEmpty(info.getExamId()) && (data.getExam() == null || !data.getExam().getId().equalsIgnoreCase(info.getExamId()))){
			Exam exam = this.examDao.load(Exam.class, info.getExamId());
			if(exam != null) data.setExam(exam);
		}
		if(data.getExam() != null){
			info.setExamName(data.getExam().getName());
			if(data.getExam().getCategory() != null){
				info.setCategoryId(data.getExam().getCategory().getId());
				info.setCategoryName(data.getExam().getCategory().getName());
			}
		}
		//设置地区
		if(!StringUtils.isEmpty(info.getAreaId()) && (data.getArea() == null || !data.getArea().getId().equalsIgnoreCase(info.getAreaId()))){
			Area area = this.areaDao.load(Area.class, info.getAreaId());
			if(area != null) data.setArea(area);
		}
		if(data.getArea() != null){
			info.setAreaName(data.getArea().getName());
		}
		if(isAdded)this.subjectDao.save(data);
		return info;
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Subject data = this.subjectDao.load(Subject.class, ids[i]);
			if(data != null){
				this.subjectDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大代码值
	 * @see com.examw.test.service.settings.ISubjectService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		List<Subject> sources = this.subjectDao.findSubjects(new SubjectInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() {return "code"; } 
			@Override
			public String getOrder() { return "desc";}
		});
		if(sources != null && sources.size() > 0){
			return sources.get(0).getCode();
		}
		return null;
	}
	
	@Override
	public List<SubjectInfo> changeModel(List<Subject> list) {
		List<SubjectInfo> results = new ArrayList<>();
		if(list != null && list.size() > 0){
			for(Subject data : list){
				SubjectInfo info = this.changeModel(data);
				if(info != null){
					results.add(info);
				}
			}
		}
		return results;
	}
}
