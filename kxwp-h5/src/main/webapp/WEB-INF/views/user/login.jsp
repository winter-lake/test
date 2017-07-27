<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>登录</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/login.css?t=<%=VersionUtil.VERSIONNO %>">
	<script src="<%=ProjectConfig.cdn_host %>/js/login.js"></script>
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<span>用户登录</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="list">
				<span class="icon-mobile-phone" id="ph"></span>
				<input type="text" name="phone" placeholder="请输入手机号" id="phone">
			</div>
			<div class="list">
				<span class="icon-user"></span>
				<input type="password" name="password" placeholder="请输入密码" id="pwd">
			</div>
			<p class="login" id="login">登录</p>
			<p class="position"><a href="<%=contextPath %>/h5/user/gotoForgotPasswd.do" class="password">忘记密码</a><a href="<%=contextPath %>/h5/user/gotoRegister.do" class="register">立即注册</a></p>		
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>