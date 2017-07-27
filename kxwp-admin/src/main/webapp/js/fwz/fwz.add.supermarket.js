/** 服务站超市添加* */
var fwz_cstj = (function() {
	var _fwz_cstj = {
		// 获取搜索参数事件 --开始
		getSearchPram : function() {
			var ajax_data = {
				startDateTime : $.trim($("#startDate").val()) || "",
				endDateTime : $.trim($("#endDate").val()) || "",
				supermarketStatus:$.trim($("#cs_zt").val()),
				serviceStationId:$.trim($("#fwz_select").val()),
				pager : {
					currentPage : kkpager.pno,
					pageSize : 1,
				}
			}
			return ajax_data;
		},// 获取搜索参数事件 --结束

		  /* 分页 */
        paging:function(result){ 
        	var _self = this;
        	var pager=result.pager;
        	function getParameter(name) { 
        		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
        		var r = window.location.search.substr(1).match(reg); 
        		if (r!=null) return unescape(r[2]); return null;
        	}
        	var totalPage = pager.totalPages;
        	var totalRecords = pager.totoalResults;
        	var pageNo = getParameter(pager.currentPage);
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
        	});
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
										+ val.phone
										+ '</td>'
										+ '<td>'
										+ val.supermarketName
										+ '</td>'
										+ '<td>'
										+ val.full_address
										+ '</td>'
										+ '<td>'
										+ (val.provinceName = null ? val.provinceName
												: "未启用")
										+ '</td>'
										+ '<td>'
										+ val.receptionName
										+ '</td>'
										+ '<td>'
										+ val.phone
										+ '</td>'
										+ '<td>'
										+ val.adress
										+ '</td>'
										+ '<td>'
										+ val.createTime
										+ '</td>'
										+ '<td>'
										+ val.updateTime
										+ '</td>'
										+ '<td class="status">'
										+ val.supermarketStatus_desc
										+ '</td>'
										+ '<td><a href="javascriptt:;" class="show">查看</a></td>'
										+ '</tr>'
							})
			$("#csmenu tbody").html(html);
		},
		
		

		// 查询 --开始
		querySupermarket : function() {
			var _self = this;
			var ajax_data = _self.getSearchPram();

			$.ajax({
				url : '/fwz/supermarket/ajax/querySuperMarketMessage.do',
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
			//绑定地址级联
			KXWP.addressSelect('#province','#city','#county','#town');
			/*KXWP.getEnumListByName("SupermarketStausEnum","#cs_zt");
			// 时间初始化
            KXWP.setStartEndTime($("#startDate"),$("#endDate"),{
                endMaxToday:false,
                preDays:365
            });
            KXWP.fwzSelect("#fwz_select");
			// 搜索条件校验
			// self.validSearchForm();
			// 绑定查询事件
			$("#cscx").on('click', function(event) {
				event.preventDefault();
				kkpager.pno = 1;
				//清除分页
				 $("#kkpager").html('<div id="kkpager"></div>');
				_self.querySupermarket();
			})*/
		}
	}

	_fwz_cstj.initPage();
	return _fwz_cstj;
})();