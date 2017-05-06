package com.java110.service.user.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.java110.service.BaseService;
import com.java110.service.user.UserService;
/**
 * 用户相关服务类
 * 
 * @author wuxw
 * @date 2016-2-19
 * version 1.0
 */
@Service("UserServiceImpl")
public class UserServiceImpl extends BaseService implements UserService {

	/**
	 * 根据用户Id 获取用户
	 * 
	 * add by wuxw 2016-2-19
	 * @param info
	 * @return
	 */
	public Map getUserByUserId(Map info){
		return dao.findOne("UserServiceImpl.getUserByUserId", info);
	}
	/**
	 * 根据用户Id查询账户信息
	 * 
	 * add by wuxw 2016-2-19
	 * @param info
	 * @return
	 */
	public Map getAccountByUserId(Map info){
		return dao.findOne("UserServiceImpl.getAccountByUserId", info);
	}
	/**
	 * 根据用户Id修改账户Amount (金额)
	 * 
	 * add by wuxw 2016-2-19
	 * @param info
	 * @return
	 */
	public int updateAccountAmountByUserId(Map info){
		return dao.update("UserServiceImpl.updateAccountAmountByUserId", info);
	}
	/**
	 * 保存账户交易信息
	 * 
	 * add by wuxw 2016-2-19
	 * @param info
	 * @return
	 */
	public int saveAccountTradeLog(Map info){
		return dao.add("UserServiceImpl.saveAccountTradeLog", info);
	}
	
	/**
	 * 根据支付宝openId 查询用户信息
	 * 
	 * add by wuxw 2016-2-21
	 * @param info
	 * @return
	 */
	public Map getUserByzOpenId(Map info){
		return dao.findOne("UserServiceImpl.getUserByzOpenId", info);
	}
	
	/**
	 * 根据微信openId 查询用户信息
	 * 
	 * add by wuxw 2016-2-21
	 * @param info
	 * @return
	 */
	public Map getUserBywOpenId(Map info){
		return dao.findOne("UserServiceImpl.getUserBywOpenId", info);
	}
	/**
	 * 保存用户信息
	 * 
	 * add by wuxw 2016-2-21
	 * @param info
	 * @return
	 * (non-Javadoc)
	 * @see com.java110.service.user.UserService#saveUser(java.util.Map)
	 */
	public int saveUser(Map info) {
		// TODO Auto-generated method stub
		return dao.add("UserServiceImpl.saveUser", info);
	}
	
	/**
	 * 保存账户信息
	 * 
	 * add by wuxw 2016-2-21
	 * @param info
	 * @return
	 * (non-Javadoc)
	 * @see com.java110.service.user.UserService#saveUser(java.util.Map)
	 */
	public int saveAccount(Map info) {
		// TODO Auto-generated method stub
		return dao.add("UserServiceImpl.saveAccount", info);
	}
	/**
	 * 根据userId 获取商户
	 * 
	 * add by wuxw 2016-3-1
	 * @param info
	 * @return
	 */
	public Map queryMerchantByUserId(Map info){
		return dao.findOne("UserServiceImpl.queryMerchantByUserId", info);
	}
	
	/**
	 * 查询商户推荐展示产品（广告）
	 * 
	 * add by wuxw 2016-3-1
	 * @param info
	 * @return
	 */
	public Map queryProduceByMerchantId(Map info){
		return dao.findOne("UserServiceImpl.queryProduceByMerchantId", info);
	}
}
