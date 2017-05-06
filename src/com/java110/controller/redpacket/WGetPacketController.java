package com.java110.controller.redpacket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.java110.bean.OutJson;
import com.java110.common.Global;
import com.java110.controller.BaseController;
import com.java110.service.redpacket.GetPacketService;
import com.java110.thread.redpacket.UpdateAccountAmountThread;
import com.java110.util.Util;
import com.java110.util.Utility;

/**
 * 抢红包服务类
 * 
 * @author wuxw
 * @date 2016-2-16 version 1.0
 */
@Controller
public class WGetPacketController extends BaseController {

	@Resource(name = "GetPacketServiceImpl")
	GetPacketService getPacketServiceImpl;

	/**
	 * 抢红包初始化页面（单个红包页面）
	 * 
	 * add by wuxw 2016-2-16
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( { "/WGetPacketController.indexPage" })
	public ModelAndView indexPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map info = getRequestData(request);
		// 1.判断用户信息是否已经获取 判断用户是否存在
		if (!this.loginAuth(request, response)) {
			return null;
		}
		// 红宝Id
		String sendRedPacketId = info.get("sendRedPacketId").toString();
		if (Util.isEmpty(sendRedPacketId)) {
			// 跳转到错误页面
			return null;
		}
		ModelAndView view = new ModelAndView("/wGetPacket.jsp", this.viewData);
		// 获取商家信息
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		Map getMerchant = servletContext.getAttribute(Global.MERCHANT
				+ sendRedPacketId) == null ? null : (Map) servletContext
				.getAttribute(Global.GET_PACKET_USER + sendRedPacketId);
		if (getMerchant == null) {
			// 个人红包
			view.addObject("userName", this.getUser().getName());
			view.addObject("userOrMerchant", Global.U);
		} else {
			// 商家红包
			view.addObject("userName",
					getMerchant.get("merchantName") == null ? ""
							: (getMerchant.get("merchantName").toString()
									.length() > 10 ? getMerchant.get(
									"merchantName").toString().substring(0, 10)
									: getMerchant.get("merchantName")
											.toString()));
			view.addObject("userOrMerchant", Global.M);
			view.addObject("productName", getMerchant.get("productName"));
			view.addObject("imgUrl", getMerchant.get("imgUrl"));
			view.addObject("productDesc", getMerchant.get("productDesc") == null ? ""
					: (getMerchant.get("productDesc").toString()
							.length() > 20 ? getMerchant.get(
							"productDesc").toString().substring(0, 20)
							: getMerchant.get("productDesc")
									.toString()));
		}
		Calendar c = Calendar.getInstance();
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日");
		view.addObject("sendRedPacketId", sendRedPacketId);
		view.addObject("userName", this.getUser().getName());
		view.addObject("startDate", myFmt.format(c.getTime()));

		// 为微信分享做准备
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String noncestr = UUID.randomUUID().toString();
		// String signurl = Global.DOMAIN+"/WGetPacketController.indexPage";
		String url = request.getScheme() + "://"; // 请求协议 http 或 https
		url += request.getHeader("host"); // 请求服务器
		url += request.getRequestURI(); // 工程名
		if (request.getQueryString() != null) { // 判断请求参数是否为空
			url += "?" + request.getQueryString(); // 参数
		}
		String signurl = url;
		logger.debug("signurl : " +signurl);
		String jsapi_ticket = this.getJsapi_ticket();
		String sourceString = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
				+ noncestr + "&timestamp=" + timestamp + "&url=" + signurl;
		logger.debug("分享初始数据：" + sourceString);

		view.addObject("APPID", Global.APPID);
		view.addObject("TIMESTAMP", timestamp);
		// 随机串
		view.addObject("NONCESTR", noncestr);
		// 获取 signature
		view.addObject("SIGNATURE", Utility.SHA1Encode(sourceString)
				.toLowerCase());

		return view;
	}

	/**
	 * 抢红宝方法
	 * 
	 * add by wuxw 2016-2-22
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( { "/WGetPacketController.getRedPacket" })
	public ModelAndView getRedPacket(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map info = getRequestData(request);
		OutJson outJson = new OutJson();
		// 2.0 校验用户是否登录
		if (!this.loginValidate(request)) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("长时间未操作，页面失效，请你刷新页面或关闭重新进入");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 红宝Id
		String sendRedPacketId = info.get("sendRedPacketId").toString();
		ModelAndView view = null;
		if (Util.isEmpty(sendRedPacketId)) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("来晚了，红包已经抢完啦");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		List<String> getPacketPerson = servletContext
				.getAttribute(Global.GET_PACKET_USER + sendRedPacketId) == null ? null
				: (List<String>) servletContext
						.getAttribute(Global.GET_PACKET_USER + sendRedPacketId);

		if (getPacketPerson != null
				&& getPacketPerson.contains(this.getUser().getUserId())) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("您已经抢过该红包了，不要贪心哦");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		} else {
			// 如果没有抢过，则把用户保存至已经抢了的列表中防止下次重复抢
			if (getPacketPerson == null) {
				getPacketPerson = new ArrayList<String>();
			}
			getPacketPerson.add(this.getUser().getUserId());
			servletContext.setAttribute(Global.GET_PACKET_USER
					+ sendRedPacketId, getPacketPerson);
		}
		// 商户红包校验是否是秒杀时间
		Map getMerchant = servletContext.getAttribute(Global.MERCHANT
				+ sendRedPacketId) == null ? null : (Map) servletContext
				.getAttribute(Global.GET_PACKET_USER + sendRedPacketId);
		
		if (getMerchant != null) {
			// 商家红包
			String spikeDate = getMerchant.get("spikeDate") == null ? "" : getMerchant.get("spikeDate").toString();
		   if(Util.compare_date_2_sysdate(spikeDate)>0){
			    outJson.setResultCode(Global.RETURN_ERROR);
				outJson.setResultInfo("还没有到秒杀时间");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
		   }
		}
		// 判断红包是否已经抢完
		// ServletContext servletContext = this.getServletContext();
		List<Map> moneyList = servletContext.getAttribute(sendRedPacketId) == null ? null
				: (List<Map>) servletContext.getAttribute(sendRedPacketId);
		// 校验红包是否为空
		if (moneyList == null || moneyList.size() == 0) {
			servletContext.removeAttribute(Global.GET_PACKET_USER
					+ sendRedPacketId);
			outJson.setResultCode(Global.RETURN_OK);
			outJson.setResultInfo("分享后查看结果");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		Map myMoneyMap = moneyList.get(0);
		moneyList.remove(myMoneyMap);
		Double money = Double.parseDouble(myMoneyMap.get(Global.RED_MONEY)
				.toString());
		// 写在内存中，让侦听自动处理保存数据
		myMoneyMap.put("userId", this.getUser().getUserId());
		myMoneyMap.put("money", money);
		myMoneyMap.put("sendRedPacketId", sendRedPacketId);
		UpdateAccountAmountThread uaat = new UpdateAccountAmountThread();
		List<Map> moneyMaps = uaat.getMoneyMaps();
		moneyMaps.add(myMoneyMap);
		uaat.setMoneyMaps(moneyMaps);
		// 保存至内存结束

		outJson.setResultCode(Global.RETURN_OK);
		outJson.setResultInfo("分享后查看结果");
		outJson.setShareDesc(Global.SHARE_DESC);
		outJson.setShareIco(Global.SHARE_ICO);
		outJson.setShareTitle(Global.SHARE_TITLE);
		outJson.setShareUrl(Global.SHARE_URL.replace("SENDREDPACKETID",
				sendRedPacketId));
		logger.debug(outJson.toString());
		outJson.setMoney(money);
		// 输出 json 对象到界面前段
		this.outJson(response, outJson);
		return null;
	}
}
