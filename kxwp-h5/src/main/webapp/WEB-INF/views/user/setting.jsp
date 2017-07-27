<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>设置</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/setting.css?t=<%=VersionUtil.VERSIONNO %>">
	
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>设置</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="setbox">
				<p class="setp">
					<span class="b-name">当前版本</span>
					<span class="b-num">v1.0.1</span>
				</p>
				<p class="setp">
					<span class="b-name">清除缓存</span>
					<span class="b-num">2.2M</span>
				</p>
				<a href="/h5/user/gotoSecurityCenter.do">
				<p class="setp Safety">
					<span class="b-name">安全中心</span>
					<i class="go"></i>
				</p>
				</a>
				<p class="setp own">
					<span class="b-name">关于我们</span>
					<i class="go"></i>
				</p>
			</div>
			<p class="exit">退出登录</p>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
	<!--js-->
	<script src="<%=ProjectConfig.cdn_host %>/js/setting.js"></script>
</body>
</html>