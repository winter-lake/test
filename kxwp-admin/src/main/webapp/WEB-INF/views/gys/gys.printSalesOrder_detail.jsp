<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>打印发货单</title>
<%@include file="../common/share_static.jsp"%>
<!--第三方end-->
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/print/print.css">
</head>
<body>
	<div class="p_print_box">
		<div class="pr_ship_order tx_c">
			<div class="row row1">
				<div class="col col1">
					<img src="<%=contextPath%>/images/print/kxlogo.jpg">
				</div>
				<div class="col col3">
					<b>订单编号:${printSalesOrder_detail.salesOrder.orderNo}</b>
				</div>
				<div class="col col2 tx_md" style="width:2.5rem;line-height:1.2rem">
					<b>供应商客服电话</b>	
				</div>
				<div class="col col2 tx_md">
						${printSalesOrder_detail.supplier.service_phone}
				</div>
				<div class="col col4 tx_date" style="width:2rem"><b>打印时间:</b></div>
				<div class="col col4 tx_date no_border_r">
					<fmt:formatDate value="${printSalesOrder_detail.printDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</div>
			</div>
			<!-- 第一行 -->
			<!-- 第二行 -->
			<div class="row row2">
				<!-- key value -->
				<div class="col key_w">
					<b>收货人</b>
				</div>
				<div class="col value_w">${printSalesOrder_detail.salesOrder.receptionName}</div>
				<!-- key value -->
				<div class="col key_w" style="width: 2rem;">
					<b>联系方式</b>
				</div>
				<div class="col value_w">${printSalesOrder_detail.salesOrder.phone}</div>
				<div class="col key_w">
					<b>超市</b>
				</div>
				<div class="col value_w w_multi_tx" style="width: 2.5rem;">
					<div>${printSalesOrder_detail.salesOrder.supermarketName}</div>
				</div>
				<div class="col key_w">
					<b>地址</b>
				</div>
				<div class="value_w  w_multi_tx" style="width: 6.8rem;">
					<div
						style="text-align: center;padding-top:.3rem;">${printSalesOrder_detail.salesOrder.print_address}</div>
				</div>
			</div>
			<!-- 第三行 -->
			<div class="row row3">
				<!-- key value -->
				<div class="col key_w">
					<b>供应商</b>
				</div>
				<div class="col value_w" style="width: 7rem;">${printSalesOrder_detail.supplier.supplierName}</div>
				<!-- key value -->
				<div class="col key_w">
					<b>电话</b>
				</div>
				<div class="col value_w w_multi_tx" style="width: 2.5rem;">
					<div>${printSalesOrder_detail.supplier.phone}</div>
				</div>
				<div class="col key_w">
					<b>地址</b>
				</div>
				<div class="col value_w no_border_r w_multi_tx"
					style="width: 5.8rem;">
					<div>${printSalesOrder_detail.supplier.full_address}</div>
				</div>
			</div>
			<div class="row row4 no_border_b">
				<div class="col col1 no_border_r" style="padding: 0">
					<table style="float: left; border-top: none !important">
						<thead>
							<tr>
								<th>序号</th>
								<th>商品条码</th>
								<th>商品名称</th>
								<th>规格</th>
								<th>单位</th>
								<th>单价</th>
								<th>数量</th>
								<th>金额</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach
								items="${printSalesOrder_detail.salesOrder.orderItemList}"
								var="log" varStatus="index">
								<tr>
									<td>${index.index+1}</td>
									<td>${log.barcode}</td>
									<td>${log.goodsName}</td>
									<td>${log.packageSpecific}</td>
									<td>${log.packageSpecificUnit}</td>
									<td>${log.salePrice}</td>
									<td>${log.itemQit}</td>
									<td>${log.itemQit*log.salePrice}元</td>
									<td></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
		</div>
		<%-- <div class="row row5">
			<div class="col key_w">
				<b>留言</b>
			</div>
			<div class="col no_border_r">
				<div class="total">
					<span>${printSalesOrder_detail.salesOrder.orderMessage}</span>
				</div>
			</div>
		</div> --%>

		<div class="row row5">
			<div class="col key_w">
				<b>合计</b>
			</div>
			<div class="col no_border_r">
				<div class="total">
					<span>商品总价：<span class="tx_money">￥${printSalesOrder_detail.salesOrder.orderAmount}元</span></span>
					<span>实付金额：<span class="tx_money">￥${printSalesOrder_detail.salesOrder.payAmount}元</span></span>
				</div>
			</div>
		</div>
		<!-- 第五行 -->
		<!-- 第六行 -->
		<div class="row row6">
			<!-- key value -->
			<div class="col key_w">
				<b>制表人</b>
			</div>
			<div class="col value_w"></div>
			<!-- key value -->
			<div class="col key_w" style="width: 1.5rem;">
				<b>送货人</b>
			</div>
			<div class="col value_w"></div>
			<div class="col key_w" style="width: 1.5rem;">
				<b>收货人</b>
			</div>
			<div class="col value_w">
				<div></div>
			</div>
			<div class="col key_w"
				style="width: 1.5rem;">
				<b>备注</b>
			</div>
		</div>
		<!-- 第六行 -->
		<!-- 第七行 -->
		<!-- <div class="row row5">
			<div class="col key_w">
				<b>备注</b>
			</div>
			<div class="col no_border_r" style="width: 18rem;">
				这里的备注可以是服务站，供应商</div>
		</div> -->
		<!-- 第七行 -->
		<!-- 第八行 -->
		<%-- <div class="row row6 no_border_b">
			<!-- key value -->
			<div class="col key_w" style="width: 3rem;">
				<b>下单时间</b>
			</div>
			<div class="col value_w" style="width: 7rem;">
				<fmt:formatDate
					value="${printSalesOrder_detail.salesOrder.orderTime}"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</div>
			<!-- key value -->
			<div class="col key_w" style="width: 3rem;">
				<b>打印时间</b>
			</div>
			<div class="col value_w" style="width: 7rem; border-right: none">
				<fmt:formatDate value="${printSalesOrder_detail.printDate}"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</div> --%>
			<!-- 第八行 -->
			<!-- 第九行 -->
			<div class="row row7 no_border_b">
				<div class="col col" style="border-left: 0.43mm solid #000;">
				<b>白联</b>	 存根</div>
				<div class="col col"><b>粉联</b>  客户</div>
				<div class="col col" style="border-right: 0.43mm solid #000">
					<b>黄联</b>  记账</div>
			</div>
		</div>
	</div>
	</div>
	    <div class="container no-printer">
		  <div class="row">
		  	<div class="col-md-12" style="text-align: center;"><button class="btn btn-info btn-sm" onclick="window.print()">打印</button> <button class="btn btn-info btn-sm" onclick="window.close()">关闭窗口</button></div>
		  </div>
		</div>
	</body>
</html>










