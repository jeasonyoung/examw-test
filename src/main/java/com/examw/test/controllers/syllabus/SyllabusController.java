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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.test.domain.security.Right;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.syllabus.ISyllabusService;
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
		nodes.add(this.createSyllabusNode(this.syllabusService.conversion(this.syllabusService.loadSyllabus(syllabusId))));
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
		attributes.put("orderNo", root.getOrderNo());
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
}