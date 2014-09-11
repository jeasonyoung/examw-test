package com.examw.test.junittest;

import java.util.Arrays;

import org.junit.Test;

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
	}
}
