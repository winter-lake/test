$.ajaxSettings = $.extend($.ajaxSettings, {
	beforeSend: function () {
        share.startLoading();
    },
	success : function(){
		share.endLoading();
        new Lazyload({img:document.getElementsByTagName('section')[0].getElementsByTagName('img')})
	},
    error : function(){
    	console.log('网络错误，请重试...');
    	return false;
    }
});