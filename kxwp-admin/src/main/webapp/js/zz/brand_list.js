/** 品牌查询* */
var ZZ_PPCX = (function() {
	var ppQuery = {
		//获取搜索参数
		getSearchPram : function(){
			var ajax_data = {
				brandName:$.trim($("#brand_name").val()),
				brandNameAbbr:$.trim($("#brand_name").val()),
				brandNo:$.trim($("#brand_no").val()),
				brandStatus:$.trim($("#brand_status").val()),
				pager : {
					currentPage : kkpager.pno,
					pageSize : 10,
				}
			}
			return ajax_data;
		},//获取查询条件参数--结束
	
		//分页
		paging:function(result){ 
			var _self = this;
        	var pager=result.pager;
        	var totalPage = pager.totalPages;
        	var totalRecords = pager.totoalResults;
        	var pageNo = pager.currentPage;
        	if(!pageNo){
        		pageNo = 1;
        	}
        	// 生成分页
        	kkpager.generPageHtml({
        		pno : pageNo,
        		// 总页码
        		total : totalPage,
        		// 总数据条数
        		totalRecords : totalRecords,
        		mode : 'click',// 默认值是link，可选link或者click
        		click : function(n){
        			this.selectPage(n);
        			_self.queryBrand();
        		}
        	},true);
		},
		
		//表格数据渲染
		renderTable : function(result){
			var _self = this;
			//清空分页
			$('#kkpager').html("");
			if(result.data && result.data.length > 0){
				_self.paging(result);
			}
			var data = result.data;
			var html = "";
			$.each(
					data,
					function(i,val){
				html += '<tr>'
					+ '<td>'
					+ (i+1)
					+ '</td>'
					+ '<td>'
					+ val.id+' / '+val.brandNo
					+'</td>'
					+ '<td>'
					+ val.brandName
					+'</td>'
					+ '<td>'
					+ '<img class="j_img_zoom" alt="暂无图片" style="width:120px;height:80px" title="' + val.brandName +  '" src='+val.photourl+'>'
					+'</td>'
					+'<td>'
					+ (val.describtion ==null?"":val.describtion)
					+'</td>'
					+'<td>'
					+(val.brandStatus == 'ONSALE'?'已上线':'')
					+(val.brandStatus == 'OFFSALE'?'已下线':'')
					+'</td>'
					+'<td>'
					+'<a href="/zz/ssBrand/getInit.do?id='+val.id+'" class="show">查看</a>'
					+(val.brandStatus == 'ONSALE'?'<a class="show" name="changeStatus" brandid="'+val.id+'" brandStatus="OFFSALE" id="down">下线</a>':'')
					+(val.brandStatus == 'OFFSALE'?'<a href="/zz/ssBrand/modifyInit.do?id='+val.id+'" class="show">编辑</a><a class="show" name="changeStatus" brandid="'+val.id+'" brandStatus="ONSALE" id="on">上线</a>':'')
					+'</td>'
					+'</tr>'
			})
			$("#brandtab tbody").html(html);
	},

	//查询--开始
	queryBrand : function(){
		var _self = this;
		var ajax_data = _self.getSearchPram();
		
		$.ajax({
			url:'/zz/ssBrand/ajax/queryBrandMessage.do',
			type:'POST',
			dataType:'json',
			data:JSON.stringify(ajax_data),
			contentType : "application/json",
			success : function(data) {
				_self.renderTable(data);
			}
		});
	},//查询结束
	
	initPage : function() {
		var _self = this;
		KXWP.getEnumListByName("BrandStatusEnum","#brand_status");
		
		$("#ppcx").on('click',function(event){
			event.preventDefault();
			kkpager.pno = 1;
			//清除分页
			$("#kkpager").empty();
			_self.queryBrand();
		});
		
		$(".table ").on('click','a[name=changeStatus]', function(){
	 		var msbrand = {};
	 		msbrand.id=$(this).attr('brandid');
	 		msbrand.brandStatus=$(this).attr('brandStatus');
	 		var title="";
	 		if($(this).attr('id')=='down'){
	 			title="确认下线选中品牌？";
	 		}
	 		else{
	 			title="确认上线选中品牌？";
	 		}
	 		
	 		if(window.confirm(title)){
	 			$.ajax({
					url :'/zz/ssBrand/changeStatus.do',
					type : 'POST',
					dataType : 'json',
					data : JSON.stringify(msbrand),
					contentType : "application/json",
					success : function(data) {
						if(data.callStatus == 'SUCCESS'){
							Common.kxpw_tishi('操作成功！')
							window.location.reload();
						}else{
							Common.kxpw_tishi('操作失败！')
						}
					}
				});
	 		}
	 		else
	 		{
	 			return false;
	 		}
		});
	 		
	  }
	}
	ppQuery.initPage();
	return ppQuery;
})();