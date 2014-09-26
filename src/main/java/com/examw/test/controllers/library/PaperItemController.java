package com.examw.test.controllers.library;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.library.IPaperItemService;

/**
 * 试卷试题控制器。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
@Controller
@RequestMapping(value = "/library/paper/items")
public class PaperItemController {
	private static final Logger logger = Logger.getLogger(PaperItemController.class);
	//注入试卷试题服务接口。
	@Resource
	private IPaperItemService paperItemService;
	/**
	 * 加载试卷试题列数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid/{paperId}", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<StructureItemInfo> structureItemsdatagrid(@PathVariable String paperId,StructureItemInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载试卷结构下试题列表页面数据...");
		if(info == null) info = new StructureItemInfo();
		info.setPaperId(paperId);
		return this.paperItemService.datagrid(info);
	}
	/**
	 * 加载试卷结构下最大排序号。
	 * @param structureId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_SOURCE + ":" + Right.VIEW})
	@RequestMapping(value="/order/{structureId}", method = RequestMethod.GET)
	@ResponseBody
	public String[] loadstructureItemsMaxOrderNo(@PathVariable String structureId){
		Long max = this.paperItemService.loadStructureItemMaxOrderNo(structureId);
		if(max == null) max = 0L;
		return new String[]{ String.format("%02d", max+1)};
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
	@RequestMapping(value = "/update/{paperId}", method = RequestMethod.POST)
	@ResponseBody
	public Json structureItemsUpdate(@PathVariable String paperId,@RequestBody StructureItemInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("更新试卷［%s］结构下试题数据...", paperId));
		Json result = new Json();
		try {
			if(info == null) info = new StructureItemInfo();
			info.setPaperId(paperId);
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
			this.paperItemService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据[%s]时发生异常:",id), e);
		}
		return result;
	}
}