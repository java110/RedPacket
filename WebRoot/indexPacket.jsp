<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>西宁微装修红包平台</title>
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<jsp:include page="/html/top.jsp"></jsp:include>
		<link rel="stylesheet" href="${WEBINFO.STATIC_DOMAIN }/RedCss/indexPacket.css" />
		<script type="text/javascript" src="${WEBINFO.STATIC_DOMAIN }/RedJs/indexPacket.js" ></script>
	</head>
	<body>
		<div class="red_header">
			<img src="${WEBINFO.IMG_DOMAIN }/RedImg/indexPackage_01.gif" alt=""/>
		</div>
		<div class="red_main">
			<div class="red_main_pic">
				<a href="${WEBINFO.DOMAIN }/SendPacketController.indexPage">
					<img src="${WEBINFO.IMG_DOMAIN }/RedImg/indexPackage_02.gif" alt="发红包" />
				</a>
				<a href="${WEBINFO.DOMAIN }/GetPacketListController.indexPage">
					<img class="fRight" src="${WEBINFO.IMG_DOMAIN }/RedImg/indexPackage_03.gif" alt="抢红包" />
				</a>
				<a href="${WEBINFO.DOMAIN }/BuyPacketController.indexPage">
					<img src="${WEBINFO.IMG_DOMAIN }/RedImg/indexPackage_04.gif" alt="包红包" />
				</a>
				<a href="${WEBINFO.DOMAIN }/ExchangePacketController.indexPage">
					<img class="fRight" src="${WEBINFO.IMG_DOMAIN }/RedImg/indexPackage_05.jpg" alt="兑红包" />
				</a>
			</div>
		</div>
		<div class="red_footer">
			<ul>
				<li class="fLeft"><a class="mask-action" href="">红包规则</a></li>
				<li class="fRight"><a class="mask-action" href="">关注官方微信</a></li>
			</ul>
		</div>
		
	</body>
</html>

