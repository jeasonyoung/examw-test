package com.examw.test.controllers.products;

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
import com.examw.test.model.products.SoftwareInfo;
import com.examw.test.service.products.ISoftwareService;

/**
 * 产品软件控制器
 * @author fengwei.
 * @since 2014年8月13日 下午4:12:34.
 */
@Controller
@RequestMapping("/products/software")
public class SoftwareController {
	private static final Logger logger = Logger.getLogger(SoftwareController.class);
	//注入产品软件服务接口。
	@Resource
	private ISoftwareService softwareService;
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_SOFTWARE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_SOFTWARE + ":" + Right.DELETE);
		return "products/software_list";
	}
	/**
	 * 加载编辑页面。
	 * @param examId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String categoryId,String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_category_id", categoryId);
		model.addAttribute("current_exam_id", examId);
		return "products/software_edit";
	}
	/**
	 * 加载最大代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARE + ":" + Right.VIEW})
	@RequestMapping(value="/order", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(String productId){
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		Integer max = this.softwareService.loadMaxOrder(productId);
		if(max == null) max = 0;
		return max + 1;
	}
	/**
	 * 加载列表数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SoftwareInfo> datagrid(SoftwareInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.softwareService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(SoftwareInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.softwareService.update(info));
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
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.softwareService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}