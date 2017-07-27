<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>首页</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/forget_password.css?t=<%=VersionUtil.VERSIONNO %>">
	<!--js-->
	<script src="<%=ProjectConfig.cdn_host %>/js/forget_password_step1.js"></script>
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<a href="<%=contextPath %>/h5/user/index.do" class="back"></a>
			<span>找回密码</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="call">
				<span>手机号</span>
				<input type="text" name="" placeholder="请输入手机号" id="phone">
			</div>
			<div class="call-yzm">
				<input type="text" name="" placeholder="请输入验证码" id="y-zm">
				<button class="yzm" id="hqyzm">获取验证码</button>
			</div>
			<p class="next" id="next">下一步</p>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>