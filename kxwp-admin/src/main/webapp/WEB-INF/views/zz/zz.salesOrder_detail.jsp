<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>总站查看订单详情</title>
<%@include file="../common/share_static.jsp"%>
<!--第三方end-->
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/zz/salesOrder_detail.css">

</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@include file="../common/header.jsp"%>
		<%@include file="../common/leftNav.jsp"%>
		<!--左边结束-->
		<!--右边开始-->
		<div class="k_right">
		<div class="content">
			<div class="row" id="row">
			  <div class="col-md-4">
			  		<h6 class="title">订单信息</h6>
			  		<p class="d-p">
			  			<span class="d-s">订单号 :</span>
			  			<em class="d-e">${salesOrder_detail.orderNo}</em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">下单时间 :</span>
			  			<em class="d-e"><fmt:formatDate value="${salesOrder_detail.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">供应商 :</span>
			  			<em class="d-e">${salesOrder_detail.supplierName}</em>
			  		</p>
			  		<p class="d-p">
						<span class="d-s">超市留言 :</span> 
						<em class="d-e"><font color = "red">${salesOrder_detail.orderMessage}</font></em>
					</p>
			  </div>
			  <div class="col-md-4">
			  		<h6 class="title">收货信息</h6>
			  		<p class="d-p">
			  			<span class="d-s">收货超市 :</span>
			  			<em class="d-e">${salesOrder_detail.supermarketName}</em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">收货人 :</span>
			  			<em class="d-e">${salesOrder_detail.receptionName}</em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">联系方式 :</span>
			  			<em class="d-e">${salesOrder_detail.phone}</em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">收货地址 :</span>
			  			<em class="d-e">${salesOrder_detail.full_address}</em>
			  		</p>
			  </div>
			  <div class="col-md-4">
			  		<h6 class="title">付款信息</h6>
			  		<p class="d-p">
			  			<span class="d-s">支付方式 :</span>
			  			<em class="d-e">${salesOrder_detail.payMethod_desc}</em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">支付时间 :</span>
			  			<em class="d-e"><fmt:formatDate value="${salesOrder_detail.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">商品金额 :</span>
			  			<em class="d-e"><font color ="red">￥${salesOrder_detail.orderAmount}元</font></em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">应付金额 :</span>
			  			<em class="d-e"><font color ="red">￥${salesOrder_detail.orderAmount}元</font></em>
			  		</p>
			  		<p class="d-p">
			  			<span class="d-s">应收金额 :</span>
			  			<em class="d-e"><font color ="red">￥${salesOrder_detail.payAmount}元</font></em>
			  		</p>
			  </div>
			</div>
			<div class="table-responsive">
				<h5 class="t-title">商品信息</h5>
				<!--table-->
				<table class="table table-bordered" style="text-align:center">
				  	<thead style="background:#eee">
						<tr>
							<td>序号</td>
							<td>商品名称</td>
							<td>商品图片</td>
							<td>包装规格</td>
							<td>销售价格</td>
							<td>数量</td>
							<td>商品金额</td>
							<td>订单状态</td>
						</tr>
					</thead>
					<tbody>
						
							<c:forEach items="${salesOrder_detail.orderItemList }" var="orderItem"	varStatus="status">
							<tr>
							<td>${status.index+1}</td>
							<td>${orderItem.goodsName}</td>
							
					 		<!-- 显示商品图片 -->
							<c:if test="${ not empty orderItem.photoList }">
							<td>
								<img style = "width:120px;height:80px" alt="商品 图片" src="${orderItem.photoList[0]}">
							</td>
							</c:if> 
							
							<c:if test="${empty orderItem.photoList }">
							<td>
								<img style = "width:120px;height:80px" alt="暂无 图片" >
							</td>
							</c:if>
							
							<td>${orderItem.packageSpecific}</td>
							<td>${orderItem.salePrice}元</td>
							<td>${orderItem.itemQit}</td>
							<td>${orderItem.itemQit*orderItem.salePrice}元</td>
							<td>${salesOrder_detail.orderStatus_desc}</td>
							</tr>
							</c:forEach>
						
					</tbody>
				</table>
				<div class="price">
				    <p class="p-p">
			  			<span class="d-s">商品总价 :</span>
			  			<em class="d-e"><font color ="red">￥${salesOrder_detail.orderAmount}</font></em>
			  		</p>
			  		<p class="p-p">
			  			<span class="d-s">应收金额 :</span>
			  			<em class="d-e"><font color ="red">￥${salesOrder_detail.payAmount}</font></em>
			  		</p>
				</div>
			</div>
		</div>	
	</div>
	<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background: #fff">
				<li><a href="#">总站</a></li>
				<li><a href="#">订单管理</a></li>
				<li><a href="#">订单详情</a></li>
			</ol>
			<a href="<%=contextPath %>/zz/manager/salesOrder/gotolistSalesOrder.do" class="add_menu">订单列表</a>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp"%>
		<!--footer结束-->
	</div>
	<!--end-->
	<script src="<%=contextPath%>/js/share/goods_detail.js"></script>
</body>
</html>