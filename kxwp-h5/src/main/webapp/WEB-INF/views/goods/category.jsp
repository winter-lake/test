<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>商品分类</title>
	<%@include file="../common/share_static.jsp" %>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/category.css?t=<%=VersionUtil.VERSIONNO %>">
</head>
<body>
	<!--开始-->
	<div class="container">
		<!--头部-->
		<header class="header">
			<p class="search">
				<i class="icon-search" id="search"></i><input type="text" name="" placeholder="请输入商品名称" id="goods_name">
			</p>
			<span class="category_search">搜索</span>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="content">
			<div class="main">
				<div class="scroll-left">
					<div class="scroller">
					<ul class="left_menu">
						<script id="left_menu" type="text/html">
						{{each list as value i}}
							<li>
								<a href="javascript:void(0);" data-id='{{value.id}}'>{{value.name}}</a>
							</li>
						{{/each}}
						</script>
					</ul>
					</div>
				</div>
				<div class="scroll-right">
					<div class="scroller">
					<div class="right_main">
					<script id="right_list" type="text/html">
					{{each list as value i}}
					<h6 class="title" parent_id="{{value.id}}">{{value.name}}<em class="go"></em></h6>
					<ol class="right_main" style="list-style:none">
						{{each value.children as child index}}
						<li data_id="{{child.id}}" class="data_id">
						{{child.name}}
						{{/each}}
						</li>
					{{/each}}
					</ol>
					</script>
					</div>
					</div>
				</div>
			</div>
		</section>
		<!--主体-->
		<!--footer-->
		<%@ include file="../common/footer.jsp" %>
		<!--footer-->
	</div>
	<!--结束-->
	<script src="<%=ProjectConfig.cdn_host %>/js/category.js"></script>
</body>
</html>