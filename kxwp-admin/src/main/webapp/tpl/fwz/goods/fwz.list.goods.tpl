{{each list}}
<tr>
	<!-- <td><input type="checkbox"></td> -->
	<td>{{$index+1}}</td>
	<td>{{$value.id + '/' + $value.goodsNo}}</td>
	<td data-toggle="tooltip" data-placement="bottom" title="{{$value.goodsName}}">{{$value.goodsName}}</td>
	<td>{{$value.brand_detail.brandName}}</td>
	<td>{{$value.barcode}}</td>
	<td>{{$value.supplier_name}}</td>
	<td>{{$value.minPurchased}}</td>
	<td>{{$value.salePrice}}</td>
	<td>{{$value.createTime}}</td>
	<td>{{$value.goodsStatus_desc || "--"}}</td>
	<td>
	    <a class="btn btn-link" href="/fwz/goods/gotoGoodsDetail.do?goodsID={{$value.id}}" role="button" target="_blank">查看</a>}} 
	   {{if $value.goodsStatus == 'ONSALE'}}<a class=" btn btn-danger btn-sm" href="/fwz/goods/gotoGoodsReview.do?goodsID={{$value.id}}" role="button">下架</a>  {{/if}}
	   {{if $value.goodsStatus == 'REVIEWING'}}<a class=" btn btn-info" href="/fwz/goods/gotoGoodsReview.do?goodsID={{$value.id}}" role="button" target="_blank">审核</a>{{/if}}
	</td>
</tr>
{{/each}}
{{if (list && list.length == 0) }}
<tr><td colspan="12"><div class="e_noresult">暂无结果</div></td></tr>
{{/if}}