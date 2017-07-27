define('fwz/gys/edit', function(require, exports, module) {

	var Model = require('model/fwz/gys'); // 引入接口文件
	var TreeSelect = require('widget/treeselect'); // 配送范围树形选择组件

	module.exports = {

		// 页面绑定事件，所有的事件都在这里写
		initEvents : function() {
			var view = this;

			// 提交表单
			view.$el.on('click', '.j_submit', function(event) {
				var check = $('#add_gys_form').validationEngine('validate')
				if(!check){
					return;
				}
			    check = view.validForm();
				if(check){
					var ajax_data = view.getSubmitData(); // 获取表单数据
					// 提交
					Model.edit(ajax_data).done(function(data) {
						if (data.callStatus == 'SUCCESS') {
							Common.kxpw_tishi('修改成功');
							window.location.href="/fwz/manager/gys/gysList.do";
						}
					})
				}
			});

		},

		// 初始化品牌选择移除事件
		initBrandPick: function() {
			var view = this;
			KXWP.sysBrandRepoSelect("#search_brand", function(){
				// 添加到下面
				var obj = $("#search_brand").select2("data")[0];
				if(obj.id) {
					// 判断是否存在于下面的容器，不存在则添加
					var flag = true;
					$(".j_brand_selected").find(".label-info").each(function(index, el) {
						var label_id = $(el).data('brandid');
						if (label_id && label_id == obj.id) {
							flag = false;
							return false;
						}
					});
					if(flag) {
						var html = '<span class="label label-info" data-brandid="'+ obj.id +'">'+ obj.name +' <i title="移除" class="icon-remove"></i></span>';
						$(".j_brand_selected").append(html);
					}
				}
			});

			// 移除事件
			view.$el.on('click', '.j_brand_selected .icon-remove', function(event) {
				event.preventDefault();
				var item =  $(this);
				item.closest('.label').remove();
			});
		},

		// 初始化配送范围收起展开事件
		initDeliveryRangeFold: function() {
			var view = this;
			view.$el.on('click', '.w_label_box.tree_box ', function(event) {
				event.preventDefault();
				/* Act on the event */
			});
		},

		//需要手动校验的表单字段
		validForm: function(){
			var view = this;
			//配送范围
			var shiping_area_ids = view.deliveryTree.getSelectedIds();
			if(shiping_area_ids== null || shiping_area_ids.length == 0){
				Common.kxpw_tishi('请选择配送范围');
				return false;
			}
			var business_scope_ids = new Array();
            view.$el.find('.j_category label input:checked').each(function(index, el) {
            	business_scope_ids.push($(el).val());
            });
            if(business_scope_ids == null || business_scope_ids.length == 0 ){
            	Common.kxpw_tishi('请选择经营范围');
				return false;
            }
            if($(".w_flag_box span").size() == 0){
            	Common.kxpw_tishi('请选择代理品牌');
		    	return false;
		    }
            
            // 获取已选择的品牌
            var brandlist = new Array(); 
            view.$el.find(".j_brand_selected .label").each(function(index, el) {
            	var id = $(this).data("brandid");
            	if(id) {
            		brandlist.push(id);
            	}
            });
            if(brandlist == null || brandlist.length == 0 ){
            	Common.kxpw_tishi('请选择代理品牌');
				return false;
            }
			return true;
		},
		// 此函数用来获取整个页面中要提交到后台的值
		getSubmitData : function() {
			var view = this;
			var supplier = {
					supplierName:$.trim($("#supplier_name").val()),
					id:$.trim($("#supplierID").val()),
					name:$.trim($("#contact_person").val()),
					account_mobile:$.trim($("#account_mobile").val()),
					service_phone:$.trim($("#service_phone").val()),//客服电话
					legalPerson:$.trim($("#legalPerson").val()),
					minShippingAmount:$.trim($("#minShippingAmount").val()),
					license_num:$.trim($("#license_num").val()),
					identityCardNum:$.trim($("#identityCardNum").val()),
					remark:$.trim($("#remark").val())
			};
			var business_scope_ids = new Array();
            view.$el.find('.j_category label input:checked').each(function(index, el) {
            	business_scope_ids.push($(el).val());
            });
            // 获取已选择的品牌
            var brandlist = new Array(); 
            view.$el.find(".j_brand_selected .label").each(function(index, el) {
            	var id = $(this).data("brandid");
            	if(id) {
            		brandlist.push(id);
            	}
            });
			var ajax_data = {
					supplier:supplier,
					sendPassword:$.trim($(
					'input[type="checkbox"][name="sendPassword"]:checked')
					.val()),
					classificaionIdList:business_scope_ids,
					brandIdList: brandlist,
				    //shippingAreaIdList:view.deliveryTree.getSelectedIds(),	
				};
			return ajax_data;
		},

		// 初始化页面函数
		init : function() {
			var view = this;
			view.$el = $('.k_right'); // 准备好这个页面的dom元素
			view.initEvents(); // 初始化绑定事件
			// 初始化表单验证
			$('#add_gys_form').validationEngine();
			// 初始化配送范围树形选择插件
			var tree_el = view.$el.find(".w_label_box.tree_box");
			view.deliveryTree = new TreeSelect(tree_el);
			//地址级联
			KXWP.addressSelect("#province","#city","#county","#town");
			// 品牌选择
			view.initBrandPick();
			$(".j_back").click(function(){
				window.location.href="/fwz/manager/gys/gysList.do";
			});
		}
	};
});