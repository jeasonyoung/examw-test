package com.ks100;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * 题目标题解密类。
 * 
 * @author yangyong
 * @since 2014年12月20日
 */
public  class QuestionTitleDecrypt {
	private static Logger logger = Logger.getLogger(QuestionTitleDecrypt.class);
	@Test
	public void main(){
//		 String encypt_title = "jZDym6/7NTafpPa1g/qZhPiOgMWAicOrn8y8mc2Ygsa4uNS5i9C5itiDit2I udi6pafB+qfb/qHG56Hj9aTB8qDA2bTrz7LT6bDhwbzzxbji44nu4ozJ+oHS 9o/SzYTK15T16JTV257txJ0=";
		 logger.debug("解密：");
//		 logger.debug(decrypt(encypt_title));
	}
	/**
	 * 解密标题。
	 * @param encyptTitle
	 * 密文标题。
	 * @return
	 * 明文标题。
	 */
	public static final String decrypt(String encyptTitle){
		if(StringUtils.isEmpty(encyptTitle)) return null;
		try {
			return decrypt(encyptTitle, "4E1318FC");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
	//解密。
	private static String decrypt(String encypt,String key) throws Exception {
		byte[] keyBytes = key.getBytes();
		byte[] encyptBytes = new Base64Encoder().decode(encypt);//encodedToArray(encypt);
		IvParameterSpec localIvParameterSpec = new IvParameterSpec(keyBytes);
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(keyBytes,"DES");
		Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		localCipher.init(2, localSecretKeySpec, localIvParameterSpec);
		return new String(localCipher.doFinal(encyptBytes));
	}
	//编码转换为数组。
//	private static byte[] encodedToArray(String encypt) throws IOException {
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		encodedToStream(encypt, outputStream);
//		return outputStream.toByteArray();
//	}
	//编码转换为流
//	private static void encodedToStream(String encypt,OutputStream outputStream) throws IOException {
//		
////		int i = 0,j = encypt.length();
////		while(true){
////			int k;
////			while((i < j) && (encypt.charAt(i) <= ' ')){
////				++i;
////			}
////			if(i == j);
////			do{
////				do{
////					return;
////					k = (replaceChar(encypt.charAt(i)) << 18) + (replaceChar(encypt.charAt(i + 1)) << 12) + (replaceChar(encypt.charAt(i + 2)) << 6) + replaceChar(encypt.charAt(i + 3));
////				}while(encypt.charAt(i + 2) == '=');
////				outputStream.write(0xFF & k >> 8);
////			}while(encypt.charAt(i + 3) == '=');
////			outputStream.write(k & 0xFF);
////			i+=4;
////		}
//	}
	//字符替换
//	private static int replaceChar(char param){
//		if((param >= 'A') && (param <= 'Z'))
//			return param - 'A';
//		if((param >= 'a') && (param <= 'z'))
//			return 26 + param - 'a';
//		if((param >= '0') && (param <= '9'))
//			return 26 + 26 +param - '0';
//		switch(param){
//			case '+': return 62;
//			case '/': return 63;
//			case '=': return 0;
//			default: throw new RuntimeException("unexpected code:" + param);
//		}
//	}
}