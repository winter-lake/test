define('zz/stop/add', function(require, exports, module) {

	var Model = require('model/zz/stop'); // 引入接口文件

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
							layer.alert('添加服务站成功',function(index){
								window.location.reload();
							});
						}
					})
				}
			})
		},
		// 获取配送范围
		initGetDeliveryRange: function() {
			var view = this;
			// 绑定选择完成
			view.$el.find('#j_city').on('selected', function(event, data) {
				// 假如城市id存在，则获取配送范围,否则清空配送范围
				if(data) {
					view.getDevliveryRange(data);
				} else {
					$(".j_delivery_range .label_list").html("");
				}
			});
			// 初始化地址级联
			KXWP.addressSelect("#j_province", "#j_city");
		},
		getDevliveryRange: function(city) {
			var ajax_data = {
				city: city
			}
			Model.deliveryRange(ajax_data).done(function(data) {
				if(data.callStatus == 'SUCCESS') {
					var tpl = '{{each list}}<label>{{if $value.isOpendFWZ == "N"}}<input type="checkbox" value="{{$value.id}}">{{else}}<i class="icon-location-arrow" title="已开通服务站"></i> {{/if}}{{$value.name}}</label>{{/each}}';
					var html = template.compile(tpl)({
						list: data.data
					});
					$(".j_delivery_range .label_list").html(html);
				}
			})
		},
		//需要手动校验的表单字段
		validForm: function(){
			var view = this;
			if (!$('#add_fwz_form').validationEngine('validate')) {
				return false;
			}
			var ajax_data = view.getSubmitData();
			// 判断是否选了区县集合
			if(ajax_data.fwzCreateInfo.countyList.length == 0) {
				layer.alert("请选择服务范围");
				return false;
			}
			return true;
		},
		// 此函数用来获取整个页面中要提交到后台的值
		getSubmitData : function() {
			var view = this;
			var form = view.$el.find("#add_fwz_form");
			// 账户信息
			var ajax_data = {
				fwz_admin_name: form.find("[name=fwz_admin_name]").val(),
				fwz_admin_mobile: form.find("[name=fwz_admin_mobile]").val(),
			};
			// 服务站信息
			ajax_data.fwzCreateInfo = {
				fwz_name: form.find('[name=fwz_name]').val(),
				service_phone: form.find('[name=service_phone]').val(),
				province: form.find("#j_province").val(),
				city: form.find("#j_city").val(),
				platformFee: form.find('[name=platformFee]').val(),
				bzj: form.find('[name=bzj]').val(),
			}
			// 区县范围
			var countyList = new Array();
			form.find(".j_delivery_range .label_list label input:checked").each(function(index, el) {
				countyList.push($(el).val());
			});
			ajax_data.fwzCreateInfo.countyList = countyList;
			return ajax_data;
		},

		// 初始化页面函数
		init : function() {
			var view = this;
			view.$el = $('.k_right'); // 准备好这个页面的dom元素
			view.initEvents(); // 初始化绑定事件
			view.initGetDeliveryRange(); // 初始化获取配送范围
			// 初始化表单验证
			$('#add_fwz_form').validationEngine();
		}
	};
});