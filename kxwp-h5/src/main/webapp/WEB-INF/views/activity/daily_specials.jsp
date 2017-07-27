<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>天天特价</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=ProjectConfig.cdn_host %>/css/daily_specials.css">
</head>
<body>
	<div class="j_container">
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>天天特价</span>
		</header>
		<section class="j_content">
			<div class="message_box">
				
			</div>
		</section>
		<!--search-footer-->
		<footer class="search-footer j_total_car">
			<span class="foot">数量 :<i class="z_num j_total_numbers">0</i></span>
			<span class="foot">价格 :<i class="rmb">￥ <span class="j_total_price">0</span></i></span>
			<button class="addlist j_add_submit">加入进货单</button>
		</footer>
		<!-- 购物车 -->
		<a href="/h5/shoppingCart/gotoShoppingCart.do" class="shopcar j_shopcar_icon"></a>
	</div>
	<!-- 引入模版 -->
	<%@include file="../../../tpl/faily_specials.tpl" %>
	<script type="text/javascript" src="<%=ProjectConfig.cdn_host %>/js/daily_specials.js"></script>
</body>
</html>