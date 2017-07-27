<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/html" id="faily_list_tpl">
	{{each list as value i}}
		<div class="goods">
		<a href="/h5/goods/goodsDetail.do?id={{value.goodsId}}">
			<dl class="d_l">
				<dt><img src="{{value.photoList[0]}}"></dt>
				<dd>
					<h4 class="title">{{value.goodsName}}</h4>
					<p class="g_g">规格 : {{value.packageSpecific}}</p>
					<p class="new_price">￥{{value.promotionPrice}}</p>
					<p class="old_price"><label>平台价</label> <s>{{value.salePrice}}</s></p>
				</dd>
			</dl>
		</a>
		</div>
	{{/each}}
</script>