<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/html" id="faily_list_tpl">
	{{each list as value i}}
		<div class="goods">
			<div class="label"><label>特卖</label></div>
			<dl class="d_l j_goods_item" data-goodsid="{{value.goodsId}}" data-price="{{value.salePrice}}" data-newprice="{{value.promotionPrice}}">
				<dt><a href="/h5/goods/goodsDetail.do?id={{value.goodsId}}"><img src="{{value.photoList[0]}}"></a></dt>
				<dd>
					<h4 class="title">{{value.goodsName}}</h4>
					<p class="g_g">规格 : {{value.packageSpecific}}</p>
					<div class="old_new_price">
						<span class="new_price">￥ {{value.promotionPrice}}</span>
						<s class="old_price">{{value.salePrice}}</s>
						<ul class="a_r">
							<li class="add j_goods_reduce"><img src="<%=contextPath %>/images/r_ole.png"></li><li class="input j_goods_num">0</li><li class="a_dd j_goods_plus"><img src="<%=contextPath %>/images/a_dd.png"></li>
						</ul>
					</div>
					<p class="company">{{value.supplierName}}</p>
				</dd>
			</dl>
		</div>
	{{/each}}
</script>