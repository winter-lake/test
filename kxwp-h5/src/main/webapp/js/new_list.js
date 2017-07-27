$(function(){
	$('.j_content .kx_msg').on('click',function(){
		location.href="/h5/announcement/listAnnouncementInit.do"
	})
	$.ajax({
		url:"/h5/announcement/ajax/getLatestAnnouncement.do",
		type:'post',
		contentType:"application/json",
		dataType:'json',
		data:JSON.stringify({}),
		success:function(data){
			renderhtml(data);
		}
	})
	var html="";
	function renderhtml(data){
		var json=data.data[0];
		$(".kx_msg .message").html(json.announcementName);
		$(".kx_msg .time").html(json.pushTime);
	}
})