package com.examw.test.junittest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.dao.library.IItemDao;
import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemType;
import com.examw.utils.HttpUtil;
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
	
	private ObjectMapper mapper = new ObjectMapper();
	private static final String serverUrl = "http://localhost:8080/examw-test/api/imports";
	@Test
	public void fenXi() throws Exception{
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		
		String paperId = //"b68bbc88-660f-44ee-b4de-35bdc11bf972";
		 "5d78c902-e13a-4bee-972a-fb870a5faeab";
		String cailiaoStructureId = //"b65113e9-7741-40e7-99d3-7f7841abe37b";
		 "0b601e25-8a8c-47ea-89fa-7fd01e26d867";
		
		@SuppressWarnings("unchecked")
		List<CaiLianFenXi> list = session.createQuery("from CaiLianFenXi where classId = 1").list();
		System.out.println(list.size());
		for(int i=0;i<list.size();i++) {
			ClientUploadItem item = this.parse(session,list.get(i),i+1);
			if(item != null)
			{
				System.out.println(this.mapper.writeValueAsString(item));
				try{
					this.upload(paperId,cailiaoStructureId, item);
					Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
					break;
				}
			}
		}
		session.getTransaction().commit();
		session.close();
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.writeValue(System.out, info);
	}
	
	private ClientUploadItem parse(Session session, CaiLianFenXi caiLianFenXi,int index)throws Exception {
		ClientUploadItem info = new ClientUploadItem();
		info.setType(ItemType.SHARE_TITLE.getValue());
		info.setOrderNo(index);
		info.setContent(caiLianFenXi.getContent());
		List<ShiTi> items = session.createQuery("from ShiTi where analyId = "+caiLianFenXi.getId()).list();
		if(items == null || items.size()==0)
		return null;
		Set<ClientUploadItem> children = new HashSet<ClientUploadItem>();
		for(int i=0;i<items.size();i++)
		{
			ClientUploadItem child = parseMulti(items.get(i));
			if(child!=null)
			{
				child.setOrderNo(i+1);
				children.add(child);
			}
		}
		if(children.size()>0)
		{
			info.setChildren(children);
		}
		return info;
	}



	private ClientUploadItem parseMulti(ShiTi st) throws Exception {
		ClientUploadItem info = new ClientUploadItem();
		info.setType(ItemType.UNCERTAIN.getValue());
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		String[] arr = content.split("###");
		info.setContent(arr[0]);	//题目
		//if(answer.length()==1) return null;	//多选题不能单选
		if(arr.length<2) return null; //没有选项
		Set<ClientUploadItem> set = new HashSet<ClientUploadItem>();
		String itemAnswer = "";
		for (int i = 1; i < arr.length; i++) {
			char abcd = (char)(i+64);
			ClientUploadItem child = new ClientUploadItem();
			child.setOrderNo(i);
			child.setId(UUID.randomUUID().toString());
			child.setContent(abcd+"."+arr[i]);
			if(answer.contains(String.valueOf(abcd)))
			{
				itemAnswer += child.getId()+",";
			}
			child.setOrderNo(i);
			set.add(child);
		}
		if(set.size()>0)
			info.setChildren(set);
		if(answer.length()>0)
			info.setAnswer(itemAnswer.substring(0, itemAnswer.length()-1));
		info.setAnalysis(st.getAnalysis());
		return info;
	}
	
	public boolean upload(String paperId,String structureId,ClientUploadItem data) throws Exception {
		//if(logger.isDebugEnabled()) logger.debug(String.format("上传试题数据到试卷［paperId=%1$s］...", paperId));
		if(StringUtils.isEmpty(paperId)) throw new Exception("所属试卷ID为空！");
		if(data == null) throw new Exception("试题数据为NULL！");
		String post = this.mapper.writeValueAsString(data);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-type","application/json;charset=UTF-8");
		String callback = HttpUtil.sendRequest(String.format("%1$s/update/%2$s/%3$s", serverUrl, paperId,structureId),headers, "POST", post);
		System.out.println("callback ==== "+callback);
		Json json = this.mapper.readValue(callback, Json.class);
		if(json == null){
			throw new Exception("反馈收据转换失败！callback=>" + callback);
		}
		if(json.isSuccess()) return true;
		if(StringUtils.isEmpty(json.getMsg())) return false;
		throw new Exception(json.getMsg());
	}
//	//分析题
//	public ItemInfo fenXiTi(CaiLianFenXi clfx){
//		ItemInfo info = new  ItemInfo();
//		String content=clfx.getContent();
//		info.setType(ItemType.SHARE_TITLE.getValue());
//		//正则表达式进行替换</P><P>为<br/> 删除不是<br/><img>的标签
//		content = content.replaceAll("</[p|P]><[p|P](.+?)>", "<br/>");
//		content = content.replaceAll("<(?!/?(?i)(img|br)).*?>", "");
//		info.setContent(content);
//		info.setOpt(4);
//		info.setStatus(ItemStatus.NONE.getValue());
//		if("1".equals( clfx.getClassId())){
//			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
//		}
//		if("2".equals( clfx.getClassId())){
//			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
//		}
//		Session session = sessionFactorySql.openSession();
//		session.beginTransaction();
//		@SuppressWarnings("unchecked")
//		List<ShiTi> list =session.createQuery("from ShiTi where examId in("+clfx.getCexamId()+")").list();
//		session.getTransaction().commit();
//		session.close();
//		ItemInfo children = new ItemInfo();
//		for (ShiTi shiTi : list) {
//			String examId = clfx.getCexamId();
//			if(examId == null){
//				if(clfx.getId().equals(shiTi.getAnalyId())){
//					ItemInfo bu = this.BuDingXiang(children, shiTi, 0, info);
//					System.out.println(bu);
//				}
//			}else{
//				String[] ans = examId.split(",");
//				for(int i = 0; i < ans.length; i++){
//					if(ans[i].equals(shiTi.getExamId())){
//						ItemInfo buDing = BuDingXiang(children, shiTi, i, info);
//						System.out.println(buDing);
//					}
//				}
//			}
//		}
//		return info;
//	}
//	//不定项函数
//	public ItemInfo BuDingXiang(ItemInfo children,ShiTi shiTi,int i,ItemInfo info){
//			Set<ItemInfo> child = new HashSet<ItemInfo>();
//			children.setAnalysis(shiTi.getAnalysis());
//			String answer =shiTi.getAnswer().toUpperCase();
//			String contents = shiTi.getContent();
//			String[] arr = contents.split("###");
//			children.setContent(arr[0]);
//			int[] a= null;
//			Set<ItemInfo> set= new HashSet<ItemInfo>();
//			String jieQu = null;
//			if(answer.indexOf(",") != -1){
//				String[] an = answer.split(",");
//				a = new int[an.length];
//				for (int j = 0; j < an.length; j++) {
//					a[j] = an[j].toCharArray()[0]-64;
//				}	
//				for(int j=1;j<arr.length;j++){
//					ItemInfo childs = new ItemInfo();
//					childs.setId(UUID.randomUUID().toString());
//					childs.setContent(arr[j]);
//					childs.setOrderNo(j);
//					for(int e=0;e<a.length;e++){
//						String as = null;
//						if(j == a[e]){
//							System.out.println(j +"dd"+a[e]);
//							if(a.length == 1){
//								answer = childs.getId();
//								jieQu = answer;
//							}
//							if(a.length == 2){
//								answer = answer + childs.getId()+",";
//								as = answer.substring(3);
//								jieQu = as.substring(0,as.length()-1);
//							}
//							if(a.length == 3){
//								answer = answer + childs.getId()+",";
//								as = answer.substring(5);
//								jieQu = as.substring(0,as.length()-1);
//								System.out.println(jieQu+"ff"+as+"FF");
//							}
//							if(a.length ==4){
//								answer = answer + childs.getId()+",";
//								as = answer.substring(7);
//								jieQu = as.substring(0,as.length()-1);
//							}
//						}
//					}
//					info.setOrder(String.valueOf(i));
//					set.add(childs);
//				}
//				children.setOrderNo(i);
//				children.setAnswer(jieQu);
//				children.setChildren(set);
//				children.setType(ItemType.UNCERTAIN.getValue());
//				child.add(children);
//				info.setChildren(child);
//			}else{
//				char[] an=answer.toCharArray();
//				a = new int[an.length];
//				for (int j = 0; j < an.length; j++) {
//					a[j] = (int)an[j]-64;
//				}
//				for(int j=1;j<arr.length;j++){
//					ItemInfo childs = new ItemInfo();
//					childs.setId(UUID.randomUUID().toString());
//					childs.setContent(arr[j]);
//					childs.setOrderNo(j);
//					for(int e=0;e<a.length;e++){
//						String as = null;
//						if(j == a[e]){
//							if(a.length == 1){
//								answer = childs.getId();
//								jieQu = answer;
//							}
//							if(a.length == 2){
//								answer = answer + childs.getId()+",";
//								as = answer.substring(2);
//								jieQu = as.substring(0,as.length()-1);
//							}
//							if(a.length == 3){
//								answer = answer + childs.getId()+",";
//								as = answer.substring(3);
//								jieQu = as.substring(0,as.length()-1);
//								System.out.println(jieQu+"ff"+as+"FF");
//							}
//							if(a.length ==4){
//								answer = answer + childs.getId()+",";
//								as = answer.substring(4);
//								jieQu = as.substring(0,as.length()-1);
//							}
//						}
//					}
//					info.setOrder(String.valueOf(i));
//					set.add(childs);
//				}
//				children.setOrderNo(i);
//				children.setAnswer(jieQu);
//				children.setChildren(set);
//				children.setType(ItemType.UNCERTAIN.getValue());
//				child.add(children);
//				info.setChildren(child);
//			}
//		return info;
//	}
}