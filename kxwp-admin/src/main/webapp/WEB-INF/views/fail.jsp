<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="./common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>操作失败</title>
	<%@include file="./common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/share/czsb.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="./common/header.jsp" %>
	<%@include file="./common/leftNav.jsp" %>
	<!--左边结束-->
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<div class="message">
				<dl>
					<dt><img src="<%=contextPath %>/images/share/no.png"></dt>
					<dd>
						<h4>操作失败</h4>
						<p>错误信息: <i class="name">${error_message }</i></p>
					</dd>
				</dl>
				<textarea class="hide">
					失败原因
				</textarea>
			</div>
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