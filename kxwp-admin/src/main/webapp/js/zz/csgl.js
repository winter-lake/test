/*超市管理js*/
var Csmenu = (function () {
    var csmenu = {
    	/*初始化页面*/
        init:function(){
        	var self=this;
        	self.render();
        	self.renderzt();
        	/*调用模糊查询*/
        	Common.select2($("#sel_menu2"),'/zz/supermarket/ajax/queryServiceStationName.do');
        	Common.date(); //日历调用

            // 省市区级联
            KXWP.addressSelect("#j_province", '#j_city', '#j_county' , '#j_town');
        },
        renderdata:function(result){
			self.paging(result);
			var data=result.data;
			var html="";
			$.each(data,function(i,val){
				html+='<tr>'+
				 '<td><input type="checkbox"></td>'+
           		 '<td>'+val.id+'</td>'+
           		 '<td>'+val.phone+'</td>'+
           		 '<td>'+val.supermarketName+'</td>'+
           		 '<td>'+val.full_address+'</td>'+
           		 '<td>'+(val.provinceName=null?val.provinceName:"未启用")+'</td>'+
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
		},
        
        querySupermarket:function(n){
        	
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
       					currentPage:n == null ? 0 :n,//当前页码
       					pageSize   :1,//每页条数
       				 }
    		}
    		
    		  $.ajax({
                  url: '/zz/supermarket/ajax/querySuperMarketMessage.do',
                  type: 'POST',
                  dataType: 'json',
                  data: JSON.stringify(data),
                  contentType: "application/json",
                  success: function(data) {
                	  renderdata(data);
                  }
              });
        },
        
        /*渲染页面*/
        render:function(){
        	var self=this;
        	$("#cscx").click(function(e){
        		self.querySupermarket(1);
        	})
        },
        /*渲染状态请求*/
        renderzt:function(){
    		Common.post('/common/ajax/getEnumListByName.do?enumname=SupermarketStausEnum',"",zt);
    		function zt(result){
    			var data=result.data;
        		var html = '<option>请选择</option>';
        		$.each(data,function(i,val){
                    html+='<option name="'+val.name+'">'+val.desc+'</option>'
                })
    			$("#zt").html(html);
        	}
        },
        /*分页*/
        paging:function(result){ 
        	var self=this;
        	var pager=result.pager;
        	var totalPage = pager.totalPages;
        	var totalRecords = pager.totoalResults;
        	var pageNo = pager.currentPage;
        	if(!pageNo){
        		pageNo = 1;
        	}
        	//生成分页
        	//有些参数是可选的，比如lang，若不传有默认值
        	kkpager.generPageHtml({
        		pno : pageNo,
        		//总页码
        		total : totalPage,
        		//总数据条数
        		totalRecords : totalRecords,
        		mode : 'click',//默认值是link，可选link或者click
        		click : function(n){
        			querySupermarket(n);
        			this.selectPage(n);
        			return false;
        		}
        	},true);
        }
    }
    return csmenu;
})();


Csmenu.init();//初始化页面


