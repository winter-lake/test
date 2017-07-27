/*
 * 服务站-供应商所有接口都在这里 
*/ 

define('model/fwz/channel', function(require, exports, module) {
    module.exports = {

        add: function(data) {
            return $.ajax({
                url: '/fwz/channel/ajax/addChannel.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
            
        },
        list: function(data) {
        	return $.ajax({
        		url: '/fwz/channel/ajax/listChannel.do',
        		type: 'post',
        		contentType:"application/json",
        		dataType: 'json',
        		data: JSON.stringify(data)
        	})
        	
        },
        edit: function(data) {
        	return $.ajax({
        		url: '/fwz/channel/ajax/modifyChannel.do',
        		type: 'post',
        		contentType:"application/json",
        		dataType: 'json',
        		data: JSON.stringify(data)
        	})
        	
        },

    };
});