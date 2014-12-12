package com.examw.test.controllers.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.settings.AreaInfo;
import com.examw.test.model.settings.ExamInfo;
import com.examw.test.service.settings.ExamStatus;
import com.examw.test.service.settings.IExamService;
import com.examw.test.support.EnumMapUtils;

/**
 * 考试控制器。
 * @author fengwei.
 * @since 2014-08-07.
 */
@Controller
@RequestMapping(value = "/settings/exam")
public class ExamController {
	private static Logger logger  = Logger.getLogger(ExamController.class);
	//考试数据接口.
	@Resource
	private IExamService examService;
	/**
	 * 加载列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_EXAM + ":" + Right.DELETE);
		return "settings/exam_list";
	}
	/**
	 * 加载编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		Map<String, String> examStatusMaps = EnumMapUtils.createTreeMap();
		for(ExamStatus status : ExamStatus.values()){
			examStatusMaps.put(String.format("%d", status.getValue()), this.examService.loadStatusName(status.getValue()));
		}
		model.addAttribute("ExamStatusMaps", examStatusMaps);
		return "settings/exam_edit";
	}
	/**
	 * 加载列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ExamInfo> datagrid(ExamInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.examService.datagrid(info);
	}
	/**
	 * 加载考试类别下的考试集合。
	 * @return
	 */
	@RequestMapping(value={"/all"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<ExamInfo> all(String categoryId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试类别［categoryId = %s］下的考试集合...", categoryId));
		List<ExamInfo> list = new ArrayList<>();
		if(!StringUtils.isEmpty(categoryId)){
			list = this.examService.loadExams(categoryId,null);
		}
		return list;
	}
	/**
	 * 加载考试所属地区集合。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 */
	@RequestMapping(value = {"/areas"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<AreaInfo> loadArea(String examId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［examId = %s］所属地区集合...", examId));
		return this.examService.loadExamAreas(examId);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ExamInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.examService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据［%s］...", id));
		Json result = new Json();
		try {
			this.examService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载最大考试代码。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	@ResponseBody
	public Integer code(){
		if(logger.isDebugEnabled()) logger.debug("加载最大考试代码...");
		Integer max = this.examService.loadMaxCode();
		if(max == null) max = 0;
		return max+1;
	}
}