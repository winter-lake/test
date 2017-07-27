/*
 * 总站-服务商接口
*/ 

define('model/zz/stop', function(require, exports, module) {
    module.exports = {

        // 获取配送范围
        deliveryRange: function(data) {
            return $.ajax({
                url: '/zz/manager/fwz/getUnOpendCounty.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
            
        },

        // 添加服务商
        add: function(data) {
            return $.ajax({
                url: '/zz/manager/fwz/createFWZ.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
        }

    };
});