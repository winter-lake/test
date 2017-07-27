<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>提交订单</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/confirm_order.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container j_container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/share_static.jsp" %>
			<span>订单确认</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content" style="overflow: auto;">
			<div class="scroller">
		    <input type="hidden" name="orderSeq" id="orderSeq" value="${orderData.orderSeq }"/>
			<div class="msg">
				<h4 class="name-phone"><span class="name">${orderData.receptionName }</span><span class="phone">${orderData.phone }</span></h4>
				<h5 class="addres">${orderData.full_address }</h5>
			</div>
			<c:forEach items="${orderData.supplierResultList }" var="order">
			    <div class="s-j">
				<h4 class="s-title">${order.supplierName }</h4>
					<c:forEach items="${order.shoppingCarList }" var="item">
					  <dl class="s-dl">
					   <dt><img src="${item.photoList[0] }"></dt>
						<dd>
							<h4 class="d-title">${item.goodsName }"</h4>
							<p class="d-gg">规格：${item.packageSpecific }</p>
							<p class="price-num">
								<span class="price">￥${item.salePrice }</span>
								<span class="number">X${item.goodQit }</span>
							</p>
						</dd>
				      </dl>
					</c:forEach>
				<p class="l-y">
					<textarea placeholder="给供应商留言" supplier-id="${order.supplierId }"></textarea>
				</p>
			</div>
			</c:forEach>
			<div class="z-f">
				<p class="zxzf hide"><input type="checkbox" name=""> 在线支付</p>
				<p class="hdfk"><input type="checkbox" name="" checked="checked"> 货到付款</p>
			</div>
			</div>
		</section>
		<!--主体-->
		<footer class="confirm_footer">
			<span class="f-number-price">
				总计: <i>${orderData.totalGoodsQit }</i>件
				<em class="f-price">￥${orderData.totalAmount }</em>
			</span>
			<span class="j_submit_order submit-list">提交订单</span>
		</footer>
	</div>
	<!--结束-->
	<script src="<%=ProjectConfig.cdn_host %>/js/order.confirm.js"></script>
</body>
</html>