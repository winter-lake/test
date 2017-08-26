(function(){
	var page = {
		initEvent : function(){
			var bar = this.$el.querySelectorAll('.bar');
			var bd_list = this.$el.querySelectorAll('.bd-list');
			for(var i=0;i<bar.length;i++){
				bar[i].index = i;
				bar[i].onclick = function(){
					for(var i=0;i<bar.length;i++){
						bar[i].classList.remove('on');
						bd_list[i].classList.remove('on');
					}
					this.classList.add('on');
					bd_list[this.index].classList.add('on');
				}
			}
		},
		ajax : function(){
			var self = this;
			//推荐
			SongModel.index.tj().then(function(data){
				if(data.error_code == '22000'){
					var html = "";
					var obj = data.result.list;
					for(var i=0;i<obj.length;i++){
						html+='<div class="tj-song-item col-4">'+
							'<a href="#'+obj[i].song_id+'">'+
								'<div class="song-img">'+
									'<img src="img/load.jpg" data-img="'+obj[i].pic_big+'" class="image">'+
									'<span class="iconfont icon-play">&#xe601;</span>'+
								'</div>'+
								'<div class="song-name">'+obj[i].title+'</div>'+
							'</a>'+
						'</div>'
					}
					self.tj_song.innerHTML = html;
				}
			})
			//新歌速递
			SongModel.index.newSong().then(function(data){
				if(data.error_code == '22000'){
					var html = "";
					var obj = data.song_list;
					for(var i=0;i<obj.length;i++){
						html+='<div class="new-song-item col-4">'+
								'<a href="javascript:;">'+
									'<div class="song-img">'+
										'<img src="img/load.jpg" data-img="'+obj[i].pic_big+'">'+
										'<span class="iconfont icon-play">&#xe601;</span>'+
									'</div>'+
									'<div class="song-name">'+obj[i].title+'</div>'+
									'<div class="new-song-author">'+obj[i].author+'</div>'+
								'</a>'+
							  '</div>'
					}
					self.new_song_list.innerHTML = html;
					//$('self.new_song_list img').Lazyload();
				}
			})
			//音乐榜单
			/*热歌榜*/
			SongModel.index.hotSong().then(function(data){
				if(data.error_code == '22000'){
					var html = "";
					var obj = data.song_list;
					for(var i=0;i<obj.length;i++){
						html+='<dl class="bd-item">'+
									'<dt>'+
										'<img src="img/load.jpg" data-img="'+obj[i].pic_big+'">'+
										'<span class="ranking">'+obj[i].album_no+'</span>'+
									'</dt>'+
									'<dd>'+
										'<span class="iconfont top">&#xe607;</span>'+
										'<div class="bd-msg">'+
											'<div class="bd-sing-name">'+obj[i].title+' <i class="iconfont member">&#xe501;</i></div>'+
											'<div class="bd-sing-author">'+obj[i].author+'</div>'+
										'</div>'+
										'<span class="iconfont download">&#xe61d;</span>'+
									'</dd>'+
								'</dl>'
					}
					$(html).insertBefore($(self.hot.querySelector('.show-more')));
					///$('self.hot img').Lazyload();
				}
			})
			/*新歌榜*/
			SongModel.index.NewSong().then(function(data){
				if(data.error_code == '22000'){
					var html = "";
					var obj = data.song_list;
					for(var i=0;i<obj.length;i++){
						html+='<dl class="bd-item">'+
								'<dt>'+
								'<img src="img/load.jpg" data-img="'+obj[i].pic_big+'">'+
								'<span class="ranking">'+obj[i].album_no+'</span>'+
								'</dt>'+
								'<dd>'+
								'<span class="iconfont top">&#xe607;</span>'+
								'<div class="bd-msg">'+
								'<div class="bd-sing-name">'+obj[i].title+' <i class="iconfont member">&#xe501;</i></div>'+
								'<div class="bd-sing-author">'+obj[i].author+'</div>'+
								'</div>'+
								'<span class="iconfont download">&#xe61d;</span>'+
								'</dd>'+
							  '</dl>'
					}
					$(html).insertBefore($(self.new.querySelector('.show-more')));
					//$('self.new img').Lazyload();
				}
			})
			/*king榜*/
			SongModel.index.kingSong().then(function(data){
				if(data.error_code == '22000'){
					var html = "";
					var obj = data.song_list;
					for(var i=0;i<obj.length;i++){
						html+='<dl class="bd-item">'+
							'<dt>'+
							'<img src="img/load.jpg" data-img="'+obj[i].pic_big+'">'+
							'<span class="ranking">'+obj[i].album_no+'</span>'+
							'</dt>'+
							'<dd>'+
							'<span class="iconfont top">&#xe607;</span>'+
							'<div class="bd-msg">'+
							'<div class="bd-sing-name">'+obj[i].title+' <i class="iconfont member">&#xe501;</i></div>'+
							'<div class="bd-sing-author">'+obj[i].author+'</div>'+
							'</div>'+
							'<span class="iconfont download">&#xe61d;</span>'+
							'</dd>'+
							'</dl>'
					}
					$(html).insertBefore($(self.King.querySelector('.show-more')));
					//$('self.King img').Lazyload();
				}
			})
			/*热门歌单*/
			SongModel.index.hotList().then(function(data){
				if(data.error_code == '22000'){
					var html = "";
					var obj = data.song_list;
					for(var i=0;i<obj.length;i++){
						html+='<div class="hot-song-item col-4">'+
									'<a href="javascript:;">'+
										'<div class="song-img">'+
											'<img src="img/load.jpg" data-img="'+obj[i].pic_big+'">'+
										'</div>'+
										'<div class="song-name">'+obj[i].title+'</div>'+
									'</a>'+
							   '</div>'
					}
					$(self.hot_song).html(html);
					//$('self.hot_song img').Lazyload();
				}
			})
			/*mv*/
			// SongModel.index.mvList().then(function(data){
			// 	if(data.error_code == '22000'){
			// 		var html = "";
			// 		var obj = data.song_list;
			// 		for(var i=0;i<obj.length;i++){
			// 			html+='<div class="mv-item">'+
			// 				'<a href="javascript:;">'+
			// 				'<div class="mv-song-img">'+
			// 				'<img src="'+obj[i].pic_big+'">'+
			// 				'</div>'+
			// 				'<div class="mv-name">'+obj[i].title+'</div>'+
			// 			'<div class="mv-author">'+obj[i].author+'</div>'+
			// 				'</a>'+
			// 				'</div>'
			// 		}
			// 		$(self.mv_List).html(html);
			// 	}
			// })
			/*audio*/
			SongModel.index.audio().then(function(data){
				if(data.error_code == '22000'){
					var audio = document.querySelector('.player audio');
					var source = document.querySelector('.player audio source');
					audio.src = data.bitrate.file_link;
					source.src = data.bitrate.file_link;
				}
			})
			/*lrc*/
			SongModel.index.lrc().then(function(data){
				
			})
		},
		init : function(){
			this.$el = document.getElementById('b-wrap');
			this.initEvent();
			this.ajax();
			this.tj_song = this.$el.querySelector('.tj-song');
			this.new_song_list = this.$el.querySelector('.new-song-list');
			this.hot = this.$el.querySelector('.hot');
			this.new = this.$el.querySelector('.new');
			this.King = this.$el.querySelector('.King');
			this.hot_song = this.$el.querySelector('.hot-song');
			this.mv_List = this.$el.querySelector('.mv-list');
		}
	}
	page.init();
})()


