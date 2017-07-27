<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>进货单</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/shopcart_list.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>进货单</span>
			<a href="javascript:;" class="edit">编辑</a>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content" style="overflow:auto;">
			<div class="scroller">
			<c:forEach items="${shopcartData.shoppingcarResult }" var="order">
			   	<div class="main_shop">
				<h4 class="shop_title">
					<input type="checkbox" name="" <c:if test="${order.supplierAllCheck eq 1 }">checked="checked"</c:if> >
					<i><img src="<%=contextPath %>/images/shop_logo.png"></i>
					<span class="shop_name">${order.supplierName }</span>
				</h4>
				<c:forEach items="${order.shoppingCarList }" var="item">
				  <dl class="d-l j_goods_item" data-goodsid="${item.goodsId}" shopcart-id="${item.id }">
					<p class="cec"><input type="checkbox" name="" class="j_goods_check" <c:if test="${order.supplierAllCheck eq 1 }">checked="checked"</c:if>></p>
					<dt><a href="/h5/goods/goodsDetail.do?id=${item.goodsId }"><img src="${item.photoList[0] }"></a></dt>
					<dd>
						<h5 class="dity">${item.goodsName }</h5>
						<p class="g-g">规格：${item.packageSpecific }</p>
						<p class="price-add">
							<span class="price">￥ ${item.salePrice }</span>
							<ul class="a_r"><li class="add j_goods_reduce"><img src="<%=contextPath %>/images/r_ole.png"</li><li class="input j_goods_num"> ${item.goodQit }</li><li class="a_dd j_goods_plus"><img src="<%=contextPath %>/images/a_dd.png"</li></ul>
						</p>
						<c:if test="${item.goodsMessage ne '' }">
						    <p class="msg">${item.goodsMessage}</p>
						</c:if>
						
					</dd>
				</dl>
				</c:forEach>
				<h5 class="range">
					<span class="min-range <c:if test="${order.supplierMessage ne '' }">msg</c:if>">最低起订金额 : <i> ￥${order.minShippingAmount }</i></span>
					<span class="number">共 <i>${order.supplierGoodsQit }</i> 件</span>
					<span class="z-price">￥${order.supplierAmount } </span>
				</h5>
			</div>
			</c:forEach>
			</div>
		</section>
		<!--主体-->
		<div class="message"><c:if test="${shopcartData.platFormMessage ne '' }">
						    <p class="msg">${shopcartData.platFormMessage}</p>
						</c:if></div>
		<div class="all">
			<span class="q-x">
				<input type="checkbox" name="" style="position:relative;top:.2rem;" <c:if test="${shopcartData.allCheck eq 1 }">checked="checked"</c:if>>
				<label>全选</label>
			</span>
			<span class="z-num">
				<em>合计 :<i class="zz-price">￥${shopcartData.totalAmount}</i></em><br>
				<em>共<i>${shopcartData.totalGoodsCount}</i>种 <i>${shopcartData.totalGoodsQit}</i>件</em>
			</span>
			<a href="/h5/shoppingCart/confirmOrder.do" class="account">结算</a>
		</div>
		<!--footer-->
		<%@ include file="../common/footer.jsp" %>
		<!--footer-->
	</div>
	<!--结束-->
	<script src="<%=ProjectConfig.cdn_host %>/js/shopcart_list.js"></script>
</body>
</html>