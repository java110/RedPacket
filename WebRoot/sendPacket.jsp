<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>发红包</title>
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<jsp:include page="html/top.jsp"></jsp:include>
		<link rel="stylesheet" href="RedCss/sendPacket.css" />
		<script src="https://a.alipayobjects.com/u/ecmng/js/201410/3fzyLyZl9J.js"></script>
		<script src="https://static.alipay.com/aliBridge/1.0.0/aliBridge.min.js"></script>
		<script src="https://a.alipayobjects.com/amui/zepto/1.1.3/zepto.js"></script>
		<script type="text/javascript" src="${WEBINFO.STATIC_DOMAIN }/RedJs/sendPacket.js" ></script>
	</head>
	<body>
		<div class="red_header_send">
			<h1>发红包</h1>
			<input type="hidden" id="domain" name="domain" value="${WEBINFO.DOMAIN }"/>
			<input type="hidden" id="shareTitle" name="shareTitle" value="特使"/>
			<input type="hidden" id="shareIco" name="shareIco" value="http://img.java110.com/RedImg/redPacketImg.png"/>
			<input type="hidden" id="shareDesc" name="shareDesc" value="特使"/>
			<input type="hidden" id="shareUrl" name="shareUrl" value="http://m.java110.com"/>
		</div>
		<div class="red_main">
			<div class="red_money">
				<span class="red_money_info">当前金额为<i>${amount }</i>元</span>
			</div>
			<div class="red_main_type" id="red_main_type">
				<div class="r_type">
					<span class="r_type_txt">份数:</span>
					<input type="number" class="r_type_input" name="copies" id="copies" placeholder="请输入份数"/>
				</div>
				<div class="r_type">
					<span class="r_type_txt" id="moneyCount">总金额:</span>
					<input type="number" class="r_type_input" name="money" id="money" placeholder="请输入总金额"/>
				</div>
				<div class="r_type">
					<input type="radio" class="r_type_i_redio" name="red_type" id="red_type_01" value="red_type_01" checked="checked" onclick="red_type_m('red_type_01')"/>随机红包
					<input type="radio" class="r_type_i_redio" name="red_type" id="red_type_02" value="red_type_02" onclick="red_type_m('red_type_02')"/>平均红包
					<input type="hidden" id="red_type" name="red_type_hidden" value="red_type_01"/>
				</div>
				<div class="r_type margin_top_50">
					<a href="/IndexPacketController.indexPage">
						<input type="button" class="mask-action width45 fLeft" name="backIndex" id="backIndex" value="返回首页" onclick="backIndex()"/>	
					</a>
					<input type="button" class="mask-action width45 fRight" name="prepareRed" id="prepareRed" value="准备红包" onclick="prepareRed()"/>
				</div>
			</div>
			<div class="red_send_success" id="red_send_success">
				<img src="RedImg/ohyeah.png" alt="准备红包成功" />
				<input type="button" class="mask-action" name="backIndex" id="backIndex1" value="分享给朋友" />
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
            Z('#backIndex1').click(function(e){
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

