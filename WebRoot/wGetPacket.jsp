<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<link rel="stylesheet" href="${WEBINFO.STATIC_DOMAIN }/RedCss/getPacket.css" />
		<script type="text/javascript"
			src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="${WEBINFO.STATIC_DOMAIN }/RedJs/wGetPacket.js" ></script>
	</head>
	<body>
		<div class="red_main" id="red_main_1">
			<input type="hidden" id="domain" name="domain" value="${WEBINFO.DOMAIN }"/>
			<input type="hidden" id="shareFlag" name="shareFlag" value="1" />
			<input type="hidden" id="shareTitle" name="shareTitle" value="一大波红包来袭" />
			<input type="hidden" id="shareIco" name="shareIco"
				value="http://img.java110.com/RedImg/redPacketImg.png" />
			<input type="hidden" id="shareDesc" name="shareDesc" value="一大波土豪正在发红包，您还在等神马……" />
			<input type="hidden" id="shareUrl" name="shareUrl"
				value="http://m.java110.com/RedPacket/WIndexPacketController.indexPage" />
			
			<input type="hidden" id="appId" name="appId" value="${APPID }" />
			<input type="hidden" id="timestamp" name="timestamp"
				value="${TIMESTAMP }" />
			<input type="hidden" id="nonceStr" name="nonceStr"
				value="${NONCESTR }" />
			<input type="hidden" id="signature" name="signature"
				value="${SIGNATURE }" />
			
			<input type="hidden" id="sendRedPacketId" name="sendRedPacketId" value="${sendRedPacketId }">
			<img class="getPacket_01" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_01.png" alt=""/>
			<img class="getPacket_03" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_03.png" alt=""/>
			<input type="button" class="mask-action getPacketButton" name="getRed" id="getRed" value="立即抢红包>" onclick="getRed()"/>
			<span class="r_date">开抢时间:${startDate }</span>
			<span class="r_title">该红包由${userName }赞助</span>
			
			<div class="r_bottom_button margin_top_50">
				<a href="${WEBINFO.DOMAIN }/WIndexPacketController.indexPage">
					<input type="button" class="mask-action width45 fLeft" name="backIndex"  value="返回首页"/>	
				</a>
				<a href="${WEBINFO.DOMAIN }/WGetPacketListController.indexPage">
					<input type="button" class="mask-action width45 fRight" name="prepareRed"  value="更多红包"/>
				</a>
			</div>
		</div>
		
		<div class="red_main" style="display:none;" id="red_main_2">
			<img class="getPacket_01" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_01.png" alt=""/>
			<img class="getPacket_02" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_02.png" alt=""/>
			<span class="r_title">请点击右上角<img src="RedImg/wShare.png"/>分享给朋友<br />查看红包金额，5分钟内不分享红包已作废处理</span>
		</div>
		
		<div class="red_main" style="display:none;" id="red_main_3">
			<img class="getPacket_01" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_01.png" alt=""/>
			<img class="getPacket_04" src="${WEBINFO.IMG_DOMAIN }/RedImg/red_money.png" alt=""/>
			<span class="red_money" id="red_money"></span>
			
			<div class="r_bottom_button margin_top_250">
				<a href="${WEBINFO.DOMAIN }/WIndexPacketController.indexPage">
					<input type="button" class="mask-action width45 fLeft" name="backIndex"  value="返回首页"/>	
				</a>
				<a href="${WEBINFO.DOMAIN }/WGetPacketListController.indexPage">
					<input type="button" class="mask-action width45 fRight" name="prepareRed"  value="更多红包"/>
				</a>
			</div>
		</div>
		<!-- 商家广告 -->
		
		<div id="merchant_ad" class="merchant_ad" style="display:none;">
			<div class="add_info" id="add_info">
				<div class="ad_title">
					<span>${productName }</span>
				</div>
				<img class="ad_img" src="${imgUrl }" alt="${productName }" />
				<div class="ad_des">
					<span>${productDesc }</span>
				</div>
			</div>
		</div>
		<jsp:include page="html/footer.jsp"></jsp:include>
<script>
	/**********************************微信分享***************************************/
	wx.config( {
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : $('#appId').val(), // 必填，公众号的唯一标识
		timestamp : $('#timestamp').val(), // 必填，生成签名的时间戳
		nonceStr : $('#nonceStr').val(), // 必填，生成签名的随机串
		signature : $('#signature').val(),// 必填，签名，见附录1
		jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage']
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
	wx.ready(function() {
			// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		//分享到朋友圈
			wx.onMenuShareTimeline( {
					title : $('#shareTitle').val(), // 分享标题
					link : $('#shareUrl').val(), // 分享链接
					imgUrl : $('#shareIco').val(), // 分享图标
					success : function() {
						// 用户确认分享后执行的回调函数 解决微信分享bug
					var shareFlag = $('#shareFlag').val();
					if(shareFlag == 1){
						showResult("0");
					}else{
	                    $('#red_main_2').hide();
                      	//判断是商家发的红包还是普通用户发 的红包
                      	var userOrMerchant = $('#userOrMerchant').val();
                      	if(userOrMerchant == 'M'){
                      		//商家红包展示商家5秒钟广告
                      		$('#merchant_ad').show();
                      		//休息5秒
                      		time();
                      		$('#merchant_ad').hide();
                      		$('#red_main_3').show();
                      	}else{
                      		$('#red_main_3').show();
                      	}
					}
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					showResult("1");
				}
				});	
	//分享给好友
				wx.onMenuShareAppMessage( {
					desc : $('#shareDesc').val(), // 分享描述
					title : $('#shareTitle').val(), // 分享标题
					link : $('#shareUrl').val(), // 分享链接
					imgUrl : $('#shareIco').val(), // 分享图标
					type : 'link', // 分享类型,music、video或link，不填默认为link
					dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
					success : function() {
						// 用户确认分享后执行的回调函数
					var shareFlag = $('#shareFlag').val();
					if(shareFlag == 1){
						showResult("0");
					}else{
						$('#red_main_2').hide();
                      	//判断是商家发的红包还是普通用户发 的红包
                      	var userOrMerchant = $('#userOrMerchant').val();
                      	if(userOrMerchant == 'M'){
                      		//商家红包展示商家5秒钟广告
                      		$('#merchant_ad').show();
                      		//休息5秒
                      		time();
                      		$('#merchant_ad').hide();
                      		$('#red_main_3').show();
                      	}else{
                      		$('#red_main_3').show();
                      	}
					}
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					showResult("1");
				}
				});
		});

	wx.error(function(res) {

		// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		});
//初始话分享内容
 function shareFriendsLine(){
	 //分享到朋友圈
			wx.onMenuShareTimeline( {
					title : $('#shareTitle').val(), // 分享标题
					link : $('#shareUrl').val(), // 分享链接
					imgUrl : $('#shareIco').val(), // 分享图标
					success : function() {
						// 用户确认分享后执行的回调函数
					$('#red_main_2').hide();
                      	//判断是商家发的红包还是普通用户发 的红包
                      	var userOrMerchant = $('#userOrMerchant').val();
                      	if(userOrMerchant == 'M'){
                      		//商家红包展示商家5秒钟广告
                      		$('#merchant_ad').show();
                      		//休息5秒
                      		time();
                      		$('#merchant_ad').hide();
                      		$('#red_main_3').show();
                      	}else{
                      		$('#red_main_3').show();
                      	}
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					showResult("1");
				}
				});	
	//分享给好友
				wx.onMenuShareAppMessage( {
					desc : $('#shareDesc').val(), // 分享描述
					title : $('#shareTitle').val(), // 分享标题
					link : $('#shareUrl').val(), // 分享链接
					imgUrl : $('#shareIco').val(), // 分享图标
					type : 'link', // 分享类型,music、video或link，不填默认为link
					dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
					success : function() {
						// 用户确认分享后执行的回调函数
					$('#red_main_2').hide();
                      	//判断是商家发的红包还是普通用户发 的红包
                      	var userOrMerchant = $('#userOrMerchant').val();
                      	if(userOrMerchant == 'M'){
                      		//商家红包展示商家5秒钟广告
                      		$('#merchant_ad').show();
                      		//休息5秒
                      		time();
                      		$('#merchant_ad').hide();
                      		$('#red_main_3').show();
                      	}else{
                      		$('#red_main_3').show();
                      	}
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					showResult("1");
				}
				});
	//分享到QQ
			wx.onMenuShareQQ({
			    desc : $('#shareDesc').val(), // 分享描述
				title : $('#shareTitle').val(), // 分享标题
				link : $('#shareUrl').val(), // 分享链接
				imgUrl : $('#shareIco').val(), // 分享图标
			    success: function () { 
			       // 用户确认分享后执行的回调函数
					$('#red_main_2').hide();
                      	//判断是商家发的红包还是普通用户发 的红包
                      	var userOrMerchant = $('#userOrMerchant').val();
                      	if(userOrMerchant == 'M'){
                      		//商家红包展示商家5秒钟广告
                      		$('#merchant_ad').show();
                      		//休息5秒
                      		time();
                      		$('#merchant_ad').hide();
                      		$('#red_main_3').show();
                      	}else{
                      		$('#red_main_3').show();
                      	}
			    },
			    cancel: function () { 
			       // 用户取消分享后执行的回调函数
			       showResult("1");
			    }
			});
		//分享到腾讯微博
			wx.onMenuShareWeibo({
			    desc : $('#shareDesc').val(), // 分享描述
				title : $('#shareTitle').val(), // 分享标题
				link : $('#shareUrl').val(), // 分享链接
				imgUrl : $('#shareIco').val(), // 分享图标
			    success: function () { 
			       // 用户确认分享后执行的回调函数
			      $('#red_main_2').hide();
                      	//判断是商家发的红包还是普通用户发 的红包
                      	var userOrMerchant = $('#userOrMerchant').val();
                      	if(userOrMerchant == 'M'){
                      		//商家红包展示商家5秒钟广告
                      		$('#merchant_ad').show();
                      		//休息5秒
                      		time();
                      		$('#merchant_ad').hide();
                      		$('#red_main_3').show();
                      	}else{
                      		$('#red_main_3').show();
                      	}
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			        showResult("1");
			    }
			});
			//分享到QQ空间
			wx.onMenuShareQZone({
				    desc : $('#shareDesc').val(), // 分享描述
					title : $('#shareTitle').val(), // 分享标题
					link : $('#shareUrl').val(), // 分享链接
					imgUrl : $('#shareIco').val(), // 分享图标
				    success: function () { 
				       // 用户确认分享后执行的回调函数
				      $('#red_main_2').hide();
                      	//判断是商家发的红包还是普通用户发 的红包
                      	var userOrMerchant = $('#userOrMerchant').val();
                      	if(userOrMerchant == 'M'){
                      		//商家红包展示商家5秒钟广告
                      		$('#merchant_ad').show();
                      		//休息5秒
                      		time();
                      		$('#merchant_ad').hide();
                      		$('#red_main_3').show();
                      	}else{
                      		$('#red_main_3').show();
                      	}
				    },
				    cancel: function () { 
				        // 用户取消分享后执行的回调函数
				        showResult("1");
				    }
				});
}
</script>
	</body>
</html>
