<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>公告列表</title>
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
				<form class="k_change j_search_form" method="post">
					<b class="k-b">
						<i>公告名称</i>
						<input type="text"  id="announcementName" placeholder="公告名称">
					</b>
					<b class="k-b">
						<i>状态</i>
						<select id="announcementStatus">
						</select>
					</b>
					<b class="k-b">
						<i>发送时间</i>
						<input type="text" id="startDateTime">
					</b>
					<b class="k-b">
						<i>至</i>
						<input type="text" id="endDateTime">
					</b>
					<input type="button" class="Inquire j_search_btn" value="查询">
				</form>
				<!--table-->
				<table class="table table-bordered" style="text-align:center" id="announcement_list">
				  	<thead style="background:#eee">
						<tr>
							<td>编号</td>
							<td>公告名称</td>
							<td>公告描述</td>
							<td>缩略图</td>
							<td>发送时间</td>
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
			  	<li><a href="#">公告消息</a></li>
			  	<li><a href="#">公告列表</a></li>
			</ol>
			<!-- 总站没有添加会员 -->
			<a href="<%=contextPath %>/fwz/SsAnnouncement/addInit.do" class="add_menu"><i class="icon-plus"></i>添加公告</a>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
				<%@include file ="../common/footer.jsp" %>
		<!--footer结束-->
	</div>	
	<!--end-->
	<script type="text/javascript">
	seajs.use("fwz/announcement/list", function(view) {
		view.init();
	});
	</script>
</body>
</html>