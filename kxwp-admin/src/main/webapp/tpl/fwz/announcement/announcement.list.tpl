{{each list}}
<tr>
	<td>{{$index+1}}</td>
	<td>{{$value.announcementName}}</td>
	<td>{{$value.announcementDesc}}</td>
	<td>{{$value.announcementDesc}}</td>
	<td>{{$value.pushTime}}</td>
	{{if $value.announcementStatus == 'SENT'}}<td>已发送</td>{{/if}}
	{{if $value.announcementStatus == 'UNSENT'}}<td>未发送</td>{{/if}}
	{{if $value.announcementStatus == 'DELETED'}}<td>已删除</td>{{/if}}
	<td>
	    <a class="btn btn-link" href="#" id="preview" announcement-id="{{$value.id}}" role="button">预览</a>
	   {{if $value.announcementStatus != 'DELETED'}}
	   <button type="button" class="j_update_announcement btn btn-warning btn-sm" new-status='DELETED' announcement-id="{{$value.id}}">删除</button>
	   {{/if}}
	</td>
</tr>
{{/each}}
{{if (list && list.length == 0) }}
<tr><td colspan="11"><div class="e_noresult">暂无结果</div></td></tr>
{{/if}}
