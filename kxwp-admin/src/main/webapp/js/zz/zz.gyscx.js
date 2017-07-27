/** 服务站供应商查询 */
var fwz_gys = (function() {
	var gysQuery = {
		// 获取搜索参数事件 --开始
		getSearchPram : function() {
			var ajax_data = {
				name:$.trim($("#admin_name").val()) || "",
				supplierAdminMobile:$.trim($("#supplierAdminMobile").val()),
				id:$.trim($("#supplier_name").val()) || "",
				serviceStationId:$.trim($("#fwz_select").val()) || "",
				pager : {
					currentPage : kkpager.pno,
					pageSize : 10,
				}
			}
			return ajax_data;
		},// 获取搜索参数事件 --结束

		  /* 分页 */
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
        	// 有些参数是可选的，比如lang，若不传有默认值
        	kkpager.generPageHtml({
        		pno : pageNo,
        		// 总页码
        		total : totalPage,
        		// 总数据条数
        		totalRecords : totalRecords,
        		mode : 'click',// 默认值是link，可选link或者click
        		click : function(n){
        			this.selectPage(n);
        			_self.querySupermarket();
        			return false;
        		}
        	},true);
        },
		
		// 表格数据渲染
		renderTable : function(result) {
			var _self = this;
			//清空分页
			$('#kkpager').html("");
			if(result.data && result.data.length > 0){
				_self.paging(result);
			}
			
			var data = result.data;
			var html = "";
			$
					.each(
							data,
							function(i, val) {
								html += '<tr>'
										+ '<td><input type="checkbox"></td>'
										+ '<td>'
										+ (i+1)
										+ '</td>'
										+ '<td>'
										+  val.id + "/" +val.supplierNo
										+ '</td>'
										+ '<td>'
										+  val.id + "/" +val.supplierNo
										+ '</td>'
										+ '<td>'
										+ val.supplierAdmin
										+ '</td>'
										+ '<td>'
										+ val.supplierAdminMobile
										+ '</td>'
										+ '<td>'
										+ val.supplierName
										+ '</td>'
										+ '<td>'
										+ val.full_address
										+ '</td>'
										+ '<td>'
										+ val.phone
										+ '</td>'
										+ '<td>'
										+ val.createTime
										+ '</td>'
										+ '<td><a href="/zz/manager/gys/gotoGYSDetail.do?supplier_id='+val.id+'" class="show">查看</a></td>'
										+ '</tr>'
							})
			$("#gys_table tbody").html(html);
		},
		
		

		// 查询 --开始
		querySupermarket : function() {
			var _self = this;
			var ajax_data = _self.getSearchPram();

			$.ajax({
				url : '/zz/manager/gys/ajax/querygys.do',
				type : 'POST',
				dataType : 'json',
				data : JSON.stringify(ajax_data),
				contentType : "application/json",
				success : function(data) {
					_self.renderTable(data);
				}
			});
		},// 查询 --结束

		initPage : function() {
			var _self = this;
			KXWP.getEnumListByName("OrganizationStatusEnum","#gys_status");
			// 时间初始化
            KXWP.setStartEndTime($("#startDate"),$("#endDate"),{
                endMaxToday:false,
                preDays:365
            });
            KXWP.fwzSelect("#fwz_select");
            KXWP.gysSelect("#supplier_name");
			// 绑定查询事件
			$("#gyscx").on('click', function(event) {
				event.preventDefault();
				kkpager.pno = 1;
				//清除分页
				_self.querySupermarket();
			})
		}
	}

	gysQuery.initPage();
	return gysQuery;
})();