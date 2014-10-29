package test.service;

import org.junit.Test;

public class TestChineseSymbol {

	@Test
	public void main(){
		char c = '。';
		System.out.println(c);
		System.out.println((int)c);
		//final int sbc_char_start = 65281 - 100,sbc_char_end = 65374 + 100, sbc_space = 12288;//12290
		
//		for(int i = sbc_space - 50; i < sbc_space + 100; i++){
//			System.out.println(String.format("%1$d => %2$s", i, (char)i));
//		}
	}
}