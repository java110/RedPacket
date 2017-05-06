package com.java110.service.redpacket.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.java110.service.BaseService;
import com.java110.service.redpacket.SendPacketService;
/**
 * 包红包服务类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
@Service("SendPacketServiceImpl")
public class SendPacketServiceImpl extends BaseService implements SendPacketService{
	
	/**
	 * 根据Id 查询发的红包
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getSendPacketById(Map info){
		return dao.findOne("SendPacketServiceImpl.getSendPacketById", info);
	}
	/**
	 * 保存发的红包数据
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public int saveSendPacket(Map info){
		return dao.add("SendPacketServiceImpl.saveSendPacket", info);
	}
	
	/**
	 * 分页获取 发的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getSendPacketList(Map info){
		return dao.findList("SendPacketServiceImpl.getSendPacketList", info);
	}
	
	/**
	 * 分页获取 微信发的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getSendPacketListByWOpenId(Map info){
		return dao.findList("SendPacketServiceImpl.getSendPacketListByWOpenId", info);
	}
	
	/**
	 * 分页获取 支付宝发的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getSendPacketListByZOpenId(Map info){
		return dao.findList("SendPacketServiceImpl.getSendPacketListByZOpenId", info);
	}
	/**
	 * 保存商家发红包数据
	 * 
	 * add by wuxw 2016-3-3
	 * @param info
	 * @return
	 */
	public int saveMerchantSendRedPacket(Map info){
		return dao.add("SendPacketServiceImpl.saveMerchantSendRedPacket", info);
	}
	/**
	 * 根据红包Id获取商家信息
	 * 
	 * add by wuxw 2016-3-3
	 * @param info
	 * @return
	 */
	public Map getMerchantSendRedPacketBySendRedPacketId(Map info){
		return dao.findOne("SendPacketServiceImpl.getMerchantSendRedPacketBySendRedPacketId", info);
	}
	
}
