package com.examw.test.service.records;

import java.util.List;

import com.examw.test.model.records.PaperRecordInfo;

/**
 * 试卷记录服务接口
 * @author fengwei.
 * @since 2014年9月18日 上午10:32:35.
 */
public interface IPaperRecordService{
	/**
	 * 查找试卷记录
	 * @param info
	 * @return
	 */
	List<PaperRecordInfo> findPaperRecords(PaperRecordInfo info);
}
