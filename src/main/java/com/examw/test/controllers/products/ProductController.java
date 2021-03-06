package com.examw.test.controllers.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.products.ProductInfo;
import com.examw.test.service.products.IProductService;
import com.examw.test.service.products.ProductAnalysisType;
import com.examw.test.service.products.ProductRealType;
import com.examw.test.service.products.ProductStatus;
import com.examw.test.support.EnumMapUtils;

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
	 * 获取编辑页面。
	 * @param catalogId
	 * 所属考试类别ID。
	 * @param examId
	 * 所属考试ID
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String categoryId, String examId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("current_category_id", categoryId);
		model.addAttribute("current_exam_id", examId);
		
		Map<String, String> productStatusMap = EnumMapUtils.createTreeMap(),
				analysisTypeMap = EnumMapUtils.createTreeMap(),realTypeMap = EnumMapUtils.createTreeMap();
		for(ProductStatus status : ProductStatus.values()){//状态
			productStatusMap.put(String.format("%d", status.getValue()), this.productService.loadStatusName(status.getValue()));
		}
		model.addAttribute("productStatusMap", productStatusMap);
		for(ProductAnalysisType type : ProductAnalysisType.values()){//答案解析类型
			analysisTypeMap.put(String.format("%d", type.getValue()), this.productService.loadAnalysisTypeName(type.getValue()));
		}
		model.addAttribute("analysisTypeMap", analysisTypeMap);
		for(ProductRealType type : ProductRealType.values()){//真题类型
			realTypeMap.put(String.format("%d", type.getValue()), this.productService.loadRealTypeName(type.getValue()));
		}
		model.addAttribute("realTypeMap", realTypeMap);
		return "products/product_edit";
	}
	/**
	 * 加载最大排序号。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.VIEW})
	@RequestMapping(value="/order", method = RequestMethod.GET)
	@ResponseBody
	public Integer loadMaxOrder(String examId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［examId = %s］下最大排序号...", examId));
		Integer max = this.productService.loadMaxOrder(examId);
		if(max == null) max = 0;
		return max + 1;
	}
	/**
	 * 加载考试下产品数据集合。
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<ProductInfo> loadProducts(String examId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试［examId = %s］下产品数据集合...", examId)); 
		List<ProductInfo> list = new ArrayList<>();
		if(!StringUtils.isEmpty(examId)){
			list = this.productService.loadProducts(examId);
		}
		return list;
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
			logger.error(String.format("更新数据发生异常:%s", e.getMessage()),e);
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
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.productService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	
	/**
	 * 设置所有产品的标题,关键字,描述
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PRODUCTS_PRODUCT + ":" + Right.UPDATE})
	@RequestMapping(value="/setKeywords", method = RequestMethod.POST)
	@ResponseBody
	public Json setKeywords(){
		if(logger.isDebugEnabled()) logger.debug("设置标题,关键字,描述...");
		Json result = new Json();
		try {
			this.productService.setTitleKeywords();
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("设置标题,关键字,描述数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
	
}