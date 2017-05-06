<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>发红包</title>
		<meta name="viewport"
			content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<jsp:include page="html/top.jsp"></jsp:include>
		<link rel="stylesheet" href="RedCss/sendPacket.css" />
		<script type="text/javascript"
			src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript"
			src="${WEBINFO.STATIC_DOMAIN }/RedJs/wSendPacket.js"></script>
	</head>
	<body>
		<div class="red_header_send">
			<h1>
				发红包
			</h1>
			<input type="hidden" id="domain" name="domain"
				value="${WEBINFO.DOMAIN }" />
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
		</div>
		<div class="red_main">
			<div class="red_money">
				<span class="red_money_info">当前金额为<i>${amount }</i>元</span>
			</div>
			<div class="red_main_type" id="red_main_type">
				<div class="r_type">
					<span class="r_type_txt">份数:</span>
					<input type="number" class="r_type_input" name="copies" id="copies"
						placeholder="请输入份数" />
				</div>
				<div class="r_type">
					<span class="r_type_txt" id="moneyCount">总金额:</span>
					<input type="number" class="r_type_input" name="money" id="money"
						placeholder="请输入总金额" />
				</div>
				<div class="r_type">
					<input type="radio" class="r_type_i_redio" name="red_type"
						id="red_type_01" value="red_type_01" checked="checked"
						onclick="red_type_m('red_type_01')" />
					随机红包
					<input type="radio" class="r_type_i_redio" name="red_type"
						id="red_type_02" value="red_type_02" onclick=red_type_m( 'red_type_02');
/>
					平均红包
					<input type="hidden" id="red_type" name="red_type_hidden"
						value="red_type_01" />
				</div>
				<div class="r_type margin_top_50">
					<a href="/WIndexPacketController.indexPage"> <input
							type="button" class="mask-action width45 fLeft" name="backIndex"
							id="backIndex" value="返回首页" onclick=backIndex(); /> </a>
					<input type="button" class="mask-action width45 fRight"
						name="prepareRed" id="prepareRed" value="准备红包"
						onclick=prepareRed(); />
				</div>
			</div>
			<div class="red_send_success" id="red_send_success">
				<img src="RedImg/ohyeah.png" alt="准备红包成功" />
				<input type="button" class="mask-action" name="backIndex"
					id="backIndex1" value="点击右上角，分享给朋友或朋友圈" />
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
		jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage' ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
	wx.ready(function() {
			// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		//分享到朋友圈
			wx.onMenuShareTimeline( {
					title : $('#shareTitle').val(), // 分享标题
					link : $('#shareUrl').val(), // 分享链接
					imgUrl : $('#shareIco').val(), // 分享图标
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
					showResult("0");
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
					showResult("0");
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
					showResult("0");
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
			       showResult("0");
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
				       showResult("0");
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

