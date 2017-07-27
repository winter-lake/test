<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>商品清单</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/order_goods_list.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container j_container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>商品清单</span>
			<label class="j_num">共  <i></i>  件</label>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content j_content">
			<!-- 列表容器 -->
			<div class="j_list_box">
				
			</div>
		</section>
	</div>
	<!--结束-->
	<!-- 引入模板 -->
	<%@include file="../../../tpl/order_goods_list.tpl" %>
	<!-- 引入模板 -->
	<script type="text/javascript" src="<%=ProjectConfig.cdn_host %>/js/order_goods_list.js"></script>
</body>
</html>