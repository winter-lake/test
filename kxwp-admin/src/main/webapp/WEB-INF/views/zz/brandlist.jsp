<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>品牌管理</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="/css/zz/brandlist.css">
</head>
<body> 
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %> 
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<!--条件查询功能-->
			<form class="k_change" method="post" action="#">
				<b class="k-b">
					<i>品牌名称 :</i>
					<input type="text" id="brand_name">
				</b>
				<b class="k-b">
					<i>品牌编号 :</i>
					<input type="text" id="brand_no" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
				</b>
				<b class="k-b">
					<i>状态 :</i>
					<select id="brand_status">
						<option>ALL</option>
					</select>
				</b>
				<input type="button" class="Inquire" value="查询" id="ppcx">
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center" id="brandtab">
			  	<thead style="background:#eee">
					<tr>
						<td>编号</td>
						<td>品牌ID/编号</td>
						<td>品牌名</td>
						<td>品牌LOGO</td>
						<td>品牌slogan（广告语）</td>
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
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">品牌管理</a></li>
		  	<li><a href="#">品牌列表</a></li>
		</ol>
		<a href="/zz/ssBrand/goaddbrand.do" class="add_menu"><i class="icon-plus"></i>添加品牌</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
<script src="<%=contextPath %>/js/zz/brand_list.js"></script>
<script type="text/javascript">
$(document).ready(function(){ 
	$("#ppcx").trigger("click"); 
})
   // 点击查看大图
   Common.initProp("tr td img.j_img_zoom");
</script>
</body>
</html>