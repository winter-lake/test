<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>供应商详情</title>
      <%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=contextPath %>/css/gys/gys_detail.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<!--头部开始-->
	<%@ include file="../common/header.jsp"%>
	<!--头部结束-->
	<!--左边开始-->
    <%@ include file="../common/leftNav.jsp"%>
	<!--左边结束-->
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<h2 class="title">账户信息</h2>
			<div class="row">
			  <div class="col-md-2">负责人 :</div>
			  <div class="col-md-4">${gys_detail.supplierAdmin }</div>
			</div>
			<div class="row">
			  <div class="col-md-2 ">负责人手机号 :</div>
			  <div class="col-md-4">${gys_detail.supplierAdminMobile }</div>
			</div>
			<h2 class="title">企业信息</h2>
			<div class="row">
			  <div class="col-md-2">企业名称 :</div>
			  <div class="col-md-4">${gys_detail.supplierName }</div>
			</div>
			<div class="row">
			  <div class="col-md-2">企业法人 :</div>
			  <div class="col-md-4">${gys_detail.legalPerson }</div>
			</div>
			<div class="row">
			  <div class="col-md-2">业务员 :</div>
			  <div class="col-md-4"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">固定电话 :</div>
			  <div class="col-md-4">${gys_detail.service_phone }</div>
			</div>
			<div class="row">
			  <div class="col-md-2">供货商地址:</div>
			  <div class="col-md-4">${gys_detail.full_address }</div>
			</div>
			<h2 class="title">服务信息</h2>
			<div class="row">
			  <div class="col-md-2">供货商配送范围:</div>
			  <div class="col-md-4">
				  	<div class="Street">
				  	    <c:forEach items="${shipping_area_list }" var="area">
				  	       <span class="s-treet">${area.region_desc }</span>
				  	    </c:forEach>
				  	</div>
			  	</div>
			</div>
			
			<div class="row">
			  <div class="col-md-2">经营范围:</div>
			  <div class="col-md-4">
			    <c:forEach items="${gys_detail.businessScopeList }" var="business">
				    <label class="checkbox-inline">
					  <input type="checkbox" id="inlineCheckbox1" value="option1" disabled="disabled"> ${business.category_name }
					</label>
			    </c:forEach>
			  </div>
			</div>
			<div class="row hide">
			  <div class="col-md-2">供货商地址:</div>
			  <div class="col-md-4">${gys_detail.full_address }</div>
			</div>
			<div class="row">
			  <div class="col-md-2">代理品牌:</div>
			  <div class="col-md-4">
			    <c:forEach items="${gys_detail.agentBrandList }" var="brand">
			       <button type="button" class="btn btn-success">${brand.brand_name }</button>
			    </c:forEach>
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-2">社会统一信用代码:</div>
			  <div class="col-md-4">${gys_detail.licenseNum }</div>
			</div>
			<div class="row">
			  <div class="col-md-2">营业执照扫描件:</div>
			  <div class="col-md-4"><img src="<%=contextPath %>/images/share/404.png" alt="未上传" class="img-rounded"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">身份证号:</div>
			  <div class="col-md-4">${gys_detail.identityCardNum }</div>
			</div>
			<div class="row">
			  <div class="col-md-2">身份证扫描件:</div>
			  <div class="col-md-4"><img src="<%=contextPath %>/images/share/404.png" alt="未上传" class="img-rounded"></div>
			</div>
			<div class="row hide">
			  <div class="col-md-3"><button type="button" class="btn btn-success">确认</button></div>
			  <div class="col-md-3"><button type="button" class="btn btn-primary">返回</button>
</div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">服务站</a></li>
		  	<li><a href="#">供应商管理</a></li>
		  	<li><a href="#">供应商详情</a></li>
		</ol>
		<a href="<%=contextPath %>/fwz/manager/gys/gysList.do" class="add_menu"><i class="icon-plus"></i>供应商列表</a>
	</div>
	<!--面包屑导航结束-->
	<!--footer开始-->
	<%@include file="../common/footer.jsp"%>
	<!--footer结束-->
</div>	
<!--end-->
</body>
</html>




































