package com.examw.test.junittest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.library.ItemType;

/**
 * 
 * @author fengwei.
 * @since 2014年9月3日 下午5:25:50.
 */
public class Test01 {
	@Test
	public void testStr(){
		String answer = "ABCD";
		String arr[] = answer.split("");
		char[] c = answer.toCharArray();
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(c));
		String id = "cbe9d85a-fd6b-4e6b-8fac-745a37f4d980,b83e1d20-c1f5-4876-bbfa-aa56a243298c,16ed2088-e503-425d-a301-31241079d8e8";
		System.out.println(id);
		System.out.println(id.replaceAll("([a-z0-9-]{36})", "'$1'"));
		StructureItemInfo info = new StructureItemInfo();
		info.setType(1);
		System.out.println(info.getType() == ItemType.SINGLE.getValue());
		
		String content = "";
		content = content.replaceAll("</[p|P]><[p|P](.+?)>", "<br/>");
		content = content.replaceAll("<(?!/?(?i)(img|br)).*?>", "");
		ItemType type = ItemType.convert(6);
		System.out.println(type.equals(ItemType.SHARE_TITLE));
		Map<Integer,Integer> map  = new HashMap<Integer,Integer>();
		map.put(1, 1);
		map.put(2, 2);
		System.out.println(map);
	}
}
