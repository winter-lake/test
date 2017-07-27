<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>复制商品</title>
<%@ include file="../common/share_static.jsp"%>
<link rel="stylesheet" href="<%=contextPath%>/css/zz/cs.css">
<link rel="stylesheet" href="<%=contextPath%>/css/zz/menu.css">


</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftNav.jsp"%>

		<!--右边开始-->
		<div class="k_right">
			<div class="content">
				<!--查询等功能-->
				<form class="k_change j_search_form" method="post" action="">
					<b class="k-b"> <i>从&nbsp;供应商</i> <select id="copyed_supplier"
						name="copyed_supplier"></select>
					</b> <b class="k-b"> <i>&nbsp;复制到&nbsp;供应商</i> <select
						id="supplier" name="supplier"></select>
					</b> <input type="button" class="Inquire j_search_btn" value="复制">
				</form>
			</div>
		</div>
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background: #fff">
				<li><a href="#">服务站</a></li>
				<li><a href="#">商品管理</a></li>
				<li><a href="#">复制商品</a></li>
			</ol>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp"%>
		<!--footer结束-->
	</div>
	<!--end-->
	<script src="<%=contextPath%>/js/zz/goods/copy.js"></script>
	<script type="text/javascript">
		seajs.use("zz/goods/copy", function(view) {
			view.init();
		});
	</script>
</body>
</html>