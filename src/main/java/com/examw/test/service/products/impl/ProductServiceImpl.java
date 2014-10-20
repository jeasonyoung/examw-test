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
import com.examw.test.dao.settings.IAreaDao;
import com.examw.test.dao.settings.IExamDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Area;
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
	private IAreaDao areaDao;
	private Map<Integer,String> statusMap;
	/**
	 * 设置产品数据接口
	 * @param productDao
	 * 产品数据接口
	 */
	public void setProductDao(IProductDao productDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品数据接口...");
		this.productDao = productDao;
	}
	/**
	 * 设置考试数据接口
	 * @param examDao
	 * 考试数据接口
	 */
	public void setExamDao(IExamDao examDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试数据接口...");
		this.examDao = examDao;
	}
	/**
	 * 设置考试科目数据接口
	 * @param subjectDao
	 * 考试科目数据接口
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置地区数据接口。
	 * @param areaDao 
	 *	  areaDao
	 */
	public void setAreaDao(IAreaDao areaDao) {
		if(logger.isDebugEnabled()) logger.debug("注入地区数据接口...");
		this.areaDao = areaDao;
	}
	/**
	 * 设置产品状态名称映射。
	 * @param statusMap
	 * 产品状态名称映射。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入产品状态名称映射...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态名称映射
	 * @see com.examw.test.service.products.IProductService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品状态［status = %d］名称...", status));
		if(status == null || statusMap == null) return null;
		return statusMap.get(status);
	}
	/*
	 * 加载考试下产品集合。
	 * @see com.examw.test.service.products.IProductService#loadProducts(java.lang.String)
	 */
	@Override
	public List<ProductInfo> loadProducts(String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［%s］下产品集合...", examId));
		return this.changeModel(this.productDao.loadProducts(examId));
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Product> find(ProductInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.productDao.findProducts(info);
	}
	/*
	 * 模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ProductInfo changeModel(Product data) {
		if (logger.isDebugEnabled())	logger.debug("[产品]数据模型转换...");
		if (data == null) return null;
		ProductInfo info = new ProductInfo();
		BeanUtils.copyProperties(data, info);
		//所属考试
		if(data.getExam() != null){
			info.setExamId(data.getExam().getId());
			info.setExamName(data.getExam().getName());
			if(data.getExam().getCategory() != null){
				info.setCategoryId(data.getExam().getCategory().getId());
				info.setCategoryName(data.getExam().getCategory().getName());
			}
		}
		//所属地区
		if(data.getArea() != null){
			info.setAreaId(data.getArea().getId());
			info.setAreaName(data.getArea().getName());
		}
		//包含科目
		if(data.getSubjects()!=null){
			List<String> subjectIds = new ArrayList<>(),subjectNames = new ArrayList<>();
			for(Subject subject : data.getSubjects()){
				if(subject != null) {
					subjectIds.add(subject.getId());
					subjectNames.add(subject.getName());
				}
			}
			info.setSubjectId(subjectIds.toArray(new String[0]));
			info.setSubjectName(subjectNames.toArray(new String[0]));
		}
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.products.IProductService#conversion(com.examw.test.domain.products.Product)
	 */
	@Override
	public ProductInfo conversion(Product product) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Product => ProductInfo ...");
		return this.changeModel(product);
	}
	/*
	 * 查询统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ProductInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.productDao.total(info);
	}
	/*
	 * 修改新增数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ProductInfo update(ProductInfo info) {
		if (logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Product  data = StringUtils.isEmpty(info.getId()) ?  null : this.productDao.load(Product.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new Product();
		}else{
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		BeanUtils.copyProperties(info, data);
		//所属考试
		data.setExam(StringUtils.isEmpty(info.getExamId()) ?  null : this.examDao.load(Exam.class, info.getExamId()));
		//所属地区
		data.setArea(StringUtils.isEmpty(info.getAreaId()) ?  null : this.areaDao.load(Area.class, info.getAreaId()));
		//包含科目
		Set<Subject> subjects = new HashSet<Subject>();
		if(info.getSubjectId() !=null){
			for(String subjectId : info.getSubjectId()){
				if(StringUtils.isEmpty(subjectId)) continue;
				Subject subject = this.subjectDao.load(Subject.class, subjectId);
				if(subject !=null) subjects.add(subject);
			}
		}
		data.setSubjects(subjects);
		data.setLastTime(new Date());
		if(isAdded) this.productDao.save(data);
		
		return this.changeModel(data);
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled()) logger.debug("删除数据[" + ids + "]...");
		if (ids == null || ids.length == 0) return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i])) continue;
			Product data = this.productDao.load(Product.class, ids[i]);
			if (data != null) {
				logger.debug("删除产品数据：" + ids[i]);
				this.productDao.delete(data);
			}
		}
	}
	/*
	 * 加载产品数据。
	 * @see com.examw.test.service.products.IProductService#loadProduct(java.lang.String)
	 */
	@Override
	public Product loadProduct(String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品[%s]信息...", productId));
		if(StringUtils.isEmpty(productId)) return null;
		return this.productDao.load(Product.class, productId);
	}
	/*
	 * 加载考试下最大排序号。
	 * @see com.examw.test.service.products.IProductService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［examId = %s］下最大排序号...", examId));
		return this.productDao.loadMaxOrder(examId);
	}
}