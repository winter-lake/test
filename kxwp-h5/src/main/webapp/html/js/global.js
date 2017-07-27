$(document).on('touchmove',function(e){e.preventDefault();})
$(function(){
	$(".footer>span").on('click',function(e){
		e.preventDefault();
		var _this=$(this).find('em').text();
		console.log(_this);
		$(this).addClass('cur').siblings().removeClass('cur');
		if(_this=="首页"){
			window.location.href="index.html";
		}else if(_this=="分类"){
			window.location.href="cation.html";
		}else if(_this=="供应商"){
			window.location.href="gys.html";
		}else if(_this=="进货单"){
			window.location.href="shop_list.html";
		}else if(_this=="我的"){
			window.location.href="mypage.html";
		}
	})
})
