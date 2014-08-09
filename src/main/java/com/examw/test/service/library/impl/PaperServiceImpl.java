package com.examw.test.service.library.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.ISourceDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Source;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.PaperStatus;

/**
 * 试卷服务接口实现类。
 * @author yangyong.
 * @since 2014-08-06.
 */
public class PaperServiceImpl extends BaseDataServiceImpl<Paper, PaperInfo> implements IPaperService {
	private static final Logger logger = Logger.getLogger(PaperServiceImpl.class);
	private IPaperDao paperDao;
	private ISourceDao sourceDao;
	private ISubjectDao subjectDao;
	private Map<Integer,String> typeMap,statusMap;
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/**
	 * 设置来源数据接口。
	 * @param sourceDao 
	 *	  来源数据接口。
	 */
	public void setSourceDao(ISourceDao sourceDao) {
		this.sourceDao = sourceDao;
	}
	/**
	 * 设置所属科目数据接口。
	 * @param subjectDao 
	 *	  所属科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置试卷类型集合。
	 * @param typeMap 
	 *	  试卷类型集合。
	 */
	public void setTypeMap(Map<Integer,String> typeMap) {
		this.typeMap = typeMap;
	}
	/**
	 * 设置试卷状态集合。
	 * @param statusMap 
	 *	  试卷状态集合。
	 */
	public void setStatusMap(Map<Integer,String> statusMap) {
		this.statusMap = statusMap;
	}
	/*
	 * 加载试卷类型名称。
	 * @see com.examw.test.service.library.IPaperService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(type == null || this.typeMap == null || this.typeMap.size() == 0) return null;
		return this.typeMap.get(type);
	}
	/*
	 * 加载试卷状态名称。
	 * @see com.examw.test.service.library.IPaperService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}

	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Paper> find(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.paperDao.findPapers(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PaperInfo changeModel(Paper data) {
		if(logger.isDebugEnabled()) logger.debug("类型转换...");
		if(data == null) return null;
		PaperInfo info = new PaperInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamId(data.getSubject().getExam().getId());
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if(data.getSource() != null){
			info.setSourceId(data.getSource().getId());
			info.setSourceName(data.getSource().getName());
		}
		if(!StringUtils.isEmpty(data.getType())) info.setTypeName(this.loadTypeName(data.getType()));
		if(!StringUtils.isEmpty(data.getStatus())) info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		 return this.paperDao.total(info);
	}
	/*
	 * 更新数据.
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PaperInfo update(PaperInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Paper data = StringUtils.isEmpty(info.getId()) ?  null : this.paperDao.load(Paper.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			info.setStatus(PaperStatus.NONE.getValue());
			data = new Paper();
		}
		if(!StringUtils.isEmpty(data.getStatus())){
			if(data.getStatus() == Paper.STATUS_AUDIT) throw new RuntimeException("试卷已审核，不能被修改！");
			if(data.getStatus() == Paper.STATUS_PUBLISH) throw new RuntimeException("试卷已发布，不能被修改！");
		}
		if(!isAdded){
			info.setStatus(data.getStatus());
			info.setCreateTime(data.getCreateTime());
			info.setPublishTime(data.getPublishTime());
		}
		info.setLastTime(new Date());
		
		BeanUtils.copyProperties(info, data);
		
		if(!StringUtils.isEmpty(info.getSubjectId()) && (data.getSubject() == null || !data.getSubject().getId().equalsIgnoreCase(info.getSubjectId()))){
			data.setSubject(this.subjectDao.load(Subject.class, info.getSubjectId()));
		}
		if(data.getSubject() != null){
			info.setSubjectName(data.getSubject().getName());
			if(data.getSubject().getExam() != null){
				info.setExamName(data.getSubject().getExam().getName());
			}
		}
		if(!StringUtils.isEmpty(info.getSourceId()) && (data.getSource() == null || !data.getSource().getId().equalsIgnoreCase(info.getSourceId()))){
			data.setSource(this.sourceDao.load(Source.class, info.getSourceId()));
		}
		if(data.getSource() != null){
			info.setSourceName(data.getSource().getName());
		}
		if(!StringUtils.isEmpty(data.getType())) info.setTypeName(this.loadTypeName(data.getType()));
		if(!StringUtils.isEmpty(data.getStatus())) info.setStatusName(this.loadStatusName(data.getStatus()));
		if(isAdded) this.paperDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据....");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Paper data = this.paperDao.load(Paper.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除［%s］...", ids[i]));
				this.paperDao.delete(data);
			}
		}
	}
	/*
	 * 更新数据状态。
	 * @see com.examw.test.service.library.IPaperService#updateStatus(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void updateStatus(String id, PaperStatus status) {
		if(logger.isDebugEnabled())logger.debug(String.format("更新状态：%1$s -> %2$s", id, status));
		String msg = null;
		if(StringUtils.isEmpty(id)){
			msg = "试卷ID为空！";
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(status == null) status = PaperStatus.NONE;
		Paper data = this.paperDao.load(Paper.class, id);
		if(data == null){
			msg = String.format("试卷［id=%s］不存在！", id);
		}
		data.setStatus(status.getValue());
		data.setLastTime(new Date());
		if(status == PaperStatus.PUBLISH){
			data.setPublishTime(new Date());
		}
		if(logger.isDebugEnabled()) logger.debug(String.format("更新状态［%1$s,%2$s］%3$s ＝> %4$s", data.getId(),data.getName(), PaperStatus.convert(data.getStatus()),  status));
	}
	/*
	 * 加载试卷结构集合。
	 * @see com.examw.test.service.library.IPaperService#loadStructures(java.lang.String)
	 */
	@Override
	public List<StructureInfo> loadStructures(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷结构集合［paperId = %s］...", paperId));
		String msg =  null;
		if(StringUtils.isEmpty(paperId)){
			msg = "试卷ID不存在！";
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null){
			msg = String.format("试卷不存在［paperId = %s］！", paperId);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(paper.getStructures() == null || paper.getStructures().size() == 0) return null;
		 List<StructureInfo> list = new ArrayList<>();
		 for(Structure s: paper.getStructures()){
			 if(s == null || (s.getParent() != null)) continue;
			 StructureInfo info = new StructureInfo();
			 if(this.changeModel(s, info)){
				 list.add(info);
			 }
		 }
		return list;
	}
	//试卷结构类型转换。
	private boolean changeModel(Structure source, StructureInfo target){
		if(source == null || target == null) return false;
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(source.getChildren() != null && source.getChildren().size() > 0){
			Set<StructureInfo> children = new TreeSet<StructureInfo>(new Comparator<StructureInfo>(){
				@Override
				public int compare(StructureInfo o1, StructureInfo o2) {
					return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(Structure s : source.getChildren()){
				if(s == null) continue;
				 StructureInfo e = new StructureInfo();
				 e.setPid(target.getId());
				 if(this.changeModel(s, e)){
					 children.add(e);
				 }
			}
			if(children.size() > 0) target.setChildren(children);
		}
		return true;
	}
	/*
	 * 更新试卷结构。
	 * @see com.examw.test.service.library.IPaperService#updateStructure(java.lang.String, com.examw.test.model.library.StructureInfo)
	 */
	@Override
	public void updateStructure(String paperId, StructureInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新试卷［paperId = "+ paperId +"］结构...");
		String msg = null;
		if(StringUtils.isEmpty(paperId)){
			msg = "所属试卷ID为空！";
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(info == null){
			msg = String.format("更新的试卷［paperId = %s］结构不存在！", paperId);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null){
			msg = String.format("所属试卷［paperId = %s］不存在！", paperId);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		boolean isAdded = false;
		Structure data = null;
		if(isAdded = StringUtils.isEmpty(info.getId())){
			info.setId(UUID.randomUUID().toString());
			data = new Structure();
			data.setPaper(paper);
		}
		if(!isAdded){
			data = this.findStructure(paper, info.getId());
			if(StringUtils.isEmpty(info.getPid()) && data.getParent() != null){
				data.setParent(null);
				if(logger.isDebugEnabled()) logger.debug(String.format("提升为一级菜单［%s］。", info.getId()));
			}
		}
		if(!StringUtils.isEmpty(info.getPid()) && (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid()))){
			data.setParent(this.findStructure(paper, info.getPid()));
		}
		this.updateStructureChildren(paper,info, data);
		if(isAdded){
			if(paper.getStructures() == null) paper.setStructures(new HashSet<Structure>());
			paper.getStructures().add(data);
		}
	}
	//更新当前结构及其子结构。
	private boolean updateStructureChildren(Paper paper,StructureInfo source, Structure target){
		if(paper == null || source == null || target == null) return false;
		BeanUtils.copyProperties(source, target, new String[]{"children"});
		if(source.getChildren() != null && source.getChildren().size() > 0){
			Set<Structure> children = new HashSet<>();
			for(StructureInfo info : source.getChildren()){
				Structure e = StringUtils.isEmpty(info.getId()) ? null : this.findStructure(paper, info.getId());
				if(e == null){
					if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
					e = new Structure();
				}
				e.setParent(target);
				if(this.updateStructureChildren(paper, info, e)){
					children.add(e);
				}
			}
			if(children.size() > 0) target.setChildren(children);
		}
		return true;
	}
	
	//查找试卷下的结构对象。
	private Structure findStructure(Paper paper, String structureId){
		Structure find = null;
		if(paper == null || StringUtils.isEmpty(structureId)) return find;
		if(paper.getStructures() == null || paper.getStructures().size() == 0) return find;
		for(Structure s : paper.getStructures()){
			 find = this.findStructure(s, structureId);
			 if(find != null) break;
		}
		return find;
	}
	//查找当前结构极其子机构对象。
	private Structure findStructure(Structure structure, String structureId){
		if(structure == null || StringUtils.isEmpty(structureId)) return null;
		if(structure.getId().equalsIgnoreCase(structureId)) return structure;
		if(structure.getChildren() != null && structure.getChildren().size() > 0){
			for(Structure s : structure.getChildren()){
				Structure data = this.findStructure(s, structureId);
				if(data != null) return data;
			}
		}
		return null;
	}
	/*
	 * 删除试卷结构。
	 * @see com.examw.test.service.library.IPaperService#deleteStructure(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteStructure(String paperId, String... structureId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷［paperId = %1$s］结构［structureId = %2$s］...", paperId, structureId));
		if(StringUtils.isEmpty(paperId) || structureId == null || structureId.length == 0) return;
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null){
			String msg = String.format("所属试卷［paperId = %s］不存在！", paperId);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		for(int i = 0; i< structureId.length; i++){
			if(logger.isDebugEnabled()) logger.debug(String.format("删除结构［%s］数据...", structureId[i]));
			Structure data = this.findStructure(paper, structureId[i]);
			if(data == null) return;
			Structure parent = data.getParent();
	 		if(parent == null){
				paper.getStructures().remove(data);
				continue;
			}
	 		parent.getChildren().remove(data);
		}
	}
}