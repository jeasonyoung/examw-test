package com.examw.test.service.api;

import com.examw.test.model.api.AppClientSync;
import com.examw.test.model.api.ExamSync;
/**
 * 数据同步服务接口。
 * 
 * @author yangyong
 * @since 2015年2月27日
 */
public interface IDataSyncService {
	/**
	 * 同步考试科目数据。
	 * @param req
	 * @return
	 */
	ExamSync syncExams(AppClientSync req) throws Exception;
}