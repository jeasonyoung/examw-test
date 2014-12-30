package com.examw.test.controllers.api;


import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.model.library.ItemErrorRecorveryInfo;
import com.examw.test.model.products.FrontUserInfo;
import com.examw.test.model.records.UserItemFavoriteInfo;
import com.examw.test.model.records.UserItemRecordInfo;
import com.examw.test.model.records.UserPaperRecordInfo;
import com.examw.test.model.settings.FrontSubjectInfo;
import com.examw.test.service.library.IFrontPaperService;
import com.examw.test.service.library.IItemErrorRecorveryService;
import com.examw.test.service.products.IProductUserService;
import com.examw.test.service.records.IUserItemFavoriteService;
import com.examw.test.service.records.IUserItemRecordService;
import com.examw.test.service.records.IUserPaperRecordService;

/**
 * 前端用户数据接口。
 * @author fengwei.
 * @since 2014年9月5日 上午10:41:23.
 */
@Controller
@RequestMapping(value = { "/api/data/user" })
public class FrontDataController {
	private static final Logger logger = Logger.getLogger(FrontDataController.class);
	//用户试卷记录服务。
	@Resource
	private IUserPaperRecordService userPaperRecordService;
	//用户试题记录服务。
	@Resource
	private IUserItemRecordService userItemRecordService;
	//用户收藏记录服务。
	@Resource
	private IUserItemFavoriteService userItemFavoriteService;
	//产品用户服务。
	@Resource
	private IProductUserService productUserService;
	@Resource
	private IFrontPaperService frontPaperService;
	@Resource
	private IItemErrorRecorveryService itemErrorRecorveryServcie;
	private ObjectMapper mapper;
	/**
	 * 构造函数。
	 */
	public FrontDataController() {
		this.mapper = new ObjectMapper();
	}
	/**
	 * 验证前端用户。
	 * @param info
	 * 前端用户信息。
	 * @return
	 * 验证结果。
	 */
	@RequestMapping(value = {""}, method = { RequestMethod.POST })
	@ResponseBody
	public Json verfiyUser(@RequestBody FrontUserInfo info){
		if(logger.isDebugEnabled()) logger.debug("验证前端用户...");
		Json result = new Json();
		try {
			result.setData(this.productUserService.verifyFrontUser(info).getId());
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("验证前端用户生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 添加用户试卷记录。
	 * @param info
	 * 用户试卷记录信息。
	 * @return
	 */
	@RequestMapping(value = {"/paper/record/add"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json addPaperRecord(@RequestBody UserPaperRecordInfo info){
		if(logger.isDebugEnabled()) logger.debug("添加用户试卷记录...");
		Json result = new Json();
		try {
			result.setData(this.userPaperRecordService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("添加用户试卷记录发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 添加用户试题记录。
	 * @param paperRecordId
	 * 所属试卷记录ID。
	 * @param data
	 * 试题记录集合。
	 * @return
	 */
	@RequestMapping(value = {"/item/{paperRecordId}/record/add"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json addItemRecord(@PathVariable String paperRecordId, @RequestBody List<UserItemRecordInfo> data){
		if(logger.isDebugEnabled()) logger.debug(String.format("添加用户试题记录［试卷记录ID paperRecordId = %s ］...", paperRecordId));
		Json result = new Json();
		try {
			this.userItemRecordService.addItemRecord(paperRecordId, data);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("添加用户试题记录发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 添加用户试题收藏。
	 * @param userId
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/favorite/add"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json addItemFavorite(@PathVariable String userId,@RequestBody UserItemFavoriteInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("添加用户［userId = %s］试题收藏..", userId));
		Json result = new Json();
		try {
			info.setUserId(userId);
			return this.userItemFavoriteService.favorOrCancel(info);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("添加用户试题收藏发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 添加用户试题收藏。
	 * @param userId
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/itemerrorrecorvery/add"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json addItemErrorRecorvery(@PathVariable String userId,@RequestBody ItemErrorRecorveryInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("添加用户［userId = %s］试题纠错信息..", userId));
		Json result = new Json();
		try {
			info.setUserId(userId);
			this.itemErrorRecorveryServcie.update(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("添加用户试题纠错发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 获取试卷最高得分。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 */
	@RequestMapping(value = {"/paper/{paperId}/score"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadPaperMaxScore(@PathVariable String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("获取试卷［paperId = %s］最高得分...", paperId));
		Json result = new Json();
		try {
			result.setData(this.userPaperRecordService.findMaxScore(paperId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("获取试卷最高得分发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 获取试卷参考人次。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 */
	@RequestMapping(value = {"/paper/{paperId}/total"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadPaperUsersTotal(String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("获取试卷［paperId = %s］参考人次...", paperId));
		Json result = new Json();
		try {
			result.setData(this.userPaperRecordService.findUsersTotal(paperId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("获取试卷参考人次发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 加载某产品下用户试卷最新记录数据。[Add by FW 2014.10.12]
	 * @param userId
	 * 用户ID。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/paper/{productId}/records"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<UserPaperRecordInfo> loadUserPaperRecords(@PathVariable String userId,@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］记录数据...", userId,productId));
		try {
			return this.userPaperRecordService.findLastedPaperRecordsOfProduct(userId, productId);
		} catch (Exception e) {
			logger.error("加载用户试卷记录数据发生异常：" + e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 加载用户试卷记录数据。
	 * @param userId
	 * 用户ID。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/paper/{paperId}/record"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadUserPaperRecord(@PathVariable String userId,@PathVariable String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］记录数据...", userId,paperId));
		Json result = new Json();
		try {
			result.setData(this.mapper.writeValueAsString(this.userPaperRecordService.load(userId, paperId)));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("加载用户试卷记录数据发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 加载用户当天剩余没有练习的每日一练个数
	 * @param userId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/paper/{productId}/dailyrest"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadLeftNumUserDailyPaper(@PathVariable String userId,@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］产品［productId = %2$s］今日一练未练个数数据...", userId,productId));
		Json result = new Json();
		try {
			result.setData((this.frontPaperService.loadResidueUserDailyPaperNumber(userId, productId)));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("加载用户试卷记录数据发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	@RequestMapping(value = {"/{userId}/paper/record/{recordId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadUserPaperRecordById(@PathVariable String userId,@PathVariable String recordId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷记录［record = %2$s］记录数据...", userId,recordId));
		Json result = new Json();
		try {
			UserPaperRecordInfo info = this.userPaperRecordService.load(recordId);
			if(info != null)
				result.setData(this.mapper.writeValueAsString(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("加载用户试卷记录数据发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 加载用户试卷最新试题记录数据。
	 * @param userId
	 * 所属用户ID。
	 * @param paperId
	 * 所属试卷ID
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/paper/{paperId}/record/item/last"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadUserPaperItemLastRecord(@PathVariable String userId,@PathVariable String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］最新试题记录数据...", userId,paperId));
		Json result = new Json();
		try {
			result.setData(this.userItemRecordService.loadUserPaperLastRecord(userId, paperId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("加载用户最新试题记录数据发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 加载用户试题最新记录数据。
	 * @param userId
	 * 所属用户ID。
	 * @param paperId
	 * 所属试卷ID
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/paper/{paperId}/item/{itemId}/record/last"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadUserPaperItemLastRecord(@PathVariable String userId,@PathVariable String paperId,@PathVariable String itemId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］试题［itemId = %3$s］最新记录数据...", userId,paperId,itemId));
		Json result = new Json();
		try {
			result.setData(this.userItemRecordService.loadUserPaperLastRecord(userId, paperId, itemId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("加载用户试题最新记录数据发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 加载用户错题集
	 * @param userId
	 * 所属用户ID
	 * @param paperId
	 * 试卷ID
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/errors"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<UserItemRecordInfo>  loadUserErrorItems(@PathVariable String userId, String paperId,String subjectId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］［paperId = %2$s］错题集合...", userId,paperId));
		return this.userItemRecordService.loadUserErrorItems(userId, paperId,subjectId);
	}
	/**
	 * 判断用户是否收藏试题。
	 * @param userId
	 * 所属用户ID。
	 * @param itemId
	 * 所属试题ID。
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/favorite/{itemId}/exists"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json existsItemFavorite(@PathVariable String userId,@PathVariable String itemId){
		if(logger.isDebugEnabled()) logger.debug(String.format("判断用户［userId = %1$s］是否收藏试题［itemId = %2$s］...", userId,itemId));
		Json result = new Json();
		try {
			result.setData(this.userItemFavoriteService.exists(userId, itemId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("判断用户是否收藏试题发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 加载试题被收藏的次数。
	 * @param itemId
	 * 所属试题ID。
	 * @return
	 */
	@RequestMapping(value = {"/favorite/{itemId}/total"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadItemFavoriteTotal(@PathVariable String itemId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题［itemId = %s］被收藏的次数...", itemId));
		Json result = new Json();
		try {
			result.setData(this.userItemFavoriteService.totalItemFavorites(itemId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("加载试题被收藏的次数发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 加载用户收藏试题总数。
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/favorite/total"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadUserFavoriteTotal(@PathVariable String userId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %s］收藏试题总数...", userId));
		Json result = new Json();
		try {
			result.setData(this.userItemFavoriteService.totalUserFavorites(userId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("加载用户收藏试题总数发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 加载用户收藏试题集合。
	 * @param userId
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"/{userId}/favorites"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Json loadItemFavorites(@PathVariable String userId,UserItemFavoriteInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %s］收藏试题集合...", userId));
		Json result = new Json();
		try {
			info.setUserId(userId);
			result.setData(this.userItemFavoriteService.datagrid(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("加载用户收藏试题集合发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	@RequestMapping(value = {"/{userId}/favorite/list"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<UserItemFavoriteInfo> loadItemFavoriteList(@PathVariable String userId,UserItemFavoriteInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %s］收藏试题集合...", userId));
		try {
			return this.userItemFavoriteService.datagrid(info).getRows();
		} catch (Exception e) {
			logger.error("加载用户收藏试题集合发生异常：" + e.getMessage(), e);
		}
		return null;
	}
	@RequestMapping(value = {"/{productId}/{userId}/favorite/subjects"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<FrontSubjectInfo> loadItemFavoriteList(@PathVariable String productId,@PathVariable String userId,UserItemFavoriteInfo info){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %s］收藏试题集合...", userId));
		try {
			return this.userItemFavoriteService.loadProductFrontSubjects(productId, userId);
		} catch (Exception e) {
			logger.error("加载用户收藏试题集合发生异常：" + e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 删除试题收藏。
	 * @param favoriteId
	 * 试题收藏ID。
	 * @return
	 */
	@RequestMapping(value = {"/favorite/{favoriteId}/delete"}, method = {RequestMethod.GET, RequestMethod.DELETE})
	public Json deleteItemFavorite(@PathVariable String favoriteId){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试题收藏［favoriteId = %s］...", favoriteId));
		Json result = new Json();
		try {
			this.userItemFavoriteService.delete(new String[]{favoriteId});
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除试题收藏发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	@RequestMapping(value = {"/{productId}/{userId}/{terminalId}/record/lasttime"}, method = {RequestMethod.GET})
	@ResponseBody
	public Json loadLastTimeRecord(@PathVariable String productId,@PathVariable String userId,@PathVariable Integer terminalId)
	{
		if(logger.isDebugEnabled()) logger.debug(String.format("查询用户[%s]考试记录的最新时间", userId));
		Json result = new Json();
		try {
			result.setData(this.userPaperRecordService.loadRecordLastTime(productId,userId,terminalId));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除试题收藏发生异常：" + e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 上传用户试卷记录。
	 * @param info
	 * 用户试卷记录信息。
	 * @return
	 */
	@RequestMapping(value = {"/records/add"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json addPaperRecords(@RequestBody UserPaperRecordInfo[] records){
		if(logger.isDebugEnabled()) logger.debug("添加用户试卷记录...");
		Json result = new Json();
		try {
			this.userPaperRecordService.updateRecords(records);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("添加用户试卷记录发生异常：" + e.getMessage(), e);
		}
		return result;
	}
}