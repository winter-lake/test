<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>商品搜索列表</title>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/search_goods.css?t=<%=VersionUtil.VERSIONNO %>">
  <%@include file="../common/share_static.jsp" %>
</head>
<body>
	<!-- 滑动菜单 -->
	<div class="slider">
		<div class="s_header">
			<a class="s_back"></a>
			<span>筛选品牌</span>
			<a class="s_sure" id="s_sure">确定</a>
		</div>
		<div class="s_content">
			<div class="s-category">
				<span class="t_j">推荐</span>
			</div>
			<div class="s_main">
				<script id="tpl_category_list" type="text/html">
					<div class="s_good">
					{{each list as value i}}
						
					{{/each}}
					</div>
				</script>
			</div>
		</div>
	</div>
	
	<div class="j_container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<p class="val"><input type="text" name="goods_name" placeholder="输入商品名称" value="${param.goods_name }"></p>
			<a href="javascript:void(0);" class="search" id="j_search_btn">搜索</a>
		</header>
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
		<!-- 搜索参数 -->
		<input type="hidden" name="category_id_list" id="category_id_list" value="${param.category_id_list }"/>
		<input type="hidden" name="brand_id_list" id="brand_id_list" value="${param.brand_id_list }"/>
		<input type="hidden" name="goods_name" id="goods_name" value="${param.goods_name }"/> 
		<!--头部-->
		<!--主体-->
		<section id="content" style="overflow-y: auto;" class="j_content">
	        <!-- 商品列表 -->
			<div class="sp">
				<div class="sp-list j_list_box"></div>
			</div>
	        <!-- 商品列表 -->
		</section>
		<!--主体-->
		<!--search-footer-->
		<footer class="search-footer j_total_car">
			<span class="foot">数量 :<i class="z_num j_total_numbers">0</i></span>
			<span class="foot">价格 :<i class="rmb">￥ <span class="j_total_price">0</span></i></span>
			<span class="addlist j_add_submit">加入进货单</span>
		</footer>
		<!--footer-->
		<a href="/h5/shoppingCart/gotoShoppingCart.do" class="shopcar j_shopcar_icon">

		</a>
	</div>
	<!-- 引入模板 -->
	<%@include file="../../../tpl/goods_list.tpl" %>
	<!-- 引入模板 -->
	<script src="<%=ProjectConfig.cdn_host %>/js/search_goods_list.js"></script>
</body>
</html>




				 