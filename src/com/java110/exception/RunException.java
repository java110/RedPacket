package com.java110.exception;

import java.util.ArrayList;
import java.util.List;

import com.java110.bean.exception.RunExcBean;
import com.java110.util.StaticMapUtil;

/**
 * 系统运行过程中的异常处理类
 * 
 * @author wuxw
 * @date 2015-12-28
 * version 1.0
 */
public class RunException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer excCode ; //异常编码
	
	private String excMsg; //异常信息
	/**
	 * 无参构造方法
	 * 
	 * add by wuxw 2015-12-28
	 */
	public RunException(){
		
	}
	
	/**
	 * 有参构造方法
	 * 自定义抛出的异常信息类
	 * add by wuxw 2015-12-28
	 */
	public RunException(Integer excCode,String excMsg,String url){
		//入库保存
		RunExcBean rExcBean = new RunExcBean();
//		Person loginuser = null;
		rExcBean.setExcCode(excCode);
		rExcBean.setExcMsg(excMsg);
		rExcBean.setCause("");
		rExcBean.setMessage("");
		rExcBean.setExcInfo("");
		rExcBean.setUrl(url);
//		rExcBean.setPersonId(loginuser.getPersonid());
		rExcBean.setPersonId(1);
	}
	
	/**
	 * 有参构造方法
	 * 自定义抛出的异常信息类
	 * add by wuxw 2015-12-28
	 */
	public RunException(Integer excCode,String excMsg,String url,Exception e){
		
	}
	
}
