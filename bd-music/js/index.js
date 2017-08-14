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
		render : function(data,box){
			var view = this;
	            var tpldata = {
	                list: data
	            };
	            var html = template(index_tpl, tpldata);
	            $(box).innerHTML = html;
		},
		ajax : function(){
			var self = this;
			SongModel.index.tj().then(function(data){
				if(data.error_code == '22000'){
					share.endLoading();
					self.render(data.result.list,self.tj_song);
				}
			})
		},
		init : function(){
			this.$el = document.getElementById('b-wrap');
			this.initEvent();
			this.ajax();
			this.tj_song = this.$el.querySelector('.tj-song');
			share.startLoading();
		}
	};
	page.init();
})()