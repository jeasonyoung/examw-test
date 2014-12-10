package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.model.library.PaperInfo;

/**
 * 试卷数据接口。
 * @author yangyong.
 * @since 2014-08-06.
 */
public interface IPaperDao extends IBaseDao<Paper> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Paper> findPapers(PaperInfo info,Integer[] paperTypes);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	Long total(PaperInfo info,Integer[] paperTypes);
	/**
	 * 加载已审核的全部试卷总数。
	 * @return
	 */
	Long loadAllAuditCount();
	/**
	 * 加载已审核的全部试卷集合。
	 * @return 已审核的全部试卷集合。
	 */
	List<Paper> loadAllAudit(Integer count);
	/**
	 * 统计科目地区下的试卷总数。
	 * @param subjectIds
	 * 科目ID集合。
	 * @param areaId
	 * 所属地区ID。
	 * @return
	 * 试卷总数。
	 */
	Integer totalPapers(String[] subjectIds,String areaId);
}