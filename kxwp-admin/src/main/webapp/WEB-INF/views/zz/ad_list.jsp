<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>广告列表</title>
	 <%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="/css/zz/ad.css">
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
			<form class="k_change" method="post" action="#">
				<b class="k-b">
					<i>广告名称</i>
					<input type="text" id="name">
				</b>
				<b class="k-b">
					<i>状态</i>
					<select id="adStatus">
					</select>
				</b>
				<b class="k-b">
					<i>类型</i>
					<select id="adType"></select>
				</b>
				<b class="k-b">
					<i>所属区域</i>
					<select id="adLocation" style="width:200px">
						
					</select>
				</b>
				<b class="k-b">
					<i>开始时间</i>
					<input type="text" id="startDateTime">
				</b>
				<b class="k-b">
					<i>结束时间</i>
					<input type="text" id="endDateTime">
				</b>
				<input type="button" class="Inquire" value="查询" id="adcx">
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center" id="zzad">
			  	<thead style="background:#eee">
					<tr>
						<td>编号</td>
						<td>广告名称</td>
						<td>缩略图</td>
						<td>平台类型</td>
						<td>所属区域</td>
						<td>类型</td>
						<td>状态</td>
						<td>开始时间</td>
						<td>结束时间</td>
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
		  	<li><a href="#">广告管理</a></li>
		  	<li><a href="#">广告列表</a></li>
		</ol>
		<!-- <a href="/zz/msAd/addInit.do" class="add_menu"><i class="icon-plus"></i>添加广告</a> -->
	</div>
	<!--面包屑导航结束-->
	 <%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
<script src="/js/zz/ad.js"></script>

<script type="text/javascript">
   // 点击查看大图
   Common.initProp("tr td img.j_img_zoom");
</script>
</body>
</html>