package com.examw.test.service.records.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.records.IQuestionDao;
import com.examw.test.domain.records.Question;
import com.examw.test.model.records.QuestionInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.records.IQuestionService;

/**
 * 常见问题服务接口实现类。
 * 
 * @author yangyong
 * @since 2015年1月3日
 */
public class QuestionServiceImpl extends BaseDataServiceImpl<Question, QuestionInfo> implements IQuestionService {
	private static final Logger logger = Logger.getLogger(QuestionServiceImpl.class);
	private IQuestionDao questionDao;
	/**
	 * 设置常见问题数据接口。
	 * @param questionDao 
	 *	  常见问题数据接口。
	 */
	public void setQuestionDao(IQuestionDao questionDao) {
		if(logger.isDebugEnabled()) logger.debug("注入常见问题数据接口...");
		this.questionDao = questionDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Question> find(QuestionInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.questionDao.findQuestions(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected QuestionInfo changeModel(Question data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Question => QuestionInfo ...");
		QuestionInfo info = new QuestionInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(QuestionInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.questionDao.total(info);
	}
	/*
	 * 数据更新。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public QuestionInfo update(QuestionInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据更新...");
		boolean isAdded = false;
		Question data = StringUtils.isEmpty(info.getId()) ? null : this.questionDao.load(Question.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Question();
		}
		info.setCreateTime(new Date());
		BeanUtils.copyProperties(info, data);
		
		if(isAdded) this.questionDao.save(data);
		
		return this.changeModel(data);
	}
	/*
	 * 数据删除。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("数据删除:%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Question data = this.questionDao.load(Question.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s", ids[i]));
				this.questionDao.delete(data);
			}
		}
	}
}