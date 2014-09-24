package com.examw.test.controllers.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.domain.records.ItemRecord;
import com.examw.test.domain.records.Note;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.front.CategoryFrontInfo;
import com.examw.test.model.front.PaperFrontInfo;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.model.products.ProductInfo;
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.products.IProductService;
import com.examw.test.service.records.ICollectionService;
import com.examw.test.service.records.IItemRecordService;
import com.examw.test.service.records.INoteService;
import com.examw.test.service.settings.ICategoryService;
import com.examw.test.service.settings.ISubjectService;
import com.examw.test.service.syllabus.IKnowledgeService;
import com.examw.test.service.syllabus.ISyllabusService;

/**
 * 考试分类-考试控制器
 * @author fengwei.
 * @since 2014年9月5日 上午10:41:23.
 */
@Controller
@RequestMapping(value = { "/api/data" })
public class FrontDataController {
	private static final Logger logger = Logger.getLogger(FrontDataController.class);
	@Resource
	private ICategoryService categoryService;
	@Resource
	private IProductService productService;
	@Resource
	private ISubjectService subjectService;
	@Resource
	private ISyllabusService syllabusService;
	@Resource
	private IPaperService paperService;
	@Resource
	private IItemService itemService;
	@Resource
	private IKnowledgeService knowledgeService;
	@Resource
	private IItemRecordService itemRecordService;
	@Resource
	private INoteService noteService;
	@Resource
	private ICollectionService collectionService;
	/**
	 * 加载所有考试分类和其下的所有考试
	 * @param username
	 * @param token
	 * @return
	 */
	@RequestMapping(value = {"/category-exams"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<CategoryFrontInfo> loadAllCategoryExams(){
		if(logger.isDebugEnabled()) logger.debug("考试分类-考试数据");
		return this.categoryService.loadAllCategoryAndExams();
	}
	/**
	 * 加载考试下所有的产品
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = {"/products"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<ProductInfo> loadProducts(String examId){
		if(logger.isDebugEnabled()) logger.debug("考试下产品数据");
		if(StringUtils.isEmpty(examId)) return null;
		return this.productService.loadProducts(examId);
	}
	/**
	 * 单个产品信息
	 */
	@RequestMapping(value = {"/product"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ProductInfo loadProduct(String id){
		if(logger.isDebugEnabled()) logger.debug("选中产品数据");
		if(StringUtils.isEmpty(id)) return null;
		ProductInfo info = this.productService.loadProduct(id);
		return info;
	}
	/**
	 * 章节练习
	 */
	@RequestMapping(value = {"/chapter"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> loadChapters(final String productId,String subjectId){
		if(logger.isDebugEnabled()) logger.debug("章节数据");
		if(StringUtils.isEmpty(productId)) return null;
		Map<String,Object> map = new HashMap<String,Object>();
		List<SubjectInfo> subjects = this.subjectService.changeModel(this.productService.loadSubjectList(productId));
		map.put("SUBJECTLIST",subjects);
		if(StringUtils.isEmpty(subjectId)){
			if(subjects!=null && subjects.size()>0)
				subjectId = subjects.get(0).getId();
		}
		map.put("CHAPTERLIST", this.syllabusService.loadSyllabuss(subjectId, null));
		map.put("CURRENT_SUBJECT_ID", subjectId);
		return map;
	}
	/**
	 * 章节详情
	 * @param pid
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"/chapter/detail"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> loadChapterDetail(final String pid,String id){
		if(logger.isDebugEnabled()) logger.debug("章节详情数据");
		if(StringUtils.isEmpty(pid)) return null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("CHAPTER",(SyllabusInfo) this.syllabusService.loadSysSyllabusInfo(pid));
		map.put("KNOWLEDGE", this.knowledgeService.loadKnowledge(id));
		return map;
	}
	/**
	 * 加载产品下的试卷列表
	 * @param productId
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"/papers"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> loadPapers(String productId,PaperInfo info,String userId){
		if(logger.isDebugEnabled()) logger.debug("试卷列表数据");
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(productId)) return map;
		List<Subject> list = this.productService.loadSubjectList(productId);
		
		//科目集合
		map.put("SUBJECTLIST", this.subjectService.changeModel(list));
		//地区集合
		map.put("AREALIST", this.productService.loadAreaList(productId));
		//试卷类型映射
		map.put("PAPERTYPE", this.productService.getPaperTypeMap());
		//试卷列表
//		DataGrid<PaperInfo> dg = this.paperService.datagrid(info);
//		map.put("PAPERLIST", dg.getRows());
//		//总条数
//		map.put("TOTAL",dg.getTotal());
		map.put("PAPERLIST", this.paperService.loadPaperFrontInfo(info, userId));
		map.put("TOTAL",this.paperService.totalPaperFrontInfo(info));
		return map;
	}
	/**
	 * 加载试卷基本信息数据
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = {"/paper"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public PaperPreview loadPaperInfo(String paperId){
		if(logger.isDebugEnabled()) logger.debug("试卷基本信息数据");
		if(StringUtils.isEmpty(paperId)) return null;
		return this.paperService.loadPaperInfo(paperId);
	}
	/**
	 * 加载试卷的详细信息[包含题目数据]
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = {"/paper/do"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public PaperPreview loadPaperDetail(String paperId,String userId){
		if(logger.isDebugEnabled()) logger.debug("试卷基本信息数据[包含题目]");
		if(StringUtils.isEmpty(paperId)||StringUtils.isEmpty(userId)) return null;
		return this.paperService.loadPaperPreviewAndAddRecord(paperId,userId);
	}
	
	@RequestMapping(value = {"/paper/submit"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Json submitPaper(String paperId,String userId,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("试卷基本信息数据[包含题目]");
		if(StringUtils.isEmpty(paperId)||StringUtils.isEmpty(userId)) return null;
		String model = request.getParameter("model");
		String limitTime = request.getParameter("limitTime");
		String chooseAnswers = request.getParameter("chooseAnswers");
		String textAnswers = request.getParameter("textAnswers");
		logger.debug(chooseAnswers);
		return this.paperService.submitPaper(Integer.valueOf(limitTime), chooseAnswers, textAnswers,Integer.valueOf(model), paperId,userId);
	}
	
	@RequestMapping(value = {"/paper/record"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public PaperFrontInfo paperRecord(String paperId,String userId,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("试卷解析详情");
		if(StringUtils.isEmpty(paperId)||StringUtils.isEmpty(userId)) return null;
		return this.paperService.loadPaperRecordDetail(paperId, userId);
	}
	/**
	 * 查询试题笔记
	 * @param structureItemId
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/item/notes"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> findNotes(String structureItemId,String userId,HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("查询试题笔记...");
		if(StringUtils.isEmpty(structureItemId)) return null;
		Map<String,Object> map = new HashMap<String,Object>();
		Note info = new Note();
		info.setStructureItemId(structureItemId);
		info.setUserId(userId);
		Long total = this.noteService.total(info);
		map.put("total", total);
		if(total.equals(0)) return map;
		map.put("rows", this.noteService.findNotes(info));
		return map;
	}
	@RequestMapping(value = {"/item/addnote"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Json addNotes(Note data){
		if(logger.isDebugEnabled()) logger.debug("查询试题笔记...");
		if(data==null || StringUtils.isEmpty(data.getStructureItemId())) return null;
		Json json = new Json();
		json.setSuccess(this.noteService.insertNote(data));
		if(json.isSuccess()){
			json.setMsg("添加成功");
			json.setData(data);
		}
		return json;
	}
	/**
	 * 收藏或取消收藏
	 * @param structureItemId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = {"/item/collection"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Json collection(String structureItemId,String itemId,String userId){
		if(logger.isDebugEnabled()) logger.debug("查询试题笔记...");
		if(StringUtils.isEmpty(structureItemId)||StringUtils.isEmpty(userId)) return null;
		return this.collectionService.collectOrCancel(structureItemId,itemId,userId);
	}
	/**
	 *
	 */
	@RequestMapping(value = {"/record/save"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Json saveItemRecord(@RequestBody ItemRecord info){
		if(logger.isDebugEnabled()) logger.debug("试卷基本信息数据[包含题目]");
		if(StringUtils.isEmpty(info.getStructureItemId())||StringUtils.isEmpty(info.getUserId())) return null;
		Json result = new Json();
		result.setSuccess(this.itemRecordService.insertRecord(info));
		if(result.isSuccess())
			result.setMsg("提交成功");
		else
			result.setMsg("提交失败");
		return result;
	}
}
