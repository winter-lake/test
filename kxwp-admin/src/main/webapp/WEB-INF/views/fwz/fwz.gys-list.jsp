<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>供应商列表页面</title>
	<%@ include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=contextPath %>/css/fwz/cs.css">
	<link rel="stylesheet" href="<%=contextPath %>/css/fwz/menu.css">

	 
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
					<i>负责人</i>
					<input id="gys_admin_name" name="gys_admin_name" type ="text">
				</b>
				<b class="k-b">
					<i>负责人电话</i>
					<input id="supplierAdminMobile" name="supplierAdminMobile" type ="text">
				</b>
				<b class="k-b">
					<i>供应商名称</i>
					<select id="supplier_name" name="supplier_name"></select>
				</b>
				<b class="k-b">
					<i>状态</i>
					<select id="cs_zt">
						
					</select>
				</b>
				<input type="button" class="Inquire" value="查询" id="gyscx">
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center" id="gys_table">
			  	<thead style="background:#eee">
					<tr>
						<td>序号</td>
						<td>id/编号</td>
						<td>负责人</td>
						<td>负责人电话</td>
						<td>供应商名称</td>
						<td>详细地址</td>
						<td>客服电话</td>
						<td>注册时间</td>
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
		  	<li><a href="#">供应商管理</a></li>
		  	<li><a href="#">供应商列表</a></li>
		</ol>
		<!-- 总站没有添加会员 -->
	<a href="<%=contextPath%>/fwz/manager/gys/gotoAddGys.do" class="add_menu"><i class="icon-plus"></i>添加供应商</a>

	</div>
	<!--面包屑导航结束-->
	<!--footer开始-->
			<%@include file ="../common/footer.jsp" %>
	<!--footer结束-->
</div>	
<!--end-->
	<script src="<%=contextPath %>/js/fwz/fwz.gyscx.js"></script>
</body>
</html>