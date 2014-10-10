package com.examw.test.service.library;

import com.examw.test.domain.library.Paper;
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
	 *  加载试卷基本数据。
	 * @param paperId
	 * @return
	 */
	Paper loadPaper(String paperId);
	/**
	 * 数据模型转换。
	 * @param paper
	 * @return
	 */
	PaperInfo conversion(Paper paper);
}