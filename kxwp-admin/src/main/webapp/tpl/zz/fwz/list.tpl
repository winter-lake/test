{{each list}}
<tr>
	<!-- <td><input type="checkbox"></td> -->
	<td>{{$index+1}}</td>
	<td>{{$value.serviceStationName}}</td>
	<td>{{$value.serviceStationAdmin}}</td>
	<td data-toggle="tooltip" data-placement="bottom" title="{{each $value.service_region_list as _value}} {{_value}},{{/each}}">{{each $value.service_region_list as _value}} {{_value}},{{/each}}</td>
	<td>{{$value.phone}}</td>
	<td>{{$value.platformFee}}</td>
	<td>{{$value.platformBzj}}</td>
	<td>{{$value.createTime}}</td>
	<td>
	    <a class=" btn btn-link" href="/zz/manager/fwz/queryFWZDetail.do?id={{$value.id}}" role="button">查看</a>}} 
	</td>
</tr>
{{/each}}
{{if (list && list.length == 0) }}
<tr><td colspan="12"><div class="e_noresult">暂无结果</div></td></tr>
{{/if}}