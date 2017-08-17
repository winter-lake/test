(function(global){
	global.page = {
		initEvent : function(){
			var self = this;
			this.letter_icon.onclick = function(){
				share.markTip();
				self.letter_all_box.style.height = 5.8 + 'rem';
			}
			this.letter_close.onclick = function(){
				self.letter_all_box.style.height = 0;
				share.removeMark();
			}
		},
		renderLetter : function(){
			//渲染字母
			var letter = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
			var html = "";
			for(var i=0;i<letter.length;i++){
				html += '<div class="letter-item col-2">'+letter[i]+'</div>';
			}
			this.letter_box.innerHTML = html;
		},
		renderList : function(){
			var self = this;
			SongModel.singer.singerList().then(function(data){
				var html = "";
				for(var i=0;i<data.length;i++){
					html+= '<div class="singer-item">'+
						'<dl>'+
							'<dt><img src="'+data[i].img+'"></dt>'+
							'<dd>'+data[i].singer+'</dd>'+
						'</dl>'+
					'</div>'
				}
				self.singer_list.innerHTML = html;
			})
		},
		init : function(){
			this.$el = document.querySelector('#b-wrap');
			this.letter_all_box = this.$el.querySelector('.letter-all-box');
			this.letter_box = this.$el.querySelector('.letter-box');
			this.letter_icon = this.$el.querySelector('.letter-icon');
			this.letter_close = this.$el.querySelector('.letter-close');
			this.singer_list = this.$el.querySelector('.singer-list');
			this.renderLetter();
			this.initEvent();
			this.renderList();
		}
	}
	page.init();
})(window)