<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>忘记密码</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=contextPath %>/css/fwz/fwzwjmm.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
<!--头部开始-->
	<div class="k_top">
		<div class="k_logo">
			<span class="k_logo1">
				<img src="../../images/share/kxwp_logo.png" alt="">
			</span>
			<span>
				<img src="../../images/share/title_fwz.png" alt="">
			</span>               
		</div>
	</div>
	<!--头部结束-->
	<!--右边开始-->
		<div class="main">
			<form class="wjmm" id="wjmmform" action="/fwz/userManage/fwzforgotPassword.do" method="POST">
				<h5 class="title">忘记密码</h5>
				<p><span>手机号码</span><input type="tel" id="usermobile" name="forgot_mobile" placeholder="请输入绑定手机号" class="validate[required,custom[phone],minSize[11],maxSize[11]]" class="phone"></p>
				<p><span>验证码</span><input type="text" id="smscode" name="vercode" placeholder="请输入6位验证码" class="validate[required,custom[number],minSize[6],maxSize[6]]" ><button id="fsyzm"class="hqyz" type="button" style="border: none;;">获取验证码</button></p>
				<p><span>新密码</span><input type="password" id="newpwd" name="new_password" placeholder="请输入8-20位字母和数字" class="validate[required,custom[checkpassword],minSize[8],maxSize[20]]" class="pwd"></p>
				<p><span>确认密码</span><input type="password" id="repwd" name="re_password" placeholder="请输入8-20位字母和数字" class="validate[required,custom[checkpassword],minSize[8],maxSize[20],equals[newpwd]]" class="ypwd"></p>
				<p style="border:none"><button type="button" class="qdxg">确定</button></p>
			</form>
		</div>
	<!--右边结束-->
	 <%@include file="../common/footer.jsp" %>
</div>
<script src="/js/fwz/fwzwjmm.js"></script>
<!--end-->
</body>
</html>