
function Good_detail(){
  /*初始化页面*/
  this.init=function(){
  	var self=this;
  	self.swiper();
  	self.favorites();
  }
  /*焦点图*/
  this.swiper=function(){
     var mySwiper = new Swiper ('.swiper-container', {
    	 loop: true,
	      pagination: '.swiper-pagination',//分页器
	      preventClicks :true,
	      autoplay:2000,
	      scrollbar:null,
         slidesPerView: 'auto',
         paginationClickable: true,
         spaceBetween: 30
    })  
  }
  /*点击收藏*/
  this.favorites=function(){
	  var self=this;
	  $('.j_favorites').on('click',function(){
	  	  var self=$(this);
		  var collectionId=Number(location.search.split('=')[1]);
		  var data={
				  createCollectionItemForm_list:[{
					  collectionId:collectionId,
					  collectionType:"GOODS"
				  }]
		  }
		  $.ajax({
			  url:'/h5/v2/collectionItem/addCollectionItem.do',
			  type:'post',
			  dataType:'json',
			  contentType:"application/json",
			  data:JSON.stringify(data),
			  success:function(data){
				  show(data);
			  }
		  })
		  function show(data){
			  if(data.callStatus=="SUCCESS" ){
				  common.msg("收藏成功");
				  $(self).addClass('cur');
				  $(this).addClass('cur');
			  }else if(data.callStatus=="FAIL"){
				  common.msg(data.message);
			  }
		  }
	  })
  }
}
var good_detail=new Good_detail();

$(function(){
	good_detail.init();
	KxApp.initAddToShopCar();
})
