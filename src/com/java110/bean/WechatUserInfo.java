package com.java110.bean;

import net.sf.json.JSONObject;

public class WechatUserInfo {

	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
	public int subscribe;	
	
	/**
	 * 用户的标识，对当前公众号唯一
	 */
	public String openid;	
	
	/**
	 * 用户的昵称
	 */
	public String nickname;

	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public int sex;	
		 
	/**
	 * 用户所在城市
	 */
	public String city;	
		 
	/**
	 * 用户所在国家
	 */
	public String country;	
	
	/**
	 * 用户所在省份
	 */
	public String province;	
	
	/**
	 *  用户的语言，简体中文为zh_CN
	 */
	public String language;	
		 
	/**
	 *  用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	 */
	public String headimgurl;	
		
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	public String subscribe_time;	
	
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 */
	public String unionid;
		
	/**
	 * 将从数据库获取到的IData 数据解析到成员变量中
	 * 
	 * 正常情况下，微信会返回下述JSON数据包给公众号：
	 * {
	 *     "subscribe": 1, 
	 *     "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
	 *     "nickname": "Band", 
	 *     "sex": 1, 
	 *     "language": "zh_CN", 
	 *     "city": "广州", 
	 *     "province": "广东", 
	 *     "country": "中国", 
	 *     "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0", 
	 *     "subscribe_time": 1382694957,
	 *     "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
	 * }
	 * 
	 * 正确时返回的JSON数据包如下：
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
	 * @see [类、类#方法、类#成员]
	 */
	public void parse(JSONObject jsonObject) {
		//对关注用户可多获取到的
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
