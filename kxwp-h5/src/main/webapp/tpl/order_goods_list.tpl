<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/html" id="order_list_tpl">
	{{each list as value i}}
	<dl class="s-dl">
		<dt><a href="/h5/goods/goodsDetail.do?id={{value.goodsId}}"><img src="{{value.photoList[0]}}" onerror="this.src='../../../images/404.png'"></a></dt>
		<dd>
			<h4 class="d_title">{{value.goodsName}}</h4>
			<p class="d_gg">规格：{{value.packageSpecific}}</p>
			<p class="price_num">
				<span class="price">￥ {{value.salePrice}}</span>
				<span class="number">X{{value.itemQit}}</span>
			</p>
		</dd>
	</dl>
	{{/each}}
</script>