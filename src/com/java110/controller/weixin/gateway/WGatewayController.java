package com.java110.controller.weixin.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.java110.common.Global;
import com.java110.constants.AlipayServiceEnvConstants;
import com.java110.controller.BaseController;
import com.java110.dispatcher.Dispatcher;
import com.java110.executor.ActionExecutor;
import com.java110.util.LogUtil;
import com.java110.util.RequestUtil;
import com.java110.util.Utility;
/**
 * 支付宝调用controller
 * 开发者网关，支付宝所有主动和开发者的交互会经过此网关进入开发者系统
 * 
 * @author wuxw
 * @date 2016-2-13
 * version 1.0
 */
@Controller
public class WGatewayController extends BaseController{
	
	private static final long serialVersionUID = 1210436705188940602L;
	
	 @SuppressWarnings("unchecked")
	 @RequestMapping({"/WGatewayController.indexPage"})
	   public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response)
	     throws Exception
	   {
//			微信对接
			String token = Global.TOKEN;//配置文件中获取
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			String responseStr = "";
			logger.debug("token = " + token + "||||" + "signature = " + signature + "|||" + "timestamp = " 
					+ timestamp + "|||" + "nonce = " + nonce + "|||" + "echostr = " + echostr);
			String  sourceString = "";
			 String[] ss = new String[] { token, timestamp, nonce };  
		        Arrays.sort(ss);  
		        for (String s : ss) {  
		        	sourceString += s;  
		        }  
			String signature1 = Utility.SHA1Encode(sourceString).toLowerCase();
			logger.debug("sourceString = " + sourceString + "||||" + "signature1 = " + signature1);
			if(signature1.equals(signature)){
				if(echostr == null){
					String postStr = this.readStreamParameter(request.getInputStream());
					if(postStr == null || postStr.length() == 0){
						responseStr = "亲~~~输入一些东西嘛~~~";
					}else{
						if(postStr.equals(new String(postStr.getBytes("ISO-8859-1"), "ISO-8859-1")))
						{
							logger.debug(" This type is  iso-8859-1");
							postStr = new String(postStr.getBytes("ISO-8859-1"),"UTF-8");
							
						}
						if(postStr.equals(new String(postStr.getBytes("GB2312"), "GB2312")))
						{
							logger.debug(" This type is  GB2312"+postStr);
							postStr = new String(postStr.getBytes("GB2312"),"UTF-8");
							logger.debug(" change postStr to utf-8 "+postStr);
						}
						if(postStr.equals(new String(postStr.getBytes("GBK"), "GBK")))
						{
							logger.debug(" This type is  GBK");
							postStr = new String(postStr.getBytes("GBK"),"UTF-8");
							
						}
						postStr = new String(postStr.getBytes("GB2312"), "UTF-8");
						Document document;
						try {
							document = DocumentHelper.parseText(postStr);
							// 获取微信POST过来的数据
							Element root = document.getRootElement();
							String fromUserName = root.elementText("FromUserName");
							String toUserName = root.elementText("ToUserName");
							String keyword = root.elementTextTrim("Content");
							String msgType = root.elementTextTrim("MsgType");
							String event = root.elementText("Event");
							String eventKey = root.elementText("EventKey");
							if (Global.MSG_TYPE_TEXT.equals(msgType)) {
								// 返回消息
								responseStr = textResponseHandler(fromUserName, toUserName, keyword);
							}else if(Global.MSG_TYPE_EVENT.equals(msgType)){
								responseStr = eventResponseHandler(fromUserName, toUserName, keyword, event, eventKey);
							}else{
								
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							responseStr = "亲~~~您好，小E正在和外星人大干了一场，正在自我修复中~~~请你稍候重试！";
						}
					}
				}else{
					responseStr = echostr;	
				}
				logger.debug(">>>>>>>>>>>>>..responseStr>>>>>>>>>>>>>>>"+ responseStr);
				this.printCharSetUtf8(response, responseStr);
				//response.getWriter().write(responseStr);
			}else{
				
			}
		
			return null;
		}
		/**
		 * 文本信息回复
		 * @param fromUserName
		 * @param toUserName
		 * @param keyword
		 * @return
		 */
		private String textResponseHandler(String fromUserName, String toUserName,
				String keyword) {
			if(keyword == null || keyword.length() == 0){
				return Utility
				.formatText(toUserName, fromUserName,"亲~~~写一些东西嘛~~~");
			}else{
				String responseStr = keyWordHandler(fromUserName, toUserName,
						keyword);
				return Utility
				.formatText(toUserName, fromUserName,responseStr);
			}
		}
		//关键字配备
		private String keyWordHandler(String fromUserName, String toUserName,
				String keyword) {
			// TODO Auto-generated method stub
			String domain = Global.TOKEN;//配置文件中获取
			String url = domain +"/IMSS/indexPage.do";
				return "亲~~~欢迎关注 西宁最权威最专业的微信端装修平台 <a href='http://weixin.moyusheji.com/wxhome'>点击进入 </a>西宁微装修平台";
		}
		/**
		 * 事件处理
		 * @param pd
		 * @param channel
		 * @param fromUserName
		 * @param toUserName
		 * @param event
		 * @param eventKey
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings( { "unchecked" })
		public String eventResponseHandler(String fromUserName, String toUserName, String keyWords, String event,
				String eventKey) throws Exception {
			String resultStr = "";
			// 订阅
			if (event.equals("subscribe")) {
//				//录入用户关注信息
//				GZService gzService = new GZServiceImpl();
//				Map info = new HashMap();
//				info.put("open_id", fromUserName);
//				info.put("status", Global.GZ);
//				info.put("delflag", Global.NODEL);
//				info.put("create_time", Util.getSimilerDatePath());
//				Boolean addFlag =  gzService.addGZUser(info);
//				if(!addFlag){
//					log.error("用户关注信息失败");
//				}
//				关注表中增加用户信息
				resultStr = "亲~~~您好，欢迎关注 <a href='http://weixin.moyusheji.com/wxhome'>西宁微装修平台</a>，" +
						"为了给广大装修的朋友带来帮助，加入我们请联系手机：15897089471 QQ：928255095，你也可以用电脑访问我们的官方" +
						"网址http://www.xiningzhuangxiu.com";
			} else if (event.equals("unsubscribe")) {
				//删除关注表中的用户（更改状态）
//				GZService gzService = new GZServiceImpl();
//				Map info = new HashMap();
//				info.put("open_id", fromUserName);
//				info.put("status", Global.QXGZ);
//				Boolean addFlag =  gzService.updateGZUser(info);
//				if(!addFlag){
//					logger.error("用户取消关注信息更新失败");
//				}
			} else if (event.equalsIgnoreCase("CLICK")) {
				resultStr = textResponseHandler(fromUserName, toUserName,
						eventKey);
			}else{
				
			}
			return Utility.formatText(toUserName, fromUserName,resultStr);
		}
		public String readStreamParameter(ServletInputStream in) {
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != reader) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return buffer.toString();
		}
		
		 public static void printCharSetUtf8(HttpServletResponse response, String text) {
			    try {
			      print(response, "text/html;charset=UTF-8", text);
			    } catch (Exception ex) {
			    }
			  }
		
		  private static void print(HttpServletResponse response, String contentType, String text)
				    throws IOException
		  {
			  System.out.println("输出回复信息>>>>>>>>>>>>>>>>>>>>>>>>>>"+text);
		    response.setContentType(contentType);
		    response.setHeader("Pragma", "No-cache");
		    response.setHeader("Cache-Control", "no-cache");
		    response.setDateHeader("Expires", 0L);
		    PrintWriter pw = response.getWriter();
		    pw.write(text);
		    pw.close();
		  }
}
