package com.examw.test.service.syllabus.impl;

import java.util.Date;
import java.util.List; 
import java.util.UUID;

import org.apache.log4j.Logger; 
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.syllabus.IBookChapterDao;
import com.examw.test.dao.syllabus.IChapterKnowledgeDao;
import com.examw.test.dao.syllabus.ISyllabusDao;
import com.examw.test.domain.syllabus.BookChapter;
import com.examw.test.domain.syllabus.ChapterKnowledge; 
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.ChapterKnowledgeInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.IChapterKnowledgeService;
/**
 * 章节知识点服务接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class ChapterKnowledgeServiceImpl extends BaseDataServiceImpl<ChapterKnowledge, ChapterKnowledgeInfo> implements IChapterKnowledgeService {
	private static final Logger logger = Logger.getLogger(ChapterKnowledgeServiceImpl.class);
	private IChapterKnowledgeDao chapterKnowledgeDao;
	private IBookChapterDao bookChapterDao;
	private ISyllabusDao syllabusDao;
	/**
	 * 设置章节知识点数据接口。
	 * @param chapterKnowledgeDao 
	 *	  章节知识点数据接口。
	 */
	public void setChapterKnowledgeDao(IChapterKnowledgeDao chapterKnowledgeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入章节知识点数据接口...");
		this.chapterKnowledgeDao = chapterKnowledgeDao;
	}
	/**
	 * 设置教材章节数据接口。
	 * @param bookChapterDao 
	 *	  教材章节数据接口。
	 */
	public void setBookChapterDao(IBookChapterDao bookChapterDao) {
		if(logger.isDebugEnabled()) logger.debug("注入教材章节数据接口...");
		this.bookChapterDao = bookChapterDao;
	}
	/**
	 * 设置考试大纲数据接口。
	 * @param syllabusDao 
	 *	  考试大纲数据接口。
	 */
	public void setSyllabusDao(ISyllabusDao syllabusDao) {
		if(logger.isDebugEnabled()) logger.debug("考试大纲数据接口...");
		this.syllabusDao = syllabusDao;
	}
	/*
	 * 加载大纲要点和章节下的知识点集合
	 * @see com.examw.test.service.syllabus.IChapterKnowledgeService#loadKnowledges(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ChapterKnowledgeInfo> loadKnowledges(String syllabusId,String chapterId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载大纲要点［syllabusId = %1$s］和章节［chapterId = %2$s］下的知识点集合..", syllabusId, chapterId));
		return this.changeModel(this.chapterKnowledgeDao.loadSyllabusKnowledge(syllabusId, chapterId));
	}
	/*
	 * 加载知识点数据。
	 * @see com.examw.test.service.syllabus.IChapterKnowledgeService#loadKnowledge(java.lang.String)
	 */
	@Override
	public ChapterKnowledge loadKnowledge(String knowledgeId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载知识点［knowledgeId ＝％s］数据...", knowledgeId));
		if(StringUtils.isEmpty(knowledgeId)) return null;
		return this.chapterKnowledgeDao.load(ChapterKnowledge.class, knowledgeId);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.syllabus.IChapterKnowledgeService#conversion(com.examw.test.domain.syllabus.ChapterKnowledge)
	 */
	@Override
	public ChapterKnowledgeInfo conversion(ChapterKnowledge knowledge) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 ChapterKnowledge => ChapterKnowledgeInfo ...");
		return this.changeModel(knowledge);
	}
	/*
	 * 加载章节下的最大排序号。
	 * @see com.examw.test.service.syllabus.IChapterKnowledgeService#loadMaxCode(java.lang.String)
	 */
	@Override
	public Integer loadMaxCode(String chapterId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载章节［chapterId = %s］下的最大排序号...", chapterId));
		return this.chapterKnowledgeDao.loadMaxCode(chapterId);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<ChapterKnowledge> find(ChapterKnowledgeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.chapterKnowledgeDao.findKnowledges(info);
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ChapterKnowledgeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.chapterKnowledgeDao.total(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ChapterKnowledgeInfo changeModel(ChapterKnowledge data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 ChapterKnowledge => ChapterKnowledgeInfo ...");
		if(data == null) return null;
		ChapterKnowledgeInfo info = new ChapterKnowledgeInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getChapter() != null){
			info.setChapterId(data.getChapter().getId());
			info.setChapterName(data.getChapter().getTitle());
		}
		if(data.getSyllabus() != null){
			info.setSyllabusId(data.getSyllabus().getId());
			info.setSyllabusName(data.getSyllabus().getTitle());
			this.buildTopSyllabus(data.getSyllabus(), info);
		}
		return info;
	}
	//构建所属考试大纲版本。
	private void buildTopSyllabus(Syllabus syllabus, ChapterKnowledgeInfo info){
		if(syllabus == null || info == null) return;
		if(syllabus.getParent() == null){
			info.setTopSyllabusId(syllabus.getId());
			info.setTopSyllabusName(syllabus.getTitle());
			return;
		}
		this.buildTopSyllabus(syllabus.getParent(), info);
	}
	/*
	 * 数据更新。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ChapterKnowledgeInfo update(ChapterKnowledgeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据更新...");
		boolean isAdded = false;
		ChapterKnowledge data = StringUtils.isEmpty(info.getId()) ?  null : this.chapterKnowledgeDao.load(ChapterKnowledge.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) {
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new ChapterKnowledge();
		}else {
			//内容为空,删除这个知识点
			if(StringUtils.isEmpty(info.getDescription()))
			{
				if(logger.isDebugEnabled()) logger.debug(String.format("知识点内容为空,删除数据［id = %s］...", data.getId()));
				this.chapterKnowledgeDao.delete(data);
				return info;
			}
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null){
				info.setCreateTime(new Date());
			}
		}
		BeanUtils.copyProperties(info, data);
		data.setChapter(StringUtils.isEmpty(info.getChapterId()) ?  null : this.bookChapterDao.load(BookChapter.class, info.getChapterId()));
		data.setSyllabus(StringUtils.isEmpty(info.getSyllabusId()) ? null : this.syllabusDao.load(Syllabus.class, info.getSyllabusId()));
		if(isAdded) this.chapterKnowledgeDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(String id : ids){
			if(StringUtils.isEmpty(id)) continue;
			ChapterKnowledge data = this.chapterKnowledgeDao.load(ChapterKnowledge.class, id);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据［id = %s］...", id));
				this.chapterKnowledgeDao.delete(data);
			}
		}
	}
}