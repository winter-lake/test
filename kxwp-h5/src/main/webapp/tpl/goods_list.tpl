<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/html" id="goods_list_tpl">
	{{each list as value i}}
		<dl class="j_goods_item" data-goodsid="{{value.id}}" data-price="{{value.salePrice}}">
			<a href="/h5/goods/goodsDetail.do?id={{value.id}}">
			<dt class="i_mg">
				<img src="{{value.photoList[0]}}" class="photo" onerror="this.src='../../../images/404.png'">
			</dt>
			</a>
			<dd>
				<p class="title">{{value.goodsName}}</p>
				<p class="G-g">规格 :{{value.packageSpecific}}</p>
				<p class="price-add">
					<i class="price">￥ {{value.salePrice}}</i>
					<ul class="a_r">
						<li class="add j_goods_reduce"><img src="<%=contextPath %>/images/r_ole.png"></li><li class="input j_goods_num">0</li><li class="a_dd j_goods_plus"><img src="<%=contextPath %>/images/a_dd.png"></li>
					</ul>
				</p>
				<p class="company"><a href="javascript:;">{{value.supplier_name}}</a></p>
			</dd>
		</dl>
	{{/each}}
</script>