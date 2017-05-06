package com.java110.service.user;

import java.util.Map;
/**
 * 用户服务接口类
 * 
 * @author wuxw
 * @date 2016-2-19
 * version 1.0
 */
public interface UserService {

	/**
	 * 根据用户Id 获取用户
	 * 
	 * add by wuxw 2016-2-19
	 * @param info
	 * @return
	 */
	public Map getUserByUserId(Map info);
	/**
	 * 根据用户Id查询账户信息
	 * 
	 * add by wuxw 2016-2-19
	 * @param info
	 * @return
	 */
	public Map getAccountByUserId(Map info);
	
	/**
	 * 根据用户Id修改账户Amount (金额)
	 * 
	 * add by wuxw 2016-2-19
	 * @param info
	 * @return
	 */
	public int updateAccountAmountByUserId(Map info);
	
	/**
	 * 保存账户交易信息
	 * 
	 * add by wuxw 2016-2-19
	 * @param info
	 * @return
	 */
	public int saveAccountTradeLog(Map info);
	
	/**
	 * 根据支付宝openId 查询用户信息
	 * 
	 * add by wuxw 2016-2-21
	 * @param info
	 * @return
	 */
	public Map getUserByzOpenId(Map info);
	
	/**
	 * 根据微信openId 查询用户信息
	 * 
	 * add by wuxw 2016-2-21
	 * @param info
	 * @return
	 */
	public Map getUserBywOpenId(Map info);
	
	/**
	 * 保存用户信息
	 * 
	 * add by wuxw 2016-2-21
	 * @param info
	 * @return
	 */
	public int saveUser(Map info);
	
	/**
	 * 保存用户信息
	 * 
	 * add by wuxw 2016-2-21
	 * @param info
	 * @return
	 */
	public int saveAccount(Map info);
	
	/**
	 * 根据userId 获取商户
	 * 
	 * add by wuxw 2016-3-1
	 * @param info
	 * @return
	 */
	public Map queryMerchantByUserId(Map info);
	
	/**
	 * 查询商户推荐展示产品（广告）
	 * 
	 * add by wuxw 2016-3-1
	 * @param info
	 * @return
	 */
	public Map queryProduceByMerchantId(Map info);
}
