package com.java110.service.redpacket.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.java110.service.BaseService;
import com.java110.service.redpacket.GetPacketService;
/**
 * 抢红包服务类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
@Service("GetPacketServiceImpl")
public class GetPacketServiceImpl extends BaseService implements GetPacketService{
	
	/**
	 * 查询抢到的红包（所有）
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getPacketList(Map info){
		return dao.findList("GetPacketServiceImpl.getPacketList", info);
	}
	/**
	 * 保存我抢到的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public int saveGetPacket(Map info){
		return dao.add("GetPacketServiceImpl.saveGetPacket", info);
	}
	/**
	 * 根据发红包Id 获取 已经被抢的红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public List<Map> getPacketListBySendPacketId(Map info){
		return dao.findList("GetPacketServiceImpl.getPacketListBySendPacketId", info);
	}
	/**
	 * 查询用户是否抢过改红包
	 * 
	 * add by wuxw 2016-2-23
	 * @param info
	 * @return
	 */
	public Map getPacketBySendPacketIdAndUserId(Map info){
		return dao.findOne("GetPacketServiceImpl.getPacketBySendPacketIdAndUserId", info);
	}
}
