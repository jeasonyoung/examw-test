package com.examw.test.controllers.record;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.records.QuestionInfo;
import com.examw.test.service.records.IQuestionService;

/**
 * 常见问题控制器。
 * 
 * @author yangyong
 * @since 2015年1月3日
 */
@Controller
@RequestMapping("/record/question")
public class QuestionController {
	private static final Logger logger = Logger.getLogger(QuestionController.class);
	//注入常见问题服务。
	@Resource
	private IQuestionService questionService;
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPER_QUESTION + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PAPER_QUESTION + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PAPER_QUESTION + ":" + Right.DELETE);
		return "records/question_list";
	}
	/**
	 * 加载列表页面数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPER_QUESTION + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<QuestionInfo> datagrid(QuestionInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.questionService.datagrid(info);
	}
	/**
	 * 加载编辑页面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPER_QUESTION + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "records/question_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPER_QUESTION + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(QuestionInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.questionService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param ids
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPER_QUESTION + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.questionService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}