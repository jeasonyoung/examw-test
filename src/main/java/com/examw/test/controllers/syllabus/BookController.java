package com.examw.test.controllers.syllabus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
 
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.test.domain.security.Right; 
import com.examw.test.model.syllabus.BookChapterInfo;
import com.examw.test.model.syllabus.BookInfo;
import com.examw.test.service.syllabus.BookStatus;
import com.examw.test.service.syllabus.IBookChapterService;
import com.examw.test.service.syllabus.IBookService;
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
	public String edit(String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_exam_id", examId);
		
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
	@RequestMapping(value="/{bookId}/chapters/list", method = RequestMethod.GET)
	public String loadChapterList(@PathVariable String bookId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载教材［bookId = %s］章节管理列表页面...", bookId));
		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUS_BOOK + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUS_BOOK + ":" + Right.DELETE);
		
		model.addAttribute("current_book_id", bookId);
		return "syllabus/book_chapters_list";
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
			info.setBookId(StringUtils.isEmpty(info.getPid()) ?  bookId : null);
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
	
//	/**
//	 * 获取知识点编辑页面。
//	 * @return
//	 * 编辑页面。
//	 */
//	@RequiresPermissions({ModuleConstant.SYLLABUS_TEXTBOOK+ ":" + Right.VIEW})
//	@RequestMapping(value="/syll/edit/{bookId}", method = RequestMethod.GET)
//	public String editKnow(@PathVariable String bookId,Model model){
//		if(logger.isDebugEnabled()) logger.debug("加载知识点编辑页面...");
//		model.addAttribute("CURRENT_BOOK_ID", bookId);
//		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUS_TEXTBOOK + ":" + Right.UPDATE);
//		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUS_TEXTBOOK + ":" + Right.DELETE);
//		return "syllabus/know_edit";
//	}
//	/**
//	 * 教材下知识点数据列表。
//	 * @param info
//	 * 查询知识点。
//	 * @return
//	 * 查询结果。
//	 */
//	@RequiresPermissions({ModuleConstant.SYLLABUS_TEXTBOOK+ ":" + Right.VIEW})
//	@RequestMapping(value="/{bookId}/datagrid", method = RequestMethod.POST)
//	@ResponseBody
//	public DataGrid<ChapterKnowledgeInfo> dgStructureItems(@PathVariable String bookId, ChapterKnowledgeInfo info){
//		if(logger.isDebugEnabled()) logger.debug("加载教材下的［"+bookId+"］知识点数据列表...");
//		if(info == null)info = new ChapterKnowledgeInfo();
//		info.setBookId(bookId);
//		return this.knowledgeService.datagrid(info);
//	}
//	/**
//	 * 获取添加知识点页面。
//	 * @return
//	 * 添加页面。
//	 */
//	@RequiresPermissions({ModuleConstant.SYLLABUS_TEXTBOOK+ ":" + Right.VIEW})
//	@RequestMapping(value="/add", method = RequestMethod.GET)
//	public String add(String bookId,String syllId,Model model){
//		if(logger.isDebugEnabled()) logger.debug("加载添加知识点页面...");
//		model.addAttribute("CURRENT_BOOK_ID", bookId);
//		model.addAttribute("CURRENT_SYLL_ID", syllId);
//		return "syllabus/know_add";
//	}


//	/**
//	 * 更新知识点数据。
//	 * @param info
//	 * 更新源数据。
//	 * @return
//	 * 更新后数据。
//	 */
//	@RequiresPermissions({ModuleConstant.SYLLABUS_TEXTBOOK+ ":" + Right.VIEW})
//	@RequestMapping(value="/{bookId}/update", method = RequestMethod.POST)
//	@ResponseBody
//	public Json updateStructure(@PathVariable String bookId, ChapterKnowledgeInfo info){
//		if(logger.isDebugEnabled()) logger.debug("更新知识点数据...");
//		Json result = new Json();
//		try {
//			info.setBookId(bookId);
//			result.setData(this.knowledgeService.update(info));
//			result.setSuccess(true);
//		} catch (Exception e) {
//			result.setSuccess(false);
//			result.setMsg(e.getMessage());
//			logger.error("更新知识点数据发生异常", e);
//		}
//		return result;
//	}


}