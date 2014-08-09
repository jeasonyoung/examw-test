package com.examw.test.controllers.library;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.PaperStatus;

/**
 * 试卷控制器。
 * 
 * @author yangyong
 * @since 2014年8月8日
 */
@Controller
@RequestMapping(value = "/library/paper")
public class PaperController {
	private static final Logger logger = Logger.getLogger(PaperController.class);
	//注入试卷服务。
	@Resource
	private IPaperService paperService;
	/**
	 * 列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_PAPER + ":" + Right.DELETE);
		
		model.addAttribute("PAPER_STATUS_NONE", PaperStatus.NONE.getValue());
		
		return "library/paper_list";
	}
	/**
	 * 加载列表页面数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_SOURCE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PaperInfo> datagrid(PaperInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面数据...");
		return this.paperService.datagrid(info);
	}
	/**
	 * 加载全部数据。
	 * @param examId
	 * @param subjectId
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<PaperInfo> all(final String examId,final String subjectId){
		return this.paperService.datagrid(new PaperInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getExamId(){return examId;}
			@Override
			public String getSubjectId(){return subjectId;}
			@Override
			public String getSort(){return "name";}
			@Override
			public String getOrder(){return "asc";}
		}).getRows();
	}
	/**
	 * 加载编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String examId,String subjectId,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId.trim());
		model.addAttribute("CURRENT_SUBJECT_ID", StringUtils.isEmpty(subjectId) ? "" : subjectId.trim());
		
		model.addAttribute("CURRENT_YEAR", new SimpleDateFormat("yyyy").format(new Date()));
		
		model.addAttribute("TYPE_REAL_VALUE", Paper.TYPE_REAL);
		model.addAttribute("TYPE_REAL_NAME", this.paperService.loadTypeName(Paper.TYPE_REAL));
		model.addAttribute("TYPE_SIMU_VALUE", Paper.TYPE_SIMU);
		model.addAttribute("TYPE_SIMU_NAME", this.paperService.loadTypeName(Paper.TYPE_SIMU));
		model.addAttribute("TYPE_FORECAST_VALUE", Paper.TYPE_FORECAST);
		model.addAttribute("TYPE_FORECAST_NAME", this.paperService.loadTypeName(Paper.TYPE_FORECAST));
		model.addAttribute("TYPE_PRACTICE_VALUE", Paper.TYPE_PRACTICE);
		model.addAttribute("TYPE_PRACTICE_NAME", this.paperService.loadTypeName(Paper.TYPE_PRACTICE));
		
		return "library/paper_edit";
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(PaperInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.paperService.update(info));
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
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.paperService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}