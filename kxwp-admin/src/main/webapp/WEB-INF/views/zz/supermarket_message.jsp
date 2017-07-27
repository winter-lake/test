<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>超市会员管理页面</title>
	<%@ include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=basePath %>css/zz/ckcs.css">
	<link rel="stylesheet" href="<%=basePath %>css/zz/menu.css">
</head>
<body>

<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>

	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<div class="csck">
				<h4>账户信息</h4>
				<p>
					<span>手机号</span>
					<input type="text" value='${supermarketMessage.accountMobile}'>
				</p>
				<h4>企业信息</h4>
				<p>
					<span>超市名称</span>
					<input type="text" name="" value='${supermarketMessage.supermarketName}'>
				</p>
				<p>
					<span>收货地址</span>
					<select class="xl">
						<option value='${supermarketMessage.province}'>${supermarketMessage.provinceName}</option>
					</select>
					<select class="xl">
						<option value='${supermarketMessage.city}'>${supermarketMessage.cityName}</option>
					</select>
					<select class="xl">
						<option value='${supermarketMessage.county}'>${supermarketMessage.countyName}</option>
					</select>
					<select class="xl">
						<option value='${supermarketMessage.town}'>${supermarketMessage.townName}</option>
					</select>
				</p>
				<p>
					<span>详细地址</span>
					<input type="text" name="" value='${supermarketMessage.full_address}'>
				</p>
				<p>
					<span>收货人</span>
					<input type="text" name="" value='${supermarketMessage.receptionName}'>
				</p>
				<p>
					<span>联系方式</span>
					<input type="text" name="" value='${supermarketMessage.phone}'>
				</p>
				<p>
					<span>服务站</span>
					<input type="text" name="" value='${supermarketMessage.serviceStationName}'>
				</p>
				<p>
					<span>业务员</span>
					<input type="text" name="" placeholder="和新">
				</p>
				<p>
					<span>社会统一信用代码</span>
					<input type="text" name="" value='${supermarketMessage.licenseNum}'>
				</p>
				<p>
					<span>营业执照扫描件</span>
					<i class="img"><img src="../ii.png"></i>
				</p>
				<p>
					<span>身份证扫描件</span>
					<i class="img"><img src="../ii.png"></i>
				</p>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">超市会员管理</a></li>
		  	<li><a href="#">查看会员详情</a></li>
		</ol>
		<!-- <a href="add_menu.html" class="add_menu"><i class="icon-plus"></i>添加会员</a> -->
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
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