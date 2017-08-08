function Swiper(parent,options){
	this.parent = typeof parent === 'string' ? document.querySelector(parent) : parent;
	this.wrapper = this.parent.querySelector('.swiper-wrap');
	this.swiperSlide = this.wrapper.children;//wrapper的子元素
	this.slideLen = this.swiperSlide.length;//滑块的长度
	this.slideW = this.parent.offsetWidth;//一张滑块的宽度
	this.index = 0;
	this.slideEnd = options.slideEnd;
	this.timer = null;
	this.autoplay = options.autoplay ? this.timer = setInterval(this.autoplay.bind(this),options.autoplay) : false;
	this.init();
}

Swiper.prototype = {
	constructor : Swiper,//将原型指向原来的构造函数
	//初始化swiper事件
	init : function(){
		this.wrapper.style.width = this.slideLen * this.slideW + 'px';//设置wrapper的宽度
		this.initEvent();
	},
	initEvent : function(){
		var self = this;
		var startX,moveX,endX,moveSize,numX;

		this.wrapper.addEventListener('touchstart',function(e){
			clearInterval(self.timer)
			var touchs = e.touches[0];
			startX = touchs.pageX; 
			numX = -self.index * self.slideW;
			self.wrapper.classList.remove('animate');
		},false)

		this.wrapper.addEventListener('touchmove',function(e){
			var touchs = e.touches[0];
			moveX = touchs.pageX;
			moveSize = moveX - startX;
			self.wrapper.style.transform = 'translate3d('+(moveSize + numX)+'px,0,0)';
		},false)

		this.wrapper.addEventListener('touchend',function(){
			//向右滑
			if(moveX - startX > 50){
				self.index--;
				if(self.index < 0){
					self.index = 0;
				}
			}
			//向左滑
			if(moveX - startX < -50){
				self.index++;
				if(self.index > self.slideLen - 1){
					self.index = self.slideLen - 1;
				}
			}
			self.slideMove();
			self.timer = setInterval(self.autoplay,1500)
		},false)
	},
	slideMove : function(){
		this.wrapper.style.transform = 'translate3d('+(-this.index * this.slideW)+'px,0,0)';
		this.wrapper.classList.add('animate');
		this.slideEnd(this.index);
	},
	autoplay : function(){
		this.index++;
		if(this.index > this.slideLen - 1){
			this.index = 0;
		}
		this.slideMove();
	}
}