/**
 * 公共的ajax的封装
 */
/*请求前*/
$(document).ajaxStart(function (e, xhr, settings) {
    common.start_loading();
});
/*请求完成*/
$(document).ajaxComplete(function (e, xhr, settings) {
    common.end_loading();
});
/*请求成功*/
$(document).ajaxSuccess(function (e, xhr, settings) {
	var data = $.parseJSON(xhr.responseText || {});
    /*当请求成功时*/
	if(data && data.callStatus == "SUCCESS"){
		if(data.authStatus=="Y"){
			console.log(data)
		}else if(data.authStatus=="N"){
			common.msg(data.message);
			window.parent.location.href="/";
			console.log(data)
		}
	}else if(data && data.callStatus=="FAIL"){
		if(data.message) {
			common.msg(data.message);
		}else{
			common.msg("系统异常!");
		}
	}
});
/*请求失败*/
$(document).ajaxError(function(){
	common.msg("系统异常,请稍后重试...");
})