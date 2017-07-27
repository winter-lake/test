<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/html" id="message_tpl">
	{{each list as value i}}
		<div class="list" data_id="{{value.id}}">
			<div class="time">{{value.pushTime}}</div>
			<div class="d_box" data_id='{{value.id}}'>
				<h4 class="title">[{{value.serviceStationName}}]  {{value.announcementName}}</h4>
				<p class="img"><img src="{{value.pictureUrl}}" alt=""></p>
				<p class="msg">{{value.content}}</p>
				<p class="detail">
					<span>查看详情</span>
					<span class="go"></span>
				</p>
			</div>
		</div>
	{{/each}}
</script>