/*
 * 供应商所有接口都在这里 
*/ 

define('model/gys/goods', function(require, exports, module) {
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

        // 查找商品
        search: function(data) {
            return $.ajax({
                url: '/gys/goods/listGoods.do',
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
        },
        
        // 编辑商品
        edit: function(data) {
            return $.ajax({
                url: '/gys/goods/editGoods.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        }

    };
});