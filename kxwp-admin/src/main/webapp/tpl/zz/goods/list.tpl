{{each list}}
<tr>
	<td><input type="checkbox" value="{{$value.id}}" class="j_checkbox_item"></td>	
	<td>{{$value.id + '/' + $value.goodsNo}}</td>
	<td data-toggle="tooltip" data-placement="bottom" title="{{$value.goodsName}}">{{$value.goodsName}}</td>
	<td>{{$value.brand_detail.brandName}}</td>
	<td>{{$value.barcode}}</td>
	<td>{{$value.serviceStationId}}</td>
	<td>{{$value.supplierId}}</td>
	<td>{{$value.minPurchased}}</td>
	<td>{{$value.salePrice}}</td>
	<td>{{$value.createTime}}</td>
	<td>{{$value.goodsStatus_desc || "--"}}</td>
	<td>
	    <a class="btn btn-link" href="/zz/goods/gotoGoodsDetail.do?goodsID={{$value.id}}" role="button">查看</a>
	</td>
</tr>
{{/each}}
{{if (list && list.length == 0) }}
<tr><td colspan="12"><div class="e_noresult">暂无结果</div></td></tr>
{{/if}}