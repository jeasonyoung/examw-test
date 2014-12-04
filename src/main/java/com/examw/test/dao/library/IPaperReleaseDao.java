package com.examw.test.dao.library;

import java.util.Date;
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
	 * 按试卷类型科目地区加载已发布的试卷集合。
	 * @param paperTypes
	 * 试卷类型。
	 * @param subjectsId
	 * 科目ID集合。
	 * @param areasId
	 * 地区ID集合
	 * @param createTime
	 * 生成时间 [时间范围查询]
	 * @param page
	 * 页码。
	 * @param rows
	 * 行数。
	 * @return
	 */
	List<PaperRelease> loadReleases(Integer[] paperTypes,String[] subjectsId, String[] areasId,Date createTime,Integer page,Integer rows);
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
	 * 清理发布。
	 */
	void clearRelease();
	/**
	 * 加载科目下的试卷数量。
	 * @param paperType
	 * 试卷类型。
	 * @param subjetsId
	 * 科目ID集合。
	 * @return
	 * 试卷数量。
	 */
	Integer loadPapersCount(Integer[] paperType,String[] subjetsId,String areaId);
	/**
	 * 加载科目下试题数量。
	 * @param paperType
	 * 试卷类型。
	 * @param subjectsId
	 * 科目ID集合。
	 * @return
	 * 试题数量。
	 */
	Integer loadItemsCount(Integer[] paperType,String[] subjectsId,String areaId);
	/**
	 * 是否包含真题试卷。
	 * @param subjectsId
	 * 科目ID集合。
	 * @return
	 */
	boolean hasRealItem(String[] subjectsId);
}