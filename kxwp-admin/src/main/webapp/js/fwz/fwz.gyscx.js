/** 总站供应商查询 */
var fwz_gys = (function() {
	var gysQuery = {
		// 获取搜索参数事件 --开始
		getSearchPram : function() {
			var ajax_data = {
				name:$.trim($("#gys_admin_name").val()),
				supplierAdminMobile:$.trim($("#supplierAdminMobile").val()),
				id:$.trim($("#supplier_name").val()),
				supplierStatus:$.trim($("#cs_zt").val()),
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
			// 先清空分页
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
										+ '<td>'
										+ (val.supplierStatus == 'VALID' ? '已启用' : '')
											+ (val.supplierStatus == 'UNVALID' ? '已停用' : '')
												+ (val.supplierStatus == 'REVIEW' ? '待审核' : '')
													+ (val.supplierStatus == 'REJECT' ? '未通过' : '')
										+ '</td>'
										+ '<td>'
										+ '<a href="/fwz/manager/gys/gotoGYSDetail.do?supplier_id='+val.id+'" class="show">查看</a>'
										+ (val.supplierStatus == 'VALID' ? '<a href="#" name="stop" class="show" supplierId="'+val.id+'">停用</a>' : '')
										+ (val.supplierStatus == 'UNVALID' ? '<a href="#" name="start" class="show" supplierId="'+val.id+'">启用</a>' : '')
										+ '<a href="/fwz/manager/gys/gotoEditGys.do?id='+val.id+'" class="show">编辑</a>'
										+ '</td>'
										+ '</tr>'
							})
			$("#gys_table tbody").html(html);
		},
		
		

		// 查询 --开始
		querySupermarket : function() {
			var _self = this;
			var ajax_data = _self.getSearchPram();

			$.ajax({
				url : '/fwz/manager/gys/ajax/querygys.do',
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
			$("table").on("click","a[name=stop]",function(){
				var supplier = {};
				
				supplier.id = $(this).attr("supplierId");
				supplier.supplierStatus = 'UNVALID';
				
				layer.prompt({
					  title: '确认停用？',
					  formType: 2 //prompt风格，支持0-2
					}, function(pass){
						supplier.statusChangeReason = pass;
						$.ajax({
							type:"POST",
							url:"/fwz/manager/gys/updateMixture.do",
							dataType:"json",
							data:JSON.stringify(supplier),
							contentType:"application/json",
							success:function(){
								layer.alert('操作成功', function(index){
									  window.location.reload();
									}); 
							},
							error:function(){
								layer.alert('操作失败，请联系管理员', function(index){
									  window.location.reload();
									});
							}
						});
					});
			})
			
			$("table").on("click","a[name=start]",function(){
				var supplier = {};
				
				supplier.id = $(this).attr("supplierId");
				supplier.supplierStatus = 'VALID';
				
				$.ajax({
					type:"POST",
					url:"/fwz/manager/gys/updateMixture.do",
					dataType:"json",
					data:JSON.stringify(supplier),
					contentType:"application/json",
					success:function(){
						layer.alert('操作成功', function(index){
							  window.location.reload();
							}); 
					},
					error:function(){
						layer.alert('操作失败，请联系管理员', function(index){
							  window.location.reload();
							});
					}
				});
			})
			
			$.ajax({
		        url:"/common/ajax/getEnumListByName.do?enumname=OrganizationStatusEnum",
		        type:'post',
		        dataType:'json',
		        success:function(data){
		        	var html = '';
		        	
		            $.each(data.data,function(i,val){
		                html+='<option value="'+val.name+'">'+val.desc+'</option>'
		            })
		            
		            $('#cs_zt').html(html);
		            $("#cs_zt option:last").remove();
		            $("#cs_zt option:eq(0)").text('已启用');
		            $("#cs_zt option:eq(1)").text('已停用');
		            $("#cs_zt option:eq(2)").text('待审核');
		            $("#cs_zt option:eq(3)").text('未通过');
		        }
		    })
			var _self = this;
			// 时间初始化
            KXWP.setStartEndTime($("#startDate"),$("#endDate"),{
                endMaxToday:false,
                preDays:365
            });
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