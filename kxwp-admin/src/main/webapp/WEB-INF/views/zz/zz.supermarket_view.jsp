<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>会员查看</title>
	<%@ include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=basePath %>css/zz/ckcs.css">
	<link rel="stylesheet" href="<%=basePath %>css/zz/zz.supermarket_view.css">
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
			  <div class="col-md-4">${supermarketMessage.provinceName}${supermarketMessage.cityName}${supermarketMessage.countyName}${supermarketMessage.townName}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">详细地址 :</div>
			  <div class="col-md-4">${supermarketMessage.full_address}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">收货人 :</div>
			  <div class="col-md-4">${supermarketMessage.receptionName}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">联系方式 :</div>
			  <div class="col-md-4">${supermarketMessage.phone}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">服务站 :</div>
			  <div class="col-md-4">${supermarketMessage.serviceStationName}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">业务员 :</div>
			  <div class="col-md-4">${supermarketMessage.serviceStationName}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">社会统一信用代码 :</div>
			  <div class="col-md-4">${supermarketMessage.licenseNum}</div>
			</div>
			<div class="row">
			  <div class="col-md-2">营业执照扫描件 :</div>
			  <div class="col-md-4"><i class="img"><img src="../ii.png"></i></div>
			</div>
			<div class="row">
			  <div class="col-md-2">营业执照扫描件 :</div>
			  <div class="col-md-4"><i class="img"><img src="../ii.png"></i></div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
			<li><a href="#">超市管理</a></li>
		  	<li><a href="#">超市查看</a></li>
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
	$(".tgsq").click(function(e){
		e.preventDefault();
		layer.confirm('确认通过申请？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		  $(".cssh").submit();
		})
	})
 	$(".jjsq").click(function(){
		layer.open({
		  type: 1,
		  skin: 'layui-layer-demo', //样式类名
		  closeBtn: 0, //不显示关闭按钮
		  shift: 2,
		  shadeClose: true, //开启遮罩关闭
		  content: '<form action="updateSupermarketStatusOn.do" method="post"> <input type="hidden" name="supermarketStatus" value="REJECT" ><textarea name="statusUpdateDescription"></textarea><input type="hidden" value="${supermarketMessage.id}" name="id"><button type="submit">确认</button><button>取消</button></form>'
		});
	}) 
</script>
</body>
</html>