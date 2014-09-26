package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.PaperRelease;

/**
 * 试卷发布数据接口。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public interface IPaperReleaseDao extends IBaseDao<PaperRelease> {
	/**
	 * 按科目或地区加载已发布的试卷集合。
	 * @param subjectsId
	 * 科目ID集合。
	 * @param areasId
	 * 地区ID集合
	 * @return
	 */
	List<PaperRelease> loadReleases(String[] subjectsId, String[] areasId);
	/**
	 * 试卷是否已发布。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 */
	boolean hasRelease(String paperId);
	/**
	 * 删除试卷发布。
	 * @param paperId
	 * 试卷ID。
	 */
	void deleteRelease(String paperId);
	/**
	 * 加载科目下的试卷数量。
	 * @param subjetsId
	 * 科目ID集合。
	 * @return
	 * 试卷数量。
	 */
	Integer loadPapersCount(String[] subjetsId);
	/**
	 * 加载科目下试题数量。
	 * @param subjectsId
	 * 科目ID集合。
	 * @return
	 * 试题数量。
	 */
	Integer loadItemsCount(String[] subjectsId);
	/**
	 * 是否包含真题试卷。
	 * @param subjectsId
	 * 科目ID集合。
	 * @return
	 */
	boolean hasRealItem(String[] subjectsId);
}