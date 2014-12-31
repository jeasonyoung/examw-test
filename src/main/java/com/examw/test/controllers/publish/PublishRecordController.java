package com.examw.test.controllers.publish;

import java.util.Arrays;
import java.util.List;

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
import com.examw.test.model.publish.PublishRecordInfo;
import com.examw.test.service.publish.IPublishRecordService;

/**
 * 发布记录控制器。
 * 
 * @author yangyong
 * @since 2014年12月28日
 */
@Controller
@RequestMapping(value = "/publish/record")
public class PublishRecordController {
	private static final Logger logger = Logger.getLogger(PublishRecordController.class);
	//注入发布记录服务接口
	@Resource
	private IPublishRecordService publishRecordService;
	/**
	 * 加载列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_RECORD + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PUBLISH_CONFIG + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PUBLISH_CONFIG + ":" + Right.DELETE);
		return "publish/record_list";
	}
	/**
	 * 加载列表数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_RECORD + ":" + Right.VIEW})
	@RequestMapping(value={"/datagrid"}, method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PublishRecordInfo> datagrid(PublishRecordInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.publishRecordService.datagrid(info);
	}
	/**
	 * 加载全部发布记录数据。
	 * @return
	 */
	@RequestMapping(value={"/all"}, method = {RequestMethod.GET , RequestMethod.POST})
	@ResponseBody
	public List<PublishRecordInfo> loadRecords(){
		if(logger.isDebugEnabled()) logger.debug("加载全部发布记录数据...");
		return this.publishRecordService.datagrid(new PublishRecordInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() { return "startTime";}
			@Override
			public String getOrder() { return "desc";}
		}).getRows();
	}
	/**
	 * 删除数据。
	 * @param ids
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PUBLISH_RECORD + ":" + Right.DELETE})
	@RequestMapping(value={"/delete"}, method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.publishRecordService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}