package com.java110.bean;

import net.sf.json.JSONObject;

public class WechatAuthInfo {

	/**
	 * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 */
	public String access_token;	
	
	/**
	 * access_token接口调用凭证超时时间，单位（秒）
	 */
	public int expires_in;
	
	/**
	 * 用户刷新access_token
	 */
	public String refresh_token;
	
	/**
	 * 用户唯一标识
	 */
	public String openid;
	
	/**
	 * 用户授权的作用域，使用逗号（,）分隔
	 */
	public String scope;
	
	/**
	 * 从微信平台获取到access_token的时间
	 */
	public long getTime;
	
	/**
	 * 将从数据库获取到的IData 数据解析到成员变量中
	 * 
	 * @param data
	 * @see [类、类#方法、类#成员]
	 */
	public void parse(JSONObject jsonObject) {
		access_token = jsonObject.getString("access_token");	
		expires_in = jsonObject.getInt("expires_in");
		refresh_token = jsonObject.getString("refresh_token");
		openid = jsonObject.getString("openid");
		scope = jsonObject.getString("scope");
		
		getTime = System.currentTimeMillis();
	}	
	
	/**
	 * 判断access_token是否已过期
	 * @return
	 */
	public boolean isExpired() {
		if((System.currentTimeMillis() - getTime) > expires_in*1000) {
			return true;
		} else {
			return false;
		}
	}
}
