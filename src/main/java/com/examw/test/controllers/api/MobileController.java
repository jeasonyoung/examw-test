package com.examw.test.controllers.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.model.api.AppClientPush;
import com.examw.test.model.api.AppClientSync;
import com.examw.test.model.api.AppRegister;
import com.examw.test.model.api.FavoriteSync;
import com.examw.test.model.api.LoginUser;
import com.examw.test.model.api.PaperItemRecordSync;
import com.examw.test.model.api.PaperRecordSync;
import com.examw.test.model.api.RegisterUser;
import com.examw.test.service.api.IDataSyncService;
import com.examw.test.service.api.IHostAccessProxyService;
import com.examw.test.service.api.IHostRegisterService;

/**
 * 移动接口控制器。
 * 
 * @author yangyong
 * @since 2015年2月4日
 */
@Controller
@RequestMapping(value = { "/api/m" })
public class MobileController {
	private static final Logger logger = Logger.getLogger(MobileController.class);
	@Resource//注入访问中华考试网代理服务接口。
	private IHostAccessProxyService  hostAccessProxyService;
	@Resource//注入注册码服务接口
	private IHostRegisterService hostRegisterService;
	@Resource//注入数据同步服务接口
	private IDataSyncService dataSyncService;
	/**
	 * 注册新用户
	 * @param info
	 * 注册新用户信息。
	 * @return 反馈数据。
	 */
	@RequestMapping(value = {"/user/register"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json userRegister(@RequestBody RegisterUser info){
		if(logger.isDebugEnabled()) logger.debug("注册新用户...");
		Json  result = new Json();
		try {
			if(info != null){
				this.hostAccessProxyService.registerUser(info);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 用户登录。
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"/user/login"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json userLogin(@RequestBody LoginUser info){
		if(logger.isDebugEnabled()) logger.debug("用户登录...");
		Json  result = new Json();
		try {
			if(info != null){
				result.setData(this.hostAccessProxyService.login(info));
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 应用注册。
	 * @param register
	 * 注册信息。
	 * @return 应用注册结果。
	 */
	@RequestMapping(value = {"/app/register"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json appRegister(@RequestBody AppRegister register){
		if(logger.isDebugEnabled()) logger.debug("应用注册...");
		Json result = new Json();
		try {
			if(logger.isDebugEnabled()) logger.debug(String.format( "AppRegister:%s",register));
			result.setSuccess(this.hostRegisterService.verifyAppRegister(register));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 同步考试分类集合
	 * @param examCode
	 * @return
	 */
	@RequestMapping(value = {"/download/categories"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json downloadCategories(){
		if(logger.isDebugEnabled()) logger.debug("同步考试分类集合...");
		Json result = new Json();
		try {
			result.setData(this.dataSyncService.downloadCategories());
			result.setSuccess(true);
		} catch (Exception e) {
			 result.setSuccess(false);
			 result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 同步考试科目数据。
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"/sync/exams"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json syncExam(@RequestBody AppClientSync req){
		if(logger.isDebugEnabled()) logger.debug("同步考试科目数据...");
		Json result = new Json();
		try {
			result.setData(this.dataSyncService.syncExams(req));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 同步试卷数据。
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"/sync/papers"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json syncPapers(@RequestBody AppClientSync req){
		if(logger.isDebugEnabled()) logger.debug("同步试卷数据...");
		Json result = new Json();
		try {
			result.setData(this.dataSyncService.syncPapers(req));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 同步试卷记录数据。
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"/sync/records/papers"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json syncPaperRecords(@RequestBody AppClientPush<PaperRecordSync> req){
		if(logger.isDebugEnabled()) logger.debug("同步试卷记录数据...");
		Json result = new Json();
		try {
			result.setData(this.dataSyncService.syncPaperRecords(req));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 同步试题记录数据。
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"/sync/records/items"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json syncPaperItemRecords(@RequestBody AppClientPush<PaperItemRecordSync> req){
		if(logger.isDebugEnabled()) logger.debug("同步试题记录数据...");
		Json result = new Json();
		try {
			result.setData(this.dataSyncService.syncPaperItemRecords(req));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 同步收藏夹数据。
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"/sync/favorites"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json syncFavorites(@RequestBody AppClientPush<FavoriteSync> req){
		if(logger.isDebugEnabled()) logger.debug("同步收藏夹数据...");
		Json result = new Json();
		try {
			result.setData(this.dataSyncService.syncFavorites(req));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
}