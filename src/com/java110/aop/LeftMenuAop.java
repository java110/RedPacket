package com.java110.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class LeftMenuAop {
	private Logger logger = Logger.getLogger(LeftMenuAop.class);
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		Object[] httpParamObjects = pjp.getArgs();
		/*if(httpParamObjects.length != 0){
			if(httpParamObjects[0] instanceof HttpServletRequest){
				logger.debug(">>>get left menu. ");
				BaseController bc = (BaseController)pjp.getTarget();
				HttpServletRequest request = (HttpServletRequest) httpParamObjects[0];
				String uri = request.getRequestURI();
				logger.debug(">>>get left menu. "+uri);
				uri = uri.replaceAll("/", ""); // 防止多个/出现时不能识别
				uri = "/"+uri;
				MenuService menuService = new MenuService();
				List<Map> menus = menuService.findMenuListByAppId(uri);
				if(menus.size() > 0){
					bc.getViewData().put("app_name", menus.get(0).get("app_name"));
					for (Map map : menus) {
						if(map.get("action_uri").equals(uri)){
							bc.getViewData().put("current_menu_id", map.get("menu_id"));
						}
					}
				}
				bc.getViewData().put("menus", menus);
				bc.getViewData().put("current_uri", uri);
			}
		}*/

		Object obj = null;
		try {
			obj = pjp.proceed(pjp.getArgs());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
