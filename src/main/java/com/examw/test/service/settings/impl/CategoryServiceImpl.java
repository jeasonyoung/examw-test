package com.examw.test.service.settings.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
import com.examw.test.dao.settings.ICategoryDao;
import com.examw.test.domain.settings.Category;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.settings.CategoryInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.settings.ICategoryService;

/**
 * 考试分类服务接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午3:36:37.
 */
public class CategoryServiceImpl extends BaseDataServiceImpl<Category, CategoryInfo>
		implements ICategoryService {
	private static final Logger logger = Logger	.getLogger(CategoryServiceImpl.class);
	private ICategoryDao categoryDao;
	/**
	 * 设置 考试分类数据接口
	 * @param categoryDao
	 * 
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Category> find(CategoryInfo info) {
		if (logger.isDebugEnabled())	logger.debug("查询[考试分类]数据...");
		return categoryDao.findCategorys(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected CategoryInfo changeModel(Category data) {
		if (logger.isDebugEnabled())	logger.debug("[考试分类]数据模型转换...");
		if(data == null) return null;
		CategoryInfo info = new CategoryInfo();
		BeanUtils.copyProperties(data, info, new String[] {"children"});
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<CategoryInfo> children = new ArrayList<>();
			for(Category child : data.getChildren()){
				CategoryInfo c = this.changeModel(child);
				if(c != null){
					c.setPid(info.getId());
					children.add(c);
				}
			}
			if(children.size() > 0){
				info.setChildren(children);
			}
		}
		return info;
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(CategoryInfo info) {
		if (logger.isDebugEnabled())	logger.debug("查询[考试分类]数据总数...");
		return this.categoryDao.total(info);
	}
	/*
	 * 更新或插入数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public CategoryInfo update(CategoryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Category data = StringUtils.isEmpty(info.getId()) ? null :this.categoryDao.load(Category.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Category();
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getPid()) && (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid()))){
			Category parent = this.categoryDao.load(Category.class, info.getPid());
			if(parent != null)data.setParent(parent);
		}
		if(isAdded) this.categoryDao.save(data);
		return info;
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Category data = this.categoryDao.load(Category.class, ids[i]);
			if(data != null && (data.getChildren() == null || data.getChildren().size() == 0)){
				this.categoryDao.delete(data); 
				if(logger.isDebugEnabled()) logger.debug("删除数据:" + data.getName());
			}
		}
	}
	
	/*
	 * 加载最大的代码值
	 * @see com.examw.test.service.settings.ICategoryService#loadMaxCode(java.lang.String)
	 */
	@Override
	public String[] loadMaxCode(final String pid) {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		Integer max = null;
		List<Category> sources = this.find(new CategoryInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getPid() { return pid;}
			@Override
			public String getSort() {return "code"; } 
			@Override
			public String getOrder() { return "desc";}
		});
		if(sources != null && sources.size() > 0){
			max = new Integer(sources.get(0).getCode());
		}
		if(max == null)
		{
			max = 0;
			if(StringUtils.isEmpty(pid))	//顶级
				return new String[]{ String.format("%02d", max + 1) };
			String code = this.categoryDao.load(Category.class, pid).getCode();
			return new String[]{String.format("%0"+(code.length()+2)+"d", new Integer(code)*100+1)};
		}
		return new String[]{ String.format("%02d", max + 1) };
	}
	
	/*
	 * 查询所有的考试分类
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategorys()
	 */
	@Override
	public List<TreeNode> loadAllCategorys() {
		List<TreeNode> result = new ArrayList<>();
		List<Category> list = this.categoryDao.findCategorys(new CategoryInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getOrder() {return "asc";}
			@Override
			public String getSort() {return "code";};
		});
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				result.add(createTreeNode(data,null,false,false));
			}
		}
		return result;
	}
	/**
	 * 根据条件创建树节点
	 * @param data		考试分类
	 * @param attributes	分类节点带的属性
	 * @param withExam	是否加载所包含考试
	 * @param withSubject	是否加载所包含科目
	 * @return
	 */
	private TreeNode createTreeNode(Category data,Map<String, Object> attributes,boolean withExam,boolean withSubject){
		if(data == null) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getName());
		node.setAttributes(attributes);
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<TreeNode> list = new ArrayList<>();
			for(Category c : data.getChildren()){
				TreeNode t = this.createTreeNode(c,attributes,withExam,withSubject);
				 if(t != null){
					 list.add(t);
				 }
			}
			node.setChildren(list);
//			if(node.getChildren()==null)
//				node.setChildren(list);
//			else{
//				list.addAll(node.getChildren());
//				node.setChildren(list);
//			}
		}
		if(withExam){
			List<TreeNode> list_exams = new ArrayList<>();
			for(final Exam e : data.getExams()){
				if(e == null) continue;
				TreeNode tv_exam = new TreeNode();
				tv_exam.setId(e.getId());
				tv_exam.setText(e.getName());
				attributes = new HashMap<>();
				attributes.put("type", "exam");
				tv_exam.setAttributes(attributes);
				if(withSubject){
					if(e.getSubjects() != null && e.getSubjects().size() > 0){
						List<TreeNode> list_subjects = new ArrayList<>();
						for(Subject s : e.getSubjects()){
							TreeNode tv_subject = new TreeNode();
							tv_subject.setId(s.getId());
							tv_subject.setText(s.getName());
							attributes = new HashMap<>();
							attributes.put("type", "subject");
							tv_subject.setAttributes(attributes);
							list_subjects.add(tv_subject);
						}
						tv_exam.setChildren(list_subjects);
					}
					list_exams.add(tv_exam);
				}else{
					list_exams.add(tv_exam);
				}
			}
			if(node.getChildren()==null)
				node.setChildren(list_exams);
			else{
				list_exams.addAll(node.getChildren());
				node.setChildren(list_exams);
			}
		}
		return node;
	}
	/*
	 * 加载所有的考试分类-考试树
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExams()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExams() {
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list = this.find(new CategoryInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getOrder() {return "asc";}
			@Override
			public String getSort() {return "code";};
		});
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				Map<String,Object> attributes = new HashMap<>();
				attributes.put("type", "category");
				treeNodes.add(createTreeNode(data,attributes,true,false));
			}
		}
		return treeNodes;
	}
	/*
	 * 加载考试分类-考试-科目树
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExamSubjects()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExamSubjects() {
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list = this.find(new CategoryInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getOrder() {return "asc";}
			@Override
			public String getSort() {return "code";};
		});
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				Map<String,Object> attributes = new HashMap<>();
				attributes.put("type", "category");
				treeNodes.add(createTreeNode(data,attributes,true,true));
			}
		}
		return treeNodes;
	}
}
