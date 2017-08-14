$(document).ready(function() {
	// 定义头部菜单对应的链接
	var menuData = {
		// 分类
		'j_anchor_category': ['category.html'],
		// 歌手
		'j_anchor_author': ['song-author.html'],
		//榜单
		'j_anchor_bd': ['bd.html'],
		// 我的
		'j_anchor_user': ['user.html']
	}

	// 获取当前的url
	var current_url = window.location.pathname;
	// 寻找菜单数组中是否有这个url, 有则高亮对应的菜单项
	for(var key in menuData) {
		var urls = menuData[key];
		if($.inArray(current_url, urls) > -1) {
			$("header").find("." + key).addClass('cur');
			return false;
		}
	}
});


(function(global){
	global.share = {
		mark : document.querySelector('.mark')
	};
	share.markTip = function(){
		if(!this.mark){
			this.mark = document.createElement('div');
			this.mark.className = 'mark';
			document.body.appendChild(this.mark);
		}
	}
	share.startLoading = function(){
		if(!this.mark){
			this.markTip();
		}
		var imgBox = document.createElement('div');
		imgBox.className = 'imgBox';
		var loadImg = document.createElement('img');
		loadImg.class = 'loadImg';
		loadImg.src = "img/loading.gif";
		imgBox.appendChild(loadImg);
		this.mark.appendChild(imgBox);
	}
	share.endLoading = function(){
		if(!this.mark){
			document.body.removeChild(this.mark);
		}
		return false;
	}
	global.share = share;
})(window)


