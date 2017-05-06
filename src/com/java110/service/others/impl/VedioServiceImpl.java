package com.java110.service.others.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.java110.service.BaseService;
import com.java110.service.others.VedioService;
import com.java110.service.redpacket.BuyPacketService;
/**
 * 包红包服务类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
@Service("VedioServiceImpl")
public class VedioServiceImpl extends BaseService implements VedioService{
	
	/**
	 * 根据Id查询视频
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getVedioByVedioId(Map info){
		return dao.findOne("VedioServiceImpl.getVedioByVedioId", info);
	}
	
	/**
	 * 保存下载视频记录
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public int saveDownloadedVedio(Map info){
		return dao.add("VedioServiceImpl.saveDownloadedVedio", info);
	}
	
	/**
	 * 保存下载视频记录
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getDownloadedVedio(Map info){
		return dao.findOne("VedioServiceImpl.getDownloadedVedio", info);
	}
}
