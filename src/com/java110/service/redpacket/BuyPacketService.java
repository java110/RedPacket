package com.java110.service.redpacket;

import java.util.Map;
/**
 * 包红包接口类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
public interface BuyPacketService {
	
	/**
	 * 根据Id 查询包的红包
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getBuyPacketById(Map info);
	
	/**
	 * 保存交易日志
	 * 
	 * add by wuxw 2016-2-25
	 * @param info
	 * @return
	 */
	public int saveBuyPacketPayLog(Map info);
	
	/**
	 * 根据订单号查询订单
	 * 
	 * add by wuxw 2016-2-25
	 * @param info
	 * @return
	 */
	public Map queryPayLogByOutTradeNo(Map info);
	
	/**
	 * 根据payId 修改订单
	 * 
	 * add by wuxw 2016-2-25
	 * @param info
	 * @return
	 */
	public int updatePayLogByPayId(Map info);

}
