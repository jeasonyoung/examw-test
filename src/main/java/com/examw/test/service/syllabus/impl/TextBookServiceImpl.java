package com.examw.test.service.syllabus.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.DataGrid;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.dao.syllabus.IKnowledgeDao;
import com.examw.test.dao.syllabus.IPressDao;
import com.examw.test.dao.syllabus.ISyllabusDao;
import com.examw.test.dao.syllabus.ITextBookDao;
import com.examw.test.domain.settings.Subject;
import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.domain.syllabus.Press;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.domain.syllabus.TextBook;
import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.model.syllabus.TextBookInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.ITextBookService;
/**
 * 教材服务接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class TextBookServiceImpl extends BaseDataServiceImpl<TextBook, TextBookInfo> implements ITextBookService {
	private static final Logger logger = Logger.getLogger(TextBookServiceImpl.class);
	private ITextBookDao bookDao;
	private ISubjectDao subjectDao;
	private IPressDao pressDao;
	private IKnowledgeDao knowDao;
	private ISyllabusDao syllabusDao;
	/**
	 * 设置教材数据接口。
	 * @param bookDao
	 * 教材数据接口。
	 */
	public void setBookDao(ITextBookDao bookDao) {
		if(logger.isDebugEnabled())logger.debug("注入教材数据接口...");
		this.bookDao = bookDao;
	}
	/**
	 * 设置科目数据接口。
	 * @param subjectDao
	 * 科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled())logger.debug("注入科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置大纲要点数据接口。
	 * @param syllabusDao
	 * 大纲要点数据接口。
	 */
	public void setSyllabusDao(ISyllabusDao syllabusDao) {
		if(logger.isDebugEnabled())logger.debug("注入大纲要点数据接口...");
		this.syllabusDao = syllabusDao;
	}
	/**
	 * 设置知识点数据接口。
	 * @param knowDao
	 * 知识点数据接口。
	 */
	public void setKnowDao(IKnowledgeDao knowDao) {
		if(logger.isDebugEnabled())logger.debug("注入知识点数据接口...");
		this.knowDao = knowDao;
	}
	/**
	 * 设置出版社数据接口。
	 * @param pressDao
	 * 出版社数据接口。
	 */
	public void setPressDao(IPressDao pressDao) {
		if(logger.isDebugEnabled())logger.debug("注入出版社数据接口...");
		this.pressDao = pressDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<TextBook> find(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		return this.bookDao.findTextBooks(info);
	}
	/*
	 * 统计数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		return this.bookDao.total(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected TextBookInfo changeModel(TextBook data) {
		if(logger.isDebugEnabled())logger.debug("类型转换...");
		if(data == null) return null;
		TextBookInfo info = new TextBookInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getPress() != null){
			info.setPressId(data.getPress().getId());
			info.setPressName(data.getPress().getName());
		}
		if(data.getSubject() != null){
			info.setSubId(data.getSubject().getId());
			info.setSubName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		//知识点的类型转换。
		if(data.getKnowledges() != null && data.getKnowledges().size() > 0){
			Set<KnowledgeInfo> knows = new HashSet<KnowledgeInfo>();
			for(Knowledge know : data.getKnowledges()){
				KnowledgeInfo k = this.changeModel(know);
				if(k != null)knows.add(k);
			}
			info.setKnowledges(knows);
		}
		return info;
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public TextBookInfo update(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		Boolean isAdded = false;
		TextBook data = StringUtils.isEmpty(info.getId()) ? null : this.bookDao.load(TextBook.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new TextBook();
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getPressId()) && (data.getPress() == null || !data.getPress().getId().equalsIgnoreCase(info.getPressId()))){
			Press p = this.pressDao.load(Press.class, info.getPressId());
			if(p != null) data.setPress(p);
		}
		if(!StringUtils.isEmpty(info.getSubId()) && (data.getSubject() == null || !data.getSubject().getId().equalsIgnoreCase(info.getSubId()))){
			Subject s = this.subjectDao.load(Subject.class, info.getSubId());
			if(s != null) data.setSubject(s);
		}
		if(data.getPress() != null){
			info.setPressId(data.getPress().getId());
			info.setPressName(data.getPress().getName());
		}
		if(data.getSubject() != null){
			info.setSubId(data.getSubject().getId());
			info.setSubName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if(isAdded)this.bookDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			TextBook data = this.bookDao.load(TextBook.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除数据［"+ ids[i]+"］");
				this.bookDao.delete(data);
			} 
		}	
	}
	/*
	 * 加载知识点最大代码。
	 * @see com.examw.test.service.syllabus.ITextBookService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		List<Knowledge> sources = this.knowDao.findKnowledges(null, new KnowledgeInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() {return "code"; } 
			@Override
			public String getOrder() { return "desc";}
		});
		if(sources != null && sources.size() > 0){
			return sources.get(0).getCode();
		}
		return null;
	}
	/*
	 * 知识点类型转换。
	 */
	private KnowledgeInfo changeModel(Knowledge data) {
		if(logger.isDebugEnabled())logger.debug(" 知识点类型转换...");
		if(data == null) return null;
		KnowledgeInfo info = new KnowledgeInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getSyllabus() != null){
			info.setSyllId(data.getSyllabus().getId());
			info.setSyllName(data.getSyllabus().getTitle());
		}
		if(data.getBook() != null){
			info.setBookId(data.getBook().getId());
		}
		return info;
	}
	/*
	 * 更新知识点数据。
	 * @see com.examw.test.service.syllabus.ITextBookService#updateKnowledge(java.lang.String, com.examw.test.model.syllabus.KnowledgeInfo)
	 */
	@Override
	public KnowledgeInfo updateKnowledge(String bookId, KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("更新知识点数据...");
		if(StringUtils.isEmpty(bookId) || info == null) return null;
		boolean isAdded = false;
		Knowledge data = StringUtils.isEmpty(info.getId()) ?  null : this.knowDao.load(Knowledge.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Knowledge();
		}
		BeanUtils.copyProperties(info,data);
		if(!StringUtils.isEmpty(info.getSyllId()) && (data.getSyllabus() == null || !data.getSyllabus().getId().equalsIgnoreCase(info.getSyllId()))){
			Syllabus s = this.syllabusDao.load(Syllabus.class, info.getSyllId());
			if(s != null) data.setSyllabus(s);
		}
		if(data.getSyllabus() != null){
			info.setSyllId(data.getSyllabus().getId());
			info.setSyllName(data.getSyllabus().getTitle());
		}
		if(data.getBook() == null || !data.getBook().getId().equalsIgnoreCase(bookId)){
			data.setBook(this.bookDao.load(TextBook.class, bookId)); 
		}
		if(isAdded)this.knowDao.save(data);
		return info;
	}
	/*
	 * 查询知识点。
	 * @see com.examw.test.service.syllabus.ITextBookService#findKnowledge(java.lang.String, com.examw.test.model.syllabus.KnowledgeInfo)
	 */
	@Override
	public DataGrid<KnowledgeInfo> findKnowledge(String bookId, KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("根据教材ID查询知识点...");
		DataGrid<KnowledgeInfo> grid = new DataGrid<KnowledgeInfo>();
		List<Knowledge> list = this.knowDao.findKnowledges(bookId, info);
		if(list != null){
			List<KnowledgeInfo> rows = new ArrayList<>();
			for(Knowledge data : list){
				KnowledgeInfo k = new KnowledgeInfo();
				BeanUtils.copyProperties(data, k);
				if(data.getBook() != null){
					 k.setBookId(data.getBook().getId());
				}
				if(data.getSyllabus() != null){
					k.setSyllId(data.getSyllabus().getId());
					k.setSyllName(data.getSyllabus().getTitle());
				}
				rows.add(k);
			}
			grid.setRows(rows);
			grid.setTotal(this.knowDao.total(bookId, info));
		}
		return grid;
	}
}