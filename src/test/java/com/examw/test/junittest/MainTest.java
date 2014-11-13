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
import com.examw.test.service.library.IPaperItemService;
import com.examw.test.service.library.ItemType;
import com.examw.utils.HttpUtil;

/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午6:06:08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring-examw-test-hibernate-2.xml",
		"classpath:spring-examw-test.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class MainTest {
	@Resource
	private IItemDao itemDao;
	@Resource
	private IItemService itemService;
	@Resource
	private IPaperItemService paperItemService;
	@Resource
	private SessionFactory sessionFactorySql;
	
	private ObjectMapper mapper = new ObjectMapper();
	private static final String serverUrl = "http://tiku.examw.com/examw-test/api/imports";
	
	@Test
	public void ShiTis() throws Exception {
		//会计基础
		import_JiChu_Single();
		
		import_JiChu_Multi();
		
		import_JiChu_Uncentern();
		
		import_JiChu_Judge();
		
		//财经法规
		import_CaiJing_Single();
		import_CaiJing_Multi();
		import_CaiJing_Uncentern();
		import_CaiJing_Judge();
		
		//会计电算化
		import_DianSuan_Single();
		import_DianSuan_Multi();
		import_DianSuan_Uncentern();
		import_DianSuan_Judge();
	}

	//题型
	public ClientUploadItem parse(ShiTi st,Integer index)throws Exception {
		ClientUploadItem info = new ClientUploadItem();
		info.setOrderNo(index);
		switch (st.getType()) {
		case 1: // 单选
			info.setType(ItemType.SINGLE.getValue());
			return parseSingle(info,st);
		case 2: // 多选
			info.setType(ItemType.MULTY.getValue());
			return parseMulti(info,st);
		case 3: // 不定项
			info.setType(ItemType.UNCERTAIN.getValue());
			return parseMulti(info,st);
		case 4: // 判断题
			info.setType(ItemType.JUDGE.getValue());
			return parseJudge(info,st);
		default:
			return null;
		}
	}
	//判断
	private ClientUploadItem parseJudge(ClientUploadItem info, ShiTi st) {
		info.setAnalysis(st.getAnalysis());
		info.setContent(st.getContent());
		info.setAnswer(st.getAnswer());
		return info;
	}
	//多选,不定项
	private ClientUploadItem parseMulti(ClientUploadItem info, ShiTi st) throws Exception {
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
		System.out.println(this.mapper.writeValueAsString(info));
		return info;
	}
	//解析单选题
	private ClientUploadItem parseSingle(ClientUploadItem info, ShiTi st) throws Exception {
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		String[] arr = content.split("###");
		info.setContent(arr[0]);	//题目
		Set<ClientUploadItem> set = new HashSet<ClientUploadItem>();
		if(answer.length()>1) return null;	//单选题不能多选
		if(arr.length<2) return null; //没有选项
		String itemAnswer = null;
		for (int i = 1; i < arr.length; i++) {
			char abcd = (char)(i+64);
			ClientUploadItem child = new ClientUploadItem();
			child.setOrderNo(i);
			child.setId(UUID.randomUUID().toString());
			child.setContent(abcd+"."+arr[i]);
			if(String.valueOf(abcd).equals(answer))
			{
				itemAnswer = child.getId();
			}
			child.setOrderNo(i);
			set.add(child);
		}
		if(set.size()>0)
			info.setChildren(set);
		info.setAnswer(itemAnswer);
		info.setAnalysis(st.getAnalysis());
		System.out.println(this.mapper.writeValueAsString(info));
		return info;
	}
	//上传
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
	// 会计基础单选题
	private void import_JiChu_Single() throws Exception
	{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";	//数据库会计基础试卷的ID
		String structureId = "a70edc9f-e813-4b96-bd4d-b7d18862c07e";//单选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 1 and type = 1 and (analyId = 0 or analyId is null)");
	}
	private void import_JiChu_Multi() throws Exception
	{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";	//数据库会计基础试卷的ID
		String structureId = "d108859b-38d7-4e5f-a5d7-06b92a88a834";//多选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 1 and type = 2 and (analyId = 0 or analyId is null)");
	}
	private void import_JiChu_Uncentern() throws Exception
	{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";	//数据库会计基础试卷的ID
		String structureId = "7655a8ce-92a1-4e57-9904-978f023a6836";//不定项选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 1 and type = 3 and (analyId = 0 or analyId is null)");
	}
	private void import_JiChu_Judge() throws Exception
	{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";	//数据库会计基础试卷的ID
		String structureId = "3d1bfeda-256f-47c8-8b14-166a53e5bdab";;//判断题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 1 and type = 4 and (analyId = 0 or analyId is null)");
	}
	private void import_common(String paperId,String structureId,String sql) throws Exception
	{
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		// 会计基础的单选试题
		List<ShiTi> shiTiList = session.createQuery(sql).list();
		if(shiTiList!=null && shiTiList.size()>0)
		{
			for(int i=0;i<shiTiList.size();i++){
				ClientUploadItem data = this.parse(shiTiList.get(i),i+1);
				if(data != null){
					try{
						this.upload(paperId,structureId, data);
						Thread.sleep(1000);;
					}catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("试题ID = "+shiTiList.get(i).getId());
						continue;
					}
				}
			}
		}
		session.getTransaction().commit();
		// transaction.commit();
		session.close();
	}
	// 财经
	private void import_CaiJing_Single() throws Exception
	{
		String paperId = "bcf21dc6-87f3-4968-9b02-7a183330c90c";	//数据库财经法规试卷的ID
		String structureId = "fa90e6ba-1e43-41be-85b3-ad5cdf68ceb4";//单选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 2 and type = 1 and (analyId = 0 or analyId is null)");
	}
	private void import_CaiJing_Multi() throws Exception
	{
		String paperId = "bcf21dc6-87f3-4968-9b02-7a183330c90c";	//数据库财经法规试卷的ID
		String structureId = "bd333c73-24fb-4b92-8f98-841dc714c286";//多选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 2 and type = 2 and (analyId = 0 or analyId is null)");
	}
	private void import_CaiJing_Uncentern() throws Exception
	{
		String paperId = "bcf21dc6-87f3-4968-9b02-7a183330c90c";	//数据库财经法规试卷的ID
		String structureId = "ca10e6fc-13c0-47ba-803a-bbf578a1dfd7";//不定项选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 2 and type = 3 and (analyId = 0 or analyId is null)");
	}
	private void import_CaiJing_Judge() throws Exception
	{
		String paperId = "bcf21dc6-87f3-4968-9b02-7a183330c90c";	//数据库财经法规试卷的ID
		String structureId = "ef429691-1aee-454c-854a-f9d30cad2581";;//判断题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 2 and type = 4 and (analyId = 0 or analyId is null)");
	}
	
	//电算化
	private void import_DianSuan_Single() throws Exception
	{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";	//数据库电算化试卷的ID
		String structureId = "a70edc9f-e813-4b96-bd4d-b7d18862c07e";//单选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 3 and type = 1 and (analyId = 0 or analyId is null)");
	}
	private void import_DianSuan_Multi() throws Exception
	{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";	//数据库电算化试卷的ID
		String structureId = "d108859b-38d7-4e5f-a5d7-06b92a88a834";//多选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 3 and type = 2 and (analyId = 0 or analyId is null)");
	}
	private void import_DianSuan_Uncentern() throws Exception
	{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";	//数据库电算化试卷的ID
		String structureId = "7655a8ce-92a1-4e57-9904-978f023a6836";//不定项选题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 3 and type = 3 and (analyId = 0 or analyId is null)");
	}
	private void import_DianSuan_Judge() throws Exception
	{
		String paperId = "5d78c902-e13a-4bee-972a-fb870a5faeab";	//数据库电算化试卷的ID
		String structureId = "3d1bfeda-256f-47c8-8b14-166a53e5bdab";;//判断题结构ID
		import_common(paperId,structureId,"from ShiTi where ClassID = 3 and type = 4 and (analyId = 0 or analyId is null)");
	}
}