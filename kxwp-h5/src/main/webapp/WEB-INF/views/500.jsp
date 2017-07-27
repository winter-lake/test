<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="./common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>500</title>
	<%@include file="./common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/500.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<a href="/" class="back"></a>
			<span>系统异常</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content" id="content">
			<div class="null">
				<img src="<%=contextPath %>/images/500.png">
				<h4 class="go_home">系统异常, <a href="/h5/user/home.do">请返回首页</a></h4>
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