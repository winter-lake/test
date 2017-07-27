<%@page import="com.kxwp.admin.constants.CommonRequestAttr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="./common/common.jsp" %>
    
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>404</title>
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
			right:0;
			bottom:120px;
			background:#eff7ff url(<%=contextPath %>/images/share/404_1.png) no-repeat;
		}
		.content p{
			width:400px;
			height:50px;
			position:absolute;
			top:400px;
			left:900px;
		}
		
	</style>
</head>
<body>
	<div class="container">
		<div class="top">
			<p>
				<a href="http://${pageContext.request.serverName}"><img src="<%=contextPath %>/images/share/kxwp_logo.png" alt="" style="position:relative;top:20px;"></a>
				<img src="<%=contextPath %>/images/share/error_title.png" alt="">
			</p>
		</div>
		<div class="content">
			<img src="<%=contextPath %>/images/share/404_2.png" alt="" style="position:relative;left:10%;top:150px;">
			<div class="right" style="position:relative;top:-200px;left:50%;margin-left:-300px;">
				<h1 style="margin-bottom:30px;color:#252a2c;font-size:40px;">抱歉 &nbsp;&nbsp;&nbsp;! &nbsp;&nbsp;&nbsp;未找到您要访问的页面 </h1>
				<h2 style="margin-bottom:30px;color:#6d7376;font-size:30px;">可能因为 :</h2>
				<h4 style="margin-bottom:20px;color:#484d50;font-size:20px;">• 您输入的网址有误，请检查网址是否完整或存在多余字符</h4>
				<h4 style="margin-bottom:30px; color:#484d50;font-size:20px;">• 网址已失效，页面可能已删除</h4>
				<h4 style="color:#252a2c;font-size:20px;">您可以回到  <a href="http://${pageContext.request.serverName}" style="font-weight:bold;color:#1f9ddf;font-size:24px;">首页</a></h4>
			</div>
		</div>
		<%@include file="./common/footer.jsp" %>
	</div>
</body>
</html>