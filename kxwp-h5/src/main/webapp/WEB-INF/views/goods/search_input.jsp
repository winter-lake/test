<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>搜索输入页</title>
	  <%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/search_input.css?t=<%=VersionUtil.VERSIONNO %>">
	<script src="<%=ProjectConfig.cdn_host %>/js/search_input.js"></script>
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<%@include file="../common/back.jsp" %>
			<p class="val">
				<select id="select_val">
					<option value="商品">商品</option>
					<option value="供应商">供应商</option>
				</select>
				<input type="text" name="" id="search_val" placeholder="搜索商品或供应商">
			</p>
			<a href="javascript:;" class="search" id="search">搜索</a>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="hot-search">
				<h4 class="title">热门搜索</h4>
				<div class="hot-list">
					<a href="/h5/goods/searchGoodsProxy.do?goods_name=屈臣氏">屈臣氏</a>
					<a href="/h5/goods/searchGoodsProxy.do?goods_name=百岁山">百岁山</a>
					<a href="/h5/goods/searchGoodsProxy.do?goods_name=哈尔滨">哈尔滨</a>
					<a href="/h5/goods/searchGoodsProxy.do?goods_name=喜之郎">喜之郎</a>
					<a href="/h5/goods/searchGoodsProxy.do?goods_name=湾仔码头">湾仔码头</a>
					<!-- <a href="javascript:;">哇奋斗奋斗哈哈</a>
					<a href="javascript:;">哇哈哈</a>
					<a href="javascript:;">哇哈奋斗奋斗哈</a>
					<a href="javascript:;">哇哈哈</a>
					<a href="javascript:;">哇对方地方哈哈</a> -->
				</div>
				<!-- <h4 class="title">搜索历史</h4>
				<div class="his-list">
					<p class="p_list">可口可乐</p>
					<p class="p_list">金银花露</p>
				</div>
				<p class="clear" id="clear">清空搜索历史</p>  -->
			</div>
		</section>
		<!--主体-->
	</div>
	<!--结束-->
</body>
</html>