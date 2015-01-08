package com.examw.test.controllers.publish;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.publish.StaticPageInfo;
import com.examw.test.service.publish.IStaticPageService;

/**
 * 静态页面控制器。
 * 
 * @author yangyong
 * @since 2014年12月28日
 */
@Controller
@RequestMapping(value = "/publish/page")
public class StaticPageController {
	private static final Logger logger = Logger.getLogger(StaticPageController.class);
	//注入静态页面服务接口。
	@Resource
	private IStaticPageService staticPageService;
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_PAGE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PUBLISH_CONFIG + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PUBLISH_CONFIG + ":" + Right.DELETE);
		return "/publish/page_list";
	}
	/**
	 * 加载静态页面预览。
	 * @param pageId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_PAGE + ":" + Right.VIEW})
	@RequestMapping(value={"/preview/{pageId}"}, method = RequestMethod.GET)
	public String preview(@PathVariable String pageId, Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载静态页面［%s］预览...", pageId));
		model.addAttribute("content", this.staticPageService.loadPageContent(pageId));
		return "/publish/page_preview";
	}
	/**
	 * 加载列表数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_PAGE + ":" + Right.VIEW})
	@RequestMapping(value={"/datagrid"}, method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<StaticPageInfo> datagrid(StaticPageInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.staticPageService.datagrid(info);
	}
	/**
	 * 加载编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_PAGE + ":" + Right.UPDATE})
	@RequestMapping(value={"/edit"}, method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "publish/page_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_PAGE + ":" + Right.UPDATE})
	@RequestMapping(value={"/update"}, method = RequestMethod.POST)
	@ResponseBody
	public Json update(StaticPageInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.staticPageService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据发生异常:%s", e.getMessage()),e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param ids
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_PAGE + ":" + Right.DELETE})
	@RequestMapping(value={"/delete"}, method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.staticPageService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}