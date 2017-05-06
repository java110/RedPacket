<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
	    <meta charset="utf-8">
	    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	    <meta name="description" content="java110是由love编程团队提供的java相关视频分享平台，主要包括java视频，java项目，移动开发视频，大数据视频，网页设计视频和数据库视频教程，帮助每一位编程爱好者快速成长。" />
		<meta name="keywords" content="java视频教程,java项目,java110" />	
		<link rel="stylesheet" href="http://img.java110.com/RedCss/common.css" />
		<link rel="stylesheet" href="http://img.java110.com/RedCss/MaskLayer.css" />
		<link rel="stylesheet" href="http://img.java110.com/mCss/common.css" />
	    <link rel="stylesheet" href="http://img.java110.com/mCss/index.css" />
	    <script type="text/javascript" src="http://img.java110.com/RedJs/jquery.min.js" ></script>
		<script type="text/javascript" src="http://img.java110.com/RedJs/common.js" ></script>
	    <title>java视频教程-java110</title>
		<script type="text/javascript"
			src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	</head>
	<body style="background:#FFF;">
		<div class="red_main" id="red_main_1">
			<input type="hidden" id="domain" name="domain" value="${WEBINFO.DOMAIN }"/>
			<input type="hidden" id="shareTitle" name="shareTitle" value="${shareTitle }" />
			<input type="hidden" id="shareIco" name="shareIco"
				value="${shareIco }" />
			<input type="hidden" id="shareDesc" name="shareDesc" value="${shareDesc }" />
			<input type="hidden" id="shareUrl" name="shareUrl"
				value="${shareUrl }" />
				
			<input type="hidden" id="personId" name="personId" value="${personId }" />
			<input type="hidden" id="vedioId" name="vedioId"
				value="${vedioId }" />
			
			<input type="hidden" id="appId" name="appId" value="${APPID }" />
			<input type="hidden" id="timestamp" name="timestamp"
				value="${TIMESTAMP }" />
			<input type="hidden" id="nonceStr" name="nonceStr"
				value="${NONCESTR }" />
			<input type="hidden" id="signature" name="signature"
				value="${SIGNATURE }" />
		</div>
		<!--
    	作者：928255095@qq.com
    	时间：2016-01-25
    	描述：头部
    -->
	<div class="ui-header">
		<ul>
			<li><a href="http://m.java110.com/index.html">首页</a></li>
			<li><a href="http://m.java110.com/guanfangkecheng">官方课程</a></li>
			<li><a href="http://m.java110.com/xuexixiangmu">学习项目</a></li>
			<li id="loginId">
				<a href="http://m.java110.com/index.html">登录</a>
			</li>
			
		</ul>
	</div>
	
	<!--
    	作者：928255095@qq.com
    	时间：2016-01-25
    	描述：图片轮播
    -->
	<div class="slider_img">
		<img src="http://img.java110.com/RedImg/share.jpg" width="100%" height="200px"/>
	</div>
	<!--
    	作者：928255095@qq.com
    	时间：2016-01-25
    	描述：正文类
    -->
	<div class="ui-main">
		<div class="ui-type">
			<div class="ui-type-1">
				<a href="http://m.java110.com/javawebshipin">
					<img src="http://m.java110.com/img/ui-type-1.jpg"/>
					<span class="ui-current-type">JavaWeb</span>
				</a>
			</div>
			<div class="ui-type-1">
				<a href="http://m.java110.com/yidongkaifashipin">
					<img src="http://m.java110.com/img/ui-type-1.jpg"/>
					<span class="ui-current-type">移动开发</span>
				</a>
			</div>
			<div class="ui-type-1">
				<a href="http://m.java110.com/yunjisuanshipin">
					<img src="http://m.java110.com/img/ui-type-1.jpg"/>
					<span class="ui-current-type">云计算</span>
				</a>
			</div>
			<div class="ui-type-1">
				<a href="http://m.java110.com/shujukushipin">
					<img src="http://m.java110.com/img/ui-type-1.jpg"/>
					<span class="ui-current-type">数据库</span>
				</a>
			</div>
		</div>	 

		<div class="ui-java">
			<div class="ui_i_title">
				<p class="i_txt">java入门视频教程</p>
				<span class="more">更多</span>
			</div>
			<div class="ui_i_context">
				 
					    <div class="i_context_1">
					    	<a href="http://m.java110.com/javashipin/10.html" target="_blank">
								<img src="http://img.java110.com/upload/image/1450973750616.jpg" alt="尚硅谷_佟刚_SSH 整合案例视频教程"/>
							</a>
						</div>
				 
					    <div class="i_context_1">
					    	<a href="http://m.java110.com/javashipin/6.html" target="_blank">
								<img src="http://img.java110.com/upload/image/1450884152516.jpg" alt="刘老师 SpringMVC视频教程"/>
							</a>
						</div>
				 
					    <div class="i_context_1">
					    	<a href="http://m.java110.com/javashipin/8.html" target="_blank">
								<img src="http://img.java110.com/upload/image/1450971013666.jpg" alt="mybatis视频教程"/>
							</a>
						</div>
				 
					    <div class="i_context_1">
					    	<a href="http://m.java110.com/javashipin/11.html" target="_blank">
								<img src="http://img.java110.com/upload/image/1450974155346.jpg" alt="webservice视频教程"/>
							</a>
						</div>
				
			</div>
		</div>
	</div>
	<!--
    	作者：928255095@qq.com
    	时间：2016-01-25
    	描述：尾部
    -->
	<div class="ui-footer">
		<div class="foot_list">
					<p class="ng-url-list">
						<a href="http://www.lovebiancheng.net" target="_blank">西宁网站建设</a><span>|</span>
						<a href="http://www.xiningzhuangxiu.com" target="_blank">西宁微装修</a><span>|</span>
						<a href="http://www.java110.com" target="_blank">关于java110</a>
					</p>
					<p class="ng-copyright">Copyright© 2002-2016 ，love编程工作室版权所有</p>
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
		jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage','onMenuShareQQ','onMenuShareQZone']
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
						shareVedio();
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					
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
					shareVedio();
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					
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
					shareVedio();
			    },
			    cancel: function () { 
			       // 用户取消分享后执行的回调函数
			       
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
				       shareVedio();
				    },
				    cancel: function () { 
				        // 用户取消分享后执行的回调函数
				        
				    }
				});
		});

	wx.error(function(res) {

		// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		});
	//分享成功后处理
	function shareVedio(){
		//异步请求服务器，提现
	$.ajax({
	     type: 'POST',
	     url: $('#domain').val()+"/ShareVedioController.shareVedio",
	    data: {
			"personId" : $('#personId').val(),
			"vedioId": $('#vedioId').val()
		} ,
		dataType: "json",
	    success: function(data){
			if(data.resultCode == '200'){
				$('#red_main_type').hide();
				$("#red_send_success").show();
				//隐藏加载层
				$('#asynLayerId').hide();
				//展示提示分享框
				$('#tinybox_1').show();
				$('#alterInfo').html("<span class=\"red_send_success_txt\">"+data.resultInfo+"</span> ");
				$('#red_send_success_txt').html(data.resultInfo);
				$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
			}else{
				//关闭加载框
				$('#asynLayerId').hide();
				//展示提示分享框
				$('#tinybox_1').show();
				$('#alterInfo').html("<span class=\"red_send_success_txt\">"+data.resultInfo+"</span> ");
				$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
			}
		},
		error : function(e){
			//关闭加载框
			$('#asynLayerId').hide();
			//展示提示分享框
			$('#tinybox_1').show();
			$('#alterInfo').html("<span class=\"red_send_success_txt\">网络繁忙，请稍后重试</span> ");
			$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		}
	});
	}
</script>
	</body>
</html>
