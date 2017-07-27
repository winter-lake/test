(function(){

	var Page = {
		// 所有的事件绑定都在这里
		initEvents: function(){
			var view = this;
			// 点击上方订单的订单，显示隐藏
			view.$el.on('click', '#my_list', function(event) {
				// event.preventDefault();
				if($(this).hasClass('active')) {
					$(this).removeClass('active');
				} else {
					$(this).addClass('active');
				}
			});
			// 绑定确认收货
			view.$el.on('click', '.j_confirm_btn', function(event) {
				event.preventDefault();
				var id = $(this).data('id');
				if(id) {
					common.start_loading();
					KxModel.order.completeOrder({orderId: id}).then(function(data){
						common.end_loading();
                        if(data.callStatus == "SUCCESS") {
                        	common.msg("确认成功！");
                        	setTimeout(function(){
                        	    window.location.reload();
                        	},1000);
                        }
					}).fail(function(){
						common.end_loading();
					})
				}
			});
		},
		// 获取参数等
		getSubmitData: function(){
			var view = this;
			// 页码
			var pager = {
				currentPage: view.model.get('page'),
				pageSize : 5
			}
			var data = {
				pager: pager
			};
			//订单状态
			if(view.model.get("orderStatus") !=''){
	            data.orderStatus = view.model.get("orderStatus");
	        }
			return data;
		},

		// 搜索函数
		search: function(){
			var view = this;
			var ajax_data = view.getSubmitData();

			return KxModel.order.getOrderList(ajax_data).then(function(data){
				if(data.callStatus == 'SUCCESS') {
					// 如果有数据，则渲染, 否则标记为加载完全部
					if(data.data && data.data.length > 0) {
						view.render(data);
					} else {
						view.model.set('isAll', true);
					}
				} else {
					var msg = data.message || '获取订单失败，请重试'
					common.app(msg);
				}
				return data;
			});
		},

		// 渲染结果
		render: function(data) {
			var view = this;
			var tpldata = {
				list: data.data
			};

			var html = template(view.listTpl, tpldata);
			view.$listBox.append(html);
		},

		// 重置页面状态
		resetPage: function() {
			var view = this;
			view.model.set("page", 1);
			view.model.set("isAll", false);
		},

		init: function() {
			var view = this;
			this.$el = $('.j_container'); // 整个视图容器
			this.$listBox = this.$el.find('.j_list_box'); //列表盒子
			this.listTpl = 'order_list_tpl';
			this.initEvents();
			// 实例化页面model
			this.model = new KxModel.BaseModel({
				page: 1, // 当前页码
				isAll: false, // 全部是否加载完毕
			}, view);
			//当前选择的订单状态
			var orderStatus = view.$el.find('[name=orderStatus]').val();
			if(orderStatus != ''){
				this.model.set("orderStatus",orderStatus);
			}
			// 初始化下拉刷新
			this.$el.find('.j_content').dropload({
		        autoLoad:false,
			    loadUpFn : function(me){
			    	// 重置页面状态
			    	view.resetPage();
			    	view.search().then(function(data){
			    		// 清空列表，重新渲染
			    		view.$listBox.html("");
			    		view.render(data);
			    	}).always(function(data){
			        	me.resetload();
			    	})
			    },
			    loadDownFn: function(me) {
			    	// 如果已经加载全部了
			    	if(view.model.get('isAll')) {
			    		me.noData(true);
			    		me.resetload();
						return false;
					}

					var current_page = view.model.get('page');
					view.model.set('page', ++current_page);
			    	view.search().then(function(data){
			    		// 如果没有数据，标记为全部加载完毕
			    		if(!data.data || data.data.length <= 0) {
			    			me.noData(true);
			    		}
			    	}).always(function(){
			        	me.resetload();
			    	});
			    }
			});
			common.start_loading();
			view.search().then(function(data){
				common.end_loading();
				if(!data.data || data.data.length <= 0) {
	    			common.msg("暂无相关记录");
	    		}
			}).fail(function(){
				common.end_loading();
			})
		}
	};

	Page.init();

})();
