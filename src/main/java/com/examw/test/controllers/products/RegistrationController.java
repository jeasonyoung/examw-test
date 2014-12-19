package com.examw.test.controllers.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.examw.test.model.products.BatchRegistrationInfo;
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
	private static final Map<String, List<String>> cache_registration_code = new HashMap<>();
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
	public String edit(String registrationId, String categoryId,String examId,Model model){
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
	 * 加载批量创建注册码编辑页面。
	 * @param registrationId
	 * @param categoryId
	 * @param examId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value = "/batch/edit", method = RequestMethod.GET)
	public String batchCreateEdit(String registrationId, String categoryId,String examId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载批量创建注册码编辑页面..");
		this.edit(registrationId, categoryId, examId, model);
		return "products/registration_batch_edit";
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
	 * 批量创建注册码处理。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value="/batch/update", method = RequestMethod.POST)
	@ResponseBody
	public Json batchUpdate(@RequestBody BatchRegistrationInfo info){
		if(logger.isDebugEnabled()) logger.debug("批量创建注册码处理...");
		Json result = new Json();
		try {
			String key = UUID.randomUUID().toString();
			cache_registration_code.put(key, this.registrationService.updateBatch(info));
			result.setData(key);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 导出批量创建的注册码。
	 * @param key
	 * @param response
	 * @throws Exception 
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_REGISTRATION + ":" + Right.UPDATE})
	@RequestMapping(value="/batch/export/{key}", method = RequestMethod.GET)
	public void batchExport(@PathVariable String key,HttpServletResponse response) throws Exception{
		if(logger.isDebugEnabled()) logger.debug("导出批量创建的注册码...");
		StringBuilder builder = new StringBuilder();
		List<String> list = cache_registration_code.get(key);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				builder.append(i+1).append(".").append(list.get(i)).append("\r\n");
			}
			cache_registration_code.remove(key);
		}else{
			builder.append("没有注册码数据！");
		}
		byte[] data = builder.toString().getBytes("UTF-8");
		if(data != null && data.length > 0){
			//设置页面不缓存。
			response.setHeader("Pragme", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("text/plain;charset=UTF-8");
			String fileName = String.format("attachment;filename=%1$s-%2$s", new String("批量创建注册码".getBytes("UTF-8"),"ISO8859-1"),new Date().getTime());
			response.setHeader("Content-disposition", fileName);
			response.getOutputStream().write(data);
		}
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