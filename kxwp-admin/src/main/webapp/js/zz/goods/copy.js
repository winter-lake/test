define('zz/goods/copy', function(require, exports, module) {

	var Model = require('model/zz/goods'); // 引入接口文件

	module.exports = {

		// 页面绑定事件，所有的事件都在这里写
		initEvents : function() {
			var view = this;
			// 绑定查询事件
			view.$el.on('click', '.j_search_btn', function(event) {
				event.preventDefault();
				view.copy();
			});
		},

		// 复制商品函数
		copy : function() {
			var view = this;
			var ajax_data = view.getSubmitData();
			Model.copy(ajax_data).done(function(data) {
				alert(data.callStatus);
			})
		},
	
		// 此函数用来获取整个页面中要提交到后台的值
		getSubmitData : function() {
			var view = this;
			var ajax_data = {
			};
			var copyed_supplierId = $.trim(($('#copyed_supplier').val()));
			if (copyed_supplierId) {
				ajax_data.copyed_supplierId = copyed_supplierId;
			}
			var supplierId = $.trim(($('#supplier').val()));
			if (supplierId) {
				ajax_data.supplierId = supplierId;
			}
			if(copyed_supplierId == supplierId){
				alert("请不要选择相同的供应商");
			}
			return ajax_data;
		},

		// 初始化页面函数
		init : function() {
			var view = this;
			view.$el = $('.k_right'); // 准备好这个页面的dom元素
			view.initEvents(); // 初始化绑定事件
			KXWP.gysSelect("#supplier");
			KXWP.gysSelect("#copyed_supplier");
		}
	};
});