/*
 * 订单所有接口都在这里 
 */

define('model/fwz/order', function(require, exports, module) {
	module.exports = {
		// 查询订单
		search : function(data) {
			return $.ajax({
				url : '/fwz/manager/salesOrder/listSalesOrder.do',
				type : 'post',
				contentType : "application/json",
				dataType : 'json',
				data : JSON.stringify(data)
			})
		},

		// 更新订单状态
		update_order_status : function(data) {
			return $.ajax({
				url : '/fwz/manager/salesOrder/updateSalesOrderStatus.do',
				type : 'post',
				contentType : "application/json",
				dataType : 'json',
				data : JSON.stringify(data)
			})
		},
		
		// 订单导出
		export_order : function(data) {
			return $.ajax({
				url : '/fwz/report/ajax/exportOrder.do',
				type : 'post',
				contentType : "application/json",
				dataType : 'json',
				data : JSON.stringify(data)
			})
		},
	};
});