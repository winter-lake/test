define('zz/fwz/list', function(require, exports, module) {

	var Model = require('model/zz/fwz'); // 引入接口文件
	var ListTpl = require('tpl/zz/fwz/list.tpl'); // 引入模板文件

	module.exports = {

		// 页面绑定事件，所有的事件都在这里写
		initEvents : function() {
			var view = this;
			// 绑定查询事件
			view.$el.on('click', '.j_search_fwz_btn', function(event) {
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
		
		// 更新函数
		update_goods : function(btn) {
			var view = this;
			var ajax_data = {
					    id:btn.attr("goods-id"),
					    new_status:btn.attr("new-status"),
					    versionNO:btn.attr("version-no")
					};
			Model.update_goods_status(ajax_data).done(function(data) {
				if (data.callStatus == 'SUCCESS') {
					layer.alert('操作成功',function(index){
						window.location.reload();
					});        
				}
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
			if (result.data && result.data.length > 0) {
				view.paging(result);
			}
			var data = result.data;
			var html = template.compile(ListTpl)({
				list: data
			});
			$("#fwz_list tbody").html(html);
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
			var province = $.trim(($('#province').val()));
			if (province) {
				ajax_data.province = province;
			}
			var city = $.trim(($('#city').val()));
			if (city) {
				ajax_data.city = city;
			}
			var county = $.trim(($('#county').val()));
			if (county) {
				ajax_data.county = county;
			}

			var serviceStationID = $.trim(($('#serviceStation').val()));
			if (serviceStationID) {
				ajax_data.serviceStationID = serviceStationID;
			}

			return ajax_data;
		},

		// 初始化页面函数
		init : function() {
    
			var view = this;
			view.$el = $('.k_right'); // 准备好这个页面的dom元素
			view.initEvents(); // 初始化绑定事件
			KXWP.fwzSelect('#serviceStation'); //服务站筛选
			KXWP.addressSelect('#province','#city','#county');
		}
	};
});