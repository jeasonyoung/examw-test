package com.examw.test.service.library;

import java.util.List;
import java.util.Map;

import com.examw.test.model.library.FrontPaperInfo;
import com.examw.test.model.library.PaperPreview;
/**
 * 前端试卷服务接口。
 * 
 * @author yangyong
 * @since 2014年9月23日
 */
public interface IFrontPaperService {
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
	/**
	 * 加载产品试卷。
	 * @param productId
	 * 产品ID。
	 * @return
	 * 试卷集合。
	 */
	List<FrontPaperInfo> loadProductPapers(String productId);
	/**
	 * 加载每日一练试卷集合。
	 * @param subjectId
	 * 所属科目。
	 * @param areaId
	 * 所属地区。
	 * @param page
	 * 当前页码。
	 * @param rows
	 * @return
	 */
	List<FrontPaperInfo> loadDailyPapers(String productId,Integer page,Integer rows);
	/**
	 * 加载试卷内容。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 */
	PaperPreview loadPaperContent(String paperId) throws Exception;
	/**
	 * 加载试卷的类型[add by FW 2014.09.28]
	 * @return
	 */
	Map<String,String> loadPaperType();
	/**
	 * 加载今日一练剩余的记录
	 * @param userId
	 * @param productId
	 * @return
	 */
	Long loadResidueUserDailyPaperNumber(String userId,String productId);
}