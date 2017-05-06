package com.java110.controller.others;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java110.bean.OutJson;
import com.java110.common.Global;
import com.java110.controller.BaseController;
import com.java110.service.others.VedioService;
import com.java110.service.user.UserService;
import com.java110.util.Util;
import com.java110.util.Utility;

/**
 * 分享视频服务类
 * 
 * @author wuxw
 * @date 2016-2-16 version 1.0
 */
@Controller
public class ShareVedioController extends BaseController {
	// 用户服务
	@Resource(name = "UserServiceImpl")
	UserService userServiceImpl;

	@Resource(name = "VedioServiceImpl")
	VedioService vedioServiceImpl;

	/**
	 * 包红包初始化页面
	 * 
	 * add by wuxw 2016-2-16
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( { "/ShareVedioController.indexPage" })
	public ModelAndView indexPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String shareTitle = "";
		String shareIco = "";
		String shareDesc = "";
		String shareUrl = "";
		Map info = getRequestData(request);
		// 1.判断用户信息是否已经获取 判断用户是否存在
		if (!this.loginAuth(request, response)) {
			return null;
		}
		ModelAndView view = new ModelAndView("/shareVedio.jsp", this.viewData);
		// 判断入参是否为空
		String vedioId = info.get("vedioId") == null ? "25" : info.get(
				"vedioId").toString();
		String personId = info.get("personId") == null ? "" : info.get(
		"personId").toString();
		Map paramIn = new HashMap();
		paramIn.put("vedioId", vedioId);
		Map vedio = vedioServiceImpl.getVedioByVedioId(paramIn);
		if (vedio == null) {
			shareTitle = Global.shareTitle;
			shareIco = Global.shareIco;
			shareDesc = Global.shareDesc;
			shareUrl = Global.shareUrl;
		} else {
			shareTitle = vedio.get("vedioname") == null ? Global.shareTitle
					: vedio.get("vedioname").toString();
			shareIco = vedio.get("vedioImage") == null ? Global.shareIco
					: "http://img.java110.com/upload/image/"+vedio.get("vedioImage").toString();
			shareDesc = vedio.get("vediocontext") == null ? Global.shareDesc
					: vedio.get("vediocontext").toString();
			shareUrl = vedio.get("id") == null ? Global.shareUrl
					: "http://www.java110.com/javashipin/java/"
							+ vedio.get("id").toString() + ".html";
		}
		shareDesc = Utility.delHTMLTag(shareDesc);
		logger.debug("shareDesc:"+shareDesc);
		shareDesc = shareDesc.length()>=25?shareDesc.substring(0,24):shareDesc;
		logger.debug("shareDesc:"+shareDesc);
		// 为微信分享做准备
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String noncestr = UUID.randomUUID().toString();
		String url = request.getScheme() + "://"; // 请求协议 http 或 https
		url += request.getHeader("host"); // 请求服务器
		url += request.getRequestURI(); // 工程名
		if (request.getQueryString() != null) { // 判断请求参数是否为空
			url += "?" + request.getQueryString(); // 参数
		}
		String signurl = url;
		logger.debug("signurl : " + signurl);
		//生产用
		String jsapi_ticket = this.getJsapi_ticket();
		//测试用
//		String jsapi_ticket = "7100000166180525";
		String sourceString = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
				+ noncestr + "&timestamp=" + timestamp + "&url=" + signurl;
		logger.debug("分享初始数据：" + sourceString);
		logger.debug("SIGNATURE：" + Utility.SHA1Encode(sourceString)
				.toLowerCase());
		view.addObject("APPID", Global.APPID);
		view.addObject("TIMESTAMP", timestamp);
		// 随机串
		view.addObject("NONCESTR", noncestr);
		// 获取 signature
		view.addObject("SIGNATURE", Utility.SHA1Encode(sourceString)
				.toLowerCase());
		view.addObject("shareTitle", shareTitle);
		view.addObject("shareIco", shareIco);
		view.addObject("shareDesc", shareDesc);
		view.addObject("shareUrl", shareUrl);
		view.addObject("personId", personId);
		view.addObject("vedioId", vedioId);
		return view;
	}

	/**
	 * 充值请求
	 * 
	 * add by wuxw 2016-2-25
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( { "/ShareVedioController.shareVedio" })
	public ModelAndView shareVedio(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map info = getRequestData(request);
		OutJson outJson = new OutJson();
		// 1.0 入参为空
		if (info == null) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("请求入参为空，请稍后再试~~~");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 2.0 校验用户是否登录
		if (!this.loginValidate(request)) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("长时间未操作，页面失效，请你刷新页面或关闭重新进入");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		String personId = info.get("personId") == null ? "" : info.get(
				"personId").toString();
		String vedioId = info.get("vedioId") == null ? "" : info.get("vedioId")
				.toString();
		if (Util.isEmpty(personId) || Util.isEmpty(vedioId)) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("扫描出错，请重新扫描后重试");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 查询视频是否已经下载过

		Map paramIn = new HashMap();
		paramIn.put("personId", personId);
		paramIn.put("vedioId", vedioId);
		paramIn.put("status", "0");
		paramIn.put("createdate", Util.getSimilerDate());
		//查看视频是否已经下载过
		Map paramOut = vedioServiceImpl.getDownloadedVedio(paramIn);
		if (paramOut == null) {
			int saveFlag = vedioServiceImpl.saveDownloadedVedio(paramIn);
			if (saveFlag > 0) {
				outJson.setResultCode(Global.RETURN_OK);
				outJson.setResultInfo("分享成功");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
			} else {
				outJson.setResultCode(Global.RETURN_ERROR);
				outJson.setResultInfo("扫描出错，请重新扫描后重试");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
			}
		} else {
			outJson.setResultCode(Global.RETURN_OK);
			outJson.setResultInfo("分享成功");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
	}
	public static void main(String[] args) {
		String sourceString = "jsapi_ticket=7100000166180525&noncestr=1986c9f6-d588-4bb8-ad44-7de5d5d87ddd&timestamp=1456932553&url=http://m.java110.com/RedPacket/ShareVedioController.indexPage?vedioId=6&personId=1";
		System.out.println(Utility.SHA1Encode(sourceString).toLowerCase());
	}
}
