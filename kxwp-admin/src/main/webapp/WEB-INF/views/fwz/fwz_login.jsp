<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>服务站登录</title>
	 <%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=contextPath %>/css/share/login_common.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/fwz/fwz_login.css">
</head>
<body>
	<div class="k_container">
		<div class="k_top">
			<a href=""><img src="<%=contextPath %>/images/share/kxwp_logo.png"></a>
			<a href=""><img src="<%=contextPath %>/images/fwz/fwz_logo.png"></a>
		</div>
		<div class="k_content">
			<div class="content_top">
				<form class="form" method="post" action="" id="fwz_login_form">
					<p class="form_title"><span>服务站管理系统登录</span></p>
					<p class="form_user"><i class="icon-user"></i><input type="text" placeholder="请输入手机号" id="login_mobile" name="login_mobile" class="validate[required,minSize[11],maxSize[11],custom[phone]]"></p>
					<p class="form_pwd"><i class="icon-lock"></i><input type="password" id="login_password" name="login_password" placeholder="密码" class="validate[required,minSize[8]]"></p>
					<h6><a href="/fwz/userManage/gofwzForgot.do" class="wjmm">忘记密码?</a></h6>
					<p class="form_btn">
						<span class="login">登录</span>
						<input type="reset" class="reset" value="重置">
					</p>
				</form>
			</div>
			<div class="gys_tab">
				<a href="http://zz.kxp1688.com"><img src="<%=contextPath %>/images/share/footer_zz.png">总站</a>
				<a href="http://gys.kxp1688.com"><img src="<%=contextPath %>/images/share/footer_fwz.png">供应商</a>
				<a href="http://kxp1688.com"><img src="<%=contextPath %>/images/share/footer_kxgw.png">快消官网</a>
            </div>
		</div>
		 <%@include file="../common/footer.jsp" %>
	</div>
	<script src="<%=contextPath %>/js/fwz/fwz.login.js"></script>
</body>
</html>











