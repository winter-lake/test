<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<%@include file="../common/share_static.jsp" %>
	<title>收藏夹</title>
	<link rel="stylesheet" href="<%=ProjectConfig.cdn_host %>/css/Favorites.css">
</head>
<body>
	<div class="j_container">
		<!-- 头部 -->
		<header class="header">
			<a class="back"></a>
			<ul class="tab">
				<li class="cur">商品</li>
				<li>供应商</li>
			</ul>
		</header>
		<!-- 内容 -->
		<section class="j_content">
			<div class="message_box">
				
			</div>
		</section>
		<!-- 购物车 -->
		<a href="/h5/shoppingCart/gotoShoppingCart.do" class="shopcar j_shopcar_icon"></a>
	</div>
	<!-- 引入模板 -->
	<%@include file="../../../tpl/Favorites.tpl" %>
</body>
</html>