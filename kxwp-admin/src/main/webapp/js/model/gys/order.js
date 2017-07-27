/*
 * 订单所有接口都在这里 
 */

define('model/gys/order', function(require, exports, module) {
	module.exports = {
		// 查询订单
		search : function(data) {
			return $.ajax({
				url : '/gys/manager/salesOrder/listSalesOrder.do',
				type : 'post',
				contentType : "application/json",
				dataType : 'json',
				data : JSON.stringify(data)
			})
		},

		// 更新订单状态
		update_order_status : function(data) {
			return $.ajax({
				url : '/gys/manager/salesOrder/updateSalesOrderStatus.do',
				type : 'post',
				contentType : "application/json",
				dataType : 'json',
				data : JSON.stringify(data)
			})
		},

		// 调整价格
		updatePayAmount: function(data) {
			return $.ajax({
				url : '/gys/manager/salesOrder/updatePayAmount.do',
				type : 'post',
				contentType : "application/json",
				dataType : 'json',
				data : JSON.stringify(data)
			});
		}
	};
});