<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="./common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>500</title>
		<%@include file="./common/share_static.jsp" %>
	<style>
		*{
			margin:0;
			padding:0;
			font-family:"微软雅黑";
		}
		.container{
			width:100%;
			height:100%;
			overflow:hidden;
			position:fixed;
			top:0;
			bottom:0;
			right:0;
			left:0;
		}
		
		.content{
			position:absolute;
			top:100px;
			left:0;
			bottom:120px;
			background:#5191cc;
		}
		
	</style>
</head>
<body>
	<div class="container">
		<div class="top">
			<p>
				<a href="http://${pageContext.request.serverName}"><img src="<%=contextPath %>/images/share/kxwp_logo.png" alt="" style="position:relative;top:20px;"/></a>
				<img src="<%=contextPath %>/images/share/error_title.png" alt="" />
			</p>
		</div>
		<div class="content">
			<a href="http://${pageContext.request.serverName}"><img src="<%=contextPath %>/images/share/500.png" alt=""></a>
		</div>
		<%@include file="./common/footer.jsp" %>
	</div>
</body>
</html>