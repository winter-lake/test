(function($){
	var index = {
		render : function(){
			var self = this;
			$.ajax({
				url:'data.json',
				success:function(req){
					render(req);
				}
			})
			var render = function(data){
				var html = "";
				for(var i=0;i<data.length;i++){
					html+= `
						<dl class="col-xs-6">
							<dt><img src="${data[i].img}"></dt>
							<dd>
								<div class="message">
									${data[i].msg}
								</div>
								<div class="price">
									${data[i].price}
								</div>
							</dd>
						</dl>
					   `
				}
				self.$el.innerHTML += html;
			}		
		},
		scroll : function(){
			var self = this;
			this.top = document.documentElement.scrollTop || document.body.scrollTop;
			this.lastTop = this.$el.lastElementChild.offsetTop;	
			if(this.top + this.winh > this.lastTop){
				window.onscroll = null;
				self.render();
				window.onscroll = this.scroll.bind(this);
			}
		},
		init : function(){
			var self = this;
			this.$el = document.querySelector('.goods-list-box');
			this.winh = window.innerHeight;
			self.render();
			window.onscroll = self.scroll.bind(this);//给window绑方法this指向指向window必须手动改回
		}
	}
	index.init()
})(Zepto)