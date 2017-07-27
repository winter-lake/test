/*
 * 供应商所有接口都在这里 
*/ 

define('model/fwz/supermarket', function(require, exports, module) {
    module.exports = {

    	// 更新商品状态
         update_supermarket_status: function(data) {
                return $.ajax({
                    url: '/fwz/supermarket/updateSupermarketStatus.do',
                    type: 'post',
                    contentType:"application/json",
                    dataType: 'json',
                    data: JSON.stringify(data)
                })
         }
    };
});