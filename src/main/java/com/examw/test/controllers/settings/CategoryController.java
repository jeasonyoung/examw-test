package com.examw.test.controllers.settings;

import java.util.ArrayList;
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
import com.examw.test.domain.settings.Category;
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
	public String edit(String pid,Model model){
		model.addAttribute("CURRENT_CATEGORY_ID", pid);
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
	public List<TreeNode> tree(){
		List<TreeNode> result = new ArrayList<>();
		List<Category> list = this.categroyService.loadAllCategorys();
		if(list != null && list.size() > 0){
			for(final Category data : list){
				if(data == null) continue;
				result.add(createTreeNode(data));
			}
		}
		return result;
	}
	private TreeNode createTreeNode(Category data){
		if(data == null) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getName());
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<TreeNode> list = new ArrayList<>();
			for(Category c : data.getChildren()){
				TreeNode t = this.createTreeNode(c);
				 if(t != null) list.add(t);
			}
			node.setChildren(list);
		}
		return node;
	}
	/**
	 * 考试类别考试树。
	 * @return
	 */
//	@RequestMapping(value = "/exams-tree", method = {RequestMethod.GET,RequestMethod.POST})
//	@ResponseBody
//	public List<TreeNode> allCatalogExams(){
//		return this.categroyService.loadAllCatalogExams();
//	}
	/**
	 * 考试科目树。
	 * @return
	 */
//	@RequestMapping(value = "/subject-tree", method = { RequestMethod.GET, RequestMethod.POST})
//	@ResponseBody
//	public List<TreeNode> allCatalogExamSubjects(){
//		return this.catalogService.loadAllCatalogExamSubjects();
//	}
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
}
