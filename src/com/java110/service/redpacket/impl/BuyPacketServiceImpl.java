package com.java110.service.redpacket.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.java110.service.BaseService;
import com.java110.service.redpacket.BuyPacketService;
/**
 * 包红包服务类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
@Service("BuyPacketServiceImpl")
public class BuyPacketServiceImpl extends BaseService implements BuyPacketService{
	
	/**
	 * 根据Id 查询包的红包
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getBuyPacketById(Map info){
		return dao.findOne("BuyPacketServiceImpl.getBuyPacketById", info);
	}
	/**
	 * 保存交易日志
	 * 
	 * add by wuxw 2016-2-25
	 * @param info
	 * @return
	 */
	public int saveBuyPacketPayLog(Map info){
		return dao.add("BuyPacketServiceImpl.saveBuyPacketPayLog", info);
	}
	/**
	 * 根据订单号查询订单
	 * 
	 * add by wuxw 2016-2-25
	 * @param info
	 * @return
	 */
	public Map queryPayLogByOutTradeNo(Map info){
		return dao.findOne("BuyPacketServiceImpl.queryPayLogByOutTradeNo", info);
	}
	/**
	 * 根据payId 修改订单
	 * 
	 * add by wuxw 2016-2-25
	 * @param info
	 * @return
	 */
	public int updatePayLogByPayId(Map info){
		return dao.update("BuyPacketServiceImpl.updatePayLogByPayId", info);
	}
}
