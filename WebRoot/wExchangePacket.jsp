<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>兑红包</title>
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<jsp:include page="html/top.jsp"></jsp:include>
		<link rel="stylesheet" href="${WEBINFO.STATIC_DOMAIN }/RedCss/sendPacket.css" />
		<script type="text/javascript" src="${WEBINFO.STATIC_DOMAIN }/RedJs/wExchangePacket.js" ></script>
	</head>
	<body>
		<div class="red_header_send">
			<h1>兑红包</h1>
			<input type="hidden" id="domain" name="domain" value="${WEBINFO.DOMAIN }"/>
			<input type="hidden" id="amount" name="amount" value="${amount }"/>
		</div>
		<div class="red_main">
			<div class="red_money">
				<span class="red_money_info">当前金额为<i>${amount }</i>元</span>
			</div>
			<div class="red_main_type" id="red_main_type">
				<div class="r_type">
					<span class="r_type_txt">账号:</span>
					<input type="number" class="r_type_input" name="alipayAccount" id="alipayAccount" placeholder="请输入支付宝账号"/>
				</div>
				<div class="r_type">
					<span class="r_type_txt">金额:</span>
					<input type="number" class="r_type_input" name="money" id="money" placeholder="请输入提现金额"/>
				</div>
				<div class="r_type margin_top_50">
					<a href="${WEBINFO.DOMAIN }/WIndexPacketController.indexPage">
						<input type="button" class="mask-action width45 fLeft" name="backIndex" id="backIndex" value="返回首页" onclick="backIndex()"/>	
					</a>
					<input type="button" class="mask-action width45 fRight" name="prepareRed" id="prepareRed" value="提现" onclick="exchangeRedPacket()"/>
				</div>
			</div>
			<div class="red_send_success" id="red_send_success">
				<img src="${WEBINFO.IMG_DOMAIN }/RedImg/ohyeah.png" alt="准备红包成功" />
				<span class="red_send_success_txt" id="red_send_success_txt">亲~~~您提现的200元金额，预计2016-12-24 13:24:26 之前到账，请注意查收</span>
				<a href="${WEBINFO.DOMAIN }/IndexPacketController.indexPage">
					<input type="button" class="mask-action" name="backIndex" id="backIndex" value="返回首页" />
				</a>
			</div>
		</div>
		<jsp:include page="html/footer.jsp"></jsp:include>
	</body>
</html>
