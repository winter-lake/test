<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>个人中心</title>
    <%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/mypage.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--主体-->
		<section class="content">
			<div class="head">
				<a href="javascript:;" class="set"></a>
				<dl class="icon-name">
					<dt><img src="<%=contextPath %>/images/user.png"></dt>
					<dd>${user.supermarket_name }</dd>
				</dl>
			</div>
			<div class="mylist">
				<span>我的订单</span>
				<a href="<%=contextPath %>/h5/order/gotoMyOrder.do" class="all-list">全部订单</a>
			</div>
			<div class="state">
				<dl class="d-state">
					<a href="<%=contextPath %>/h5/order/gotoMyOrder.do?orderStatus=Awaiting_Shipping">
					<dt><img src="<%=contextPath %>/images/dfk.png"></dt>
					<dd>
						待发货
						<em class="number"></em>
					</dd>
					<em class="number">${orderCount.awaiting_ShippingCount }</em>
					</a>
				</dl>
				<dl class="d-state">
					<a href="<%=contextPath %>/h5/order/gotoMyOrder.do?orderStatus=Awaiting_Receive">
					<dt><img src="<%=contextPath %>/images/dfh.png"></dt>
					<dd>
						待收货
						<em class="number"></em>
					</dd>
					<em class="number">${orderCount.awaiting_ReceiveCount }</em>
					</a>
				</dl>
				<dl class="d-state">
					<a href="<%=contextPath %>/h5/order/gotoMyOrder.do?orderStatus=Completed">
					<dt><img src="<%=contextPath %>/images/dsh.png"></dt>
					<dd>
						已完成
						<em class="number"></em>
					</dd>
					<em class="number">${orderCount.completedCount}</em>
					</a>
				</dl>
			</div>
			<a href="/h5/user/myAccountInfo.do"><div class="zhxx">账户信息 <i></i></div></a>
			<a href="/h5/user/Favorites.do"><div class="scj">我的收藏夹 <i></i></div></a>
			<a href="javascript:void(0)" class="load"><div class="zb">我的账本 <i></i></div></a>
			<a href="javascript:void(0)" class="load"><div class="yjfk">意见反馈 <i></i></div></a>
		</section>
		<!--主体-->
		<!--footer-->
		<%@ include file="../common/footer.jsp" %>
		<!--footer-->
	</div>
	<!--结束-->
	<script src="<%=ProjectConfig.cdn_host %>/js/mypage.js"></script>
</body>
</html>