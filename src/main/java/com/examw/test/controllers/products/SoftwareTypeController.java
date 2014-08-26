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
import com.examw.test.model.products.SoftwareTypeInfo;
import com.examw.test.service.products.ISoftwareTypeService;

/**
 * 软件类型控制器
 * @author fengwei.
 * @since 2014年8月12日 上午9:03:07.
 */
@Controller
@RequestMapping("/products/softwaretype")
public class SoftwareTypeController {
	private static final Logger logger = Logger.getLogger(SoftwareTypeController.class);
	//软件类型服务接口。
	@Resource
	private ISoftwareTypeService softwareTypeService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARETYPE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_SOFTWARETYPE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_SOFTWARETYPE + ":" + Right.DELETE);
		return "products/softwaretype_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARETYPE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SoftwareTypeInfo> datagrid(SoftwareTypeInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.softwareTypeService.datagrid(info);
	}
	
	/**
	 * 获取编辑页面。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARETYPE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "products/softwaretype_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARETYPE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(SoftwareTypeInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.softwareTypeService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新软件类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_SOFTWARETYPE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.softwareTypeService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 软件类型的下拉数据
	 * @return
	 */
	@RequestMapping(value="/combo", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<SoftwareTypeInfo> combo(){
		return this.softwareTypeService.datagrid(new SoftwareTypeInfo(){
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
	public String[] code(){
		Integer max = this.softwareTypeService.loadMaxCode();
		if(max == null) max = 0;
		return new String[]{ String.format("%02d", max + 1) };
	}
}
