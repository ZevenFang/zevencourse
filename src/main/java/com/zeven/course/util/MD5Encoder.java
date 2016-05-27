package com.zeven.course.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 *
 */
public class MD5Encoder {
	
	/**
	 * 加密操作
	 * @param str 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encode(String str){
		byte []b = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			b = digest.digest(str.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String s = "";
		for(int i=0;i<b.length;i++){
			String temp = Integer.toHexString(0xff&b[i]);
			if(temp.length()==1)
				temp = '0'+temp;
			s+=temp;
		}
		return s;
	}
	
}
