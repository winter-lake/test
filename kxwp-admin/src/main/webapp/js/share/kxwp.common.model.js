/*
*此文件定义所有公用的接口
*/

var CommonModel = (function(){
    var model = {
        
        // 删除图片接口
        delFile: function(data) {
            return $.ajax({
                url: '/common/file/ajax/deleteFile.do',
                type: 'post',
                contentType:"application/json",
                dataType: 'json',
                data: JSON.stringify(data)
            })
            
        }

    }
    return model;
})();

