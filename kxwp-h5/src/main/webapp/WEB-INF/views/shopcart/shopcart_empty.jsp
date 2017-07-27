<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>进货单</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/shop_list_empty.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<span>进货单</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="null">
				<p><img src="<%=contextPath %>/images/null.png"></p>
				<a href="/h5/user/home.do" class="roam">去逛逛</a>
			</div>
		</section>
		<!--主体-->
		
		<!--footer-->
		<%@ include file="../common/footer.jsp" %>
		<!--footer-->
	</div>
	<!--结束-->
</body>
</html>