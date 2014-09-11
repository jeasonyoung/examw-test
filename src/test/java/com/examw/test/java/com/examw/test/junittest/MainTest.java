package com.examw.test.junittest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.junit.domain.ShiTi;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.IItemService;
/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午6:06:08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test-hibernate-2.xml","classpath:spring-examw-test.xml"})
public class MainTest {
	@Resource
	private IItemDao itemDao;
	@Resource
	private IItemService itemService;
	@Resource
	private SessionFactory sessionFactorySql;
//	//@Transactional
//	//@Test
//	public void show(){
//		Session session = sessionFactorySql.openSession();
//		session.beginTransaction();
//		@SuppressWarnings("unchecked")
//		List<Question> questionList = session.createQuery("from Question").list();
//		System.out.println(questionList.size());
//		session.getTransaction().commit();
//		session.close();
//		Item item = itemDao.load(Item.class, "ef50d06b-9f47-4767-b4d5-99e77105998f");
//		System.out.println(item);
//	}
	@Transactional
	@Test
	public void ShiTis() throws JsonGenerationException, JsonMappingException, IOException{
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<ShiTi> shiTiList = session.createQuery("from ShiTi").list();
		System.out.println(shiTiList.size());
		session.getTransaction().commit();
		session.close();
		ItemInfo info = new ItemInfo();
		//for(ShiTi sts: shiTiList){
			//根据类型判断调用类型方法
			ShiTi sts = new ShiTi();
			sts.setAnalysis("应付账款=(498000-345000-52000)-18000-16000-35000=32000(元)");
			sts.setAnswer("B,C");
			sts.setClassId("1");
			sts.setId("1");
			sts.setType(3);
			sts.setContent("应付账款的金额是###28000###32000###45000###60000");
			if(Item.TYPE_SINGLE == sts.getType()){
				//单选题
				info = danXuan(sts);
			}
			if(Item.TYPE_MULTY == sts.getType()){
				//多选题
				info = duoXuan(sts);
			}
			if(Item.TYPE_UNCERTAIN == sts.getType()){
				//不定项题
			
				info = buDingXi(sts);
			}
			if (Item.TYPE_JUDGE == sts.getType()){
				//判断题
				info = panDuan(sts);
			}
			if(Item.TYPE_QANDA == sts.getType()){
				//问答题
				info = wenDa(sts);
		//	}
//			if(Item.TYPE_SHARE_TITLE == sts.getType()){
//				//共享题干题
//				if(Item.TYPE_JUDGE == sts.getType()){
//					//判断题
//					info = tiGanPanDuan(sts);
//				}
//				if(Item.TYPE_MULTY == sts.getType()){
//					//多选题
//					info = tiGanDuoXuan(sts);
//				}
//				if(Item.TYPE_QANDA == sts.getType()){
//					//问答题
//					info = tiGanWenDa(sts);
//				}
//				if(Item.TYPE_SINGLE == sts.getType()){
//					//单选题
//					info = tiGanDanXuan(sts);
//				}
//				if(Item.TYPE_UNCERTAIN == sts.getType()){
//					//不定项
//					info = tiGanBuDingXiang(sts);
//				}
//		}
		}
//		if(null != info){
//			itemService.update(info);
//		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(System.out, info);
	}
	//单选
	public ItemInfo danXuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		int answer_index = 0;
		if(answer != null){
			answer_index = answer.charAt(0)-64;	
		}else{
			return null;
		}
		String[] arr = content.split("###");
		info.setContent(arr[0]);
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
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setAnalysis(st.getAnalysis());
		info.setType(Item.TYPE_SINGLE);
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		return info;
	}
	//判断题
	public ItemInfo panDuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		if(st.getAnswer() == null)return null;
		info.setContent(st.getContent());
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setAnalysis(st.getAnalysis());
		info.setAnswer(st.getAnswer());
		info.setType(Item.TYPE_JUDGE);
		//根据sql科目ID设置mysql科目ID
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		return info;
	}
	//多选题
	public ItemInfo duoXuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		String[] ans = answer.split(",");
		int[] a = new int[ans.length];
		for (int j = 0; j < ans.length; j++) {
			a[j] = ans[j].toCharArray()[0]-64;
		}	
		String[] arr = content.split("###");
		info.setContent(arr[0]);
		Set<ItemInfo> set= new HashSet<ItemInfo>();
		String jieQu = new String();
		for(int i=1;i<arr.length;i++){
			ItemInfo child = new ItemInfo();
			child.setId(UUID.randomUUID().toString());
			child.setContent(arr[i]); 
			for(int j=0; j<a.length;j++){
				String as = new String();
				if(i == a[j]){
					if(a.length == 1){
						answer = child.getId();
						jieQu = answer;
					}
					if(a.length == 2){
						answer = answer + child.getId()+",";
						as = answer.substring(3);
						jieQu = as.substring(0,as.length()-1);
					}
					if(a.length == 3){
						answer = answer + child.getId()+",";
						as = answer.substring(5);
						jieQu = as.substring(0,as.length()-1);
					}
					if(a.length ==4){
						answer = answer + child.getId()+",";
						as = answer.substring(7);
						jieQu = as.substring(0,as.length()-1);
					}
				}
			}
			info.setOrder(String.valueOf(i));
			set.add(child);
		}
		info.setChildren(set);
		info.setAnswer(jieQu);
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setAnalysis(st.getAnalysis());
		info.setType(Item.TYPE_MULTY);
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		return info;
	}
	//问答题
	public ItemInfo wenDa(ShiTi st){
		ItemInfo info = new ItemInfo();
		if(st.getAnswer() == null)return null;
		info.setContent(st.getContent());
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setAnalysis(st.getAnalysis());
		info.setAnswer(st.getAnswer());
		info.setType(Item.TYPE_QANDA);
		//根据sql科目ID设置mysql科目ID
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		return info;
	}
	//不定项
	public ItemInfo buDingXi(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		int[] a= null;
		if(answer.indexOf(",") != -1){
			String[] ans = answer.split(",");
			a = new int[ans.length];
			for (int j = 0; j < ans.length; j++) {
				a[j] = ans[j].toCharArray()[0]-64;
			}	
		}else{
			char[] ans=answer.toCharArray();
			a = new int[ans.length];
			for (int j = 0; j < ans.length; j++) {
				a[j] = (int)ans[j]-64;
			}
		}
		String[] arr = content.split("###");
		info.setContent(arr[0]);
		//答案只有一个就调用单选的方法
		if(Item.TYPE_UNCERTAIN == st.getType() && a.length == 1){
			int answer_index = answer.charAt(0)-64;	
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
			Integer level=Integer.parseInt(st.getId());
			info.setLevel(level);
			info.setAnalysis(st.getAnalysis());
			info.setAnswer(answer);
			info.setType(Item.TYPE_UNCERTAIN);
			if("1".equals( st.getClassId())){
				info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
			}
			if("2".equals( st.getClassId())){
				info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
			}
			if("3".equals( st.getClassId())){
				info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
			}
			return info;
		}
		//答案有多个就调用多选的方法
		if(Item.TYPE_UNCERTAIN == st.getType()&& a.length > 1){
			Set<ItemInfo> set= new HashSet<ItemInfo>();
			String jieQu = null;
			for(int i=1;i<arr.length;i++){
				ItemInfo child = new ItemInfo();
				child.setId(UUID.randomUUID().toString());
				child.setContent(arr[i]);
				for(int j=0; j<a.length;j++){
					String as = null;
					if(i == a[j]){
						if(a.length == 1){
							answer = child.getId();
							jieQu = answer;
						}
						if(a.length == 2){
							answer = answer + child.getId()+",";
							as = answer.substring(3);
							jieQu = as.substring(0,as.length()-1);
						}
						if(a.length == 3){
							answer = answer + child.getId()+",";
							as = answer.substring(5);
							jieQu = as.substring(0,as.length()-1);
						}
						if(a.length ==4){
							answer = answer + child.getId()+",";
							as = answer.substring(7);
							jieQu = as.substring(0,as.length()-1);
						}
					}
				}
				info.setOrder(String.valueOf(i));
				set.add(child);
			}
			info.setChildren(set);
			info.setAnswer(jieQu);
			Integer level=Integer.parseInt(st.getId());
			info.setLevel(level);
			info.setAnalysis(st.getAnalysis());
			info.setType(Item.TYPE_MULTY);
			if("1".equals( st.getClassId())){
				info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
			}
			if("2".equals( st.getClassId())){
				info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
			}
			if("3".equals( st.getClassId())){
				info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
			}
		}
		return info;
	}
	//共享题干题单选
	public ItemInfo tiGanDanXuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		if(answer == null) return null;
		int answer_index = 0;
		try {
			answer_index = answer.charAt(0)-64;	
		} catch (Exception e) {
			System.out.print(answer+"kkkkkkk");
		}
		String[] arr = content.split("###");
		//一级题干
		info.setContent(arr[0]);
		//二级题干
		Set<ItemInfo> two = new HashSet<ItemInfo>();
		ItemInfo child = new ItemInfo();
		child.setId(UUID.randomUUID().toString());
		child.setContent(arr[0]);
		child.setType(Item.TYPE_SINGLE);
		child.setAnalysis(st.getAnalysis());
		two.add(child);
		info.setChildren(two);
		//选项
		Set<ItemInfo> childrens = new HashSet<ItemInfo>();
		for(int i=1;i<arr.length;i++){
			ItemInfo children = new ItemInfo();
			children.setId(UUID.randomUUID().toString());
			children.setContent(arr[i]);
			if(i == answer_index){
				answer = children.getId();
			}
			child.setOrder(String.valueOf(i));
			childrens.add(children);
		}
		child.setChildren(childrens);
		child.setAnswer(answer);
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setType(Item.TYPE_SHARE_TITLE);
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		return info;
	}
	//共享题干的判断题
	public ItemInfo tiGanPanDuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		info.setContent(content);
		Set<ItemInfo> child = new HashSet<ItemInfo>();
			ItemInfo children = new ItemInfo();
			children.setId(UUID.randomUUID().toString());
			children.setContent(content);
			children.setAnalysis(st.getAnalysis());
			children.setAnswer(st.getAnswer());
			children.setType(Item.TYPE_JUDGE);
			child.add(children);
		info.setChildren(child);
		info.setType(Item.TYPE_SHARE_TITLE);
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		return info;	
	}
	//共享题干的多选题
	public ItemInfo tiGanDuoXuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		String[] ans = answer.split(",");
		int[] a = new int[ans.length];
		for (int j = 0; j < ans.length; j++) {
			a[j] = ans[j].toCharArray()[0]-64;
		}	
		String[] arr = content.split("###");
		//一级题干
		info.setContent(arr[0]);
		//二级题干
		Set<ItemInfo> two = new HashSet<ItemInfo>();
		ItemInfo child = new ItemInfo();
		child.setId(UUID.randomUUID().toString());
		child.setContent(arr[0]);
		child.setType(Item.TYPE_MULTY);
		child.setAnalysis(st.getAnalysis());
		two.add(child);
		info.setChildren(two);
		//选项
		Set<ItemInfo> childrens = new HashSet<ItemInfo>();
		String jieQu = null; 
		for(int i=1;i<arr.length;i++){
			ItemInfo children = new ItemInfo();
			children.setId(UUID.randomUUID().toString());
			children.setContent(arr[i]);
			for(int j=0; j<a.length;j++){
				String as = null;
				if(i == a[j]){
					if(a.length == 1){
						answer = children.getId();
						jieQu = answer;
					}
					if(a.length == 2){
						answer = answer + children.getId()+",";
						as = answer.substring(3);
						jieQu = as.substring(0,as.length()-1);
					}
					if(a.length == 3){
						answer = answer + children.getId()+",";
						as = answer.substring(5);
						jieQu = as.substring(0,as.length()-1);
					}
					if(a.length ==4){
						answer = answer + children.getId()+",";
						as = answer.substring(7);
						jieQu = as.substring(0,as.length()-1);
					}
				}
			}
			info.setOrder(String.valueOf(i));
			childrens.add(children);
		}
		child.setChildren(childrens);
		child.setAnswer(jieQu);
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setType(Item.TYPE_SHARE_TITLE);
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		return info;
	}
	//共享题干的不定项
	public ItemInfo tiGanBuDingXiang(ShiTi st){
		ItemInfo  info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		String[] ans = answer.split(",");
		int[] a = new int[ans.length];
		for (int j = 0; j < ans.length; j++) {
			a[j] = ans[j].toCharArray()[0]-64;
		}	
		String jieQu = null;
		String[] arr = content.split("###");
		//一级题目
		info.setContent(arr[0]);
		//二级题目
		Set<ItemInfo> one = new HashSet<ItemInfo>();
		ItemInfo child = new ItemInfo();
		child.setId(UUID.randomUUID().toString());
		child.setAnalysis(st.getAnalysis());
		child.setType(Item.TYPE_UNCERTAIN);
		one.add(child);
		info.setChildren(one);
		//答案只有一个
		if(Item.TYPE_UNCERTAIN == st.getType()&& a.length == 1){
			Set<ItemInfo> two = new HashSet<ItemInfo>();
			ItemInfo children = new ItemInfo();
			for(int i=1;i<arr.length;i++){
				children.setId(UUID.randomUUID().toString());
				children.setContent(arr[i]);
				int answer_index = answer.charAt(0)-64;
				if(i == answer_index){
					answer = children.getId();
				}
				info.setOrder(String.valueOf(i));
				two.add(children);
			}
			child.setChildren(two);
			child.setAnswer(answer);
			info.setType(Item.TYPE_SHARE_TITLE);
			Integer level=Integer.parseInt(st.getId());
			info.setLevel(level);
			if("1".equals( st.getClassId())){
				info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
			}
			if("2".equals( st.getClassId())){
				info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
			}
			if("3".equals( st.getClassId())){
				info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
			}
			return info;
		}
		//答案有多个
		if(Item.TYPE_UNCERTAIN == st.getType() && a.length >1){
			Set<ItemInfo> set= new HashSet<ItemInfo>();
			for(int i=1;i<arr.length;i++){
				ItemInfo childs = new ItemInfo();
				childs.setId(UUID.randomUUID().toString());
				childs.setContent(arr[i]);
				for(int j=0; j<a.length;j++){
					String as = null;
					if(i == a[j]){
						if(a.length == 1){
							answer = childs.getId();
							jieQu = answer;
						}
						if(a.length == 2){
							answer = answer + childs.getId()+",";
							as = answer.substring(3);
							jieQu = as.substring(0,as.length()-1);
						}
						if(a.length == 3){
							answer = answer + childs.getId()+",";
							as = answer.substring(5);
							jieQu = as.substring(0,as.length()-1);
						}
						if(a.length ==4){
							answer = answer + childs.getId()+",";
							as = answer.substring(7);
							jieQu = as.substring(0,as.length()-1);
						}
					}
				}
				info.setOrder(String.valueOf(i));
				set.add(childs);
			}
			child.setChildren(set);
			child.setAnswer(jieQu);
			Integer level=Integer.parseInt(st.getId());
			info.setLevel(level);
			info.setType(Item.TYPE_MULTY);
			if("1".equals( st.getClassId())){
				info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
			}
			if("2".equals( st.getClassId())){
				info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
			}
			if("3".equals( st.getClassId())){
				info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
			}
		}
		return info;
	}
	//共享题干的问答题
	public ItemInfo tiGanWenDa(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		info.setContent(content);
		Set<ItemInfo> child = new HashSet<ItemInfo>();
			ItemInfo children = new ItemInfo();
			children.setId(UUID.randomUUID().toString());
			children.setContent(content);
			children.setAnalysis(st.getAnalysis());
			children.setAnswer(st.getAnswer());
			children.setType(Item.TYPE_QANDA);
			child.add(children);
		info.setChildren(child);
		info.setType(Item.TYPE_SHARE_TITLE);
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		return info;
	}
}