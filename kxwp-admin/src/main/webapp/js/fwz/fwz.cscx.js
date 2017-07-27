/** 服务站超市查询* */
var fwz_cscx = (function() {
	var cscxQuery = {
		// 获取搜索参数事件 --开始
		getSearchPram : function() {
			var ajax_data = {
				startDateTime : $.trim($("#startDate").val()) || "",
				endDateTime : $.trim($("#endDate").val()) || "",
				supermarketStatus:$.trim($("#cs_zt").val()),
				serviceStationId:$.trim($("#fwz_select").val()),
				pager : {
					currentPage : kkpager.pno,
					pageSize : 10,
				}
			}
			var supermarket_id =$("#supermarket_name").val();
			if(supermarket_id){
				ajax_data.id=supermarket_id;
			}
			
			//由于目前超市账号手机号与收货人手机号一致，为了方便，先使用收货人手机号进行查询
			var supermarket_phone =$("#supermarket_phone").val();
			if(supermarket_phone){
				ajax_data.phone=supermarket_phone;
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
										+ '<td>'
										+ (i+1)
										+ '</td>'
										+ '<td>'
										+ val.accountMobile
										+ '</td>'
										+ '<td>'
										+ val.supermarketName
										+ '</td>'
										+ '<td>'
										+ val.full_address
										+ '</td>'
										+ '<td>'
										+ val.receptionName
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
										+ '<td>'
										+ '<a href="getSupermarketId.do?id='+val.id+'" class="show">查看</a>'
										+ (val.supermarketStatus == 'ON' ? '<a href="#" name="stop" supermarketId="'+val.id+'" class="show">停用</a>':'')
										+ (val.supermarketStatus == 'OFF' ? '<a href="#" name="start" supermarketId="'+val.id+'" class="show">启用</a>':'')
										+ '</td>'
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
			KXWP.getEnumListByName("SupermarketStausEnum","#cs_zt");
			// 时间初始化
            KXWP.setStartEndTime($("#startDate"),$("#endDate"),{
                endMaxToday:false,
                preDays:365
            });
            KXWP.fwzSelect("#fwz_select");
            KXWP.supermarketRepoSelectByName("#supermarket_name")
			// 搜索条件校验
			// self.validSearchForm();
			// 绑定查询事件
			$("#cscx").on('click', function(event) {
				event.preventDefault();
				kkpager.pno = 1;
				//清除分页
				 $("#kkpager").html('<div id="kkpager"></div>');
				_self.querySupermarket();
			})
			$("table").on("click","a[name=stop]",function(){
				var supermarketId = $(this).attr("supermarketId");
				layer.prompt({
					  title: '请输入停用理由',
					  formType: 2 //prompt风格，支持0-2
					}, function(pass){
						var form = {};
						
						form.id = supermarketId;
						form.supermarketStatus = "OFF";
						form.statusUpdateDescription = pass;
						
						$.ajax({
							type:"POST",
							url:"/fwz/supermarket/updateSupermarketStatus.do",
							dataType:"json",
							data:JSON.stringify(form),
							contentType:"application/json",
							success:function(){
								layer.alert('操作成功', function(){
									window.location.reload();
								});
							},
							error:function(){
								layer.alert('操作失败，请联系管理员', function(){
									window.location.reload();
								});
							}
						});
					});
			});
            $("table").on("click","a[name=start]",function(){
        		var form = {};
        		
        		form.id = $(this).attr("supermarketId");
        		form.supermarketStatus = "ON";
        		form.statusUpdateDescription = "启用超市成功";
        		
        		$.ajax({
        			type:"POST",
        			url:"/fwz/supermarket/updateSupermarketStatus.do",
        			dataType:"json",
        			data:JSON.stringify(form),
        			contentType:"application/json",
        			success:function(){
        				layer.alert('操作成功', function(){
        					window.location.reload();
        				});
        			},
        			error:function(){
        				layer.alert('操作失败，请联系管理员', function(){
        					window.location.reload();
        				});
        			}
        		});
            });
		}
	}

	cscxQuery.initPage();
	return cscxQuery;
})();