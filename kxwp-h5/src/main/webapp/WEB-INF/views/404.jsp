<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="./common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>404</title>
	<%@include file="./common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/404.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<a href="/" class="back"></a>
			<span>404</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content" id="content">
			<div class="no_wifi">
				<img src="<%=contextPath %>/images/no_wifi.png">
				<h3 class="msg">抱歉,页面不存在</h3>
				<h4 class="refresh">回首页  <a href="/h5/user/home.do">看看</a></h4>
			</div>
		</section>
		<!--主体-->
		<!--footer-->
		<%@ include file="./common/footer.jsp" %>
		<!--footer-->
	</div>
	<!--结束-->
</body>
</html>