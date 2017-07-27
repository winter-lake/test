/*超市js*/
var Csmenu = (function () {
    var csmenu = {
    	/*初始化页面*/
        init:function(){
        	var self=this;
        	self.render();
        	 // 获取包装规格枚举值
            KXWP.getEnumListByName({
                enumName:"FWZTypeEnum", // 枚举的名字
                selectDom:"#goods_unit", // select dom
                valueFiled:"code", // option的value 用的是返回结果的哪个字段
                // isZero:true,//是否显示为空的
            });

        	/*调用模糊查询*/
        	Common.select2($("#sel_menu2"),'/zz/supermarket/ajax/queryServiceStationName.do');
        //	Common.paging();//分页调用
        	Common.date(); //日历调用
        	
        },
        /*渲染页面*/
        render:function(){
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
	       				 pager:{
	       					currentPage:1,
	       					pageSize   :10
	       				 }
        		}
        		//渲染表格
        		Common.post('/zz/supermarket/ajax/querySuperMarketMessage.do',JSON.stringify(data),renderdata);
        		function renderdata(result){
        			console.log(result)
        		}
        	})
        	/*渲染状态请求*/
    		Common.post('/common/ajax/getEnumListByName.do?enumname=SupermarketStausEnum',"",zt);
    		function zt(result){
    			var data=result.data;
        		var html = '<option>请选择</option>';
        		$.each(data,function(i,val){
                    html+='<option name="'+val.name+'">'+val.desc+'</option>'
                })
    			$("#zt").html(html);
        	}
        }
    }
    return csmenu;
})();


Csmenu.init();//初始化页面


