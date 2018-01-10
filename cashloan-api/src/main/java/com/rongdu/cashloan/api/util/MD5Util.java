package com.rongdu.cashloan.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * 
 * @author deelon.hejian
 * 
 */
public class MD5Util {

	public static String MD5(String plainText) {

		String md5Str = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// System.out.println("result: " + buf.toString().substring(8,
			// 24));// 16位的加密
			md5Str = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5Str;
	}
	 // 可逆的加密算法  
	 public static String KL(String inStr) {
	  // String s = new String(inStr);  
	  char[] a = inStr.toCharArray();  
	  for (int i = 0; i < a.length; i++) {  
	   a[i] = (char) (a[i] ^ 't');  
	  }  
	  String s = new String(a);
	  return s;  
	 } 
	 // 加密后解密  
	 public static String JM(String inStr) {
	  char[] a = inStr.toCharArray();  
	  for (int i = 0; i < a.length; i++) {  
	   a[i] = (char) (a[i] ^ 't');  
	  }  
	  String k = new String(a);
	  return k;  
	 }
	 
	 //生成推广码专用 
	 public static String GenerateCode(String s) {
		  char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',   
		    'a', 'b', 'c', 'd', 'e', 'f' };   
		  try {   
		   byte[] strTemp = s.getBytes();   
		   MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		   mdTemp.update(strTemp);   
		   byte[] md = mdTemp.digest();   
		   int j = s.length() / 2;   
		   char str[] = new char[j * 2];   
		   int k = 0;   
		   for (int i = 0; i < j; i++) {   
		    byte byte0 = md[i];   
		    str[k++] = hexDigits[byte0 >>> 4 & 0xf];   
		    str[k++] = hexDigits[byte0 & 0xf];   
		   }   
		   return new String(str);
		  } catch (Exception e) {
		   return null;   
		  }   
}
}
