package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.ItemType;
/**
 * 题目数据操作接口。
 * 
 * @author yangyong
 * @since 2014年8月8日
 */
public interface IItemDao extends IBaseDao<Item> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<Item> findItems(ItemInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(ItemInfo info);
	/**
	 * 根据校验码加载试题。
	 * @param checkCode
	 * 校验码。
	 * @return
	 * 题目对象。
	 */
	Item loadItemByCode(String checkCode);
	/**
	 * 加载试题。
	 * @param itemId
	 * 试题ID。
	 * @return
	 */
	Item loadItem(String itemId);
	/**
	 * 查询是否包含真题
	 * @param subjectIds
	 * @return
	 */
	boolean hasRealItem(String[] subjectIds);
	/**
	 * 加载科目题型下的试题集合。
	 * @param subject
	 * 所属科目。
	 * @param itemType
	 * 所属题型。
	 * @param area
	 * 所属地区。
	 * @return
	 * 试题集合。
	 */
	List<Item> loadItems(String[] subjectId, ItemType itemType,Area area);
	/**
	 * 加载科目下的题型集合。
	 * @param subject
	 * 所属科目。
	 * @return
	 * 题型集合。
	 */
	List<ItemType> loadItemTypes(Subject subject);
	/**
	 * 加载与试卷无关联的试题数据集合。
	 */
	List<Item> loadIsolatedItems();
	/**
	 * 统计科目地区下的试题总数。
	 * @param subjectIds
	 * 科目ID集合。
	 * @param areaId
	 * 所属地区ID。
	 * @return
	 * 试题总数。
	 */
	Integer totalItems(String[] subjectIds,String areaId);
	
	/**
	 * 根据ID查询试题
	 * @param itemId
	 * @return
	 * 2015.03.05
	 */
	List<Item> findItems(String[] itemId);
}