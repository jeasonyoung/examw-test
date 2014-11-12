package com.examw.test.service.library.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.dao.syllabus.ISyllabusDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.BaseSyllabusInfo;
import com.examw.test.service.library.IItemSyllabusService;

/**
 * 试题大纲关联服务接口实现类
 * @author fengwei.
 * @since 2014年11月8日 上午11:33:08.
 */
public class ItemSyllabusServiceImpl implements IItemSyllabusService {
	private static final Logger logger = Logger.getLogger(ItemErrorRecorveryServiceImpl.class);
	private IItemDao itemDao;
	private ISyllabusDao syllabusDao;
	/**
	 * 设置 试题数据接口
	 * @param itemDao
	 * 试题数据接口
	 */
	public void setItemDao(IItemDao itemDao) {
		this.itemDao = itemDao;
	}
	/**
	 * 设置 大纲数据接口
	 * @param syllabusDao
	 * 大纲数据接口
	 */
	public void setSyllabusDao(ISyllabusDao syllabusDao) {
		this.syllabusDao = syllabusDao;
	}
	/*
	 * 加载试题关联的要点
	 * @see com.examw.test.service.library.IItemSyllabusService#loadItemSyllabuses(java.lang.String)
	 */
	@Override
	public List<BaseSyllabusInfo> loadItemSyllabuses(String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题[%1$s]的要点信息", itemId));
		Item item = this.itemDao.loadItem(itemId);
		if(item == null){
			throw new RuntimeException(String.format("试题[%1$s]不存在",itemId));
		}
		return conversion(item.getSyllabuses());
	}
	/*
	 * 转换
	 */
	private List<BaseSyllabusInfo> conversion(Set<Syllabus> syllabuses) {
		List<BaseSyllabusInfo> result = new ArrayList<BaseSyllabusInfo>();
		if(syllabuses != null && syllabuses.size() > 0) 
		{
			for(Syllabus data:syllabuses){
				if(data == null) continue;
				result.add(this.changeModel(data));
			}
		}
		return result;
	}
	/**
	 * 模型转化
	 * @param data
	 * @return
	 */
	private BaseSyllabusInfo changeModel(Syllabus data){
		if(data == null) return null;
		BaseSyllabusInfo info = new BaseSyllabusInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getParent()!=null)
		{
			info.setPid(data.getParent().getId());
		}
		info.setFullTitle(this.loadFullTitle(data));
		return info;
	}
	/*
	 * 加载完整的要点标题
	 */
	private String loadFullTitle(Syllabus data)
	{
		if(data == null) return null;
		if(data.getParent() == null) return data.getTitle();
		StringBuilder builder = new StringBuilder(data.getTitle());
		builder.insert(0, this.loadFullTitle(data.getParent()) + " >> ");
		return builder.toString();
	}
	/*
	 * 更改试题大纲要点
	 * @see com.examw.test.service.library.IItemSyllabusService#updateItemSyllabus(java.lang.String, java.lang.String[])
	 */
	@Override
	public void updateItemSyllabus(String itemId, String syllabusIds) {
		if(logger.isDebugEnabled()) logger.debug(String.format("更新试题[%1$s]的要点信息", itemId));
		Item item = this.itemDao.loadItem(itemId);
		if(item == null){
			throw new RuntimeException(String.format("试题[%1$s]不存在",itemId));
		}
		if(StringUtils.isEmpty(syllabusIds)){
			item.setSyllabuses(null);
		}else{
			String[] ids = syllabusIds.split(",");
			Set<Syllabus> set = new HashSet<Syllabus>();
			for(String id:ids){
				Syllabus syllabus = this.syllabusDao.load(Syllabus.class, id);
				if(syllabus == null){
					throw new RuntimeException(String.format("大纲要点[%1$s]不存在",id));
				}
				set.add(syllabus);
			}
			item.setSyllabuses(set);
		}
	}
	
	/*
	 * 删除试题大纲要点
	 * @see com.examw.test.service.library.IItemSyllabusService#deleteItemSyllabus(java.lang.String, java.lang.String[])
	 */
	@Override
	public void deleteItemSyllabus(String itemId, String syllabusIds) {
		if(logger.isDebugEnabled()) logger.debug(String.format("更新试题[%1$s]的要点信息", itemId));
		Item item = this.itemDao.loadItem(itemId);
		if(item == null){
			throw new RuntimeException(String.format("试题[%1$s]不存在",itemId));
		}
		if(StringUtils.isEmpty(syllabusIds)) return;
		Set<Syllabus> set = item.getSyllabuses();
		if(set == null || set.size()==0)return;
		Set<Syllabus> syllabuses = new HashSet<Syllabus>();
		for(Syllabus data:set){
			if(!syllabusIds.contains(data.getId())){
				syllabuses.add(data);
			}
		}
		set.clear();
		if(syllabuses.size()==0) item.setSyllabuses(null);
		else item.setSyllabuses(syllabuses);
	}
}
