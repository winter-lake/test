<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>账户信息</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/account_info.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>账户信息</span>
			<a href="" class="hide edit">编辑</a>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="j-b-msg">
				<h4 class="title">基本信息 :</h4>
				<p class="j-p">
					<span class="j-s">超市名称 :</span>
					<i class="j-i">${supermarket.supermarketName }</i>
				</p>
				<!--收货信息-->
				<div class="j-d">
					<span class="j-s">收货信息 :</span>
					<p class="j-d-p">
						<span class="j-s">收货人 :</span>
						<i class="j-i">${supermarket.receptionName }</i>
					</p>
					<p class="j-d-p">
						<span class="j-s">联系方式 :</span>
						<i class="j-i">${supermarket.phone }</i>
					</p>
					<p class="j-d-p">
						<span class="j-s">收货地址 :</span>
						<i class="j-i">${supermarket.full_address}</i>
					</p>
				</div>
			</div>
			<div class="r-z-msg">
				<h4 class="title">服务站 :</h4>
				<p class="j-p">
					<span class="j-s">名称 :</span>
					<i class="j-i">${supermarket.serviceStationName}</i>
				</p>
				<p class="j-p">
					<span class="j-s">服务站电话 :</span>
					<i class="j-i">${supermarket.serviceStationPhone}</i>
				</p>
				<!--收货信息-->
				<div class="hide j-d">
					<span class="j-s">收货信息 :</span>
					<p class="j-d-p">
						<span class="j-s">收货人 :</span>
						<i class="j-i">李静</i>
					</p>
					<p class="j-d-p">
						<span class="j-s">联系方式 :</span>
						<i class="j-i">13212345678</i>
					</p>
					<p class="j-d-p">
						<span class="j-s">收货地址 :</span>
						<i class="j-i">北京市大兴区经济技术开发区</i>
					</p>
				</div>
			</div>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>