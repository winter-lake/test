/*
*此处定义ajax的全局处理事件
*/
/*请求前*/
$(document).ajaxStart(function (e, xhr, settings) {
    common.start_loading();
});
/*请求完成*/
$(document).ajaxComplete(function (e, xhr, settings) {
    common.endloading();
});
/*请求成功*/
$(document).ajaxSuccess(function (e, xhr, settings) {
	var data = $.parseJSON(xhr.responseText || {});
    /*当请求成功时*/
	if(data && data.callStatus == "SUCCESS"){
		if(data.authStatus=="Y"){
			
		}else if(data.authStatus=="N"){
			Common.kxpw_tishi(data.message);
			window.parent.location.href="/";
			console.log(data)
		}
	}else if(data && data.callStatus=="FAIL"){
		if(data.message) {
			Common.kxpw_tishi(data.message);
		}
	}
});
/*请求失败*/
$(document).ajaxError(function(){
	Common.kxpw_tishi("系统异常,请稍后重试...");
})

