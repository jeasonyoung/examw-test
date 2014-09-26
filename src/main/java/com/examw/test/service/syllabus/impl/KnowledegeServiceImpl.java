package com.examw.test.service.syllabus.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.syllabus.IKnowledgeDao;
import com.examw.test.dao.syllabus.ISyllabusDao;
import com.examw.test.dao.syllabus.ITextBookDao;
import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.domain.syllabus.TextBook;
import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.IKnowledgeService;
/**
 * 知识点服务接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class KnowledegeServiceImpl extends BaseDataServiceImpl<Knowledge, KnowledgeInfo> implements IKnowledgeService {
	private static final Logger logger = Logger.getLogger(KnowledegeServiceImpl.class);
	private IKnowledgeDao knowledgeDao;
	private ISyllabusDao syllabusDao;
	private ITextBookDao textBookDao;
	/**
	 * 设置知识点数据接口。
	 * @param knowDao
	 * 知识点数据接口。
	 */
	public void setKnowledgeDao(IKnowledgeDao knowledgeDao) {
		if(logger.isDebugEnabled())logger.debug("注入知识点数据接口...");
		this.knowledgeDao = knowledgeDao;
	}
	/**
	 * 设置考试大纲数据接口。
	 * @param syllabusDao 
	 *	  考试大纲数据接口。
	 */
	public void setSyllabusDao(ISyllabusDao syllabusDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试大纲数据接口...");
		this.syllabusDao = syllabusDao;
	}
	/**
	 * 设置教材数据接口。
	 * @param textBookDao 
	 *	  教材数据接口。
	 */
	public void setTextBookDao(ITextBookDao textBookDao) {
		if(logger.isDebugEnabled()) logger.debug("注入教材数据接口...");
		this.textBookDao = textBookDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Knowledge> find(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return this.knowledgeDao.findKnowledges(info);
	}
	/*
	 * 查询统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询统计...");
		return this.knowledgeDao.total(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected KnowledgeInfo changeModel(Knowledge data) {
		if(logger.isDebugEnabled())logger.debug("数据模型转换 Knowledge => KnowledgeInfo...");
		if(data == null) return null;
		KnowledgeInfo info = new KnowledgeInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getSyllabus() != null){
			info.setSyllabusId(data.getSyllabus().getId());
			info.setSyllabusName(data.getSyllabus().getTitle());
		}
		if(data.getBook() != null){
			info.setBookId(data.getBook().getId());
			info.setBookName(data.getBook().getName());
		}
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.syllabus.IKnowledgeService#conversion(com.examw.test.domain.syllabus.Knowledge)
	 */
	@Override
	public KnowledgeInfo conversion(Knowledge knowledge) {
		return this.changeModel(knowledge);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public KnowledgeInfo update(KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Knowledge data = StringUtils.isEmpty(info.getId()) ?  null : this.knowledgeDao.load(Knowledge.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Knowledge();
		}
		BeanUtils.copyProperties(info,data);
		//大纲
		data.setSyllabus(StringUtils.isEmpty(info.getSyllabusId()) ? null : this.syllabusDao.load(Syllabus.class, info.getSyllabusId()));
		//教材
		data.setBook(StringUtils.isEmpty(info.getBookId()) ? null : this.textBookDao.load(TextBook.class, info.getBookId()));
		if(isAdded)this.knowledgeDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length;i++){
			Knowledge data = this.knowledgeDao.load(Knowledge.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除数据［"+ ids[i]+"］");
				this.knowledgeDao.delete(data);
			}
		}
	}
	/*
	 * 加载知识点
	 * @see com.examw.test.service.syllabus.IKnowledgeService#loadKnowledge(java.lang.String)
	 */
	@Override
	public List<KnowledgeInfo> loadKnowledges(String syllabusId,String textBookId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载大纲［syllabusId = %1$s, textBookId =%2$s］下的知识点集合...", syllabusId,textBookId));
		return this.changeModel(this.knowledgeDao.loadSyllabusKnowledge(syllabusId,textBookId));
	}
	/*
	 * 加载知识点数据。
	 * @see com.examw.test.service.syllabus.IKnowledgeService#loadKnowledge(java.lang.String)
	 */
	@Override
	public Knowledge loadKnowledge(String knowledgeId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载知识点［knowledgeId = %s］数据...", knowledgeId));
		if(StringUtils.isEmpty(knowledgeId)) return null;
		return this.knowledgeDao.load(Knowledge.class, knowledgeId);
	}
	/*
	 * 加载知识点代码最大值。
	 * @see com.examw.test.service.syllabus.IKnowledgeService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载知识点代码最大值...");
		return this.knowledgeDao.loadMaxCode();
	}
}