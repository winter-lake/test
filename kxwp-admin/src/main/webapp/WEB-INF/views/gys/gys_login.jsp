<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>供应商登录</title>
	 <%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=contextPath %>/css/share/login_common.css">
</head>
<body>
	<div class="k_container">
		<div class="k_top">
			<a href=""><img src="<%=contextPath %>/images/share/kxwp_logo.png"></a>
			<a href=""><img src="<%=contextPath %>/images/gys/logo_gys.png"></a>
		</div>
		<div class="k_content">
			<div class="content_top">
				<form class="form" method="POST" id="gys_login_form" action="">
					<p class="form_title"><span>供应商管理系统登录</span></p>
					<p class="form_user"><i class="icon-user"></i><input type="text" placeholder="请输入手机号" id="login_mobile" name="login_mobile" class="validate[required,minSize[11],maxSize[11],custom[phone]]"></p>
					<p class="form_pwd"><i class="icon-lock"></i><input type="password" id="login_password" name="login_password" placeholder="密码" class="validate[required,minSize[8]]"></p>
					<h6><a href="" class="yhzc">用户注册</a><a href="/gys/userManage/gogysForgot.do" class="wjmm">忘记密码?</a></h6>
					<p class="form_btn">
						<span class="login">登录</span>
						<input type="reset" class="reset" value="重置">
					</p>
				</form>
			</div>
			<div class="gys_tab">
				<a href="http://zz.kxp1688.com"><img src="<%=contextPath %>/images/share/footer_zz.png">总站</a>
				<a href="http://fwz.kxp1688.com"><img src="<%=contextPath %>/images/share/footer_fwz.png">服务站</a>
				<a href="http://kxp1688.com"><img src="<%=contextPath %>/images/share/footer_kxgw.png">快消官网</a>
			</div>
		</div>
		 <%@include file="../common/footer.jsp" %>
	</div>
		<script src="<%=contextPath %>/js/gys/gys.login.js"></script>
</body>
</html>











