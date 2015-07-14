package com.examw.test.service.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.dao.settings.ICategoryDao;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.settings.Category;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.api.AppClientPush;
import com.examw.test.model.api.AppClientSync;
import com.examw.test.model.api.CategorySync;
import com.examw.test.model.api.ExamSync;
import com.examw.test.model.api.FavoriteSync;
import com.examw.test.model.api.PaperItemRecordSync;
import com.examw.test.model.api.PaperRecordSync;
import com.examw.test.model.api.PaperSync;
import com.examw.test.model.api.ProductSync;
import com.examw.test.model.api.SubjectSync;
import com.examw.test.model.records.UserItemFavoriteInfo;
import com.examw.test.model.records.UserItemRecordInfo;
import com.examw.test.model.records.UserPaperRecordInfo;
import com.examw.test.service.api.IDataSyncService;
import com.examw.test.service.library.PaperType;
import com.examw.test.service.products.IProductService;
import com.examw.test.service.products.IRegistrationCodeService;
import com.examw.test.service.records.IUserItemFavoriteService;
import com.examw.test.service.records.IUserItemRecordService;
import com.examw.test.service.records.IUserPaperRecordService;

/**
 * 数据同步服务接口实现类。
 * 
 * @author yangyong
 * @since 2015年2月27日
 */
public class DataSyncServiceImpl implements IDataSyncService {
	private final static Logger logger = Logger.getLogger(DataSyncServiceImpl.class);
	private Cache cache;
	private IPaperReleaseDao paperReleaseDao;
	private IRegistrationCodeService registrationCodeService;
	private IUserPaperRecordService userPaperRecordService;
	private IUserItemRecordService userItemRecordService;
	private IUserItemFavoriteService userItemFavoriteService;
	private IProductService productService;
	private ICategoryDao categoryDao;
	
	/**
	 * 设置EnCache缓存。
	 * @param cache 
	 *	  EnCache缓存。
	 */
	public void setCache(Cache cache) {
		logger.debug("注入缓存对象...");
		this.cache = cache;
	}
	/**
	 * 设置发布试卷数据接口。
	 * @param paperReleaseDao 
	 *	  发布试卷数据接口。
	 */
	public void setPaperReleaseDao(IPaperReleaseDao paperReleaseDao) {
		logger.debug("注入发布试卷数据接口...");
		this.paperReleaseDao = paperReleaseDao;
	}
	/**
	 * 设置注册码服务接口。
	 * @param registrationCodeService 
	 *	  注册码服务接口。
	 */
	public void setRegistrationCodeService(IRegistrationCodeService registrationCodeService) {
		logger.debug("注入注册码服务接口...");
		this.registrationCodeService = registrationCodeService;
	}
	/**
	 * 设置用户试卷记录服务接口。
	 * @param userPaperRecordService 
	 *	  用户试卷记录服务接口。
	 */
	public void setUserPaperRecordService(IUserPaperRecordService userPaperRecordService) {
		logger.debug("注入用户试卷记录服务接口...");
		this.userPaperRecordService = userPaperRecordService;
	}
	/**
	 * 设置用户试题记录服务接口。
	 * @param userItemRecordService 
	 *	  用户试题记录服务接口。
	 */
	public void setUserItemRecordService(IUserItemRecordService userItemRecordService) {
		logger.debug("注入用户试题记录服务接口...");
		this.userItemRecordService = userItemRecordService;
	}
	/**
	 * 设置用户试题收藏服务接口。
	 * @param userItemFavoriteService 
	 *	  用户试题收藏服务接口。
	 */
	public void setUserItemFavoriteService(IUserItemFavoriteService userItemFavoriteService) {
		logger.debug("注入用户试题收藏服务接口...");
		this.userItemFavoriteService = userItemFavoriteService;
	}
	/**
	 * 设置产品服务接口。
	 * @param productService 
	 *	  产品服务接口。
	 */
	public void setProductService(IProductService productService) {
		logger.debug("注入产品服务接口...");
		this.productService = productService;
	}
	/**
	 * 设置考试分类数据接口。
	 * @param categoryDao 
	 *	  考试分类数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		logger.debug("注入考试分类数据接口...");
		this.categoryDao = categoryDao;
	}
	//缓存键名
	private static final String CACHE_KEY_CATEGORIES = "__downloadCategories__";
	/*
	 * 下载考试类别数据。
	 * @see com.examw.test.service.api.IDataSyncService#downloadCategories()
	 */
	@Override
	public List<CategorySync> downloadCategories() throws Exception {
		logger.debug("下载考试类别数据...");
		//检查缓存
		if(this.cache != null){
			Element element = this.cache.get(CACHE_KEY_CATEGORIES);
			if(element != null){
				logger.debug("从缓存中加载考试类别数据...");
				CategorySync[] arrays = (CategorySync [])element.getObjectValue();
				if(arrays != null && arrays.length > 0){
					 logger.debug("从缓存中反馈考试类别数据...");
					return Arrays.asList(arrays);
				}
			}
		}
		//初始化目标数据
		List<CategorySync> list  = new ArrayList<CategorySync>();
		//从数据库中加载数据..
		List<Category> categories =  this.categoryDao.loadTopCategories();
		if(categories != null && categories.size() > 0){
			for(Category category : categories){
				if(category == null)continue;
				CategorySync categorySync = new CategorySync();
				categorySync.setId(category.getId());
				categorySync.setCode(category.getCode());
				categorySync.setName(category.getName());
				categorySync.setAbbr(category.getAbbr());
				//
				this.createCategories(categorySync, category);
				//考试排序
				if(categorySync.getExams() != null && categorySync.getExams().size() > 0){
					Collections.sort(categorySync.getExams(), new Comparator<ExamSync>(){
						@Override
						public int compare(ExamSync o1, ExamSync o2) {
							return o1.getCode() - o2.getCode();
						}
					});
				}
				//
				list.add(categorySync);
			}
			//排序
			Collections.sort(list, new Comparator<CategorySync>(){
				@Override
				public int compare(CategorySync o1, CategorySync o2) {
					return o1.getCode() - o2.getCode();
				}
			});
		}
		//检查数据并进行缓存处理
		if(this.cache != null && list != null && list.size() > 0){
			logger.debug("将考试类别数据加载到缓存中...");
			this.cache.put(new Element(CACHE_KEY_CATEGORIES,  list.toArray(new CategorySync[0])));
		}
		return list;
	}
	//创建考试类别模型
	private void createCategories(CategorySync categorySync,Category category){
		if(categorySync == null || category == null) return;
		//创建考试
		this.createExamProducts(categorySync, category.getExams());
		//
		if(category.getChildren() != null && category.getChildren().size() > 0){
			for(Category c : category.getChildren()){
				if(c == null) continue;
				this.createCategories(categorySync, c);
			}
		}
	}
	//创建考试及其产品
	private void createExamProducts(CategorySync categorySync, Set<Exam> exams){
		if(categorySync == null || exams == null || exams.size() == 0) return;
		if(categorySync.getExams() == null){
			categorySync.setExams(new ArrayList<ExamSync>());
		}
		for(Exam exam : exams){
			if(exam == null || exam.getStatus() == 0) continue;
			//考试
			ExamSync examSync = new ExamSync();
			examSync.setId(exam.getId());
			examSync.setCode(exam.getCode());
			examSync.setName(exam.getName());
			examSync.setAbbr(exam.getAbbr());
			//产品
			if(exam.getProducts() != null){
				List<ProductSync> productSyncs = new ArrayList<ProductSync>();
				for(Product product : exam.getProducts()){
					if(product == null || product.getStatus() == 0) continue;
					ProductSync productSync = new ProductSync();
					productSync.setId(product.getId());
					productSync.setName(product.getName());
					productSync.setPrice(product.getPrice());
					productSync.setDiscount(product.getDiscount());
					if(product.getArea() != null){
						productSync.setArea(product.getArea().getName());
					}
					productSync.setPapers(product.getPaperTotal());
					productSync.setItems(product.getItemTotal());
					productSync.setOrder(product.getOrderNo());
					
					productSyncs.add(productSync);
				}
				if(productSyncs.size() > 0){
					Collections.sort(productSyncs, new Comparator<ProductSync>(){
						@Override
						public int compare(ProductSync o1, ProductSync o2) {
							return o1.getOrder() - o2.getOrder();
						}
					});
					examSync.setProducts(productSyncs);
				}
			}
			//添加到考试类别
			categorySync.getExams().add(examSync);
		}
	}
	/*
	 * 同步考试科目数据。
	 * @see com.examw.test.service.api.IDataSyncService#syncExams(com.examw.test.model.api.AppClientSync)
	 */
	@Override
	public ExamSync syncExams(AppClientSync req) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("同步考试科目数据...");
		final String cacheKey = "syncExams_" + req.getProductId();
		//检查缓存
		if(this.cache != null){
			Element element = this.cache.get(cacheKey);
			if(element != null){
				ExamSync exam = (ExamSync)element.getObjectValue();
				if(exam != null){
					logger.debug("从缓存中加载产品考试科目数据...");
					return exam;
				}
			}
		}
		//校验产品
		Product p = this.validationSyncReq(req);
		if(p == null) throw new Exception("加载产品数据失败！");
		Exam exam = null;
		if((exam = p.getExam()) == null) throw new Exception(String.format("产品［%s］未设置考试！", p.getName()));
		ExamSync examSync = new ExamSync();
		examSync.setCode(exam.getCode());
		examSync.setName(exam.getName());
		examSync.setAbbr(exam.getAbbr());
		if(p.getSubjects() != null && p.getSubjects().size() > 0){
			List<SubjectSync> subjectSyncs = new ArrayList<SubjectSync>();
			for(Subject subject : p.getSubjects()){
				if(subject == null) continue;
				SubjectSync subjectSync = new SubjectSync();
				subjectSync.setCode(String.format("%d", subject.getCode()));
				subjectSync.setName(subject.getName());
				subjectSyncs.add(subjectSync);
			}
			examSync.setSubjects(subjectSyncs);
		}
		//存入缓存
		if(this.cache != null && examSync != null){
			logger.debug("产品["+cacheKey+"]科目数据存入缓存...");
			this.cache.put(new Element(cacheKey, examSync));
		}
		return examSync;
	}
	//创建试卷缓存键名
	private static String createPapersCacheKey(AppClientSync req){
		return  "__syncPapers__" + req.getProductId() + req.getStartTime();
	}
	/*
	 * 同步试卷数据。
	 * @see com.examw.test.service.api.IDataSyncService#syncPapers(com.examw.test.model.api.AppClientSync)
	 */
	@Override
	public List<PaperSync> syncPapers(AppClientSync req) throws Exception {
		logger.debug("同步试卷数据...");
		Product p = this.validationSyncReq(req);
		//缓存键
		final String cacheKey = createPapersCacheKey(req);
		//检查缓存
		if(this.cache != null){
			Element element = this.cache.get(cacheKey);
			if(element != null){
				logger.debug("从缓存中加载试卷数据...");
				PaperSync [] papers = (PaperSync [])element.getObjectValue();
				if(papers != null && papers.length > 0){
					logger.debug("从缓存中反馈试卷数据...");
					return Arrays.asList(papers);
				}
			}
		}
		//试卷类型
		Integer[] paper_types = {PaperType.REAL.getValue(),PaperType.SIMU.getValue(),PaperType.FORECAS.getValue(),PaperType.PRACTICE.getValue()};
		Date startTime = null;
		//获取数据开始时间
		if(!StringUtils.isEmpty(req.getStartTime())){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startTime = dateFormat.parse(req.getStartTime());
		}
		List<PaperSync> list = new ArrayList<>();
		//查询数据
		List<PaperRelease> paperReleases = this.paperReleaseDao.loadReleases(paper_types, this.buildSubjectIds(p.getSubjects()), 
					p.getArea() == null ?  null : new String[]{p.getArea().getId()}, startTime, null, null);
		if(paperReleases != null && paperReleases.size() > 0){
			for(PaperRelease paperRelease : paperReleases){
				if(paperRelease == null || paperRelease.getPaper() == null) continue;
				PaperSync sync = new PaperSync();
				sync.setId(paperRelease.getPaper().getId());
				sync.setTitle(paperRelease.getTitle());
				sync.setType(paperRelease.getPaper().getType());
				sync.setTotal(paperRelease.getTotal());
				sync.setContent(paperRelease.getContent());
				sync.setCreateTime(paperRelease.getCreateTime());
				if(paperRelease.getPaper().getSubject() != null){
					sync.setSubjectCode(String.format("%d", paperRelease.getPaper().getSubject().getCode()));
				}
				list.add(sync);
			}
		}
		//缓存数据
		if(this.cache != null && list != null && list.size() > 0){
			logger.debug("将试卷数据加载到缓存中...");
			this.cache.put(new Element(cacheKey,  list.toArray(new PaperSync[0])));
		}
		 return list;
	}
	private String[] buildSubjectIds(Set<Subject> subjects){
		if(subjects == null || subjects.size() == 0) return null;
		List<String> list = new ArrayList<>();
		for(Subject s : subjects){
			if(s == null) continue;
			list.add(s.getId());
		}
		return list.toArray(new String[0]);
	}
	/*
	 * 同步试卷记录。
	 * @see com.examw.test.service.api.IDataSyncService#syncPaperRecords(com.examw.test.model.api.AppClientPush)
	 */
	@Override
	public List<String> syncPaperRecords(AppClientPush<PaperRecordSync> req)  throws Exception{
		if(logger.isDebugEnabled()) logger.debug("同步试卷记录...");
		if(req == null) throw new IllegalArgumentException("同步请求信息为空！");
		//验证客户端信息
		this.validationSyncReq(req.toAppClientSync());
		//
		if(req.getRecords() == null || req.getRecords().size() == 0) return null;
		List<String> failures = new ArrayList<>();
		for(PaperRecordSync record : req.getRecords()){
			if(record == null) continue;
			try {
					UserPaperRecordInfo data = new UserPaperRecordInfo();
					data.setId(record.getId());
					data.setUserId(req.getUserId());
					data.setPaperId(record.getPaperId());
					data.setProductId(req.getProductId());
					data.setTerminalCode(Integer.parseInt(req.getClientTypeCode()));
					data.setRightNum(record.getRights());
					data.setScore(record.getScore());
					data.setStatus(record.getStatus());
					data.setUsedTime((long)record.getUseTimes());
					data.setCreateTime(record.getCreateTime());
					data.setLastTime(record.getLastTime());
					this.userPaperRecordService.update(data);
			} catch (Exception e) {
				 if(logger.isDebugEnabled())logger.debug(String.format("同步数据异常：%1$s[%2$s]", e.getMessage(), record));
				 failures.add(record.getId());
			}
		}
		return failures;
	}
	/*
	 * 同步试题记录。
	 * @see com.examw.test.service.api.IDataSyncService#syncPaperItemRecords(com.examw.test.model.api.AppClientPush)
	 */
	@Override
	public List<String> syncPaperItemRecords(AppClientPush<PaperItemRecordSync> req)  throws Exception{
		if(logger.isDebugEnabled())logger.debug("同步试题记录...");
		if(req == null) throw new IllegalArgumentException("同步请求信息为空！");
		//验证客户端信息
		this.validationSyncReq(req.toAppClientSync());
		if(req.getRecords() == null || req.getRecords().size() == 0) return null;
		List<String> failures = new ArrayList<>();
		for(PaperItemRecordSync record : req.getRecords()){
			if(record == null) continue;
			try {
				UserItemRecordInfo data = new UserItemRecordInfo();
				data.setId(record.getId());
				data.setStructureId(record.getStructureId());
				data.setItemId(record.getItemId());
				data.setItemContent(record.getContent());
				data.setAnswer(record.getAnswer());
				data.setStatus(record.getStatus());
				data.setTerminalCode(Integer.parseInt(req.getClientTypeCode()));
				data.setUsedTime((long)record.getUseTimes());
				data.setScore(record.getScore());
				data.setCreateTime(record.getCreateTime());
				data.setLastTime(record.getLastTime());
				List<UserItemRecordInfo> list = new ArrayList<>();
				list.add(data);
				this.userItemRecordService.addItemRecord(record.getPaperRecordId(), list);
			} catch (Exception e) {
				if(logger.isDebugEnabled())logger.debug(String.format("同步数据异常：%1$s[%2$s]", e.getMessage(), record));
				failures.add(record.getId());
			}
		}
		return failures;
	}
	/*
	 * 同步收藏记录。
	 * @see com.examw.test.service.api.IDataSyncService#syncFavorites(com.examw.test.model.api.AppClientPush)
	 */
	@Override
	public List<String> syncFavorites(AppClientPush<FavoriteSync> req)  throws Exception{
		if(logger.isDebugEnabled()) logger.debug("同步收藏记录...");
		if(req == null) throw new IllegalArgumentException("同步请求信息为空！");
		//验证客户端信息
		this.validationSyncReq(req.toAppClientSync());
		if(req.getRecords() == null || req.getRecords().size() == 0) return null;
		List<String> failures = new ArrayList<>();
		for(FavoriteSync favorite : req.getRecords()){
			if(favorite == null) continue;
			try {
				if(favorite.getStatus() == 0){//删除数据
					this.userItemFavoriteService.delete(new String[]{favorite.getId()});
				}else {//更新数据
					UserItemFavoriteInfo data = new UserItemFavoriteInfo();
					data.setId(favorite.getId());
					data.setUserId(req.getUserId());
					data.setSubjectId(favorite.getSubjectId());
					data.setItemId(favorite.getItemId());
					data.setItemContent(favorite.getContent());
					data.setRemarks(favorite.getRemarks());
					data.setItemType(favorite.getItemType());
					data.setTerminalCode(Integer.parseInt(req.getClientTypeCode()));
					data.setCreateTime(favorite.getCreateTime());
					this.userItemFavoriteService.update(data);
				}
			} catch (Exception e) {
				if(logger.isDebugEnabled())logger.debug(String.format("同步数据异常：%1$s[%2$s]", e.getMessage(), favorite));
				failures.add(favorite.getId());
			}
		}
		return failures;
	}
	//验证同步请求的合法性。
	private Product validationSyncReq(AppClientSync req) throws Exception{
		if(logger.isDebugEnabled()) logger.debug("验证同步请求的合法性...");
		if(req == null) throw new IllegalArgumentException("同步请求为空！");
		if(req.isIgnoreCode()){//忽略注册码验证
			if(StringUtils.isEmpty(req.getProductId())) throw new Exception("未设置产品ID！");
			return this.productService.loadProduct(req.getProductId());
		}
		//验证注册码
		String reg_code =  this.registrationCodeService.cleanCodeFormat(req.getCode());
		if(StringUtils.isEmpty(reg_code)) throw new Exception("注册码为空！");
		if(this.registrationCodeService.validation(reg_code)){
			Registration reg = this.registrationCodeService.loadRegistration(reg_code);
			if(reg == null) throw new Exception(String.format("加载注册码对象失败! %s", reg_code));
			Product p = reg.getProduct();
			if(p == null) throw new Exception("加载产品信息失败！");
			if(!p.getId().equalsIgnoreCase(req.getProductId())){
				throw new Exception(String.format("注册码［%1$s］属于产品［%2$s］与App应用产品不符!", reg_code, p.getName()));
			}
			return p;
		}
		return null;
	}
}