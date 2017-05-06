package com.java110.controller.redpacket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java110.bean.OutJson;
import com.java110.common.Global;
import com.java110.controller.BaseController;
import com.java110.service.redpacket.ExchangePacketService;
import com.java110.service.user.UserService;
import com.java110.util.Amount;
import com.java110.util.Util;
/**
 * 兑红包服务类
 * 
 * @author wuxw
 * @date 2016-2-16
 * version 1.0
 */
@Controller
public class ExchangePacketController extends BaseController {
	
	// 用户服务
	@Resource(name = "UserServiceImpl")
	UserService userServiceImpl;
	// 提现服务
	@Resource(name = "ExchangePacketServiceImpl")
	ExchangePacketService exchangePacketServiceImpl;
	/**
	 * 兑红包初始化页面
	 * 
	 * add by wuxw 2016-2-16
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/ExchangePacketController.indexPage"})
	   public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response)
	     throws Exception{
		
		 Map info = getRequestData(request);
		// 1.判断用户信息是否已经获取  判断用户是否存在
	 	  if(!this.loginAuthFlag(request,response)){
	 		return null;
	 	  }
	    ModelAndView view = new ModelAndView("/exchangePacket.jsp", this.viewData);
	  //查询用户账户信息
		info.put("userId", this.getUser().getUserId());
		Map accountMap = userServiceImpl.getAccountByUserId(info);
		//获取用户当前金额
		double amount = Double.parseDouble(accountMap.get("amount").toString());
		view.addObject("amount", amount);
		return view;
	}
	
	/**
	 * 兑红包
	 * 
	 * add by wuxw 2016-2-16
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/ExchangePacketController.exchangeMoney"})
	public ModelAndView exchangeMoney(HttpServletRequest request, HttpServletResponse response)
    throws Exception{
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
		if(!this.loginValidate(request)){
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("长时间未操作，页面失效，请你刷新页面或关闭重新进入");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		String alipayAccount = info.get("alipayAccount") == null ? null : info.get("alipayAccount").toString();
		String money = info.get("money") == null ? null : info.get("money").toString();
		//入参空值判断
		if(Util.isEmpty(alipayAccount) || Util.isEmpty(money)){
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("支付宝账号和提现金额不能为空");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		//入参是否为数值
		if(!Util.isNumeric(alipayAccount) || !Util.isNumeric(money)){
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("支付宝账号和提现金额必须为数字");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		//校验账户金额
		info.put("userId", this.getUser().getUserId());
		Map accountMap = userServiceImpl.getAccountByUserId(info);
		//获取用户当前金额
		double amount = Double.parseDouble(accountMap.get("amount").toString());
		Double m = Double.parseDouble(money);
		if(m>amount){
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("请输入金额太大，账户金额不足");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		
		//每天只能提现一次功能
		
		//扣减账户金额
		// 从用户账户扣除 money 并在账户交易表中插入数据
		info.put("amount", Amount.sub(amount, m));
		int updateAccountAmountFlag = userServiceImpl.updateAccountAmountByUserId(info);
		//扣除金额成功，将交易记录插入到 账户交易表中
		if(updateAccountAmountFlag >0){
			//封装保存数据
			Map paramAccountTradeLog = new HashMap();
			paramAccountTradeLog.put("accountId", accountMap.get("accountId"));
			paramAccountTradeLog.put("channelId", Global.CHANNEL_D);
			paramAccountTradeLog.put("amount",m);
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
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("系统繁忙，请稍后重试");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		
		//提现 保存
		Map paramIn = new HashMap();
		paramIn.put("exchangeRedPacketId", Util.getExchangeRedPacketId());
		paramIn.put("userId", this.getUser().getUserId());
		paramIn.put("alipayAccount", alipayAccount);
		paramIn.put("money", money);
		paramIn.put("rate", Global.RATE);
		paramIn.put("status", Global.DEAL_W);
		paramIn.put("createDate", Util.getSimilerDate());
		paramIn.put("months", Util.getMonths());
		paramIn.put("days", Util.getDays());
		int exchangePacketFlag = exchangePacketServiceImpl.saveExchangePacket(paramIn);
		if(exchangePacketFlag > 0){
			logger.debug("提现交易成功");
		}else{
			logger.error("提现交易失败");
		}
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 2);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
		String time = format.format(c.getTime());
		outJson.setResultCode(Global.RETURN_OK);
		outJson.setResultInfo("您提现的"+money+"元金额，预计"+time+"之前到账，请注意查收");
		// 输出 json 对象到界面前段
		this.outJson(response, outJson);
		return null;
	}
}
