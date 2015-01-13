package com.examw.test.controllers.library;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.domain.security.Right;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.IPaperPreviewService;
import com.examw.test.service.library.ItemType;
import com.examw.test.service.library.PaperStatus;
import com.examw.test.support.PaperItemUtils;

/**
 * 试卷阅览控制器。
 * 
 * @author yangyong
 * @since 2014年9月23日
 */
@Controller
@RequestMapping(value = "/library/paper/preview")
public class PaperPreviewController {
	private static Logger logger = Logger.getLogger(PaperPreviewController.class);
	//注入试题服务接口。
	@Resource
	private IItemService itemService;
	//注入试卷预览服务接口。
	@Resource
	private IPaperPreviewService paperPreviewService;
	/**
	 * 试卷预览。
	 * @param paperId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/{paperId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String paperPreview(@PathVariable String paperId, Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("预览试卷［paperId = %s］...", paperId));
		PaperPreview paperPreview =  this.paperPreviewService.loadPaperPreview(paperId);
		if(paperPreview == null) throw new RuntimeException(String.format("试卷［paperId = %s］不存在!", paperId));
		model.addAttribute("paper", paperPreview);
		PaperItemUtils.addItemJudgeAnswers(this.itemService, model);
		return "library/paper_preview";
	}
	
	@RequiresPermissions({ModuleConstant.LIBRARY_PAPER + ":" + Right.VIEW})
	@RequestMapping(value="/front/{paperId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String paperFrontPreview(@PathVariable String paperId, Model model){
		if(logger.isDebugEnabled()) logger.debug(String.format("预览试卷［paperId = %s］...", paperId));
		PaperPreview paperPreview =  this.paperPreviewService.loadPaperPreview(paperId);
		if(paperPreview == null) throw new RuntimeException(String.format("试卷［paperId = %s］不存在!", paperId));
		model.addAttribute("PAPER", paperPreview);
		PaperItemUtils.addItemJudgeAnswers(this.itemService, model);
		model.addAttribute("ITEMLIST",this.findItemsList(paperPreview));
		model.addAttribute("paper_status_none_value", PaperStatus.NONE.getValue());
		model.addAttribute("paper_status_done_value", PaperStatus.AUDIT.getValue());
		return "library/paper_front_preview";
	}
	private List<StructureItemInfo> findItemsList(PaperPreview paper){
		if(paper == null) return null;
		List<StructureItemInfo> result = new ArrayList<StructureItemInfo>();
		List<StructureInfo> structures = paper.getStructures();
		if(structures == null || structures.size()==0) return result;
		for(StructureInfo s:structures){
			if(s == null) continue;
			this.getStructureItems(result, s);
		}
		return result;
	};
	private void getStructureItems(List<StructureItemInfo> result,StructureInfo info)
	{
		if(info.getChildren()!=null && info.getChildren().size()>0)
		{
			for(StructureInfo child:info.getChildren())
			{
				getStructureItems(result, child);
			}
		}else{
			TreeSet<StructureItemInfo> items = new TreeSet<StructureItemInfo>();
			if(info.getItems() == null || info.getItems().size()==0) return;
			items.addAll(info.getItems());
			for(StructureItemInfo item : items){
				if(item.getType().equals(ItemType.SHARE_TITLE.getValue())){
					result.addAll(getShareTitleSortedChildrenList(item)); 
				}
				else if(item.getType().equals(ItemType.SHARE_ANSWER.getValue())){
					result.addAll(getShareAnswerSortedChildrenList(item));
				}
				else{
					result.add(item);
				}
			}
		}
	}
	/*
	 * 获取共享题干题按序子题集合
	 */
	private List<StructureItemInfo> getShareTitleSortedChildrenList(StructureItemInfo item) {
		List<StructureItemInfo> list = new ArrayList<StructureItemInfo>();
		TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>();
		set.addAll(item.getChildren());
		for(StructureItemInfo info : set){
			list.add(info);
		}
		return list;
	}
	/*
	 * 获取共享答案题按序子题集合
	 */
	private List<StructureItemInfo> getShareAnswerSortedChildrenList(StructureItemInfo item) {
		List<StructureItemInfo> list = new ArrayList<StructureItemInfo>();
		TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>();
		set.addAll(item.getChildren());
		StructureItemInfo last = set.last();	//最后一个
		set.clear();
		set.addAll(last.getChildren());
		for(StructureItemInfo info : set){
			info.setPid(last.getPid());
			list.add(info);
		}
		return list;
	}
}