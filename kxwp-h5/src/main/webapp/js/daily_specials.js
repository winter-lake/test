/**
 * 天天特价
 */

(function(){

    // 页面中的一些状态，初始值
    var PageModel = {
        page: 1, // 当前页码
        isAll: false // 全部是否加载完毕
    }

    var Page = {
        // 所有的事件绑定都在这里
        initEvents: function(){
            var view = this;
        },
        // 获取参数等
        getSubmitData: function(){
            var view = this;
            // 页码
            var pager = {
                currentPage: view.model.get('page')
            }
            var data = {
            	"channelName": "EveryDaySpecial",
                pager: pager
            };
            return data;
        },

        // 搜索函数
        search: function(isClear){
            // 参数isClear代表是清空列表还是追加
            var view = this;
            var ajax_data = view.getSubmitData();
            
            return KxModel.specials.getspecials(ajax_data).then(function(data){
                if(data.callStatus == 'SUCCESS') {
                    // 如果有数据，则渲染, 否则标记为加载完全部
                    common.end_loading();
                    if(data.data) {
                        view.render(data, isClear);
                        if(data.data.length <= 0) {
                            view.model.set('isAll', true);
                            // 如果是第一页就没有数据，则提示没有数据
                            if(ajax_data.pager.currentPage == 1) {
                                common.msg("没有相关记录");
                            }
                        }
                    } else {
                        view.model.set('isAll', true);
                    }
                } else {
                    var msg = data.message || '搜索商品失败，请重试'
                    common.msg(msg);
                }
                return data;
            });
        },

        // 渲染结果
        render: function(data, isClear) {
            var view = this;
            var tpldata = {
                list: data.data
            };

            var html = template(view.listTpl, tpldata);
            if(isClear) {
                view.$listBox.html("");
            }
            view.$listBox.append(html);
        },

        // 重置页面状态
        resetPage: function() {
            var view = this;
            view.model.set("page", 1);
            view.model.set("isAll", false);
            // 清空已选择的购物车
            KxApp.ShopCar.resetPage();
            // 重置上拉刷新
            view.$el.find(".j_content").scrollTop(0)
            view.dropload.resetload();
            // view.dropload.noData(false);
        },

        init: function() {
            var view = this;
            this.$el = $('.j_container'); // 整个视图容器
            this.$listBox = this.$el.find('.message_box'); //列表盒子
            this.listTpl = 'faily_list_tpl';
            this.initEvents();
            // 取得页面的初始值
            var pageStatus = {
              
            };
            // 实例化页面model
            this.model = new KxModel.BaseModel($.extend(PageModel,pageStatus), view);

            // 初始化下拉刷新
            view.dropload = this.$el.find('.j_content').dropload({
                autoLoad:false,
                distance: 50,
                loadUpFn : function(me){
                    // 重置页面状态
                    view.resetPage();
                    view.search().then(function(data){
                        // 清空列表，重新渲染
                        view.render(data, true);
                    }).always(function(data){
                        me.resetload();
                    })
                },
                loadDownFn: function(me) {
                    // 如果已经加载全部了
                    if(view.model.get('isAll')) {
                        me.noData(true);
                        me.resetload();
                        return false;
                    }

                    var current_page = view.model.get('page');
                    view.model.set('page', ++current_page);
                    view.search().then(function(data){
                        // 如果没有数据，标记为全部加载完毕
                        if(!data.data || data.data.length <= 0) {
                            me.noData(true);
                        }
                    }).always(function(){
                        me.resetload();
                    });
                }
            });
            //默认搜索
            common.start_loading();
            view.search(true).always(function(){
                common.end_loading();
            });
            // 初始化购物车
            KxApp.ShopCar.init();
        }
    };

    Page.init();

})();

