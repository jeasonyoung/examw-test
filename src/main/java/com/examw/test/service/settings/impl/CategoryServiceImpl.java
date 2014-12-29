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
import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Category;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.settings.CategoryInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.settings.ICategoryService;
/**
 * 考试类别服务接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午3:36:37.
 */
public class CategoryServiceImpl extends BaseDataServiceImpl<Category, CategoryInfo> implements ICategoryService {
	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	private ICategoryDao categoryDao;
	/**
	 * 设置考试分类数据接口。
	 * @param categoryDao
	 * 考试分类数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试分类数据接口...");
		this.categoryDao = categoryDao;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Category> find(CategoryInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询数据...");
		return categoryDao.findCategorys(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected CategoryInfo changeModel(Category data) {
		if (logger.isDebugEnabled())logger.debug("数据模型转换［Category => CategoryInfo ］...");
		if(data == null) return null;
		CategoryInfo info = new CategoryInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getParent() != null){
			info.setPid(data.getParent().getId());
		}
		info.setFullName(this.loadFullName(data));
		return info;
	}
	//加载类别全称。
	private String loadFullName(Category data){
		if(data == null) return null;
		if(data.getParent() == null) return data.getName();
		StringBuilder builder = new StringBuilder(data.getName());
		builder.insert(0, this.loadFullName(data.getParent()) + " >> ");
		return builder.toString();
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(CategoryInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询数据统计...");
		return this.categoryDao.total(info);
	}
	/*
	 * 更新数据。
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
			//自己不能是自己的父类
			if(parent != null && !parent.getId().equalsIgnoreCase(data.getId())){
				data.setParent(parent);
			}
		}
		if(StringUtils.isEmpty(info.getPid())){
			data.setParent(null);
		}
		if(isAdded) this.categoryDao.save(data);
		return this.changeModel(data);
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
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("［%1$d］删除数据［%2$s］...", i+1, data.getId()));
				this.categoryDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大的代码值
	 * @see com.examw.test.service.settings.ICategoryService#loadMaxCode(java.lang.String)
	 */
	@Override
	public Integer loadMaxCode(String parentCatalogId) {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.categoryDao.loadMaxCode(parentCatalogId);
	}
	/*
	 * 加载全部考试类别。
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategorys()
	 */
	@Override
	public List<TreeNode> loadAllCategorys(String ignoreCategoryId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载全部考试类别［ignore = %s］", ignoreCategoryId));
		List<TreeNode> result = new ArrayList<>();
		List<Category> topCategories = this.categoryDao.loadTopCategories();
		if(topCategories != null && topCategories.size() > 0){
			for(Category data : topCategories){
				if(data == null) continue;
				TreeNode e = this.buildCategoryNode(data, ignoreCategoryId, true);
				if(e != null)result.add(e);
			}
		}
		return result;
	}
	//创建考试类别节点
	private TreeNode buildCategoryNode(Category category,String ignoreCategoryId, boolean withChildren){
		if(category == null || (!StringUtils.isEmpty(ignoreCategoryId) && category.getId().equalsIgnoreCase(ignoreCategoryId))) return null;
		TreeNode node = new TreeNode(); 
		node.setId(category.getId());
		node.setText(category.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("type", "category");
		node.setAttributes(attributes);
		if(withChildren && category.getChildren() != null && category.getChildren().size() > 0){
			List<TreeNode> children = new ArrayList<TreeNode>();
			for(Category child : category.getChildren()){
				TreeNode e = this.buildCategoryNode(child, ignoreCategoryId, withChildren);
				if(e != null) children.add(e);
			}
			if(children.size() > 0) node.setChildren(children);
		}
		return node;
	}
	//创建考试节点
	private TreeNode buildExamNode(Exam exam){
		if(exam == null) return null;
		TreeNode node = new TreeNode();
		node.setId(exam.getId());
		node.setText(exam.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("type", "exam");
		node.setAttributes(attributes);
		return node;
	}
	//创建科目节点
	private TreeNode buildSubjectNode(Subject subject, boolean withChildren){
		if(subject == null) return null;
		TreeNode node = new TreeNode();
		node.setId(subject.getId());
		node.setText(subject.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("type", "subject");
		node.setAttributes(attributes);
		if(withChildren && subject.getChildren() != null && subject.getChildren().size() > 0){
			List<TreeNode> children = new ArrayList<TreeNode>();
			for(Subject child : subject.getChildren()){
				TreeNode e = this.buildSubjectNode(child, withChildren);
				if(e != null) children.add(e);
			}
			if(children.size() > 0) node.setChildren(children);
		}
		return node;
	}
	/*
	 * 加载全部考试类别/考试树。
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExams()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExams() {
		if(logger.isDebugEnabled()) logger.debug("加载全部考试类别/考试树...");
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list =  this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(Category data : list){
				TreeNode e = this.buildAllCategoryExamNode(data);
				if(e != null)treeNodes.add(e);
			}
		}
		return treeNodes;
	}
	//创建全部考试类别/考试节点
	private TreeNode buildAllCategoryExamNode(Category category){
		if(category == null) return null;
		TreeNode node = this.buildCategoryNode(category, null, false);
		if(node != null){
			if(category.getExams() != null && category.getExams().size() > 0){//考试
				List<TreeNode> examNodes = new ArrayList<>();
				for(Exam exam : category.getExams()){
					TreeNode tn = this.buildExamNode(exam);
					if(tn != null) examNodes.add(tn);
				}
				if(examNodes.size() > 0) node.setChildren(examNodes);
			}
			if(category.getChildren() != null && category.getChildren().size() > 0){//子节点
				List<TreeNode> childrenNodes = new ArrayList<>();
				for(Category child : category.getChildren()){
					if(child == null) continue;
					TreeNode e = this.buildAllCategoryExamNode(child);
					if(e != null) childrenNodes.add(e);
				}
				if(childrenNodes.size() > 0) node.setChildren(childrenNodes);
			}
		}
		return node;
	}
	/*
	 * 加载全部考试类别/考试/顶级科目树。
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExamSubjects()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExamSubjects() {
		if(logger.isDebugEnabled()) logger.debug("加载全部考试类别/考试/科目树...");
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list = this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(Category data : list){
				TreeNode e = this.buildCategoryExamSubjectNode(data, true);
				if(e != null)treeNodes.add(e);
			}
		}
		return treeNodes;
	}
	/*
	 *  加载全部考试类别/考试/全部科目树。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#datagrid(java.lang.Object)
	 */
	@Override
	public List<TreeNode> loadAllCategoryExamAllSubjects() {
		if(logger.isDebugEnabled()) logger.debug("加载全部考试类别/考试/科目树...");
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Category> list = this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(Category data : list){
				TreeNode e = this.buildCategoryExamSubjectNode(data, false);
				if(e != null)treeNodes.add(e);
			}
		}
		return treeNodes;
	}
	//构建考试类别/考试/科目
	private TreeNode buildCategoryExamSubjectNode(Category category,boolean topSubject){
		if(category == null) return null;
		TreeNode node = this.buildCategoryNode(category, null, false);
		if(node == null) return node;
		if(category.getExams() != null && category.getExams().size() > 0){//考试
			List<TreeNode> examNodes = new ArrayList<>();
			for(Exam exam : category.getExams()){
				TreeNode tn = this.buildExamNode(exam);
				if(tn != null){
					if(exam.getSubjects() != null && exam.getSubjects().size() > 0){//科目
						List<TreeNode> subjectNodes = new ArrayList<>();
						for(Subject subject : exam.getSubjects()){
							TreeNode e = this.buildSubjectNode(subject, !topSubject);
							if(e != null) subjectNodes.add(e);
						}
						if(subjectNodes.size() > 0) tn.setChildren(subjectNodes);
					}
					examNodes.add(tn);
				}
			}
			if(examNodes.size() > 0) node.setChildren(examNodes);
		}
		//children
		if(category.getChildren() != null && category.getChildren().size() > 0){
			List<TreeNode> childrenNodes = new ArrayList<>();
			for(Category child : category.getChildren()){
				TreeNode e = this.buildCategoryExamSubjectNode(child, topSubject);
				if(e != null) childrenNodes.add(e);
			}
			if(childrenNodes.size() > 0) node.setChildren(childrenNodes);
		}
		return node;
	}
	/*
	 * 加载全部考试类别/考试/产品树。
	 * @see com.examw.test.service.settings.ICategoryService#loadAllCategoryExamProducts()
	 */
	@Override
	public List<TreeNode> loadAllCategoryExamProducts() {
		if(logger.isDebugEnabled()) logger.debug("加载全部考试类别/考试/产品树...");
		List<TreeNode> treeNodes = new ArrayList<>(); 
		List<Category> list = this.categoryDao.loadTopCategories();
		if(list != null && list.size() > 0){
			for(Category category : list){
				TreeNode e = this.buildCategoryExamProduct(category);
				if(e != null) treeNodes.add(e);
			}
		}
		return treeNodes;
	}
	//构建产品节点
	private TreeNode buildProductNode(Product product){
		if(product == null) return null;
		TreeNode node = new TreeNode();
		node.setId(product.getId());
		node.setText(product.getName());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("type", "product");
		node.setAttributes(attributes);
		return node;
	}
	//构建考试类别/考试/产品节点
	private TreeNode buildCategoryExamProduct(Category category){
		if(category == null) return null;
		TreeNode node = this.buildCategoryNode(category, null, false);
		if(node == null) return null;
		if(category.getExams() != null && category.getExams().size() > 0){//考试
			List<TreeNode> examNodes = new ArrayList<>();
			for(Exam exam : category.getExams()){
				TreeNode examNode = this.buildExamNode(exam);
				if(examNode != null){
					if(exam.getProducts() != null && exam.getProducts().size() > 0){//产品
						List<TreeNode> productNodes = new ArrayList<>();
						for(Product product : exam.getProducts()){
							TreeNode pNode = this.buildProductNode(product);
							if(pNode != null) productNodes.add(pNode);
						}
						if(productNodes.size() > 0) examNode.setChildren(productNodes);
					}
					examNodes.add(examNode);
				}
			}
			if(examNodes.size() > 0) node.setChildren(examNodes);
		}
		if(category.getChildren() != null && category.getChildren().size() > 0){//children
			List<TreeNode> childrenNodes = new ArrayList<>();
			for(Category child : category.getChildren()){
				TreeNode e = this.buildCategoryExamProduct(child);
				if(e != null) childrenNodes.add(e);
			}
			if(childrenNodes.size() > 0) node.setChildren(childrenNodes);
		}
		return node;
	}
}