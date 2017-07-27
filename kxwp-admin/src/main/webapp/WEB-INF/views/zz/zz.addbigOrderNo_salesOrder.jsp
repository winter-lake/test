<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>生成大订单编号</title>
<%@ include file="../common/share_static.jsp"%>
<link rel="stylesheet" href="<%=contextPath%>/css/zz/cs.css">
<link rel="stylesheet" href="<%=contextPath%>/css/zz/menu.css">


</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftNav.jsp"%>

		<!--右边开始-->
		<div class="k_right">
			<div class="content">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="<%=contextPath%>/zz/manager/salesOrder/addBigOrderNo.do"><button class="btn btn-info btn-sm" >生成大订单编号</button></a>
			</div>
		</div>
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background: #fff">
				<li><a href="#">总站</a></li>
				<li><a href="#">订单管理</a></li>
				<li><a href="#">生成大订单编号</a></li>
			</ol>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp"%>
		<!--footer结束-->
	</div>
	<!--end-->
</body>
</html>