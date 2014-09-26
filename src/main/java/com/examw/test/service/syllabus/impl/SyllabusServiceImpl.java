package com.examw.test.service.syllabus.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
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
		if(data == null) return null;
		SyllabusInfo info = new SyllabusInfo();
		BeanUtils.copyProperties(data, info, new String[]{"children"});
		info.setFullTitle(this.buildFullTitle(data));
		if(data.getParent() != null) info.setPid(data.getParent().getId());
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			Set<SyllabusInfo> children = new TreeSet<>();
			for(Syllabus syllabus : data.getChildren()){
				if(syllabus == null) continue;
				SyllabusInfo e = this.changeModel(syllabus);
				if(e != null) children.add(e);
			}
			if(children.size() > 0) info.setChildren(children);
		}
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.syllabus.ISyllabusService#conversion(com.examw.test.domain.syllabus.Syllabus)
	 */
	@Override
	public SyllabusInfo conversion(Syllabus syllabus) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Syllabus => SyllabusInfo ...");
		return this.changeModel(syllabus);
	}
	//加载大纲全称。
	private String buildFullTitle(Syllabus data){
		if(data == null) return null;
		if(data.getParent() == null) return data.getTitle();
		StringBuilder builder = new StringBuilder(data.getTitle());
		builder.insert(0, this.buildFullTitle(data.getParent()) + ">");
		return builder.toString();
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
		if(!StringUtils.isEmpty(info.getPid()) && (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid()))){
			Syllabus parent = this.syllabusDao.load(Syllabus.class, info.getPid());
			if(parent != null && !parent.getId().equalsIgnoreCase(data.getId())) {
				data.setParent(parent);
			}
		}
		data.setSubject(StringUtils.isEmpty(info.getSubjectId()) ? null : this.subjectDao.load(Subject.class, info.getSubjectId()));
		
		info.setFullTitle(this.buildFullTitle(data));
		if(isAdded) this.syllabusDao.save(data);
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		return info;
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
	 * 加载考试大纲树结构。
	 * @see com.examw.test.service.syllabus.ISyllabusService#loadSyllabuses(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> loadSyllabuses(String subjectId, String ignore) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［subjectId = %s］ 大纲，忽略［syllabusId = %s］...", subjectId, ignore));
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Syllabus> syllabuses = this.syllabusDao.loadFristSyllabuss(subjectId);
		if(syllabuses != null && syllabuses.size() > 0){
			 for(Syllabus syllabus : syllabuses){
				 TreeNode node = this.createNode(syllabus, ignore);
				 if(node != null){
					 treeNodes.add(node);
				 }
			 }
		}
		return treeNodes;
	}
	//创建节点。
	private TreeNode createNode(Syllabus data,String ignore){
		if(data == null || (!StringUtils.isEmpty(ignore) && data.getId().equalsIgnoreCase(ignore))) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getTitle());
		if(data.getChildren() != null){
			List<TreeNode> children = new ArrayList<>();
			for(Syllabus s : data.getChildren()){
				TreeNode e = this.createNode(s, ignore);
				if(e != null) children.add(e);
			}
			if(children.size() > 0)node.setChildren(children);
		}
		return node;
	}
	/*
	 * 加载最大的代码值。
	 * @see com.examw.oa.service.check.ICatalogService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.syllabusDao.loadMaxCode();
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
		List<Syllabus> syllabuses = this.syllabusDao.loadFristSyllabuss(subjectId);
		if(syllabuses != null && syllabuses.size() > 0){
			for(Syllabus syllabus : syllabuses){
				SyllabusInfo info = this.changeModel(syllabus);
				if(info != null){
					list.add(info);
				}
			}
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