package com.examw.test.service.library.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperStructureService;
import com.examw.test.service.library.PaperStatus;

/**
 * 试卷结构服务实现类。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
public class PaperStructureServiceImpl implements IPaperStructureService {
	private static final Logger logger = Logger.getLogger(PaperStructureServiceImpl.class);
	private IStructureDao structureDao;
	private IPaperDao paperDao;
	private ISubjectDao subjectDao;
	private IItemService itemService;
	/**
	 * 设置试卷结构数据接口。
	 * @param structureDao 
	 *	试卷结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		if(logger.isDebugEnabled())logger.debug("注入试卷结构数据接口...");
		this.structureDao = structureDao;
	}
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if(logger.isDebugEnabled())logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/**
	 * 设置试卷服务接口。
	 * @param itemService 
	 *	  试卷服务接口。
	 */
	public void setItemService(IItemService itemService) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷服务接口...");
		this.itemService = itemService;
	}
	
	/**
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 科目数据接口
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/*
	 * 加载试卷结构最大排序号。
	 * @see com.examw.test.service.library.IPaperStructureService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String paperId) {
		if(logger.isDebugEnabled()) logger.error(String.format("加载试卷［paperId = %s］结构最大排序号...", paperId));
		return this.structureDao.loadPaperMaxOrder(paperId);
	}
	/*
	 * 加载结构数据。
	 * @see com.examw.test.service.library.IPaperStructureService#loadStructures(java.lang.String)
	 */
	@Override
	public List<StructureInfo> loadStructures(String paperId) {
		if(logger.isDebugEnabled())logger.debug(String.format("加载结构[paperId=%s]数据...", paperId));
		String msg = null;
		if (StringUtils.isEmpty(paperId)) {
			logger.error(msg = "试卷ID不存在！");
			throw new RuntimeException(msg);
		}
		return this.changeModel(this.structureDao.loadStructures(paperId));
	}
	/*
	 * 加载整个试卷的结构树
	 * @see com.examw.test.service.library.IPaperStructureService#loadStructureTree(java.lang.String)
	 */
	@Override
	public List<TreeNode> loadStructureTree(String paperId) {
		if(logger.isDebugEnabled())logger.debug(String.format("加载结构[paperId=%s]树数据...", paperId));
		return loadStructureTree(paperId,null,false);
	}
	/*
	 * 加载试卷结构树,排除一些不能作为上级的结构
	 * @see com.examw.test.service.library.IPaperStructureService#loadStructureTree(java.lang.String, java.lang.String, boolean)
	 */
	public List<TreeNode> loadStructureTree(String paperId,String ignoreStructureId,boolean checkHasItem){
		if(logger.isDebugEnabled())logger.debug(String.format("加载结构[paperId=%s]树数据...", paperId));
		String msg = null;
		if (StringUtils.isEmpty(paperId)) {
			logger.error(msg = "试卷ID不存在！");
			throw new RuntimeException(msg);
		}
		List<Structure> structures = this.structureDao.loadStructures(paperId);
		List<TreeNode> result = new ArrayList<>();
		if(structures != null && structures.size() > 0){
			for(Structure data : structures){
				if(data == null) continue;
				TreeNode e = this.createTreeNode(data,ignoreStructureId,checkHasItem,false);
				if(e != null)result.add(e);
			}
		}
		return result;
	}
	/**
	 * 创建结构树
	 * @param data
	 * @param ignoreStructureId
	 * @param checkHasItem
	 * @return
	 */
	private TreeNode createTreeNode(Structure data, String ignoreStructureId,boolean checkHasItem,boolean isChildren) {
		if(data == null || data.getId().equals(ignoreStructureId)) return null;
		//TODO 这里如果是子类也与试卷关联的话就要进行一次判断
		if(!isChildren && data.getParent()!=null) return null;
		if(checkHasItem){
			Long totalItems = this.structureDao.totalStructureItems(data.getId());	
			if(totalItems!=null && totalItems.longValue()>0)	//如果已经加题则不能再作为上级结构
				return null;
		}
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getTitle());
		Map<String, Object> attributes = new HashMap<String,Object>();
		attributes.put("info",this.changeModel(data, false));
		node.setAttributes(attributes);
		if(data.getChildren()!=null && data.getChildren().size()>0)
		{
			List<TreeNode> children = new ArrayList<>();
			for(Structure s:data.getChildren()){
				TreeNode child = this.createTreeNode(s, ignoreStructureId, checkHasItem,true);
				if(child!=null) children.add(child);
			}
			node.setChildren(children);
		}
		return node;
	}
	/**
	 * 数据模型集合转换。
	 * @param structures
	 * @return
	 */
	protected List<StructureInfo> changeModel(List<Structure> structures){
		if(logger.isDebugEnabled()) logger.debug("数据模型集合转换 List<Structure> => List<StructureInfo> ...");
		List<StructureInfo> list = new ArrayList<>();
		if(structures != null && structures.size() > 0){
			for(Structure structure : structures){
				if(structure == null) continue;
				if(structure.getParent()!=null) continue;	//如果是子类不加载
				StructureInfo info = this.changeModel(structure,true);
				if(info != null){
					list.add(info);
				}
			}
			if(list.size() > 0) Collections.sort(list);
		}
		return list;
	}
	//数据模型转换。
	protected StructureInfo changeModel(Structure data,boolean isLoadChildren){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Structure => StructureInfo ...");
		if(data == null) return null;
		StructureInfo info = new StructureInfo();
		BeanUtils.copyProperties(data, info,new String[]{"items","children"});
		if(data.getType() != null && this.itemService != null){
			info.setTypeName(this.itemService.loadTypeName(data.getType()));
		}
		if(data.getParent()!=null){
			info.setPid(data.getParent().getId());
		}
		if(data.getSubject()!=null){
			info.setSubjectId(data.getSubject().getId());
		}
		if(isLoadChildren && data.getChildren()!=null && data.getChildren().size()>0){
			List<StructureInfo> children = new ArrayList<>();
			for(Structure s:data.getChildren())
			{
				StructureInfo child = this.changeModel(s,isLoadChildren);
				if(child!=null) children.add(child);
			}
			if(children.size() > 0) Collections.sort(children);
			info.setChildren(children);
		}
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.library.IPaperStructureService#conversion(com.examw.test.domain.library.Structure)
	 */
	@Override
	public StructureInfo conversion(Structure structure) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换...");
		return this.changeModel(structure,true);
	}
	@Override
	public StructureInfo conversion(Structure structure,boolean changeChild) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换...");
		return this.changeModel(structure,changeChild);
	}
	/*
	 * 更新试卷结构。
	 * @see com.examw.test.service.library.IPaperStructureService#updateStructure(java.lang.String, com.examw.test.model.library.PaperStructureInfo)
	 */
	@Override
	public void updateStructure(String paperId, StructureInfo info) {
		if (logger.isDebugEnabled())logger.debug("更新试卷［paperId = " + paperId + "］结构...");
		String msg = null;
		if (info == null) {
			logger.error(String.format("更新的试卷［paperId = %s］结构不存在！", paperId));
			throw new RuntimeException(msg);
		}
		boolean isAdded = false;
		Structure data = StringUtils.isEmpty(info.getId()) ? null : this.structureDao.load(Structure.class, info.getId());
		if(isAdded = (data == null)){
			if (StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			if (StringUtils.isEmpty(paperId)) {
				logger.error(msg = "所属试卷ID为空！");
				throw new RuntimeException(msg);
			}
			Paper paper = this.paperDao.load(Paper.class, paperId);
			if(paper == null){
				logger.error(msg = String.format("所属试卷［paperId = %s］不存在!", paperId));
				throw new RuntimeException(msg);
			}
			data = new Structure();
			data.setPaper(paper);
		}
		Integer paperStatus = null;
		if(data.getPaper() != null && (paperStatus = data.getPaper().getStatus()) != PaperStatus.NONE.getValue()){
			throw new RuntimeException(String.format("所属试卷［状态＝%s］不允许修改试卷结构！", PaperStatus.convert(paperStatus)));
		}
		//TODO 查询子结构下面的是否有其他科目
		BeanUtils.copyProperties(info, data,new String[]{"items","children"});
		if(!StringUtils.isEmpty(info.getPid()) ){
			Structure parent = this.structureDao.load(Structure.class, info.getPid());
			if(parent!=null)
			{
				data.setParent(parent);
				//data.setPaper(null); //子结构还是加试卷关联[不然关联不出试题,排序号也不好算]
			}
		}
		//加科目 [如果是子类,所选科目不能是非父类及父类下子类的科目]
		if(!StringUtils.isEmpty(info.getSubjectId())){
			Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
			if(subject!=null) data.setSubject(subject);
		}
		if (isAdded) this.structureDao.save(data);
	}
	/*
	 * 删除试卷结构。
	 * @see com.examw.test.service.library.IPaperStructureService#deleteStructure(java.lang.String[])
	 */
	@Override
	public void deleteStructure(String[] structureIds) {
		if (logger.isDebugEnabled())logger.debug("删除试卷结构...");
		if (structureIds == null || structureIds.length == 0)return;
		String msg = null;
		Integer count = 0;
		for (int i = 0; i < structureIds.length; i++) {
			if (logger.isDebugEnabled())logger.debug(String.format("删除结构［%s］数据...", structureIds[i]));
			Structure data = this.structureDao.load(Structure.class,structureIds[i]);
			if (data != null){
				if(data.getItems() != null && (count = data.getItems().size()) > 0){
					msg= String.format("试卷结构[%1$s]下有试题[%2$d]!", data.getTitle(), count);
					if(logger.isDebugEnabled())logger.debug(msg);
					throw new RuntimeException(msg);
				}
				this.structureDao.delete(data);
			}
		}
	}
}