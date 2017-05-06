package com.java110.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import com.java110.common.SpringAppFactory;
import com.java110.service.common.CommonService;

/**
 * 工具类（主要实现系统工具方法实现）
 * 
 * @author wuxw
 * @date 2016-1-22 version 1.0
 */
public class Util {
	/**
	 * 获取当前日期的 yyyy-MM-dd HH:mm:SS格式
	 * 
	 * @return
	 */
	public static String getSimilerDatePath() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	/**
	 * 获取当前日期的yyyy/MM/dd格式
	 * 
	 * @return
	 */
	public static String getSimilerDate() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		return df.format(new Date());
	}

	/**
	 * 获取当前日期的yyyy年MM月dd日格式
	 * 
	 * @return
	 */
	public static String getDateString() {
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		return df.format(new Date());
	}

	/**
	 * 获取月份
	 * 
	 * add by wuxw 2016-2-18
	 * 
	 * @return
	 */
	public static int getMonths() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	public static String getFormatDate(String format) throws Exception {
		if (Util.isEmpty(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		return dateformat.format(cal.getTime());
	}

	/**
	 * 获取days
	 * 
	 * add by wuxw 2016-2-18
	 * 
	 * @return
	 */
	public static int getDays() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}

	public static byte[] md5(byte[] src) throws NoSuchAlgorithmException {
		MessageDigest tool = MessageDigest.getInstance("MD5");
		tool.update(src);
		byte[] byts = tool.digest();
		return byts;
	}

	/**
	 * MD5 加密
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String md5(String src) throws Exception {
		return new String(md5(src.getBytes()));
	}

	/**
	 * 创建32位seqid 采用uuid算法
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		return str.replaceAll("-", "");
	}

	public static Map<String, String> getRequestMap(Map map) {
		Map<String, String> rst = new HashMap<String, String>();
		Set keys = map.keySet();
		for (Object obj : keys) {
			String key = String.valueOf(obj);
			String[] value = (String[]) map.get(key);
			if (value.length > 0) {
				rst.put(key, value[0]);
			}
		}
		return rst;
	}

	public static void responseAJAXData(HttpServletResponse response,
			String writeValue) throws IOException {
		response.setContentType("text/html");
		response.setContentType("utf-8");
		response.getWriter().write(writeValue);
	}

	/**
	 * 数值判断
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^([1-9]{1})([0-9]*)");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * String 空值判断(为空)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * String 空值判断(不为空)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		str = str.trim();
		if (null == str || "".equals(str)) {
			return false;
		}
		return true;
	}

	/**
	 * Map 空值判断(为空)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Map str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * Map 空值判断(不为空)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Map str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}
	/**
	 * 获取秒杀日期
	 * 
	 * add by wuxw 2016-3-9
	 * @param str
	 * @return
	 */
	public static Date getSpikeDate(String str) {
	      Date convertDate = null;
	       SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	       try {
	          format.setLenient(false);
	          convertDate = format.parse(str);
	       } catch (Exception e) {
	    	   //出现异常就获取当前时间
	    	   convertDate= new Date();
	       } 
	       return convertDate;
	}
	/**
	 * 时间比较
	 * 
	 * add by wuxw 2016-3-9
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date(String DATE1, String DATE2) {
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
        	return 0;
        }
    }
	
	/**
	 * 时间比较
	 * 
	 * add by wuxw 2016-3-9
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date_2_sysdate(String DATE1) {
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = new Date();
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return -1;
            }
        } catch (Exception exception) {
        	return -1;
        }
    }
	public static void main(String[] args) {
		System.out.println(compare_date_2_sysdate("2016-03-23 13:13:13"));
	}
	/**
	 * 获取发红包主键
	 * 
	 * @return
	 */
	public static String getSendRedPacketId() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		CommonService cs = (CommonService) SpringAppFactory
				.getBean("CommonServiceImpl");
		Map info = new HashMap();
		info.put("primaryName", "sendRedPacketId");
		return "10000000" + df.format(new Date()) + "0000"
				+ cs.getPrimaryKey(info).get("val");
	}

	/**
	 * 获取用户主键
	 * 
	 * @return
	 */
	public static String getUserId() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		CommonService cs = (CommonService) SpringAppFactory
				.getBean("CommonServiceImpl");
		Map info = new HashMap();
		info.put("primaryName", "userId");
		return "70" + df.format(new Date()) + "0000"
				+ cs.getPrimaryKey(info).get("val");
	}

	/**
	 * 获取账户主键
	 * 
	 * @return
	 */
	public static String getAccountId() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		CommonService cs = (CommonService) SpringAppFactory
				.getBean("CommonServiceImpl");
		Map info = new HashMap();
		info.put("primaryName", "accountId");
		return "60" + df.format(new Date()) + "0000"
				+ cs.getPrimaryKey(info).get("val");
	}

	/**
	 * 抢红包主键
	 * 
	 * @return
	 */
	public static String getRedPacketId() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		CommonService cs = (CommonService) SpringAppFactory
				.getBean("CommonServiceImpl");
		Map info = new HashMap();
		info.put("primaryName", "getRedPacketId");
		return "60" + df.format(new Date()) + "0000"
				+ cs.getPrimaryKey(info).get("val");
	}

	/**
	 * 兑红包主键
	 * 
	 * @return
	 */
	public static String getExchangeRedPacketId() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		CommonService cs = (CommonService) SpringAppFactory
				.getBean("CommonServiceImpl");
		Map info = new HashMap();
		info.put("primaryName", "exchangeRedPacketId");
		return "50" + df.format(new Date()) + "0000"
				+ cs.getPrimaryKey(info).get("val");
	}

	/**
	 * 支付主键
	 * 
	 * @return
	 */
	public static String getPayId() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		CommonService cs = (CommonService) SpringAppFactory
				.getBean("CommonServiceImpl");
		Map info = new HashMap();
		info.put("primaryName", "payId");
		return "40" + df.format(new Date()) + "0000"
				+ cs.getPrimaryKey(info).get("val");
	}
}
