$.ajaxSettings = $.extend($.ajaxSettings, {
	beforeSend: function () {
        share.startLoading();
    },
	success : function(){
        console.log("success")
		share.endLoading();
	},
    error : function(){
    	console.log('网络错误，请重试...');
    	return false;
    }
});