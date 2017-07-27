define('zz/order/list', function(require, exports, module) {

	var Model = require('model/zz/order'); // 引入接口文件
	var ListTpl = require('tpl/zz/order/list.tpl'); // 引入模板文件

	module.exports = {
			//强制转换保留2位小数
			toDecimal2:function (number) {   
				var view = this;
		        var f = parseFloat(number);    
		        if (isNaN(f)) {    
		            return false;    
		        }    
		        var f = Math.round(number*100)/100;    
		        var s = f.toString();    
		        var rs = s.indexOf('.');    
		        if (rs < 0) {    
		            rs = s.length;    
		            s += '.';    
		        }    
		        while (s.length <= rs + 2) {    
		            s += '0';    
		        }    
		        return s;    
		    },
        
		// 页面绑定事件，所有的事件都在这里写
		initEvents : function() {
			var view = this;
			// 绑定查询事件
			view.$el.on('click', '.j_search_btn', function(event) {
				event.preventDefault();
				view.search();
			});
		},
		
		// 查询函数
		search : function() {
			var view = this;
			var ajax_data = view.getSubmitData();
			Model.search(ajax_data).done(function(data) {
				view.renderTable(data);
			})
		},

		/* 分页 */
		paging : function(result) {
			var view = this;
			var pager = result.pager;
			var totalPage = pager.totalPages;
			var totalRecords = pager.totoalResults;
			var pageNo = pager.currentPage;
			if (!pageNo) {
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
				click : function(n) {
					this.selectPage(n);
					view.search();
					return false;
				}
			},true);
		},

		// 表格数据渲染
		renderTable : function(result) {
			var view = this;
			if (result.data && result.data.salesOrderList.length) {
				view.paging(result);
			}
			var data = result.data;
			var html = template.compile(ListTpl)({
				list: data
			});
			$("#zz_order_list tbody").html(html);
			//结果保留两位小数
			var sumAmount = view.toDecimal2(result.data.sumAmount);
			$("#sumAmount").html(sumAmount+"元");
		},
		

		// 此函数用来获取整个页面中要提交到后台的值
		getSubmitData : function() {
			var view = this;
			var ajax_data = {
				pager : {
					currentPage : kkpager.pno,
					pageSize : 10,
				}
			};
			
			var orderNo = $.trim(($('#orderNo').val()));
			if (orderNo) {
				ajax_data.orderNo = orderNo;
			}
			var phone = $.trim(($('#phone').val()));
			if (phone) {
				ajax_data.phone = phone;
			}
			
			var fwz_id = $.trim(($('#service_station').val()));
			if (fwz_id) {
				ajax_data.serviceStationId = fwz_id;
			}
			var supplier_id = $.trim(($('#supplier').val()));
			if (supplier_id) {
				ajax_data.supplierId = supplier_id;
			}

			var order_status = $.trim(($('#order_status').val()));
			if (order_status) {
				ajax_data.orderStatus = order_status;
			}
			
			var start_time = $.trim(($('#startDate').val()));
			if (start_time) {
				ajax_data.startDateTime = start_time;
			}
			
			var end_time = $.trim(($('#endDate').val()));
			if (end_time) {
				ajax_data.endDateTime = end_time;
			}

			return ajax_data;
		},

		// 初始化页面函数
		init : function() {

			var view = this;
			view.$el = $('.k_right'); // 准备好这个页面的dom元素
			view.initEvents(); // 初始化绑定事件
			// 商品状态
			KXWP.getEnumListByName({
				enumName : "OrderStatusEnum", // 枚举的名字
				selectDom : "#order_status", // select dom
				valueFiled : "name", // option的value 用的是返回结果的哪个字段
			    isZero:true,//是否显示为空的
			});
			
			// 时间初始化
            KXWP.setStartEndTime($("#startDate"),$("#endDate"),{
                endMaxToday:false,
                preDays:365
            });
			KXWP.gysSelect("#supplier");
			KXWP.fwzSelect("#service_station");

		}
	};
});