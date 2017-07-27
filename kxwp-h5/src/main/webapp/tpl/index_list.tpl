<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script id="index_tpl" type="text/html">
		{{each list as value i}}
		<dl>
			<a href="{{value.open_url}}">
				<dt><img src="{{value.photo_url}}" onerror="this.src='../../../images/404.png'"></dt>
				<dd>
					<p class="msg">{{value.title}}</p>
					<p class="p-a">
						<span class="price">￥{{value.sub_title}}</span>
					</p>
				</dd>
			</a>
		</dl>
		{{/each}}
</script>