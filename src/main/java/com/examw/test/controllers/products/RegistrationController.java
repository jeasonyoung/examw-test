package com.examw.test.controllers.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.products.RegistrationInfo;
import com.examw.test.model.products.SoftwareTypeLimitInfo;
import com.examw.test.service.products.IRegistrationService;
import com.examw.test.service.products.ISoftwareTypeLimitService;
import com.examw.test.service.products.RegistrationStatus;
import com.examw.test.support.EnumMapUtils;

/**
 * 注册码控制器。
 * @author fengwei.
 * @since 2014年8月14日 下午3:46:38.
 */
@Controller
@RequestMapping("/products/registration")
public class RegistrationController {
	private static final Logger logger = Logger.getLogger(RegistrationController.class);
	//注入注册码服务接口。
	@Resource
	private IRegistrationService registrationService;
	//注入注册码软件类型限制服务接口。
	@Resource
	private ISoftwareTypeLimitService softwareTypeLimitService;
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.DELETE);
		
		return "products/registration_list";
	}
	/**
	 * 加载编辑页面。
	 * @param categoryId
	 * @param examId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String registrationId, String categoryId,String examId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.DELETE);
		
		model.addAttribute("current_registration_id", registrationId);
		model.addAttribute("current_category_id", categoryId);
		model.addAttribute("current_exam_id", examId);
		
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
		for(RegistrationStatus status : RegistrationStatus.values()){
			statusMap.put(String.format("%d", status.getValue()),  this.registrationService.loadStatusName(status.getValue()));
		}
		model.addAttribute("statusMap", statusMap);
		
		return "products/registration_edit";
	}
	/**
	 * 生成注册码。
	 * @param price
	 * @param limit
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.VIEW})
	@RequestMapping(value = "/code/{price}/{limit}", method = RequestMethod.GET)
	@ResponseBody
	public Json generatedCode(@PathVariable int price, @PathVariable int limit) {
		if(logger.isDebugEnabled()) logger.debug(String.format(" 生成注册码[price=%1$d][limit=%2$d]...", price,limit));
		Json result = new Json();
		try{
			result.setData(this.registrationService.generatedCode(price, limit));
			result.setSuccess(true);
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 加载列表数据。
	 * @param info
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
	 * 加载注册码软件类型限制编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value = "/limits/edit", method = RequestMethod.GET)
	public String loadLimitsEdit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载注册码软件类型限制编辑页面...");
		return "products/registration_limits_edit";
	}
	/**
	 * 加载注册码软件类型限制集合。
	 * @param registerId
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.VIEW})
	@RequestMapping(value="/limits/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SoftwareTypeLimitInfo> loadRegistrationLimits(String registrationId,SoftwareTypeLimitInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载注册码［%s］软件类型限制集合...", registrationId));
		DataGrid<SoftwareTypeLimitInfo> dg = new DataGrid<>();
		if(!StringUtils.isEmpty(registrationId)){
			info.setRegisterId(registrationId);
			dg = this.softwareTypeLimitService.datagrid(info);
		}else{
			dg.setRows(new ArrayList<SoftwareTypeLimitInfo>());
			dg.setTotal((long)0);
		}
		return dg;
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody RegistrationInfo info){
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
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据: %s...",Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.registrationService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}