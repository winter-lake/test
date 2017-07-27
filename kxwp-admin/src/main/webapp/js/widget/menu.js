define('widget/menu', function(require, exports, module) {
    module.exports = {

        /*这个函数用来初始化页面整个页面的事件
         *参数dom 为这个页面一开始就存在的根元素,
         */
        initEvents: function() {
            var view = this;
            // 绑定点击菜单事件
            view.$el.on('click', 'ul li', function(event) {
                event.preventDefault();
                var thisLi = $(event.currentTarget);
                // 先展开面板
                var isFold =  $(".k_contaniner").hasClass('fold');
                if(isFold) {
                    $(".k_contaniner").removeClass('fold');
                    view.$el.find(".j_haschild ul").hide();
                    view.$el.find('.icon-angle-right').hide();
                    view.$el.find('.icon-angle-down').hide();
                    view.$el.find('.name').hide();
                    setTimeout(function(){
                        view.$el.find('.icon-angle-right').show();
                        view.$el.find('.name').show();
                        if(thisLi.hasClass('active')){
                            view.unActive(thisLi);
                        }else{
                            view.beActive(thisLi);
                        }
                    }, 300);
                } else {
                    if(thisLi.hasClass('active')){
                        view.unActive(thisLi);
                    }else{
                        view.beActive(thisLi);
                    }
                }
                // 如果点击的是含有链接的a, 则直接跳转
                if(thisLi.hasClass('end-li')) {
                    var url = thisLi.find("a").attr("href");
                    window.location.href = url;
                }
                return false;
            });
            // 绑定展开/收起菜单事件
            view.$el.on('click', '.switch', function(event) {
                event.preventDefault();
                // 判断当时处于何种状态
                var isFold =  $(".k_contaniner").hasClass('fold');
                if(isFold) {
                    $(".k_contaniner").removeClass('fold');
                    // 修复闪一下的bug
                    view.$el.find(".j_haschild ul").hide();
                    view.$el.find('.icon-angle-right').hide();
                    view.$el.find('.icon-angle-down').hide();
                    view.$el.find('.name').hide();
                    // 动画完成后再显示
                    setTimeout(function() {
                        view.$el.find('.icon-angle-right').show();
                        view.$el.find('.name').show();
                        // 如果有激活状态，则显示激活的菜单
                        $(".j_haschild.active>ul").show();
                    }, 300);
                } else {
                    $(".k_contaniner").addClass('fold');
                    view.$el.find(".j_haschild ul").hide();
                    view.$el.find('.icon-angle-down').hide();
                    view.$el.find('.icon-angle-right').hide();
                }
                return false;
            });
        },

        // 激活状态
        beActive:function (item) {
            var view = this;

            view.unActive(item.siblings('li.active'));
            item.find(">ul").slideDown('fast', function() {
                item.addClass('active');
                item.find('>.icon-angle-right').hide();
                item.find('>.icon-angle-down').show();
            });
            view.$el.find('a.selected').removeClass('selected');
            item.children('a').addClass('selected');
        },

        // 非激活状态
        unActive:function (item) {
            if(item.hasClass('end-li')){
                return false;
            }
            item.find(">ul").slideUp('fast', function() {
                item.removeClass('active');
                item.find('>.icon-angle-down').hide();
                item.find('>.icon-angle-right').show();
            });
        },

        hightLight: function() {
            var view = this;
            var path = window.location.pathname; // 当前路径
            var item = "";
            view.$el.find('.end-li a').each(function(index, el) {
                var item = $(el);
                var data_path = item.data('path');
                if(data_path && (typeof data_path == 'string')) {
                    if(data_path.indexOf(path) > -1) {
                    	item.addClass("selected");
                		item.parents('.j_haschild ').addClass('active');
                    	return false;
                    }
                }
            });
        },

        // 初始化页面函数--入口函数
        /*
         * view.$el: 指的是这个js在页面中对应的dom作用范围，相关于一个作用域，避免于其他页面元素绑定的事件冲突
         * 在页面一开始就要准备好
         */
        init: function() {
            // 初始化绑定事件
            var view = this;
            view.$el = $('.k_left .w_side_menu'); // 准备好这个页面的dom元素
            view.initEvents();

            // 高亮左侧菜单
            view.hightLight();
        }
    };
});