define('gys/goods/add', function(require, exports, module) {

			var Model = require('model/gys/goods'); // 引入接口文件
			var TreeSelect = require('widget/treeselect'); // 配送范围树形选择组件
			var TreeSelect = require('widget/treeselect'); // 配送范围树形选择组件
			module.exports = {

				// 页面绑定事件，所有的事件都在这里写
				initEvents : function() {
					var view = this;

					// 提交表单
					view.$el.on('click', '.j_submit', function(event) {
						var check = view.validForm();
						if(check){
							var ajax_data = view.getSubmitData(); // 获取表单数据
							// 提交
							Model.add(ajax_data).done(function(data) {
								if (data.callStatus == 'SUCCESS') {
									Common.kxpw_tishi("添加商品成功");
									window.location.reload();
								}
							})
						}
					})

				},

				// 设置阶梯价
				initLadder: function() {
					var view = this;
					var ladder = view.$el.find(".j_ladder_box");
					ladder.on('click', '.j_add_ladder', function() {
						// 先判断阶梯价是否超过三个
						var length = ladder.find('.j_ladder_item').length;
						if(length >= 3) {
							Common.kxpw_tishi('阶梯价格最多设置三个');
							return false;
						}
						var html = '<div class="j_ladder_item"><i>采购量 :<input type="text" name="minQit" class="validate[custom[number]]"> 至 <input type="text" name="maxQit" class="validate[custom[number]]"></i><i>梯度价 :<input type="text" name="lotPrice"> 元 </i><a href="javascript:void(0);" class="j_del_ladder">删除</a></div>';
						ladder.append(html);
					});

					// 删除阶梯价格
					ladder.on('click', '.j_del_ladder', function(event) {
						event.preventDefault();
						$(this).parents(".j_ladder_item").remove();
					});
				},

				// 获取阶梯价格
				getLadder: function() {
					var view = this;
					var ladder = view.$el.find(".j_ladder_box");
					var list = new Array();
					//判断阶梯数量是否正确
					var cur_min = 0;
					var cur_max = 0;
					var cur_lot_price = 0.00;
					
					ladder.find('.j_ladder_item').each(function(index, el) {
						var obj = {};
						var minQit = $.trim($(el).find('[name=minQit]').val());
						var maxQit = $.trim($(el).find('[name=maxQit]').val());
						var lotPrice = $.trim($(el).find('[name=lotPrice]').val());
						if(minQit && maxQit && lotPrice) {
							obj = {
								minQit: minQit,
								maxQit: maxQit,
								lotPrice: lotPrice
							}

							list.push(obj);
						}
					});
					return list;
				},
				//需要手动校验的表单字段
				validForm: function(){
					var view = this;
					if (!$('#add_goods_form').validationEngine('validate')) {
						return false;
					}
					//无法通过插件验证的,进行人工验证
					var brandId = $("#brand_repo").val();
					if(isNaN(brandId)){
						Common.kxpw_tishi("请选择商品品牌");
						return false;
					}
					debugger;
					var fir_categoryId = $('#j_first').val()
					var sec_categoryId = $('#j_second').val()
					var third_categoryId = $('#j_third').val()
					
					if(isNaN(parseInt(fir_categoryId)) || isNaN(parseInt(sec_categoryId)) || isNaN(parseInt(third_categoryId)) ){
						Common.kxpw_tishi("请选择商品分类");
						return false;
					}
					var lotsPrice = $('input[type="radio"][name="set_price"]:checked').val();
					if(lotsPrice == 'N'){
						var salePrice = $("#sale_price").val();
						if(isNaN(salePrice)){
							Common.kxpw_tishi("请输入商品价格");
							return false;
						}
					}
					
					if(lotsPrice == 'N'){
						var salePrice = $("#sale_price").val();
						if(isNaN(parseFloat(salePrice))){
							Common.kxpw_tishi("请输入商品销售价格");
							return false;
						}
					}
					var valid = true;
					
					//判断阶梯价
					if(lotsPrice == 'Y'){
						var view = this;
						var ladder = view.$el.find(".j_ladder_box");
						//判断阶梯数量是否正确
						var prev_min_qit = 0;
						var prev_max_qit = 0;
						var prev_lot_price = 0.00;
						ladder.find('.j_ladder_item').each(function(index, el) {
							var obj = {};
							var minQit = $.trim($(el).find('[name=minQit]').val());
							var maxQit = $.trim($(el).find('[name=maxQit]').val());
							var lotPrice = $.trim($(el).find('[name=lotPrice]').val());
							if(minQit && maxQit && lotPrice) {
								//前后大小判断,
								_minQit = parseInt(minQit);
								_maxQit = parseInt(maxQit);
								_lotPrice = parseFloat(lotPrice);
								if(_minQit> 0 && (_minQit > _maxQit)){
									Common.kxpw_tishi('阶梯数量设置不正确');
									valid = false;
									return;
								}
								//每一个阶梯价的起始要等于上一个值+1
								if(_minQit != prev_max_qit+1){
									Common.kxpw_tishi('阶梯数量设置不正确,起始数量必须等于上一个数量加1');
									valid = false;
									return;
								}
								
								//价格必须逐渐减小
								if(prev_lot_price >0 && _lotPrice > 0 &&  _lotPrice > prev_lot_price ){
									Common.kxpw_tishi('阶梯价格设置不正确');
									valid = false;
									return;
								}
								
								obj = {
									minQit: minQit,
									maxQit: maxQit,
									lotPrice: lotPrice
								}
								prev_min_qit = _minQit;
								prev_max_qit = _maxQit;
								prev_lot_price = _lotPrice;
							}else{
								Common.kxpw_tishi('请输入阶梯数量及价格');
								valid = false;
								return ;
							}
						});
					}
					
					
					if(!valid){
						return;
					}
					
					//配送范围
					var shiping_area_ids = view.deliveryTree.getSelectedIds();
					if(shiping_area_ids== null || shiping_area_ids.length == 0){
						Common.kxpw_tishi('请选择配送范围');
						return false;
					}
					
					//判断图片上传
					if($(".j_main_pic_box .w_view_pic").length == 0){
						Common.kxpw_tishi('请至少上传一张主图');
						return false;
					}
					return true;
				},
				// 此函数用来获取整个页面中要提交到后台的值
				getSubmitData : function() {
					var view = this;
					var ajax_data = {};

					// 商品基本信息
					var goodsInfo = {
						goodsName : $("#goods_name").val(), // 商品名称
						goodsNo : $("#goods_no").val(),
						barcode:$.trim($("#goods_barcode").val()) == "" ? "" :$.trim($("#goods_barcode").val()),
						brandId : $("#brand_repo").val(),
						packageSpecific : $("#package_first").val() + "*"
								+ $("#package_second").val() + "*"
								+ $("#goods_unit").val(),
						minPurchased : $("#minPurchased").val(),
						salePrice : $("#sale_price").val() || "",
						suggestRetailPrice : $('#suggest_retail_price').val(),
						shelfLife : $("#shelfLife").val(),
						productDate : $("#productDate").val(),
						returnPolicy : $("#return_policy").val(),
						barcode : $("#goods_barcode").val(),
						listOrder : $("#listOrder").val(),
						lotsPrice : $(
								'input[type="radio"][name="set_price"]:checked')
								.val(),
						goodsStatus : $("#submittoReview").attr("checked") ? 'REVIEWING'
								: 'NEW'
					}

					var goodsCategoryList = [ {
						categoryId : $('#j_first').val()
					}, {
						categoryId : $('#j_second').val()
					}, {
						categoryId : $('#j_third').val()
					} ]; // 商品分类信息

					//配送范围
					var shiping_area_ids = view.deliveryTree.getSelectedIds(); // 配送范围

					ajax_data = {
						goodsInfo : goodsInfo, // 商品信息
						goodsCategoryList : goodsCategoryList, // 商品分类信息
						goodsLotPriceList : view.getLadder(), // 阶梯价格
						goodsShippingAreaIDList: shiping_area_ids //配送范围
					}
					return ajax_data;
				},

				// 初始化页面函数
				init : function() {
					var view = this;
					view.$el = $('.k_right'); // 准备好这个页面的dom元素
					view.initEvents(); // 初始化绑定事件

				//	view.getLadder();

					// 选择商品条码
					KXWP.goodsRepoSelectByBarcode("#goods_barcode");
					// 选择商品名称
					KXWP.goodsRepoSelectByName("#goods_name");

					// 品牌库搜索
					KXWP.sysBrandRepoSelect("#brand_repo");

					// 退换货
					KXWP.getEnumListByName({
						enumName : "ReturnPolicyEnum", // 枚举的名字
						selectDom : "#return_policy", // select dom
						valueFiled : "name", // option的value 用的是返回结果的哪个字段
					// isZero:true,//是否显示为空的
					});

					// 获取包装规格枚举值
					KXWP.getEnumListByName({
						enumName : "PackageSpeciEnum", // 枚举的名字
						selectDom : "#goods_unit", // select dom
						valueFiled : "name", // option的value 用的是返回结果的哪个字段
						//isZero:true,//是否显示为空的
					});


					// 初始化上传图片 -- 主图
					Common.imageUpload(".j_main_pic_box");

					// 初始化上传图片 --详情图
					Common.imageUpload(".j_detail_pic_box");

					// 商品分类
					KXWP.goodsCategoryAuto("#j_first", '#j_second', '#j_third');

					// 省市区级联
					// KXWP.addressSelect("#j_province", '#j_city', '#j_county', '#j_town');

					// 生产日期
					KXWP.initMy97Date({
						dom: '#productDate',
						dateFmt : "yyyy-MM-dd",
						minDate : '1970-01-01', // 最小
						maxDate : KXWP.formatDate(new Date()), // 最迟今天
					});

					// 初始化阶梯价
					view.initLadder();

					// 初始化配送范围树形选择插件
					var tree_el = view.$el.find(".w_label_box.tree_box");
					view.deliveryTree = new TreeSelect(tree_el);

					// 初始化表单验证
					$('#add_goods_form').validationEngine();
				}
			};
		});