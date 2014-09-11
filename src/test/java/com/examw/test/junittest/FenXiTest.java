package com.examw.test.junittest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
		for (CaiLianFenXi fx : list) {
			fx = new CaiLianFenXi();
			ItemInfo info = fenXiTi(fx) ;
			itemService.update(info);
		}
	}
	
	
	public ItemInfo fenXiTi(CaiLianFenXi clfx){
		ItemInfo info = new  ItemInfo();
		String content=clfx.getContent();
		if("1".equals( clfx.getClassId())){
			info.setSubjectId("c0e6f25c-eec8-44aa-94e8-968443ce6af9");
		}
		if("2".equals( clfx.getClassId())){
			info.setSubjectId("ad20f907-9fbc-435d-8922-77c0eff1ff3e");
		}
		if("3".equals( clfx.getClassId())){
			info.setSubjectId("9502b22d-3831-4eed-ad39-00c502618e64");
		}
		info.setType(Item.TYPE_SHARE_TITLE);
		Set<ItemInfo> child = new HashSet<ItemInfo>();
		ItemInfo children = new ItemInfo();
		children.setType(7);
		child.add(children);
		info.setChildren(child);
		//正则表达式进行替换</P><P>为<br/> 删除不是<br/><img>的标签
		content = content.replaceAll("</[p|P]><[p|P](.+?)>", "<br/>");
		content = content.replaceAll("<(?!/?(?i)(img|br)).*?>", "");
		info.setContent(content);
		children.setContent(content);
		return info;
	}
}