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
public interface GetPacketService {
	
	/**
	 * 查询抢到的红包（所有）
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getPacketList(Map info);
	/**
	 * 保存我抢到的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public int saveGetPacket(Map info);
	
	/**
	 * 根据发红包Id 获取 已经被抢的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getPacketListBySendPacketId(Map info);
	
	/**
	 * 查询用户是否抢过改红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public Map getPacketBySendPacketIdAndUserId(Map info);

}
