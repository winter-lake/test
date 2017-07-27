<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/html" id="order_list_tpl">
	{{each list as value i}}
	<div class="list">
		<div class="old_order"><span class="order_num">订单号 : {{value.bigOrderNo}}</span><span class="order_time"></span><a class="order_list" href="/h5/v2/order/gotoMyOrderItem.do?bigOrderNo={{value.bigOrderNo}}">商品清单 <i class="icon-angle-right"></i></a></div>
		{{each value.salesOrderList as supplier_order j}}
		   	<h4 class="s-title">{{supplier_order.supplierName}}<em class="status">{{supplier_order.orderStatus_desc}}</em></h4>
		{{each supplier_order.orderItemList as goods j}}
		<dl class="s-dl">
			<dt><a href="/h5/goods/goodsDetail.do?id={{goods.goodsId}}"><img src="{{goods.photoList[0]}}" onerror="this.src='../../../images/404.png'"></a></dt>
			<dd>
				<h4 class="d-title">{{goods.goodsName}}</h4>
				<p class="d-gg">规格：{{goods.packageSpecific}}</p>
				<p class="price-num">
					<span class="price">￥ {{goods.salePrice}}</span>
					<span class="number">X{{goods.itemQit}}</span>
				</p>
			</dd>
		</dl>
		{{/each}}
		<p class="z-number-price">
			<span>共<i>{{supplier_order.goodsCount}}件</i> <em class="zzz-price">￥ {{supplier_order.orderAmount}}</em></span>
			<a href="/h5/order/getOrderDetail.do?orderID={{supplier_order.id}}" id="order"><span class="view_list btn">查看订单</span>{{if supplier_order.orderStatus == 'Awaiting_Receive' }}<span class="d_sh btn j_confirm_btn" data-id="{{supplier_order.id}}">确认收货</span>{{/if}}</a>
		</p>
		{{/each}}
		
	</div>
	{{/each}}
</script>