package com.examw.test.service.library;

import com.examw.test.model.library.PaperInfo; 
import com.examw.test.service.IBaseDataService;
/**
 * 试卷服务接口。
 * @author yangyong.
 * @since 2014-08-06.
 */
public interface IPaperService extends IBaseDataService<PaperInfo> {
	/**
	 * 获取试卷类型名称。
	 * @param type
	 * 试卷类型值。
	 * @return
	 * 试卷类型名称。
	 */
	String loadTypeName(Integer type);
	/**
	 * 获取试卷状态名称。
	 * @param status
	 * 试卷状态值。
	 * @return
	 * 试卷状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 更新试卷状态。
	 * @param paperId
	 * 试卷ID。
	 * @param status
	 * 试卷状态。
	 */
	void updateStatus(String paperId,PaperStatus status);
	/**
	 *  加载试卷基本信息。
	 * @param paperId
	 * @return
	 */
	PaperInfo loadPaperInfo(String paperId);
//	/**
//	 * 加载试卷基本信息[前台调用方法]
//	 * @param paperId
//	 * @return
//	 */
//	PaperPreview loadPaperInfo(String paperId);
//	/**
//	 * 加载试卷详细信息并且添加试卷记录[前台调用方法]
//	 * @param paperId	试卷ID
//	 * @param userId 	用户ID
//	 * @return
//	 */
//	PaperPreview loadPaperPreviewAndAddRecord(String paperId,String userId);
//	/**
//	 * 加载试卷数据信息[前台调用方法]
//	 * @param info
//	 * @return
//	 */
//	List<PaperFrontInfo> loadPaperFrontInfo(PaperInfo info,String userId);
//	/**
//	 * 查询统计[前台调用方法]
//	 * @param info
//	 * @return
//	 */
//	Long totalPaperFrontInfo(PaperInfo info);
//	/**
//	 * 试卷提交答案	[前台调用方法]
//	 * @param limitTime			剩余时间[秒]
//	 * @param chooseAnswers		选择题答案
//	 * @param textAnswers		文字题答案
//	 * @param model				提交模式
//	 * @param paperId			试卷ID
//	 * @param userId			用户ID
//	 * @return
//	 */
//	Json submitPaper(Integer limitTime, String chooseAnswers,
//			String textAnswers,Integer model, String paperId, String userId);
}