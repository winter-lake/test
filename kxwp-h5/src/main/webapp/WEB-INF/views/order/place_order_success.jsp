<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>下单成功</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/place_order_success.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<span>下单成功</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<p class="success"></p>
			<p class="btn">
				<span><a href="/h5/user/home.do">继续购物</a></span>
				<span><a href="/h5/order/gotoMyOrder.do">查看订单</a></span>
			</p>
		</section>
		<!--主体-->
		
	</div>
	<!--结束-->
</body>
</html>