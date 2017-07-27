<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>下单失败</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/place_order_fail.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<span>抱歉,下单失败</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<p class="faild"></p>
			<p class="btn">
				<a href="/h5/shoPpingCart/gotoShoppingCart.do">返回购物车</a>
			</p>
		</section>
		<!--主体-->
		
	</div>
	<!--结束-->
</body>
</html>