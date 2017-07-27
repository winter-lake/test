
{{each list.salesOrderList}}
<tr>
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
	    <a href="/zz/manager/salesOrder/gotoSalesOrderDetail.do?orderID={{$value.id}}"  target="_blank"><button class="btn btn-info btn-sm" new-status="Cancelled">查看</button></a>
	</td>
</tr>
{{/each}}

{{if (list && list.length == 0) }}
<tr><td colspan="12"><div class="e_noresult">暂无结果</div></td></tr>
{{/if}}