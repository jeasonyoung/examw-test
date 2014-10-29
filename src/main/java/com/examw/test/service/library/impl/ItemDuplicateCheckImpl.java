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
		private static final String punctuation_regex = "[\\.|,|;|，|、|。|\\(|\\)|\\:|'|\"]+";
	/*
	 * 计算校验码。
	 * @see com.examw.test.service.library.IItemDuplicateCheck#computeCheckCode(com.examw.test.model.library.ItemInfo)
	 */
	@Override
	public String computeCheckCode(BaseItemInfo<?> info) {
		if(logger.isDebugEnabled()) logger.debug("开始计算校验码...");
		if(info == null) return null;
		List<String> topics = new ArrayList<>(), opts = new ArrayList<>();
		//拆分数据
		splitSymbol(info, topics, opts);
		//拆分后处理
		StringBuilder sources = new StringBuilder();
		sources.append(joinSortCollection(topics)).append(joinSortCollection(opts));
		String source = sources.toString();
		source = source.replaceAll(punctuation_regex, "").replaceAll("\\s+", "");
		if(logger.isDebugEnabled()) logger.debug(String.format("计算校验码的目标字符串：%s", source));
		String checkCode = MD5Util.MD5(source);
		if(logger.isDebugEnabled()) logger.debug(String.format("计算出的校验码：%s", checkCode));
		return checkCode;
	}
	//拼接排序集合。
	private static String joinSortCollection(List<String> sources){
		if(sources == null  || sources.size() == 0) return "";
		if(logger.isDebugEnabled()) logger.debug(String.format("排序前字符串：%s",  Arrays.toString(sources.toArray(new String[0]))));
		Collections.sort(sources);
		StringBuilder buf = new StringBuilder();
		for(String source : sources){
			if(StringUtils.isEmpty(source)) continue;
			buf.append(source);
		}
		String target = buf.toString();
		if(logger.isDebugEnabled()) logger.debug(String.format("排序后字符串：%s", target));
		return target;
	}
	//拆分字符数据
	private static void splitSymbol(BaseItemInfo<?> info, List<String> topics, List<String> opts){
		if(info == null)return;
		//this.push(info.getAnswer(), answers);//答案数据；
		//this.push(info.getAnalysis(), analysis);//答案解析；
		if(info.getChildren() != null && info.getChildren().size() > 0){
			push(info.getContent(), topics, false);
			for(BaseItemInfo<?> itemInfo : info.getChildren()){
				if(itemInfo == null) continue;
				splitSymbol(itemInfo, topics, opts);
			}
			return;
		}
		push(info.getContent(), opts, true);
	}
	//压入数据
	private static void push(String source, List<String> targets, boolean isOpts){
		if(targets == null || StringUtils.isEmpty(source)) return;
		if(logger.isDebugEnabled()) logger.debug(String.format("[去格式化之前数据] %s", source));
		String target = replaceSymbol(source);
		if(isOpts && !StringUtils.isEmpty(target)){
			target = target.replaceFirst(item_options_start, "");
		}
		if(logger.isDebugEnabled()) logger.debug(String.format("[去格式化之后数据] %s", target));
		if(!StringUtils.isEmpty(target)){
			targets.add(target);
		}
	}
	//字符替换
	private static String replaceSymbol(String source){
		if(StringUtils.isEmpty(source)) return source;
		source = toSemiangle(source);
		for(int i = 0; i < stripHTMLRegexs.length; i++){
		   source = source.replaceAll(stripHTMLRegexs[i][0], stripHTMLRegexs[i][1].trim());
		}
		source.replaceAll("<", "");
		source.replaceAll(">", "");
		source.replaceAll("\r\n", "");
		return source;
	}
	//转半角。
	//全角空格为12288，半角空格为32
	//其他半角字符（33-126）与全角（65281-65374）的对应关系是：均相差65248
	private static String toSemiangle(String source){
		if(logger.isDebugEnabled()) logger.debug(String.format("转半角前：%s", source));
		if(StringUtils.isEmpty(source)) return source;
		final int sbc_char_start = 65281,/*全角字符开始*/
					sbc_char_end = 65374,/*全角字符结束*/
					offset = 65248,/*与半角的偏移量*/
					sbc_space = 12288,/*全角空格*/
					dbc_space = 32;/*半角空格*/
		char[] chars = source.toCharArray();
		for(int i = 0; i < chars.length; i++){
			if(chars[i] == sbc_space){
				chars[i] = dbc_space;
			}else if(chars[i] >= sbc_char_start && chars[i] <= sbc_char_end){
				chars[i] = (char)(chars[i] - offset);
			}
		}
		String target = String.valueOf(chars);
		if(logger.isDebugEnabled()) logger.debug(String.format("转半角后：%s", target));
		return target;
	}
}