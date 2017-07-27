define('fwz/announcement/add', function(require, exports, module) {

			var Model = require('model/fwz/announcement'); // 引入接口文件
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
									Common.kxpw_tishi("添加公告成功");
									window.location.href = "/fwz/SsAnnouncement/listInit.do";
								}
							})
						}
					})

				},

				//需要手动校验的表单字段
				validForm: function(){
					var view = this;
					if (!$('#announcement_add_form').validationEngine('validate')) {
						return false;
					}
					
					//判断图片上传
					if($(".j_main_pic_box .w_view_pic").length == 0){
						Common.kxpw_tishi('请上传广告图片');
						return false;
					}
					
					return true;
				},
				// 此函数用来获取整个页面中要提交到后台的值
				getSubmitData : function() {
					var view = this;
					var ajax_data = {
						announcementNo : $("input[name=keyID]").val(),
						announcementName : $("#announcementName").val(),
						announcementDesc : $("#announcementDesc").val(),
						pushTime : $("#pushTime").val(),
						platformType : $("#platformType").val(),
						content : $("#content").val()
					};

					return ajax_data;
				},

				// 初始化页面函数
				init : function() {
					var view = this;
					view.$el = $('.k_right'); // 准备好这个页面的dom元素
					view.initEvents(); // 初始化绑定事件

					KXWP.getEnumListByName({
						enumName : "PlatformTypeEnum", // 枚举的名字
						selectDom : "#platformType", // select dom
						valueFiled : "name", // option的value 用的是返回结果的哪个字段
					// isZero:true,//是否显示为空的
					});

					// 初始化上传图片 -- 主图
					Common.imageUpload(".j_main_pic_box");

					KXWP.initMy97Date({
						dom: '#pushTime',
						dateFmt : "yyyy-MM-dd HH:mm:ss",
						minDate : KXWP.formatDate(new Date()), // 最小
					});

					// 初始化表单验证
					$('#announcement_add_form').validationEngine();
				}
			};
		});