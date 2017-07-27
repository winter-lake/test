/** 广告查询* */
var FWZ_ADCX = (function() {
	var cscxQuery = {
		// 获取搜索参数事件 --开始
		getSearchPram : function() {
			var ajax_data = {
				name : $.trim($("#name").val()),
				adStatus : $.trim($("#adStatus").val()) ||'',
				adType : $.trim($("#adType").val()),
				adLocation : $.trim($("#adLocation").val()),
				startDateTime : $.trim($("#startDateTime").val()),
				endDateTime : $.trim($("#endDateTime").val()),
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
        			_self.queryAD();
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
										+  val.name
										+ '</td>'
										+ '<td><img class="j_img_zoom" width ="56px" src="'
										+  (val.photoList.length == 0 ? "" : val.photoList[val.photoList.length-1])
										+ '"></img></td>'
										+ '<td>'
										+ (val.platformType == 'MOBILE'?'手机端':'')
										+ (val.platformType == 'PC'?'PC端':'')
										+ '</td>'
										+ '<td>'
										+  (val.adLocation == 'A'?'A区':'')
										+  (val.adLocation == 'B'?'B区':'')
										+  (val.adLocation == 'C'?'C区':'')
										+  (val.adLocation == 'D'?'D区':'')
										+  (val.adLocation == 'E'?'E区':'')
										+  (val.adLocation == 'APP_START'?'APP闪屏':'')
										+ '</td>'
										+ '<td>'
										+  (val.adType == 'GOODS'?'商品':'')
										+  (val.adType == 'BRAND'?'品牌':'')
										+  (val.adType == 'SUPPLIER'?'供应商':'')
										+  (val.adType == 'CLASSIFICATION'?'分类':'')
										+  (val.adType == 'PICTURE'?'图片':'')
										+  (val.adType == 'URL'?'URL':'')
										+ '</td>'
										+ '<td>'
										+ (val.adStatus == 'NOTCOMMIT'?'未提交':'')
										+ (val.adStatus == 'WAITAUDIT'?'待审核':'')
										+ (val.adStatus == 'NOTSTARTED'?'未开始':'')
										+ (val.adStatus == 'UNDERWAY'?'进行中':'')
										+ (val.adStatus == 'ALREADYOVER'?'已结束':'')
										+ (val.adStatus == 'NOTPASS'?'未通过':'')
										+ '</td>'
										+ '<td>'
										+  val.startTime
										+ '</td>'
										+ '<td>'
										+  val.endTime
										+ '</td>'
										+ '<td>'
										+'<a href="/fwz/ssAd/viewInit.do?id='+val.id+'" class="show" name="view">查看</a>'
										+ (val.adStatus == 'NOTCOMMIT'?'<a href="/fwz/ssAd/modifyInit.do?id='+val.id+'" class="show" name="edit">编辑</a><a href="#" class="show" name="changeStatus" adId="'+val.id+'" adStatus="WAITAUDIT">提审</a>':'')
										+ (val.adStatus == 'WAITAUDIT'?'':'')
										+ (val.adStatus == 'NOTSTARTED'?'<a href="#" class="show" name="changeStatus" adId="'+val.id+'" location="'+val.adLocation+'" adStatus="ALREADYOVER">下架</a>':'')
										+ (val.adStatus == 'UNDERWAY'?'<a href="#" class="show" name="changeStatus" adId="'+val.id+'" location="'+val.adLocation+'" adStatus="ALREADYOVER">下架</a>':'')
										+ (val.adStatus == 'ALREADYOVER'?'':'')
										+ (val.adStatus == 'NOTPASS'?'<a href="/fwz/ssAd/modifyInit.do?id='+val.id+'" class="show" adId="'+val.id+'" name="edit">编辑</a>':'')
										+'</td>'
										+ '</tr>'
							})
			$("#fwzad tbody").html(html);
		},
		
		

		// 查询 --开始
		queryAD : function() {
			var _self = this;
			var ajax_data = _self.getSearchPram();
			$.ajax({
				url : '/fwz/ssAd/find.do',
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
			
			KXWP.getEnumListByName("AdStatusEnum","#adStatus");
			KXWP.getEnumListByName("AdTypeEnum","#adType");
			KXWP.getEnumListByName("AdLocationEnum","#adLocation");
			KXWP.setStartEndTime($("#startDateTime"),$("#endDateTime"),{
                endMaxToday:false,
                preDays:365
            });
			
			// 绑定查询事件
			$("#adcx").on('click', function(event) {
				event.preventDefault();
				kkpager.pno = 1;
				//清除分页
				$("#kkpager").empty();
				_self.queryAD();
			})
			
			$(".table ").on('click','a[name=changeStatus]', function(){
				var adConfig = {};
				adConfig.id=$(this).attr('adId');
				adConfig.adStatus=$(this).attr('adStatus');
				adConfig.adLocation=$(this).attr('location');
				
				var confirmMessage = "";
				if(adConfig.adStatus == 'WAITAUDIT'){
					confirmMessage = "确认提审该广告";
				}else{
					confirmMessage = "确认下架该广告";
				}
				layer.confirm(confirmMessage, {
					  btn: ['取消','确认'] //按钮
					},  function(){
						  layer.msg('取消成功!');
					},function(){
				 		
						$.ajax({
							url : '/fwz/ssAd/modify.do',
							type : 'POST',
							dataType : 'json',
							data : JSON.stringify(adConfig),
							contentType : "application/json",
							success : function(data) {
								if(data.callStatus == 'SUCCESS'){
									layer.msg('操作成功!');
									$("#adcx").click();
								}
							}
						}); 
					});
			});
			
		}
	}

	cscxQuery.initPage();
	return cscxQuery;
})();