package com.java110.bean;

import net.sf.json.JSONObject;

public class WechatUserInfo {

	/**
	 */
	public int subscribe;	
	
	/**
	 */
	public String openid;	
	
	/**
	 */
	public String nickname;

	/**
	 */
	public int sex;	
		 
	/**
	 */
	public String city;	
		 
	/**
	 */
	public String country;	
	
	/**
	 */
	public String province;	
	
	/**
	 */
	public String language;	
		 
	/**
	 */
	public String headimgurl;	
		
	/**
	 */
	public String subscribe_time;	
	
	/**
	 */
	public String unionid;
		
	/**
	 * 
	 * {
	 *     "subscribe": 1, 
	 *     "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
	 *     "nickname": "Band", 
	 *     "sex": 1, 
	 *     "language": "zh_CN", 
	 *     "city": "1", 
	 *     "province": "1", 
	 *     "country": "1", 
	 *     "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0", 
	 *     "subscribe_time": 1382694957,
	 *     "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
	 * }
	 * 
	 * 
	 * {
	 * 	  "openid":" OPENID",
	 *    "nickname": NICKNAME,
	 *    "sex":"1",
	 *    "province":"PROVINCE",
	 *    "city":"CITY",
	 *    "country":"COUNTRY",
	 *    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46", 
	 *    "privilege":[
	 *      "PRIVILEGE1"
	 *      "PRIVILEGE2"
	 *     ]
	 * }
	 * 
	 * @param data
	 * @see []
	 */
	public void parse(JSONObject jsonObject) {
		if(jsonObject.containsKey("subscribe")) {
			subscribe = jsonObject.getInt("subscribe");	
			if(0 != subscribe) {
				openid = jsonObject.getString("openid");
				nickname = jsonObject.getString("nickname");
				sex = jsonObject.getInt("sex");
				language = jsonObject.getString("language");
				city = jsonObject.getString("city");
				province = jsonObject.getString("province");
				country = jsonObject.getString("country");
				headimgurl = jsonObject.getString("headimgurl");
				
				subscribe_time = jsonObject.getString("subscribe_time");
				unionid = jsonObject.getString("unionid");				
			}			
		} else {
			
			openid = jsonObject.getString("openid");
			nickname = jsonObject.getString("nickname");
			sex = jsonObject.getInt("sex");
			language = jsonObject.getString("language");
			city = jsonObject.getString("city");
			province = jsonObject.getString("province");
			country = jsonObject.getString("country");
			headimgurl = jsonObject.getString("headimgurl");
			
			if(jsonObject.containsKey("unionid")) {
				unionid = jsonObject.getString("unionid");
				subscribe = 1;				
			}
		}
	}	
}
