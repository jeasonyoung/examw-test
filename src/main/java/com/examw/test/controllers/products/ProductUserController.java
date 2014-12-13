package com.examw.test.controllers.products;

import java.util.Arrays;
import java.util.Map;

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
import com.examw.service.Status;
import com.examw.test.domain.security.Right;
import com.examw.test.model.products.ProductUserInfo;
import com.examw.test.service.products.IProductUserService;
import com.examw.test.support.EnumMapUtils;

/**
 * 产品用户控制器
 * @author fengwei.
 * @since 2014年8月12日 上午9:03:22.
 */
@Controller
@RequestMapping("/products/user")
public class ProductUserController {
	private static final Logger logger = Logger.getLogger(ProductUserController.class);
	//注入产品用户服务接口。
	@Resource
	private IProductUserService productUserService;
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.DELETE);
		return "products/user_list";
	}
	/**
	 * 加载编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.productUserService.loadStatusName(status.getValue()));
		}
		model.addAttribute("statusMap", statusMap);
		return "products/user_edit";
	}
	/**
	 * 加载列表页面数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ProductUserInfo> datagrid(ProductUserInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.productUserService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ProductUserInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.productUserService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("更新数据发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param ids
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.productUserService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}