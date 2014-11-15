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
	private static final String serverUrl = "http://tiku.examw.com/examw-test/api/imports";
	
//	@Test
	public void fenXi() throws Exception{
		
		import_JiChu();
		
		import_CaiJing();
		
//		import_DianSuan();
		
	}
	private void import_JiChu() throws Exception{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";
		String structureId = "0b601e25-8a8c-47ea-89fa-7fd01e26d867";
//		String paperId = "b68bbc88-660f-44ee-b4de-35bdc11bf972";
//		String structureId = "b65113e9-7741-40e7-99d3-7f7841abe37b";
		import_common(paperId,structureId,"from CaiLianFenXi where classId = 1");
	}
	
	private void import_CaiJing() throws Exception{
		String paperId = "bcf21dc6-87f3-4968-9b02-7a183330c90c";
		String structureId ="d325f055-e3f5-4ecc-ba4c-816a12097b1c";
		import_common(paperId,structureId,"from CaiLianFenXi where classId = 2");
	}
	
	private void import_common(String paperId,String structureId,String sql) throws Exception{
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<CaiLianFenXi> list = session.createQuery(sql).list();
		for(int i=0;i<list.size();i++) {
			ClientUploadItem item = this.parse(session,list.get(i),i+1);
			if(item != null)
			{
				System.out.println(this.mapper.writeValueAsString(item));
				try{
					this.upload(paperId,structureId, item);
					Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
					break;
				}
			}
		}
		session.getTransaction().commit();
		session.close();
	}
	private ClientUploadItem parse(Session session, CaiLianFenXi caiLianFenXi,int index)throws Exception {
		ClientUploadItem info = new ClientUploadItem();
		info.setType(ItemType.SHARE_TITLE.getValue());
		info.setOrderNo(index);
		info.setContent(caiLianFenXi.getContent());
		@SuppressWarnings("unchecked")
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

}