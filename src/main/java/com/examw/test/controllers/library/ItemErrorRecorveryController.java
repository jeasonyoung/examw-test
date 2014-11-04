package com.examw.test.controllers.library;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.ItemErrorRecorveryInfo;
import com.examw.test.service.library.IItemErrorRecorveryService;

/**
 * 试题纠错控制器
 * @author fengwei.
 * @since 2014年11月4日 下午4:28:03.
 */
@Controller
@RequestMapping(value="/library/item/recorvery")
public class ItemErrorRecorveryController implements IUserAware {
	private static final Logger logger = Logger.getLogger(ItemController.class);
	private String current_userId = null, current_userName = null;
	@Resource
	private IItemErrorRecorveryService itemErrorRecorveryService;
	@Override
	public void setUserId(String userId) {
		this.current_userId = userId;
	}
	@Override
	public void setUserName(String userName) {
		this.current_userName = userName;
	}
	@Override
	public void setUserNickName(String userNickName) {
	}
	/**
	 * 设置 试题纠错服务接口
	 * @param itemErrorRecorveryService
	 * 
	 */
	public void setItemErrorRecorveryService(
			IItemErrorRecorveryService itemErrorRecorveryService) {
		this.itemErrorRecorveryService = itemErrorRecorveryService;
	}
	/**
	 * 列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM_RECORVERY + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_ITEM_RECORVERY + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_ITEM_RECORVERY + ":" + Right.DELETE);
		return "library/item_error_recorvery_list";
	}
	/**
	 * 加载列表页面数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM_RECORVERY + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ItemErrorRecorveryInfo> datagrid(ItemErrorRecorveryInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面数据...");
		return this.itemErrorRecorveryService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody ItemErrorRecorveryInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			info.setAdminUserId(this.current_userId);
			info.setAdminUserName(this.current_userName);
			result.setData(this.itemErrorRecorveryService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
}
