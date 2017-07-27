/*
 * 总站对服务站的管理接口都在这里 
*/ 

define('model/zz/fwz', function(require, exports, module) {
    module.exports = {

        // 添加商品
        add: function(data) {
            return $.ajax({
                url: '/gys/goods/addGoods.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
            
        },

        // 服务站查询
        search: function(data) {
            return $.ajax({
                url: '/zz/manager/fwz/queryFWZ.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        },
        
        // 更新商品状态
        update_goods_status: function(data) {
            return $.ajax({
                url: '/gys/goods/updateGoodsStatus.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        }

    };
});