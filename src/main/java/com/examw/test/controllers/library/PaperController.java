package com.examw.test.controllers.library;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.examw.aware.IUserAware;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.PaperStatus;
import com.examw.test.service.library.PaperType;
import com.examw.test.support.PaperItemUtils;
/**
 * 试卷控制器。
 * 
 * @author yangyong
 * @since 2014年8月8日
 */
@Controller
@RequestMapping(value = "/library/paper")
public class PaperController implements IUserAware {
	private static final Logger logger = Logger.getLogger(PaperController.class);
	private String currentUserId = null,currentUserName = null;
	//注入试卷服务。
	@Resource
	private IPaperService paperService;
	//注入题目服务。
	@Resource
	private IItemService itemService;
	/*
	 * 注入当前用户ID。
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		if(logger.isDebugEnabled())logger.debug(String.format("注入当前用户ID =  %s", userId));
		this.currentUserId = userId;
	}
	/*
	 * 注入当前用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入当前用户名称 = %s", userName));
		this.currentUserName = userName;
	}
	/*
	 * 注入当前用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {}
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
		
		model.addAttribute("paper_status_none_value", PaperStatus.NONE.getValue());
		
		return "library/paper_list";
	}
	/**
	 * 每日一练列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value={"/daily","/daily/list"}, method = RequestMethod.GET)
	public String dailyList(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载每日一练列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.LIBRARY_PAPER + ":" + Right.DELETE);
		model.addAttribute("TYPE_DAILY", PaperType.DAILY.getValue());
		model.addAttribute("paper_status_none_value", PaperStatus.NONE.getValue());
		
		return "library/paper_list_daily";
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
		
		model.addAttribute("current_exam_id", examId);
		model.addAttribute("current_subject_id", subjectId);
		model.addAttribute("current_year", new SimpleDateFormat("yyyy").format(new Date()));
		
		PaperItemUtils.addPaperType(this.paperService, model);
		
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
			info.setUserId(this.currentUserId);
			info.setUserName(this.currentUserName); 
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
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据［%s］...", id));
		Json result = new Json();
		try {
			this.paperService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据[%s]时发生异常:",id), e);
		}
		return result;
	}
	/**
	 * 试卷审核。
	 * @param paperId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/audit/{paperId}", method = RequestMethod.GET)
	@ResponseBody
	public Json audit(@PathVariable String paperId){
		if(logger.isDebugEnabled()) logger.debug("审核试卷［"+ paperId +"］...");
		Json result = new Json();
		try {
			this.paperService.updateStatus(paperId, PaperStatus.AUDIT,true);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("审核试卷数据["+paperId+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 试卷反审核。
	 * @param paperId。
	 * 试卷ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/unaudit/{paperId}", method = RequestMethod.GET)
	@ResponseBody
	public Json unAudit(@PathVariable String paperId){
		if(logger.isDebugEnabled()) logger.debug("反审核试卷［"+ paperId +"］...");
		Json result = new Json();
		try {
			this.paperService.updateStatus(paperId, PaperStatus.NONE,true);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("反审核试卷数据["+paperId+"]时发生异常:", e);
		}
		return result;
	}
	
	/**
	 * 直接修改试卷的状态
	 * @param paperId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.UPDATE})
	@RequestMapping(value="/changeStatus/{paperId}", method = RequestMethod.POST)
	@ResponseBody
	public Json changeStatus(@PathVariable String paperId,Integer status){
		if(logger.isDebugEnabled()) logger.debug("修改试卷状态［"+ paperId +"］...");
		Json result = new Json();
		try {
			this.paperService.updateStatus(paperId, PaperStatus.convert(status),false);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("审核试卷数据["+paperId+"]时发生异常:", e);
		}
		return result;
	}
}