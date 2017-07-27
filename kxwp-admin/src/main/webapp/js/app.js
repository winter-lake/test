/*
 * 外围布局js
*/ 
define('app', function(require, exports, module) {
    var Menu = require('widget/menu');

    module.exports = {

        /*这个函数用来初始化页面整个页面的事件
         *参数dom 为这个页面一开始就存在的根元素,
         */
        initEvents: function(dom) {
            $(dom).on('click', '.selector', function(event) {
                event.preventDefault();
                /* Act on the event */
            });
        },

        // 右上角安全中心
        initSaveCenter: function() {
            var view = this;
            var this_el = view.$el.find("#j_save_center");
            var url_prefix = this_el.data('sysprefix');
            // 退出系统
            this_el.on('click', '.j_signout', function(event) {
                event.preventDefault();
                layer.confirm('确定要退出吗？',function(){
                    window.location.href= "/" + url_prefix + "/user/logout.do";
                });
            });
            // 重置密码
            // this_el.on('click', '.j_reset_pwd', function(event) {
            //     event.preventDefault();
            //     layer.confirm('确定重置密码？',function(){
            //         window.location.href= "/" + url_prefix + "/userManage/goReset.do";
            //     });
            // });
        },

        // 初始化页面函数
        init: function() {
            var view = this;
            view.$el = $(".k_contaniner");
            // 初始化左侧菜单
            Menu.init();
            // 初始化右上角安全中心事件
            view.initSaveCenter();
            // 初始化滚动条
            view.$el.find('.k_left').niceScroll({
                cursorborder: "1px solid #999",
            });
        }
    };
});