package com.java110.thread.redpacket;

import java.util.Map;

import com.java110.controller.BaseController;
import com.java110.factory.SpringAppFactory;
import com.java110.service.redpacket.SendPacketService;

/**
 * 保存发红包数据线程
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
public class SaveSendPacketThread extends BaseController implements Runnable {
	
	private Map saveData;
	
	private Map merchantSendRedPacketData;
	/**
	 * 无参构造方法
	 * 
	 * add by wuxw 2016-2-18
	 */
	public SaveSendPacketThread(){
		
	}
	
	/**
	 * 有参构造方法
	 * 
	 * add by wuxw 2016-2-18
	 */
	public SaveSendPacketThread(Map info){
		this.saveData = info;
	}
	
	/**
	 * 有参构造方法
	 * 
	 * add by wuxw 2016-2-18
	 */
	public SaveSendPacketThread(Map info,Map merchantInfo){
		this.saveData = info;
		this.merchantSendRedPacketData = merchantInfo;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//校验入参是否为空
		if(saveData == null){
			//直接返回
			logger.error("发红包时，数据保存参数为空");
			return ;
		}
		//获取保存服务接口
		SendPacketService sendPacketServiceImpl = (SendPacketService) SpringAppFactory.getBean("SendPacketServiceImpl");
		int saveFlag = sendPacketServiceImpl.saveSendPacket(saveData);
		if(saveFlag < 1){
			logger.error("发红包时，数据保存数据失败");
		}
		//校验商家发红包数据是否为空
		if(merchantSendRedPacketData == null){
			//直接返回
			logger.error("个人发红包");
			return ;
		} 
		//获取保存服务接口
		saveFlag = sendPacketServiceImpl.saveMerchantSendRedPacket(merchantSendRedPacketData);
		
		if(saveFlag < 1){
			logger.error("发红包时，保存商家信息数据失败");
		}
			
	}

	public Map getSaveData() {
		return saveData;
	}

	public void setSaveData(Map saveData) {
		this.saveData = saveData;
	}

	public Map getMerchantSendRedPacketData() {
		return merchantSendRedPacketData;
	}

	public void setMerchantSendRedPacketData(Map merchantSendRedPacketData) {
		this.merchantSendRedPacketData = merchantSendRedPacketData;
	}

}
