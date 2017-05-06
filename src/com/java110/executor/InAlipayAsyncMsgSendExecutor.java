/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.java110.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;


import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMobilePublicMessageCustomSendRequest;
import com.alipay.api.response.AlipayMobilePublicMessageCustomSendResponse;
import com.java110.factory.AlipayAPIClientFactory;
import com.java110.util.AlipayMsgBuildUtil;

/**
 * 菜单点击异步响应执行器
 * 
 * @author baoxing.gbx
 * @version $Id: InAlipayAsyncMsgSendExecutor.java, v 0.1 Jul 24, 2014 4:30:38 PM baoxing.gbx Exp $
 */
public class InAlipayAsyncMsgSendExecutor implements ActionExecutor {

    /** 线程池 */
    private static ExecutorService executors = Executors.newSingleThreadExecutor();

    /** 业务参数 */
    private JSONObject             bizContent;

    public InAlipayAsyncMsgSendExecutor(JSONObject bizContent) {
        this.bizContent = bizContent;
    }

    public InAlipayAsyncMsgSendExecutor() {
        super();
    }

    @Override
    public String execute() {

        //取得发起请求的支付宝账号id
        final String fromUserId = bizContent.getString("FromUserId");

        //1. 首先同步响应一个消息
        String syncResponseMsg = AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId);

        //2. 异步发送消息
        executors.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    // 2.1 构建一个业务响应消息，开发者根据自行业务构建，这里只是一个简单的样例
                    String requestMsg = AlipayMsgBuildUtil.buildSingleImgTextMsg(fromUserId);

                    AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
                    AlipayMobilePublicMessageCustomSendRequest request = new AlipayMobilePublicMessageCustomSendRequest();
                    request.setBizContent(requestMsg);

                    // 2.2 使用SDK接口类发送响应
                    AlipayMobilePublicMessageCustomSendResponse response = alipayClient
                        .execute(request);

                    // 2.3 开发者根据响应结果处理结果
                    //这里只是简单的打印，请开发者根据实际情况自行进行处理
                    if (null != response && response.isSuccess()) {
                        System.out.println("异步发送成功，结果为：" + response.getBody());
                    } else {
                        System.out.println("异步发送失败 code=" + response.getCode() + "msg："
                                           + response.getMsg());
                    }
                } catch (Exception e) {
                    System.out.println("异步发送失败");
                }
            }
        });

        return syncResponseMsg;
    }
}
