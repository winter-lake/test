<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>商品详情</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/goods_detail.css?t=<%=VersionUtil.VERSIONNO %>">
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/swiper.min.css">
	<script src="<%=ProjectConfig.cdn_host %>/js/swiper.min.js"></script>
	<script src="<%=ProjectConfig.cdn_host %>/js/goods_detail.js"></script>
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<span>商品详情</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="scroller">
			<!--详情图-->
			<div class="swiper-container">
			    <div class="swiper-wrapper">
			        <c:forEach items="${goods_detail.goods.photoList }" var="photo">
			           <div class="swiper-slide"><span class="faily_title">特惠</span><img src="${photo }" alt='没有图片'/></div>
			        </c:forEach>
			    </div>
			    <!-- 如果需要分页器 -->
			    <div class="swiper-pagination"></div>
			</div>
			<div class="show">
		    	<div class="title">
		    		<span class="s_title">${goods_detail.goods.goodsName }</span>
		    		<span class="f_title">天天特价，惊喜不间断！天天特价，惊喜不间断！天天特价，惊喜不间断！天天特价，惊喜不间断！</span>
		    	</div>
		    	<div class="q-j">
		    	    <c:if test="${goods_detail.goods.lotsPrice.name eq 'Y' }">
		    	    <c:forEach items="${goods_detail.goods.goodslotPriceList  }" var="goods">
			    	    <span class="s-p">
			    			<em class="price">￥${goods.lotPrice }</em><br>
			    			<em class="j-n">${goods.minQit }-${goods.maxQit }</em>
			    		</span>
		    	    </c:forEach>
		    	    </c:if>
		    	    <c:if test="${goods_detail.goods.lotsPrice.name eq 'N' }">
		    	         <em class="price" style="display:none"><i>平台价:</i> ￥${goods_detail.goods.salePrice }</em>
		    	         <em class="special_price"><i>特价:</i> ￥${goods_detail.goods.salePrice }   <s>104</s></em>
		    	    </c:if>
		    	</div>
		    	<div class="x-q">
		    		<div class="p-p">
		    			<span class="sp"><em class="e_m">销量 : </em><i class="i_i">23</i></span>
		    			<span class="sp"><em class="e_m">起订量 : </em><i class="i_i">${goods_detail.goods.minPurchased }</i></span>
		    		</div>
		    		<div class="p-p">
		    			<span class="sp"><em class="e_m">包装规格 : </em><i class="i_i">${goods_detail.goods.packageSpecific }</i></span>
		    			<span class="sp"><em class="e_m">品牌 : </em><i class="i_i">${goods_detail.goods.brand_detail.brandName }</i></span>
		    		</div>
		    		<div class="p-p">
		    			<span class="sp"><em class="e_m">条形码 : </em><i class="i_i">${goods_detail.goods.barcode }</i></span>
		    			<span class="sp"><em class="e_m">保质期 : </em><i class="i_i">${goods_detail.goods.shelfLife }</i></span>
		    		</div>
		    		<div class="p-p">
		    			<span class="sp"><em class="e_m">退换货 : </em><i class="i_i">${goods_detail.goods.return_policy_desc }</i></span>
		    		</div>
		    	</div>
		    	<a href="<%=contextPath %>/h5/supplier/supplierDetail.do?supplierID=${goods_detail.supplier.id}">
		    	<dl class="company">
		    		<dt><img src="<%=contextPath %>/images/shop.png"></dt>
		    		<dd>
		    			<h4 class="c-title">${goods_detail.goods.supplier_name }</h4>
		    			<p class="c-fw">配送范围：
		    			<c:forEach items="${goods_detail.supplier.shippingAreaList }" var="area">
		    			    ${area.region_desc },
		    			</c:forEach></p>
		    			<p class="c-pp">代理品牌：
		    			<c:forEach items="${goods_detail.supplier.agentBrandList }" var="brand">
		    			    ${brand.brand_name }
		    			</c:forEach></p>
		    		</dd>
		    	</dl>
		    	</a>
		    </div>
		    </div>
		</section>
		<!--主体-->
		<footer class="detail_footer">
			<span class="f-left s_p">
				<a class="dp" href="/h5/supplier/supplierDetail.do?supplierID=${goods_detail.supplier.id}"><label class="icon-group"></label><br>店铺</a>
				<a class="j_favorites"><label class="icon-star-empty"></label><br>收藏</a>
				<a class="shoplist j_shopcar_icon" href="/h5/shoppingCart/gotoShoppingCart.do"><label class="icon-shopping-cart"></label><br>进货单</a>
			</span>
			<button class="addshop s_p" data-addshopcar data-goodsid="${goods_detail.goods.id}">加入进货单</button>
			<!-- <span class="pay">&nbsp;&nbsp;立即订购&nbsp;&nbsp;</span> -->
		</footer>
	</div>
	<!--结束-->
	<%@include file="../common/showShopcartNum.jsp" %>
</body>
</html>