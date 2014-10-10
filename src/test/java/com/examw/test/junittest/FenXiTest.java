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
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemStatus;
import com.examw.test.service.library.ItemType;
/**
 * 
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test-hibernate-2.xml","classpath:spring-examw-test.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class FenXiTest {
	@Resource
	private IItemDao itemDao;
	@Resource
	private IItemService itemService;
	@Resource
	private SessionFactory sessionFactorySql;
	//@Test
	public void fenXi() throws JsonGenerationException, JsonMappingException, IOException{
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<CaiLianFenXi> list = session.createQuery("from CaiLianFenXi").list();
		System.out.println(list.size());
		session.getTransaction().commit();
		session.close();
		ItemInfo info = new ItemInfo();
		for(CaiLianFenXi fx : list) {
			info = fenXiTi(fx) ;
			itemService.update(info);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(System.out, info);
	}
	//分析题
	public ItemInfo fenXiTi(CaiLianFenXi clfx){
		ItemInfo info = new  ItemInfo();
		String content=clfx.getContent();
		info.setType(ItemType.SHARE_TITLE.getValue());
		//正则表达式进行替换</P><P>为<br/> 删除不是<br/><img>的标签
		content = content.replaceAll("</[p|P]><[p|P](.+?)>", "<br/>");
		content = content.replaceAll("<(?!/?(?i)(img|br)).*?>", "");
		info.setContent(content);
		info.setOpt(4);
		info.setStatus(ItemStatus.NONE.getValue());
		if("1".equals( clfx.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( clfx.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<ShiTi> list =session.createQuery("from ShiTi where examId in("+clfx.getCexamId()+")").list();
		session.getTransaction().commit();
		session.close();
		ItemInfo children = new ItemInfo();
		for (ShiTi shiTi : list) {
			String examId = clfx.getCexamId();
			if(examId == null){
				if(clfx.getId().equals(shiTi.getAnalyId())){
					ItemInfo bu = this.BuDingXiang(children, shiTi, 0, info);
					System.out.println(bu);
				}
			}else{
				String[] ans = examId.split(",");
				for(int i = 0; i < ans.length; i++){
					if(ans[i].equals(shiTi.getExamId())){
						ItemInfo buDing = BuDingXiang(children, shiTi, i, info);
						System.out.println(buDing);
					}
				}
			}
		}
		return info;
	}
	//不定项函数
	public ItemInfo BuDingXiang(ItemInfo children,ShiTi shiTi,int i,ItemInfo info){
			Set<ItemInfo> child = new HashSet<ItemInfo>();
			children.setAnalysis(shiTi.getAnalysis());
			String answer =shiTi.getAnswer().toUpperCase();
			String contents = shiTi.getContent();
			String[] arr = contents.split("###");
			children.setContent(arr[0]);
			int[] a= null;
			Set<ItemInfo> set= new HashSet<ItemInfo>();
			String jieQu = null;
			if(answer.indexOf(",") != -1){
				String[] an = answer.split(",");
				a = new int[an.length];
				for (int j = 0; j < an.length; j++) {
					a[j] = an[j].toCharArray()[0]-64;
				}	
				for(int j=1;j<arr.length;j++){
					ItemInfo childs = new ItemInfo();
					childs.setId(UUID.randomUUID().toString());
					childs.setContent(arr[j]);
					childs.setOrderNo(j);
					for(int e=0;e<a.length;e++){
						String as = null;
						if(j == a[e]){
							System.out.println(j +"dd"+a[e]);
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
								System.out.println(jieQu+"ff"+as+"FF");
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
				children.setOrderNo(i);
				children.setAnswer(jieQu);
				children.setChildren(set);
				children.setType(ItemType.UNCERTAIN.getValue());
				child.add(children);
				info.setChildren(child);
			}else{
				char[] an=answer.toCharArray();
				a = new int[an.length];
				for (int j = 0; j < an.length; j++) {
					a[j] = (int)an[j]-64;
				}
				for(int j=1;j<arr.length;j++){
					ItemInfo childs = new ItemInfo();
					childs.setId(UUID.randomUUID().toString());
					childs.setContent(arr[j]);
					childs.setOrderNo(j);
					for(int e=0;e<a.length;e++){
						String as = null;
						if(j == a[e]){
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
								System.out.println(jieQu+"ff"+as+"FF");
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
				children.setOrderNo(i);
				children.setAnswer(jieQu);
				children.setChildren(set);
				children.setType(ItemType.UNCERTAIN.getValue());
				child.add(children);
				info.setChildren(child);
			}
		return info;
	}
}