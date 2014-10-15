package com.examw.test.controllers.syllabus;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.examw.model.Json;
import com.examw.test.domain.security.Right;
import com.examw.test.service.syllabus.IChapterKnowledgeService;
/**
 * 知识点控制器。
 * @author lq.
 * @since 2014-8-06.
 */
@Controller
@RequestMapping(value = "/syllabus/knowledge")
public class KnowledegeController {
	private static final Logger logger = Logger.getLogger(KnowledegeController.class);
	//知识点服务
	@Resource
	private IChapterKnowledgeService knowService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_KNOWLEDGE+ ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE",ModuleConstant.SYLLABUS_KNOWLEDGE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE",ModuleConstant.SYLLABUS_KNOWLEDGE + ":" + Right.DELETE);
		return "syllabus/know_list";
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SYLLABUS_KNOWLEDGE + ":" + Right.VIEW})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.knowService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据知识点["+id+"]时发生异常:", e);
		}
		return result;
	}
}