package com.java110.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import sun.net.www.protocol.https.Handler;

import com.java110.common.Global;
import com.java110.controller.BaseController;

/**
 * 工具类
 * 
 * @author wuxw
 * @date 2015-11-28
 * version 1.0
 */
public class Utility extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志打印
	 * 
	 * add by wuxw 2015-11-28
	 * @param name 变量名
	 * @param value 变量值
	 */
	public static void printLog(String name ,String value){
		logger.debug(name+Global.LOGPLUS+":"+Global.LOGPLUS+value+Global.LOGPLUS);
	}
	/**
	 * 打印日志（说明）
	 * 
	 * add by wuxw 2015-11-28
	 * @param str
	 */
	public static void printLog(String str){
		logger.debug(Global.LOGPLUS+str+Global.LOGPLUS);
	}
	//校验是不是null或空字符串
	public static Boolean isNotEmpty(String str){
		if(str == null || "".equals(str)){
			return false;
		}
		return true;
	}
	/**
	 * 根据domain 和 hcode 获取 pcode
	 * 
	 * add by wuxw 2015-12-28
	 * @param domain
	 * @param pCode
	 */
	public static String getPCodeByDomainAndHcode(String domain,String hCode){
		if (!Common.isNullOrEmpty(domain) || !Common.isNullOrEmpty(hCode)){
			return null;
		}
		List<Map> codeMaps = StaticMapUtil.getCodeMapBeans();
		for(Map codeMap : codeMaps){
			if(domain.equals(codeMap.get("domain")) && hCode.equals(codeMap.get("hcode"))){
				return codeMap.get("pcode").toString();
			}
		}
		return null;
	}
	/**
	 * 根据hcode 获取 pcode
	 * 
	 * add by wuxw 2015-12-28
	 * @param domain
	 * @param pCode
	 */
	public static String getPCodeByHcode(String hCode){
		if ( Common.isNullOrEmpty(hCode)){
			return null;
		}
		List<Map> codeMaps = StaticMapUtil.getCodeMapBeans();
		for(Map codeMap : codeMaps){
			if(hCode.equals(codeMap.get("hcode"))){
				return codeMap.get("pcode").toString();
			}
		}
		return null;
	}
	
	/**
	 * 随机红包处理(个人)
	 * 
	 * add by wuxw 2016-2-17
	 */
	public static List<Map> randomRedPacket(int copies, double money) {
		// int copies = 7; // 份数
		// double money = 20.00;
		double min = 0.01;
		double m = 0;
		List<Map> moneyList = new ArrayList<Map>();
		Map<String, Double> moneyMap = null;
		int copiesCount = copies;
		for (int i = 0; i < copiesCount; i++) {
			// 最小红包金额低于0.01元时
			if (Amount.div(money, copies) < min) {
				money += m;
				double c_min_last_money = Amount.sub(money, (Amount.mul(min,
						copies))); // 当金额不够时分前一个红包的金额，补充后面红包，以满足最小红包不能超过0.01
				moneyList.remove(moneyMap);
				moneyMap = new HashMap<String, Double>();
				moneyMap.put(Global.RED_MONEY, c_min_last_money);
				moneyList.add(moneyMap);

				// 将剩余的最低金额放入List中
				for (i = copies; i > 0; i--) {
					moneyMap = new HashMap<String, Double>();
					moneyMap.put(Global.RED_MONEY, min);
					moneyList.add(moneyMap);
				}
				break;
			}
			// 最小红包金额等于0.01元时
			if (Amount.div(money, copies) == min) {
				// 将剩余的最低金额放入List中
				for (i = copies; i > 0; i--) {
					moneyMap = new HashMap<String, Double>();
					moneyMap.put(Global.RED_MONEY, min);
					moneyList.add(moneyMap);
				}
				break;
			}
			if (copies == 1) {
				moneyMap = new HashMap<String, Double>();
				moneyMap.put(Global.RED_MONEY, money);
				moneyList.add(moneyMap);
				break;
			}
			double max = Amount.div(money, (Amount.mul(copies, 0.5)));
			Random r = new Random();
			m = r.nextDouble() * max;
			m = Math.floor(m * 100) / 100;
			if (m < min) {
				m = min;
			}
			money = Amount.sub(money, m);
			moneyMap = new HashMap<String, Double>();
			moneyMap.put(Global.RED_MONEY, m);
			moneyList.add(moneyMap);
			copies--;
		}
		return moneyList;
	}
	
	

	public static String getAccessToken() throws Exception {
		try {
			String url = Global.TOKEN;
			System.out.println(">>>>>>>>>>>>>>>获取" + url);
			JSONObject jsonObject = httpRequest(url, "GET", null);
			System.out.println(jsonObject);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					if ("7200".equals(jsonObject.getString("expires_in"))) {
						return jsonObject.getString("access_token");
					}
				} catch (JSONException e) {
					// 获取token失败
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr)
	  {
	    JSONObject jsonObject = null;
	    StringBuffer buffer = new StringBuffer();
	    try
	    {
	      TrustManager[] tm = { new MyX509TrustManager() };
	      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	      sslContext.init(null, tm, new SecureRandom());

	      SSLSocketFactory ssf = sslContext.getSocketFactory();

	      URL url = new URL(null, requestUrl, new Handler());
	      HttpsURLConnection httpUrlConn = (HttpsURLConnection)url
	        .openConnection();
	      httpUrlConn.setSSLSocketFactory(ssf);

	      httpUrlConn.setDoOutput(true);
	      httpUrlConn.setDoInput(true);
	      httpUrlConn.setUseCaches(false);

	      httpUrlConn.setRequestMethod(requestMethod);

	      if ("GET".equalsIgnoreCase(requestMethod)) {
	        httpUrlConn.connect();
	      }

	      if (outputStr != null) {
	        OutputStream outputStream = httpUrlConn.getOutputStream();

	        outputStream.write(outputStr.getBytes("UTF-8"));
	        outputStream.close();
	      }

	      InputStream inputStream = httpUrlConn.getInputStream();
	      InputStreamReader inputStreamReader = new InputStreamReader(
	        inputStream, "utf-8");
	      BufferedReader bufferedReader = new BufferedReader(
	        inputStreamReader);

	      String str = null;
	      while ((str = bufferedReader.readLine()) != null) {
	        buffer.append(str);
	      }
	      bufferedReader.close();
	      inputStreamReader.close();

	      inputStream.close();
	      inputStream = null;
	      httpUrlConn.disconnect();
	      jsonObject = JSONObject.fromObject(buffer.toString());
	    } catch (ConnectException ce) {
	    	ce.printStackTrace();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return jsonObject;
	  }
  public static String SHA1Encode(String sourceString)
  {
    String resultString = null;
    try {
      resultString = new String(sourceString);
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      resultString = byte2hexString(md.digest(resultString.getBytes()));
    } catch (Exception localException) {
    }
    return resultString;
  }

  public static final String byte2hexString(byte[] bytes)
  {
    StringBuffer buf = new StringBuffer(bytes.length * 2);
    for (int i = 0; i < bytes.length; i++) {
      if ((bytes[i] & 0xFF) < 16) {
        buf.append("0");
      }
      buf.append(Long.toString(bytes[i] & 0xFF, 16));
    }
    return buf.toString().toUpperCase();
  }

  public static int GetDistance(String lat1, String lng1, String lat2, String lng2)
  {
    double EARTH_RADIUS = 6378.1369999999997D;
    double radLat1 = rad(Float.parseFloat(lat1));
    double radLat2 = rad(Float.parseFloat(lat2));
    double a = radLat1 - radLat2;
    double b = rad(Float.parseFloat(lng1)) - rad(Float.parseFloat(lng2));

    double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D) + 
      Math.cos(radLat1) * Math.cos(radLat2) * 
      Math.pow(Math.sin(b / 2.0D), 2.0D)));
    s *= EARTH_RADIUS;
    s = Math.round(s * 10000.0D) / 10L;
    return (int)s;
  }

  private static double rad(double d) {
    return d * 3.141592653589793D / 180.0D;
  }



  public static String itudeConvert(String location)
    throws Exception
  {
    double location_D = 0.0D;
    if ((!"".equals(location)) && (location.indexOf("#") > 0)) {
      String[] local = location.split("#");
      location_D = Double.parseDouble(local[0]);
      if (local.length > 1) {
        location_D += Double.parseDouble(local[1]) / 60.0D;
      }
      if (local.length > 2) {
        location_D += Double.parseDouble(local[2]) / 3600.0D;
      }
    }
    return String.valueOf(location_D);
  }


  public static String getSignature(String timestamp, String nonce, String token)
    throws Exception
  {
    String maxString = "";
    String minString = "";
    if (timestamp.compareTo(nonce) < 0) {
      maxString = nonce;
      minString = timestamp;
    } else {
      maxString = timestamp;
      minString = nonce;
    }

    if (token.compareTo(maxString) > 0)
      token = minString + maxString + token;
    else if (token.compareTo(minString) < 0)
      token = token + minString + maxString;
    else {
      token = minString + token + maxString;
    }
    return SHA1Encode(token);
  }

 
  public static String httpRequest(String requestUrl)
  {
    StringBuffer buffer = null;
    try
    {
      URL url = new URL(requestUrl);
      HttpURLConnection httpUrlConn = (HttpURLConnection)url
        .openConnection();
      httpUrlConn.setDoInput(true);
      httpUrlConn.setRequestMethod("GET");

      InputStream inputStream = httpUrlConn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(
        inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(
        inputStreamReader);

      buffer = new StringBuffer();
      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }

      bufferedReader.close();
      inputStreamReader.close();
      inputStream.close();
      httpUrlConn.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return buffer.toString();
  }

  public static String getRandomPwd()
  {
    long seed = 9999L;
    long rand = seed + Math.round(Math.random() * 1000000.0D);
    String pwd = String.valueOf(rand);
    if (pwd.length() < 6)
      pwd = String.valueOf(999999L - rand);
    else {
      pwd = pwd.substring(0, 6);
    }
    return pwd;
  }

  public static String getYearMonth()
    throws Exception
  {
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMM");
    Calendar cal = Calendar.getInstance();
    return dateformat.format(cal.getTime());
  }

  public static String getMonth()
    throws Exception
  {
    SimpleDateFormat dateformat = new SimpleDateFormat("MM");
    Calendar cal = Calendar.getInstance();
    return dateformat.format(cal.getTime());
  }

  
  public static String convertTime(long time)
    throws Exception
  {
    long second = time % 60L;
    long minute = (time - second) % 3600L / 60L;
    long hour = (time - second - minute * 60L) / 3600L;
    return hour + "Сʱ" + minute + "��" + second + "��";
  }

  public static String convertFlow(long flow)
    throws Exception
  {
    long bit = flow % 1024L;
    long kByte = (flow - bit) % 1048576L / 1024L;
    long mByte = (flow - bit - kByte * 1024L) / 1048576L;
    return mByte + "MB " + kByte + "KB " + bit + "bit";
  }
  
	/**
	 * ��ȡһ��ȥ�����ַ�"-"��UUID
	 * 
	 * @return
	 * @see [�ࡢ��#��������#��Ա]
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// ȥ����-�����
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}
	
	/**
	 * @author liangly
	 * @return ��ȡ������ĩ���ʱ�� ��ʽ��yyyyMMdd
	 * @throws Exception
	 */

	public static String getMonthLastDay() throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return sf.format(calendar.getTime()).toString();
	}
	
	/**
	 * @author
	 * @return �ϸ���ĩ���һ�� ��ʽ��yyyy-MM-dd
	 * @throws Exception
	 */
	public static String getLastMonthLastDay() throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, -1);
		calendar.roll(Calendar.DATE, 1);
		return sf.format(calendar.getTime()).toString();
	}
	
	 public static void printJson(HttpServletResponse response, String text) {
		    try {
		      print(response, "text/html;charset=UTF-8", text);
		    } catch (Exception ex) {
		    }
		  }
	
	  private static void print(HttpServletResponse response, String contentType, String text)
			    throws IOException
	  {
	    response.setContentType(contentType);
	    response.setHeader("Pragma", "No-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0L);
	    PrintWriter pw = response.getWriter();
	    pw.write(text);
	    pw.close();
	  }
	  
	  public static String formatText(String toUserName, String fromUserName, String content)
	    {
	        String str = "";
	        str = String.format("<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[%4$s]]></Content><FuncFlag>0</FuncFlag></xml>", new Object[] {
	            fromUserName, toUserName, Long.valueOf((new Date()).getTime()), content
	        });
	        return str;
	    }
	  
	  /**
		 * 
		 * toGetData 判断是否要去取数据
		 * 
		 * @param saveTime
		 * @return
		 * @throws Exception
		 * @return boolean返回说明
		 * @Exception 异常说明
		 * @author：zhangkun3
		 * @create：2014-1-21 上午11:42:31
		 * @moduser：
		 * @moddate：
		 * @remark：
		 */
		public static boolean toGetData(String saveTime, int delay) throws Exception {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// pm-time存放的是到网站取数据的时间，单位秒
			calendar.add(Calendar.MINUTE, -delay);
			return sf.parse(saveTime).before(sf.parse(sf.format(calendar.getTime())));
		}
		/**
		 * 去除html标签
		 * 
		 * add by wuxw 2016-3-2
		 * @param htmlStr
		 * @return
		 */
		public static String delHTMLTag(String htmlStr){ 
	        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
	         
	        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	        Matcher m_script=p_script.matcher(htmlStr); 
	        htmlStr=m_script.replaceAll(""); //过滤script标签 
	         
	        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	        Matcher m_style=p_style.matcher(htmlStr); 
	        htmlStr=m_style.replaceAll(""); //过滤style标签 
	         
	        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	        Matcher m_html=p_html.matcher(htmlStr); 
	        htmlStr=m_html.replaceAll("").replace("&nbsp;", ""); //过滤html标签 

	        return htmlStr.trim(); //返回文本字符串 
	    } 
}
