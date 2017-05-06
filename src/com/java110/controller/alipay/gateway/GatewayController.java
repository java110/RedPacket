package com.java110.controller.alipay.gateway;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.java110.constants.AlipayServiceEnvConstants;
import com.java110.controller.BaseController;
import com.java110.dispatcher.Dispatcher;
import com.java110.executor.ActionExecutor;
import com.java110.util.LogUtil;
import com.java110.util.RequestUtil;
/**
 * 支付宝调用controller
 * 开发者网关，支付宝所有主动和开发者的交互会经过此网关进入开发者系统
 * 
 * @author wuxw
 * @date 2016-2-13
 * version 1.0
 */
@Controller
public class GatewayController extends BaseController{
	
	private static final long serialVersionUID = 1210436705188940602L;
	
	 @SuppressWarnings("unchecked")
	 @RequestMapping({"/GatewayController.indexPage"})
	   public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response)
	     throws Exception
	   {
		//支付宝响应消息  
	        String responseMsg = "";

	        //1. 解析请求参数
	        Map<String, String> params = RequestUtil.getRequestParams(request);

	        //打印本次请求日志，开发者自行决定是否需要
	        LogUtil.log("支付宝请求串", params.toString());

	        try {
	            //2. 验证签名
//	            this.verifySign(params);

	            //3. 获取业务执行器   根据请求中的 service, msgType, eventType, actionParam 确定执行器
	            ActionExecutor executor = Dispatcher.getExecutor(params);

	            //4. 执行业务逻辑
	            responseMsg = executor.execute();

//	        } catch (AlipayApiException alipayApiException) {
//	            //开发者可以根据异常自行进行处理
//	            alipayApiException.printStackTrace();

	        } catch (Exception exception) {
	            //开发者可以根据异常自行进行处理
	            exception.printStackTrace();

	        } finally {
	            //5. 响应结果加签及返回
	            try {
	                //对响应内容加签
	                responseMsg = AlipaySignature.encryptAndSign(responseMsg,
	                    AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY,
	                    AlipayServiceEnvConstants.PRIVATE_KEY, AlipayServiceEnvConstants.CHARSET,
	                    false, true);

	                //http 内容应答
	                response.reset();
	                response.setContentType("text/xml;charset=GBK");
	                PrintWriter printWriter = response.getWriter();
	                printWriter.print(responseMsg);
	                response.flushBuffer();

	                //开发者自行决定是否要记录，视自己需求
	                LogUtil.log("开发者响应串", responseMsg);

	            } catch (AlipayApiException alipayApiException) {
	                //开发者可以根据异常自行进行处理
	                alipayApiException.printStackTrace();
	            }
	        }
	     return null;
	   }
	 /**
	     * 验签
	     * 
	     * @param request
	     * @return
	     */
	    private void verifySign(Map<String, String> params) throws AlipayApiException {

	        if (!AlipaySignature.rsaCheckV2(params, AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY,
	            AlipayServiceEnvConstants.SIGN_CHARSET)) {

	            throw new AlipayApiException("verify sign fail.");
	        }
	    }
}
