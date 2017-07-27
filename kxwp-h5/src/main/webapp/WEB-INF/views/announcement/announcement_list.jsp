<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>快销公告</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=ProjectConfig.cdn_host %>/css/announcement_list.css">
</head>
<body>

	<div class="j_container">
	
		<%-- <header class="header">
			<%@include file="../common/back.jsp" %>
			<span>快销公告</span>
		</header> --%>
		<section class="j_content">
			<input type="hidden" id="flag" value="${flag }"/>
			<input type="hidden" id="authStatus" value="${authStatus }"/>
			<div class="message_box"></div>
		</section>
	</div>
	<!-- 引入模板 -->
	<%@include file="../../../tpl/announcement_list.tpl" %>
	<script src="<%=ProjectConfig.cdn_host %>/js/announcement_list.js"></script>
	<script src="<%=ProjectConfig.cdn_host %>/js/callApp.js"></script>
</body>
</html>