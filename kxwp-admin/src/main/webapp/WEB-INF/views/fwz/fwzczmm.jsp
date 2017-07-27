<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>重置密码</title>
	 <%@include file="../common/share_static.jsp" %>
	 <link rel="stylesheet" href="<%=contextPath %>/css/fwz/fwzczmm.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<form class="czmm" action="<%=basePath %>fwz/userManage/resetFwzPassword.do" method="post">
				<input type="hidden" name="reset_mobile" value="${userModel.mobile}"> 
				<p><span>用户名</span><input type="text" id="user_mobile" value="${userModel.mobile}" disabled="disabled" style="background:none;border:none"></p>
				<p><span>新密码</span><input type="password" id="use_pwd" name="new_password" class="validate[required,custom[checkpassword],minSize[8],maxSize[20]]" placeholder="请输入8-20位字母和数字"></p>
				<p><span>确认密码</span><input type="password" id="re_pwd" name="re_password" class="validate[required,custom[checkpassword],minSize[8],maxSize[20],equals[use_pwd]]" placeholder="请输入8-20位字母和数字"></p>
				<p>
					<button type="submit" class="bc" id="czmmsub">确认</button>
					<button type="reset" class="cz">重置</button>
				</p>
			</form>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">服务站</a></li>
		  	<li><a href="#">重置密码</a></li>
		</ol>
	</div>
	<!--面包屑导航结束-->
	 <%@include file="../common/footer.jsp" %>
</div>
<script src="<%=contextPath %>/js/fwz/fwzczmm.js"></script>
<!--end-->
</body>
</html>