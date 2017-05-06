<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>抢红包</title>
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<jsp:include page="html/top.jsp"></jsp:include>
		<link rel="stylesheet" href="${WEBINFO.STATIC_DOMAIN }/RedCss/getPacketList.css" />
		<script type="text/javascript" src="${WEBINFO.STATIC_DOMAIN }/RedJs/wGetPacket.js" ></script>
	</head>
	<body>
		<div class="red_header_get">
			<h1>抢红包</h1>
		</div>
		<div class="red_main">
			<div class="red_main_type" id="red_main_type">
				<ul>
					<c:forEach items="${sendPacketMaps}" var="map" >
						<li>
							<a href="${WEBINFO.DOMAIN }/WGetPacketController.indexPage?sendRedPacketId=${map.sendRedPacketId }">
								<div class="red_info_1">
									<div class="red_packet_info">
										<img class="red_packet_img fLeft" src="${WEBINFO.IMG_DOMAIN }/RedImg/redPacketImg.png" alt=""/>
										<div class="red_packet_t fLeft">
											<h1>${map.copies }个红包</h1>
											<span class="r_b">领取红包</span>
										</div>
									</div>
									<div class="red_packet_b">
										<span class="red_packet_b_t">${map.name }红包</span>
									</div>
								</div>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</body>
</html>

