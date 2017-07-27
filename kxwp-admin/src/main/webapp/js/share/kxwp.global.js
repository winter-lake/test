/*
*此文件定义公用的函数
*/
var KXWP = (function(){
	var kxwp = {
		// 存储经纬度的对象
		locationObj:{
			lat:"",
			lng:"",
		},
		resultData:{},
		// 显示消息提醒的对话框
		showMessage:function(options,closeFn){
			if(typeof options == "string"){
				options = {
					content:options
				}
			}
			// 生成时间戳，防止dialog重复
			var now = new Date();

			var config = {
				content:"系统提示",
				title:"系统提示",
				showCloseBtn:true,
				showFooter:true,
				modalSize:"",// 对应bootstrap 模态框大小的类 modal-lg/modal-sm
				modalWidth:"",
				domid:("msg_" + now.getTime()),
			}
			var tpldata = $.extend(config, options);
			var html = template("global_modal_tpl", tpldata);
			$("body").append(html);
			var this_dialog = $("#" + tpldata.domid);
			this_dialog.modal({backdrop: 'static'});
			this_dialog.modal("show");

			this_dialog.on('hidden.bs.modal', function(event) {
				event.preventDefault();
				$(this).remove();
				if(closeFn){
					closeFn();
				}else{
					if(options.closed){
						options.closed();
					}
				}
			});

			this_dialog.on('shown.bs.modal', function(event) {
				event.preventDefault();
				// 使确定按钮得到焦点
				$(".modal-footer button.js-confim-btn").focus();
				if(options.opened){
					options.opened();
				}
			});
		},

		// 显示选择的对话框
		showYN:function(options,YesFn,NoFn){
			if(typeof options == "string"){
				options = {
					content:options
				}
			}
			// 生成时间戳，防止dialog重复
			var now = new Date();

			var config = {
				content:"温馨提示",
				title:"温馨提示",
				showCloseBtn:false,
				showYn:true,
				showFooter:false,
				modalSize:"",// 对应bootstrap 模态框大小的类 modal-lg/modal-sm
				modalWidth:"",
				domid:("msg_" + now.getTime()),
			}
			var tpldata = $.extend(config, options);
			var html = template("global_modal_tpl", tpldata);
			$("body").append(html);
			var this_dialog = $("#" + tpldata.domid);
			this_dialog.modal({backdrop: 'static'});
			this_dialog.modal("show");

			this_dialog.on('hidden.bs.modal', function(event) {
				event.preventDefault();
				$(this).remove();
			});

			this_dialog.on('shown.bs.modal', function(event) {
				event.preventDefault();
				if(options.opened){
					options.opened();
				}
				// 绑定确认，取消事件
				$(".yes-btn").on('click', function(event) {
					event.preventDefault();
					if(YesFn){
						YesFn();
					}
				});
				$(".no-btn").on('click', function(event) {
					event.preventDefault();
					if(NoFn){
						NoFn();
					}
				});
			});
		},

		// 显示全局loading --开始
		showLoading:function(){
			Common.startloading();
		},// 显示全局loading --结束

		// 捕获局部ajax错误事件 --开始
		partAjaxFail: function(jqXHR, textStatus, errorThrown) {
			try {
				var tpldata = {
					content: jqXHR.responseText,
					title: "操作失败,请稍后重试",
					domid: "ajax_error",
					opened: function() {
						$(document).on('keydown', function(event) {
							if (event.keyCode == 13) {
								$('#ajax_error').modal('hide');
							}
						});
					},
					closed: function() {
						$(document).off("keydown");
					}
				}
				Higo.hideLoading();
				Higo.Common.kxpw_tishi(tpldata);
			} catch (e) {
				$.trace(e);
			}
		},
		// 捕获局部ajax错误事件 --结束

		// 关闭loading--开始
		hideLoading:function(){
			$('#global_model_loading').modal('hide');
		},// 关闭loading--结束

		// 局部loading事件  --开始
		partLoadingShow:function(box){
			var boxDom = $(box);
			//$(box).html("");
			var height = boxDom.height();
			height = height > 100 ? height:100;
			var width = boxDom.height();
			width = width > 100 ? width:100;
			height = 100;
			width = 100;
			var html = "<div class='higo-loading center-block' style='height:"+height+"px;width:"+ width +"px;'></div>"
			boxDom.html(html);
		},//局部loading事件  --结束

		// 移除局部loading  --开始
		partLoadingHide:function(box){
			$(box).html('');
		},// 移除局部loading  --结束

		// 全选事件--开始
		initSelectAll:function(options){
			var config = {
				el: $('.j_checkbox_box'),
				allBtn: '.j_checkbox_all', // 全选checkbox
				checkboxClass: '.j_checkbox_item' // 每个checkbox的 class
			}
			config = $.extend(config, options);

			var $el = $(config.el);
			var allBtn = config.allBtn;
			var box = $el;
			var checkItem = config.checkboxClass;

			// 全选按钮
			// 绑定全选事件
            $el.on('change', allBtn, function(event) {
                event.preventDefault();
                var el = $(this);
                // 判断是否选中，对应全选或全取消
                var flag = el.prop("checked");
                box.find(checkItem).prop("checked", flag);
            });

            // 绑定单选
            $el.on('change', checkItem, function(event) {
                event.preventDefault();
                var el = $(this);
                var select_all = $el.find(allBtn);
                var flag = el.prop("checked");
                // 如果选中，判断是否全部选中，全选中，则选中全选按钮
                if(flag) {
                    var checked_inputs = box.find(checkItem + ':checked').length;
                    var all_inputs= box.find(checkItem).length
                    if(checked_inputs != all_inputs) {
                        flag = false;
                    }
                }
                select_all.prop("checked", flag);
            });
		},// 全选事件--结束

		//生成编号的函数 --开始
		generateNo:function(ajax_data,callback){
			$.ajax({
				url: '/system/ajax/generatorNO.do',
				type: 'GET',
				dataType: 'json',
				contentType:"application/json",
				data:ajax_data,
				success:function(data){
					if(data.callStatus == "SUCCESS"){
						if(data.data){
							callback(data.data);
						}else{
							Common.kxpw_tishi("获取单号失败！请刷新后重试");
						}
					}
				},
				fail:function(){
					Common.kxpw_tishi("获取单号失败！请刷新后重试");
				}
			})
		},//生成编号的函数 --结束

		// 获取枚举类型的函数(option value=枚举名称) --开始
		getEnumListByName:function(enumname,selectDom,callback){
			// 假如传的是对象，为了兼容以前
			var defaults = {
				enumName:"",
				selectDom:"",
				valueFiled:"name",
				isZero:false, //默认显示
				callback:function(){},
			}
			if(typeof enumname == "object"){
				$.extend(true, defaults, enumname);
			}else{
				defaults.enumName = enumname;
				defaults.selectDom = selectDom;
				defaults.callback = callback;
			}
			// 如果已经有了，则不再发请求
			function _randerSelect(results){
				var html = "";
				// 如果selectDom上有默认值,则直接选中
				var default_value = $(defaults.selectDom).attr("data-val");
				for (var i = 0; i < results.length; i++) {
					var selected = "";
					var emObj = results[i];
					if(emObj[defaults.valueFiled] == default_value){
						selected = "selected";
					}
					html += '<option value="' + emObj[defaults.valueFiled] + '" '+ selected +'>'+ emObj.desc +'</option>'; 
				};
				if(defaults.isZero){
					var placeholder = $(defaults.selectDom).attr("placeholder");
					html = "<option value=''>" + placeholder + "</option>" + html;
				}
				$(defaults.selectDom).html(html);
			}

			var cur_data = $(defaults.selectDom).data(defaults.enumName);
			if(cur_data){
				_randerSelect(cur_data);
			}else{
				var url = "/common/ajax/getEnumListByName.do?enumname=" + defaults.enumName;
				$.ajax({
					url: url,
					type: 'GET',
					dataType: 'json',
					contentType:"application/json",
					data:{},
					success:function(data){
						if(data.callStatus == "SUCCESS"){
							if(data.data){
								if(defaults.selectDom){
									_randerSelect(data.data);
									$(defaults.selectDom).data(defaults.enumName,data.data);
								}
								if(callback){
									callback(data.data);
								}
							}else{
								 Common.kxpw_tishi("获取枚举失败！请刷新后重试");
							}
						}
					},
					fail:function(){
						Common.kxpw_tishi("获取枚举失败！请刷新后重试");
					}
				})
			}

		},// 获取枚举类型的函数 --结束
		
		// 获取枚举类型的函数 以code为option的value值--开始
		getEnumListByCode:function(enumname,selectDom,callback){
			// 假如传的是对象，为了兼容以前
			var defaults = {
				enumName:"",
				selectDom:"",
				valueFiled:"code",
				isZero:false,
				callback:function(){},
			}
			if(typeof enumname == "object"){
				$.extend(true, defaults, enumname);
			}else{
				defaults.enumName = enumname;
				defaults.selectDom = selectDom;
				defaults.callback = callback;
			}
			// 如果已经有了，则不再发请求
			function _randerSelect(results){
				var html = "";
				// 如果selectDom上有默认值,则直接选中
				var default_value = $(defaults.selectDom).attr("data-val");
				for (var i = 0; i < results.length; i++) {
					var selected = "";
					var emObj = results[i];
					if(emObj[defaults.valueFiled] == default_value){
						selected = "selected";
					}
					html += '<option value="' + emObj[defaults.valueFiled] + '" '+ selected +'>'+ emObj.desc +'</option>'; 
				};
				if(defaults.isZero){
					var placeholder = $(defaults.selectDom).attr("placeholder");
					html = "<option value=''>" + placeholder + "</option>" + html;
				}
				$(defaults.selectDom).html(html);
			}

			var cur_data = $(defaults.selectDom).data(defaults.enumName);
			if(cur_data){
				_randerSelect(cur_data);
			}else{
				var url = "/system/ajax/getEnumListByName.do?enumname=" + defaults.enumName;
				$.ajax({
					url: url,
					type: 'GET',
					dataType: 'json',
					contentType:"application/json",
					data:{},
					success:function(data){
						if(data.callStatus == "SUCCESS"){
							if(data.data){
								if(defaults.selectDom){
									_randerSelect(data.data);
									$(defaults.selectDom).data(defaults.enumName,data.data);
								}
								if(callback){
									callback(data.data);
								}
							}else{
								Common.kxpw_tishi("获取枚举失败！请刷新后重试");
							}
						}
					},
					fail:function(){
						Common.kxpw_tishi("获取枚举失败！请刷新后重试");
					}
				})
			}

		},// 获取枚举类型的函数 --结束

		// 图标版的局部loading --开始
		IconLoading:function(dom){
			var loading_dom = dom || $(".js-loading-span");
			loading_dom.each(function(index, el) {
				var lineHeight = $(this).height();
				var fontSize = 60;
				var loadDiv = '<div style="text-align:center; color:#4284F3; height:100%;width:100%;line-height:'+ lineHeight +'px;font-size:' + fontSize + 'px;"><i class="fa fa-spinner fa-spin"></i></div>';
				$(this).html(loadDiv);
			});
		},// 图标版的局部loading --开始

		// 针对上半部分是查询条件，下半部分是结果表格的查询函数的封装  --开始
		initQuery:function(options){
			var defaults = {
				filterPanel:"#searchFilterPanel",//顶部搜索面板
				searchBtn:"#searchBtn",//查询按钮
				clearBtn:"button[type=reset]",//清空按钮,
				initSerchPanel:function(){},//初始化搜索面板事件
				getSerchFilter:function(){ //获取搜索参数的函数;
					return {};
				},
				searchParam:"",//查询的参数
				searchUrl:"",//查询的url
				showItemTotal:false,//是否显示分项总数
				validSearch:function(){
					return true;
				},
				randerTpl:"",//查询调用的结果模板
				finishCallback:function(){},//查询结束后的回调函数
				addOption:false, //是否初始化添加的函数
				updateOption:false, //是否初始化更新的函数
				deleteOption:false, //是否初始化删除的函数
			};
			var s = $.extend({}, defaults, options);//合并参数

			// 查询api --开始
			function _queryPromise(ajax_data){
				var queryPromise = $.ajax({
					url: s.searchUrl,
					type: 'POST',
					dataType: 'json',
					contentType:"application/json",
					data:JSON.stringify(ajax_data),
				});
				return queryPromise;
			}
			
			// 查询api --结束

			// 查询函数
			function _startSearch(pageIndex){
				if(!pageIndex){
					pageIndex = 1;
					$('.wave-sync-pagination').removeData("twbsPagination").html("");
				}
				var ajax_data = {
					pager:{
						currentPage:pageIndex,
						pageSize:parseInt($("#wave_showlength").val()) || 10,
					}
				}
				var search_filter = s.searchParam;
				$.extend(true,ajax_data, search_filter);
				
				Higo.partLoadingShow("#waveContent");
				var promise = _queryPromise(ajax_data);

				promise.done(function(data){
					this.resultData = data;
					Higo.partLoadingHide("#waveContent");
					if(data && data.callStatus == "SUCCESS"){
						if(data.data && data.data.length > 0){
							// 渲染页面
							_randerResults("#waveContent",data);
							//显示总数
							$("#wave_totalpages").html("共 " + data.pager.totalPages + " 页");
							$("#wave_totals").html(data.pager.totoalResults + " 条记录");
							// 分页
							if(pageIndex == 1){
								var pager = data.pager;
								if (pager && pager.totalPages && pager.totalPages > 1) {
									$('.wave-sync-pagination').twbsPagination({
										totalPages: pager.totalPages,
										visiblePages: 10,
										onPageClick: function(event, page) {
											_startSearch(page);
										}
									});
								}
							}
						}else{
							$("#waveContent").html("<div class='text-center higo-noresult'>无结果</div>")
						}
					}
				});

				promise.fail(function(){
					Higo.partLoadingHide("#waveContent");
				});
				
			};//查询函数

			// 渲染结果--开始
			function _randerResults(box,data){
				var self = this;
				var datas = data.data;
				var tpldata = {
					list:datas,
				};
				// 假如显示分项总数，则将整个data都传过去
				if(s.showItemTotal){
					tpldata.showTotal = true;
					tpldata.AjaxResults = data;
				}
				var html = template(s.randerTpl,tpldata);
				$(box).html(html);
				//为使搜索面板和内容对齐，手动触发窗口改变事件
				$(".higo-table-body").trigger('scroll');
				if(s.finishCallback){
					s.finishCallback();
				}
			};//渲染结果--结束

			// 查询事件
			$(s.filterPanel).on('click', s.searchBtn, function(event) {
				event.preventDefault();
				if(s.validSearch()){
					//只有在点击查询的时候才更新搜索条件
					s.searchParam = s.getSerchFilter();
					_startSearch();
				}
			});
			// 清空按钮
			$(s.filterPanel).on('click', s.clearBtn, function(event) {
				$("#searchFilterPanel input").each(function(index, el) {
					$(this).removeData("autoinfo");
					$(this).removeAttr('data-id');
				});
			});
			// 每页显示记录条数
			$("#wave_showlength").on('change', function(event) {
				event.preventDefault();
				/* Act on the event */
				$("#searchBtn").trigger('click');
			});
			// 初始化搜索面板的事件
			s.initSerchPanel();
		},// 针对上半部分是查询条件，下半部分是结果表格的查询函数的封装  --结束

		// 将ajax_data对象转化为url search字符串的函数 --开始
		transObjToUrlSearch:function(param, key){
			var self = this;
		    var paramStr="";
		    if(param instanceof String||param instanceof Number||param instanceof Boolean){
		        paramStr+="&"+key+"="+encodeURIComponent(param);
		    }else{
		        $.each(param,function(i){
		            var k=key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
		            paramStr+='&'+self.transObjToUrlSearch(this, k);
		        });
		    }
		    return paramStr.substr(1);
		},// 将ajax_data对象转化为url search字符串的函数 --结束

		// 查询快递信息 --开始
		queryWMSExpress:function(ajax_data,callback){
			var self = this;
			var defualt_ajax = {
				pager:{
					currentPage:1,
					pageSize: 15,
				}
			}
			// 默认根据运单号查询
			if(typeof ajax_data == "string"){
				defualt_ajax.waybillID = ajax_data;
				ajax_data = defualt_ajax;
			}else{
				$.extend(true,ajax_data,defualt_ajax);
			}
			$.ajax({
				url: "/express/ajax/queryExpress.do",
				type: 'POST',
				dataType: 'json',
				contentType:"application/json",
				data:JSON.stringify(ajax_data),
				success:function(data){
					if(data && data.callStatus == "SUCCESS"){
						if(data.data && data.data.length > 0){
							if(callback){
								callback(data.data);
							}
						}else{
							// console.error("查无此运单信息，请稍后重试");
						}
					}
				},
			})
		},// 查询快递信息 --結束

		// 持续获取gbs信息 --开始
		getLocationInfo: function(successCallback) {
			var self = this;
			var options = {
				enableHighAccuracy: true,
				maximumAge: 1000
			}
			if (navigator.geolocation) {
				//浏览器支持geolocation
				self.watchLocationId = navigator.geolocation.watchPosition(_onSuccess, _onError, options);
			} else {
				return false;
			}
			//成功时
			function _onSuccess(position) {
				//返回用户位置
				//经度
				var longitude = position.coords.longitude;
				//纬度
				var latitude = position.coords.latitude;
				var location_obj = {
					lng: longitude,
					lat: latitude,
				}
				// 将经纬度存在Higo对象下
				self.locationObj = location_obj;
			}


			//失败时
			function _onError(error) {
				switch (error.code) {
					case 1:
						alert("位置服务被拒绝");
						break;

					case 2:
						alert("暂时获取不到位置信息");
						break;

					case 3:
						alert("获取信息超时");
						break;

					case 4:
						alert("未知错误");
						break;
				}

			}
		}, // 持续获取gbs信息 --结束

	}
	return kxwp;
})();