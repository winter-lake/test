(function(global){
	global.page = {
		initEvent : function(){
			var self = this;
			this.more_fl.onclick = function(){
				if(self.flag){
					self.all_fl.style.display = "block";
					self.flag = false;
					this.innerHTML = "收起全部分类";
				}else{
					self.all_fl.style.display = "none";
					self.flag = true;
					this.innerHTML = "展开全部分类";
				}
			}
		},
		ajaxRender : function(){
			var self = this;
			SongModel.category.hotList().then(function(data){
				if(data.error_code == '22000'){
					console.log(data)
					var html = "";
					var obj = data.song_list;
					for(var i=0;i<obj.length;i++){
						html+='<div class="hot-song-item col-6">'+
                        '<a href="javascript:;">'+
                        '<div class="img-box">'+
                            '<img src="'+obj[i].pic_big+'" alt="">'+
                            '<span class="iconfont icon-play">&#xe601;</span>'+
                        '</div>'+
                        '</a>'+
                        '<div class="Language">'+obj[i].language+'</div>'+
                        '<div class="item-msg">'+obj[i].si_proxycompany+'</div>'+
                    '</div>'
					}
					self.hot_song_list.innerHTML = html;
				}
			})
		},
		//页面初始化
		init : function(){
			//页面容器
			this.$el = document.querySelector('#b-wrap');
			this.flag = true;	
			this.more_fl = this.$el.querySelector('.more-fl');
			this.all_fl = this.$el.querySelector('.all-fl');
			this.initEvent();
			this.hot_song_list = this.$el.querySelector('.hot-song-list');
			this.ajaxRender();
		}
	}
	page.init();
})(window)