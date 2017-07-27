<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>修改密码</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="css/change_password.css?t=<%=VersionUtil.VERSIONNO %>">
	<!--js-->
	<script src="js/zepto-v.1.1.6.min.js"></script>
	<script src="js/Common.js"></script>
	<script src="js/iscroll.js"></script>
	<script src="js/swiper.min.js"></script>
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/share_static.jsp" %>
			<span>修改密码</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="phone-verify">
				<p class="p-phone">
					<span class="s-phone">新密码</span>
					<input type="text" placeholder="输入新密码">
				</p>
				<p class="p-verify">
					<span class="s-verify">确认新密码</span>
					<input type="text" placeholder="再次输入新密码"/>
				</p>
			</div>
			<div class="pwd-show">密码8-20个字符,包含字母数字字符，字母区分大小写</div>
			<p class="a-sure">保存</p>
		</section>	
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>