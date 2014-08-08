package com.examw.test.service.syllabus.impl;

import java.util.ArrayList;
import java.util.List;
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
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SyllabusInfo changeModel(Syllabus data) {
		if(logger.isDebugEnabled()) logger.debug("开始类型转换...");
		SyllabusInfo info = new SyllabusInfo();
		BeanUtils.copyProperties(data, info, new String[]{"children"});
		info.setFullName(this.loadFullName(data));
		if(data.getParent() != null)info.setPid(data.getParent().getId());
		if(data.getSubject() != null){
			info.setSubId(data.getSubject().getId());
			info.setSubName(data.getSubject().getName());
		}
		return info;
	}
	//加载大纲全称。
	private String loadFullName(Syllabus data){
		if(data == null) return null;
		if(data.getParent() == null) return data.getTitle();
		StringBuilder builder = new StringBuilder(data.getTitle());
		builder.insert(0, this.loadFullName(data.getParent()) + ">");
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
			if(parent != null && !parent.getId().equalsIgnoreCase(data.getId())) data.setParent(parent);
		}else {
			data.setParent(null);
		}
		if(!StringUtils.isEmpty(info.getSubId())){
			if(data.getParent() != null && data.getParent().getSubject() != null)
				data.setSubject(data.getParent().getSubject());
			else {
				Subject sub = this.subjectDao.load(Subject.class, info.getSubId());
				if(sub != null) data.setSubject(sub);
			}
		}
		info.setFullName(this.loadFullName(data));
		if(isAdded) this.syllabusDao.save(data);
		if(data.getSubject() != null){
			info.setSubId(data.getSubject().getId());
			info.setSubName(data.getSubject().getName());
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
	 * 加载科目大纲树数据。
	 * @see com.examw.test.service.syllabus.ISyllabusService#loadSyllabuss(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> loadSyllabuss(String sudId, String ignore) {
		if(logger.isDebugEnabled()) logger.debug("加载科目［"+sudId+"］大纲［ignore="+ignore+"］树数据...");
		List<TreeNode> nodes = new ArrayList<>();
		if(!StringUtils.isEmpty(sudId)){
			List<Syllabus> list = this.syllabusDao.loadFristSyllabuss(sudId);
			if(list != null){
				for(int  i = 0; i < list.size(); i++){
					TreeNode e = this.createNode(list.get(i), ignore);
					if(e != null) nodes.add(e);
				}
			}
		}
		return nodes;
	}
	//创建节点。
	private TreeNode createNode(Syllabus data,String ignore){
		if(data == null || (!StringUtils.isEmpty(ignore) && data.getId().equalsIgnoreCase(ignore))) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getTitle());
		if(data.getChildren() != null){
			List<TreeNode> children = new ArrayList<>();
			for(Syllabus p : data.getChildren()){
				TreeNode e = this.createNode(p, ignore);
				if(e != null) children.add(e);
			}
			if(children.size() > 0)node.setChildren(children);
		}
		return node;
	}
}