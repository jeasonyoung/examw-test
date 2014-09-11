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
import com.examw.test.junit.domain.CaiLianFenXi;
import com.examw.test.junit.domain.ShiTi;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.IItemService;
/**
 * 
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test-hibernate-2.xml","classpath:spring-examw-test.xml"})
public class FenXiTest {
	@Resource
	private IItemDao itemDao;
	@Resource
	private IItemService itemService;
	@Resource
	private SessionFactory sessionFactorySql;
	@Transactional
	@Test
	public void fenXi() throws JsonGenerationException, JsonMappingException, IOException{
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<CaiLianFenXi> list = session.createQuery("from CaiLianFenXi").list();
		System.out.println(list.size());
		session.getTransaction().commit();
		session.close();
		CaiLianFenXi cai = new CaiLianFenXi();
		cai.setCexamId("5565,5566,5567,5568");
		cai.setId("1");
		cai.setContent("A市是我国某直辖市，市政府因办公需要，拟通过政府采购购进一批办公用品，由办公室李主任具体进行此项工作。");
		cai.setClassId("1");
		ItemInfo info = fenXiTi(cai);
//		for(CaiLianFenXi fx : list) {
//			info = fenXiTi(fx) ;
//		}
//		itemService.update(info);
		//ItemInfo info =fenXiTi(cai);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(System.out, info);
	}
	public ItemInfo fenXiTi(CaiLianFenXi clfx){
		ItemInfo info = new  ItemInfo();
		String content=clfx.getContent();
		info.setType(Item.TYPE_SHARE_TITLE);
		//正则表达式进行替换</P><P>为<br/> 删除不是<br/><img>的标签
		content = content.replaceAll("</[p|P]><[p|P](.+?)>", "<br/>");
		content = content.replaceAll("<(?!/?(?i)(img|br)).*?>", "");
		info.setContent(content);
		if("1".equals( clfx.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( clfx.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		Set<ItemInfo> child = new HashSet<ItemInfo>();
		ItemInfo children = new ItemInfo();
		ShiTi shiTi = new ShiTi();
		shiTi.setAnalysis("本题考核内部会计监督");
		shiTi.setAnswer("AD");
		shiTi.setExamId("5565");
		shiTi.setType(1);
		shiTi.setAnalyId("1");
		shiTi.setContent("按照不同的会计期间，不属于结账的是()。###日结###月结###季结###年结");
		String examId = clfx.getCexamId();
		String[] ans = examId.split(",");
		if(clfx.getId().equals(shiTi.getAnalyId())){
			ItemInfo bu = BuDingXiang(children, shiTi, 0, info);
			System.out.println(bu);
		}
		for(int i = 0; i < ans.length; i++){
			if(ans[i].equals(shiTi.getExamId())){
				ItemInfo buDing = BuDingXiang(children, shiTi, i, info);
				System.out.println(buDing);
			}
		}
		children.setType(7);
		child.add(children);
		info.setChildren(child);
		return info;
	}
	//不定项函数
	public ItemInfo BuDingXiang(ItemInfo children,ShiTi shiTi,int i,ItemInfo info){
			children.setAnalysis(shiTi.getAnalysis());
			String answer = shiTi.getAnswer();
			String contents = shiTi.getContent();
			String[] arr = contents.split("###");
			children.setContent(arr[0]);
			int[] a= null;
			if(answer.indexOf(",") != -1){
				String[] an = answer.split(",");
				a = new int[an.length];
				for (int j = 0; j < an.length; j++) {
					a[j] = an[j].toCharArray()[0]-64;
				}	
			}else{
				char[] an=answer.toCharArray();
				a = new int[an.length];
				for (int j = 0; j < an.length; j++) {
					a[j] = (int)an[j]-64;
				}
			}
			if(a.length == 1){
				Set<ItemInfo> childrens = new HashSet<ItemInfo>();
				for (int j = 1; j < arr.length; j++) {
					ItemInfo childs = new ItemInfo();
					childs.setId(UUID.randomUUID().toString());
					childs.setContent(arr[j]);
					int answer_index= answer.charAt(0)-64;
					if(j == answer_index){
						answer = childs.getId();
						children.setAnswer(answer);
					}
					childs.setOrder(String.valueOf(j));
					childrens.add(childs);
				}
				children.setChildren(childrens);
			}
			if(a.length > 1){
				Set<ItemInfo> set= new HashSet<ItemInfo>();
				String jieQu = null;
				for(int j=1;j<arr.length;j++){
					ItemInfo childs = new ItemInfo();
					childs.setId(UUID.randomUUID().toString());
					childs.setContent(arr[j]);
					for(int e=0;e<a.length;e++){
						String as = null;
						if(j == a[e]){
							if(a.length == 1){
								answer = childs.getId();
								as = answer;
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
				children.setAnswer(jieQu);
				children.setChildren(set);
			}
		
		return null;
		
	}

}