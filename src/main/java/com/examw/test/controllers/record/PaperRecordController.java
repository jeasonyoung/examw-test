package com.examw.test.controllers.record;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.test.domain.security.Right;
import com.examw.test.model.records.UserPaperRecordInfo;
import com.examw.test.service.records.IUserPaperRecordService;
import com.examw.test.service.records.impl.PaperRecordStatus;

/**
 * 用户考试记录控制器
 * @author fengwei.
 * @since 2014年11月4日 上午10:37:55.
 */
@Controller
@RequestMapping("/record/paper")
public class PaperRecordController {
	private static final Logger logger = Logger.getLogger(PaperRecordController.class);
	//考试记录服务接口。
	@Resource
	private IUserPaperRecordService userPaperRecordService;
	
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPER_RECORD + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载考试记录列表页面...");
		model.addAttribute("STATUS_DONE", PaperRecordStatus.DONE.getValue());
		model.addAttribute("STATUS_UNDONE", PaperRecordStatus.UNDONE.getValue());
		return "records/paper_record_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.PAPER_RECORD + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<UserPaperRecordInfo> datagrid(UserPaperRecordInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.userPaperRecordService.datagrid(info);
	}
}
