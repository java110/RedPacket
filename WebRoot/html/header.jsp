<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--
    	作者：928255095@qq.com
    	时间：2016-01-25
    	描述：头部
    -->
	<div class="ui-header">
		<ul>
			<li><a href="/index.html">首页</a></li>
			<li><a href="/guanfangkecheng">官方课程</a></li>
			<li><a href="/xuexixiangmu">学习项目</a></li>
			<li id="loginId">
			<c:if test="${USERINFO.personId == null}">
				<a href="javascript:void(0)" onclick="showLoginLayer()">登录</a>
			</c:if>
			<c:if test="${USERINFO.personId != null}">
				<a href="/wodejinbi.html">个人中心</a>
			</c:if>
			</li>
			
		</ul>
	</div>
