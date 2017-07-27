define('fwz/channel/list', function(require, exports, module) {

	var Model = require('model/fwz/channel'); // 引入接口文件
	var ListTpl = require('tpl/fwz/channel/channel.list.tpl'); // 引入模板文件

	module.exports = {

		// 页面绑定事件，所有的事件都在这里写
		initEvents : function() {
			var view = this;
			// 绑定查询事件
			view.$el.on('click', '#query_channel', function(event) {
				event.preventDefault();
				kkpager.pno = 1;
				view.search();
			});
		},

		// 查询函数
		search : function() {
			var view = this;
			var ajax_data = view.getSubmitData();
			Model.list(ajax_data).done(function(data) {
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
			$("#query_channel_table tbody").html(html);
		},

		// 此函数用来获取整个页面中要提交到后台的值
		getSubmitData : function() {
			var view = this;
			var search_form = view.$el.find('#query_channel_form');
			var ajax_data = {
				pager : {
					currentPage : kkpager.pno,
					pageSize : 10,
				}
			};
			var channelName = $.trim(($('#channelName').val()));
			if (channelName) {
				ajax_data.channelName = channelName;
			}
			
			return ajax_data;
		},

		// 初始化页面函数
		init : function() {
			var view = this;
			view.$el = $('.k_right'); // 准备好这个页面的dom元素
			view.initEvents(); // 初始化绑定事件
			
			$("tbody").on("click","button[name=view]",function(){
				var channelId = $(this).attr("channelId");
				
				//页面层
				layer.open({
				  type: 2,
				  skin: 'layui-layer-rim', //加上边框
				  area: ['820px', '210px'], //宽高
				  content: '/fwz/channel/viewChannelInit.do?channelId='+channelId
				});
			});
			
			$("tbody").on("click","button[name=maintain]",function(){
				var channelId = $(this).attr("channelId");
				
				window.location.href="/fwz/channel/getChannelInit.do?channelId="+channelId;
			});
			
			KXWP.getEnumListByName({
				enumName : "ChannelNameEnum", // 枚举的名字
				selectDom : "#channelName", // select dom
				valueFiled : "name", // option的value 用的是返回结果的哪个字段
			});
		}
	};
});