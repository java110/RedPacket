package com.java110.controller.redpacket;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.java110.service.common.CommonService;
import com.java110.service.user.UserService;
import com.java110.thread.redpacket.SaveSendPacketThread;
import com.java110.util.Amount;
import com.java110.util.Util;
import com.java110.util.Utility;

/**
 * 发红包服务类
 * 
 * @author wuxw
 * @date 2016-2-16 version 1.0
 */
@Controller
public class SendPacketController extends BaseController {
	// 公共服务
	@Resource(name = "CommonServiceImpl")
	CommonService commonServiceImpl;

	// 用户服务
	@Resource(name = "UserServiceImpl")
	UserService userServiceImpl;

	/**
	 * 进入发红包初始化页面
	 * 
	 * add by wuxw 2016-2-16
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( { "/SendPacketController.indexPage" })
	public ModelAndView indexPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map info = getRequestData(request);
		// 1.判断用户信息是否已经获取 判断用户是否存在
		if (!this.loginAuthFlag(request, response)) {
			return null;
		}

		ModelAndView view = new ModelAndView("/sendPacket.jsp", this.viewData);
		// 查询用户账户信息
		info.put("userId", this.getUser().getUserId());
		Map accountMap = userServiceImpl.getAccountByUserId(info);
		// 获取用户当前金额
		double amount = Double.parseDouble(accountMap.get("amount").toString());
		view.addObject("amount", amount);
		return view;
	}

	/**
	 * 发送红包(个人发红包)
	 * 
	 * add by wuxw 2016-2-16
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( { "/SendPacketController.sendPersonRedPacket" })
	public ModelAndView sendPersonRedPacket(HttpServletRequest request,
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
		String copies = info.get("copies") == null ? "" : info.get("copies")
				.toString();
		String money = info.get("money") == null ? "" : info.get("money")
				.toString();
		String red_type = info.get("red_type") == null ? "" : info.get(
				"red_type").toString();
		// 入参合法性校验
		if (!Util.isNumeric(copies) || !Util.isNumeric(money)) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("请求入参有误，红包份数和金额，请输入正整数");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 校验红包类型
		if (Util.isEmpty(red_type)
				|| !(Global.RED_TYPE_01 + "," + Global.RED_TYPE_02)
						.contains(red_type)) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("请求入参有误，请选择红包类别（平均红包，随机红包）");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 最低金额校验
		Double c_money = Amount.div(Double.parseDouble(money), Double
				.parseDouble(copies));
		if (Global.RED_TYPE_01.equals(red_type) && c_money < 0.01) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("输入红包份数太大，最低金额不能低于0.01元");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 查询账户余额是否足够发红包
		// 查询用户账户信息
		info.put("userId", this.getUser().getUserId());
		Map accountMap = userServiceImpl.getAccountByUserId(info);
		// 获取账户金额
		double amount = Double.parseDouble(accountMap.get("amount").toString());
		double surplusAmount = 0.0;
		// 根据红包类型判断金额是否足够
		if (Global.RED_TYPE_01.equals(red_type)) { // 随机金额，直接和money 判断即可
			if (amount < Double.parseDouble(money)) { // 金额不足
				outJson.setResultCode(Global.RETURN_ERROR);
				outJson.setResultInfo("发的红包太大，您的账户金额不足");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
			}
			// 为后期查询账户余额，更新账户用
			surplusAmount = Amount.sub(amount, Double.parseDouble(money));
		} else { // 平均红包，需要money * copies 才能比较
			if (amount < Double.parseDouble(money) * Integer.parseInt(copies)) { // 金额不足
				outJson.setResultCode(Global.RETURN_ERROR);
				outJson.setResultInfo("发的红包太大，您的账户金额不足");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
			}
			// 为后期查询账户余额，更新账户用
			surplusAmount = Amount.sub(amount, Amount.mul(Double
					.parseDouble(money), Double.parseDouble(copies)));
		}
		List<Map> moneyList = null;
		// 随机红包
		if (Global.RED_TYPE_01.equals(red_type)) {
			moneyList = Utility.randomRedPacket(Integer.parseInt(copies),
					Double.parseDouble(money));
		} else {
			// 平均红包
			moneyList = new ArrayList<Map>();
			Map<String, Double> moneyMap = null;
			for (int i = 1; i <= Integer.parseInt(copies); i++) {
				moneyMap = new HashMap<String, Double>();
				moneyMap.put(Global.RED_MONEY, Double.parseDouble(money));
				moneyList.add(moneyMap);
			}
		}
		// 获取发红包id
		String sendRedPacketId = Util.getSendRedPacketId();

		// 从用户账户扣除 money 并在账户交易表中插入数据
		info.put("amount", surplusAmount);
		int updateAccountAmountFlag = userServiceImpl
				.updateAccountAmountByUserId(info);
		// 扣除金额成功，将交易记录插入到 账户交易表中
		if (updateAccountAmountFlag > 0) {
			// 封装保存数据
			Map paramAccountTradeLog = new HashMap();
			paramAccountTradeLog.put("accountId", accountMap.get("accountId"));
			paramAccountTradeLog.put("channelId", Global.CHANNEL_F);
			paramAccountTradeLog.put("amount", Amount
					.sub(amount, surplusAmount));
			paramAccountTradeLog.put("accountFlag", Global.ACCOUNT_DOWN);
			paramAccountTradeLog.put("createDate", Util.getSimilerDate());
			paramAccountTradeLog.put("months", Util.getMonths());
			paramAccountTradeLog.put("days", Util.getDays());
			int saveAccountTradeLog = userServiceImpl
					.saveAccountTradeLog(paramAccountTradeLog);

			if (saveAccountTradeLog > 0) {
				logger.debug("保存账户交易成功");
			} else {
				logger.error("保存账户交易失败");
			}
		} else {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("系统繁忙，请稍后重试");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}

		// 将红包保存到servletContext
		// ServletContext servletContext = this.getServletContext();
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		servletContext.setAttribute(sendRedPacketId, moneyList);

		// 将红包数据异步保存到数据
		// 封装保存数据
		Map paramIn = new HashMap();
		paramIn.put("sendRedPacketId", sendRedPacketId);
		paramIn.put("userId", this.getUser().getUserId());
		paramIn.put("redType", red_type);
		paramIn.put("copies", copies);
		paramIn.put("money", Global.RED_TYPE_01.equals(red_type) ? Double
				.parseDouble(money) : Double.parseDouble(money)
				* Integer.parseInt(copies));
		paramIn.put("status", Global.DEAL_W);
		paramIn.put("userOrMerchant", Global.U);
		paramIn.put("createDate", Util.getSimilerDate());
		paramIn.put("months", Util.getMonths());
		paramIn.put("days", Util.getDays());
		// 启用线程保存
		SaveSendPacketThread sspt = new SaveSendPacketThread(paramIn);
		Thread t = new Thread(sspt);
		t.start();

		// 将分享成功的数据返回给客户端分享出去
		outJson.setResultCode(Global.RETURN_OK);
		outJson.setResultInfo("准备红包成功");
		outJson.setShareDesc(Global.SHARE_DESC);
		outJson.setShareIco(Global.SHARE_ICO);
		outJson.setShareTitle(Global.SHARE_TITLE);
		outJson.setShareUrl(Global.SHARE_URL.replace("SENDREDPACKETID",
				sendRedPacketId));
		logger.debug(outJson.toString());
		// 输出 json 对象到界面前段
		this.outJson(response, outJson);
		return null;
	}

	/**
	 * 发送红包(商家发红包)
	 * 
	 * add by wuxw 2016-2-16
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( { "/SendPacketController.sendMerchantRedPacket" })
	public ModelAndView sendMerchantRedPacket(HttpServletRequest request,
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
		String copies = info.get("copies") == null ? "" : info.get("copies")
				.toString();
		String money = info.get("money") == null ? "" : info.get("money")
				.toString();
		String red_type = info.get("red_type") == null ? "" : info.get(
				"red_type").toString();
		String spikeDate = info.get("spikeDate") == null ? "" : info.get("spikeDate").toString();
		// 入参合法性校验
		if (!Util.isNumeric(copies) || !Util.isNumeric(money)) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("请求入参有误，红包份数和金额，请输入正整数");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 校验红包类型
		if (Util.isEmpty(red_type)
				|| !(Global.RED_TYPE_01 + "," + Global.RED_TYPE_02)
						.contains(red_type)) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("请求入参有误，请选择红包类别（平均红包，随机红包）");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 最低金额校验
		Double c_money = Amount.div(Double.parseDouble(money), Double
				.parseDouble(copies));
		if (Global.RED_TYPE_01.equals(red_type) && c_money < 0.1) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("商家红包，最低金额不能低于0.1元");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}
		// 总金额校验 总金额不能低于200元
		if (Global.RED_TYPE_01.equals(red_type)) {
			// 随机金额直接比较总金额和200元
			if (Double.parseDouble(money) < Global.MONEY_200) {
				outJson.setResultCode(Global.RETURN_ERROR);
				outJson.setResultInfo("商家红包，总金额不能低于200元");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
			}
		} else {
			if (Amount.mul(Double.parseDouble(money), Double
					.parseDouble(copies)) < Global.MONEY_200) {
				outJson.setResultCode(Global.RETURN_ERROR);
				outJson.setResultInfo("商家红包，总金额不能低于200元");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
			}
		}

		// 查询账户余额是否足够发红包
		// 查询用户账户信息
		info.put("userId", this.getUser().getUserId());
		Map accountMap = userServiceImpl.getAccountByUserId(info);
		// 获取账户金额
		double amount = Double.parseDouble(accountMap.get("amount").toString());
		double surplusAmount = 0.0;
		// 根据红包类型判断金额是否足够
		if (Global.RED_TYPE_01.equals(red_type)) { // 随机金额，直接和money 判断即可
			if (amount < Double.parseDouble(money)) { // 金额不足
				outJson.setResultCode(Global.RETURN_ERROR);
				outJson.setResultInfo("发的红包太大，您的账户金额不足");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
			}
			// 为后期查询账户余额，更新账户用
			surplusAmount = Amount.sub(amount, Double.parseDouble(money));
		} else { // 平均红包，需要money * copies 才能比较
			if (amount < Double.parseDouble(money) * Integer.parseInt(copies)) { // 金额不足
				outJson.setResultCode(Global.RETURN_ERROR);
				outJson.setResultInfo("发的红包太大，您的账户金额不足");
				// 输出 json 对象到界面前段
				this.outJson(response, outJson);
				return null;
			}
			// 为后期查询账户余额，更新账户用
			surplusAmount = Amount.sub(amount, Amount.mul(Double
					.parseDouble(money), Double.parseDouble(copies)));
		}

		// 是否开通商家服务
		Map pIn = new HashMap();
		pIn.put("userId", this.getUser().getUserId());
		Map merchant = userServiceImpl.queryMerchantByUserId(pIn);
		if (merchant == null) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("您还没有开通商户信息，请到www.xiningzhuangxiu.com 开通商户");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}

		// 查找推荐产品
		pIn.put("merchantId", merchant.get("merchantId"));
		Map product = userServiceImpl.queryProduceByMerchantId(pIn);
		if (product == null) {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("没有广告产品，请到www.xiningzhuangxiu.com 录入广告产品");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}

		List<Map> moneyList = null;
		// 随机红包
		if (Global.RED_TYPE_01.equals(red_type)) {
			moneyList = Utility.randomRedPacket(Integer.parseInt(copies),
					Double.parseDouble(money));
		} else {
			// 平均红包
			moneyList = new ArrayList<Map>();
			Map<String, Double> moneyMap = null;
			for (int i = 1; i <= Integer.parseInt(copies); i++) {
				moneyMap = new HashMap<String, Double>();
				moneyMap.put(Global.RED_MONEY, Double.parseDouble(money));
				moneyList.add(moneyMap);
			}
		}

		// 获取发红包id
		String sendRedPacketId = Util.getSendRedPacketId();
		// 从用户账户扣除 money 并在账户交易表中插入数据
		info.put("amount", surplusAmount);
		int updateAccountAmountFlag = userServiceImpl
				.updateAccountAmountByUserId(info);
		// 扣除金额成功，将交易记录插入到 账户交易表中
		if (updateAccountAmountFlag > 0) {
			// 封装保存数据
			Map paramAccountTradeLog = new HashMap();
			paramAccountTradeLog.put("accountId", accountMap.get("accountId"));
			paramAccountTradeLog.put("channelId", Global.CHANNEL_F);
			paramAccountTradeLog.put("amount", Amount
					.sub(amount, surplusAmount));
			paramAccountTradeLog.put("accountFlag", Global.ACCOUNT_DOWN);
			paramAccountTradeLog.put("createDate", Util.getSimilerDate());
			paramAccountTradeLog.put("months", Util.getMonths());
			paramAccountTradeLog.put("days", Util.getDays());
			int saveAccountTradeLog = userServiceImpl
					.saveAccountTradeLog(paramAccountTradeLog);

			if (saveAccountTradeLog > 0) {
				logger.debug("保存账户交易成功");
			} else {
				logger.error("保存账户交易失败");
			}
		} else {
			outJson.setResultCode(Global.RETURN_ERROR);
			outJson.setResultInfo("系统繁忙，请稍后重试");
			// 输出 json 对象到界面前段
			this.outJson(response, outJson);
			return null;
		}

		// 将红包保存到servletContext
		// ServletContext servletContext = this.getServletContext();
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		servletContext.setAttribute(sendRedPacketId, moneyList);
		// 将商家信息和产品信息分装到map 中
		Map merchantMap = new HashMap();
		merchantMap.put("sendRedPacketId", sendRedPacketId);
		merchantMap.put("merchantId", merchant.get("merchantId"));
		merchantMap.put("merchantName", merchant.get("name"));
		merchantMap.put("productId", product.get("productId"));
		merchantMap.put("productName", product.get("name"));
		merchantMap.put("imgUrl", product.get("imgUrl"));
		merchantMap.put("createDate", Util.getSimilerDate());
		merchantMap.put("productDesc", product.get("description"));
		merchantMap.put("status", Global.USING);
		merchantMap.put("spikeDate", Util.getSpikeDate(spikeDate)); //秒杀时间
		servletContext.setAttribute(Global.MERCHANT + sendRedPacketId,
				merchantMap);
		// 将红包数据异步保存到数据
		// 封装保存数据
		Map paramIn = new HashMap();
		paramIn.put("sendRedPacketId", sendRedPacketId);
		paramIn.put("userId", this.getUser().getUserId());
		paramIn.put("redType", red_type);
		paramIn.put("copies", copies);
		paramIn.put("money", Global.RED_TYPE_01.equals(red_type) ? Double
				.parseDouble(money) : Double.parseDouble(money)
				* Integer.parseInt(copies));
		paramIn.put("status", Global.DEAL_W);
		paramIn.put("userOrMerchant", Global.M);
		paramIn.put("createDate", Util.getSimilerDate());
		paramIn.put("months", Util.getMonths());
		paramIn.put("days", Util.getDays());
		// 启用线程保存
		SaveSendPacketThread sspt = new SaveSendPacketThread(paramIn,
				merchantMap);
		Thread t = new Thread(sspt);
		t.start();

		// 将分享成功的数据返回给客户端分享出去
		outJson.setResultCode(Global.RETURN_OK);
		outJson.setResultInfo("准备红包成功");
		outJson.setShareDesc(Global.SHARE_DESC);
		outJson.setShareIco(Global.SHARE_ICO);
		outJson.setShareTitle(Global.SHARE_TITLE);
		outJson.setShareUrl(Global.SHARE_URL.replace("SENDREDPACKETID",
				sendRedPacketId));
		logger.debug(outJson.toString());
		// 输出 json 对象到界面前段
		this.outJson(response, outJson);
		return null;
	}

}
