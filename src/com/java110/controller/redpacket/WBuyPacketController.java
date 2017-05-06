package com.java110.controller.redpacket;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



import com.java110.common.Global;
import com.java110.common.SpringAppFactory;
import com.java110.controller.BaseController;
import com.java110.service.redpacket.BuyPacketService;
import com.java110.service.user.UserService;
import com.java110.util.Amount;
import com.java110.util.Md5;
import com.java110.util.Util;
/**
 * 发红包服务类
 * 
 * @author wuxw
 * @date 2016-2-16
 * version 1.0
 */
@Controller
public class WBuyPacketController extends BaseController {
	// 用户服务
	@Resource(name = "UserServiceImpl")
	UserService userServiceImpl;
	
	@Resource(name="BuyPacketServiceImpl")
	BuyPacketService buyPacketServiceImpl;
	/**
	 * 包红包初始化页面
	 * 
	 * add by wuxw 2016-2-16
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/WBuyPacketController.indexPage"})
	   public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response)
	     throws Exception{
		
		 Map info = getRequestData(request);
		// 1.判断用户信息是否已经获取  判断用户是否存在
	 	  if(!this.loginAuth(request,response)){
	 		return null;
	 	  }
	    ModelAndView view = new ModelAndView("/wBuyPacket.jsp", this.viewData);
	  //查询用户账户信息
		info.put("userId", this.getUser().getUserId());
		Map accountMap = userServiceImpl.getAccountByUserId(info);
		//获取用户当前金额
		double amount = Double.parseDouble(accountMap.get("amount").toString());
		view.addObject("amount", amount);
		return view;
	}
	/**
	 * 充值请求
	 * 
	 * add by wuxw 2016-2-25
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/WBuyPacketController.Recharge"})
	public ModelAndView Recharge(HttpServletRequest request, HttpServletResponse response)
    throws Exception{
		 Map info = getRequestData(request);
		// 1.判断用户信息是否已经获取  判断用户是否存在
	 	  if(!this.loginAuth(request,response)){
	 		return null;
	 	  }
	 	 ModelAndView view = new ModelAndView("/buyPacket.jsp", this.viewData);
		 //判断入参是否为空
	 	  if(info == null || info.get("money") == null){
	 		 view.addObject(Global.ERRORMSG, "入参为空，请输入充值金额");
	 		 return view;
	 	  }
	 	  String money = info.get("money").toString();
	 	  //数值判断
	 	  if(!Util.isNumeric(money)){
	 		 view.addObject(Global.ERRORMSG, "输入金额不合法，请输入正整数金额");
	 		 return view;
	 	  }
	 	 String out_trade_no = Global.TRADE_NO +String.valueOf(System.currentTimeMillis());
	 	//插入交易日志
	 	 Map paramIn = new HashMap();
	 	 paramIn.put("payId", Util.getPayId());
	 	 paramIn.put("wIDoutTradeNo", out_trade_no);
	 	 paramIn.put("createDate", Util.getSimilerDate());
	 	 paramIn.put("days", Util.getDays());
	 	 paramIn.put("money", money);
	 	 paramIn.put("months", Util.getMonths());
	 	 paramIn.put("personId", this.getUser().getUserId());
	 	 paramIn.put("status", Global.DEAL_W);
	 	 int savePayLogFlag = buyPacketServiceImpl.saveBuyPacketPayLog(paramIn);
	 	 if(savePayLogFlag > 0 ){
	 		 logger.debug("保存支付日志成功");
	 	 }else{
	 		 logger.debug("保存支付日志失败");
	 	 }
	 	 String outParam = builtParam(out_trade_no,money,this.getUser().getName()==null?"Recharger":this.getUser().getName());
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-type","text/html;charset=UTF-8");
			out.println(outParam);
			out.flush();
			out.close();
		return null;
	}
	/**
	 * 充值成功后回调
	 * 
	 * add by wuxw 2016-2-25
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/WBuyPacketController.ReturnPayController"})
	public ModelAndView ReturnPayController(HttpServletRequest request, HttpServletResponse response)
    throws Exception{
		String out_trade_no = request.getParameter("out_trade_no");//订单交易号
		String trade_no = request.getParameter("trade_no");//淘宝交易号
		// 判断订单 redirectFlag 
		String redirectFlag = request.getParameter("redirectFlag");
		//如果订单号为空，则返回
		if(out_trade_no == null || "".equals(out_trade_no.trim())){
//			response.sendRedirect(Global.DOMAIN+"/HomeController.indexPage");
			ModelAndView view = new ModelAndView("/wBuyPacket.jsp", this.viewData);
			view.addObject(Global.ERRORMSG, Global.RETURN_ERROR);
			return view;
		}
		
		// 根据订单号去查询 ，该订单信息
		BuyPacketService buyPacketServiceImpl = (BuyPacketService) SpringAppFactory.getBean("BuyPacketServiceImpl");
		Map paramIn = new HashMap();
		paramIn.put("wIDoutTradeNo", out_trade_no);
		Map payLog = buyPacketServiceImpl.queryPayLogByOutTradeNo(paramIn);
		if(payLog == null){
			ModelAndView view = new ModelAndView("/wBuyPacket.jsp", this.viewData);
			view.addObject(Global.ERRORMSG, Global.RETURN_ERROR);
			return view;
		}
		// 如果有订单
		
		// 如果订单状态为 已完成，则直接返回，如果没有完成继续
		if("C".equals(payLog.get("status").toString())){//订单已经完成跳转到我的金币页面
			if("1".equals(redirectFlag)){
				//调用发货接口(http接口)
				String url = "http://www.java110.com/Java110Pay/services/SendGoodServlet?" +
						"WIDtrade_no="+trade_no+"&WIDlogistics_name=send%20goods&WIDinvoice_no=10069087&WIDtransport_type=POST";
				HttpClient hc = new HttpClient();
				HttpMethod method = new GetMethod(url);
				hc.executeMethod(method);
				method.releaseConnection();
			}
			logger.debug("支付宝调回成功");
			Map aMap = new HashMap();
			aMap.put("userId", payLog.get("personId"));
			Map accountMap = userServiceImpl.getAccountByUserId(aMap);
			ModelAndView view = new ModelAndView("/wBuyPacket.jsp", this.viewData);
			view.addObject("money", payLog.get("money"));
			view.addObject("amount",accountMap.get("amount"));
			view.addObject(Global.ERRORMSG, Global.RETURN_OK);
			return view;
		}
		// 更改订单状态为 完成
		Map pIn = new HashMap();
		pIn.put("status", Global.DEAL_C);
		pIn.put("dealDate", Util.getSimilerDate());
		pIn.put("WIDtradeNo",trade_no);
		pIn.put("redirectFlag", redirectFlag);
		pIn.put("payId", payLog.get("payId"));
		int updatePayFlag = buyPacketServiceImpl.updatePayLogByPayId(pIn);
		
		if(updatePayFlag > 0 ){
	 		 logger.debug("更新支付日志成功");
	 	 }else{
	 		 logger.debug("更新支付日志失败");
	 	 }
		Map aMap = new HashMap();
		aMap.put("userId", payLog.get("personId"));
		Map accountMap = userServiceImpl.getAccountByUserId(aMap);
		// 从用户账户扣除 money 并在账户交易表中插入数据
		Double surplusAmount = Amount.add(Double.parseDouble(payLog.get("money").toString()), Double.parseDouble(accountMap.get("amount").toString()));
		aMap.put("amount", surplusAmount);
		int updateAccountAmountFlag = userServiceImpl.updateAccountAmountByUserId(aMap);
		//扣除金额成功，将交易记录插入到 账户交易表中
		if(updateAccountAmountFlag >0){
			//封装保存数据
			Map paramAccountTradeLog = new HashMap();
			paramAccountTradeLog.put("accountId", accountMap.get("accountId"));
			paramAccountTradeLog.put("channelId", Global.CHANNEL_B);
			paramAccountTradeLog.put("amount", payLog.get("money"));
			paramAccountTradeLog.put("accountFlag", Global.ACCOUNT_DOWN);
			paramAccountTradeLog.put("createDate", Util.getSimilerDate());
			paramAccountTradeLog.put("months", Util.getMonths());
			paramAccountTradeLog.put("days", Util.getDays());
			int saveAccountTradeLog = userServiceImpl.saveAccountTradeLog(paramAccountTradeLog);
			
			if(saveAccountTradeLog > 0){
				logger.debug("保存账户交易成功");
			}else{
				logger.error("保存账户交易失败");
			}
		}else{
			logger.error("保存账户交易失败");
		}
		
		// 根据订单中personId，在金币表中增加相应金币
		if("2".equals(redirectFlag)){
			logger.debug("异步更新状态中……");
			//通知类，异步调用
			return null;
		}
		return null;
	}
	
	/**
	 * 构建请求参数
	 * 
	 * add by wuxw 2016-1-9
	 * @param out_trade_no
	 * @param monery
	 * @param name
	 * @return
	 */
	public String builtParam(String out_trade_no,String monery,String name){
		 StringBuffer sbHtml = new StringBuffer();
		 String param = "WIDout_trade_no="+out_trade_no+"&WIDsubject=金币购买&WIDprice="+String.valueOf(monery)
			+"&WIDbody=java110 Platform buy gold&WIDshow_url=http://www.java110.com&WIDreceive_name="+name+"&WIDreceive_address=Virtual goods, do not provide delivery address, without changing" 
			+"&WIDreceive_zip=810000&WIDreceive_phone=15897089471&receive_mobile=15897089471";
		 	Md5 md5 = new Md5();
		 	String md5Value = md5.loginMd5(param+"&key=wuxw2016928255095");
	        sbHtml.append("<html><meta charset=\"UTF-8\"><title></title><body><form id=\"payRequest\" name=\"payRequest\" action=\"http://www.java110.com/Java110Pay/services/PayServlet\"" +
	        		"_input_charset=utf-8 method=\"post\">");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDout_trade_no\" value=\"" + out_trade_no + "\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDsubject\" value=\"buy gold\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDprice\" value=\"" + String.valueOf(monery) + "\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDbody\" value=\"java110Platform buy gold\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDshow_url\" value=\"http://www.java110.com\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDreceive_name\" value=\"" + name + "\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDreceive_address\" value=\"Virtual goods, do not provide delivery address, without changing\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDreceive_zip\" value=\"810000\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDreceive_phone\" value=\"15897089471\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"WIDreceive_mobile\" value=\"15897089471\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"value\" value=\""+md5Value+"\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"channelId\" value=\"RED\"/>");
	            sbHtml.append("<input type=\"hidden\" name=\"returnPayUrl\" value=\""+Global.DOMAIN+"/WBuyPacketController.ReturnPayController\"/>");
	        //submit按钮控件请不要含有name属性
	        sbHtml.append("<input type=\"submit\" value=\"请求支付\" style=\"display:none;\"></form>");
	        sbHtml.append("<script>document.forms['payRequest'].submit();</script></body></html>");
	        return sbHtml.toString();
	}
}
