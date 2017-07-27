<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/register.css?t=<%=VersionUtil.VERSIONNO %>">
	<script src="<%=ProjectConfig.cdn_host %>/js/register.js"></script>
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<a href="<%=contextPath %>/h5/user/index.do" class="back"></a>
			<span>注册</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content" style="overflow-y:auto;">
			<div class="d-g">
				<span>手机号</span>
				<input type="text" name="" placeholder="11位手机号" id="user" class="in">
			</div>
			<div class="d-g">
				<span>验证码</span>
				<input type="text" name="" placeholder="请输入验证码" id="y-zm" class="in">
				<em class="y_zm" id="sendyzm">发送验证码</em>
			</div>
			<div class="d-g">
				<span>密码</span>
				<input type="password" name="" placeholder="请输入密码" id="y-password" class="in">
				<em id="open-pwd" class="close icon-eye-close"></em>
			</div>
			<div class="bz"><i>备注 : 将密码设置为8-20位，并且由字母，数字组成，不能与旧密码相同</i></div>
			<div class="d-g" style="border-top:.5rem solid #f3f5f7;">
				<span>超市名称</span>
				<input type="text" name="" placeholder="超市名称" id="supermarketName" class="in">
			</div>
			<div class="d-g">
				<span>收货人</span>
				<input type="text" name="" placeholder="收货人" id="man" class="in">
			</div>
			<div class="hide d-g">
				<span>联系手机</span>
				<input type="text" name="" placeholder="11位手机号" id="phone" class="in">
			</div>
			<div id="element_id">
				<div class="d-g">
					<span>省份</span>
					<select class="province" id="province" class="in"></select>
				</div>
				<div class="d-g">
					<span>市</span>
					<select class="city" id="city" class="in"></select>
				</div>
				<div class="d-g">
					<span>区/县</span>
					<select class="area" id="area" class="in"></select>
				</div>
				<div class="d-g">
					<span>镇/街道</span>
					<select class="street" id="street" class="in"></select>
				</div>
			</div>
			<div class="d-g">
				<span>详细地址</span>
				<input type="text" name="" placeholder="请输入详细地址" id="addre" class="in">
			</div>
			<p class="register" id="register">注册</p>
			<h6 class="x-y">北京快消互联科技有限公司</h6>
			<h6 class="x-y">点击注册表示同意 <a href="<%=contextPath %>/h5/user/register_protocol.do" class="blue">用户注册协议</a></h6>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>