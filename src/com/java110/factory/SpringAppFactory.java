package com.java110.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring 工厂类
 * 
 * @author wuxw
 * @date 2015-12-29
 * version 1.0
 */
public class SpringAppFactory {

	//上下文对象
	public static ApplicationContext context;
	/**
	 * 返回上下文对象
	 * 
	 * add by wuxw 2015-12-29
	 * @return
	 */
	public static ApplicationContext getContext(){
		//单例设置
		if(context == null){
			context = new ClassPathXmlApplicationContext("application.xml");
		}
		return context;
	}
	
	public static Object getBean(String beanName){
		ApplicationContext context = getContext();
		return context.getBean(beanName);
	}
}
