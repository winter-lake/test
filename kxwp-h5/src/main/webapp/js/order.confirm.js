//订单确认
(function(){

	var Page = {
		// 所有的事件绑定都在这里
		initEvents: function(){
			var view = this;
			 // 点击提交订单
            view.$el.on('click', '.j_submit_order', function(event) {
            	var this_btn = $(this);
            	// 如果正在发送请求，则返回
            	if(this_btn.data("submit")) {
            		return false;
            	}
                // event.preventDefault();
                // 标记为正在发请求
                this_btn.data("submit", true);
            	view.submitOrder().always(function(){
            		// 请求完成后，把标记取消
            		this_btn.data("submit", false);
            	});
                return false;
            });
		},
		// 获取参数等
		getSubmitData: function(){
			var view = this;
			var data = {
				orderReq:$("#orderSeq").val(),
			};
			//获取供应商留言
			var list = new Array();
			view.$el.find("p.l-y").find('p.l-y textarea').each(function(index, el) {
				var obj = {
							 supplierId : $.trim($(el).attr('supplier-id')),
							 message:$.trim($(el).val())
						  };
				list.push(obj);
			});
			data.supplierList = list;
			return data;
		},

		// 搜索函数
		submitOrder: function(){
			var view = this;
			var ajax_data = view.getSubmitData();

			return KxModel.order.submitOrder(ajax_data).then(function(data){
				if(data.callStatus == 'SUCCESS') {
					// 如果有数据，则渲染, 否则标记为加载完全部
					window.location.href="/h5/order/gotoPlaceOrderResult.do?result=success";
				} else {
					var msg = data.message || '创建订单失败，请重试'
					common.msg(msg);
					window.location.href="/h5/order/gotoPlaceOrderResult.do?result=fail";
				}
				return data;
			});
		},

		init: function() {
			var view = this;
			this.$el = $('.j_container'); // 整个视图容器
			this.initEvents();
		}
	};

	Page.init();

})();
