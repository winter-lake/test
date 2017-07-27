/*
 * 供应商所有接口都在这里 
*/ 

define('model/fwz/goods', function(require, exports, module) {
    module.exports = {

    	 // 更新商品状态
         update_goods_status: function(data) {
                return $.ajax({
                    url: '/fwz/goods/updateGoodsStatus.do',
                    type: 'post',
                    contentType:"application/json",
                    dataType: 'json',
                    data: JSON.stringify(data)
                })
         },

        // 查找商品
        search: function(data) {
            return $.ajax({
                url: '/fwz/goods/listGoods.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        }

    };
});