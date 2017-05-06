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
		<script src="https://a.alipayobjects.com/u/ecmng/js/201410/3fzyLyZl9J.js"></script>
		<script src="https://static.alipay.com/aliBridge/1.0.0/aliBridge.min.js"></script>
		<script src="https://a.alipayobjects.com/amui/zepto/1.1.3/zepto.js"></script>
		<script type="text/javascript" src="${WEBINFO.STATIC_DOMAIN }/RedJs/getPacket.js" ></script>
	</head>
	<body>
		<div class="red_main" id="red_main_1">&nbsp; 
			<input type="hidden" id="domain" name="domain" value="${WEBINFO.DOMAIN }"/>
			<input type="hidden" id="shareTitle" name="shareTitle" value="特使"/>
			<input type="hidden" id="shareIco" name="shareIco" value="http://img.java110.com/RedImg/redPacketImg.png"/>
			<input type="hidden" id="shareDesc" name="shareDesc" value="特使"/>
			<input type="hidden" id="shareUrl" name="shareUrl" value="http://m.java110.com"/>
			<input type="hidden" id="sendRedPacketId" name="sendRedPacketId" value="${sendRedPacketId }">
			<input type="hidden" id="userOrMerchant" name="userOrMerchant" value="${userOrMerchant }">
			<img class="getPacket_01" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_01.png" alt=""/>
			<img class="getPacket_03" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_03.png" alt=""/>
			<input type="button" class="mask-action getPacketButton" name="getRed" id="getRed" value="立即抢红包>" onclick="getRed()"/>
			<span class="r_date">开抢时间:${startDate }</span>
			<span class="r_title">该红包由${userName }赞助</span>
			
			<div class="r_bottom_button margin_top_50">
				<a href="${WEBINFO.DOMAIN }/IndexPacketController.indexPage">
					<input type="button" class="mask-action width45 fLeft" name="backIndex"  value="返回首页"/>	
				</a>
				<a href="${WEBINFO.DOMAIN }/GetPacketListController.indexPage">
					<input type="button" class="mask-action width45 fRight" name="prepareRed"  value="更多红包"/>
				</a>
			</div>
			
		</div>
		
		<div class="red_main" style="display:none;" id="red_main_2">
			<img class="getPacket_01" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_01.png" alt=""/>
			<img class="getPacket_02" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_02.png" alt=""/>
<!--			<span class="r_title">请点击右上角<img src="RedImg/wShare.png"/>分享给朋友<br />查看红包金额，5分钟内不分享红包已作废处理</span>-->
			<span class="r_title">分享给朋友，查看红包金额，5分钟内不分享红包已作废处理</span>
			<div class="r_bottom_button margin_top_50">
					<input type="button" class="mask-action width45" name="shareFriends" id="shareFriends" value="分享给朋友" />
			</div>
			
		</div>
		
		<div class="red_main" style="display:none;" id="red_main_3">
			<img class="getPacket_01" src="${WEBINFO.IMG_DOMAIN }/RedImg/getPacket_01.png" alt=""/>
			<img class="getPacket_04" src="${WEBINFO.IMG_DOMAIN }/RedImg/red_money.png" alt=""/>
			<span class="red_money" id="red_money"></span>
			
			<div class="r_bottom_button margin_top_250">
				<a href="${WEBINFO.DOMAIN }/IndexPacketController.indexPage">
					<input type="button" class="mask-action width45 fLeft" name="backIndex"  value="返回首页"/>	
				</a>
				<a href="${WEBINFO.DOMAIN }/GetPacketListController.indexPage">
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
    (function(Z){
        Z(document).ready(function(){
            if(navigator.userAgent.indexOf("AlipayClient")!==-1){
                Ali.showTitle(function(){});
                Ali.hideToolbar();
            }
            function getAbsoluteUrl(url){
                var a = document.createElement('A');
                a.href = url; // 设置相对路径给Image, 此时会发送出请求
                url = a.href; // 此时相对路径已经变成绝对路径
                return url;
            }

            Z('a#J_codeExample').click(function(e){
                var t=this;
                if(navigator.userAgent.indexOf("AlipayClient")!==-1){
                    e.preventDefault();
                    Ali.pushWindow({
                        url: getAbsoluteUrl(Z(t).attr('href')),
                        param: {
                            readTitle: "YES",
                            showToolBar: "NO"
                        }
                    })
                }
            });
            Z('#shareFriends').click(function(e){
                e.preventDefault();
                closeMaskLayer();
                if(navigator.userAgent.indexOf("AlipayClient")===-1){
                    alert('请在支付宝钱包内运行');
                }else{
                    if((Ali.alipayVersion).slice(0,3)>=8.1){
                        Ali.share({
                            //渠道名称。支持以下几种：Weibo/LaiwangContacts/LaiwangTimeline/Weixin/WeixinTimeLine/SMS/CopyLink
                            'channels': [{
								name : 'ALPContact', //支付宝联系人,9.0版本
								param : { //请注意，支付宝联系人仅支持一下参数
									contentType : 'url', //必选参数,目前支持支持"text","image","url"格式
									content : $('#shareDesc').val(), //必选参数,分享描述
									iconUrl : $('#shareIco').val(), //必选参数,缩略图url，发送前预览使用,
									imageUrl : $('#shareIco').val(), //图片url
									url : $('#shareUrl').val(), //必选参数，卡片跳转连接
									title : $('#shareTitle').val(), //必选参数,分享标题
									memo : "", //透传参数,分享成功后，在联系人界面的通知提示。
									otherParams : { //透传参数，额外的分享入参
										extendData : "testXY", //可选参数，额外的分享入参。服务器验证签名使用，由服务器@笑六 提前发给业务方
										alipayUrl : 'alipays://xxxx' // 这种方式的url打开 不会跳转到h5中间页
									}
								}
							}]
                        }, function(result) {
                            if(result.errorCode){
                                //没有成功分享的情况
                                //errorCode=10，分享失败或取消
                                switch(result.errorCode){
                                    default:
                                        alert('操作失败');
                                }
                            }else{
                            	//location.url=$('#domain').val()+"/IndexPacketController.indexPage";
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

                        });
                    }else{
                        alert('请在钱包8.1以上版本运行');
                    }
                }
            });
        });
    })(Zepto);
</script>
	</body>
</html>
