$(document).on('touchmove',function(e){e.preventDefault();})
// index
function Shop_view(){
  var self=this;
  /*焦点图*/
  this.swiper=function(){
     var mySwiper = new Swiper ('.swiper-container', {
      loop: true,
      // 如果需要分页器
      pagination: '.swiper-pagination',
      // 如果需要前进后退按钮
      nextButton: '.swiper-button-next',
      prevButton: '.swiper-button-prev',
      preventClicks :false
    })  
  }
  /*滚动条*/
  /*初始化页面*/
  this.init=function(){
  	var self=this;
    self.swiper();
    common.scroll($("#content"));
  }
}
var shopview=new Shop_view();


$(function(){
	shopview.init();
})