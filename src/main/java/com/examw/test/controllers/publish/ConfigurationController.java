package com.examw.test.controllers.publish;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
import com.examw.service.Status;
import com.examw.test.domain.security.Right;
import com.examw.test.model.publish.ConfigurationInfo;
import com.examw.test.service.publish.ConfigurationTemplateType;
import com.examw.test.service.publish.IConfigurationService;
import com.examw.test.support.EnumMapUtils;

/**
 * 发布配置控制器。
 * 
 * @author yangyong
 * @since 2014年12月28日
 */
@Controller
@RequestMapping(value = "/publish/config")
public class ConfigurationController {
	private static final Logger logger = Logger.getLogger(ConfigurationController.class);
	//注入发布配置服务接口。
	@Resource
	private IConfigurationService configurationService;
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_CONFIG + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PUBLISH_CONFIG + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PUBLISH_CONFIG + ":" + Right.DELETE);
		return "publish/config_list";
	}
	/**
	 * 加载列表数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_CONFIG + ":" + Right.VIEW})
	@RequestMapping(value={"/datagrid"}, method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ConfigurationInfo> datagrid(ConfigurationInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.configurationService.datagrid(info);
	}
	/**
	 * 加载编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_CONFIG + ":" + Right.UPDATE})
	@RequestMapping(value={"/edit"}, method = RequestMethod.GET)
	public String edit(String configurationId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		//当前配置ID
		model.addAttribute("current_configuration_Id", configurationId);
		
		Map<String, String> templateMap = EnumMapUtils.createTreeMap(),statusMap = EnumMapUtils.createTreeMap();
		//模版
		for(ConfigurationTemplateType type : ConfigurationTemplateType.values()){
			if(type == ConfigurationTemplateType.NONE) continue;
			templateMap.put(String.format("%d", type.getValue()), this.configurationService.loadTemplateName(type.getValue()));
		}
		model.addAttribute("templateMap", templateMap);
		//状态
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.configurationService.loadStatusName(status.getValue()));
		}
		model.addAttribute("statusMap", statusMap);
		return "publish/config_edit";
	}
	/**
	 * 加载发布配置的考试类别/考试/产品数据。
	 * @param configurationId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_CONFIG + ":" + Right.UPDATE})
	@RequestMapping(value={"/cfg/{configurationId}"}, method = RequestMethod.GET)
	@ResponseBody
	public Json loadCategoryExamProducts(@PathVariable String configurationId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载发布配置［%s］的考试类别/考试/产品数据...", configurationId));
		Json result = new Json();
		try {
			ConfigurationInfo info = this.configurationService.convert(this.configurationService.loadConfiguration(configurationId));
			result.setSuccess(info != null);
			if(result.isSuccess()){
				Map<String, Object> map = new HashMap<>();
				map.put("categoryId", info.getCategoryId());
				map.put("examId", info.getExamId());
				map.put("productId", info.getProductId());
				result.setData(map);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("加载数据发生异常:%s", e.getMessage()),e);
		}
		return result;
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_CONFIG + ":" + Right.UPDATE})
	@RequestMapping(value={"/update"}, method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody ConfigurationInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.configurationService.update(info));
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
	@RequiresPermissions({ModuleConstant.PUBLISH_CONFIG + ":" + Right.DELETE})
	@RequestMapping(value={"/delete"}, method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.configurationService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}