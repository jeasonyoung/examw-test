package com.examw.test.controllers.syllabus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.test.domain.security.Right;
import com.examw.test.domain.syllabus.BookChapter;
import com.examw.test.domain.syllabus.ChapterKnowledge;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.BookChapterInfo;
import com.examw.test.model.syllabus.BookInfo;
import com.examw.test.model.syllabus.ChapterKnowledgeInfo;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.syllabus.BookStatus;
import com.examw.test.service.syllabus.IBookChapterService;
import com.examw.test.service.syllabus.IBookService;
import com.examw.test.service.syllabus.IChapterKnowledgeService;
import com.examw.test.service.syllabus.ISyllabusService;
import com.examw.test.support.PaperItemUtils;
/**
 * 教材控制器。
 * @author lq.
 * @since 2014-8-06.
 */
@Controller
@RequestMapping(value = "/syllabus/book")
public class BookController {
	private static final Logger logger = Logger.getLogger(BookController.class);
	//教材服务接口。
	@Resource
	private IBookService bookService;
	//教材章节服务接口。
	@Resource
	private IBookChapterService bookChapterService;
	//章节知识点服务接口。
	@Resource
	private IChapterKnowledgeService chapterKnowledgeService;
	@Resource
	private ISyllabusService syllabusService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK+ ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUS_BOOK + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUS_BOOK + ":" + Right.DELETE);
		return "syllabus/book_list";
	}
	/**
	 * 加载排序号。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value="/order", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(){
		Integer order = this.bookService.loadMaxOrder();
		if(order ==  null) order = 0;
		return order + 1;
	}
	/**
	 * 获取编辑页面。
	 * @return
	 * 编辑页面。
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK+ ":" + Right.VIEW})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String examId,String subjectId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_exam_id", examId);
		model.addAttribute("current_subject_id", subjectId);
		
		Map<String, String> bookStatusMap = PaperItemUtils.createTreeMap();
		for(BookStatus status : BookStatus.values()){
			if(status ==  null) continue;
			bookStatusMap.put(String.format("%d", status.getValue()), this.bookService.loadStatusName(status.getValue()));
		}
		model.addAttribute("BookStatusMaps", bookStatusMap);
		
		return "syllabus/book_edit";
	}
	/**
	 * 查询教材数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<BookInfo> datagrid(BookInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.bookService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK+ ":" + Right.VIEW})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(BookInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.bookService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新教材数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.bookService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据教材["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载章节管理列表页面。
	 * @param bookId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value="/{syllabusId}/{bookId}/chapters/list", method = RequestMethod.GET)
	public String loadChapterList(@PathVariable String syllabusId,@PathVariable String bookId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教材［bookId = %s］章节管理列表页面...", bookId));
		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUS_BOOK + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUS_BOOK + ":" + Right.DELETE);
		model.addAttribute("current_syllabus_id", syllabusId);
		model.addAttribute("current_book_id", bookId);
		return "syllabus/book_chapters_list";
	}
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value = {"/{syllabusId}/{bookId}/syllabus/tree"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> loadSyllabus(@PathVariable String syllabusId,@PathVariable String bookId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲［syllabusId = %s］树数据...", syllabusId));
		List<TreeNode> nodes = new ArrayList<>();
		Syllabus root = this.syllabusService.loadSyllabus(syllabusId);
		if(root == null) return nodes;
		TreeNode e = this.createSyllabusNode(this.syllabusService.conversion(root),bookId);
		if(e != null) nodes.add(e);
		return nodes;
	}
	//创建考试大纲树结构。
	private TreeNode createSyllabusNode(SyllabusInfo root,String bookId){
		if(root == null) return null;
		TreeNode node = new TreeNode();
		node.setId(root.getId());
		BookChapter chapter = this.bookChapterService.loadChapters(bookId,root.getId());
		if(chapter!=null && chapter.getKnowledges()!=null && chapter.getKnowledges().size()>0)
			node.setText(root.getTitle()+" [已加]");
		else
			node.setText(root.getTitle());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("pid", root.getPid());
		attributes.put("id", root.getId());
		attributes.put("title", root.getTitle());
		attributes.put("orderNo", root.getOrderNo());
		node.setAttributes(attributes);
		if(root.getChildren() != null && root.getChildren().size() > 0){
			List<TreeNode> childrenNodes = new ArrayList<>();
			for(SyllabusInfo child : root.getChildren()){
				if(child == null) continue;
				TreeNode e = this.createSyllabusNode(child,bookId);
				if(e != null){
					childrenNodes.add(e);
				}
			}
			if(childrenNodes.size() > 0){
				node.setChildren(childrenNodes);
			}
		}
		return node;
	}
	/**
	 * 加载章节管理编辑页面。
	 * @param bookId
	 * @param parentChapterId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value="/{bookId}/chapters/edit", method = RequestMethod.GET)
	public String loadChapterEdit(@PathVariable String bookId,String parentChapterId, Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教材［bookId = %s］章节管理列表页面...", bookId));
		model.addAttribute("current_book_id", bookId);
		model.addAttribute("current_parent_chapter_id", parentChapterId);
		return "syllabus/book_chapters_edit"; 
	}
	/**
	 * 加载章节排序号。
	 * @param pid
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value="/chapters/order", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadChapterMaxOrder(String pid){
		Integer order = this.bookChapterService.loadMaxOrder(pid);
		if(order == null) order = 0;
		return order + 1;
	}
	/**
	 * 加载教材章节树结构数据集合。
	 * @param bookId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value="/{bookId}/chapters", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> loadChaptersData(@PathVariable String bookId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教材［bookId = %s］章节树结构数据集合...", bookId));
		List<TreeNode> list = new ArrayList<>();
		List<BookChapterInfo> chapters = this.bookChapterService.loadChapters(bookId);
		if(chapters != null && chapters.size() > 0){
			for(BookChapterInfo chapter : chapters){
				TreeNode node = this.createChapterNode(chapter);
				if(node != null) list.add(node);
			}
		}
		return list;
	}
	private TreeNode createChapterNode(BookChapterInfo chapter){
		if(chapter == null) return null;
		TreeNode node = new TreeNode();
		node.setId(chapter.getId());
		node.setText(chapter.getTitle());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("pid", chapter.getPid());
		attributes.put("id", chapter.getId());
		attributes.put("title", chapter.getTitle());
		attributes.put("content", chapter.getContent());
		attributes.put("orderNo", chapter.getOrderNo());
		node.setAttributes(attributes);
		if(chapter.getChildren() != null && chapter.getChildren().size() > 0){
			List<TreeNode> children = new ArrayList<>();
			for(BookChapterInfo info : chapter.getChildren()){
				if(info == null) continue;
				TreeNode e = this.createChapterNode(info);
				if(e != null) children.add(e);
			}
			if(children.size() > 0) node.setChildren(children);
		}
		return node;
	}
	/**
	 * 更新教材章节数据。
	 * @param bookId
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.UPDATE})
	@RequestMapping(value="/{bookId}/chapters/update", method = RequestMethod.POST)
	@ResponseBody
	public Json updateChapters(@PathVariable String bookId,BookChapterInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("更新教材［bookId = %s］章节数据...", bookId));
		Json result = new Json();
		try {
			info.setBookId(bookId);
			this.bookChapterService.update(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新教材章节数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据教材章节。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.DELETE})
	@RequestMapping(value="/chapters/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteChapters(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.bookChapterService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据教材章节["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载教材章节下的知识点集合。
	 * @param bookId
	 * 所属教材。
	 * @param info
	 * 查询条件。
	 * @return
	 * 知识点集合。
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value = {"/{bookId}/knowledges"}, method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ChapterKnowledgeInfo> loadKnowledges(@PathVariable String bookId, ChapterKnowledgeInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教材［bookId ＝ %s］章节下的知识点集合...", bookId));
		info.setBookId(bookId);
		return this.chapterKnowledgeService.datagrid(info);
	}
	/**
	 * 加载知识点编辑页面。
	 * @param bookId
	 * 教材ID。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value = {"/{bookId}/knowledge/edit"}, method = RequestMethod.GET)
	public String loadKnowledgeEdit(@PathVariable String bookId,String syllabusId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教材［bookId = %s］章节知识点编辑页面", bookId));
		model.addAttribute("current_book_id", bookId);
		model.addAttribute("current_syllabus_id", syllabusId);
		BookInfo info = this.bookService.loadBook(bookId);
		if(info == null) throw new RuntimeException(String.format("教材［bookId ＝%s］不存在！", bookId));
		BookChapter chapter = this.bookChapterService.createChapters(bookId,syllabusId);
		if(chapter!=null){
			model.addAttribute("current_chapter_id", chapter.getId());
			Set<ChapterKnowledge> knowledges = chapter.getKnowledges();
			if(knowledges!=null && knowledges.size()>0)
			{
				model.addAttribute("KNOWLEDGE",knowledges.iterator().next());
			}
		}
		model.addAttribute("current_subject_id", info.getSubjectId());
		return "syllabus/book_knowledges_edit"; 
	}
	/**
	 * 加载章节下知识点排序号。
	 * @param chapterId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.VIEW})
	@RequestMapping(value = {"/knowledges/order"}, method = RequestMethod.GET)
	@ResponseBody
	public Integer loadKnowledgeMaxCode(String chapterId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载章节［chapterId = %s］下知识点排序号...", chapterId));
		Integer order = this.chapterKnowledgeService.loadMaxCode(chapterId);
		if(order == null) order = 0;
		return order + 1;
	}
	/**
	 * 更新知识点数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.UPDATE})
	@RequestMapping(value = {"/knowledge/update"}, method = RequestMethod.POST)
	@ResponseBody
	public Json updateKnowledge(ChapterKnowledgeInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新知识点数据...");
		Json result = new Json();
		try {
			result.setData(this.chapterKnowledgeService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新教材章节数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除知识点数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_BOOK + ":" + Right.DELETE})
	@RequestMapping(value = {"/knowledges/delete"}, method = RequestMethod.POST)
	@ResponseBody
	public Json deleteKnowledges(String id){
		if(logger.isDebugEnabled()) logger.debug("删除知识点数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.chapterKnowledgeService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据教材知识点["+id+"]时发生异常:", e);
		}
		return result;
	}
}