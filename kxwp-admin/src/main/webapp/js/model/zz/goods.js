/*
 * 供应商所有接口都在这里 
*/ 

define('model/zz/goods', function(require, exports, module) {
    module.exports = {

        // 添加商品
        add: function(data) {
            return $.ajax({
                url: '/zz/goods/addGoods.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
            
        },

        // 查找商品
        search: function(data) {
            return $.ajax({
                url: '/zz/goods/listGoods.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        },
        
        // 更新商品状态
        update_goods_status: function(data) {
            return $.ajax({
                url: '/zz/goods/updateGoodsStatus.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        },
        
        // 复制商品
        copy: function(data) {
            return $.ajax({
                url: '/zz/goods/copyGoods.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        }

    };
});