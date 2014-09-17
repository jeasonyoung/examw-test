package com.examw.test.controllers.products;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.security.Right;
import com.examw.test.model.products.ProductInfo;
import com.examw.test.service.products.IProductService;

/**
 * 产品控制器
 * @author fengwei.
 * @since 2014年8月12日 下午4:57:38.
 */
@Controller
@RequestMapping("/products/product")
public class ProductController {
	private static final Logger logger = Logger.getLogger(ProductController.class);
	//产品服务接口。
	@Resource
	private IProductService productService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.DELETE);
		return "products/product_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ProductInfo> datagrid(ProductInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.productService.datagrid(info);
	}
	
	/**
	 * 获取编辑页面。
	 * @param examId
	 * 所属考试ID
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String examId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId)?"":examId);
		return "products/product_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ProductInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.productService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新产品数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.productService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	
	/**
	 * 产品的下拉数据
	 * @return
	 */
	@RequestMapping(value="/combo", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<ProductInfo> combo(final String examId){
		if(StringUtils.isEmpty(examId)) return new ArrayList<>();
		return this.productService.datagrid(new ProductInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort(){ return "code"; }
			@Override
			public String getOrder() { return "asc"; }
			@Override
			public String getExamId() {return examId;}
			@Override
			public Integer getStatus() {return Product.STATUS_NONE;}
		}).getRows();
	}
	
	/**
	 * 加载来源代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	@ResponseBody
	public Integer code(){
		Integer max = this.productService.loadMaxCode();
		if(max == null) max = 0;
		return max + 1;
	}
}
