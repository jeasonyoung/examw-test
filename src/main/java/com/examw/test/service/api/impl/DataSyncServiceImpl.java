package com.examw.test.service.api.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.api.AppClientSync;
import com.examw.test.model.api.ExamSync;
import com.examw.test.model.api.SubjectSync;
import com.examw.test.service.api.IDataSyncService;
import com.examw.test.service.products.IRegistrationCodeService;

/**
 * 数据同步服务接口实现类。
 * 
 * @author yangyong
 * @since 2015年2月27日
 */
public class DataSyncServiceImpl implements IDataSyncService {
	private final static Logger logger = Logger.getLogger(DataSyncServiceImpl.class);
	private IRegistrationCodeService registrationCodeService;
	/**
	 * 设置注册码服务接口。
	 * @param registrationCodeService 
	 *	  注册码服务接口。
	 */
	public void setRegistrationCodeService(IRegistrationCodeService registrationCodeService) {
		if(logger.isDebugEnabled()) logger.debug("注入注册码服务接口...");
		this.registrationCodeService = registrationCodeService;
	}
	/*
	 * 同步考试科目数据。
	 * @see com.examw.test.service.api.IDataSyncService#syncExams(com.examw.test.model.api.AppClientSync)
	 */
	@Override
	public ExamSync syncExams(AppClientSync req) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("同步考试科目数据...");
		Product p = this.validationSyncReq(req);
		if(p == null) throw new Exception("加载产品数据失败！");
		Exam exam = null;
		if((exam = p.getExam()) == null) throw new Exception(String.format("产品［%s］未设置考试！", p.getName()));
		ExamSync examSync = new ExamSync();
		examSync.setCode(String.format("%d", exam.getCode()));
		examSync.setName(exam.getName());
		examSync.setAbbr(exam.getAbbr());
		if(p.getSubjects() != null && p.getSubjects().size() > 0){
			Set<SubjectSync> subjectSyncs = new HashSet<>();
			for(Subject subject : p.getSubjects()){
				if(subject == null) continue;
				SubjectSync subjectSync = new SubjectSync();
				subjectSync.setCode(String.format("%d", subject.getCode()));
				subjectSync.setName(subject.getName());
				subjectSyncs.add(subjectSync);
			}
			examSync.setSubjects(subjectSyncs);
		}
		return examSync;
	}
	//验证同步请求的合法性。
	private Product validationSyncReq(AppClientSync req) throws Exception{
		if(logger.isDebugEnabled()) logger.debug("验证同步请求的合法性...");
		if(req == null) throw new IllegalArgumentException("同步请求为空！");
		if(this.registrationCodeService.validation(req.getCode())){
			Registration reg = this.registrationCodeService.loadRegistration(req.getCode());
			if(reg == null) throw new Exception(String.format("加载注册码对象失败! %s", req.getCode()));
			Product p = reg.getProduct();
			if(p == null) throw new Exception("加载产品信息失败！");
			if(!p.getId().equalsIgnoreCase(req.getProductId())){
				throw new Exception(String.format("注册码［%1$s］属于产品［%2$s］与App应用产品不符!", req.getCode(), p.getName()));
			}
			return p;
		}
		return null;
	}
}