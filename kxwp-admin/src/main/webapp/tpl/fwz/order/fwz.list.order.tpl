{{each list.fwz_salesOrderList}}
					<tr>
						<td><input type="checkbox" value="{{$value.bigOrderNo}}" class="j_checkbox_item"></td> 
						<td colspan="3">订单编号：{{$value.bigOrderNo}}</td>
						<td colspan="4">下单时间：{{$value.orderTime}}</td>
						<td colspan="4">收货超市：{{$value.supermarketName}}</td>
						<td>
							<button type="button" class="btn btn-info">打印订单</button>
						</td>
					</tr>
			{{each $value.salesOrderList}}	
				<tr>	
					<td></td>
					<td>{{$index+1}}</td>
					<td>{{$value.orderNo}}</td>
					<td>{{$value.receptionName}}</td>
					<td>{{$value.phone}}</td>
					<td>{{$value.payMethod_desc|| "--"}}</td>
					<td>{{$value.orderAmount}}</td>
					<td>{{$value.payAmount}}</td>
					<td>{{$value.orderStatus_desc|| "--"}}</td>
					<td>{{$value.orderTime}}</td>
					<td>{{$value.comfirmedTime ||"--"}}</td>
					<td>{{$value.supplierName}}</td>
					<td>
						&nbsp;<a href="/fwz/manager/salesOrder/gotoSalesOrderDetail.do?orderID={{$value.id}}" target="_blank"><button class="btn btn-primary">查看订单</button></a>
	    				{{if $value.orderStatus == 'Awaiting_Shipping'}}
	    					<button type="button" class="j_update_goods btn btn-danger" new-status="Cancelled" order-id="{{$value.id}}">取消订单</button>
	     				{{/if}}
					</td>
				</tr>	
			{{/each}}
{{/each}}

{{if (list.fwz_salesOrderList && list.fwz_salesOrderList.length == 0) }}
<tr><td colspan="13"><div class="e_noresult">暂无结果</div></td></tr>
{{/if}}
