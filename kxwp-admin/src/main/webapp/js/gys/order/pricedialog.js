define('gys/order/pricedialog', function(require, exports, module) {

	var Model = require('model/gys/order'); // 引入接口文件
	var PriceDialogTpl = require('tpl/gys/order/gys.price.dialog.tpl'); // 引入模板文件

	// 引入vue
	require('plugin/vue/vue');

	var Price = function() {

        
		// 页面绑定事件，所有的事件都在这里写
		this.initEvents = function() {
			var view = this;
			view.$el.on('click', '.j_sure_btn', function(e) {
				var ajax_data = view.getSubmitData();
				Model.updatePayAmount(ajax_data).done(function(data){
					if(data.callStatus == 'SUCCESS') {
						view.def.resolve(data);
						view.hide();
					}
				})
			})
		};

		// 获取数据
		this.getSubmitData = function() {
			var view = this;
			var discount_type = view.vm.$get('discount_type');
			var ajax_data = {
				orderId: view.vm.$get('orderId')
			}

			if(discount_type == 1) {
	    		ajax_data.discount = view.vm.$get('discount');
	    	} else {
		    	if(discount_type == 2) {
		    		ajax_data.payAmount = view.vm.$get('payAmount');
		    	}
	    	}

	    	return ajax_data;
		};

		// 显示函数
		this.show = function(data) {
			var view = this;
			// 先重置数据
			view.resetData();
			view.vm.orderId = data.orderId; // 填充订单号id
			view.vm.orderNo = data.orderNo; // 填充订单号
			view.vm.ori_price = data.ori_price; // 填充原始价格
			view.$el.modal("show");
			// 开启promise
			return view.setPromise();
		};

		this.resetData = function(data) {
			var view = this;
			view.vm.orderId = "";
			view.vm.orderNo = "";
			view.vm.ori_price = "";
			view.vm.discount = 1; //默认不打折
			view.vm.payAmount = ""; //默认不减
			view.vm.discount_type = 1;
		};

		// 隐藏函数
		this.hide = function() {
			this.$el.modal("hide");
		};

		this.setPromise = function() {
            this.def = $.Deferred();

            return this.def.promise();
        };

		// 初始化页面函数
		this.init = function() {

			var view = this;
			$('body').append(PriceDialogTpl);
			view.$el = $('#j_price_dialog'); // 准备好这个页面的dom元素
			// 初始化vue
			view.vm = new Vue({
                el: '#j_price_dialog',
                data: {
                    orderId: "", // 订单id
					orderNo: "", // 订单号
					ori_price: "", // 原价
					discount: "", // 折扣
					payAmount: "", // 直接减价格
					discount_type: "" // 调价方式 1:打折 2:直接减
                },
                computed: {
				    real_price: function () {
				    	var ori_price = Number(this.ori_price);
				    	var discount = Number(this.discount);
				    	var payAmount = Number(this.payAmount);
				    	var discount_type = this.discount_type;
				    	var price = "";
				    	// 判断计价方式，显示不同的调整后价格
				    	if(discount_type == 1) {
				    		if(!isNaN(ori_price) && !isNaN(discount)) {
				    			price = ori_price * discount;
				    			//四舍五入2位小数
					    		price = Math.round(price*Math.pow(10,2))/Math.pow(10,2);
					    	} else {
					    		price = '非法数字';
					    	}
				    	} else {
					    	if(!isNaN(ori_price) && !isNaN(payAmount)) {
					    		price = ori_price + payAmount;
					    	} else {
					    		price = '非法数字';
					    	}
				    	}

				    	return price;
				    },
				    discount_price: function() {
				    	var ori_price = Number(this.ori_price);
				    	var discount = Number(this.discount);
				    	var price = "";
				    	if(!isNaN(ori_price) && !isNaN(discount)) {
				    		price = ori_price * discount;
				    	} else {
				    		price = '非法数字';
				    	}
				    	return price;
				    },
				    // 是否使用打折方式
				    hasDiscount: function() {
				    	return this.discount_type != 1;
				    },
				    // 是否使用直接减方式
				    hasPayamount: function() {
				    	return this.discount_type != 2;
				    }
			  	}
            });
			view.$el.modal({
				show: false,
				backdrop: 'static'
			})
			view.initEvents(); // 初始化绑定事件

		};

		this.init();
	};

	module.exports = Price;
});