<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>供应商详情</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/gys_detail.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="j_container">
		<!-- header -->
		<div class="header">
			<%@include file="../common/back.jsp" %>
			<p class="val"><input type="text" name="goods_name" placeholder="搜索供应商商品" id="goods_name"></p>
			<a href="javascript:;" class="a_search" id="j_search_btn">搜索</a>
		</div>
		<!-- header -->
		<!--主体-->
		<section class="j_content" id="content" style="overflow-y:auto;">
			<!-- 配送范围和优惠政策 -->
			<dl class="company_show">
				<dt><img src="<%=contextPath %>/images/company.png"></dt>
				<dd>
					<h4 class="title">${supplierDetail.supplierName }</h4>
					<p class="c_p"><span>配送范围 :</span> <i><c:forEach items="${supplierDetail.shippingAreaList }" var="area" varStatus="area_index">${area.region_desc } <c:if test="${area_index.index+1 != fn:length(supplierDetail.shippingAreaList) }">,</c:if></c:forEach> </i></p>
					<p class="c_p"><span>优惠政策 :</span> <i>无</i></p>
				</dd>
			</dl>
			<!-- 代理品牌 -->
			<div class="pp_show">
				<span class="s_show">代理品牌 : <c:forEach items="${supplierDetail.agentBrandList }" var="brand" varStatus="brand_index">${brand.brand_name }<c:if test="${brand_index.index+1 != fn:length(supplierDetail.agentBrandList) }">,</c:if></c:forEach></span>
			</div>
			<input type="hidden" name="supplier_id" id="supplier_id" value="${param.supplierID }"/>
			<!-- 排序 -->
			<div class="d_sub" id="d_sub">
				<span class="j_rank_code sub cur z_h">
					<span data-rcode="CREATE_TIME_DESC" class="active">综合</span>
				</span>
				<span class="j_rank_code sub x_l">
					<span data-rcode="SOLD_DESC" class="active">销量 <i class="icon-caret-down"></i></span>
					<span data-rcode="SOLD_ASC" class="hide">销量 <i class="icon-caret-up"></i></span>
				</span>
				<span class="j_rank_code sub j_g">
					<span data-rcode="SALE_PRICE_DESC" class="active">价格 <i class="icon-caret-down"></i></span>
					<span data-rcode="SALE_PRICE_ASC" class="hide">价格 <i class="icon-caret-up"></i></span>
				</span>
				<span class="sub j_filter_btn">
					<span>筛选</span>
				</span>
			</div>
			<!-- 商品列表 -->
			<div class="sp">
				<div class="sp-list j_list_box"></div>
			</div>
		</section>
		<!-- 主体 -->
		<!--footer-->
		<footer class="search-footer j_total_car">
			<span class="foot">数量 :<i class="z_num j_total_numbers">0</i></span>
			<span class="foot">价格 :<i class="rmb">￥ <span class="j_total_price">0</span></i></span>
			<span class="addlist j_add_submit">加入进货单</span>
		</footer>
		<!--footer-->
		<!-- 购物车 -->
		<a href="/h5/shoppingCart/gotoShoppingCart.do" class="shopcar j_shopcar_icon">

		</a>
		<!-- 购物车 -->
	</div>
	<!--结束-->
	<!-- 引入模板 -->
	<%@include file="../../../tpl/goods_list.tpl" %>
	<!-- 引入js -->
	<script src="<%=ProjectConfig.cdn_host %>/js/gys_detail.js"></script>
</body>
</html>