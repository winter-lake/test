/*超市js*/
var Csmenu = (function () {
    var csmenu = {
    	/*初始化页面*/
        init:function(){
        	var self=this;
        	self.initrender();
        	self.render();
        },
        render:function(){
        	var start=Common.date("#date-start"),
        		end=Common.date("#date-end");
        	$("#cscx").click(function(e){
        		e.preventDefault();
        		/*数据字段*/
        		var data={
        			start             : $("#date-start").val(),//起始日期
        			end               : $("#date-end").val(),//结束日期
        			val               : $("#ssfwz").val(),//所属服务站
        			val1              : $("#sjh").val(),//手机号
        			zt                : $("#zt option:selected").html(),
        			totalPages        : "",//总页数
        			totalRecords      : "",//总条数
        			pageNo            : "",//当前页吗
        			pagenum           : 10  //每页显示条数
        		}
        		//ajax
        		Common.post(url,data,renderdata);
        		//渲染表格
        		function renderdata(result){
        			
        		}
        	})
        },
        initrender:function(){{
        	//状态请求
        	Common.post(url,zt);
        	//所属服务站请求
        	Common.post(url,ssfwz);
        }}

    }
    return csmenu;
})();


