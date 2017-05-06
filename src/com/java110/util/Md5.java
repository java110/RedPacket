package com.java110.util;

import java.security.MessageDigest;



/**
 * md5加密
 * @author Administrator
 *
 */
public class Md5 {
	

	public String loginMd5(String md5String){
		String md5="";
		try {
			MessageDigest messageDigest =MessageDigest.getInstance("MD5");
			byte[] mm=messageDigest.digest(md5String.getBytes());
			 md5=toHex(mm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return md5;
	}
	public String toHex(byte[] result){

		StringBuilder stringBuilder= new StringBuilder();
		for(int i=0;i<result.length;i++){
			int hi=((result[i]>>4) & 0x0f);
			int lo=result[i] & 0x0f;
			stringBuilder.append(hi>9?(char)((hi-10)+'a'):(char)(hi+'0'));
			stringBuilder.append(lo>9?(char)((lo-10)+'a'):(char)(lo+'0'));
		}
		
		return stringBuilder.toString();
	}
}
