<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>品牌详情</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="/css/zz/brand_detail.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp"%>
	<%@include file="../common/leftNav.jsp"%>
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
		<h2 class="title">品牌信息</h2>
			<div class="row">
			  <div class="col-md-2">品牌名称 :</div>
			  <div class="col-md-4">${msBrand.brandName}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">品牌slogan :</div>
			  <div class="col-md-4">${msBrand.describtion}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">图片略缩图 :</div>
			  <c:if test="${msBrand.photourl==''}">
			  	<div class="col-md-4"><img id="brandlogo" style="width:300px;height:150px;" src="<%=contextPath %>/images/share/404.png"></div>
			  </c:if>
			  <c:if test="${msBrand.photourl!=''}">
				<div class="col-md-4"><img id="brandlogo" style="width:300px;height:150px;" src="${msBrand.photourl}"></div>
			  </c:if>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">品牌管理</a></li>
		  	<li><a href="#">品牌详情</a></li>
		</ol>
		<a href="/zz/ssBrand/queryBrandInit.do" class="add_menu">品牌列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<script>
$("#comeback").click(function(){
	window.location.href="/zz/ssBrand/queryBrandInit.do";
});
</script>
</body>
</html>