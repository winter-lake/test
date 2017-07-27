<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>我的订单</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/my_order.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container j_container">
	   <input type="hidden" name="orderStatus" value="${param.orderStatus }" />
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<div id="my_list">
			<c:choose>
				<c:when test="${param.orderStatus eq 'Awaiting_Shipping' }">
					待发货
				</c:when>
				<c:when test="${param.orderStatus eq 'Awaiting_Receive' }">
					已发货
				</c:when>
				<c:when test="${param.orderStatus eq 'Completed' }">
					交易完成
				</c:when>
				<c:when test="${param.orderStatus eq 'Cancelled' }">
					已取消
				</c:when>
	    		<c:otherwise>
	        		全部订单
	    		</c:otherwise>
	    	</c:choose>
				<i class="icon-angle-down"></i>
				<ul class="all_list">
					<li><a href="/h5/order/gotoMyOrder.do"><button>全部订单</button></a></li>
					<!-- <li>待付款</li> -->
					<li><a href="/h5/order/gotoMyOrder.do?orderStatus=Awaiting_Shipping"><button>待发货</button></a></li>
					<li><a href="/h5/order/gotoMyOrder.do?orderStatus=Awaiting_Receive"><button>已发货</button></a></li>
					<li><a href="/h5/order/gotoMyOrder.do?orderStatus=Completed"><button>交易完成</button></a></li>
					<li><a href="/h5/order/gotoMyOrder.do?orderStatus=Cancelled"><button>已取消</button></a></li>
				</ul>
			</div>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content j_content" style="margin-bottom:0;">
			<!-- 列表容器 -->
			<div class="j_list_box"></div>
			<div class="clear_view" style="display:none;">
				<span class="view_list btn">查看订单</span>
				<span class="clear_list btn">取消订单</span>
			</div>
		</section>
	</div>
	<!--结束-->
	<!-- 引入模板 -->
	<%@include file="../../../tpl/order_list.tpl" %>
	<!-- 引入模板 -->
	<script type="text/javascript" src="<%=ProjectConfig.cdn_host %>/js/order.myorder.js"></script>
</body>
</html>