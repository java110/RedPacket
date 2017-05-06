package com.java110.service.redpacket.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.java110.service.BaseService;
import com.java110.service.redpacket.ExchangePacketService;
/**
 * 抢红包服务类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
@Service("ExchangePacketServiceImpl")
public class ExchangePacketServiceImpl extends BaseService implements ExchangePacketService{
	/**
	 * 保存提现记录
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public int saveExchangePacket(Map info){
		return dao.add("ExchangePacketServiceImpl.saveExchangePacket", info);
	}
}
