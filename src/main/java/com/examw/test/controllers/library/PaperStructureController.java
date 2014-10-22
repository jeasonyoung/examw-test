package com.examw.test.controllers.library;
 
import java.util.List; 

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperStructureService;
import com.examw.test.support.PaperItemUtils;
/**
 * 试卷结构控制器。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
@Controller
@RequestMapping(value = "/library/paper/structure")
public class PaperStructureController {
	private static final Logger logger = Logger.getLogger(PaperStructureController.class);
	//注入试卷结构服务。
	@Resource
	private IPaperStructureService paperStructureService;
	//注入试题服务。
	@Resource
	private IItemService itemService;
	/**
	 * 加载试卷结构树数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 */
	@RequestMapping(value="/all/{paperId}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<StructureInfo> loadStructures(@PathVariable String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［paperId = %s］结构数据...", paperId));
		return this.paperStructureService.loadStructures(paperId);
	}
	/**
	 * 加载试卷结构列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/list/{paperId}", method = RequestMethod.GET)
	public String structureList(@PathVariable String paperId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［paperId = %s］结构列表页面...", paperId));

		model.addAttribute("current_paper_id", paperId);
		
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_PAPER + ":" + Right.DELETE);
		
		return "library/paper_structure_list";
	}
	/**
	 * 加载试卷结构排序号。
	 * @param paperId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/order/{paperId}", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(@PathVariable String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［paperId = %s］结构最大排序号...", paperId));
		Integer order = this.paperStructureService.loadMaxOrder(paperId);
		if(order == null) order = 0;
		return order + 1;
	}
	/**
	 * 加载试卷结构编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String structureEdit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试卷结构编辑页面...");
		PaperItemUtils.addAllItemType(this.itemService, model);
		
		return "library/paper_structure_edit";
	}

	/**
	 * 更新试卷结构数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * 试卷结构数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value = "/update/{paperId}", method = RequestMethod.POST)
	@ResponseBody
	public Json structureUpdate(@PathVariable String paperId,StructureInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("更新试卷［%s］结构数据...", paperId));
		Json result = new Json();
		try {
			 this.paperStructureService.updateStructure(paperId, info);
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除试卷结构数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @param id
	 * 删除的结构ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.DELETE})
	@RequestMapping(value = "/delete/{paperId}", method = RequestMethod.POST)
	@ResponseBody
	public Json structureDelete(@PathVariable String paperId,String structureId){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷［%1$s］结构数据［%2$s］...", paperId, structureId));
		Json result = new Json();
		try {
			this.paperStructureService.deleteStructure(structureId.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据[%s]时发生异常:",structureId), e);
		}
		return result;
	}
}