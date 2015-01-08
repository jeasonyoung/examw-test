package com.examw.test.controllers.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.model.publish.StaticPageInfo;
import com.examw.test.service.publish.IStaticPageService;

/**
 * 发布API控制器。
 * 
 * @author yangyong
 * @since 2015年1月8日
 */
@Controller
@RequestMapping(value = { "/api/publish" })
public class PublishController {
	private static final Logger logger = Logger.getLogger(PublishController.class);
	//注入静态页面服务接口。
	@Resource
	private IStaticPageService staticPageService;
	/**
	 * 加载静态页面总数。
	 * @return 静态页面总数。
	 */
	@RequestMapping(value = {"/total"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Json loadTotal(){
		if(logger.isDebugEnabled()) logger.debug("加载静态页面总数...");
		Json json = new Json();
		try {
			json.setData(this.staticPageService.loadTotal(new StaticPageInfo()));
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}
	/**
	 * 加载静态页面数据。
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"/pages"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Json loadPages(StaticPageInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载静态页面数据...");
		Json json = new Json();
		try {
			if(info.getPage() == null) info.setPage(0);
			if(info.getRows() == null) info.setRows(0);
			if(StringUtils.isEmpty(info.getSort())){
				info.setSort("createTime");
				info.setOrder("desc");
			}
			json.setData(this.staticPageService.loadPages(info));
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}
	/**
	 * 加载静态页面内容。
	 * @param pageId
	 * 静态页面ID。
	 * @return
	 */
	@RequestMapping(value = {"/pages/{pageId}"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Json loadPageContent(@PathVariable String pageId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载静态页面［%s］内容...", pageId));
		Json json = new Json();
		try {
			json.setData(this.staticPageService.loadPageContent(pageId));
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}
}