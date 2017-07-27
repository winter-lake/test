
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
    	/*分类渲染*/
    	cationrender:function(){
            var _self = this;
    		var url='/h5/classificaion/ajax/queryClassificaion.do';
    		data={"parentClassficationID":0};
    		 /*发送请求*/
    	     common.submit_success(url,JSON.stringify(data),success);
    	     /*调用请求成功函数*/
    	     function success(data){
    	    	var menu=data.data;
    	 		var data = {
    	 				list: menu
    	 			};
    	 		var html = template('left_menu', data);
    	 		$(".left_menu").html(html);
                // 默认选中第一个
                _self.getCategoryList($('.left_menu li a').eq(0).data('id'));
                $(".left_menu li").eq(0).addClass('cur');
             }
    		$(".left_menu").on('click','a',function(){
    			var id=$(this).data('id');
    			_self.getCategoryList(id);
    		});
    	},
        // 点击左侧搜索
        getCategoryList: function(id) {
            var url='/h5/classificaion/ajax/queryClassificaion.do';
            /*发送请求*/
            common.submit_success(
                url,JSON.stringify({'parentClassficationID':id,"includeChildren":true}),success);
             /*调用请求成功函数*/
            function success(data){
                var right_list=data.data;
                var data = {
                        list: right_list
                    };
                var html = template('right_list', data);
                $(".right_main").html(html);
            }
        },
    	/*分类搜索*/
    	category_search:function(){
    		$(".category_search").on('click',function(){
    			var goods_name=$("#goods_name").val();
    			var url="/h5/goods/searchGoodsProxy.do?goods_name="+goods_name+"";
    			window.location.href=url;
    		})
    	},
    	/*点击跳转到列表页*/
    	go_list:function(){
    		$(".scroll-right").on('click','.data_id',function(){
    			var data_id=$(this).attr('data_id');
    			var url='/h5/goods/searchGoodsProxy.do?category_id_list='+data_id+'';
    			window.location=url;
    		})
    		$(".scroll-right").on('click','.title',function(){
    			var parent_id=$(this).attr('parent_id');
    			var url='/h5/goods/searchGoodsProxy.do?category_id_list='+parent_id+'';
    			window.location=url;
    		})
    	},
    	//初始化函数
	     init:function(){
	     	var self=this;
	     	self.tab();
	     	self.cationrender();
	     	self.category_search();
	     	self.go_list();
	     }
    }
    return cation;
})();

$(document).ready(function() {
	Cation.init();
});