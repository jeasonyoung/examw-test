package com.examw.test.controllers.library;

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
import com.examw.test.model.library.SourceInfo;
import com.examw.test.service.library.ISourceService;

/**
 * 来源控制器。
 * @author yangyong.
 * @since 2014-08-06.
 */
@Controller
@RequestMapping(value = "/library/source")
public class SourceController {
	private static final Logger logger = Logger.getLogger(SourceController.class);
	//来源服务。
	@Resource
	private ISourceService sourceService;
	/**
	 * 列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_SOURCE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_SOURCE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_SOURCE + ":" + Right.DELETE);
		return "library/source_list";
	}
	/**
	 * 加载来源代码值。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_SOURCE + ":" + Right.VIEW})
	@RequestMapping(value="/code", method = RequestMethod.GET)
	@ResponseBody
	public String[] code(){
		Integer max = this.sourceService.loadMaxCode();
		if(max == null) max = 0;
		return new String[]{ String.format("%02d", max + 1) };
	}
	/**
	 * 加载来源的全部数据。
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<SourceInfo> all(){
		return this.sourceService.datagrid(new SourceInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort(){return "code";};
			@Override
			public String getOrder(){return "asc";};
		}).getRows();
	}
	/**
	 * 加载列表页面数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_SOURCE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SourceInfo> datagrid(SourceInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面数据...");
		return this.sourceService.datagrid(info);
	}
	/**
	 * 编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_SOURCE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		return "library/source_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_SOURCE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(SourceInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.sourceService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_SOURCE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.sourceService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}