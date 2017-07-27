<%@ page language="java" pageEncoding="UTF-8"%>
		<footer class="footer">
			<a href="/h5/user/home.do" class="s_p j_anchor_home">
				<i class="icon-home"></i><br>
				<em class="e_m" name="首页">首页</em>
			</a>
			<a href="/h5/classificaion/gotoCategory.do" class="s_p j_anchor_category">
				<i class="icon-th-large"></i><br>
				<em class="e_m" name="分类">分类</em>
			</a>
			<a href="/h5/supplier/searchSupplierProxy.do" class="s_p j_anchor_gys">
				<i class="icon-truck"></i><br>
				<em class="e_m" name="供应商">供应商</em>
			</a>
			<a href="/h5/shoppingCart/gotoShoppingCart.do" class="s_p list j_anchor_shopcar  j_shopcar_icon">
				<i class="icon-file-alt"></i><br>
				<!-- <span class="num j_shopcar_count">0</span> -->
				<em class="e_m" name="进货单">进货单</em>
			</a>
			<a href="/h5/user/mypage.do" class="s_p j_anchor_user">
				<i class="icon-user"></i><br>
				<em class="e_m" name="我的">我的</em>
			</a>
		</footer>

<script type="text/javascript">
	$(document).ready(function() {
		// 更新底部已选择的商品总计
		KxApp.getShopCarCount($('.footer .j_anchor_shopcar'));

		// 定义底部菜单对应的链接
		var menuData = {
			// 首页
			'j_anchor_home': ['/h5/user/home.do'],
			// 分类
			'j_anchor_category': ['/h5/classificaion/gotoCategory.do'],
			// 供应商
			'j_anchor_gys': ['/h5/supplier/searchSupplierProxy.do'],
			// 进货单
			'j_anchor_shopcar': ['/h5/shoppingCart/gotoShoppingCart.do'],
			// 我的
			'j_anchor_user': ['/h5/user/mypage.do']
		}

		// 获取当前的url
		var current_url = window.location.pathname;
		// 寻找菜单数组中是否有这个url, 有则高亮对应的菜单项
		for(var key in menuData) {
			var urls = menuData[key];
			if($.inArray(current_url, urls) > -1) {
				$(".footer").find("." + key).addClass('cur');
				return false;
			}
		}
	});
</script>

<script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="500312034" ></script>
