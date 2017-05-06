/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.java110.controller.app.auth;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.java110.bean.User;
import com.java110.bean.WechatAuthInfo;
import com.java110.bean.WechatUserInfo;
import com.java110.common.Global;
import com.java110.controller.BaseController;
import com.java110.service.user.UserService;
import com.java110.util.RequestUtil;
import com.java110.util.Util;
import com.java110.util.Utility;

/**
 * 用户信息共享（获取用户信息）
 * 
 * @author taixu.zqq
 * @version $Id: LoginAuthServlet.java, v 0.1 2014年7月25日 下午5:13:03 taixu.zqq Exp
 *          $
 */
@Controller
public class AppLoginAuthController extends BaseController {

	/**  */
	private static final long serialVersionUID = -6807693807426739985L;

	@Resource(name = "UserServiceImpl")
	UserService userServiceImpl;

	/**
	 * 业务处理
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping( { "/AppLoginAuthController.indexPage" })
	public ModelAndView indexPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 1. 解析请求参数
		Map<String, String> params = RequestUtil.getRequestParams(request);

		// 2. 获得authCode
		String authCode = params.get("code");
		String state = params.get("state");
		String url = Global.APP_GET_ACCESS_TOKEN_URL.replace("APPID", Global.APPID)
													.replace("SECRET", Global.APPSECRET)
													.replace("CODE", authCode);
			
			JSONObject jsonObject = Utility.httpRequest(url, "GET", null);	

			// 如果请求成功
			if (null != jsonObject) {
				if(jsonObject.containsKey("access_token")) {
					WechatAuthInfo authInfo = new WechatAuthInfo();
					authInfo.parse(jsonObject);
				
					// 这里仅是简单打印， 请开发者按实际情况自行进行处理

					// 将支付宝 的userId 当作 openId 存放在wOpenId 字段中
					String wOpenId = authInfo.openid;
					// 根据wOpenId 去用户表中查询
					Map paramIn = new HashMap();
					paramIn.put("wOpenId", wOpenId);
					Map userMap = userServiceImpl.getUserBywOpenId(paramIn);
					User user = null;
					if (userMap == null) {
						//拉去用户信息
						 url = Global.APP_GET_USER_INFO_SNSURL.replace("ACCESS_TOKEN", authInfo.access_token)
						 								  .replace("OPENID", authInfo.openid);
						 jsonObject = Utility.httpRequest(url, "GET", null);
//						 WechatUserInfo wechatUserInfo = new WechatUserInfo();
//						 wechatUserInfo.parse(jsonObject);
						// 保存用户至用户表
						// 获取自动生成的userId
						String userId = Util.getUserId();
						paramIn.put("userId", userId);
						paramIn
								.put("name", jsonObject.get("nickname"));
						paramIn.put("sex", jsonObject.get("sex"));
						paramIn.put("email", "");
						paramIn.put("phone", "");
						paramIn.put("wOpenId", jsonObject.get("openid"));
						paramIn.put("createDate", Util.getSimilerDate());
						paramIn.put("months", Util.getMonths());
						paramIn.put("days", Util.getDays());
						int saveUserFlag = userServiceImpl.saveUser(paramIn);
						if (saveUserFlag > 0) {
							// 保存账户信息
							String accountId = Util.getAccountId();
							paramIn.put("userId", userId);
							paramIn.put("accountId", accountId);
							paramIn.put("amount", 0.0);
							paramIn.put("createDate", Util.getSimilerDate());
							paramIn.put("status", "0");
							int accountFlag = userServiceImpl
									.saveAccount(paramIn);
							if (accountFlag > 0) {
								Map paramAccountTradeLog = new HashMap();
								paramAccountTradeLog
										.put("accountId", accountId);
								paramAccountTradeLog.put("channelId",
										Global.CHANNEL_F);
								paramAccountTradeLog.put("amount", 0.0);
								paramAccountTradeLog.put("accountFlag",
										Global.ACCOUNT_UP);
								paramAccountTradeLog.put("createDate", Util
										.getSimilerDate());
								paramAccountTradeLog.put("months", Util
										.getMonths());
								paramAccountTradeLog
										.put("days", Util.getDays());
								int saveAccountTradeLog = userServiceImpl
										.saveAccountTradeLog(paramAccountTradeLog);
								if (saveAccountTradeLog > 0) {
									logger.debug("保存账户交易成功");
								} else {
									logger.error("保存账户交易失败");
								}
								user = new User();
								user.setUserId(userId);
								user.setName(jsonObject.get("nickname").toString());
								user.setSex(String.valueOf(jsonObject.get("sex")));
								user.setEmail("");
								user.setPhone("");
								user.setzOpenId(String.valueOf(jsonObject.get("openid")));
							}
						}
					} else {
						// 将用户保存至session中
						user = new User();
						user.setUserId(userMap.get("userId") == null ? null
								: userMap.get("userId").toString());
						user.setName(userMap.get("name") == null ? null
								: userMap.get("name").toString());
						user.setSex(userMap.get("sex") == null ? null : userMap
								.get("sex").toString());
						user.setEmail(userMap.get("email") == null ? null
								: userMap.get("email").toString());
						user.setPhone(userMap.get("phone") == null ? null
								: userMap.get("phone").toString());
						user.setzOpenId(userMap.get("wOpenId") == null ? null
								: userMap.get("wOpenId").toString());
					}
					this.setContext(Global.PERSON, user);
					String sendRedPacketId = params.get("sendRedPacketId");
					String vedioId = params.get("vedioId");
					String personId = params.get("personId");
					if (!Util.isEmpty(sendRedPacketId)) {
						response
								.sendRedirect(Global.DOMAIN
										+ "/WGetPacketController.indexPage?sendRedPacketId="
										+ sendRedPacketId);
					}else if(!Util.isEmpty(vedioId)){
						//跳转到视频分享页面
						response
						.sendRedirect(Global.DOMAIN
								+ "/ShareVedioController.indexPage?vedioId="
								+ vedioId+"&personId="+personId);
					} else {
						response.sendRedirect(Global.DOMAIN
								+ "/WIndexPacketController.indexPage");
					}
				} else {
					// 这里仅是简单打印， 请开发者按实际情况自行进行处理
					System.out.println("获取用户信息失败");

				}
			} else {
				// 这里仅是简单打印， 请开发者按实际情况自行进行处理
				System.out.println("authCode换取authToken失败");
			}
		return null;
	}

}
