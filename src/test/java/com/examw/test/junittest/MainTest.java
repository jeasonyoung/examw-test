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
import com.examw.test.model.library.StructureItemInfo;
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
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
//		String kuaijiClassId = "1";
//		String caijingClassId = "2";
//		String diansuanhuaClassId = "3";
		// 会计基础的考试代码
				String paperId = //"b68bbc88-660f-44ee-b4de-35bdc11bf972";
				 "5d78c902-e13a-4bee-972a-fb870a5faeab";
				String danxuanStructureId = //"1e8a24aa-d6c7-461e-8a96-4c58052c9f55";
				 "a70edc9f-e813-4b96-bd4d-b7d18862c07e";
				String duoxuanStructureId = //"905a99c6-30a2-49ae-9a22-c1e52ae74466";
				 "d108859b-38d7-4e5f-a5d7-06b92a88a834";
				String budingxiangStructureId = //"3443b599-e5e6-4d4b-b582-eda8a0306f4b";
				 "7655a8ce-92a1-4e57-9904-978f023a6836";
				String cailiaoStructureId = //"b65113e9-7741-40e7-99d3-7f7841abe37b";
				 "0b601e25-8a8c-47ea-89fa-7fd01e26d867";
				String panduanStructureId = //"36539f8a-08f8-4101-9382-b4ca34788765";
				 "3d1bfeda-256f-47c8-8b14-166a53e5bdab";
				String[] structures = {"",danxuanStructureId,duoxuanStructureId,budingxiangStructureId,cailiaoStructureId};

		// transaction.begin();
		@SuppressWarnings("unchecked")
		// 会计基础的单选试题
		List<ShiTi> shiTiList = session.createQuery("from ShiTi where ClassID = 1 and type = 4 and (analyId = 0 or analyId is null)").list();
		if(shiTiList!=null && shiTiList.size()>0)
		{
			for(int i=0;i<shiTiList.size();i++){
				ClientUploadItem data = this.parse(shiTiList.get(i),i+1);
				if(data != null){
					try{
						this.upload(paperId,panduanStructureId, data);
						Thread.sleep(1000);;
					}catch(Exception e)
					{
						continue;
					}
				}
			}
		}
		session.getTransaction().commit();
		// transaction.commit();
		session.close();
		// ItemInfo info = new ItemInfo();
		// List<ItemInfo> list = new ArrayList<ItemInfo>();
		// for(ShiTi sts: shiTiList){
		// ItemInfo info = danXuan(sts);
		// if(info!=null) list.add(info);
		// this.itemService.update(info);
		// }
		// this.itemService.insertItemList(list);
	}

	// //单选
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

	private ClientUploadItem parseJudge(ClientUploadItem info, ShiTi st) {
		info.setAnalysis(st.getAnalysis());
		info.setContent(st.getContent());
		info.setAnswer(st.getAnswer());
		return info;
	}

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
	//解析单选题
		private StructureItemInfo parseSingle(ShiTi st) throws Exception {
			StructureItemInfo info = new StructureItemInfo();
			String content = st.getContent();
			String answer = st.getAnswer().toUpperCase();
			String[] arr = content.split("###");
			info.setContent(arr[0]);	//题目
			Set<StructureItemInfo> set = new HashSet<StructureItemInfo>();
			if(answer.length()>1) return null;	//单选题不能多选
			if(arr.length<2) return null; //没有选项
			String itemAnswer = null;
			for (int i = 1; i < arr.length; i++) {
				char abcd = (char)(i+64);
				StructureItemInfo child = new StructureItemInfo();
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
			info.setType(ItemType.SINGLE.getValue());
			System.out.println(this.mapper.writeValueAsString(info));
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

	// //判断题
	// public ItemInfo panDuan(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// if(st.getAnalyId() != null || st.getAnswer() == null)return null;
	// info.setContent(st.getContent());
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// info.setAnalysis(st.getAnalysis());
	// info.setAnswer(st.getAnswer());
	// info.setType(ItemType.JUDGE.getValue());
	// info.setSubjectId("53951b86-3578-4d72-8c81-00d6d111e9b1");
	// info.setOpt(4);
	// info.setStatus(ItemStatus.NONE.getValue());
	// return info;
	// }
	// //多选题
	// public ItemInfo duoXuan(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// String content = st.getContent();
	// String answer = st.getAnswer().toUpperCase();
	// int[] a= null;
	// String[] arr = content.split("###");
	// info.setContent(arr[0]);
	// Set<ItemInfo> set= new HashSet<ItemInfo>();
	// String jieQu = null;
	// if(answer.indexOf(",") != -1){
	// String[] ans = answer.split(",");
	// a = new int[ans.length];
	// for (int j = 0; j < ans.length; j++) {
	// a[j] = ans[j].toCharArray()[0]-64;
	// }
	// for(int i=1;i<arr.length;i++){
	// ItemInfo child = new ItemInfo();
	// child.setOrderNo(i);
	// child.setId(UUID.randomUUID().toString());
	// child.setContent(arr[i]);
	// for(int j=0; j<a.length;j++){
	// String as = null;
	// if(i == a[j]){
	// if(a.length == 1){
	// answer = child.getId();
	// jieQu = answer;
	// }
	// if(a.length == 2){
	// answer = answer + child.getId()+",";
	// as = answer.substring(3);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length == 3){
	// answer = answer + child.getId()+",";
	// as = answer.substring(5);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length ==4){
	// answer = answer + child.getId()+",";
	// as = answer.substring(7);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// }
	// }
	// info.setOrder(String.valueOf(i));
	// set.add(child);
	// }
	// }
	// info.setChildren(set);
	// info.setAnswer(jieQu);
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// info.setAnalysis(st.getAnalysis());
	// info.setType(ItemType.MULTY.getValue());
	// if("1".equals( st.getClassId())){
	// info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
	// }
	// if("2".equals( st.getClassId())){
	// info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
	// }
	// if("3".equals( st.getClassId())){
	// info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
	// }
	// info.setOpt(4);
	// info.setStatus(ItemStatus.NONE.getValue());
	// return info;
	// }
	// //问答题
	// public ItemInfo wenDa(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// if(st.getAnalyId() != null ||st.getAnswer().equals(""))return null;
	// info.setContent(st.getContent());
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// info.setAnalysis(st.getAnalysis());
	// info.setAnswer(st.getAnswer());
	// info.setType(ItemType.QANDA.getValue());
	// info.setOpt(4);
	// info.setStatus(ItemStatus.NONE.getValue());
	// //根据sql科目ID设置mysql科目ID
	// info.setSubjectId("53951b86-3578-4d72-8c81-00d6d111e9b1");
	// // if("1".equals( st.getClassId())){
	// // info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
	// // }
	// // if("2".equals( st.getClassId())){
	// // info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
	// // }
	// // if("3".equals( st.getClassId())){
	// // info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
	// // }
	// return info;
	// }
	// //不定项
	// public ItemInfo buDingXi(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// if(st.getAnalyId().equals("0"))return null;
	// String content = st.getContent();
	// String answer = st.getAnswer().toUpperCase();
	// int[] a= null;
	// String[] arr = content.split("###");
	// info.setContent(arr[0]);
	// Set<ItemInfo> set= new HashSet<ItemInfo>();
	// String jieQu = null;
	// if(answer.indexOf(",") != -1){
	// String[] ans = answer.split(",");
	// a = new int[ans.length];
	// for (int j = 0; j < ans.length; j++) {
	// a[j] = ans[j].toCharArray()[0]-64;
	// }
	// for(int i=1;i<arr.length;i++){
	// ItemInfo child = new ItemInfo();
	// child.setOrderNo(i);
	// child.setId(UUID.randomUUID().toString());
	// child.setContent(arr[i]);
	// for(int j=0; j<a.length;j++){
	// String as = null;
	// if(i == a[j]){
	// if(a.length == 1){
	// answer = child.getId();
	// jieQu = answer;
	// }
	// if(a.length == 2){
	// answer = answer + child.getId()+",";
	// as = answer.substring(3);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length == 3){
	// answer = answer + child.getId()+",";
	// as = answer.substring(5);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length ==4){
	// answer = answer + child.getId()+",";
	// as = answer.substring(7);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// }
	// }
	// info.setOrder(String.valueOf(i));
	// set.add(child);
	// }
	// }else{
	// char[] ans=answer.toCharArray();
	// a = new int[ans.length];
	// for (int j = 0; j < ans.length; j++) {
	// a[j] = (int)ans[j]-64;
	// }
	// for(int i=1;i<arr.length;i++){
	// ItemInfo child = new ItemInfo();
	// child.setOrderNo(i);
	// child.setId(UUID.randomUUID().toString());
	// child.setContent(arr[i]);
	// for(int j=0; j<a.length;j++){
	// String as = null;
	// if(i == a[j]){
	// if(a.length == 1){
	// answer = child.getId();
	// jieQu = answer;
	// }
	// if(a.length == 2){
	// answer = answer + child.getId()+",";
	// as = answer.substring(2);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length == 3){
	// answer = answer + child.getId()+",";
	// as = answer.substring(3);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length ==4){
	// answer = answer + child.getId()+",";
	// as = answer.substring(4);
	// jieQu = as.substring(0,as.length()-1); }
	// }
	// }
	// info.setOrder(String.valueOf(i));
	// set.add(child);
	// }
	// }
	// info.setChildren(set);
	// info.setAnswer(jieQu);
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// info.setAnalysis(st.getAnalysis());
	// info.setType(ItemType.UNCERTAIN.getValue());
	// info.setOpt(4);
	// info.setStatus(ItemStatus.NONE.getValue());
	// info.setSubjectId("53951b86-3578-4d72-8c81-00d6d111e9b1");
	// // if("1".equals( st.getClassId())){
	// // info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
	// // }
	// // if("2".equals( st.getClassId())){
	// // info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
	// // }
	// // if("3".equals( st.getClassId())){
	// // info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
	// // }
	// return info;
	// }
	// //共享题干题的单选
	// public ItemInfo tiGanDanXuan(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// String content = st.getContent();
	// String answer = st.getAnswer().toUpperCase();
	// if(answer == null) return null;
	// int[] a= null;
	// char[] ans=answer.toCharArray();
	// a = new int[ans.length];
	// for (int j = 0; j < ans.length; j++) {
	// a[j] = (int)ans[j]-64;
	// }
	// String[] arr = content.split("###");
	// //一级题干
	// info.setContent(arr[0]);
	// //二级题干
	// Set<ItemInfo> two = new HashSet<ItemInfo>();
	// ItemInfo child = new ItemInfo();
	// child.setId(UUID.randomUUID().toString());
	// child.setContent(arr[0]);
	// child.setType(ItemType.SINGLE.getValue());
	// child.setAnalysis(st.getAnalysis());
	// child.setOrderNo(1);
	// child.setOpt(4);
	// child.setStatus(ItemStatus.NONE.getValue());
	// two.add(child);
	// info.setChildren(two);
	// //选项
	// Set<ItemInfo> childrens = new HashSet<ItemInfo>();
	// for(int i=1;i<arr.length;i++){
	// ItemInfo children = new ItemInfo();
	// children.setId(UUID.randomUUID().toString());
	// children.setContent(arr[i]);
	// children.setOrderNo(i);
	// for(int j=0; j<a.length;j++){
	// if(i == a[j]){
	// if(a.length == 1){
	// answer = child.getId();
	// info.setAnswer(answer);
	// }
	// }
	// child.setOrder(String.valueOf(i));
	// childrens.add(children);
	// }
	// child.setChildren(childrens);
	// child.setAnswer(answer);
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// info.setType(ItemType.SHARE_TITLE.getValue());
	// if("1".equals( st.getClassId())){
	// info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
	// }
	// if("2".equals( st.getClassId())){
	// info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
	// }
	// if("3".equals( st.getClassId())){
	// info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
	// }
	// }
	// return info;
	// }
	// //共享题干的判断题
	// public ItemInfo tiGanPanDuan(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// String content = st.getContent();
	// info.setContent(content);
	// Set<ItemInfo> child = new HashSet<ItemInfo>();
	// ItemInfo children = new ItemInfo();
	// children.setId(UUID.randomUUID().toString());
	// children.setContent(content);
	// children.setAnalysis(st.getAnalysis());
	// children.setAnswer(st.getAnswer());
	// children.setType(ItemType.JUDGE.getValue());
	// children.setOpt(4);
	// children.setStatus(ItemStatus.NONE.getValue());
	// child.add(children);
	// info.setChildren(child);
	// info.setType(ItemType.SHARE_TITLE.getValue());
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// if("1".equals( st.getClassId())){
	// info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
	// }
	// if("2".equals( st.getClassId())){
	// info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
	// }
	// if("3".equals( st.getClassId())){
	// info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
	// }
	// return info;
	// }
	// //共享题干的多选题
	// public ItemInfo tiGanDuoXuan(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// String content = st.getContent();
	// String answer = st.getAnswer().toUpperCase();
	// String[] ans = answer.split(",");
	// int[] a = new int[ans.length];
	// for (int j = 0; j < ans.length; j++) {
	// a[j] = ans[j].toCharArray()[0]-64;
	// }
	// String[] arr = content.split("###");
	// //一级题干
	// info.setContent(arr[0]);
	// //二级题干
	// Set<ItemInfo> two = new HashSet<ItemInfo>();
	// ItemInfo child = new ItemInfo();
	// child.setId(UUID.randomUUID().toString());
	// child.setContent(arr[0]);
	// child.setType(ItemType.MULTY.getValue());
	// child.setAnalysis(st.getAnalysis());
	// child.setOpt(4);
	// child.setStatus(ItemStatus.NONE.getValue());
	// two.add(child);
	// info.setChildren(two);
	// //选项
	// Set<ItemInfo> childrens = new HashSet<ItemInfo>();
	// String jieQu = null;
	// for(int i=1;i<arr.length;i++){
	// ItemInfo children = new ItemInfo();
	// children.setId(UUID.randomUUID().toString());
	// children.setContent(arr[i]);
	// for(int j=0; j<a.length;j++){
	// String as = null;
	// if(i == a[j]){
	// if(a.length == 1){
	// answer = children.getId();
	// jieQu = answer;
	// }
	// if(a.length == 2){
	// answer = answer + children.getId()+",";
	// as = answer.substring(3);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length == 3){
	// answer = answer + children.getId()+",";
	// as = answer.substring(5);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length ==4){
	// answer = answer + children.getId()+",";
	// as = answer.substring(7);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// }
	// }
	// info.setOrder(String.valueOf(i));
	// childrens.add(children);
	// }
	// child.setChildren(childrens);
	// child.setAnswer(jieQu);
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// info.setType(ItemType.SHARE_TITLE.getValue());
	// if("1".equals( st.getClassId())){
	// info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
	// }
	// if("2".equals( st.getClassId())){
	// info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
	// }
	// if("3".equals( st.getClassId())){
	// info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
	// }
	// return info;
	// }
	// //共享题干的不定项
	// public ItemInfo tiGanBuDingXiang(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// String content = st.getContent();
	// String answer = st.getAnswer().toUpperCase();
	//
	// String[] arr = content.split("###");
	// //一级题目
	// info.setContent(arr[0]);
	// //二级题目
	// Set<ItemInfo> one = new HashSet<ItemInfo>();
	// ItemInfo child = new ItemInfo();
	// child.setId(UUID.randomUUID().toString());
	// child.setAnalysis(st.getAnalysis());
	// child.setType(ItemType.UNCERTAIN.getValue());
	// one.add(child);
	// info.setChildren(one);
	// Set<ItemInfo> set= new HashSet<ItemInfo>();
	// int[] a =null;
	// String jieQu = null;
	// if(answer.indexOf(",") !=-1){
	// String[] ans = answer.split(",");
	// a = new int[ans.length];
	// for (int j = 0; j < ans.length; j++) {
	// a[j] = ans[j].toCharArray()[0]-64;
	// }
	// for(int i=1;i<arr.length;i++){
	// ItemInfo childs = new ItemInfo();
	// childs.setId(UUID.randomUUID().toString());
	// childs.setContent(arr[i]);
	// for(int j=0; j<a.length;j++){
	// String as = null;
	// if(i == a[j]){
	// if(a.length == 1){
	// answer = childs.getId();
	// jieQu = answer;
	// }
	// if(a.length == 2){
	// answer = answer + childs.getId()+",";
	// as = answer.substring(3);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length == 3){
	// answer = answer + childs.getId()+",";
	// as = answer.substring(5);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length ==4){
	// answer = answer + childs.getId()+",";
	// as = answer.substring(7);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// }
	// }
	// info.setOrder(String.valueOf(i));
	// set.add(childs);
	// }
	// child.setChildren(set);
	// child.setAnswer(jieQu);
	// }else{
	// char[] ans=answer.toCharArray();
	// a = new int[ans.length];
	// for (int j = 0; j < ans.length; j++) {
	// a[j] = (int)ans[j]-64;
	// }
	// for(int i=1;i<arr.length;i++){
	// ItemInfo childs = new ItemInfo();
	// childs.setId(UUID.randomUUID().toString());
	// childs.setContent(arr[i]);
	// for(int j=0; j<a.length;j++){
	// String as = null;
	// if(i == a[j]){
	// if(a.length == 1){
	// answer = childs.getId();
	// jieQu = answer;
	// }
	// if(a.length == 2){
	// answer = answer + childs.getId()+",";
	// as = answer.substring(2);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length == 3){
	// answer = answer + childs.getId()+",";
	// as = answer.substring(3);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// if(a.length ==4){
	// answer = answer + childs.getId()+",";
	// as = answer.substring(4);
	// jieQu = as.substring(0,as.length()-1);
	// }
	// }
	// }
	// info.setOrder(String.valueOf(i));
	// set.add(childs);
	// }
	// child.setChildren(set);
	// child.setAnswer(jieQu);
	// }
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// info.setType(ItemType.MULTY.getValue());
	// if("1".equals( st.getClassId())){
	// info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
	// }
	// if("2".equals( st.getClassId())){
	// info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
	// }
	// if("3".equals( st.getClassId())){
	// info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
	// }
	// return info;
	// }
	// //共享题干的问答题
	// public ItemInfo tiGanWenDa(ShiTi st){
	// ItemInfo info = new ItemInfo();
	// String content = st.getContent();
	// info.setContent(content);
	// Set<ItemInfo> child = new HashSet<ItemInfo>();
	// ItemInfo children = new ItemInfo();
	// children.setId(UUID.randomUUID().toString());
	// children.setContent(content);
	// children.setAnalysis(st.getAnalysis());
	// children.setAnswer(st.getAnswer());
	// children.setType(ItemType.QANDA.getValue());
	// children.setOpt(4);
	// children.setStatus(ItemStatus.NONE.getValue());
	// child.add(children);
	// info.setChildren(child);
	// info.setType(ItemType.SHARE_TITLE.getValue());
	// Integer level=Integer.parseInt(st.getId());
	// info.setLevel(level);
	// if("1".equals( st.getClassId())){
	// info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
	// }
	// if("2".equals( st.getClassId())){
	// info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
	// }
	// if("3".equals( st.getClassId())){
	// info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
	// }
	// return info;
	// }
}