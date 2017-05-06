package com.java110.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.java110.bean.OutJson;
import com.java110.bean.User;
import com.java110.common.Global;
import com.java110.constants.AlipayServiceEnvConstants;
import com.java110.factory.SpringAppFactory;
import com.java110.service.user.UserService;
import com.java110.util.Util;
import com.java110.util.Utility;

public abstract class BaseController extends HttpServlet {
	protected static Logger logger = Logger.getLogger(BaseController.class);
	protected Map<String, Object> viewData = null;
	private static HttpSession session = null;

	private static Map<String, String> tokenMap = new HashMap<String, String>();

	private static Map<String, String> jsapi_ticket = new HashMap<String, String>();

	public BaseController() {
		logger = Logger.getLogger(this.getClass());
		viewData = new HashMap<String, Object>();
	}

	/**
	 * 登录校验
	 * 
	 * add by wuxw 2016-1-31
	 * 
	 * @param request
	 * @return
	 */
	public boolean loginValidate(HttpServletRequest request) {
		// 生产获取用户
//		User user = this.getUser();
		// 测试获取用户
		 User user = this.getTestUser();
		if (user == null) {
			return false;
		}
		return true;
	}

	/**
	 * 从session中获取用户
	 * 
	 * add by wuxw 2016-2-18
	 * 
	 * @return
	 */
	public User getUser() {
		return this.getContext(Global.PERSON);
	}

	public void setContext(String sessionName, Object obj) {
		session.setAttribute(sessionName, obj);
	}

	public <T> T getContext(String sessionName) {
		return (T) session.getAttribute(sessionName);
	}

	/**
	 * 设置session
	 * 
	 * add by wuxw 2016-1-29
	 * 
	 * @param request
	 * @param sessionName
	 * @param obj
	 */
	public void setContext(HttpServletRequest request, String sessionName,
			Object obj) {
		if (session == null) {
			session = request.getSession();
		}
		session.setAttribute(sessionName, obj);
	}

	/**
	 * 获取上下文对象
	 * 
	 * add by wuxw 2016-1-29
	 * 
	 * @param <T>
	 * @param request
	 * @param sessionName
	 * @return
	 */
	public <T> T getContext(HttpServletRequest request, String sessionName) {
		if (session == null) {
			session = request.getSession();
		}
		return (T) session.getAttribute(sessionName);
	}

	protected Map getRequestData(HttpServletRequest request) {
		return Util.getRequestMap(request.getParameterMap());
	}

	public Map<String, Object> getViewData() {
		return this.viewData;
	}

	public WebApplicationContext getAppCtx(HttpServletRequest request) {
		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession()
						.getServletContext());
	}

	public HttpSession getSession() {
		return this.session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * 获取web信息
	 * 
	 * add by wuxw 2016-1-31
	 * 
	 * @return
	 */
	public Map getWebInfo() {
		Map outParam = new HashMap();
		outParam.put("IMG_DOMAIN", Global.IMG_DOMAIN);
		outParam.put("STATIC_DOMAIN", Global.STATIC_DOMAIN);
		outParam.put("DOMAIN", Global.DOMAIN);
		return outParam;
	}

	/**
	 * 获取用户信息
	 * 
	 * add by wuxw 2016-1-31
	 * 
	 * @return
	 */
	public Map getUserInfo(HttpServletRequest request) {
		Map outParam = new HashMap();
		// Person p = this.getContext(request,Global.PERSON);
		// if(p != null){
		// outParam.put("personId", p.getPersonid());
		// outParam.put("name", p.getName());
		// }
		return outParam;
	}

	/**
	 * 输出JSON 数据
	 * 
	 * add by wuxw 2016-2-16
	 * 
	 * @param response
	 * @param outJson
	 * @throws IOException
	 */
	protected void outJson(HttpServletResponse response, OutJson outJson)
			throws IOException {
		PrintWriter writer = response.getWriter();
		JSONObject oJson = JSONObject.fromObject(outJson);
		writer.print(oJson);
		writer.flush();
	}

	/**
	 * 支付宝鉴权
	 * 
	 * add by wuxw 2016-2-21
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected Boolean loginAuthFlag(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (!this.loginValidate(request)) {
			String sendRedPacketId = request.getParameter("sendRedPacketId");

			// 抢红宝用户支付宝鉴权
			if (!Util.isEmpty(sendRedPacketId)) {
				// 用户没有登录，去支付宝鉴权
				String openUrl = AlipayServiceEnvConstants.OPEN_AUTH
						.replace("APPID", AlipayServiceEnvConstants.APP_ID)
						.replace("SCOPE", "auth_userinfo")
						.replace(
								"ENCODED_URL",
								URLEncoder
										.encode(
												(Global.DOMAIN
														+ "/LoginAuthController.indexPage?sendRedPacketId=" + sendRedPacketId),
												"UTF-8"));
				response.sendRedirect(openUrl);
			} else {
				// 用户没有登录，去支付宝鉴权
				String openUrl = AlipayServiceEnvConstants.OPEN_AUTH.replace(
						"APPID", AlipayServiceEnvConstants.APP_ID).replace(
						"SCOPE", "auth_userinfo").replace(
						"ENCODED_URL",
						URLEncoder.encode(Global.DOMAIN
								+ "/LoginAuthController.indexPage", "UTF-8"));
				response.sendRedirect(openUrl);
			}

			return false;
		}
		return true;
	}

	/**
	 * 微信鉴权
	 * 
	 * add by wuxw 2016-2-21
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected Boolean loginAuth(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//判断是微信客户端还是自己的app进来的
		String clientFlag = request.getParameter("clientFlag");
		if(Global.CLIENT_FLAG_APP.equals(clientFlag)){ //app鉴权
			return appLoginAuthFlag(request,response);
		}else{//微信鉴权
			return weiXinLoginAuth(request,response);
		}
	}
	
	protected Boolean weiXinLoginAuth(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		if (!this.loginValidate(request)) {
			String sendRedPacketId = request.getParameter("sendRedPacketId");
			// 抢红宝用户支付宝鉴权
			String vedioId = request.getParameter("vedioId");
			String personId = request.getParameter("personId");
			if (!Util.isEmpty(sendRedPacketId)) {
				// 用户没有登录，去微信鉴权
				String openUrl = Global.OPEN_AUTH
						.replace("APPID", Global.APPID)
						.replace("SCOPE", "snsapi_userinfo")
						.replace(
								"REDIRECT_URL",
								URLEncoder
										.encode(
												(Global.DOMAIN
														+ "/WLoginAuthController.indexPage?sendRedPacketId=" + sendRedPacketId),
												"UTF-8")).replace("STATE", "1");
				logger.debug("鉴权地址" + openUrl);
				response.sendRedirect(openUrl);
			} else if (!Util.isEmpty(vedioId)) {
				// 用户没有登录，去微信鉴权
				String openUrl = Global.OPEN_AUTH
						.replace("APPID", Global.APPID)
						.replace("SCOPE", "snsapi_userinfo")
						.replace(
								"REDIRECT_URL",
								URLEncoder
										.encode(
												(Global.DOMAIN
														+ "/WLoginAuthController.indexPage?vedioId="
														+ vedioId
														+ "&personId=" + personId),
												"UTF-8")).replace("STATE", "1");
				logger.debug("鉴权地址" + openUrl);
				
				response.sendRedirect(openUrl);
			} else {
				// 用户没有登录，去微信鉴权
				String openUrl = Global.OPEN_AUTH
						.replace("APPID", Global.APPID).replace("SCOPE",
								"snsapi_userinfo").replace(
								"REDIRECT_URL",
								URLEncoder.encode(Global.DOMAIN
										+ "/WLoginAuthController.indexPage",
										"UTF-8")).replace("STATE", "1");
				logger.debug("鉴权地址" + openUrl);
				response.sendRedirect(openUrl);
			}
			return false;
		}
		return true;
	}
	
	
	/**
	 * 微信鉴权
	 * 
	 * add by wuxw 2016-2-21
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected Boolean appLoginAuthFlag(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (!this.loginValidate(request)) {
			String sendRedPacketId = request.getParameter("sendRedPacketId");
			// 抢红宝用户支付宝鉴权
			String vedioId = request.getParameter("vedioId");
			String personId = request.getParameter("personId");
			if (!Util.isEmpty(sendRedPacketId)) {
				// 用户没有登录，去微信鉴权
				String openUrl = Global.APP_OPEN_AUTH
						.replace("APPID", Global.APPID)
						.replace("SCOPE", "snsapi_userinfo")
						.replace(
								"REDIRECT_URL",
								URLEncoder
										.encode(
												(Global.DOMAIN
														+ "/AppLoginAuthController.indexPage?sendRedPacketId=" + sendRedPacketId),
												"UTF-8")).replace("STATE", "1");
				logger.debug("鉴权地址" + openUrl);
				response.sendRedirect(openUrl);
			} else if (!Util.isEmpty(vedioId)) {
				// 用户没有登录，去微信鉴权
				String openUrl = Global.APP_OPEN_AUTH
						.replace("APPID", Global.APPID)
						.replace("SCOPE", "snsapi_userinfo")
						.replace(
								"REDIRECT_URL",
								URLEncoder
										.encode(
												(Global.DOMAIN
														+ "/AppLoginAuthController.indexPage?vedioId="
														+ vedioId
														+ "&personId=" + personId),
												"UTF-8")).replace("STATE", "1");
				logger.debug("鉴权地址" + openUrl);
				
				response.sendRedirect(openUrl);
			} else {
				// 用户没有登录，去微信鉴权
				String openUrl = Global.OPEN_AUTH
						.replace("APPID", Global.APPID).replace("SCOPE",
								"snsapi_userinfo").replace(
								"REDIRECT_URL",
								URLEncoder.encode(Global.DOMAIN
										+ "/AppLoginAuthController.indexPage",
										"UTF-8")).replace("STATE", "1");
				logger.debug("鉴权地址" + openUrl);
				response.sendRedirect(openUrl);
			}
			return false;
		}
		return true;
	}

	/**
	 * 电脑端测试用
	 * 
	 * add by wuxw 2016-2-23
	 * 
	 * @return
	 */
	private User getTestUser() {
		UserService userService = (UserService) SpringAppFactory
				.getBean("UserServiceImpl");
		Map paramIn = new HashMap();
		paramIn.put("userId", "10020160223001");
		Map userMap = userService.getUserByUserId(paramIn);
		User user = new User();
		user.setUserId(userMap.get("userId").toString());
		user.setName(userMap.get("name").toString());
		user.setzOpenId(userMap.get("zOpenId").toString());
		this.setContext(Global.PERSON, user);
		return user;
	}

	/**
	 * 获取微信菜单凭证
	 * 
	 * @return null表示获取失败
	 * @throws Exception
	 */
	public static String getAccessToken() throws Exception {
		String token = "";
		if (null != tokenMap.get("TIME")
				&& !Utility.toGetData(tokenMap.get("TIME"), 60)) {
			token = tokenMap.get("TOKEN");
		} else {
			String url = Global.GET_ACCESS_TOKEN.replace("APPID", Global.APPID)
					.replace("SECRET", Global.APPSECRET);
			JSONObject jsonObject = Utility.httpRequest(url, "GET", null);
			logger.debug("获取AccessToken URL ：" + url);
			// 如果请求成功
			if (null != jsonObject) {
				logger.debug("获取jsonObject ：" + jsonObject.toString());
				try {
					if ("7200".equals(jsonObject.getString("expires_in"))) {
						token = jsonObject.getString("access_token");
						tokenMap.put("TIME", Util.getFormatDate(null));
						tokenMap.put("TOKEN", token);
					}
				} catch (JSONException e) {
					// 获取token失败
					logger.error("获取token失败:errorCode--"
							+ jsonObject.getString("errcode") + "--errmsg--"
							+ jsonObject.getString("errmsg"));
				}
			}
		}
		return token;
	}

	/**
	 * 获取jsapi_ticket
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public static String getJsapi_ticket() throws Exception {
		String ticket;
		if (null != jsapi_ticket.get("TIME")
				&& !Utility.toGetData(jsapi_ticket.get("TIME"), 60)) {
			ticket = jsapi_ticket.get("TICKET");
		} else {
			String access_token = getAccessToken();
			String url = Global.GET_JSAPI_TICKET.replace("ACCESS_TOKEN",
					access_token);
			JSONObject ticketObjeck = Utility.httpRequest(url, "GET", null);
			logger.debug("获取Jsapi_ticket URL ：" + url);
			if (null != ticketObjeck) {
				logger.debug("获取ticketObjeck ：" + ticketObjeck.toString());
				if ("7200".equals(ticketObjeck.getString("expires_in"))) {
					ticket = ticketObjeck.getString("ticket");
					jsapi_ticket.put("TIME", Util.getFormatDate(null));
					jsapi_ticket.put("TICKET", ticket);
				}
			}
			ticket = jsapi_ticket.get("TICKET");
		}

		return ticket;
	}
}
