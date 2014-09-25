package com.examw.test.controllers.library;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.domain.security.Right;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperPreviewService;
import com.examw.test.service.library.ItemJudgeAnswer;

/**
 * 试卷阅览控制器。
 * 
 * @author yangyong
 * @since 2014年9月23日
 */
@Controller
@RequestMapping(value = "/library/paper/preview")
public class PaperPreviewController {
	private static Logger logger = Logger.getLogger(PaperPreviewController.class);
	//注入试题服务接口。
	@Resource
	private IItemService itemService;
	//注入试卷预览服务接口。
	@Resource
	private IPaperPreviewService paperPreviewService;
	/**
	 * 试卷预览。
	 * @param paperId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/{paperId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String paperPreview(@PathVariable String paperId, Model model){
		if(logger.isDebugEnabled()) logger.debug("预览试卷:" + paperId);
		model.addAttribute("ItemJudgeAnswer_Right_Value", ItemJudgeAnswer.RIGTH.getValue());
		model.addAttribute("ItemJudgeAnswer_Right_Name", this.itemService.loadJudgeAnswerName(ItemJudgeAnswer.RIGTH.getValue()));

		model.addAttribute("ItemJudgeAnswer_Wrong_Value", ItemJudgeAnswer.WRONG.getValue());
		model.addAttribute("ItemJudgeAnswer_Wrong_Name", this.itemService.loadJudgeAnswerName(ItemJudgeAnswer.WRONG.getValue()));
		
		model.addAttribute("paper",  this.paperPreviewService.loadPaperPreview(paperId));
		return "library/paper_preview";
	}
}