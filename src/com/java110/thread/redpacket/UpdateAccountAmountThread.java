package com.java110.thread.redpacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java110.common.Global;
import com.java110.common.SpringAppFactory;
import com.java110.controller.BaseController;
import com.java110.service.redpacket.GetPacketService;
import com.java110.service.user.UserService;
import com.java110.util.Amount;
import com.java110.util.Util;

/**
 * 更新账户信息
 * 
 * @author wuxw
 * @date 2016-2-22
 * version 1.0
 */
public class UpdateAccountAmountThread extends BaseController implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static List<Map> moneyMaps = new ArrayList<Map>();
	
	private UserService userServiceImpl = (UserService) SpringAppFactory.getBean("UserServiceImpl");
	
	private GetPacketService getPacketServiceImpl = (GetPacketService) SpringAppFactory.getBean("GetPacketServiceImpl");
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				//获取需要处理的数据
				List<Map> mMaps = this.getMMaps();
				//判断moneyMaps 是否为空
				if(mMaps == null || mMaps.size() == 0){
					//线程休眠
					Thread.sleep(60000);
					continue;
				}
				for(Map moneyMap : mMaps){
					String userId = moneyMap.get("userId").toString();
					Double money = Double.parseDouble(moneyMap.get("money").toString());
					String sendRedPacketId = moneyMap.get("sendRedPacketId") == null ? null :moneyMap.get("sendRedPacketId").toString();
					//更新用户的账户信息
					Map paramIn = new HashMap();
					paramIn.put("userId", userId);
					Map accountMap = userServiceImpl.getAccountByUserId(paramIn);
					Double amount = Double.parseDouble(accountMap.get("amount").toString());
					amount = Amount.add(money,amount);
					paramIn.put("userId", userId);
					paramIn.put("amount", amount);
					int updateAccount = userServiceImpl.updateAccountAmountByUserId(paramIn);
					if(updateAccount > 0){
						Map paramAccountTradeLog = new HashMap();
						paramAccountTradeLog.put("accountId", accountMap.get("accountId"));
						paramAccountTradeLog.put("channelId", Global.CHANNEL_Q);
						paramAccountTradeLog.put("amount", money);
						paramAccountTradeLog.put("accountFlag", Global.ACCOUNT_UP);
						paramAccountTradeLog.put("createDate", Util.getSimilerDate());
						paramAccountTradeLog.put("months", Util.getMonths());
						paramAccountTradeLog.put("days", Util.getDays());
						int saveAccountTradeLog = userServiceImpl.saveAccountTradeLog(paramAccountTradeLog);
						if(saveAccountTradeLog > 0){
							logger.debug("保存账户交易成功");
						}else{
							logger.error("保存账户交易失败");
						}
					}
					//将抢到的红包报错到抢红包表中
					Map getPacketInfo = new HashMap();
					getPacketInfo.put("getRedPacketId", Util.getRedPacketId());
					getPacketInfo.put("sendRedPacketId", sendRedPacketId);
					getPacketInfo.put("userId", userId);
					getPacketInfo.put("money", money);
					getPacketInfo.put("createDate", Util.getSimilerDate());
					getPacketInfo.put("months", Util.getMonths());
					getPacketInfo.put("days", Util.getDays());
					getPacketInfo.put("status", Global.DEAL_W);
					int saveGetPacketFlag = getPacketServiceImpl.saveGetPacket(getPacketInfo);
					if(saveGetPacketFlag > 0){
						logger.debug("保存抢红包交易成功");
					}else{
						logger.error("保存抢红包交易失败");
					}
				}
				//线程休眠
				Thread.sleep(60000);
			}catch(Exception e){
				logger.error(e.getCause());
			}
		}
	}
	/**
	 * 获取需要处理的数据（同步）
	 * 
	 * add by wuxw 2016-2-22
	 * @return
	 */
	private synchronized List<Map> getMMaps(){
		List<Map> mMaps = new ArrayList<Map>();
		List<Map> moneyMaps = this.getMoneyMaps();
		for(Map moneyMap : moneyMaps){
			mMaps.add(moneyMap);
		}
		moneyMaps.clear();
		return mMaps;
	}
	
	public static List<Map> getMoneyMaps() {
		return moneyMaps;
	}
	public static void setMoneyMaps(List<Map> moneyMaps) {
		UpdateAccountAmountThread.moneyMaps = moneyMaps;
	}

}
