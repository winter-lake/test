<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>服务站列表</title>
	 <%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=basePath %>/css/zz/menu.css">
	<link rel="stylesheet" href="<%=basePath %>/css/fwz/fwz_list.css">
	<script type="text/javascript" src="<%=contextPath %>/js/zz/fwz_list.js"></script>
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %> 
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<!--查询等功能-->
			<form class="k_change" method="post" action="">
				<b class="k-b">
					<i>服务省</i>
					<select id="province">
					</select>
				</b>
				<b class="k-b">
					<i>服务市</i>
					<select id="city">
					</select>
				</b>
				<b class="k-b">
					<i>服务区县</i>
					<select id="county">
					</select>
				</b>
				<b class="k-b">
					<i>服务站名称</i>
					<select id="serviceStation" style="width:200px">
						
					</select>
				</b>
				<button type="button" class="j_search_fwz_btn btn btn-primary">查询</button>
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center" id="fwz_list">
			  	<thead style="background:#eee">
					<tr>
						<td>编号</td>
						<td>服务站</td>
						<td>联系人</td>
						<td>服务区域</td>
						<td>客服电话</td>
						<td>平台使用费</td>
						<td>保证金</td>
						<td>添加时间</td>
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
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">服务站管理</a></li>
		  	<li><a href="#">服务站列表</a></li>
		</ol>
		<a href="<%=basePath %>zz/manager/fwz/gotoAddFWZ.do" class="add_menu"><i class="icon-plus"></i>添加服务站</a>
	</div>
	<!--面包屑导航结束-->
	 <%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
<script type="text/javascript">
	seajs.use("zz/fwz/list", function(view) {
		view.init();
	});
</script>
</body>
</html>