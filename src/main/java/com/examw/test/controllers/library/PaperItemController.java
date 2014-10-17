package com.examw.test.controllers.library;

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
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperItemService;
import com.examw.test.service.library.ItemType;
import com.examw.test.support.PaperItemUtils;

/**
 * 试卷试题控制器。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
@Controller
@RequestMapping(value = "/library/paper/items")
public class PaperItemController implements IUserAware {
	private static final Logger logger = Logger.getLogger(PaperItemController.class);
	private String userId,userName;
	//注入试卷试题服务接口。
	@Resource
	private IPaperItemService paperItemService;
	//注入试题服务接口。
	@Resource
	private IItemService itemService;
	/*
	 * 注入用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入用户ID［%s］...", userId));
		this.userId = userId;
	}
	/*
	 * 注入用户名。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入用户名［%s］...", userName));
		this.userName = userName;
	}
	/*
	 * 注入用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String arg0) {}
	/**
	 * 加载试卷试题列数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid/{paperId}", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<StructureItemInfo> structureItemsdatagrid(@PathVariable String paperId,String structureId, StructureItemInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载试卷结构下试题列表页面数据...");
		info.setPaperId(paperId);
		return this.paperItemService.datagrid(info);
	}
	/**
	 * 加载试卷结构下最大排序号。
	 * @param structureId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/order/{structureId}", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadstructureItemsMaxOrderNo(@PathVariable String structureId){
		if(logger.isDebugEnabled()) logger.debug("加载试卷结构下的排序号...");
		Integer max = this.paperItemService.loadMaxOrderNo(structureId);
		if(max == null) max = 0;
		return max + 1;
	}
	/**
	 * 编辑试卷试题。
	 * @param type
	 * @param child
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit/{type}", method = RequestMethod.GET)
	public String editPaperItem(@PathVariable Integer type, Boolean child,Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷结构下试题［题型：%d］编辑页面...", type));
		
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_PAPER + ":" + Right.DELETE);
		
		model.addAttribute("current_item_child", (child == null) ? false : child);
		//获取当前题型。
		ItemType itemType =  ItemType.convert(type);
		model.addAttribute("current_item_type_value", itemType.getValue());
		model.addAttribute("current_item_type_name", this.itemService.loadTypeName(itemType.getValue()));
		
		if(itemType == ItemType.SHARE_TITLE){
			PaperItemUtils.addNormalItemType(this.itemService, model);//添加普通题型。
		}else if(itemType == ItemType.SHARE_ANSWER){
			PaperItemUtils.addChoiceItemType(this.itemService, model);//添加选择题型。
		}else if(itemType == ItemType.JUDGE){
			PaperItemUtils.addItemJudgeAnswers(this.itemService, model);//添加判断题型答案。
		}
		
		return String.format("/library/paper_item_edit_%d", itemType.getValue());
	}
	/**
	 * 更新试卷结构下试题数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * 试卷结构数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value = "/update/{paperId}/{structureId}", method = RequestMethod.POST)
	@ResponseBody
	public Json structureItemsUpdate(@PathVariable String paperId,@PathVariable String structureId,@RequestBody StructureItemInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("更新试卷［%s］结构下试题数据...", paperId));
		Json result = new Json();
		try { 
			info.setPaperId(paperId);
			info.setStructureId(structureId);
			info.setUserId(this.userId);
			info.setUserName(this.userName);
			result.setData(this.paperItemService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除试卷结构下试题数据。
	 * @param id
	 * 删除的结构ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.DELETE})
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json structureItemsDelete(String id){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷结构下试题［%s］数据...",  id));
		Json result = new Json();
		try {
			String[] array = id.split("\\|");
			for(int i = 0; i < array.length; i++){
				if(StringUtils.isEmpty(array[i]) || array[i].indexOf("$") == -1) continue;
				String[] str_attrs = array[i].split("\\$");
				String structrure_id = str_attrs[0], item_id =  str_attrs[1];
				if(StringUtils.isEmpty(structrure_id) || StringUtils.isEmpty(item_id)) continue;
				this.paperItemService.delete(structrure_id, item_id);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据[%s]时发生异常:",id), e);
		}
		return result;
	}
}