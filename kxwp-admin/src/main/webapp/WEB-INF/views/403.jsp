<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="./common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>403</title>
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
			top:90px;
			left:0;
			right:0;
			bottom:70px;
			background:#dcf3f2 url(<%=contextPath %>/images/share/no-diction.png) no-repeat;
			background-size:100% 520px;
		}
		.content .back{
			width:400px;
			height:50px;
			position:absolute;
			top:53%;
			left:57%;
			font-size:18px;
			font-family:"微软雅黑";
		}
		.content .back a{
			color:#e50101;
			font-weight:bold;
			font-size:20px;
		}
	</style>
</head>
<body>
	<div class="container">
		<div class="top">
			<p>
				<img src="<%=contextPath %>/images/share/kxwp_logo.png" alt="" style="position:relative;top:14px;"/>
				<img src="<%=contextPath %>/images/share/title_${sys_url_prefix }.png" style="position:relative;top:-8px;left:10px;" alt="" />
			</p>
		</div>
		<div class="content">
			<p class="back">
				点击返回  <a href="/">登录页面</a>
			</p>
		</div>
		<%@include file="./common/footer.jsp" %>
	</div>
</body>
</html>