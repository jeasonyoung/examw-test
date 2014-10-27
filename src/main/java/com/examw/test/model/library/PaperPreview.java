package com.examw.test.model.library;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * 试卷预览信息。
 * 
 * @author yangyong
 * @since 2014年8月15日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PaperPreview extends BasePaperInfo {
	private static final long serialVersionUID = 1L;
	private List<StructureInfo> structures;
	/**
	 * 获取试卷结构集合。
	 * @return 试卷结构集合。
	 */
	public List<StructureInfo> getStructures() {
		return structures;
	}
	/**
	 * 设置试卷结构集合。
	 * @param structures 
	 *	  试卷结构集合。
	 */
	public void setStructures(List<StructureInfo> structures) {
		this.structures = structures;
	}
	private Integer total;	//[Add by FW 2014.10.27还是加上这个属性]
	/**
	 * 获取试卷题目总数。
	 * @return 试卷题目总数。
	 */
	public Integer getTotal() {
		this.total = 0;
		if(this.structures != null && this.structures.size() > 0){
			for(StructureInfo info : this.structures){
				if(info == null || info.getTotal() == null) continue;
				this.total += info.getTotal();
			}
		}
		return this.total;
	}
	/**
	 * 设置 试卷题目总数。
	 * @param total
	 * 试卷题目总数。
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}