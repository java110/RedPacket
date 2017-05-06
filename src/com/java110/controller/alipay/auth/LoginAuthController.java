/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.java110.controller.alipay.auth;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.java110.bean.User;
import com.java110.common.Global;
import com.java110.constants.AlipayServiceEnvConstants;
import com.java110.controller.BaseController;
import com.java110.factory.AlipayAPIClientFactory;
import com.java110.service.user.UserService;
import com.java110.util.Amount;
import com.java110.util.BeanToMapUtil;
import com.java110.util.RequestUtil;
import com.java110.util.Util;

/**
 * 用户信息共享（获取用户信息）
 * 
 * @author taixu.zqq
 * @version $Id: LoginAuthServlet.java, v 0.1 2014年7月25日 下午5:13:03 taixu.zqq Exp
 *          $
 */
@Controller
public class LoginAuthController extends BaseController {

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
	@RequestMapping( { "/LoginAuthController.indexPage" })
	public ModelAndView indexPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 1. 解析请求参数
		Map<String, String> params = RequestUtil.getRequestParams(request);

		// 2. 获得authCode
		String authCode = params.get("auth_code");

		try {
			// 3. 利用authCode获得authToken
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(authCode);
			oauthTokenRequest
					.setGrantType(AlipayServiceEnvConstants.GRANT_TYPE);
			AlipayClient alipayClient = AlipayAPIClientFactory
					.getAlipayClient();
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient
					.execute(oauthTokenRequest);

			// 成功获得authToken
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {

				// 4. 利用authToken获取用户信息
				AlipayUserUserinfoShareRequest userinfoShareRequest = new AlipayUserUserinfoShareRequest();
				AlipayUserUserinfoShareResponse userinfoShareResponse = alipayClient
						.execute(userinfoShareRequest, oauthTokenResponse
								.getAccessToken());

				// 成功获得用户信息
				if (null != userinfoShareResponse
						&& userinfoShareResponse.isSuccess()) {
					// 这里仅是简单打印， 请开发者按实际情况自行进行处理
					System.out.println("获取用户信息成功："
							+ userinfoShareResponse.getBody());

					// 将支付宝 的userId 当作 openId 存放在wOpenId 字段中
					String zOpenId = userinfoShareResponse.getUserId();
					// 根据wOpenId 去用户表中查询
					Map paramIn = new HashMap();
					paramIn.put("zOpenId", zOpenId);
					Map userMap = userServiceImpl.getUserByzOpenId(paramIn);
					User user = null;
					if (userMap == null) {
						// 保存用户至用户表
						// 获取自动生成的userId
						String userId = Util.getUserId();
						paramIn.put("userId", userId);
						paramIn
								.put("name", userinfoShareResponse
										.getNickName());
						paramIn.put("sex", userinfoShareResponse.getGender());
						paramIn.put("email", userinfoShareResponse.getEmail());
						paramIn.put("phone", userinfoShareResponse
								.getDeliverMobile());
						paramIn.put("zOpenId", zOpenId);
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
								user.setName(userinfoShareResponse
										.getNickName());
								user.setSex(userinfoShareResponse.getGender());
								user.setEmail(userinfoShareResponse.getEmail());
								user.setPhone(userinfoShareResponse
										.getDeliverMobile());
								user.setzOpenId(zOpenId);
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
						user.setzOpenId(userMap.get("zOpenId") == null ? null
								: userMap.get("zOpenId").toString());
					}
					this.setContext(Global.PERSON, user);
					String sendRedPacketId = params.get("sendRedPacketId");
					if (!Util.isEmpty(sendRedPacketId)) {
						response
								.sendRedirect(Global.DOMAIN
										+ "/GetPacketController.indexPage?sendRedPacketId="
										+ sendRedPacketId);
					} else {
						response.sendRedirect(Global.DOMAIN
								+ "/HomeController.indexPage");
					}
				} else {
					// 这里仅是简单打印， 请开发者按实际情况自行进行处理
					System.out.println("获取用户信息失败");

				}
			} else {
				// 这里仅是简单打印， 请开发者按实际情况自行进行处理
				System.out.println("authCode换取authToken失败");
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
		}
		return null;
	}

}
