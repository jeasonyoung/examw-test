package com.examw.test.controllers.products;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.products.RegistrationInfo;
import com.examw.test.service.products.IRegistrationService;

/**
 * 注册码控制器
 * @author fengwei.
 * @since 2014年8月14日 下午3:46:38.
 */
@Controller
@RequestMapping("/products/registration")
public class RegistrationController {
	private static final Logger logger = Logger.getLogger(RegistrationController.class);
	//注册码服务接口。
	@Resource
	private IRegistrationService registrationService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.DELETE);
		model.addAttribute("STATUS_MAP",this.registrationService.getStatusMap());
		return "products/registration_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RegistrationInfo> datagrid(RegistrationInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.registrationService.datagrid(info);
	}
	/**
	 * 编辑页面
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "products/registration_edit";
	}
	/**
	 * 选择产品
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value = "/chooseproduct", method = RequestMethod.GET)
	public String chooseProduct(String productId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载选择产品页面...");
		model.addAttribute("CURRENT_CHOOSE_PRODUCT_ID", productId);
		return "products/registration_choose_product";
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(RegistrationInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.registrationService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新产品数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.registrationService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}
