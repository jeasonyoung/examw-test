package com.examw.test.junittest;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.junit.domain.Question;
import com.examw.test.model.library.ItemInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午6:06:08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test-sql.xml","classpath:spring-examw-test.xml"})
@TransactionConfiguration(defaultRollback=false)
public class MainTest {
	private static Logger logger = Logger.getLogger(MainTest.class);
	@Resource
	private IItemDao itemDao;
	@Resource
	private SessionFactory sessionFactorySql;
	@Transactional()
	@Test
	public void show() throws JsonGenerationException, JsonMappingException, IOException{
//		Session session = sessionFactorySql.openSession();
//		session.beginTransaction();
//		@SuppressWarnings("unchecked")
//		List<ShiTi> questionList = session.createQuery("from ShiTi").list();
//		System.out.println(questionList.size());
//		session.getTransaction().commit();
//		session.close();
//		Item item = new Item();
//		item.setId("11111111111111111111111111111111111");
//		itemDao.save(item);
//		System.out.println(item);
		Question question = new Question();
		question.setContent("会计档案的定期保管期限最短的为()。###3年###2年###4年###5年");
		question.setAnalysis("会计档案的定期保管期限分为3年、5年、10年、15年和25年五类。");
		question.setAnswer("ABC");
		ItemInfo info = multiSelect(question);
		//转化成JSON 输出,看是不是和从网页的差不多 ,差不多就可以插入
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(System.out, info);
	}
	//单选题 数据转换 
	private ItemInfo convert(Question question){
		ItemInfo info = new ItemInfo();
		String content = question.getContent();
		String answer = question.getAnswer().toUpperCase();
		//答案ABCD 转换成选项数组对应的 索引序号
		// 多选题的答案要进行拆分, 
		int answer_index = answer.charAt(0)-64;	
		logger.info("answer index ================== "+answer_index);
		System.out.println(answer_index);
		String[] arr = content.split("###");
		//设置题干
		info.setContent(arr[0]);
		//设置选项[选项就是这个题目的子类]
		Set<ItemInfo> children = new HashSet<ItemInfo>();
		for(int i=1;i<arr.length;i++){
			ItemInfo child = new ItemInfo();
			child.setId(UUID.randomUUID().toString());
			child.setContent(arr[i]);
			if(i == answer_index){
				answer = child.getId();
			}
			child.setOrder(String.valueOf(i));
			children.add(child);
		}
		info.setChildren(children);
		info.setAnswer(answer);
		info.setAnalysis(question.getAnalysis());
		info.setExamId("");
		info.setSubjectId("");
//		info.setOpt(opt);
//		info.setStatus(status);
//		info.setLevel(level);
//		info.setYear(year);
		//不同的题型要去对应
//		info.setType(type);
		return info;
	}
	
	private ItemInfo multiSelect(Question question){
		ItemInfo info = new ItemInfo();
		String content = question.getContent();
		String answer = question.getAnswer().toUpperCase();
		String trueAnswer = "";
		if(!StringUtils.isEmpty(answer)){
			if(answer.contains(",")){ //"A,B,C,D"型的答案
				String[] answers1 = answer.split(",");
				
				String[] arr = content.split("###");
				//设置题干
				info.setContent(arr[0]);
				//设置选项[选项就是这个题目的子类]
				Set<ItemInfo> children = new HashSet<ItemInfo>();
				for(int i=1;i<arr.length;i++){
					ItemInfo child = new ItemInfo();
					child.setId(UUID.randomUUID().toString());
					child.setContent(arr[i]);
					if(judgeWithAnswer(i,answers1)){
						trueAnswer = trueAnswer + child.getId()+",";
					}
					child.setOrder(String.valueOf(i));
					children.add(child);
				}
				info.setChildren(children);
				if(trueAnswer.endsWith(","))
					info.setAnswer(trueAnswer.substring(0,trueAnswer.lastIndexOf(",")));
				else
					info.setAnswer(trueAnswer);
				info.setAnalysis(question.getAnalysis());
				info.setExamId("");
				info.setSubjectId("");
//				info.setOpt(opt);
//				info.setStatus(status);
//				info.setLevel(level);
//				info.setYear(year);
				//不同的题型要去对应
//				info.setType(type);
				return info;
			}else{	//"ABCD"型的答案
				char[] answers2 = answer.toCharArray();
				String[] arr = content.split("###");
				//设置题干
				info.setContent(arr[0]);
				//设置选项[选项就是这个题目的子类]
				Set<ItemInfo> children = new HashSet<ItemInfo>();
				for(int i=1;i<arr.length;i++){
					ItemInfo child = new ItemInfo();
					child.setId(UUID.randomUUID().toString());
					child.setContent(arr[i]);
					if(judgeWithAnswer(i,answers2)){
						trueAnswer = trueAnswer + child.getId()+",";
					}
					child.setOrder(String.valueOf(i));
					children.add(child);
				}
				info.setChildren(children);
				if(trueAnswer.endsWith(","))
					info.setAnswer(trueAnswer.substring(0,trueAnswer.lastIndexOf(",")));
				else
					info.setAnswer(trueAnswer);
				info.setAnalysis(question.getAnalysis());
				info.setExamId("");
				info.setSubjectId("");
//				info.setOpt(opt);
//				info.setStatus(status);
//				info.setLevel(level);
//				info.setYear(year);
				//不同的题型要去对应
//				info.setType(type);
				return info;
			}
		}
		return info;
	}
	/**
	 * 判断序号是否与多选的答案对应
	 * @param i
	 * @param answers1 字符串数组
	 * @return
	 */
	private boolean judgeWithAnswer(int i, String[] answers1) {
		for(String s:answers1)
		{
			int answer_index = s.charAt(0)-64;	
			if(i == answer_index)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断序号是否与多选的答案对应
	 * @param i
	 * @param answers1
	 * @return
	 */
	private boolean judgeWithAnswer(int i, char[] answers1) {
		for(char s:answers1)
		{
			int answer_index = s -64;	
			if(i == answer_index)
			{
				return true;
			}
		}
		return false;
	}
}
