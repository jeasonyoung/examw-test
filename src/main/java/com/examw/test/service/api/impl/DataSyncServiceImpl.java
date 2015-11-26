package com.examw.test.service.api.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.examw.utils.MD5Util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 数据同步服务接口实现类。
 * 
 * @author yangyong
 * @since 2015年2月27日
 */
public class DataSyncServiceImpl implements IDataSyncService {
	private final static Logger logger = Logger.getLogger(DataSyncServiceImpl.class);
	private Cache cache;
	private String zipTempDir;
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
	 * 设置Zip存储临时目录。
	 * @param zipTempDir 
	 *	  Zip存储临时目录。
	 */
	public void setZipTempDir(String zipTempDir) {
		this.zipTempDir = zipTempDir;
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
	
	//获取缓存数据
	private Object getCacheValue(String key){
		if(logger.isDebugEnabled()) logger.debug("获取缓存数据=>" +  key);
		if(this.cache != null && !StringUtils.isEmpty(key)){
			final Element e = this.cache.get(key);
			if(e != null){
				return e.getObjectValue();
			}
		}
		return null;
	}
	//设置缓存数据
	private void setCacheValue(String key, Serializable value){
		if(logger.isDebugEnabled()) logger.debug("设置缓存["+key+"]数据..=>" + value);
		if(this.cache != null && !StringUtils.isEmpty(key) && value != null){
			this.cache.put(new Element(key, value));
		}
	}
	
	//缓存键名
	private static final String CACHE_KEY_CATEGORIES = MD5Util.MD5( "__downloadCategories__");
	/*
	 * 下载考试类别数据。
	 * @see com.examw.test.service.api.IDataSyncService#downloadCategories()
	 */
	@Override
	public List<CategorySync> downloadCategories() throws Exception {
		logger.debug("下载考试类别数据...");
		//检查缓存
		final CategorySync[] arrays = (CategorySync[])this.getCacheValue(CACHE_KEY_CATEGORIES);
		if(arrays != null && arrays.length > 0){
			logger.debug("从缓存["+CACHE_KEY_CATEGORIES+"]中反馈考试类别数据...");
			return Arrays.asList(arrays);
		}
		//初始化目标数据
		final List<CategorySync> list  = new ArrayList<CategorySync>();
		//从数据库中加载数据..
		final List<Category> categories =  this.categoryDao.loadTopCategories();
		if(categories != null && categories.size() > 0){
			for(Category category : categories){
				if(category == null)continue;
				final CategorySync categorySync = new CategorySync();
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
		
		if(list != null && list.size() > 0){
			logger.debug("将考试类别数据加载到缓存中...");
			this.setCacheValue(CACHE_KEY_CATEGORIES, list.toArray(new CategorySync[0]));
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
		//校验产品
		Product p = this.validationSyncReq(req);
		if(p == null) throw new Exception("加载产品数据失败！");
		//创建缓存键
		final String cacheKey = MD5Util.MD5("syncExams_" + req.getProductId());
		//检查缓存
		ExamSync examSync = (ExamSync)this.getCacheValue(cacheKey);
		if(examSync != null){
			logger.debug("从缓存中加载产品考试科目数据...");
			return examSync;
		}
		Exam exam = null;
		if((exam = p.getExam()) == null) throw new Exception(String.format("产品［%s］未设置考试！", p.getName()));
		examSync = new ExamSync();
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
		if(examSync != null){
			logger.debug("产品["+cacheKey+"]科目数据存入缓存...");
			this.setCacheValue(cacheKey, examSync);
		}
		return examSync;
	}
	//创建试卷缓存键名
	private static String createPapersCacheKey(AppClientSync req){
		//return  MD5Util.MD5("__syncPapers__" + req.getProductId() + req.getStartTime());
		return createPapersCacheKey(req.getProductId(), req.getStartTime());
	}
	//创建试卷缓存键名
	private static String createPapersCacheKey(String productId, String startTime){
		return  MD5Util.MD5(StringUtils.trimAllWhitespace("__syncPapers__" + productId + (StringUtils.isEmpty(startTime) ? "" : startTime))); 
	}
	/*
	 * 同步试卷数据。
	 * @see com.examw.test.service.api.IDataSyncService#syncPapers(com.examw.test.model.api.AppClientSync)
	 */
	@Override
	public List<PaperSync> syncPapers(AppClientSync req) throws Exception {
		logger.debug("同步试卷数据...");
		final Product p = this.validationSyncReq(req);
		//缓存键
		final String cacheKey = createPapersCacheKey(req);
		if(logger.isDebugEnabled()) logger.debug("生成缓存键=>" + cacheKey);
		//检查缓存
		final PaperSync [] papers = (PaperSync[])this.getCacheValue(cacheKey);
		if(papers != null && papers.length > 0){
			logger.debug("从缓存中反馈试卷数据...");
			return Arrays.asList(papers);
		}
		//
		Date startTime = null;
		//获取数据开始时间
		if(!StringUtils.isEmpty(req.getStartTime())){
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startTime = dateFormat.parse(req.getStartTime());
		}
		final List<PaperSync> list =  this.buildProductPapers(p, startTime);
		//缓存数据
		if(list != null && list.size() > 0){
			logger.debug("将试卷数据加载到缓存键["+ cacheKey +"]中...");
			this.setCacheValue(cacheKey, list.toArray(new PaperSync[0]));
		}
		return list;
	}
	
	/*
	 * 下载zip格式试卷数据。
	 * @see com.examw.test.service.api.IDataSyncService#downloadZipPapers(com.examw.test.model.api.AppClientSync)
	 */
	@Override
	public byte[] downloadZipPapers(AppClientSync req) throws Exception {
		if(logger.isInfoEnabled()) logger.info("下载zip格式试卷数据...");
		final String zipCacheJSONKey = "zip_" + createPapersCacheKey(req) + "_json", zipCacheHashKey = zipCacheJSONKey + "_hash";
		//从缓存中读取文件名
		final String zipFileHash = (String)this.getCacheValue(zipCacheHashKey);
		try{
			//从临时缓存目录读取文件
			if(logger.isInfoEnabled()) logger.info("从临时缓存目录读取文件=>" + zipFileHash);
			if(!StringUtils.isEmpty(zipFileHash) && !StringUtils.isEmpty(this.zipTempDir) && new File(this.zipTempDir).exists()){
				final File zipFilePath = new File(this.zipTempDir, zipFileHash + ".zip");
				if(zipFilePath.exists()){
					if(logger.isInfoEnabled()) logger.info("从临时文件["+zipFilePath.getAbsolutePath()+"]中读取数据=>" + zipFileHash);
					 return FileUtils.readFileToByteArray(zipFilePath);
				}
			}
		}catch(Exception e){
			logger.error("从临时缓存目录读取文件["+zipFileHash+"]异常:" + e.getMessage(), e);
		}
		//从缓存中读取JSON串
		if(logger.isInfoEnabled()) logger.info("从缓存中读取JSON串=>" + zipCacheJSONKey);
		String json = (String)this.getCacheValue(zipCacheJSONKey);
		if(StringUtils.isEmpty(json)){
			//加载查询数据
			final List<PaperSync> papers = this.syncPapers(req);
			if(papers != null && papers.size() > 0){
				final ObjectMapper mapper = new ObjectMapper();
				 json = mapper.writeValueAsString(papers);
				 if(!StringUtils.isEmpty(json)){
					 if(logger.isInfoEnabled()) logger.info("将数据JSON处理,并缓存=>" + zipCacheJSONKey);
					 this.setCacheValue(zipCacheJSONKey, json);
				 }
			}
		}
		//检查数据/压缩处理
		if(logger.isInfoEnabled()) logger.info("JSON压缩处理...");
		if(!StringUtils.isEmpty(json)){
			byte[] bodys = null;
			//输出流
			ByteArrayOutputStream outputStream = null;
			ZipOutputStream zipOutputStream = null;
			try{
				//初始化输出流
				outputStream = new ByteArrayOutputStream();
				//初始化Zip输出流
				zipOutputStream = new ZipOutputStream(outputStream);
				//zip压缩
				this.zip(zipOutputStream, json.getBytes("UTF-8"), "data.json");
				//flush
				zipOutputStream.flush();
				//结果数据
				bodys = outputStream.toByteArray();
			}catch(Exception e){
				 logger.error("JSON压缩时发生异常:" + e.getMessage(), e);
			}finally{
				//关闭zip输出流
				if(zipOutputStream != null) zipOutputStream.close();
				//关闭输出流
				if(outputStream != null) outputStream.close();
			}
			try{
				 //临时文件处理
				 if(bodys != null && bodys.length > 0 &&  !StringUtils.isEmpty(this.zipTempDir) && new File(this.zipTempDir).exists()){
					 final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					 //计算文件Hash
					 final String zipHash = sdf.format(new Date()) + File.separator + MD5Util.MD5(json);
					 //生成临时文件
					 if(logger.isInfoEnabled()) logger.info("生成临时文件=>" + zipHash);
					 FileUtils.writeByteArrayToFile(new File(this.zipTempDir, zipHash + ".zip"), bodys);
					 //缓存文件键
					 this.setCacheValue(zipCacheHashKey, zipHash);
				 }
			}catch(Exception e){
				logger.error("写入临时目录缓存文件异常:" + e.getMessage(), e);
			}
			 //压缩结果处理
			 return bodys;
		}
		return null;
	}
	
	//zip压缩
	private void zip(final ZipOutputStream out, final byte[] data, final String fileName) throws Exception{
		//初始化zip输出缓冲流
		final BufferedOutputStream bufOutputStream = new BufferedOutputStream(out);
		//创建zip文件
		out.putNextEntry(new ZipEntry(fileName));
		//初始化输入缓冲流
		final BufferedInputStream bufInputStream = new BufferedInputStream(new ByteArrayInputStream(data));
		//
		final byte[] buf = new byte[1024];
		int count = 0;
		while((count = bufInputStream.read(buf, 0, buf.length)) != -1){
			bufOutputStream.write(buf, 0, count);
		}
		//关闭输入缓冲流
		bufInputStream.close();
		//关闭输出缓冲流
		bufOutputStream.close();
	}
	
	//构建产品试卷集合
	private List<PaperSync> buildProductPapers(Product p, Date startTime){
		if(p == null) return null;
		try{
			if(logger.isDebugEnabled()) logger.debug("构建产品["+p.getId()+","+p.getName()+"]试卷..");
			//试卷类型
			final Integer[] paper_types = { PaperType.REAL.getValue(),
															   PaperType.SIMU.getValue(),
															   PaperType.FORECAS.getValue(),
															   PaperType.PRACTICE.getValue() };
			//查询数据
			final List<PaperRelease> paperReleases = this.paperReleaseDao.loadReleases(paper_types,
																																	  		   this.buildSubjectIds(p.getSubjects()), 
																																	           p.getArea() == null ?  null : new String[]{ p.getArea().getId() }, 
																																	           startTime, null, null);
			if(paperReleases == null || paperReleases.size() == 0) return null;
			//初始化集合
			final List<PaperSync> list = new ArrayList<>();
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
			return list;
		}catch(Exception e){
			logger.error("发生异常:" + e.getMessage(), e);
		}
		return null;
	}
	//科目类型转换
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
	 * 自动缓存产品试卷。
	 * @see com.examw.test.service.api.IDataSyncService#updateAutoProductPapersCache()
	 */
	@Override
	public void updateAutoProductPapersCache(){
		try{
			if(logger.isDebugEnabled()) logger.debug("自动产品试卷缓存...");
			if(this.cache == null){
				if(logger.isDebugEnabled()) logger.debug("未配置缓存设置!");
				return;
			}
			//加载考试类别
			final List<CategorySync> categories = this.downloadCategories();
			if(categories == null || categories.size() == 0){
				if(logger.isDebugEnabled()) logger.debug("加载考试类别数据为空, 自动缓存试卷结束!");
				return;
			}
			//循环考试类别
			for(CategorySync category : categories){
				try{
					if(category == null) continue;
					if(logger.isDebugEnabled()) logger.debug("开始缓存考试类别["+ category.getId() +","+category.getName()+"]下考试...");
					final List<ExamSync> exams = category.getExams();
					if(exams == null || exams.size() == 0){
						if(logger.isDebugEnabled()) logger.debug("考试类型["+ category.getName() +"]下没有考试!");
						continue;
					}
					//循环考试
					for(ExamSync exam : exams){
						try{
							if(exam == null) continue;
							if(logger.isDebugEnabled()) logger.debug("开始缓存考试["+ exam.getId() +","+exam.getName()+"]下产品...");
							final List<ProductSync> products =  exam.getProducts();
							if(products == null || products.size() == 0){
								if(logger.isDebugEnabled()) logger.debug("考试["+ exam.getId() +","+exam.getName()+"]下没有产品!");
								continue;
							}
							//循环产品
							for(ProductSync product : products){
								try{
									if(product == null) continue;
									if(logger.isDebugEnabled()) logger.debug("开始自动缓存产品["+product.getId()+","+ product.getName() +"]下试卷集合");
									//加载产品数据
									final Product p = this.productService.loadProduct(product.getId());
									if(p == null){
										if(logger.isDebugEnabled()) logger.debug("产品["+product.getId()+"]不存在!");
										continue;
									}
									//生成缓存键
									final String cacheKey = createPapersCacheKey(product.getId(), null);
									if(logger.isDebugEnabled()) logger.debug("自动缓存生成缓存键=>" + cacheKey);
									if(StringUtils.isEmpty(cacheKey)) continue;
									//查询数据
									final List<PaperSync> list =  this.buildProductPapers(p, null);
									if(list == null || list.size() == 0){
										if(logger.isDebugEnabled()) logger.debug("产品["+p.getId()+","+p.getName()+"]下没有试卷集合...");
										continue;
									}
									//开始缓存
									logger.debug("自动将试卷数据加载到缓存键["+ cacheKey +"]中...");
									this.setCacheValue(cacheKey, list.toArray(new PaperSync[0]));
								}catch(Exception e){
									logger.error("自动缓存产品异常:" + e.getMessage(), e);
								}
							}
						}catch(Exception e){
							logger.error("自动缓存考试异常:" + e.getMessage(), e);
						}
					}
				}catch(Exception e){
					logger.error("自动缓存考试类别["+ category.getId()+"," + category.getName()+ "]异常:" + e.getMessage(), e);
				}
			}
		}catch(Exception e){
			logger.error("自动缓存异常:" + e.getMessage(), e);
		}
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
		final String reg_code =  this.registrationCodeService.cleanCodeFormat(req.getCode());
		if(StringUtils.isEmpty(reg_code)) throw new Exception("注册码为空！");
		if(this.registrationCodeService.validation(reg_code)){
			final Registration reg = this.registrationCodeService.loadRegistration(reg_code);
			if(reg == null) throw new Exception(String.format("加载注册码对象失败! %s", reg_code));
			final Product p = reg.getProduct();
			if(p == null) throw new Exception("加载产品信息失败！");
			if(!p.getId().equalsIgnoreCase(req.getProductId())){
				throw new Exception(String.format("注册码［%1$s］属于产品［%2$s］与App应用产品不符!", reg_code, p.getName()));
			}
			return p;
		}
		return null;
	}
}