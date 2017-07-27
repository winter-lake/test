<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>安全中心</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/security_center.css?t=<%=VersionUtil.VERSIONNO %>">
	<!--js-->
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/share_static.jsp" %>
			<span>安全中心</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="safebox">
				<a href="">
				<p class="safep">
					<span class="b-name">修改绑定手机</span>
					<i class="go"></i>
				</p>
				</a>
				<a href="">
				<p class="safep">
					<span class="b-name">修改登录密码</span>
					<i class="go"></i>
				</p>
				</a>
			</div>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>