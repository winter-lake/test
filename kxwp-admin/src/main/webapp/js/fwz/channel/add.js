define('fwz/channel/add', function(require, exports, module) {

			var Model = require('model/fwz/channel'); // 引入接口文件
			module.exports = {

				// 页面绑定事件，所有的事件都在这里写
				initEvents : function() {
					var view = this;

					// 提交表单
					view.$el.on('click', '#submit', function(event) {
						var check = view.validForm();
						if(check){
							var ajax_data = view.getSubmitData(); // 获取表单数据
							// 提交
							Model.add(ajax_data).done(function(data) {
								if (data.callStatus == 'SUCCESS') {
									Common.kxpw_tishi("添加频道成功");
									window.location.href = "/fwz/channel/listChannelInit.do";
								}
							})
						}
					})

				},

				//需要手动校验的表单字段
				validForm: function(){
					var view = this;
					if (!$('#add_channel_form').validationEngine('validate')) {
						return false;
					}
					
					return true;
				},
				// 此函数用来获取整个页面中要提交到后台的值
				getSubmitData : function() {
					var view = this;
					
					var channelItems = [];
					
					$("#tbody tr").each(function(){
						var channelItem = {};
						
						channelItem.supplierId = $(this).find("td:eq(0)").text();
						channelItem.itemType = $(this).find("td:eq(1)").text();
						channelItem.itemId = $(this).find("td:eq(2)").text();
						channelItem.promotionPrice = $(this).find("td:eq(5)").text();
						
						channelItems.push(channelItem);
					});
					
					var ajax_data = {
						channelName : $("#channelName").val(),
						platformType : $("input[name=platformType]").val(),
						channelDesc : $("#channelDesc").val(),
						channelItems : channelItems
					};
					
					

					return ajax_data;
				},

				// 初始化页面函数
				init : function() {
					var view = this;
					view.$el = $('.k_right'); // 准备好这个页面的dom元素
					view.initEvents(); // 初始化绑定事件
					
					//获取频道名称
					$.ajax({
						type:"POST",
						url:"/fwz/channel/ajax/getChannelNameData.do",
						dataType:"json",
						data:JSON.stringify({}),
						contentType:"application/json",
						success:function(response){
							_randerSelect(response.data);
						},
						error:function(){
							layer.alert(response.message);
						}
					});
					
					function _randerSelect(results){
						var html = "";
						// 如果selectDom上有默认值,则直接选中
						var default_value = $("#channelName").attr("data-val");
						for (var i = 0; i < results.length; i++) {
							var selected = "";
							var emObj = results[i];
							if(emObj["name"] == default_value){
								selected = "selected";
							}
							html += '<option value="' + emObj["name"] + '" '+ selected +'>'+ emObj.desc +'</option>'; 
						};
						 $("#channelName").html(html);
					}
					//获取平台类型
					var url = "/common/ajax/getEnumListByName.do?enumname=PlatformTypeEnum";
					$.ajax({
						url: url,
						type: 'GET',
						dataType: 'json',
						contentType:"application/json",
						data:{},
						success:function(data){
							if(data.callStatus == "SUCCESS"){
								if(data.data){
										_randerOption(data.data);
								}else{
									 Common.kxpw_tishi("获取枚举失败！请刷新后重试");
								}
							}
						},
						fail:function(){
							Common.kxpw_tishi("获取枚举失败！请刷新后重试");
						}
					})
					
					function _randerOption(results){
						var html = "";
						// 如果selectDom上有默认值,则直接选中
						var default_value = $("#platformType").attr("data-val");
						for (var i = 0; i < results.length; i++) {
							if(i > 0){
								break;
							}
							var checked = "";
							var emObj = results[i];
							if(emObj["name"] == default_value){
								checked = "checked";
							}
							
							html += '<label class="checkbox-inline"><input type="checkbox" checked="true" name="platformType" value="' + emObj["name"] + '">'+ emObj.desc +'</label>'; 
						};
						$("#platformType").append(html);
					}
					
					//获取供应商信息
					KXWP.gysSelect("#supplierId");
					
					$("#add").click(function(){
					 //页面层
					 layer.open({
						  title: "",
						  type: 2,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['820px', '210px'], //宽高
						  content: '/fwz/channel/addGoodsInit.do?supplierID='+$("#supplierId").val(),
						  btn: ['保存', '取消'],
						  yes: function(index,layero){
							    var body = layer.getChildFrame('body', index);
							    var iframeWin = window[layero.find('iframe')[0]['name']];
							    
								$("#tbody").append(
										"<tr>"+
										"<td hidden=true>"+body.find('#supplierID').val()+"</td>"+
										"<td hidden=true>"+body.find('#itemType').val()+"</td>"+
										"<td hidden=true>"+body.find('#goodsName').val()+"</td>"+
							            "<td>"+body.find('#select2-goodsName-container').text()+"</td>"+
							            "<td>"+body.find('#goodsPrice').text()+"</td>"+
							            "<td>"+body.find('#goodsPromotionPrice').val()+"</td>"+
							            '<td><button type="button" class="btn btn-link btn-xs" name="delete">删除</button></td>"'+
							          "</tr>"
							    );
								
							    console.log(body.find('#goodsPromotionPrice').val())
							  	layer.close(index);
							  }
						});
					});
					
					$("tbody").on("click","button[name=delete]",function(){
						$(this).parent().parent().remove();
					});
					
					//根据供应商id和商品名称查询可用商品
					var defaults = {
					        url:"",//*查询的url
					        element:"",//*绑定的元素 eg:"#demo"
					        ajax_data:{},//发送参数的ajax参数
					        ajaxType:"post",
					        placeholder:"请选择一个值",
					        parentDom:"",//根据父元素的值来查询
					        parentDomField:"id",//查询时所带的父元素参数名称，和上一行一起传入
					        queryField:"query",//*input的参数
					        valueField:"",// *程序中要用的值字段名，指$(element).val()取得的值
					        displayField:"",//*显示在input中的返回值字段名,如果不传，则表示返回的是一个字符串数组
					        minLength:0,//至少输入几个字符触发
					        resultList:"",// ajax返回的结果列表的字段名,如果没传，则默认为data.data
					        ItemType:"obj",//ajax返回的结果，可能是obj,也可能是string
					        showInput:true,//是否显示输入框
					        hasPages:true,//传参数的时候是否带pager
					        afterSelected:function(){},//选择完一个值之后触发
					        hasInputValue: false // 是否能自定义输入，即将查询的参数作为值填充
					    };
					
					var KXWPSelect2_Channel = function(options){
				    	var self = this;
						//将插件的默认参数及用户定义的参数合并到一个新的obj里
						this.settings = $.extend(true, {}, defaults, options);
				        // 格式化部分参数，以适应初始化时的参数
				        if(this.settings.showInput == false){
				            this.settings.showInput = Infinity;
				        }
						//将dom jquery对象赋值给插件，方便后续调用
						var settings = this.settings;
						var element = $(settings.element);
						// 定义取值做参数的父元素
						var parent_Dom = $(settings.parentDom);
				        // 假如父元素存在，则绑定和父元素的联动事件
				        if(parent_Dom.length > 0 && parent_Dom.val() !== "" ){
				            element.attr('disabled', 'disabled');
				        }


				        element.select2({
				            placeholder: element.attr("placeholder") || "请选择一个值",
				            allowClear: true,
				            theme: "bootstrap",
				            ajax: {
				                url: settings.url,
				                dataType: 'json',
				                delay: 250,
				                global: false,
				                contentType: "application/json",
				                type: settings.ajaxType,
				                data: function(params) {
				                    //组合查询的参数
				                    var query_parameter = settings.ajax_data;
				                    query_parameter[settings.queryField] = params.term || "";
				                    // 当要取值的父元素存在时
				                    if (parent_Dom && parent_Dom.length > 0) {
				                        // 如果有取父元素的函数存在
				                        if (settings.getParentDomFieldValue) {
				                            query_parameter[settings.parentDomField] = settings.getParentDomFieldValue();
				                        } else {
				                            query_parameter[settings.parentDomField] = parent_Dom.val();
				                        }
				                    }
				                    if(settings.hasPages){
				                        query_parameter.pager = {
				                            currentPage:1,
				                            pageSize:20,
				                        }
				                    }
				                    if(settings.ajaxType == "post"){
				                        return JSON.stringify(query_parameter);
				                    }else{
				                        return query_parameter;
				                    }
				                    
				                },
				                processResults: function(data, page) {
				                    var results = [];
				                    if (data.callStatus = "SUCCESS" && data.data && data.data.length > 0) {
				                        // 此处有可能显示多级的结果，暂且保留，以后再来改
				                        var returnData = [];
				                        if (settings.resultList && settings.resultList !== "") {
				                            returnData = data.data[0][settings.resultList];
				                        } else {
				                            returnData = data.data;
				                        }
				                        $.each(returnData, function(i, v) {
				                            var o = {};
				                            // 根据返回的每个对象的类型做不同处理
				                            // 假如返回的每个对象为字符串
				                            if(settings.ItemType == "string"){
				                                o.id = v;
				                                o.value = v;
				                                o.name = v;
				                                o.price = v;
				                            }else{
				                                o.id = v[settings.valueField];
				                                o.value = v[settings.valueField];
				                                o.salePrice = v['salePrice'];
				                                var display_name = "";

				                                if (settings.displayField !== "") {
				                                    // 如果有多个显示字段，则返回相加后的字段
				                                    display_name = v[settings.displayField];
				                                    if (settings.displayField2) {
				                                        display_name = v[settings.displayField] + " " + v[settings.displayField2];
				                                    }
				                                }
				                                o.name = display_name;
				                            }
				                            results.push(o);
				                            
				                        });
				                    }else{
				                    	//console.log("ajax查询无数据返回");
				                    }
				                    return {
				                        results: results
				                    };
				                    
				                },
				                // cache: true
				            },
				            escapeMarkup: function(markup) {
				                return markup;
				            }, // let our custom formatter work
				            minimumInputLength: settings.minLength,
				            minimumResultsForSearch: settings.showInput,
				            templateResult: function(repo) {
				                if (repo.loading) return repo.text;
				                var markup = '<div value="' + repo.id + '">' + repo.name;
				                markup += '</div>';
				                return markup;
				            },
				            templateSelection: function(repo) {
				                return repo.name || repo.text;
				            }
				        });
				        // 保存初始化的参数,主要让设置默认值的时候用
				        element.data("select2-options",settings);
				        
				        element.on('change', function(event) {
				            event.preventDefault();
				            if(settings.afterSelected){
				                settings.afterSelected();
				            }
				        });

				        if(settings.hasInputValue) {
				            element.on('select2:close', function(event) {
				                if(!element.val()) {
				                    var defauls = element.data('select2').results.lastParams.term;
				                    if(defauls) {
				                        KXWPSelect2SetValue({
				                            Ddom: settings.element, //设置的dom
				                            Dvalue:defauls, // 设置的值
				                            Ddisplay:defauls, // 要显示的名字
				                        })
				                    }
				                }
				                
				            });
				        }
				    }
					
					KXWPSelect2_Channel({
						url: "/fwz/channel/ajax/goods/queryGoodsRepoByCondition.do",
						element: $("#goodsName"),
						hasPages: false,
						ajaxType: 'get',
						ajax_data:{supplierID : $("#supplierID").val()},
						queryField: "goods_name", //*input的参数
						displayField: "goodsName", //*显示在input中的返回值字段名
						valueField: "id",// *程序中要用的值
						minLength: 1,//输入几个字符就触发
						hasInputValue: false, // 是否可以直接输入
					});
					
					//商品名称改变时触发商品价格改变
					$("#goodsName").change(function(){
						$("#goodsPrice").text($("#goodsName").select2('data')[0].salePrice);
					});
					
					//点击返回按钮
					$("#back").click(function(){
						window.location.href="/fwz/channel/listChannelInit.do";
					});

					// 初始化表单验证
					$('#announcement_add_form').validationEngine();
				}
			};
		});