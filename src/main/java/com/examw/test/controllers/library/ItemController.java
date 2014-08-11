package com.examw.test.controllers.library;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemStatus;

/**
 * 题目管理控制器。
 * 
 * @author yangyong
 * @since 2014年8月9日
 */
@Controller
@RequestMapping(value="/library/item")
public class ItemController {
	private static final Logger logger = Logger.getLogger(ItemController.class);
	//注入题目服务接口
	@Resource
	private IItemService itemService;
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
		//单选
		model.addAttribute("TYPE_SINGLE_VALUE", Item.TYPE_SINGLE);
		model.addAttribute("TYPE_SINGLE_NAME", this.itemService.loadTypeName(Item.TYPE_SINGLE));
		//多选
		model.addAttribute("TYPE_MULTY_VALUE", Item.TYPE_MULTY);
		model.addAttribute("TYPE_MULTY_NAME", this.itemService.loadTypeName(Item.TYPE_MULTY));
		//不定向选
		model.addAttribute("TYPE_UNCERTAIN_VALUE", Item.TYPE_UNCERTAIN);
		model.addAttribute("TYPE_UNCERTAIN_NAME", this.itemService.loadTypeName(Item.TYPE_UNCERTAIN));
		//判断
		model.addAttribute("TYPE_JUDGE_VALUE", Item.TYPE_JUDGE);
		model.addAttribute("TYPE_JUDGE_NAME", this.itemService.loadTypeName(Item.TYPE_JUDGE));
		//问答
		model.addAttribute("TYPE_QANDA_VALUE", Item.TYPE_QANDA);
		model.addAttribute("TYPE_QANDA_NAME", this.itemService.loadTypeName(Item.TYPE_QANDA));
		//共提干
		model.addAttribute("TYPE_SHARE_TITLE_VALUE", Item.TYPE_SHARE_TITLE);
		model.addAttribute("TYPE_SHARE_TITLE_NAME", this.itemService.loadTypeName(Item.TYPE_SHARE_TITLE));
		//共答案
		model.addAttribute("TYPE_SHARE_ANSWER_VALUE", Item.TYPE_SHARE_ANSWER);
		model.addAttribute("TYPE_SHARE_ANSWER_NAME", this.itemService.loadTypeName(Item.TYPE_SHARE_ANSWER));
		
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
	public String edit(@PathVariable Integer type,String examId,Boolean opts,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_ITEM + ":" + Right.DELETE);
		
		model.addAttribute("CURRENT_YEAR",new SimpleDateFormat("yyyy").format(new Date()));
		
		model.addAttribute("CURRENT_ITEM_TYPE_VALUE", type);
		model.addAttribute("CURRENT_ITEM_STATUS_VALUE", ItemStatus.NONE.getValue());
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		model.addAttribute("CURRENT_OPTS_STATUS", opts == null ? true : opts);
		
		model.addAttribute("OPT_REAL_VALUE", Paper.TYPE_REAL);
		model.addAttribute("OPT_REAL_NAME", this.itemService.loadOptName(Paper.TYPE_REAL));
		model.addAttribute("OPT_SIMU_VALUE", Paper.TYPE_SIMU);
		model.addAttribute("OPT_SIMU_NAME", this.itemService.loadOptName(Paper.TYPE_SIMU));
		model.addAttribute("OPT_PRACTICE_VALUE", Paper.TYPE_PRACTICE);
		model.addAttribute("OPT_PRACTICE_NAME", this.itemService.loadOptName(Paper.TYPE_PRACTICE));
		model.addAttribute("OPT_FORECAST_VALUE", Paper.TYPE_FORECAST);
		model.addAttribute("OPT_FORECAST_NAME", this.itemService.loadOptName(Paper.TYPE_FORECAST));
		
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
}