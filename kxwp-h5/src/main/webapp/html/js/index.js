$(document).on('touchmove',function(e){e.preventDefault();})
// index
function Index(){
  var self=this;
  //跳转到搜索页
  this.href=function(){
  	$("#search").focus(function(){
  		window.location.href="sy-search.html";
  	})
  }
  /*焦点图*/
  this.swiper=function(){
     var mySwiper = new Swiper ('.swiper-container', {
      loop: true,
      // 如果需要分页器
      pagination: '.swiper-pagination',
      // 如果需要前进后退按钮
      nextButton: '.swiper-button-next',
      prevButton: '.swiper-button-prev',
      preventClicks :false,
      autoplay:1,
      speed:5000,
      loop:true,
      freeMode:true
    })  
  }
  /*滚动条*/
  this.scroll=function(){
  	
  }
  /*初始化页面*/
  this.init=function(){
  	var self=this;
  	self.href();
    self.swiper();
    self.scroll();
  }
}
var index=new Index();


$(function(){
	index.init();
})
