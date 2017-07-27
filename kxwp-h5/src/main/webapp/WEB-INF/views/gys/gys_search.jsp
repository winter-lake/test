<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>供应商搜索</title>
	 <%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/gys.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="j_container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<p class="p-center"><input type="text" name="supplier_name" value="${param.supplier_name }" placeholder="请输入供应商名称" id="gys_search"></p>
			<a href="javascript:;" class="search" id="j_search_btn">搜索</a>
		</header>
		<!--头部-->
		<!--主体-->
		<input type="hidden" name="supplier_name" id="supplier_name" value="${param.supplier_name }"/>
		<section class="j_content" id="content" style="overflow-y:auto">
			<!-- <div class="gys_px">
				<span class="lei">综合</span>
				<span class="lei">交易量</span>
				<span class="lei cur sup">品类 <i class="icon-angle-down"></i></span>	
				<div class="down_list">
					<p class="dl">
						<a href="">休闲食品</a>
						<a href="">酒水饮料</a>
						<a href="">粮油副食</a>
					</p>
					<p class="dl">
						<a href="">进口食品</a>
						<a href="">母婴用品</a>
						<a href="">日用百货</a>
					</p>
					<p class="dl">
						<a href="">文体办公</a>
						<a href="">水果生鲜</a>
						<a href="">&nbsp;&nbsp;&nbsp;其他&nbsp;&nbsp;&nbsp;</a>
					</p>
					<div class="btn">
						<span class="reset">重置选项</span>
						<span class="sure">&nbsp;&nbsp;&nbsp;确定&nbsp;&nbsp;&nbsp;</span>
					</div>
				</div>
				-->
			<div class="gys_list">
			<div class="j_list_box"></div>
			</div>
		</section>
		<!--主体-->
		<!--footer-->
		<%@ include file="../common/footer.jsp" %>
		<!--footer-->
	</div>
	<!--结束-->
	<!-- 引入模板 -->
	<%@include file="../../../tpl/supplier_list.tpl" %>
	<script src="<%=ProjectConfig.cdn_host %>/js/gys_search.js"></script>
</body>
</html>