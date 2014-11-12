package com.examw.test.junittest;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.dao.library.IItemDao;
import com.examw.test.junittest.ClientUploadItem.ItemScoreInfo;
import com.examw.test.service.library.IItemService;
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
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class MainTest {
	@Resource
	private IItemDao itemDao;
	@Resource
	private IItemService itemService;
	@Resource
	private SessionFactory sessionFactorySql;
	
	private ObjectMapper mapper = new ObjectMapper();
	private static final String serverUrl = "http://localhost:8080/examw-test/api/imports";
	
	@Test
	public void ShiTis() throws JsonGenerationException, JsonMappingException,
			IOException {
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		// transaction.begin();
		@SuppressWarnings("unchecked")
		// 会计基础的单选试题
		List<ShiTi> shiTiList = session.createQuery("from ShiTi where ClassID = 1").list();
		System.out.println(shiTiList.size());
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

		// 会计基础的考试代码
		String paperId = "b68bbc88-660f-44ee-b4de-35bdc11bf972";
		// "5d78c902-e13a-4bee-972a-fb870a5faeab";
		String danxuanStructureId = "1e8a24aa-d6c7-461e-8a96-4c58052c9f55";
		// "a70edc9f-e813-4b96-bd4d-b7d18862c07e";
		String duoxuanStructureId = "905a99c6-30a2-49ae-9a22-c1e52ae74466";
		// "d108859b-38d7-4e5f-a5d7-06b92a88a834";
		String budingxiangStructureId = "3443b599-e5e6-4d4b-b582-eda8a0306f4b";
		// "7655a8ce-92a1-4e57-9904-978f023a6836";
		String cailiaoStructureId = "b65113e9-7741-40e7-99d3-7f7841abe37b";
		// "0b601e25-8a8c-47ea-89fa-7fd01e26d867";
		String[] structures = {danxuanStructureId,duoxuanStructureId,budingxiangStructureId,cailiaoStructureId};
		String kuaijiClassId = "1";
		String caijingClassId = "2";
		String diansuanhuaClassId = "3";
	}

	// //单选
	public ClientUploadItem parse(ShiTi st,String[] structures) {
		ClientUploadItem info = new ClientUploadItem();
		switch (st.getType()) {
		case 1: // 单选
			return parseSingle(info,st);
		case 2: // 多选
			return parseMulti(info,st);
		case 3: // 不定项
			return parseUnCertain(info,st);
		case 4: // 材料题
			return parseShareTitle(info,st);
		default:
			return null;
		}
	}

	private ClientUploadItem parseShareTitle(ClientUploadItem info, ShiTi st) {
		
		return null;
	}

	private ClientUploadItem parseUnCertain(ClientUploadItem info, ShiTi st) {
		
		return null;
	}

	private ClientUploadItem parseMulti(ClientUploadItem info, ShiTi st) {
		
		return null;
	}
	//解析单选题
	private ClientUploadItem parseSingle(ClientUploadItem info, ShiTi st) {
		String content = st.getContent();
		String answer = st.getAnswer().toUpperCase();
		String[] arr = content.split("###");
		ItemScoreInfo item = new ItemScoreInfo();
		item.setContent(arr[0]);	//题目
		Set<ItemScoreInfo> set = new HashSet<ItemScoreInfo>();
		if(answer.contains(",")) return null;	//单选题不能多选
		for (int i = 1; i < arr.length; i++) {
			char abcd = (char)(i+64);
			ItemScoreInfo child = new ItemScoreInfo();
			child.setOrderNo(i);
			child.setId(UUID.randomUUID().toString());
			child.setContent(abcd+"."+arr[i]);
			if(String.valueOf(abcd).equals(answer))
			{
				item.setAnalysis(child.getId());
			}
			child.setOrderNo(i);
			set.add(child);
		}
		item.setChildren(set);
		item.setAnswer(answer);
		item.setAnalysis(st.getAnalysis());
		item.setType(ItemType.SINGLE.getValue());
		return info;
	}
	
	public boolean upload(String paperId, ClientUploadItem data) throws Exception {
		//if(logger.isDebugEnabled()) logger.debug(String.format("上传试题数据到试卷［paperId=%1$s］...", paperId));
		if(StringUtils.isEmpty(paperId)) throw new Exception("所属试卷ID为空！");
		if(data == null) throw new Exception("试题数据为NULL！");
		String post = this.mapper.writeValueAsString(data);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-type","application/json;charset=UTF-8");
		String callback = HttpUtil.sendRequest(String.format("%1$s/update/%2$s", serverUrl, paperId),headers, "POST", post);
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