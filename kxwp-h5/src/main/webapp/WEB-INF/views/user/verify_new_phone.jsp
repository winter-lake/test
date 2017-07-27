<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>验证新手机号</title>
		<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="css/verify_new_phone.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>验证新手机号</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="phone-verify">
				<p class="p-phone">
					<span class="s-phone">绑定手机号</span>
					<input type="text" placeholder="请输入新手机号">
				</p>
				<p class="p-verify">
					<span class="s-verify">验证码</span>
					<input type="text" placeholder="6位验证"/>
					<em class="new-yzm">获取验证码</em>
				</p>
			</div>
			<p class="a-sure">确定</p>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>