<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>订单详情</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/order_detail.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>订单详情</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<!--订单状态-->
			<div class="list-state">订单状态 : <span class="state">${orderDetail.orderStatus.desc }</span></div>
			<!--个人信息-->
			<div class="msg">
				<h4 class="name-phone"><span class="name">${orderDetail.receptionName }</span><span class="phone">${orderDetail.phone }</span></h4>
				<h5 class="addres">${orderDetail.full_address }</h5>
			</div>
			<!--商家-->
			<div class="s-j">
				<h4 class="s-title">${orderDetail.supplierName }</h4>
				<c:forEach items="${orderDetail.orderItemList }" var="item">
				   <dl class="s-dl">
					<dt><a href="/h5/goods/goodsDetail.do?id=${item.goodsId }"><img src="<c:if test='${item.photoList == null || fn:length(item.photoList) eq 0 }'>/images/404.png</c:if><c:if test='${item.photoList != null && fn:length(item.photoList) gt 0 }'>${item.photoList[0] }</c:if>"></a></dt>
					<dd>
						<h4 class="d-title">${item.goodsName }</h4>
						<p class="d-gg">规格：${item.packageSpecific }</p>
						<p class="price-num">
							<span class="price">￥${item.salePrice }</span>
							<span class="number">X${item.itemQit }</span>
						</p>
					</dd>
				</dl>
				</c:forEach>
				<p class="z-number-price">
					<span>共<i>${orderDetail.sumGoodsQit }件</i> <em class="zzz-price">￥${orderDetail.orderAmount }</em></span>
				</p>
			</div>
			<!--留言-->
			<div class="l-y">
				<span>留言 :</span>
				<input type="text" name="" value="${orderDetail.orderMessage }">
			</div>
			<!--订单详情-->
			<div class="list-detail">
				<p class="l_p"><i>订单编号 :</i> <span class="list-number">${orderDetail.orderNo }</span></p>
				<p class="l_p"><i>下单时间 :</i> <span class="list-start"><fmt:formatDate value="${orderDetail.orderTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span></p>
				<p class="hide"><i>付款时间 :</i> <span class="fk-time">2016-2-12 10:22:22</span></p>
				<p class="l_p"><i>发货时间 :</i> <span class="fh-time"><c:if test="${orderDetail.deliverTime ne null }"><fmt:formatDate value="${orderDetail.deliverTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></c:if></span></p>
				<p class="l_p"><i>收货时间 :</i> <span class="sh-time"><c:if test="${orderDetail.comfirmedTime ne null }"><fmt:formatDate value="${orderDetail.comfirmedTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></c:if></span></p>
			</div>
		</section>
		<!--主体-->
		<footer class="hide footer">
			<span class="clear-list">
				取消订单
			</span>
			<span class="pay-list">付款</span>
		</footer>
	</div>
	<!--结束-->
</body>
</html>