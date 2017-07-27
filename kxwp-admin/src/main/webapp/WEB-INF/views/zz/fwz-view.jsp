<%@ page language="java" import="java.util.*,com.kxwp.admin.entity.serviceStation.ServiceStation" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>服务站查看查看</title>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/zz/fwz-add.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/zz/fwz_view.css">
	<%@include file="../common/share_static.jsp" %>
	<% ServiceStation data = (ServiceStation)request.getAttribute("zzDetail"); %>
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<h2 class="title">账户信息</h2>
			<div class="row" style="margin-left: 300px;">
			  <div class="col-md-2">负责人 :</div>
			  <div class="col-md-4">${zzDetail.serviceStationAccount.name }</div>
			</div>
			<div class="row hide" style="margin-left: 300px;">
			  <div class="col-md-2">绑定手机号 :</div>
			  <div class="col-md-4">${zzDetail.serviceStationAccount.mobile }</div>
			</div>
			<div class="row hide" style="margin-left: 300px;">
			  <div class="col-md-2">密码 :</div>
			  <div class="col-md-4"><i>密码会以手机短信的方式发送到您的手机上</i></div>
			</div>
			<h2 class="title">服务信息</h2>
			<div class="row" style="margin-left: 300px;">
			  <div class="col-md-2">服务站名称 :</div>
			  <div class="col-md-4">${zzDetail.serviceStationName }</div>
			</div>
			<div class="row" style="margin-left: 300px;">
			  <div class="col-md-2">客服电话 :</div>
			  <div class="col-md-4">${zzDetail.phone }</div>
			</div>
			<div class="row hide" style="margin-left: 300px;">
			  <div class="col-md-2">类型 :</div>
			  <div class="col-md-4" id="fwzType"></div>
			</div>
			<div class="row" style="margin-left: 300px;">
			  <div class="col-md-2">服务区域 :</div>
			  <div class="col-md-4">${zzDetail.province_desc }${zzDetail.city_desc}<c:forEach items="${regionList }" var="region">${region.county_desc }</c:forEach></div>
			</div>
			<div class="row" style="margin-left: 300px;">
			  <div class="col-md-2">平台使用费 :</div>
			  <div class="col-md-4">${zzDetail.platformFee}</div>
			</div>
			<div class="row" style="margin-left: 300px;">
			  <div class="col-md-2">保证金 :</div>
			  <div class="col-md-4">${zzDetail.platformFee}</div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">服务站管理</a></li>
		  	<li><a href="#">查看服务站</a></li>
		</ol>
		<a href="<%=basePath%>zz/manager/fwz/listFWZ.do" class="add_menu">服务站列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
</body>
<script type="text/javascript">
	$(function(){
	//初始化资源状态
		$.ajax({
		    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=FWZTypeEnum",
		    type:'post',
		    dataType:'json',
		    success:function(data){
		        render_FWZType(data);
		    }
		})
		function render_FWZType(data){
			var result = '';
			
		    $.each(data.data,function(i,val){
		    	result = val.desc;
		    })
		    
		    $('#fwzType').text(result);
		}
	});
</script>
</html>