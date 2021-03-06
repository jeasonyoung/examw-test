package com.examw.test.controllers.syllabus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.syllabus.ISyllabusService;
import com.examw.test.service.syllabus.SyllabusStatus;
import com.examw.test.support.EnumMapUtils;
/**
 * 大纲控制器。
 * @author lq.
 * @since 2014-8-06.
 */
@Controller
@RequestMapping(value = "/syllabus/syllabus")
public class SyllabusController {
	private static final Logger logger = Logger.getLogger(PressController.class);
	//大纲服务。
	@Resource
	private ISyllabusService syllabusService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUSS_SYLLABUS + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUSS_SYLLABUS + ":" + Right.DELETE);
		
		return "syllabus/syllabus_list";
	}
	/**
	 * 获取编辑页面。
	 * @return
	 * 编辑页面。
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String examId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_exam_id", examId);
		
		Map<String, String> syllabusStatusMap = EnumMapUtils.createTreeMap();
		for(SyllabusStatus status : SyllabusStatus.values()){
			if(status == null) continue;
			syllabusStatusMap.put(String.format("%d", status.getValue()), this.syllabusService.loadStatusName(status.getValue()));
		}
		model.addAttribute("current_year", new SimpleDateFormat("yyyy").format(new Date()));
		model.addAttribute("SyllabusStatusMap", syllabusStatusMap);
		
		return "syllabus/syllabus_edit";
	}
	/**
	 * 加载考试大纲要点列表页面。
	 * @param syllabusId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value = {"/{syllabusId}/points_list"}, method = RequestMethod.GET)
	public String pointsList(@PathVariable String syllabusId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲要点［syllabusId = %s］列表页面...",syllabusId));
		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUSS_SYLLABUS + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUSS_SYLLABUS + ":" + Right.DELETE);
		
		model.addAttribute("current_syllabus_id", syllabusId);
		
		return "syllabus/syllabus_points_list";
	}
	/**
	 * 加载考试大纲要点列表页面。
	 * @param syllabusId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value = {"/{syllabusId}/points_items"}, method = RequestMethod.GET)
	public String pointsItemsPage(@PathVariable String syllabusId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲要点［syllabusId = %s］列表页面...",syllabusId));
		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUSS_SYLLABUS + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUSS_SYLLABUS + ":" + Right.DELETE);
		
		model.addAttribute("current_syllabus_id", syllabusId);
		
		return "syllabus/syllabus_link_item";
	}
	/**
	 * 加载考试大纲要点编辑页面。
	 * @param syllabusId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value = {"/{syllabusId}/points_edit"}, method = RequestMethod.GET)
	public String pointsEdit(@PathVariable String syllabusId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲要点［parent_syllabusId = %s］编辑页面...",syllabusId));
		
		model.addAttribute("current_parent_syllabus_id", syllabusId);
		
		return "syllabus/syllabus_points_edit";
	}
	/**
	 * 加载最大的排序号。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value="/order", method=RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(String syllabusId){
		if(logger.isDebugEnabled()) logger.debug("加载考试大纲最大排序号...");
		Integer max = this.syllabusService.loadMaxOrder(syllabusId);
		if(max == null) max = 0;
		return max + 1;
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SyllabusInfo> datagrid(SyllabusInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.syllabusService.datagrid(info);
	}
	/**
	 * 查询与试题的关联。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS + ":" + Right.VIEW})
	@RequestMapping(value="/{syllabusId}/items", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ItemInfo> items(@PathVariable String syllabusId){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		if(StringUtils.isEmpty(syllabusId)) return null;
		DataGrid<ItemInfo> result = new DataGrid<ItemInfo>();
		List<ItemInfo> list = this.syllabusService.loadSyllabusItems(syllabusId);
		result.setRows(list);
		result.setTotal((long) list.size());
		return result;
	}
	/**
	 * 
	 * @param syllabusId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value = {"/{syllabusId}/points_items_search"}, method = RequestMethod.GET)
	public String pointsItemsSearchPage(@PathVariable String syllabusId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载大纲关键字搜索［syllabusId = %s］列表页面...",syllabusId));
		model.addAttribute("current_syllabus_id", syllabusId);
		Syllabus data = this.syllabusService.loadSyllabus(syllabusId);
		if(data!=null)
		{
			model.addAttribute("subject_id", data.getSubject().getId());
		}else
			throw new RuntimeException("该大纲不存在");
		return "syllabus/points_items_search";
	}
	/**
	 * 加载考试大纲树数据。
	 * @param syllabusId
	 * 考试大纲ID。
	 * @return
	 */
	@RequestMapping(value = {"/{syllabusId}/tree"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> loadSyllabus(@PathVariable String syllabusId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲［syllabusId = %s］树数据...", syllabusId));
		List<TreeNode> nodes = new ArrayList<>();
		Syllabus root = this.syllabusService.loadSyllabus(syllabusId);
		if(root == null) return nodes;
		TreeNode e = this.createSyllabusNode(this.syllabusService.conversion(root));
		if(e != null) nodes.add(e);
		return nodes;
	}
	/**
	 * 加载考试大纲要点树数据。
	 * @param syllabusId
	 * 考试大纲ID。
	 * @return
	 */
	@RequestMapping(value = {"/tree"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> loadSyllabusChildren(String syllabusId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲［syllabusId = %s］要点树数据...", syllabusId));
		List<TreeNode> nodes = new ArrayList<>();
		if(StringUtils.isEmpty(syllabusId)) return nodes;
		 Syllabus root = this.syllabusService.loadSyllabus(syllabusId);
		 if(root == null || root.getChildren() == null) return nodes;
		 for(Syllabus child : root.getChildren()){
			 if(child == null) continue;
			 TreeNode e = this.createSyllabusNode(this.syllabusService.conversion(child));
			 if(e != null) nodes.add(e);
		 }
		return nodes;
	}
	//创建考试大纲树结构。
	private TreeNode createSyllabusNode(SyllabusInfo root){
		if(root == null) return null;
		TreeNode node = new TreeNode();
		node.setId(root.getId());
		node.setText(root.getTitle());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("pid", root.getPid());
		attributes.put("id", root.getId());
		attributes.put("title", root.getTitle());
		attributes.put("orderNo", root.getOrderNo());
		attributes.put("description", root.getDescription());
		node.setAttributes(attributes);
		if(root.getChildren() != null && root.getChildren().size() > 0){
			List<TreeNode> childrenNodes = new ArrayList<>();
			for(SyllabusInfo child : root.getChildren()){
				if(child == null) continue;
				TreeNode e = this.createSyllabusNode(child);
				if(e != null){
					childrenNodes.add(e);
				}
			}
			if(childrenNodes.size() > 0){
				node.setChildren(childrenNodes);
			}
		}else
		{
			node.setText(root.getTitle()+(StringUtils.isEmpty(root.getDescription())?"":"[已加]"));
		}
		return node;
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(SyllabusInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.syllabusService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新大纲数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.syllabusService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据大纲["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载科目下的考试大纲集合。
	 * @param subjectId
	 * @return
	 */
	@RequestMapping(value = {"/all/subjects/{subjectId}"}, method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<SyllabusInfo> loadAllSyllabuses(@PathVariable String subjectId){
		if(logger.isDebugEnabled()) logger.debug(String.format(" 加载科目［subjectId = %s］下的考试大纲集合...", subjectId));
		return this.syllabusService.loadAllSyllabuses(subjectId);
	}
	
	/**
	 * 更新题目关联
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value="/updateItemLink", method = RequestMethod.POST)
	@ResponseBody
	public Json updateItemLink(String syllabusId,String itemId){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			this.syllabusService.updateSyllabusItems(syllabusId, itemId.split("\\|"),false); 
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新大纲数据发生异常", e);
		}
		return result;
	}
	/**
	 * 更新题目关联
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value="/deleteItemLink", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteItemLink(String syllabusId,String itemId){
		if(logger.isDebugEnabled()) logger.debug("删除关联数据...");
		Json result = new Json();
		try {
			this.syllabusService.updateSyllabusItems(syllabusId, itemId.split("\\|"),true); 
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除关联数据发生异常", e);
		}
		return result;
	}
	/**
	 * 导入知识点数据
	 * @param syllabusId	大纲ID
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUSS_SYLLABUS+ ":" + Right.VIEW})
	@RequestMapping(value="/importContent/{syllabusId}", method = RequestMethod.POST)
	@ResponseBody
	public Json importSyllabusContent(@PathVariable String syllabusId){
		if(logger.isDebugEnabled()) logger.debug("导入知识点数据...");
		Json result = new Json();
		try {
			this.syllabusService.importBookContentIntoSyllabusPoint(syllabusId);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("导入知识点数据发生异常", e);
		}
		return result;
	}
}