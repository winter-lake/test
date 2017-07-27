<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>消息中心</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/new_list.css">
	<script src="<%=ProjectConfig.cdn_host %>/js/new_list.js"></script>
</head>
<body>
	<!--开始-->
	<div class="j_container">
		<!--头部-->
		<%-- <header class="header">
			<%@include file="../common/back.jsp" %>
			<span>消息中心</span>
			<a class="setting"></a>
		</header> --%>
		<!--头部-->
		<!--主体-->
		<section class="j_content">
			<div class="kx_msg">
				<dl>
					<dt><img src="<%=contextPath %>/images/kx_msg.png"></dt>
					<dd>
						<h4 class="title">
							快销公告
							<a class="time"></a>
						</h4>
						<p class="message"></p>
					</dd>
				</dl>
			</div>
			<%-- <div class="order_msg">
				<dl>
					<dt><img src="<%=contextPath %>/images/onder_msg.png"></dt>
					<dd>
						<h4 class="title">
							快销公告
							<a class="time">04-14</a>
						</h4>
						<p class="message">银鱼。一般常见的小鱼仔都是用银鱼做成的。银鱼是淡水鱼，生存分布于东亚咸水和淡水中，在中国被誉为美味。体细长，似鲑，无鳞或具细鳞。银鱼可分为小银鱼和大银鱼，其中大银鱼可长到长约七至十厘米，而小银鱼就是常见的小鱼仔零食的原料</p>
					</dd>
				</dl>
			</div> --%>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>