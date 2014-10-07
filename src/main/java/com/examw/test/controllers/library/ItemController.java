package com.examw.test.controllers.library;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemJudgeAnswer;
import com.examw.test.service.library.ItemStatus;
import com.examw.test.support.ItemTypeUtils;

/**
 * 题目管理控制器。
 * 
 * @author yangyong
 * @since 2014年8月9日
 */
@Controller
@RequestMapping(value="/library/item")
public class ItemController implements IUserAware {
	private static final Logger logger = Logger.getLogger(ItemController.class);
	//注入题目服务接口
	@Resource
	private IItemService itemService;
	private String current_userId = null, current_userName = null;
	/*
	 * 注入当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug("注入当前用户ID=" + userId);
		this.current_userId = userId;
	}
	/*
	 * 注入当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		if(logger.isDebugEnabled()) logger.debug("注入当前用户名称=" + userName);
		this.current_userName = userName;
	}
	/*
	 * 注入当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {
	}
	/**
	 * 列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_ITEM + ":" + Right.DELETE);
		
		model.addAttribute("ITEM_STATUS_NONE_VALUE", ItemStatus.NONE.getValue());
		
		ItemTypeUtils.addAllItemType(this.itemService, model);
		
		return "library/item_list";
	}
	/**
	 * 加载列表页面数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ItemInfo> datagrid(ItemInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面数据...");
		return this.itemService.datagrid(info);
	}
	/**
	 * 编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit/{type}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer type,String examId,Boolean opts,Boolean isChild,Boolean isStructure, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_ITEM + ":" + Right.DELETE);
		
		model.addAttribute("CURRENT_YEAR",new SimpleDateFormat("yyyy").format(new Date()));
		
		model.addAttribute("CURRENT_ITEM_TYPE_VALUE", type);
		model.addAttribute("CURRENT_ITEM_TYPE_NAME", this.itemService.loadTypeName(type));
		
		model.addAttribute("CURRENT_ITEM_STATUS_VALUE", ItemStatus.NONE.getValue());
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		model.addAttribute("CURRENT_OPTS_STATUS", opts == null ? true : opts);
		model.addAttribute("CURRENT_ITEM_ISCHILD", isChild == null ? false : isChild);
		model.addAttribute("CURRENT_IS_STRUCTURE", isStructure == null ? false : isStructure);
		
		model.addAttribute("OPT_REAL_VALUE", Paper.TYPE_REAL);
		model.addAttribute("OPT_REAL_NAME", this.itemService.loadOptName(Paper.TYPE_REAL));
		model.addAttribute("OPT_SIMU_VALUE", Paper.TYPE_SIMU);
		model.addAttribute("OPT_SIMU_NAME", this.itemService.loadOptName(Paper.TYPE_SIMU));
		model.addAttribute("OPT_PRACTICE_VALUE", Paper.TYPE_PRACTICE);
		model.addAttribute("OPT_PRACTICE_NAME", this.itemService.loadOptName(Paper.TYPE_PRACTICE));
		model.addAttribute("OPT_FORECAST_VALUE", Paper.TYPE_FORECAST);
		model.addAttribute("OPT_FORECAST_NAME", this.itemService.loadOptName(Paper.TYPE_FORECAST));
		
		model.addAttribute("JUDGE_ANSWER_RIGTH_VALUE", ItemJudgeAnswer.RIGTH.getValue());
		model.addAttribute("JUDGE_ANSWER_RIGTH_NAME", this.itemService.loadJudgeAnswerName(ItemJudgeAnswer.RIGTH.getValue()));
		model.addAttribute("JUDGE_ANSWER_WRONG_VALUE", ItemJudgeAnswer.WRONG.getValue());
		model.addAttribute("JUDGE_ANSWER_WRONG_NAME", this.itemService.loadJudgeAnswerName(ItemJudgeAnswer.WRONG.getValue()));
		
		//添加普通题型。
		ItemTypeUtils.addNormalItemType(this.itemService, model);
		
		return String.format("library/item_edit_%s", type);
	}
	/**
	 * 试题选项页面。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/option/{type}", method = RequestMethod.GET)
	public String itemOption(@PathVariable Integer type,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试题选项页面...");
		model.addAttribute("CURRENT_ITEM_TYPE_VALUE", type);
		model.addAttribute("CURRENT_ID", UUID.randomUUID().toString());
		return "library/item_option_dialog";
	}
	/**
	 * 创建共享题子题ID
	 * @return
	 */
	@RequestMapping(value = "/uuid", method = RequestMethod.GET)
	@ResponseBody
	public String[] shareItemUUID(Integer count){
		if(logger.isDebugEnabled()) logger.debug("加载创建共享题子题ID...");
		if(count == null || count < 1) count = 1;
		List<String> list = new ArrayList<>();
		for(int i = 0; i < count; i++){
			list.add(UUID.randomUUID().toString());
		}
		return list.toArray(new String[0]);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody ItemInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(info != null){
				info.setUserId(this.current_userId);
				info.setUserName(this.current_userName);
			}
			result.setData(this.itemService.update(info));
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
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.itemService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 试题预览。
	 * @param itemId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.VIEW})
	@RequestMapping(value="/preview/{itemId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String itemPreview(@PathVariable String itemId,Model model){
		model.addAttribute("ItemJudgeAnswer_Right_Value", ItemJudgeAnswer.RIGTH.getValue());
		model.addAttribute("ItemJudgeAnswer_Right_Name", this.itemService.loadJudgeAnswerName(ItemJudgeAnswer.RIGTH.getValue()));

		model.addAttribute("ItemJudgeAnswer_Wrong_Value", ItemJudgeAnswer.WRONG.getValue());
		model.addAttribute("ItemJudgeAnswer_Wrong_Name", this.itemService.loadJudgeAnswerName(ItemJudgeAnswer.WRONG.getValue()));
		
		//model.addAttribute("item", this.itemService.loadItemPreview(itemId));
		return "library/item_preview";
	}
	/**
	 * 试题审核。
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE})
	@RequestMapping(value="/audit/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public Json audit(@PathVariable String itemId){
		if(logger.isDebugEnabled()) logger.debug("审核试题［"+ itemId +"］...");
		Json result = new Json();
		try {
			 this.itemService.updateStatus(itemId, ItemStatus.AUDIT);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("审核数据["+itemId+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 试题反审核。
	 * @param itemId。
	 * 试题ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE})
	@RequestMapping(value="/unaudit/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public Json unAudit(@PathVariable String itemId){
		if(logger.isDebugEnabled()) logger.debug("反审核试题［"+ itemId +"］...");
		Json result = new Json();
		try {
			 this.itemService.updateStatus(itemId, ItemStatus.NONE);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("反审核数据["+itemId+"]时发生异常:", e);
		}
		return result;
	}
}