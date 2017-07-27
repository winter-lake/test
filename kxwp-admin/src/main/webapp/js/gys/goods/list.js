define('gys/goods/list', function(require, exports, module) {

	var Model = require('model/gys/goods'); // 引入接口文件
	var ListTpl = require('tpl/gys/goods/gys.list.tpl'); // 引入模板文件

	module.exports = {

		// 页面绑定事件，所有的事件都在这里写
		initEvents : function() {
			var view = this;
			// 绑定查询事件
			view.$el.on('click', '.j_search_btn', function(event) {
				event.preventDefault();
				kkpager.pno = 1;
				view.search();
			});
			
			// 绑定更新商品状态
			view.$el.on('click', '#gys_goods_list td .j_update_goods', function(event) {
				event.preventDefault();
				var btn = $(this);
				layer.confirm('确认更改商品状态？', function(index){
					  view.update_goods(btn);
					  layer.close(index);
					}); 
				
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
			}, true);
		},

		// 表格数据渲染
		renderTable : function(result) {
			var view = this;
			// 先清空分页
			view.$el.find('#kkpager').html("");
			if (result.data && result.data.length > 0) {
				view.paging(result);
			}
			var data = result.data;
			var html = template.compile(ListTpl)({
				list: data
			});
			$("#gys_goods_list tbody").html(html);
		},

		// 此函数用来获取整个页面中要提交到后台的值
		getSubmitData : function() {
			var view = this;
			var search_form = view.$el.find('.j_search_form');
			var ajax_data = {
				pager : {
					currentPage : kkpager.pno,
					pageSize : 10,
				}
			};
			var goods_name = $.trim(($('#goods_name').val()));
			if (goods_name) {
				ajax_data.goods_name = goods_name;
			}
			var barcode = $.trim(($('#barcode').val()));
			if (barcode) {
				ajax_data.barcode = barcode;
			}
			var goods_no = $.trim(($('#goods_no').val()));
			if (goods_no) {
				ajax_data.goods_no = goods_no;
			}

			var goods_status = $.trim(($('#goods_status').val()));
			if (goods_status) {
				ajax_data.goods_statusEnum = goods_status;
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
				enumName : "GoodsStatusEnum", // 枚举的名字
				selectDom : "#goods_status", // select dom
				valueFiled : "name", // option的value 用的是返回结果的哪个字段
			    isZero:true,// 是否显示为空的
			});

			// 初始化选择、全选
			KXWP.initSelectAll({
				el: view.$el.find("#gys_goods_list")
			})

		}
	};
});