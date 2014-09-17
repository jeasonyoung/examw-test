package com.examw.test.controllers.products;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.products.ChannelInfo;
import com.examw.test.service.products.IChannelService;

/**
 * 渠道控制器
 * @author fengwei.
 * @since 2014年8月11日 下午4:57:50.
 */
@Controller
@RequestMapping(value = "/products/channel")
public class ChannelController {
	private static final Logger logger = Logger.getLogger(ChannelController.class);
	//渠道服务接口。
	@Resource
	private IChannelService channelService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_CHANNEL + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_CHANNEL + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_CHANNEL + ":" + Right.DELETE);
		return "products/channel_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_CHANNEL + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ChannelInfo> datagrid(ChannelInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.channelService.datagrid(info);
	}
	
	/**
	 * 获取编辑页面。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_CHANNEL + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "products/channel_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_CHANNEL + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ChannelInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.channelService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新渠道数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_CHANNEL + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.channelService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 渠道的下拉数据
	 * @return
	 */
	@RequestMapping(value="/combo", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<ChannelInfo> combo(){
		return this.channelService.datagrid(new ChannelInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort(){ return "code"; }
			@Override
			public String getOrder() { return "asc"; }
		}).getRows();
	}
	
	/**
	 * 加载来源代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_CHANNEL + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	@ResponseBody
	public Integer code(){
		Integer max = this.channelService.loadMaxCode();
		if(max == null) max = 0;
		return max + 1;
	}
}
