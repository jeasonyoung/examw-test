package com.examw.test.service.products.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IProductDao;
import com.examw.test.dao.settings.IExamDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.products.ProductInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.IProductService;

/**
 * 产品服务接口实现类
 * @author fengwei.
 * @since 2014年8月12日 下午3:38:24.
 */
public class ProductServiceImpl  extends BaseDataServiceImpl<Product,ProductInfo> implements IProductService{
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	private IProductDao productDao;
	private IExamDao examDao;
	private ISubjectDao subjectDao;
	private Map<Integer,String> statusMap;
	/**
	 * 设置 产品数据接口
	 * @param productDao
	 * 
	 */
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}
	
	/**
	 * 设置 考试数据接口
	 * @param examDao
	 * 
	 */
	public void setExamDao(IExamDao examDao) {
		this.examDao = examDao;
	}

	/**
	 * 设置 考试科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	
	/**
	 * 设置 状态名称映射
	 * @param statusMap
	 * 
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}

	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Product> find(ProductInfo info) {
		return this.productDao.findProducts(info);
	}
	/*
	 * 模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ProductInfo changeModel(Product data) {
		if (logger.isDebugEnabled())	logger.debug("[产品]数据模型转换...");
		if (data == null)
			return null;
		ProductInfo info = new ProductInfo();
		BeanUtils.copyProperties(data, info);
		//所属考试
		if(data.getExam()!=null){
			info.setExamId(data.getExam().getId());
			info.setExamName(data.getExam().getName());
		}
		//包含科目
		if(data.getSubjects()!=null){
			List<String> list = new ArrayList<>();
			String name = "";
			for(Subject subject : data.getSubjects()){
				if(subject != null) {
					list.add(subject.getId());
					name += subject.getName()+",";
				}
			}
			info.setSubjectId(list.toArray(new String[0]));
			info.setSubjectName(name);
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ProductInfo info) {
		return this.productDao.total(info);
	}
	/*
	 * 修改新增数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ProductInfo update(ProductInfo info) {
		if (logger.isDebugEnabled())	logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Product  data = StringUtils.isEmpty(info.getId()) ?  null : this.productDao.load(Product.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Product();
			data.setCreateTime(new Date());
		}
		info.setCreateTime(data.getCreateTime());
		BeanUtils.copyProperties(info, data);
		if(data.getStatus() == null){
			data.setStatus(Product.STATUS_NONE);
			info.setStatus(Product.STATUS_NONE);
		}
		//所属考试
		if(info.getExamId()!=null && (data.getExam()==null || !data.getExam().getId().equalsIgnoreCase(info.getExamId()))){
			Exam exam = this.examDao.load(Exam.class, info.getExamId());
			if(exam!=null)
			{
				data.setExam(exam);
				info.setExamName(exam.getName());
			}
		}
		//包含科目
		Set<Subject> subjects = new HashSet<Subject>();
		if(info.getSubjectId()!=null && info.getSubjectId().length>0){
			for(String id:info.getSubjectId()){
				Subject s = this.subjectDao.load(Subject.class, id);
				if(s!=null) subjects.add(s);
			}
		}
		data.setSubjects(subjects);
		//新增数据。
		if(isAdded) this.productDao.save(data);
		else{
			data.setLastTime(new Date());
			info.setLastTime(new Date());
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())
			logger.debug("删除数据...");
		if (ids == null || ids.length == 0)
			return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))
				continue;
			Product data = this.productDao.load(Product.class, ids[i]);
			if (data != null) {
				logger.debug("删除产品数据：" + ids[i]);
				this.productDao.delete(data);
			}
		}
	}
	
	/*
	 * 加载最大的代码值
	 * @see com.examw.test.service.products.IProductService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		List<Product> sources = this.productDao.findProducts(new ProductInfo(){
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
	/*
	 * 加载状态名称映射
	 * @see com.examw.test.service.products.IProductService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(status == null || statusMap == null)
		return null;
		return statusMap.get(status);
	}
	/*
	 *  加载考试下所有的产品
	 * @see com.examw.test.service.products.IProductService#loadProducts(java.lang.String)
	 */
	@Override
	public List<ProductInfo> loadProducts(final String examId) {
		if(logger.isDebugEnabled()) logger.debug("加载考试下所有的产品...");
		return this.datagrid(new ProductInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getExamId() {return examId;}
			@Override
			public Integer getStatus() {return Product.STATUS_NONE;}	//状态必须正常
			@Override
			public String getSort() {return "examId"; } 
			@Override
			public String getOrder() { return "desc";}}).getRows();
	}
	/*
	 * 根据id加载产品
	 * @see com.examw.test.service.products.IProductService#loadProduct(java.lang.String)
	 */
	@Override
	public ProductInfo loadProduct(String id) {
		return this.changeModel(this.productDao.load(Product.class, id));
	}
}
