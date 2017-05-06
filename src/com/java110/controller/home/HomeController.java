package com.java110.controller.home;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java110.common.Global;
import com.java110.constants.AlipayServiceEnvConstants;
import com.java110.controller.BaseController;
/**
 * 系统入口类
 * 
 * @author wuxw
 * @date 2016-1-26
 * version 1.0
 */
@Controller
public class HomeController extends BaseController{
	
	 @SuppressWarnings("unchecked")
	 @RequestMapping({"/HomeController.indexPage"})
	   public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response)
	     throws Exception
	   {
	 	//查询商家产品
	 	  Map info = getRequestData(request);
	 	 ModelAndView view = null;
	 	//判断用户是否存在
	 	  if(!this.loginAuthFlag(request,response)){
	 		return null;
	 	  }
	 	 view = new ModelAndView("/indexPacket.jsp", this.viewData);
	     
	     return view;
	   }

}
