$(document).on('touchmove',function(e){e.preventDefault();})
// 首页搜索
function search_input(){
  var self=this;
  this.search=function(){
	  /*商品/供应商搜索*/
	  $("#search").on('click',function(){
		  var input_search=$.trim($("#search_val").val());//输入框值
		  var select_val=$("#select_val").val();//下拉框的值
		  if(input_search ==''){
          	common.msg("请输入搜索关键字");
          	return;
          }
		  if(select_val=="商品"){
			  var url="/h5/goods/searchGoodsProxy.do?goods_name="+input_search+"";
			  window.location.href=url; 
		  }else if(select_val=="供应商"){
			  var url="/h5/supplier/searchSupplierProxy.do?supplier_name="+input_search+"";
			  window.location.href=url;
		  }
	  })
  }
  /*小功能*/
  this.fn=function(){
	  /*清空搜索历史*/
	  $("#clear").on('click',function(){
		  $(".his-list>.p_list").remove();
	  })
  }
  /*初始化页面*/
  this.init=function(){
  	var self=this;
  	self.search();
  	self.fn();
  }
}
var search_input=new search_input();


$(function(){
	search_input.init();
})
