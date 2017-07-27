//初始化函数
function init(){
    add();
}

//添加函数
function add(){
    $(".add_btn").click(function(e){
        e.preventDefault();
        layer.open({
          title:"添加商品",
          type: 1,
          skin: 'layui-layer-demo', //样式类名
          closeBtn: 0, //不显示关闭按钮
          shift: 2,
          shadeClose: true, //开启遮罩关闭
          content: '<div>'+
                        '<p><input type="text" placeholder="输入关键词"><span id="search">搜索</span></p>'+
                        '<div class="main">'+
                            '<div class="search-show"></div>'+
                            '</div>'
        });
    })
}

$(function(){
    init();
})
