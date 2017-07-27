$(document).ready(function(){
	
	//更新订单状态为待收货状态
	$("#shipping").click(function(){
		
		var ajax_data = {
				orderId:$("#order_id").val(),
				new_orderStatus:"Awaiting_Receive"
			};
		
		$.ajax({
			url : '/gys/manager/salesOrder/updateSalesOrderStatus.do',
			type : 'post',
			contentType : "application/json",
			dataType : 'json',
			data : JSON.stringify(ajax_data),
			success:function(data){
				if (data.callStatus == 'SUCCESS') {
					layer.alert('操作成功',function(index){
						window.location.reload();
					});        
				}
			}
		})
		});
});