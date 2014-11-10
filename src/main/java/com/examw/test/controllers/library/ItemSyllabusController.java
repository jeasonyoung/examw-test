package com.examw.test.controllers.library;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.syllabus.BaseSyllabusInfo;
import com.examw.test.service.library.IItemSyllabusService;
import com.examw.test.service.syllabus.ISyllabusService;

/**
 * 试题与大纲关联控制器
 * 
 * @author fengwei.
 * @since 2014年11月8日 上午11:21:13.
 */
@Controller
@RequestMapping(value="/library/item/syllabus")
public class ItemSyllabusController {
	private static final Logger logger = Logger.getLogger(ItemController.class);
	@Resource
	private IItemSyllabusService itemSyllabusService;
	@Resource
	private ISyllabusService syllabusService;
	/**
	 * 列出与该试题关联的考试大纲列表
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ ModuleConstant.LIBRARY_ITEM + ":" + Right.VIEW })
	@RequestMapping(value = "/{subjectId}/{itemId}", method = { RequestMethod.GET,RequestMethod.POST })
	public String itemSyllabusList(@PathVariable String subjectId,@PathVariable String itemId, Model model) {
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_ITEM + ":" + Right.DELETE);
		model.addAttribute("SUBJECTID",subjectId);
		model.addAttribute("ITEMID",itemId);
		return "library/item_syllabus_list";
	}

	/**
	 * 加载列表页面数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ ModuleConstant.LIBRARY_ITEM + ":" + Right.VIEW })
	@RequestMapping(value = "/{itemId}/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<BaseSyllabusInfo> syllabusDatagrid(@PathVariable String itemId) {
		if (logger.isDebugEnabled()) logger.debug("加载列表页面数据...");
		DataGrid<BaseSyllabusInfo> dataGrid = new DataGrid<BaseSyllabusInfo>();
		List<BaseSyllabusInfo> rows = this.itemSyllabusService.loadItemSyllabuses(itemId);
		dataGrid.setRows(rows);
		dataGrid.setTotal((long) rows.size());
		return dataGrid;
	}
	
	@RequiresPermissions({ ModuleConstant.LIBRARY_ITEM + ":" + Right.VIEW })
	@RequestMapping(value = "/{subjectId}/syllabus/list", method = RequestMethod.GET)
	public String subjectSyllabusList(@PathVariable String subjectId, Model model){
		model.addAttribute("SYLLABUSLIST", this.syllabusService.loadEnableSyllabuses(subjectId));
		return "library/item_syllabus_choose_syllabus";
	}
	
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_ITEM + ":" + Right.UPDATE})
	@RequestMapping(value="/{itemId}/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@PathVariable String itemId,String syllabusIds){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			this.itemSyllabusService.updateItemSyllabus(itemId, syllabusIds);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
}
