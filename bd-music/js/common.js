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
			console.log($('header').find('.'+key))
			return false;
		}
	}
});
/*rem计算*/
(function(win) {
    var doc = win.document;
    var docEl = doc.documentElement;
    var tid = null;
    function refreshRem() {
        var width = docEl.getBoundingClientRect().width;
        if (width > 540) { // 最大宽度
            width = 540;
        }
        var rem = width / 7.5; 
        docEl.style.fontSize = rem + 'px';
    }
    win.addEventListener('resize', function() {
        clearTimeout(tid);
        tid = setTimeout(refreshRem, 0);
    }, false);
    win.addEventListener('pageshow', function(e) {
        if (e.persisted) {
            clearTimeout(tid);
            tid = setTimeout(refreshRem, 0);
        }
    }, false);
    refreshRem();
})(window);
/*遮罩层*/
(function(global){
	var share = {};
	share.markTip = function(){
		if($('.mark').length == 0){
			this.mark = document.createElement('div');
			this.mark.className = 'mark';
			document.body.appendChild(this.mark);
		}
		return false;
	}
	share.startLoading = function(){
		if($('.mark').length == 0){
			this.markTip();
		}
		if($('.imgBox').length == 0){
			var imgBox = document.createElement('div');
			imgBox.className = 'imgBox';
			var loadImg = new Image();
			loadImg.src = "img/loading.gif";
			imgBox.appendChild(loadImg);
			this.mark.appendChild(imgBox);
		}
	}
	share.endLoading = function(){
		$('.mark').remove();
		$('.imgBox').remove();
	}
	share.removeMark = function(){
		$('.mark').remove();
	}
	global.share = share;
})(window);

/*图片懒加载*/
(function(global){
	function Lazyload(option){
		this.img = option.img;//传入存放图片的容器
		this.winHeight = window.innerHeight;
		this.init();
	}

	Lazyload.prototype = {
		constructor : Lazyload,
		init : function(){
			var self = this;
			for(var i=0;i<self.img.length;i++){
				var offsetTop = self.img[i].offsetTop;
				if(offsetTop < self.winHeight){
					self.img[i].src = self.img[i].getAttribute('data-img');
				}
			}
			window.onscroll = function(){
				this.scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
				for(var i=0;i<self.img.length;i++){
					var offsetTop = self.img[i].offsetTop;
					if((self.winHeight + this.scrollTop) > offsetTop){
						self.img[i].src = self.img[i].getAttribute('data-img');
					}
				}
			}
		}
	}

	global.Lazyload = Lazyload;
})(window);


(function($){
	$.fn.Lazyload = function(){
		var self = this;
		var winH = $(window).height();
		$.each(this,function(i,v){
			if($(this).offset().top < winH){
				$(this).attr('src',$(this).data('img'));
			}
		})
		$(window).on('scroll',function(){
			var scrollTop = $(this).scrollTop();
			$.each(self,function(i,v){
				var offsetTop = $(this).offset().top;
				if(winH + scrollTop > offsetTop){
					$(this).attr('src',$(this).data('img'));
				}
			})
		})
	}
})(Zepto);




