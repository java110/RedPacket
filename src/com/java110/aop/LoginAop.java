package com.java110.aop;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.servlet.ModelAndView;

import com.java110.controller.BaseController;

public class LoginAop {
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		Object[] httpParamObjects = pjp.getArgs();
		BaseController bc = (BaseController)pjp.getTarget();
		boolean login = bc.loginValidate((HttpServletRequest) httpParamObjects[0]);

		if (!login) {
			return new ModelAndView("redirect:/login.do");
		}
		
		Object obj = null;
		try {
			obj = pjp.proceed(pjp.getArgs());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
