package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.service.Status;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.products.ProductInfo;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.publish.impl.ExamTemplateProcess.ProductListViewData;

/**
 * 试卷详细模版处理。
 * 
 * @author yangyong
 * @since 2014年12月31日
 */
public class PaperDetailTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(PaperDetailTemplateProcess.class);
	private IProductDao productDao;
	private IPaperService paperService;
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
	/*
	 * 模版处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#addTemplateProcess(java.util.Date)
	 */
	@Override
	protected int addTemplateProcess(Date startTime) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("试卷详细模版处理...");
		List<PaperRelease> paperReleases = this.paperReleaseDao.findPaperReleases(new PaperInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() { return "createTime";}
			@Override
			public String getOrder() { return "desc";}
		});
		if(paperReleases == null || paperReleases.size() == 0) return 0;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("updateTime", startTime);
		Paper paper = null;
		Subject subject = null;
		Exam exam = null;
		int total = 0;
		for(PaperRelease release : paperReleases){
			if(release == null || (paper = release.getPaper()) == null) continue;
			subject = paper.getSubject();
			parameters.put("subjectName", subject == null ? null : subject.getName());
			parameters.put("subjectCode", subject == null ? null : subject.getCode());
			exam = subject.getExam();
			parameters.put("examName", exam.getName());
			String abbr = exam.getAbbr();
			//最新试卷
			parameters.put("newsPapers", this.loadNewsPapers(exam.getId()));
			//最热试卷
			parameters.put("hotsPapers", this.loadHotsPapers(exam.getId()));
			
			parameters.put("abbr", abbr);
			parameters.put("paperId", paper.getId());
			parameters.put("paperName", paper.getName());
			parameters.put("price", paper.getPrice());
			parameters.put("year", paper.getYear());
			parameters.put("typeName", this.paperService.loadTypeName(paper.getType()));
			parameters.put("score", paper.getScore());
			parameters.put("total", release.getTotal());
			parameters.put("time", paper.getTime());
			
			List<ProductListViewData> products = null;
			if(subject != null && subject.getExam() != null){
				final String examId = subject.getExam().getId();
				List<Product> listProducts = this.productDao.findProducts(new ProductInfo(){
					private static final long serialVersionUID = 1L;
					@Override
					public String getExamId() { return examId;  }
					@Override
					public Integer getStatus() { return Status.ENABLED.getValue();}
				});
				if(listProducts != null && listProducts.size() > 0){
					products = new ArrayList<>();
					for(Product p : listProducts){
						if(p == null) continue;
						products.add(new ProductListViewData(p.getId(), p.getName(), p.getItemTotal(), p.getPrice(), p.getDiscount()));
					}
				}
			}
			parameters.put("products", products); 
			this.updateStaticPage(String.format("%1$s-%2$d-%3$s", abbr, subject.getCode(), paper.getId()), 
					String.format("/%1$s/%2$d/%3$s.html", abbr, subject.getCode(), paper.getId()), this.createStaticPageContent(parameters)); 
			total += 1;
		}
		return total;
	}
}