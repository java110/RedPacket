/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.java110.executor;

import net.sf.json.JSONObject;

import com.java110.util.AlipayMsgBuildUtil;

/**
 * 取消关注服务窗执行器
 * 
 * @author baoxing.gbx
 * @version $Id: InAlipayUnFollowExecutor.java, v 0.1 Jul 24, 2014 4:29:29 PM baoxing.gbx Exp $
 */
public class InAlipayUnFollowExecutor implements ActionExecutor {

    /** 业务参数 */
    private JSONObject bizContent;

    public InAlipayUnFollowExecutor(JSONObject bizContent) {
        this.bizContent = bizContent;
    }

    public InAlipayUnFollowExecutor() {
        super();
    }

    @Override
    public String execute() {

        //取得发起请求的支付宝账号id
        final String fromUserId = bizContent.getString("FromUserId");

        //TODO 根据支付宝请求参数，开发者可以删除之前保存的本地支付宝UID-服务窗ID的关注关系
        // 这里只是个样例程序，所以这步省略。

        return AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId);
    }
}
