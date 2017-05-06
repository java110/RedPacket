package com.java110.service.redpacket;

import java.util.List;
import java.util.Map;
/**
 * 抢红包接口类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
public interface ExchangePacketService {
	/**
	 * 保存提现记录
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public int saveExchangePacket(Map info);
}
