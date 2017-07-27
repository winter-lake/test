
{{each list.salesOrderList}}
<tr>
	<td><input type="checkbox" value="{{$value.id}}" class="j_checkbox_item"></td>
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
	<td>
	    &nbsp;<a href="/gys/manager/salesOrder/gotoSalesOrderDetail.do?orderID={{$value.id}}" target="_blank"><button class="btn btn-info btn-sm"  new-status="Awaiting_Receive">查看</button></a>
	    {{if $value.orderStatus == 'Awaiting_Shipping'}}
	    	<button type="button" class="j_update_goods btn btn-info btn-sm" new-status="Awaiting_Receive" order-id="{{$value.id}}">发货</button> <br>
	    	<button type="button" class="j_adjust_price btn btn-info btn-sm" data-orderid="{{$value.id}}" data-orderno="{{$value.orderNo}}" data-ori_price="{{$value.payAmount}}">调整价格</button>
	    	<button type="button" class="j_update_goods btn btn-danger btn-sm" new-status="Cancelled" order-id="{{$value.id}}">取消订单</button> 
	    {{/if}}
	</td>
</tr>
{{/each}}

{{if (list && list.length == 0) }}
<tr><td colspan="12"><div class="e_noresult">暂无结果</div></td></tr>
{{/if}}