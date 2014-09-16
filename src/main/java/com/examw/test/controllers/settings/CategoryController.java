package com.examw.test.controllers.settings;

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
import com.examw.model.TreeNode;
import com.examw.test.domain.security.Right;
import com.examw.test.model.settings.CategoryInfo;
import com.examw.test.service.settings.ICategoryService;

/**
 * 考试分类控制器
 * @author fengwei.
 * @since 2014年8月6日 下午3:48:01.
 */
@Controller
@RequestMapping(value = "/settings/category")
public class CategoryController {
	private static final Logger logger = Logger.getLogger(CategoryController.class);
	//考试分类服务接口。
	@Resource
	private ICategoryService categroyService;
	
	/**
	 * 考试类别列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_CATEGORY + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_CATEGORY + ":" + Right.DELETE);
		return "settings/category_list";
	}
	/**
	 * 考试类别编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String pid,String id,Model model){
		model.addAttribute("CURRENT_CATEGORY_PID", pid);
		model.addAttribute("CURRENT_CATEGORY_ID", id);
		return "settings/category_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<CategoryInfo> datagrid(CategoryInfo info){
		return this.categroyService.datagrid(info);
	}
	/**
	 * 考试类别树结构数据。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> tree(String ignoreCategoryId){
		return this.categroyService.loadAllCategorys(ignoreCategoryId);
	}
	/**
	 * 考试类别-考试树。
	 * @return
	 */
	@RequestMapping(value = "/exams-tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> allCatalogExams(){
		return this.categroyService.loadAllCategoryExams();
	}
	/**
	 * 考试类别-考试-科目树。
	 * @return
	 */
	@RequestMapping(value = "/subject-tree", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> allCatalogExamSubjects(){
		return this.categroyService.loadAllCategoryExamSubjects();
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(CategoryInfo info){
		Json result = new Json();
		try {
			result.setData(this.categroyService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_CATEGORY + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.categroyService.delete(id.split("\\|"));
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
	@RequiresPermissions({ModuleConstant.SETTINGS_AREA + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	@ResponseBody
	public Integer code(){
		Integer max = this.categroyService.loadMaxCode();
		if(max == null) max = 0;
		return max+1; 
	}
}
