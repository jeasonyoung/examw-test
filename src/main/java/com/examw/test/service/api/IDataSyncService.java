package com.examw.test.service.api;

import java.util.List;

import com.examw.test.model.api.AppClientPush;
import com.examw.test.model.api.AppClientSync;
import com.examw.test.model.api.CategorySync;
import com.examw.test.model.api.ExamSync;
import com.examw.test.model.api.FavoriteSync;
import com.examw.test.model.api.PaperItemRecordSync;
import com.examw.test.model.api.PaperRecordSync;
import com.examw.test.model.api.PaperSync;
/**
 * 数据同步服务接口。
 * 
 * @author yangyong
 * @since 2015年2月27日
 */
public interface IDataSyncService {
	/**
	 * 下载考试类别数据。
	 * @param req
	 * @return
	 * @throws Exception
	 */
	List<CategorySync> downloadCategories() throws Exception;
	/**
	 * 同步考试科目数据
	 * @param req
	 * @return
	 * @throws Exception
	 */
	ExamSync syncExams(AppClientSync req) throws Exception;
	/**
	 * 同步试卷数据。
	 * @param req
	 * @return
	 * @throws Exception
	 */
	List<PaperSync> syncPapers(AppClientSync req) throws Exception;
	/**
	 * 自动缓存产品试卷。
	 */
	void updateAutoProductPapersCache();
	/**
	 * 同步试卷记录数据。
	 * @param req
	 * @return
	 */
	List<String> syncPaperRecords(AppClientPush<PaperRecordSync> req) throws Exception;
	/**
	 * 同步试题记录数据。
	 * @param req
	 * @return
	 */
	List<String> syncPaperItemRecords(AppClientPush<PaperItemRecordSync> req) throws Exception;
	/**
	 * 同步收藏记录数据。
	 * @param req
	 * @return
	 */
	List<String> syncFavorites(AppClientPush<FavoriteSync> req) throws Exception;
}