<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="./common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>403</title>
	<%@include file="./common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/403.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="./common/back.jsp" %>
			<span>无权访问</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content" id="content">
			<div class="no_ql">
				<img src="<%=contextPath %>/images/no_ql.png">
				<h3 class="msg">当前页面,无权查看</h3>
				<h4 class="go_home"><a href="/h5/user/home.do">返回首页</a></h4>
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