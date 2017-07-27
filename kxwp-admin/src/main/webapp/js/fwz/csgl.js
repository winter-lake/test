/*超市js*/
var Csmenu = (function () {
    var csmenu = {
    	/*初始化页面*/
        init:function(){
        	var self=this;
        	self.render();
        	/*调用模糊查询*/
        	Common.select2($("#sel_menu2"),'/fwz/supermarket/ajax/queryServiceStationName.do');
        	Common.date(); //日历调用
        },
        querySupermarket:function(data,e){

    		e.preventDefault();
    		/*数据字段*/
    		var start=new Date($.trim($("#date-start").val()));//开始时间
    		var end=new Date($.trim($("#date-end").val()));   //结束时间
    		var zt=$("#zt option:selected").attr('name');//状态
    		var data={
    				 startDateTime:start,
       				 endDateTime :end,
       				 supermarketStatus:zt,
       				 /*分页必带*/
       				 pager:{
       					currentPage:1,//当前页码
       					pageSize   :10,//每页条数
       					totalPages :null,//总页码
       					totoalResults :null//总条数
       				 }
    		}
    		//渲染表格
    		Common.post('/fwz/supermarket/ajax/querySuperMarketMessage.do',JSON.stringify(data),renderdata);
    		function renderdata(result){
    			var data=result.data;
    			Common.paging(result);//分页调用
    			var html="";
				$.each(data,function(i,val){
					html+='<tr>'+
					 '<td><input type="checkbox"></td>'+
               		 '<td>'+(i+1)+'</td>'+
               		 '<td>'+val.phone+'</td>'+
               		 '<td>'+val.supermarketName+'</td>'+
               		 '<td>'+val.full_address+'</td>'+
               		 '<td>'+(val.serviceStationName == null ? "未匹配" :val.serviceStationName) +'</td>'+
               		 '<td>'+val.receptionName+'</td>'+
               		 '<td>'+val.phone+'</td>'+
               		 '<td>'+val.adress+'</td>'+
               		 '<td>'+val.createTime+'</td>'+
               		 '<td>'+val.updateTime+'</td>'+
               		 '<td class="status">'+val.supermarketStatus_desc+'</td>'+
               		 '<td><a href="javascriptt:;" class="show">查看</a></td>'+
               	   '</tr>'
				})
    			$("#csmenu tbody").html(html);
    		}
    	
        },
        /*渲染页面*/
        render:function(){
        	self = this;
        	var start=Common.date("#date-start"),
        		end=Common.date("#date-end");
        	$("#cscx").click(function(e){
        		e.preventDefault();
        		/*数据字段*/
        		var start=new Date($.trim($("#date-start").val()));//开始时间
        		var end=new Date($.trim($("#date-end").val()));   //结束时间
        		var zt=$("#zt option:selected").attr('name');//状态
        		var data={
        				 startDateTime:start,
	       				 endDateTime :end,
	       				 supermarketStatus:zt,
	       				 /*分页必带*/
	       				 pager:{
	       					currentPage:1,//当前页码
	       					pageSize   :1,//每页条数
	       					totalPages :null,//总页码
	       					totoalResults :null//总条数
	       				 }
        		};
        		self.querySupermarket(data,e);
        	});
        }
    }
    return csmenu;
})();


Csmenu.init();//初始化页面


