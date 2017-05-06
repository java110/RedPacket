package com.java110.controller.redpacket;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java110.controller.BaseController;
import com.java110.service.redpacket.SendPacketService;
/**
 * 发红包服务类
 * 
 * @author wuxw
 * @date 2016-2-16
 * version 1.0
 */
@Controller
public class WGetPacketListController extends BaseController {
	
	@Resource(name="SendPacketServiceImpl")
	SendPacketService sendPacketServiceImpl;
	/**
	 * 抢红包初始化页面（单个红包页面）
	 * 
	 * add by wuxw 2016-2-16
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/WGetPacketListController.indexPage"})
	   public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response)
	     throws Exception{
		
		 Map info = getRequestData(request);
		// 1.判断用户信息是否已经获取 判断用户是否存在
			if (!this.loginAuth(request, response)) {
				return null;
			}
	    ModelAndView view = new ModelAndView("/wGetPacketList.jsp", this.viewData);
	    //获取 发的红包
	    info.put("page", 0);
	    info.put("rows", 100);
	    info.put("userId", this.getUser().getUserId());
	    List<Map> sendPacketMaps = sendPacketServiceImpl.getSendPacketListByWOpenId(info);
	    view.addObject("sendPacketMaps", sendPacketMaps);
		return view;
	}
}
