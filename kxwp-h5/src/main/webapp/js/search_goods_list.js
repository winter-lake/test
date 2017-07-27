
(function(){

    // 页面中的一些状态，初始值
    var PageModel = {
        page: 1, // 当前页码
        goods_name: "", // 商品名称
        category_id_list:"",//商品分类
        brand_id_list:"",//品牌分类
        rank_code: "CREATE_TIME_DESC", // 排序方式，默认综合
        isAll: false, // 全部是否加载完毕
    }

    var Page = {
        // 所有的事件绑定都在这里
        initEvents: function(){
            var view = this;
            view.$el.on('input', '[name=goods_name]', function(event) {
                event.preventDefault();
                view.model.set('goods_name', $.trim($(this).val()));
                return false;
            });
            // 输入框回车
            view.$el.on('keypress', '[name=goods_name]', function(event) {
                // event.preventDefault();
                if(event.keyCode == 13) {
                    view.resetPage();
                    common.start_loading();
                    view.search(true).always(function(){
                        // common.end_loading();
                    });
                }
            });
            // 点击搜索
            view.$el.on('click', '#j_search_btn', function(event) {
                // event.preventDefault();
                view.resetPage();
                common.start_loading();
                view.search(true).always(function(){
                    // common.end_loading();
                });
                return false;
            });
            // 点击排序
            view.$el.on('tap', '#d_sub .j_rank_code', function(event){
                var this_btn = $(this);
                // 判断本身是否已被激活，是则直接切换，否则先点亮
                if(this_btn.hasClass('cur')) {
                    // 如果本身有两个值，则切换
                    var items = this_btn.find('[data-rcode]');
                    if(items.length == 2) {
                        items.each(function(index, el) {
                            if($(el).hasClass('hide')) {
                                $(el).removeClass('hide');
                                $(el).addClass('active');
                            } else {
                                $(el).removeClass('active');
                                $(el).addClass('hide');
                            }
                        });
                    }
                } else {
                    this_btn.siblings('.j_rank_code').removeClass('cur');
                    this_btn.addClass('cur');
                }

                // 设置排序方式
                view.model.set("rank_code", this_btn.find(".active").data("rcode"));
                // 重置页面并搜索
                view.resetPage();
                view.search(true);
            });
            // 点击筛选
            this.$el.on('tap', '.j_filter_btn', function(event) {
              event.preventDefault();
              var this_btn = $(this);
              var this_pannel = $('.slider');
              if($(this).hasClass('active')) {
                $('.slider').css('right', '-80%');
                $(this).removeClass('active');
                $('body').off('tab.filter');
              } else {
                   $('.slider').css('right', '0');
                   $(this).addClass('active');
                   $('body').on('tap.filter', function(event) {
                        event.preventDefault();
                        var this_target = $(event.target);
                        if(this_target.parents('.j_filter_btn').length > 0) {
                            return false;
                        }
                        $('.slider').css('right', '-80%');
                   });
               }
            });
        },
        // 获取参数等
        getSubmitData: function(){
            var view = this;
            // 页码
            var pager = {
                currentPage: view.model.get('page')
            }
            var data = {
                goods_name: view.model.get('goods_name'),
                rank_code: view.model.get('rank_code'),
                pager: pager
            };
            //品牌和分类不允许传空串到后台
            if(view.model.get("brand_id_list") !=''){
            	data.brand_id_list = (view.model.get("brand_id_list")).split(",");
            }
           
            if(view.model.get("category_id_list") !=''){
            	data.category_id_list = (view.model.get("category_id_list")).split(",");
            }
            return data;
        },

        // 搜索函数
        search: function(isClear){
            // 参数isClear代表是清空列表还是追加
            var view = this;
            var ajax_data = view.getSubmitData();
            
            return KxModel.goods.getGoodsList(ajax_data).then(function(data){
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
            this.$listBox = this.$el.find('.j_list_box'); //列表盒子
            this.listTpl = 'goods_list_tpl';
            this.initEvents();
            // 取得页面的初始值
            var pageStatus = {
                goods_name: view.$el.find('[name=goods_name]').val(),
                category_id_list: view.$el.find('[name=category_id_list]').val(),
                brand_id_list: view.$el.find('[name=brand_id_list]').val()
                
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

           // 如果有商品名称，则开始搜索
            if(view.model.get("goods_name") != "" || view.model.get("category_id_list") != "" || view.model.get("brand_id_list") != "" ) {
                common.start_loading();
                view.search(true).then(function(data){
                    common.end_loading();
                    if(!data.data || data.data.length <= 0) {
                        common.msg("暂无相关记录");
                    }
                }).fail(function(){
                    common.end_loading();
                });
            }
            // 初始化购物车
            KxApp.ShopCar.init();
        }
    };

    Page.init();

})();

