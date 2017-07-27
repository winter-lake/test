<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>超市管理页面</title>
	<%@ include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=basePath %>css/fwz/cs.css">
	<link rel="stylesheet" href="<%=basePath %>css/fwz/menu.css">

	 
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/leftNav.jsp" %>

	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<!--查询等功能-->
			<form class="k_change" method="post" action="">
				<b class="k-b">
					<i>超市名称</i>
					<select id="supermarket_name">
					</select>
				</b>
				<b class="k-b">
					<i>手机号</i>
					<input id="supermarket_phone"></input>
				</b>
				<b class="k-b">
					<i>注册时间</i>
					<input type="text" name="startDate" id="startDate" placeholder="请输入开始时间">
					<i>至</i>
					<input type="text" name="endDate" placeholder="请输入结束时间" id="endDate">
				</b>
				<b class="k-b">
					<i>状态</i>
					<select id="cs_zt">
						
					</select>
				</b>
				<input type="button" class="Inquire" value="查询" id="cscx">
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center" id="csmenu">
			  	<thead style="background:#eee">
					<tr>
						<td>编号</td>
						<td>手机号</td>
						<td>超市名称</td>
						<td>收货地址</td>
						<td>收货人</td>
						<td>注册时间</td>
						<td>最后登录时间</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
			<!--批量操作及分页-->
			<div class="batch">
				<!--分页-->
				<div class="paging">
					<div id="kkpager"></div>
				</div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">服务站</a></li>
			<li><a href="#">超市管理</a></li>
		  	<li><a href="#">超市列表</a></li>
		</ol>
		<!-- 总站没有添加会员 -->
		<!-- <a href="add_menu.html" class="add_menu"><i class="icon-plus"></i>添加会员</a> -->
		<a href="<%=basePath%>fwz/supermarket/gotoAddSupermarket.do" class="add_menu"><i class="icon-plus"></i>添加会员</a>
	</div>
	<!--面包屑导航结束-->
	<!--footer开始-->
			<%@include file ="../common/footer.jsp" %>
	<!--footer结束-->
</div>	
<!--end-->
	<script src="<%=contextPath %>/js/fwz/fwz.cscx.js"></script>
</body>
</html>