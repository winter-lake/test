<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>首页</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/forgot_password_step2.css?t=<%=VersionUtil.VERSIONNO %>">
	<!--js-->
	<script src="<%=ProjectConfig.cdn_host %>/js/forget_password_step2.js"></script>
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<a href="/h5/user/gotoForgotPasswd.do" class="back"></a>
			<span>找回密码</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="list">
				<span>新密码</span>
				<input type="hidden" name="" value="${param.passKey }" id="passkey" name="passkey">
				<input type="hidden" name="" value="${param.mobile }" id="mobile" name="mobile">
				
				<input type="password" name="" placeholder="请输入密码" id="n_pwd">
			</div>
			<div class="list">
				<span>确认密码</span>
				<input type="password" name="" placeholder="请输入密码" id="sure_pwd">
			</div>
			<i class="bz">备注 : 请讲密码设置为8-20位，并且由字母数字组成，不能与旧密码相同</i>
			<p class="sure" id="sure">完成</p>
			<em class="ydwt">遇到问题? 请<a href="tel:010-58046928"> 联系客服</a></em>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>