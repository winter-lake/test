/*
 * 服务站-供应商所有接口都在这里 
*/ 

define('model/fwz/gys', function(require, exports, module) {
    module.exports = {

        // 添加供应商
        add: function(data) {
            return $.ajax({
                url: '/fwz/manager/gys/addGys.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
            
        },
        // 添加供应商
        edit: function(data) {
        	return $.ajax({
        		url: '/fwz/manager/gys/editGys.do',
        		type: 'post',
        		contentType:"application/json",
        		dataType: 'json',
        		data: JSON.stringify(data)
        	})
        	
        },

    };
});