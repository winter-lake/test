{{each list}}
<tr>
	<td><input type="checkbox" value="{{$value.id}}" class="j_checkbox_item"></td>
	<td>{{$index+1}}</td>
	<td>{{$value.id + '/' + $value.goodsNo}}</td>
	<td data-toggle="tooltip" data-placement="bottom" title="{{$value.goodsName}}">{{$value.goodsName}}</td>
	<td>{{$value.brand_detail.brandName}}</td>
	<td>{{$value.barcode}}</td>
	<td>{{$value.minPurchased}}</td>
	<td>{{$value.salePrice}}</td>
	<td>{{$value.createTime}}</td>
	<td>{{$value.goodsStatus_desc || "--"}}</td>
	<td>
	    <a class="btn btn-link" href="/gys/goods/gotoGoodsDetail.do?goodsID={{$value.id}}" role="button" target="_blank">查看</a>
	   {{if $value.goodsStatus == 'ONSALE'}}<button type="button" class="j_update_goods btn btn-danger btn-sm" new-status="OFFSALE" goods-id="{{$value.id}}" version-no="{{$value.versionNO}}">下架</button> {{/if}}
	   {{if $value.goodsStatus != 'ONSALE'}}<a class=" btn btn-link" href="/gys/goods/gotoEditGoods.do?goodsID={{$value.id}}" role="button">编辑</a>{{/if}}
	   {{if $value.goodsStatus == 'NEW' || $value.goodsStatus == 'OFFSALE' || $value.goodsStatus == 'REJECT'}}<button type="button" class="j_update_goods btn btn-warning btn-sm" new-status='REVIEWING' goods-id="{{$value.id}}" version-no="{{$value.versionNO}}">提审</button>{{/if}}
	</td>
</tr>
{{/each}}
{{if (list && list.length == 0) }}
<tr><td colspan="11"><div class="e_noresult">暂无结果</div></td></tr>
{{/if}}