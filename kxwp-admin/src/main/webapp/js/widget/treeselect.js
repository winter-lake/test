/* 树形选择组件
* 请参照 '/fwz/manager/gys/gotoAddGys.do' 页面选择配送范围的dom结构
*/ 
define('widget/treeselect', function(require, exports, module) {

    var TreeSelect = function($el) {
        // 初始化函数
        this.init = function() {
            this.$el = $el;
            // 绑定面板收起展开事件
            this.$el.on('click', '.first_label i', function(event) {
                event.preventDefault();
                var item = $(this).closest('.child_li');
                if(item.hasClass('active')) {
                    item.removeClass('active');
                } else {
                    item.addClass('active');
                }
                return false;
            });
            // 绑定全选事件
            this.$el.on('change', '.first_label input', function(event) {
                event.preventDefault();
                var el = $(this);
                // 展开所有
                var box = el.closest('.child_li');
                box.addClass('active');
                // 判断是否选中，对应全选或全取消
                var flag = el.prop("checked");
                box.find('ul.second_box li input').prop("checked", flag);
            });

            // 绑定单选
            this.$el.on('change', '.second_box li input', function(event) {
                event.preventDefault();
                var el = $(this);
                var box = el.closest('.child_li');
                var select_all = box.find('.first_label input');
                var flag = el.prop("checked");
                // 如果选中，判断是否全部选中，全选中，则选中全选按钮
                if(flag) {
                    var checked_inputs = box.find('.second_box li input:checked').length;
                    var all_inputs= box.find('.second_box li input').length
                    if(checked_inputs != all_inputs) {
                        flag = false;
                    }
                }
                select_all.prop("checked", flag);
            });

            // 判断子选项是否全选中，是则选中全选
            this.$el.find('.first_label input').each(function(index, el) {
                var el = $(el);
                var box = el.closest('.child_li');
                var checked_inputs = box.find('.second_box li input:checked').length;
                var all_inputs= box.find('.second_box li input').length;
                var flag = false;
                if(checked_inputs == all_inputs) {
                    flag = true;
                }
                el.prop("checked", flag);
            });
        }

        // 获取选择的id
        this.getSelectedIds = function() {
            var view = this;
            var ids = new Array();
            view.$el.find('.second_box li input:checked').each(function(index, el) {
                ids.push($(el).val());
            });
            return ids;
        }

        // 初始化
        this.init();
    }
    module.exports = TreeSelect;
});