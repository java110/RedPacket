package com.java110.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sun.xml.internal.ws.util.UtilException;
/**
 * 静态资源加载类
 * @author wuxw
 * @date 2015-08-27
 *
 */
public class InitStaticClass implements ServletContextListener{


	public void contextInitialized(ServletContextEvent arg0){
		// TODO Auto-generated method stub
		//加载静态资源
		StaticMapUtil staticMapUtil = new StaticMapUtil();
		try {
			staticMapUtil.init();
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			new InitStaticClass();
		}
	}
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
