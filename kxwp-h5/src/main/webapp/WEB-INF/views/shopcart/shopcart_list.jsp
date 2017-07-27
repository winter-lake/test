<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>进货单</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/shopcart_list.css?t=<%=VersionUtil.VERSIONNO %>">
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/shop_list_empty.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>进货单</span>
			<a href="javascript:void(0);" class="j_edit" data-bind="text: editBtnText">编辑</a>
		</header>
		<!--头部-->
		<!-- 当有数据时 -->
		<div data-bind="visible: shoppingcarResult().length > 0">
			<!--主体-->
			<section class="content" style="overflow:auto;" data-bind="visible: !isEdit()">
				<div class="scroller" data-bind="foreach: shoppingcarResult">
				   	<div class="main_shop j_shop_item" data-bind="attr: {'data-shopid': $data.supplierId }">
						<h4 class="shop_title">
							<input type="checkbox" class="j_shop_check" name="" data-bind="checked: $data.supplierAllCheck == 1">
							<i><img src="<%=contextPath %>/images/shop_logo.png"></i>
							<span class="shop_name" data-bind="text: $data.supplierName"></span>
						</h4>
						<!-- ko foreach: $data.shoppingCarList -->
						<dl class="d-l j_goods_item" data-bind="attr: {'data-goodsid': $data.goodsId, 'data-id': $data.id }">
							<p class="cec"><input type="checkbox" name="" class="j_goods_check" data-bind="checked: $data.checkStatus == 'CHECK' " ></p>
							<dt><a data-bind="attr:{href: '/h5/goods/goodsDetail.do?id=' + $data.goodsId }"><img data-bind="attr: {src: $data.photoList[0]}"></a></dt>
							<dd>
								<h5 class="dity" data-bind="text: $data.goodsName"></h5>
								<p class="g-g">规格：<span data-bind="text: $data.packageSpecific"></span></p>
								<p class="price-add">
									<span class="price">￥ <b data-bind="text: $data.salePrice"></b></span>
									<ul class="a_r"><li class="add j_goods_reduce"><img src="<%=contextPath %>/images/r_ole.png"></li><li class="input j_goods_num" data-bind="text: $data.goodQit"></li><li class="a_dd j_goods_plus"><img src="<%=contextPath %>/images/a_dd.png"</li></ul>
								</p>
								<p class="msg" data-bind="text: $data.goodsMessage, visible: $data.goodsMessage != '' "></p>
							</dd>
						</dl>
						<!-- /ko -->
						<h5 class="range">
							<span class="min-range">
								<span data-bind="visible: $data.supplierMessage != '', text: $data.supplierMessage"></span>
								最低起订金额: ￥ <i data-bind="moneyText: $data.minShippingAmount"></i>
							</span>
							<span class="number">共 <i data-bind="text: $data.supplierGoodsQit"></i> 件</span>
							<span class="z-price">￥<i data-bind="moneyText: $data.supplierAmount"></i> </span>
						</h5>
					</div>
				</div>
			</section>
			<!--主体-->
			<!-- 编辑时可见的主体 -->
			<section class="content j_edit_content" style="overflow:auto;" data-bind="visible: isEdit()">
				<div class="scroller" data-bind="foreach: shoppingcarResult">
				   	<div class="main_shop j_shop_item" data-bind="attr: {'data-shopid': $data.supplierId }">
						<h4 class="shop_title">
							<input type="checkbox" class="j_edit_shop_check" name="">
							<i><img src="<%=contextPath %>/images/shop_logo.png"></i>
							<span class="shop_name" data-bind="text: $data.supplierName"></span>
						</h4>
						<!-- ko foreach: $data.shoppingCarList -->
						<dl class="d-l j_goods_item" data-bind="attr: {'data-goodsid': $data.goodsId, 'data-id': $data.id }">
							<p class="cec"><input type="checkbox" name="" class="j_edit_goods_check" data-bind="value: $data.id; " ></p>
							<dt><a data-bind="attr:{href: '/h5/goods/goodsDetail.do?id=' + $data.goodsId }"><img data-bind="attr: {src: $data.photoList[0]}"></a></dt>
							<dd>
								<h5 class="dity" data-bind="text: $data.goodsName"></h5>
								<p class="g-g">规格：<span data-bind="text: $data.packageSpecific"></span></p>
								<p class="price-add">
									<span class="price">￥ <b data-bind="text: $data.salePrice"></b></span>
									<ul class="a_r"><li class="add j_goods_reduce"><img src="<%=contextPath %>/images/r_ole.png"></li><li class="input j_goods_num" data-bind="text: $data.goodQit"></li><li class="a_dd j_goods_plus"><img src="<%=contextPath %>/images/a_dd.png"</li></ul>
								</p>
								<p class="msg" data-bind="text: $data.goodsMessage, visible: $data.goodsMessage != '' "></p>
							</dd>
						</dl>
						<!-- /ko -->
						<h5 class="range">
							<span class="min-range">
								<span data-bind="visible: $data.supplierMessage != '', text: $data.supplierMessage"></span>
								最低起订金额: ￥ <i data-bind="moneyText: $data.minShippingAmount"></i>
							</span>
							<span class="number">共 <i data-bind="text: $data.supplierGoodsQit"></i> 件</span>
							<span class="z-price">￥<i data-bind="moneyText: $data.supplierAmount"></i> </span>
						</h5>
					</div>
				</div>
			</section>
			<!-- 编辑时可见的主体 -->
			<div class="message" data-bind="visible: platFormMessage() != '', text: platFormMessage "></div>
			<div class="all" data-bind="visible: !isEdit()">
				<span class="q-x">
					<input type="checkbox" class="j_shopcar_check" name="" style="position:relative;top:.2rem;" data-bind="checked: allCheck() == 1">
					<label>全选</label>
				</span>
				<span class="z-num">
					<em>合计 :<i class="zz-price">￥<span data-bind="moneyText: totalAmount"></span></i></em><br>
					<em>共 <i data-bind="text: totalGoodsCount"></i> 种 <i data-bind="text: totalGoodsQit"></i> 件</em>
				</span>

				<a href="javascript:void(0);" class="account" data-bind="visible: platFormstandard() == 'N' ">结算</a>
				<a href="/h5/shoppingCart/confirmOrder.do" style="background:red;" class="account" data-bind="visible: platFormstandard() == 'Y' ">结算</a>
			</div>
			<!-- 编辑时可见 -->
			<div class="all" data-bind="visible: isEdit()">
				<span class="q-x">
					<input type="checkbox" name="" class="j_edit_all" style="position:relative;top:.2rem;">
					<label>全选</label>
				</span>
				<!-- <span class="collect">移入收藏</span> -->
				<button class="delect j_delete_goods">删除</button>
			</div>
			<!-- 编辑时可见 -->
		</div>
		<!-- 当有数据时 -->
		<!-- 购物车为空 -->
		<section class="content" data-bind="visible: !isFirst() && shoppingcarResult().length == 0">
			<div class="null">
				<p><img src="<%=contextPath %>/images/null.png"></p>
				<a href="/h5/user/home.do" class="roam">去逛逛</a>
			</div>
		</section>
		<!-- 购物车为空 -->
		<!--footer-->
		<%@ include file="../common/footer.jsp" %>
		<!--footer-->
	</div>
	<!--结束-->
	<script src="<%=ProjectConfig.cdn_host %>/js/underscore.js"></script>
	<script src="<%=ProjectConfig.cdn_host %>/js/knockout.js"></script>
	<script src="<%=ProjectConfig.cdn_host %>/js/shopcart_list.js"></script>
</body>
</html>