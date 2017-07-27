/*
 * 供应商所有接口都在这里 
*/ 

define('model/zz/order', function(require, exports, module) {
    module.exports = {
        // 查找商品
        search: function(data) {
            return $.ajax({
                url: '/zz/manager/salesOrder/listSalesOrder.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        }
    };
});