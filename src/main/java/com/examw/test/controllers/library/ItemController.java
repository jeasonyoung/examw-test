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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.ItemStatus;
import com.examw.test.service.library.ItemType;
import com.examw.test.support.PaperItemUtils;

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
	private String current_userId = null, current_userName = null;
	//注入试卷服务接口。
	@Resource
	private IPaperService paperService;
	//注入题目服务接口
	@Resource
	private IItemService itemService;
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
	public void setUserNickName(String userNickName) {}
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
		
		model.addAttribute("item_status_none_value", ItemStatus.NONE.getValue());
		
		PaperItemUtils.addAllItemType(this.itemService, model);
		
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
	public String edit(@PathVariable Integer type,String examId,String subjectId, Boolean child,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_ITEM + ":" + Right.DELETE);
		
		//获取当前题型。
		ItemType itemType =  ItemType.convert(type);
		model.addAttribute("current_item_type_value", itemType.getValue());
		model.addAttribute("current_item_type_name", this.itemService.loadTypeName(itemType.getValue()));
		child = (child == null) ? false : child;
		model.addAttribute("current_item_child",child);
		if(!child){
			model.addAttribute("current_exam_id", examId);
			model.addAttribute("current_subject_id", subjectId);
			model.addAttribute("current_year",new SimpleDateFormat("yyyy").format(new Date()));
			//添加试卷类型。
			PaperItemUtils.addPaperType(this.paperService, model);
		}
		if(itemType == ItemType.JUDGE){
			PaperItemUtils.addItemJudgeAnswers(this.itemService, model);//添加判断题型答案。
		}else if(itemType == ItemType.SHARE_TITLE){
			PaperItemUtils.addNormalItemType(this.itemService, model);//添加普通题型。
			PaperItemUtils.addItemJudgeAnswers(this.itemService, model);//添加判断题型答案。
		}else if(itemType == ItemType.SHARE_ANSWER){
			PaperItemUtils.addChoiceItemType(this.itemService, model);//添加选择题型。
		} 
		return String.format("library/item_edit_%d", itemType.getValue());
	}
	/**
	 * 试题选项页面。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/option/{type}", method = RequestMethod.GET)
	public String itemOption(@PathVariable Integer type,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载试题选项页面...");
		model.addAttribute("current_item_type_value", type);
		model.addAttribute("current_id", UUID.randomUUID().toString());
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
			info.setUserId(this.current_userId);
			info.setUserName(this.current_userName);
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
	 * 删除无关联的试题数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.DELETE})
	@RequestMapping(value = {"/delete/isolated"}, method = { RequestMethod.GET})
	@ResponseBody
	public Json deleteIsolated(){
		Json json = new Json();
		try{
			Integer count = this.itemService.deleteIsolated();
			json.setSuccess(true);
			json.setMsg(String.format("删除［%d］条无关联的试题数据！", count));
			if(logger.isDebugEnabled()) logger.debug(json.getMsg());
		}catch(Exception e){
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(String.format("删除无关联的试题数据异常：%s", e.getMessage()), e);
		}
		return json;
	}
	/**
	 * 试题预览。
	 * @param itemId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.VIEW})
	@RequestMapping(value="/preview/{itemId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String itemPreview(@PathVariable String itemId,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题［itemId = %s］预览...", itemId));
		Item item = this.itemService.loadItem(itemId);
		if(item == null){
			throw new RuntimeException(String.format("试题［itemId = %s］不存在！", itemId));
		}
		ItemInfo itemInfo = new ItemInfo();
		this.itemService.conversion(item, itemInfo, true);
		model.addAttribute("item", itemInfo);
		if(itemInfo.getType() == ItemType.JUDGE.getValue()){
			PaperItemUtils.addItemJudgeAnswers(this.itemService, model);
		}else if(itemInfo.getType() == ItemType.SHARE_TITLE.getValue()){
			PaperItemUtils.addItemJudgeAnswers(this.itemService, model);//判断题答案
		}
		return "library/item_preview";
	}
	/**
	 * 加载试题信息。
	 * @param itemId
	 * 试题ID。
	 * @return
	 */
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	@ResponseBody
	public Json loadItem(@PathVariable String itemId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题［%s］信息...", itemId));
		Json json = new Json();
		try {
			Item item = this.itemService.loadItem(itemId);
			json.setSuccess(item != null);
			if(!json.isSuccess()){
				json.setMsg(String.format("试题［%s］不存在！", itemId));
				logger.error(json.getMsg());
				return json;
			}
			ItemInfo itemInfo = new ItemInfo();
			this.itemService.conversion(item, itemInfo, true);
			json.setData(itemInfo);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
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