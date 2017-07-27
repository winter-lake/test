(function(){
	/*
	* 部分字段说明
	* 操作类型 operate_type 更新范围 1:商品 2:供应商 3:超市
	*/ 
	// 店铺的model
	// var ShopModel = function() {
	// 	this.supplierName = ko.observable(); // 店铺名称
	// 	this.supplierGoodsQit = ko.observable(); // 商品一共几件
	// 	this.supplierAmount = ko.observable(); // 商品一共多少钱
	// 	this.supplierAllCheck = ko.observable(); // 是否全选 1是 0否
	// 	this.supplierMessage = ko.observable(); // 店铺提示
	// 	this.minShippingAmount = ko.observable(); // 最低起送金额
	// 	this.shoppingCarList = ko.observableArray([]); // 店铺商品列表
	// }

	// // 商品的model
	// var GoodsModel = function() {
	// 	this.id = ko.observable(); //记录id
	// 	this.goodsId = ko.observable(); // 商品id
	// 	this.goodsName = ko.observable(); // 商品名称
	// 	this.packageSpecific = ko.observable(); //商品规格
	// 	this.salePrice = ko.observable(); // 商品单价
	// 	this.goodQit = ko.observable(); // 商品数量
	// 	this.checkStatus = ko.observable(); // 是否选中 是 "CHECK" 否 "UNCHECK"
	// 	this.goodsMessage = ko.observable(); //商品提示
	// }
	// 购物车model
 	var ViewModel = function() {
		this.allCheck = ko.observable(); //是否全选 0否 1是
		this.platFormMessage = ko.observable(); //消息提示
		this.totalAmount = ko.observable(0); //总价
		this.totalGoodsCount = ko.observable(); // 商品总类数
		this.totalGoodsQit = ko.observable(); // 商品总件数
		this.platFormstandard = ko.observable(); // 是否能结算 是Y 否N
		this.shoppingcarResult = ko.observableArray([]); // 店铺列表
		this.pageIsPending = ko.observable(false); // 页面是否在发请求
		// 和编辑相关的
		this.isEdit = ko.observable(false); // 是否处于编辑状态
		this.editBtnText = ko.computed(function(){
			return this.isEdit() ? '完成' : '编辑';
		}, this);
		// 页面第一次加载
		this.isFirst = ko.observable(true);
		// 选中的商品goodsid
		// this.goodsList = ko.observableArray([]);
	}


	var ShoppingCartList = {
		initEvents: function() {
			var view = this;
			view.$el.on('click', '.j_edit', function(event) {
				event.preventDefault();
				var isEdit = view.vm.isEdit();
				view.vm.isEdit(!isEdit);
			});
		},

		// 商品相关操作
		initGoodsEvents: function(){
			var view = this;
			// 减小数量
			view.$el.on('click', '.j_goods_reduce', function(event) {
				if(view.vm.pageIsPending() == true) {
					return false;
				}
				event.preventDefault();
				var goods = $(this).parents('.j_goods_item');
				var goodsid = goods.data("goodsid");
				var id = goods.data("id");
				var ajax_data = {
					operate_type: "1",
					id: id,
					goodsId: goodsid,
					goods_operated_type: 'CUT'
				}
				view.updateShopCar(ajax_data).done(function(data){
					// 填充ko数据
	        		KxApp.linkKnockout(view.vm, data);
				});
				return false;
			});

			// 增加数量
			view.$el.on('click', '.j_goods_plus', function(event) {
				if(view.vm.pageIsPending() == true) {
				return false;
			}
				event.preventDefault();
				var goods = $(this).parents('.j_goods_item');
				var numberDom = goods.find(".j_goods_num"); // 数量
				var goodsid = goods.data("goodsid");
				var id = goods.data("id");
				var ajax_data = {
					operate_type: "1",
					id: id,
					goodsId: goodsid,
					goods_operated_type: 'ADD'
				}
				view.updateShopCar(ajax_data).then(function(data){
					// 填充ko数据
	        		KxApp.linkKnockout(view.vm, data);
				});
				return false;
			});

			// 选择商品
			view.$el.on('change', '.j_goods_check', function(event) {
				if(view.vm.pageIsPending() == true) {
				return false;
			}
				var this_btn = $(this);
				var goods = $(this).parents('.j_goods_item');
				var goodsid = goods.data("goodsid");
				var id = goods.data("id");
				var ajax_data = {
					operate_type: "1",
					id: id,
					goodsId: goodsid,
					goods_operated_type: 'CHECK'
				}
				view.updateShopCar(ajax_data).done(function(data){
					// 填充ko数据
	        		KxApp.linkKnockout(view.vm, data);
				});
			});
		},

		// 店铺相关操作
		initShopsEvents: function(){
			var view = this;
			// 选择商品
			view.$el.on('change', '.j_shop_check', function(event) {
				if(view.vm.pageIsPending() == true) {
				return false;
			}
				var this_btn = $(this);
				var shop = $(this).parents('.j_shop_item');
				var shopid = shop.data("shopid");
				var id = shop.data("id");
				var ajax_data = {
					operate_type:2,			
				    supplierAllCheck: this_btn.prop("checked") ? 1 : 0,		
				    supplierId: shopid
				}
				view.updateShopCar(ajax_data).done(function(data){
					// 填充ko数据
	        		KxApp.linkKnockout(view.vm, data);
				});
			});
		},

		// 购物车相关操作
		initShopCarEvents: function(){
			event.preventDefault();
			var view = this;
			// 全选
			view.$el.on('change', '.j_shopcar_check', function(event) {
				if(view.vm.pageIsPending() == true) {
				return false;
			}
				var this_btn = $(this);
				var ajax_data = {
					operate_type:3,			
				    allCheck: this_btn.prop("checked") ? 1 : 0,		
				}
				view.updateShopCar(ajax_data).done(function(data){
					// 填充ko数据
	        		KxApp.linkKnockout(view.vm, data);
	        		// 判断返回的是否可以选中
	        		this_btn.prop("checked", data.allCheck == 1 ? true : false);
				});
				return false;
			});
		},

		// 更新购物车
		updateShopCar: function(data) {
			var view = this;
			view.vm.pageIsPending(true);
			common.start_loading();
			return KxModel.shoppingCart.update(data).then(function(data){
				// 将数据存储
				if(data.callStatus == 'SUCCESS' && data.data) {
					view.initialData = data.data; 
					return data.data;
				}
			}).always(function(){
				common.end_loading();
				view.vm.pageIsPending(false);
			});
		},

		// 一开始进来获取数据
		getInitialData: function() {
			var view = this;
			common.start_loading();
			return KxModel.shoppingCart.fetch().then(function(data){
				// 将数据存储
				if(data.callStatus == 'SUCCESS' && data.data) {
					view.initialData = data.data; 
					return data.data;
				} else {
					common.msg(data.message || "获取进货单失败，请重试");
				}
			}).always(function(){
				common.end_loading();
				view.vm.isFirst(false);
			})
		},
		
		

		// 初始化编辑购物车的相关事件
		initEdit: function() {
			var view = this;
			// 选择商品
			view.$el.on('change', '.j_edit_goods_check', function(event) {
				var check = $(this);
				var flag = check.prop('checked');
				var shop = check.closest('.j_shop_item');
				var shopcheck = shop.find('.j_edit_shop_check');
				if(flag) {
					var shopflag = (shop.find('.j_goods_item').length == shop.find('.j_edit_goods_check:checked').length);
					if(shopflag) {
						shopcheck.prop('checked', true);
					}
				} else {
					shopcheck.prop('checked', false);
				}
				return true;
			});

			// 选择店铺
			view.$el.on('change', '.j_edit_shop_check', function(event) {
				var check = $(this);
				var flag = check.prop('checked');
				var shop = check.closest('.j_shop_item');
				var goodscheck = shop.find('.j_edit_goods_check');
				goodscheck.prop('checked', flag);
				// 判断全部店铺是否已经全选，对应相应底部全选
				var all = $(".j_edit_all");
				if(flag) {
					var allflag = ($(".j_edit_content .j_shop_item").length == $(".j_edit_shop_check:checked").length);
					all.prop('checked', allflag);
				} else {
					all.prop('checked', false);
				}

				return true;
			});

			// 全选
			view.$el.on('change', '.j_edit_all', function(event) {
				var check = $(this);
				var flag = check.prop('checked');
				var shopscheck = $(".j_edit_content .j_edit_shop_check");
				shopscheck.prop('checked', flag).trigger('change');
				return true;
			});

			// 删除
			view.$el.on('click', '.j_delete_goods', function(event) {
				if(view.vm.pageIsPending() == true) {
					return false;
				}
				var goodsList = new Array();
				$('.j_edit_goods_check:checked').each(function(index, el) {
					if($(el).val()) {
						goodsList.push($(el).val());
					}
				});
				var this_btn = $(this);
				var ajax_data = {
					operate_type: 1,			
				    goods_operated_type: 'DELETE',
				    goodsList: goodsList		
				}
				view.updateShopCar(ajax_data).then(function(data){
					// 填充ko数据
	        		KxApp.linkKnockout(view.vm, data);
	        		// 判断返回的是否可以选中
	        		common.msg('删除成功！');
	        		// 如果没数据，则重置页面
	        		if(!data) {
	        			view.resetPage();
	        			// 清除底部的进货单数量标记
	        			KxApp.removeShopCarCount();
	        		}
				});
				return false;
			});
		},

		// 重置页面ko
		resetPage: function() {
			this.vm.allCheck(0); //是否全选 0否 1是
			this.vm.platFormMessage(''); //消息提示
			this.vm.totalAmount(0); //总价
			this.vm.totalGoodsCount(0); // 商品总类数
			this.vm.totalGoodsQit(0); // 商品总件数
			this.vm.platFormstandard('N'); // 是否能结算 是Y 否N
			this.vm.shoppingcarResult([]); // 店铺列表
			this.vm.pageIsPending(false); // 页面是否在发请求
			// 和编辑相关的
			this.vm.isEdit(false); // 是否处于编辑状态
		},

	    // 初始化页面函数
	    init: function() {
	        var view = this;
	        view.$el = $("body");
	        // 绑定ko
	        view.vm = new ViewModel();
	        ko.applyBindings(view.vm, view.$el[0]);
	        // 先获取数据
	        view.getInitialData().then(function(data){
	        	// 填充ko数据
	        	KxApp.linkKnockout(view.vm, data);
	        	// 初始化相关事件
	        	view.initEvents();
	        	// 初始化商品相关事件
	        	view.initGoodsEvents();
	        	// 初始化店铺相关事件
	        	view.initShopsEvents();
	        	// 初始化购物车相关事件
	        	view.initShopCarEvents();
	        	// 编辑购物车
	        	view.initEdit();
	        });
	    }
	};

	ShoppingCartList.init();
})();




















// 选择商品的操作
