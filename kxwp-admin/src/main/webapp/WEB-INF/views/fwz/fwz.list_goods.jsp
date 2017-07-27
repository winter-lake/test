<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>商品列表</title>
	<%@ include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=contextPath %>/css/zz/cs.css">
	<link rel="stylesheet" href="<%=contextPath %>/css/zz/menu.css">

	 
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
			<form class="k_change j_search_form" method="post" action="">
			    <b class="k-b">
					<i>供应商</i>
					<select id="supplier" name="supplier"></select>
				</b>
				<b class="k-b">
					<i>商品名称</i>
					<input type="text" name="goods_name" id="goods_name" placeholder="商品名称">
					<i>条码</i>
					<input type="text" name=barcode placeholder="2343243" id="barcode">
				</b>
				<b class="k-b">
					<i>商品编号</i>
					<input type="text" name="goods_no" placeholder="2343243" id="goods_no">
				</b>
				<b class="k-b">
					<i>状态</i>
					<select id="goods_status" placeholder="全部">
						
					</select>
				</b>
				<input type="button" class="Inquire j_search_btn" value="查询">
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center" id="csmenu">
			  	<thead style="background:#eee">
					<tr>
						<!-- <td><input type="checkbox" name="b"></td> -->
						<td>编号</td>
						<td>商品id/编号</td>
						<td>商品名称</td>
						<td>品牌</td>
						<td>条码</td>
						<td>供应商</td>
						<td>起订量</td>
						<td>价格</td>
						<td>录入时间</td>
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
		  	<li><a href="#">商品管理</a></li>
		  	<li><a href="#">商品列表</a></li>
		</ol>
		<!-- 总站没有添加会员 -->
		<%-- <a href="<%=contextPath %>/gys/goods/gotoAddGoods.do" class="add_menu"><i class="icon-plus"></i>添加商品</a> --%>
	</div>
	<!--面包屑导航结束-->
	<!--footer开始-->
			<%@include file ="../common/footer.jsp" %>
	<!--footer结束-->
</div>	
<!--end-->
	<script type="text/javascript">
	seajs.use("fwz/goods/list", function(view) {
		view.init();
	});
</script>
</body>
</html>