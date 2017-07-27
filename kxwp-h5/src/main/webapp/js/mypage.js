$(function(){
	/*进入设置*/
	function setting(){
		$(".set").on('click',function(){
			window.location.href="/h5/user/setting.do";
		})
	}
	setting();
	$('.load').click(function(event){
		event.preventDefault();
		common.msg("正在开发中...");
	})
})