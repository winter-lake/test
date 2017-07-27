<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="./common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>注册成功</title>
		<%@include file="./common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/share/czcg.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<!--头部开始-->
	<%@include file="./common/header.jsp" %>
	<%@include file="./common/leftNav.jsp" %>
	<!--左边结束-->
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<dl class="message">
				<dt><img src="<%=contextPath %>/images/share/ok.png"></dt>
				<dd>
					<h4>恭喜您 ， 操作成功</h4>
					<p class=>您的会员账号为 : <i class="name">${accountMobile}</i>请妥善保管</p>
				</dd>
			</dl>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">添加资源</a></li>
		</ol>
	</div>
	<!--面包屑导航结束-->
	<!--footer开始-->
		<%@include file="./common/footer.jsp" %>
	<!--footer结束-->
</div>	
<!--end-->
</body>
</html>