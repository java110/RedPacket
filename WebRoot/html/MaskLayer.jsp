<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!-- 遮罩层  未登录遮罩层 -->
			 <div id ="tinybox_1" style="display:none">
			 		<div id = "mask-bg">
			  		<div class="mask-top">
			  			<span class="mask_i_title">温馨提示</span>
			  			<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeMaskLayer()">
			  				<span aria-hidden="true">×</span>
			  			</button>
			  		</div>
			  		<div class = "mask-content">
			  			<span id = "alterInfo">亲，您还没有登录哦~~~ 赶紧去登陆哦~~~ </span>
			  		</div>
			  		<div class = "mask_action">
			  			<span class = "span1" id = "alterActionMaskLayer">登录</span>
			  		</div>
			 		</div>
			 </div>
