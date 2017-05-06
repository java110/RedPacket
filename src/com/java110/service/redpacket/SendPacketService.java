package com.java110.service.redpacket;

import java.util.List;
import java.util.Map;
/**
 * 发红包接口类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
public interface SendPacketService {
	
	/**
	 * 根据Id 查询发的红包
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getSendPacketById(Map info);
	
	/**
	 * 保存发的红包数据
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public int saveSendPacket(Map info);
	
	/**
	 * 分页获取 发的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getSendPacketList(Map info);
	
	/**
	 * 分页获取 微信发的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getSendPacketListByWOpenId(Map info);
	
	/**
	 * 分页获取 支付宝发的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getSendPacketListByZOpenId(Map info);
	
	/**
	 * 保存商家发红包数据
	 * 
	 * add by wuxw 2016-3-3
	 * @param info
	 * @return
	 */
	public int saveMerchantSendRedPacket(Map info);
	
	/**
	 * 根据红包Id获取商家信息
	 * 
	 * add by wuxw 2016-3-3
	 * @param info
	 * @return
	 */
	public Map getMerchantSendRedPacketBySendRedPacketId(Map info);

}
