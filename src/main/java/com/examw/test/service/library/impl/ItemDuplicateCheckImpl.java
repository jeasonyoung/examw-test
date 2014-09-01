package com.examw.test.service.library.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.service.library.IItemDuplicateCheck;
import com.examw.utils.MD5Util;
/**
 * 试题重复检查校验码计算接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月21日
 */
public class ItemDuplicateCheckImpl implements IItemDuplicateCheck {
	private static final Logger logger = Logger.getLogger(ItemDuplicateCheckImpl.class);
	private static final String [][] stripHTMLRegexs = {
																						 {"<script[^>]*?>.*?</script>",""},
																						 //{"<(\\/\\s*)?!?((\\w+:)?\\w+)(\\w+(\\s*=?\\s*(([\"\"'])(\\[\"\"'tbnr]|[^\\7])*?\\7|\\w+)|.{0})|\\s)*?(\\/\\s*)?>",""},
																						 {"<[/]?.+?>",""},
																						 {"([\\r\\n])[\\s]+",""},
																						 {"&(quot|#34);","\""},
																						 {"&(amp|#38);","&"},
																						 {"&(lt|#60);","<"},
																						 {"&(gt|#62);",">"},
																						 {"&(nbsp|#160);",""},
																						 {"&(iexcl|#161);","\\xa1"},
																						 {"&(cent|#162);","\\xa2"},
																						 {"&(pound|#163);","\\xa3"},
																						 {"&(copy|#169);","\\xa9"},
																						 {"&#(\\d+);",""},
																						 {"-->","\r\n"},
																						 {"<!--.*\n",""}
																					};
		private static final String item_options_start = "^[A-Z|a-z|0-9]+(\\.|,|，|、|。)";
	/*
	 * 计算校验码。
	 * @see com.examw.test.service.library.IItemDuplicateCheck#computeCheckCode(com.examw.test.model.library.ItemInfo)
	 */
	@Override
	public String computeCheckCode(BaseItemInfo<?> info) {
		if(logger.isDebugEnabled()) logger.debug("开始计算校验码...");
		if(info == null) return null;
		List<String> topics = new ArrayList<>(), opts = new ArrayList<>(),
				answers = new ArrayList<>(), analysis = new ArrayList<>();
		//拆分数据
		this.splitSymbol(info, topics, opts, answers, analysis);
		StringBuilder sources = new StringBuilder();
		sources.append(this.joinSortCollection(topics))
		            .append(this.joinSortCollection(opts))
		            .append(this.joinSortCollection(answers))
		            .append(this.joinSortCollection(analysis));
		if(sources.length() == 0) return null;
		String checkCode = MD5Util.MD5(sources.toString());
		if(logger.isDebugEnabled()) logger.debug(String.format("计算出的校验码：%s", checkCode));
		return checkCode;
	}
	//拼接排序集合。
	private String joinSortCollection(List<String> sources){
		if(sources == null  || sources.size() == 0) return "";
		if(logger.isDebugEnabled()) logger.debug(String.format("排序前字符串：%s",  Arrays.toString(sources.toArray(new String[0]))));
		Collections.sort(sources);
		String target = Arrays.toString(sources.toArray(new String[0]));
		if(logger.isDebugEnabled()) logger.debug(String.format("排序后字符串：%s", target));
		return target;
	}
	//拆分字符数据
	private void splitSymbol(BaseItemInfo<?> info, List<String> topics, List<String> opts, List<String> answers,  List<String> analysis){
		if(info == null)return;
		//this.push(info.getAnswer(), answers);//答案数据；
		//this.push(info.getAnalysis(), analysis);//答案解析；
		if(info.getChildren() != null && info.getChildren().size() > 0){
			this.push(info.getContent(), topics);
			for(BaseItemInfo<?> itemInfo : info.getChildren()){
				if(itemInfo == null) continue;
				this.splitSymbol(itemInfo, topics, opts, answers, analysis);
			}
			return;
		}
		this.push(info.getContent(), opts, true);
	}
	//压入数据
	private void push(String source, List<String> targets){
		this.push(source, targets,false);
	}
	//压入数据
	private void push(String source, List<String> targets, boolean isOpts){
		if(targets == null || StringUtils.isEmpty(source)) return;
		if(logger.isDebugEnabled()) logger.debug(String.format("[去格式化之前数据] %s", source));
		String target = this.replaceSymbol(source);
		if(isOpts && !StringUtils.isEmpty(target)){
			target = target.replaceFirst(item_options_start, "");
		}
		if(logger.isDebugEnabled()) logger.debug(String.format("[去格式化之后数据] %s", target));
		if(!StringUtils.isEmpty(target)){
			targets.add(target);
		}
	}
	//字符替换
	private String replaceSymbol(String source){
		if(StringUtils.isEmpty(source)) return source;
		for(int i = 0; i < stripHTMLRegexs.length; i++){
		   source = source.replaceAll(stripHTMLRegexs[i][0], stripHTMLRegexs[i][1].trim());
		}
		source.replaceAll("<", "");
		source.replaceAll(">", "");
		source.replaceAll("\r\n", "");
		return source;
	}
}