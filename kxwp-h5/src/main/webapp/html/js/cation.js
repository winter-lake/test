$(document).on('touchmove',function(e){e.preventDefault();})
var Cation = (function () {
    var cation = {
    	/*tab切换*/
    	tab:function(){
    		$('.right_main li').eq(0).show().siblings().hide();
    		$(".left_menu").on('click','li',function(){
    			var index=$(this).index();
    			$(this).addClass('cur').siblings().removeClass('cur');
    			$(".right_main li").eq(index).show().siblings().hide();
    		})
    	},
    	/*滚动条*/
    	scroller:function(){
    		function loadedleft () {
			  var myScroll = new IScroll('.scroll-left', { scrollX: true, freeScroll: true,click:true });
			  myScroll.refresh();
    		}
    		loadedleft();
    		function loadedright () {
  			  var myScroll = new IScroll('.scroll-right', { scrollX: true, freeScroll: true,click:true });
  			  myScroll.refresh();
      		}
      		loadedright();
    	},
    	/*分类渲染*/
    	cationrender:function(){
    		var url='/h5/classificaion/queryClassificaion.do';
    		common.submit_success(url,render);
    		function render(data){
    			consoel.log(data)
    		}
    	},
    	//初始化函数
	     init:function(){
	     	var self=this;
	     	self.tab();
	     	self.scroller();
	     	self.cationrender();
	     }
    }
    return cation;
})();

$(document).ready(function() {
		Cation.init();
});