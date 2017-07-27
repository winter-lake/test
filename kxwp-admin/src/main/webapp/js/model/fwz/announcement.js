/*
 * 服务站-供应商所有接口都在这里 
*/ 

define('model/fwz/announcement', function(require, exports, module) {
    module.exports = {

        add: function(data) {
            return $.ajax({
                url: '/fwz/SsAnnouncement/ajax/add.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
            
        },
        list: function(data) {
        	return $.ajax({
        		url: '/fwz/SsAnnouncement/ajax/list.do',
        		type: 'post',
        		contentType:"application/json",
        		dataType: 'json',
        		data: JSON.stringify(data)
        	})
        	
        },
        get: function(data) {
        	return $.ajax({
        		url: '/fwz/SsAnnouncement/ajax/get.do?id='+data,
        		type: 'get',
        		contentType:"application/x-www-form-urlencoded"
        	})
        	
        },

    };
});