package com.java110.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {
	//校验是不是数字
	public static Boolean isNumber(String str) {
		// TODO Auto-generated method stub
		 Pattern pattern=Pattern.compile("[0-9]*");
	       Matcher match=pattern.matcher(str);
	        if(match.matches()==false)
	        {
	             return false;
	        }
	        else
	        {
	             return true;
	        }
	    }
	//校验是不是null或空字符串
	public static Boolean isNullOrEmpty(String str){
		if(str == null || "".equals(str)){
			return false;
		}
		return true;
	}
}
