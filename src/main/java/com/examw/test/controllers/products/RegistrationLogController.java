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
import com.examw.test.domain.security.Right;
import com.examw.test.model.products.RegistrationLogInfo;
import com.examw.test.service.products.IRegistrationLogService;

/**
 * 注册码日志控制器
 * @author fengwei.
 * @since 2014年8月14日 下午5:04:09.
 */
@Controller
@RequestMapping("/products/registration/log")
public class RegistrationLogController {
	private static final Logger logger = Logger.getLogger(RegistrationLogController.class);
	//注册码日志服务接口。
	@Resource
	private IRegistrationLogService registrationLogService;
	
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION_LOG + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_REGISTRATION_LOG + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_REGISTRATION_LOG + ":" + Right.DELETE);
		model.addAttribute("TYPE_MAP",this.registrationLogService.getTypeMap());
		return "products/registration_log_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION_LOG + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RegistrationLogInfo> datagrid(RegistrationLogInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.registrationLogService.datagrid(info);
	}
}
