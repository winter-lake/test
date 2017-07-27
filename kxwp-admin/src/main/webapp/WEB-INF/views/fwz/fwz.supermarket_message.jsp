<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>超市管理</title>
	<%@ include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=basePath %>css/zz/ckcs.css">
	<link rel="stylesheet" href="<%=basePath %>css/zz/menu.css">
	<link rel="stylesheet" href="<%=basePath %>css/fwz/fwz.supermarket_message.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/leftNav.jsp" %>

	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<h2 class="title">账户信息</h2>
			<c:if test="${reviewDesc!=null}">
				<div class="row">
				  <div class="col-md-2">停用理由 :</div>
				  <div class="col-md-4">${reviewDesc}</div>
				</div>
			</c:if>
			<div class="row">
			  <div class="col-md-2">手机号 :</div>
			  <div class="col-md-4">${supermarketMessage.accountMobile}</div>
			</div>
			<h2 class="title">企业信息</h2>
			<div class="row">
			  <div class="col-md-2">超市名称 :</div>
			  <div class="col-md-4">${supermarketMessage.supermarketName}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">收货地址 :</div>
			  <div class="col-md-4">
			  ${supermarketMessage.provinceName}${supermarketMessage.cityName}${supermarketMessage.countyName}${supermarketMessage.townName}
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">详细地址 :</div>
			  <div class="col-md-4">
			  ${supermarketMessage.full_address}
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">收货人 :</div>
			  <div class="col-md-4">
			  ${supermarketMessage.receptionName}
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">联系方式 :</div>
			  <div class="col-md-4">
			  ${supermarketMessage.phone}
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">服务站 :</div>
			  <div class="col-md-4">
			  ${supermarketMessage.serviceStationName}
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">业务员 :</div>
			  <div class="col-md-4">
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">社会统一信用代码 :</div>
			  <div class="col-md-4">
			  	${supermarketMessage.licenseNum}
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">营业执照扫描件 :</div>
			  <div class="col-md-4">
			  		<img src="<%=contextPath %>/images/share/404.png" alt="未上传" class="img-rounded">
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">身份证扫描件 :</div>
			  <div class="col-md-4">
			  		<img src="<%=contextPath %>/images/share/404.png" alt="未上传" class="img-rounded">
			  </div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">服务站</a></li>
			<li><a href="#">超市管理</a></li>
		  	<li><a href="#">超市详情</a></li>
		</ol>
		<!-- <a href="add_menu.html" class="add_menu"><i class="icon-plus"></i>添加会员</a> -->
	</div>
	<!--面包屑导航结束-->
	<!--footer开始-->
	<%@include file ="../common/footer.jsp" %>
	<!--footer结束-->
</div>	
<!--end-->
<script src="<%=contextPath %>/js/zz/csgl.js"></script>
<script>
	$(function(){
		$("input").attr("disabled",true);
		$("select").attr("disabled",true);
	})
</script>
</body>
</html>