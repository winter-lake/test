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
				<!--查询等功能-->
				<form class="form-inline" role="form" style="margin-top: 20px;margin-left: 5px" id="query_channel_form">
				  <div class="form-group">
				    <label class="sr-only" for="exampleInputEmail2">频道名称</label>
				    <select class="form-control" id="channelName">
					</select>
				  </div>
				  <button type="button" class="btn btn-info" id="query_channel">查询</button>
				</form>
				<!--table-->
				<div class="table-responsive" style="margin-top: 20px;" id="query_channel_table">
			      <table class="table table-bordered">
			        <thead>
			          <tr class="active">
			            <th>编号</th>
			            <th>频道名称</th>
			            <th>频道描述</th>
			            <th>操作</th>
			          </tr>
			        </thead>
			        <tbody>
			        </tbody>
			      </table>
    			</div>
				<!--批量操作及分页-->
				<div class="batch">
					<!--分页-->
					<div class="paging">
						<div id="kkpager"></div>
					</div>
				</div>
		</div>
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background:#fff">
			  	<li><a href="#">服务站</a></li>
			  	<li><a href="#">频道管理</a></li>
			  	<li><a href="#">频道列表</a></li>
			</ol>
			<!-- 总站没有添加会员 -->
			<a href="<%=contextPath %>/fwz/channel/addChannelInit.do" class="add_menu"><i class="icon-plus"></i>添加频道</a>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
				<%@include file ="../common/footer.jsp" %>
		<!--footer结束-->
	</div>	
	<!--end-->
	<script type="text/javascript">
	seajs.use("fwz/channel/list", function(view) {
		view.init();
	});
	</script>
</body>
</html>