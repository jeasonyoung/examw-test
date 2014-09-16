package com.examw.test.junittest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.IItemService;
/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午6:06:08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test-hibernate-2.xml","classpath:spring-examw-test.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class MainTest {
	@Resource
	private IItemDao itemDao;
	@Resource
	private IItemService itemService;
	@Resource
	private SessionFactory sessionFactorySql;
	@Test
	public void ShiTis() throws JsonGenerationException, JsonMappingException, IOException{
		Session session = sessionFactorySql.openSession();
//		Transaction transaction= session.beginTransaction();
//		transaction.begin();
		@SuppressWarnings("unchecked")
		List<ShiTi> shiTiList = session.createQuery("from ShiTi where type =2").list();
		System.out.println(shiTiList.size());
//		session.getTransaction().commit();
//		transaction.commit();
//		session.close();
		ItemInfo info = new ItemInfo();
		List<ItemInfo> list = new ArrayList<ItemInfo>();
		for(ShiTi sts: shiTiList){
			info = duoXuan(sts);
			if(info!=null) list.add(info);
		}
		this.itemService.insertItemList(list);
	}
	//单选
	public ItemInfo danXuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		int[] a= null;
		String[] arr = content.split("###");
		info.setContent(arr[0]);
		Set<ItemInfo> set= new HashSet<ItemInfo>();
		String jieQu = null;
		char[] ans=answer.toCharArray();
		a = new int[ans.length];
		for (int j = 0; j < ans.length; j++) {
			a[j] = (int)ans[j]-64;
		}	
		for(int i=1;i<arr.length;i++){
			ItemInfo child = new ItemInfo();
			child.setOrderNo(i);
			child.setId(UUID.randomUUID().toString());
			child.setContent(arr[i]);
			for(int j=0; j<a.length;j++){
				if(i == a[j]){
					if(a.length == 1){
						answer = child.getId();
						jieQu = answer;
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
		info.setType(Item.TYPE_SINGLE);
		//
		if("1".equals( st.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( st.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( st.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		info.setOpt(4);
		info.setYear(2014);
		info.setStatus(Item.STATUS_NONE);
		return info;
	}
	//判断题
	public ItemInfo panDuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		if(st.getAnalyId() != null || st.getAnswer() == null)return null;
		info.setContent(st.getContent());
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setAnalysis(st.getAnalysis());
		info.setAnswer(st.getAnswer());
		info.setType(Item.TYPE_JUDGE);
		info.setSubjectId("53951b86-3578-4d72-8c81-00d6d111e9b1");
		info.setOpt(4);
		info.setStatus(Item.STATUS_NONE);
		return info;
	}
	//多选题
	public ItemInfo duoXuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		int[] a= null;
		String[] arr = content.split("###");
		info.setContent(arr[0]);
		Set<ItemInfo> set= new HashSet<ItemInfo>();
		String jieQu = null;
		if(answer.indexOf(",") != -1){
			String[] ans = answer.split(",");
			a = new int[ans.length];
			for (int j = 0; j < ans.length; j++) {
				a[j] = ans[j].toCharArray()[0]-64;
			}	
			for(int i=1;i<arr.length;i++){
				ItemInfo child = new ItemInfo();
				child.setOrderNo(i);
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
		info.setOpt(4);
		info.setStatus(Item.STATUS_NONE);
		return info;
	}
	//问答题
	public ItemInfo wenDa(ShiTi st){
		ItemInfo info = new ItemInfo();
		if(st.getAnalyId() != null ||st.getAnswer().equals(""))return null;
		info.setContent(st.getContent());
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setAnalysis(st.getAnalysis());
		info.setAnswer(st.getAnswer());
		info.setType(Item.TYPE_QANDA);
		info.setOpt(4);
		info.setStatus(Item.STATUS_NONE);
		//根据sql科目ID设置mysql科目ID
		info.setSubjectId("53951b86-3578-4d72-8c81-00d6d111e9b1");
//		if("1".equals( st.getClassId())){
//			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
//		}
//		if("2".equals( st.getClassId())){
//			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
//		}
//		if("3".equals( st.getClassId())){
//			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
//		}
		return info;
	}
	//不定项
	public ItemInfo buDingXi(ShiTi st){
		ItemInfo info = new ItemInfo();
		if(st.getAnalyId().equals("0"))return null;
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		int[] a= null;
		String[] arr = content.split("###");
		info.setContent(arr[0]);
		Set<ItemInfo> set= new HashSet<ItemInfo>();
		String jieQu = null;
		if(answer.indexOf(",") != -1){
			String[] ans = answer.split(",");
			a = new int[ans.length];
			for (int j = 0; j < ans.length; j++) {
				a[j] = ans[j].toCharArray()[0]-64;
			}	
			for(int i=1;i<arr.length;i++){
				ItemInfo child = new ItemInfo();
				child.setOrderNo(i);
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
		}else{
			char[] ans=answer.toCharArray();
			a = new int[ans.length];
			for (int j = 0; j < ans.length; j++) {
				a[j] = (int)ans[j]-64;
			}
			for(int i=1;i<arr.length;i++){
				ItemInfo child = new ItemInfo();
				child.setOrderNo(i);
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
							as = answer.substring(2);
							jieQu = as.substring(0,as.length()-1);
						}
						if(a.length == 3){
							answer = answer + child.getId()+",";
							as = answer.substring(3);
							jieQu = as.substring(0,as.length()-1);
						}
						if(a.length ==4){
							answer = answer + child.getId()+",";
							as = answer.substring(4);
							jieQu = as.substring(0,as.length()-1);						}
					}
				}
				info.setOrder(String.valueOf(i));
				set.add(child);
			}
		}
		info.setChildren(set);
		info.setAnswer(jieQu);
		Integer level=Integer.parseInt(st.getId());
		info.setLevel(level);
		info.setAnalysis(st.getAnalysis());
		info.setType(Item.TYPE_UNCERTAIN);
		info.setOpt(4);
		info.setStatus(Item.STATUS_NONE);
		info.setSubjectId("53951b86-3578-4d72-8c81-00d6d111e9b1");
//		if("1".equals( st.getClassId())){
//			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
//		}
//		if("2".equals( st.getClassId())){
//			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
//		}
//		if("3".equals( st.getClassId())){
//			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
//		}
		return info;
	}
	//共享题干题的单选
	public ItemInfo tiGanDanXuan(ShiTi st){
		ItemInfo info = new ItemInfo();
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		if(answer == null) return null;
		int[] a= null;
		char[] ans=answer.toCharArray();
		a = new int[ans.length];
		for (int j = 0; j < ans.length; j++) {
			a[j] = (int)ans[j]-64;
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
		child.setOrderNo(1);
		child.setOpt(4);
		child.setStatus(Item.STATUS_NONE);
		two.add(child);
		info.setChildren(two);
		//选项
		Set<ItemInfo> childrens = new HashSet<ItemInfo>();
		for(int i=1;i<arr.length;i++){
			ItemInfo children = new ItemInfo();
			children.setId(UUID.randomUUID().toString());
			children.setContent(arr[i]);
			children.setOrderNo(i);
			for(int j=0; j<a.length;j++){
				if(i == a[j]){
					if(a.length == 1){
					answer = child.getId();
					info.setAnswer(answer);
				}
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
			children.setOpt(4);
			children.setStatus(Item.STATUS_NONE);
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
		child.setOpt(4);
		child.setStatus(Item.STATUS_NONE);
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
		Set<ItemInfo> set= new HashSet<ItemInfo>();
		int[] a =null;
		String jieQu = null;
		if(answer.indexOf(",") !=-1){
			String[] ans = answer.split(",");
			a = new int[ans.length];
			for (int j = 0; j < ans.length; j++) {
				a[j] = ans[j].toCharArray()[0]-64;
			}	
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
		}else{
			char[] ans=answer.toCharArray();
			a = new int[ans.length];
			for (int j = 0; j < ans.length; j++) {
				a[j] = (int)ans[j]-64;
			}
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
							as = answer.substring(2);
							jieQu = as.substring(0,as.length()-1);
						}
						if(a.length == 3){
							answer = answer + childs.getId()+",";
							as = answer.substring(3);
							jieQu = as.substring(0,as.length()-1);
						}
						if(a.length ==4){
							answer = answer + childs.getId()+",";
							as = answer.substring(4);
							jieQu = as.substring(0,as.length()-1);
						}
					}
				}
				info.setOrder(String.valueOf(i));
				set.add(childs);
			}
			child.setChildren(set);
			child.setAnswer(jieQu);
		}
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
			children.setOpt(4);
			children.setStatus(Item.STATUS_NONE);
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