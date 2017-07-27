/*========菜单页========*/
var Menu = (function(){
    var menu = {
        /*初始化页面*/
        init:function(){
           /*系统请求*/
           //Common.post(url,x_t);
           function x_t(rs){
        	   var html="";
        	   $.each(rs,function(i,val){
        		   html+="<option>"+val.值+"</option>"
        	   })
        	   $('#x_t').html(html);
           }
           /*状态请求*/
           //Common.post(url,z_t);
           function z_t(rs){
        	   var html="";
        	   $.each(rs,function(i,val){
        		   html+="<option>"+val.值+"</option>"
        	   })
        	   $("#z_t").html(html);
           } 
        },
        /*是否批量上下线*/
        dialog:function(){
            /*批上*/ 
            $('.batch_up').click(function(){
              /*判断是否有选中的*/
                var checked=false;
                var checkboxs=$('.table input[type=checkbox]');
                for(var i=0;i<checkboxs.length;i++){
                   if(checkboxs[i].checked){
                      checked=true;
                   }
                }
                if(!checked){
                    Common.kxwp_dialog('请先选中');
                }else{
                    Common.kxwp_dialog('','','确定上线选中的内容吗?')
                }
            })
            /*批下*/
            $('.batch_down').click(function(){
                var checked=false;
                var checkboxs=$('.table input[type=checkbox]');
                for(var i=0;i<checkboxs.length;i++){
                   if(checkboxs[i].checked){
                      checked=true;
                   }
                }
                if(!checked){
                	Common.kxwp_dialog('请先选中');
                }else{
                	Common.kxwp_dialog('','','确定下线选中的内容吗?')
                }
            })
        },
        /*分页*/
        paging:function(){
            $('.paging li').click(function(){
                var val=$(this).text();
                $('.pagecount').val(val);
            })
            $('#wave_showlength').change(function(){
              var pagenum=Number($(this).find("option:selected").text());
                $('.pagenumber').val(pagenum);
            })
            $('#top_wavePagenation li').click(function(){
              $(this).addClass('active').siblings().removeClass('active');
            })
        },
        /*添加菜单*/
        addmenu:function(){
          	$('#add_son').click(function(){
                $(this).prev('p').append('<b><select style="margin-top:14px; name="name"><option>请选择</option></select>&nbsp; &nbsp;<i class="icon-minus" style="cursor:pointer;color:#26a1d0"></i></b>')
            })
            count = 0;
            $('#add_url').click(function(){
              $(this).prev('p').append('<b><input type="text" style="margin-top:14px;" name="moduleUrls" id="moduleUrls'+(count++)+'" class="validate[required,ajax[checkModelURL]]">&nbsp;&nbsp; <i class="icon-minus" style="cursor:pointer;color:#26a1d0"></i></b>')
            })
            $('.form_menu').on('click','.icon-minus',function(){
                $(this).parent('b').remove();
            })
        },
        /*提交表单正则验证*/
        form_submit:function(){
            
        }
    }
    return menu;
})();



