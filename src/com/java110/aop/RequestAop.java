package com.java110.aop;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.java110.common.Global;
import com.java110.controller.BaseController;

public class RequestAop {
	private Logger logger = Logger.getLogger(RequestAop.class);
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		Object[] httpParamObjects = pjp.getArgs();
		
		if(httpParamObjects.length != 0){
			if(httpParamObjects[0] instanceof HttpServletRequest){
				BaseController bc = (BaseController)pjp.getTarget();
				HttpServletRequest request = (HttpServletRequest) httpParamObjects[0];
				logger.debug("init context session.");
				bc.setSession(request.getSession());
				//获取站点信息
				bc.getViewData().put(Global.WEBINFO, bc.getWebInfo());
//				//放入用户信息
//				bc.getViewData().put(Global.USERINFO, bc.getUserInfo(request));
				//判断是不是来自支付宝钱包
				
			}
		}
		Object obj = null;
		try {
			obj = pjp.proceed(pjp.getArgs());
		} catch (Exception e) {
			//new UtilException(1999,e);
			e.printStackTrace();
			logger.error("异常", e);
		}
		return obj;
	}
}
