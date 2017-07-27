{{each list}}
<tr>
	<td>{{$index+1}}</td>
	<td>{{$value.channelNameDesc}}</td>
	<td>{{$value.channelDesc}}</td>
	<td>
	    <button type="button" class="btn btn-default" name="view" channelId="{{$value.id}}">查看</button>
	    <button type="button" class="btn btn-default" name="maintain" channelId="{{$value.id}}">维护</button>
	</td>
</tr>
{{/each}}
{{if (list && list.length == 0) }}
<tr><td colspan="11"><div class="e_noresult" style="text-align: center;">暂无结果</div></td></tr>
{{/if}}
