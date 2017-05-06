package com.java110.controller.redpacket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java110.controller.BaseController;
/**
 * 红包初始化界面
 * 
 * @author wuxw
 * @date 2016-2-16
 * version 1.0
 */
@Controller
public class IndexPacketController extends BaseController {
	
	/**
	 * 进入红包初始化页面
	 * 
	 * add by wuxw 2016-2-16
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/IndexPacketController.indexPage"})
	   public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response)
	     throws Exception{
		
		 Map info = getRequestData(request);
		// 1.判断用户信息是否已经获取  判断用户是否存在
	 	  if(!this.loginAuthFlag(request,response)){
	 		return null;
	 	  }
		//查询商家产品
	    ModelAndView view = new ModelAndView("/indexPacket.jsp", this.viewData);
	    
		return view;
	}
}
