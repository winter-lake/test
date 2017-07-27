/*========角色页========*/
var Role = (function(){
    var role = {
        init:function(){
            $('.del_role').click(function(){
                var checked=false;
                var checkboxs=$('.table input[type=checkbox]');
                for(var i=0;i<checkboxs.length;i++){
                   if(checkboxs[i].checked){
                      checked=true;
                   }
                }
                if(!checked){
                    layer.alert('请先选中', {
                      skin: 'layui-layer-molv' //样式类名
                      ,closeBtn: 0
                    });
                }else{
                    layer.confirm('确定删除选中的内容吗？',{
                          btn: ['确定','取消'] //按钮
                        },function(){
                          Common.post(url,data)
                    });
                }
            })
            $('.t_del').click(function(e){
                layer.confirm('确定删除选中的内容吗？',{
                      btn: ['确定','取消'] //按钮
                    },function(){
                      Common.post(url,data)
                });
            })
        }
    }
    return role;
})();


