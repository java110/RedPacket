/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.java110.util;

import org.apache.log4j.Logger;

import com.java110.controller.BaseController;


/**
 * 日志打印工具
 * 
 * @author taixu.zqq
 * @version $Id: LogUtil.java, v 0.1 2014年7月25日 下午4:34:46 taixu.zqq Exp $
 */
public class LogUtil {
	protected static Logger logger = Logger.getLogger(BaseController.class);

    /**
     * 信息日志打印
     * 
     * @param prefixName 前缀名称
     * @param params  参数
     */
    public static void log(String prefixName, String msgContent) {
    	logger.debug(prefixName + " : " + msgContent);
    }
}
