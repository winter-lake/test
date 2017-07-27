(function(global) {
	/*
	* 列表页添加进货单
	  [description] 请参照商品列表页dom结构 '/h5/goods/searchGoodsProxy.do';
	  以下是class说明
	  ** 每一个列表项
	  .j_goods_item 每一个商品容器最外面的class
	  		.j_goods_reduce '-'号的class
	  	 	.j_goods_num '-'和'+' 中间的数字
	  	 	.j_goods_plus '+'号的class
	  
	  ** 底部总计
	  .j_total_car 底部容器div
	  		.j_total_numbers 总计数量
	  		.j_total_price 总计价格

	  ** 购物车
	  .j_shopcar_icon
	  		.j_shopcar_count 购物车数量提示

	  给对应的dom加上对应的类即可实现

	
	  	 	

	*/ 
	global.ShopCar = {
    	// 存放已选择的数据
		totalGoods: [],
		initEvents: function() {
			var view = this;
			// 减小数量
			view.$el.on('click', '.j_goods_reduce', function(event) {
				event.preventDefault();
				var goods = $(this).parents('.j_goods_item');
				// 取得当前的商品数量
				var numberDom = goods.find(".j_goods_num"); // 数量
				var number = numberDom.text();
				number = Number(number);
				if(isNaN(number) || number == 0) {
					return false;
				}
				number--;
				numberDom.text(number);
				// 通知底部数量重新渲染
				view.randerTotal();
				return false;
			});

			// 增加数量
			view.$el.on('click', '.j_goods_plus', function(event) {
				event.preventDefault();
				var goods = $(this).parents('.j_goods_item');
				// 取得最大值
				var maxNumber = $(this).data("max") || 99;
				// 取得当前的商品数量
				var numberDom = goods.find(".j_goods_num"); // 数量
				var number = numberDom.text();
				number = Number(number);
				if(isNaN(number) || number >= maxNumber) {
					return false;
				}
				number++;
				numberDom.text(number);
				// 通知底部数量重新渲染
				view.randerTotal();
				return false;
			});

			// 加入进货单
			view.$el.on('click', '.j_add_submit', function(event) {
				// 如果数组不为空，则提交
				if(view.totalGoods.length > 0) {
					KxModel.shoppingCart.addShoppingCart({
						items: view.totalGoods
					}).then(function(data) {
						if (data.callStatus == 'SUCCESS') {
							common.msg("添加商品成功");
							// 购物车显示
							if(data.data && data.data.goodsCount) {
								var num = $("<span class='num j_shopcar_count goods_num j_shopcar_count'></span>");
								$('.j_shopcar_icon').append(num);
								view.$showcar.find('.j_shopcar_count').text(data.data.goodsCount);
								// 清空数据
								view.resetPage();
							}
						} else {
							if(data.message) {
								common.msg(data.message);
							} else {
								common.msg('网络错误，请重试');
							}
						}
					})
				} else {
					common.msg("请至少添加一个商品");
				}
			})

		},

		// 通知底部重新渲染
		randerTotal: function() {
			var view = this;
			var totalCar = view.$totalDom;
			// 总计信息
			var total_numbers  = 0;
			var total_price  = 0;
			var slecteds = new Array();
			view.$el.find('.j_goods_item').each(function(index, el) {
				var goods_id = $(el).data("goodsid");
				//判断是否位特价商品
				if($(el).data("newprice")){
					var goods_price = $(el).data("newprice");
				}else{
					var goods_price = $(el).data("price");
				}
				var goods_num = $(el).find('.j_goods_num').text();
				goods_num = Number(goods_num);
				// 只有大于0 才计算在内
				if(!isNaN(goods_num) && goods_num > 0) {
					var obj = {
						goodsId: goods_id,
						goodQit: goods_num
					}
					slecteds.push(obj);
					total_numbers += goods_num;
					total_price += goods_num * goods_price;
				}
			});

			total_price = KxApp.formatMoney(total_price);
			// 更新底部总计信息
			totalCar.find(".j_total_numbers").text(total_numbers);
			totalCar.find(".j_total_price").text(total_price);

			// 存储数据
			view.totalGoods = slecteds;
		},

		// 回到初始状态
		resetPage: function() {
			var view = this;
			view.$el.find('.j_goods_item .j_goods_num').text(0);
			view.$totalDom.find('.j_total_numbers').text(0);
			view.$totalDom.find('.j_total_price').text(0);
			view.totalGoods = [];
		},

	    // 初始化页面函数
	    init: function(options) {
	    	var view = this;
	    	options = options || {};
	    	var defaults = {
	    		el: 'body',
	    		shopcar: '.j_shopcar_icon' // 是否有购物车
	    	}
	    	view.options = $.extend(defaults, options);
	    	
	    	var el = view.options.el;
	        var view = this;
	        view.$el = $(el);
	        // 购物车
	        view.$showcar = $(view.options.shopcar);
	        // 底部总计
	        view.$totalDom = view.$el.find('.j_total_car');
	        view.initEvents();

	        // 获取购物车数量
	        KxApp.getShopCarCount(view.$showcar);
	    }
	}

	// 获取购物车数量
	global.getShopCarCount = function(shopcar){
		return KxModel.shoppingCart.getCount().then(function(data){
			if(shopcar) {
				if(data.callStatus == 'SUCCESS') {
					if(data.data/* && data.data.goodsCount*/) {
						// 更新购物车数量
						if(shopcar) {
							var total = data.data.goodsCount;
							var text_num = total + '';
							if(total > 99){
								text_num = '99+';
							}
							var num = $("<span class='num j_shopcar_count goods_num'></span>");
							$(num).text(text_num);
							$('.j_shopcar_icon').append(num);
							if(total == 0){
								$(num).remove();
							}
						}
						return data.data.goodsCount;
					}
				} else {
					if(data.message) {
						common.msg(data.message);
					} else {
						common.msg('获取购物车商品数量失败，请重试');
					}
				}
			}
		})
	},

	// 清除底部购物车数量标记
	global.removeShopCarCount = function () {
		$('.footer .j_anchor_shopcar .j_shopcar_count').remove();
	},

	// 初始化所有点击，加入购物车的事件
	global.initAddToShopCar = function(btn) {
		btn = btn || '[data-addshopcar]';
		$("body").on('click', btn, function(event) {
			event.preventDefault();
			var id = $(this).data('goodsid');
			if(!id) {
				return false;
			}
			var items = new Array();
			items.push({
				goodsId: id,
				goodQit: 1
			});

			KxModel.shoppingCart.addShoppingCart({
				items: items
			}).then(function(data) {
				if (data.callStatus == 'SUCCESS') {
					common.msg("加入进货单成功");
				} else {
					if(data.message) {
						common.msg(data.message);
					} else {
						common.msg('网络错误，请重试');
					}
				}
			});
		});
	}
})(KxApp);