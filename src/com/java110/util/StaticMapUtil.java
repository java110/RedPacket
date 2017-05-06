package com.java110.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.java110.common.Global;
import com.java110.common.SpringAppFactory;
import com.java110.service.redpacket.GetPacketService;
import com.java110.service.redpacket.SendPacketService;
import com.java110.thread.redpacket.UpdateAccountAmountThread;
import com.sun.xml.internal.ws.util.UtilException;
/**
 * 初始化信息加载
 * 
 * @author wuxw
 * @date 2016-1-26
 * version 1.0
 */
public class StaticMapUtil {
	
	private static List<Map> codeMapBeans;//获取配置表（td_s_code_mapping）中数据

	public StaticMapUtil() {
	}

	public StaticMapUtil(String dbDefaultAreaCodex, String dbDefaultAreaIdx) {
	}
	public void init() throws UtilException {
		//加载配置表数据
//		GetCodeMapping();
		
//		//启动数据处理线程
		startUpdateAccountAmountThread();
		
		//加载没有抢完的红包
		loadSendRedPacket();
		
	}

	public static List<Map> getCodeMapBeans() {
		return codeMapBeans;
	}

	public static void setCodeMapBeans(List<Map> codeMapBeans) {
		StaticMapUtil.codeMapBeans = codeMapBeans;
	}
	/**
	 * 启动数据处理线程
	 * 
	 * add by wuxw 2016-2-22
	 */
	private void startUpdateAccountAmountThread(){
		UpdateAccountAmountThread uaat = new UpdateAccountAmountThread();
		Thread t = new Thread(uaat);
		t.start();
	}
	/**
	 * 加载还没有抢完的红包
	 * 
	 * add by wuxw 2016-2-23
	 */
	private void loadSendRedPacket(){
		SendPacketService sendPacketServiceImpl = (SendPacketService) SpringAppFactory.getBean("SendPacketServiceImpl");
		GetPacketService getPacketServiceImpl = (GetPacketService) SpringAppFactory.getBean("GetPacketServiceImpl");
		
		Map info = new HashMap();
		info.put("page", 1);
		info.put("rows", 1000);
		//查询所有发了1000条发了的红包
		List<Map> sendPacketMaps = sendPacketServiceImpl.getSendPacketList(info);
		for(Map sendPacketMap : sendPacketMaps){
			String sendRedPacketId = sendPacketMap.get("sendRedPacketId").toString();
			Map paramIn = new HashMap();
			paramIn.put("sendRedPacketId", sendRedPacketId);
			List<Map> getPacketMaps = getPacketServiceImpl.getPacketListBySendPacketId(paramIn);
			
			String redType = sendPacketMap.get("redType") == null ? null : sendPacketMap.get("redType").toString();
			int copies = Integer.parseInt(sendPacketMap.get("copies").toString());
			double money = Double.parseDouble(sendPacketMap.get("money").toString());
			String userOrMerchant = sendPacketMap.get("userOrMerchant") == null ? Global.U :sendPacketMap.get("userOrMerchant").toString();
			if(getPacketMaps == null || getPacketMaps.size() == 0){
				//发送红包重新随机 放入至内存中
				//判断是随机红包 还是 平均红包
				List<Map> moneyList = new ArrayList<Map>();
				if(Global.RED_TYPE_01.equals(redType)){
					moneyList = Utility.randomRedPacket(copies, money);
				}else{
					// 平均红包
					moneyList = new ArrayList<Map>();
					Map<String, Double> moneyMap = null;
					for (int i = 1; i <= copies; i++) {
						moneyMap = new HashMap<String, Double>();
						moneyMap.put(Global.RED_MONEY, money);
						moneyList.add(moneyMap);
					}
				}
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
		        ServletContext servletContext = webApplicationContext.getServletContext();  
				servletContext.setAttribute(sendRedPacketId, moneyList);
				
				//判断红包类型是商家红包还是用户红包
				if(Global.M.equals(userOrMerchant)){
					//商家红包 加载商家信息
					Map merchantMap = sendPacketServiceImpl.getMerchantSendRedPacketBySendRedPacketId(sendPacketMap);
					servletContext.setAttribute(Global.MERCHANT+sendRedPacketId, merchantMap);
				}
				continue;
			}
			double moneyed = 0.0;
			List<String> getPacketPerson = new ArrayList<String>();
			for(Map getPacketMap : getPacketMaps){
				moneyed += Double.parseDouble(getPacketMap.get("money")==null?"0":getPacketMap.get("money").toString());
				getPacketPerson.add(getPacketMap.get("userId")==null?"":getPacketMap.get("userId").toString());
			}
			int getPacketCount = getPacketMaps.size();
			
			List<Map> moneyList = new ArrayList<Map>();
			//减去 已经发出去的红包数量和金额，重新计算
			copies = copies-getPacketCount;
			money = Amount.sub(money, moneyed);
			if(Global.RED_TYPE_01.equals(redType)){
				moneyList = Utility.randomRedPacket(copies, money);
			}else{
				// 平均红包
				moneyList = new ArrayList<Map>();
				Map<String, Double> moneyMap = null;
				for (int i = 1; i <= copies; i++) {
					moneyMap = new HashMap<String, Double>();
					moneyMap.put(Global.RED_MONEY, money);
					moneyList.add(moneyMap);
				}
			}
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
	        ServletContext servletContext = webApplicationContext.getServletContext();  
			servletContext.setAttribute(sendRedPacketId, moneyList);
			//将已经抢了红包的用户导入至 内存中
			servletContext.setAttribute(Global.GET_PACKET_USER+sendRedPacketId, getPacketPerson);
			
			//判断红包类型是商家红包还是用户红包
			if(Global.M.equals(userOrMerchant)){
				//商家红包 加载商家信息
				Map merchantMap = sendPacketServiceImpl.getMerchantSendRedPacketBySendRedPacketId(sendPacketMap);
				servletContext.setAttribute(Global.MERCHANT+sendRedPacketId, merchantMap);
			}
		}
	}
}
