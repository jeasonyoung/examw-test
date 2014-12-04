package com.examw.test.service.library.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.BasePaperInfo;
import com.examw.test.model.library.FrontPaperInfo;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.service.library.IFrontPaperService;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.PaperType;
import com.examw.test.service.records.IUserPaperRecordService;
import com.examw.test.support.PaperItemUtils;

/**
 * 试卷前端服务接口。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public class FrontPaperServiceImpl implements IFrontPaperService  {
	private static final Logger logger = Logger.getLogger(FrontPaperServiceImpl.class);
	private static final Integer[] default_paper_types = {PaperType.REAL.getValue(), 
																				 		PaperType.SIMU.getValue(), 
																				 		PaperType.FORECAS.getValue(), 
																				 		PaperType.PRACTICE.getValue()};
	private IPaperReleaseDao paperReleaseDao;
	private IProductDao productDao;
	private IPaperService paperService;
	private ObjectMapper mapper  = new ObjectMapper();
	private IUserPaperRecordService userPaperRecordService;
	/**
	 * 设置试卷发布数据接口。
	 * @param paperReleaseDao 
	 *	  试卷发布数据接口。
	 */
	public void setPaperReleaseDao(IPaperReleaseDao paperReleaseDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷发布数据接口...");
		this.paperReleaseDao = paperReleaseDao;
	}
	/**
	 * 设置产品数据接口。
	 * @param productDao 
	 *	  产品数据接口。
	 */
	public void setProductDao(IProductDao productDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品数据接口...");
		this.productDao = productDao;
	}
	/**
	 * 设置试卷服务接口。
	 * @param paperService 
	 *	  试卷服务接口。
	 */
	public void setPaperService(IPaperService paperService) {
		 if(logger.isDebugEnabled()) logger.debug("注入试卷服务接口...");
		 this.paperService = paperService;
	}
	/**
	 * 设置 用户试卷记录服务接口
	 * @param userPaperRecordService
	 * 用户试卷记录服务接口
	 */
	public void setUserPaperRecordService(
			IUserPaperRecordService userPaperRecordService) {
		this.userPaperRecordService = userPaperRecordService;
	}
	/*
	 * 加载科目下的试卷数量。
	 * @see com.examw.test.service.library.IFrontPaperService#loadPapersCount(java.lang.String[])
	 */
	@Override
	public Integer loadPapersCount(String[] subjetsId,String areaId) {
		if(logger.isDebugEnabled()) logger.debug("科目下的试卷数量...");
		return this.paperReleaseDao.loadPapersCount(default_paper_types,subjetsId,areaId);
	}
	/*
	 * 加载科目下试题数量。
	 * @see com.examw.test.service.library.IFrontPaperService#loadItemsCount(java.lang.String[])
	 */
	@Override
	public Integer loadItemsCount(String[] subjectsId,String areaId) {
		if(logger.isDebugEnabled()) logger.debug("加载科目下试题数量...");
		return this.paperReleaseDao.loadItemsCount(default_paper_types,subjectsId,areaId);
	}
	/*
	 * 加载科目下是否有真题。
	 * @see com.examw.test.service.library.IFrontPaperService#hasRealItem(java.lang.String[])
	 */
	@Override
	public boolean hasRealItem(String[] subjectsId) {
		if(logger.isDebugEnabled()) logger.debug("加载科目下是否有真题...");
		return this.paperReleaseDao.hasRealItem(subjectsId);
	}
	/*
	 * 加载产品下的试卷集合。
	 * @see com.examw.test.service.library.IFrontPaperService#loadProductPapers(java.lang.String)
	 */
	@Override
	public List<FrontPaperInfo> loadProductPapers(String productId) {
		 if(logger.isDebugEnabled()) logger.debug(String.format("加载产品［productId = %s］下的试卷集合...", productId));
		 Product product = this.productDao.load(Product.class, productId);
		 if(product == null) throw new RuntimeException(String.format("产品［productId = %s］不存在！", productId));
		 return this.changeModel(this.paperReleaseDao.loadReleases(default_paper_types, this.buildProductSubjectIds(product.getSubjects()), product.getArea()==null?null:new String[]{ product.getArea().getId() },
				 																							  null,null,null));
	}
	//构建产品科目ID数组。
	private String[] buildProductSubjectIds(Set<Subject> subjects){
		if(subjects == null || subjects.size() == 0) return null;
		List<String> list = new ArrayList<>();
		for(Subject subject : subjects){
			if(subject == null) continue;
			list.add(subject.getId());
		}
		return list.toArray(new String[0]);
	}
	//数据模型转换.
	private List<FrontPaperInfo> changeModel(List<PaperRelease> paperReleases){
		if(logger.isDebugEnabled()) logger.debug("数据模型集合转换  List<PaperRelease> => List<FrontPaperInfo> ...");
		List<FrontPaperInfo> list = new ArrayList<>();
		if(paperReleases != null && paperReleases.size() > 0){
			for(PaperRelease paperRelease : paperReleases){
				 if(paperRelease == null || paperRelease.getPaper() == null) continue;
				 FrontPaperInfo frontPaperInfo = new FrontPaperInfo();
				 BeanUtils.copyProperties((BasePaperInfo)this.paperService.conversion(paperRelease.getPaper()), frontPaperInfo);
				 frontPaperInfo.setId(paperRelease.getId());//重置试卷ID。
				 frontPaperInfo.setTotal(paperRelease.getTotal());
				 //增加属性 [Add by FW 2014.10.12]
				 frontPaperInfo.setUserTotal(this.userPaperRecordService.findUsersTotal(paperRelease.getId()));
				 frontPaperInfo.setMaxScore(this.userPaperRecordService.findMaxScore(paperRelease.getId())); 
				 list.add(frontPaperInfo);
			}
		}
		return list;
	}
	/*
	 * 加载试卷内容。
	 * @see com.examw.test.service.library.IFrontPaperService#loadPaperContent(java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperContent(String paperId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［paperId = %s］内容...", paperId));
		if(StringUtils.isEmpty(paperId)) return null;
		PaperRelease paperRelease = this.paperReleaseDao.load(PaperRelease.class, paperId);
		if(paperRelease == null) throw new RuntimeException(String.format("试卷［%s］不存在!", paperId));
		if(StringUtils.isEmpty(paperRelease.getContent())) throw new RuntimeException(String.format("试卷［%s］序列化内容丢失!", paperId));
		//处理日期格式  Add by FW 2014.10.09
		DeserializationConfig cfg = mapper.getDeserializationConfig();
		cfg = cfg.withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper = mapper.setDeserializationConfig(cfg);
		//处理日期格式  Add by FW 2014.10.09
		return this.mapper.readValue(paperRelease.getContent(), PaperPreview.class);
	}
	/*
	 * 加载试卷类型映射
	 * @see com.examw.test.service.library.IFrontPaperService#loadPaperType()
	 */
	@Override
	public Map<String, String> loadPaperType() {
		if(logger.isDebugEnabled()) logger.debug("加载试卷类型映射");
		return PaperItemUtils.loadPaperTypeValueMap(this.paperService);
	}
	/*
	 * 加载每日一练试卷集合。
	 * @see com.examw.test.service.library.IFrontPaperService#loadDailyPapers(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<FrontPaperInfo> loadDailyPapers(String productId,Integer page, Integer rows) {
		//if(logger.isDebugEnabled()) logger.debug(String.format("加载每日一练试卷集合［subjectId = %1$s］［areaId = %2$s］［page = %3$d  rows = %4$d］...", subjectId,areaId,page,rows));
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品［productId = %s］下的每日一练试卷集合...", productId));
		Product product = this.productDao.load(Product.class, productId);
		if(product == null) throw new RuntimeException(String.format("产品［productId = %s］不存在！", productId));
		//构造时间 查询一个星期的每日一练
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-7, 0, 0, 0);
		return this.changeModel(this.paperReleaseDao.loadReleases(new Integer[]{ PaperType.DAILY.getValue() },this.buildProductSubjectIds(product.getSubjects()), product.getArea()==null?null:new String[]{ product.getArea().getId() },
				  														calendar.getTime(),page, rows));
	}
	/*
	 * 加载今日一练剩余的个数
	 * @see com.examw.test.service.library.IFrontPaperService#loadResidueUserDailyPaperNumber(java.lang.String, java.lang.String)
	 */
	@Override
	public Long loadResidueUserDailyPaperNumber(String userId, String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品［productId = %s］下的每日一练试卷集合...", productId));
		Product product = this.productDao.load(Product.class, productId);
		if(product == null) throw new RuntimeException(String.format("产品［productId = %s］不存在！", productId));
		//构造时间 查询一个星期的每日一练
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		List<PaperRelease> list =(this.paperReleaseDao.loadReleases(new Integer[]{ PaperType.DAILY.getValue() },this.buildProductSubjectIds(product.getSubjects()), product.getArea()==null?null:new String[]{ product.getArea().getId() },
				  														calendar.getTime(),null, null));
		if(list == null || list.size() ==0) return 0L;
		Long practiced = this.userPaperRecordService.totalUserDailyPaperRecords(userId, productId);
		return list.size() - (practiced == null?0L:practiced);
	}
}