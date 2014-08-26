package com.examw.test.controllers.products;

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
import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.security.Right;
import com.examw.test.model.products.ProductUserInfo;
import com.examw.test.service.products.IProductUserService;

/**
 * 产品用户控制器
 * @author fengwei.
 * @since 2014年8月12日 上午9:03:22.
 */
@Controller
@RequestMapping("/products/user")
public class ProductUserController {
	private static final Logger logger = Logger.getLogger(ProductUserController.class);
	//产品用户服务接口。
	@Resource
	private IProductUserService productUserService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.DELETE);
		return "products/user_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ProductUserInfo> datagrid(ProductUserInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.productUserService.datagrid(info);
	}
	
	/**
	 * 获取编辑页面。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("STATUS_ENABLE", this.productUserService.loadStatusName(ProductUser.STATUS_ENABLE));
		model.addAttribute("STATUS_DISENABLE", this.productUserService.loadStatusName(ProductUser.STATUS_DISENABLE));
		model.addAttribute("STATUS_DELETE", this.productUserService.loadStatusName(ProductUser.STATUS_DELETE));
		return "products/user_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ProductUserInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.productUserService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新产品用户数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.productUserService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 加载来源代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCTUSER + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	@ResponseBody
	public String[] code(){
		Integer max = this.productUserService.loadMaxCode();
		if(max == null) max = 0;
		if(max.toString().length()<=2)
		return new String[]{ String.format("%02d", max + 1) };
		return new String[]{ String.format("%0"+max.toString().length()+"d", max + 1) };
	}
}
