<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="<html lang="zh-CN">">
<head>
	<meta charset="UTF-8">
	<title>添加菜单</title>
	
	<link rel="stylesheet" href="<%=basePath %>css/zz/common_menu.css">
	<%@include file="../common/share_static.jsp" %>
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<form class="form_menu" method="post" action="" id="form_menu">
				<input type="hidden" name="grade" value="1"/>
				
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 功能名称 :</span> <input type="text" value="" name="name" placeholder="功能名称" class="validate[required]"/></div>
				<div class="div"><span>功能描述 :</span> <textarea name="description" id="" cols="30" rows="10" placeholder="功能描述" class="validate[maxSize[125]] "></textarea></div>
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 功能状态 :</span> <select name="resourceStatus" id="resourceStatus" class="validate[required]"></select></div>
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 所属系统 :</span> <select name="ownSystem" id="ownSystem" class="validate[required]"></select></div>
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 父节点 :</span> <p><select name="parentId" id="parentId"><option value="">请选择</option></select></p>&nbsp;&nbsp; <!-- <em class="icon-plus-sign-alt" id="add_son">(添加子菜单)</em> --></div>
				<div class="div"><span><i class="icon-asterisk tx_red"></i> URL :</span><p><input type="text"  name="url" data-type="url" class="validate[required,ajax[checkURL]]"  id="url"/></p> &nbsp;&nbsp; <em class="icon-plus-sign-alt" id="add_url" hidden="true">(添加模块URL)</em></div>
				<div class="div"><input type="submit" class="yes" value="确认"><input type="reset" class="reset" value="重置"></div>
			</form>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">资源管理</a></li>
		  	<li><a href="#">添加资源</a></li>
		</ol>
		<a href="<%=basePath %>zz/msResourceManage/list.do" class="add_menu"><i class="icon-plus"></i>菜单列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
<script src="/js/zz/menu.js"></script>
<script>
$(function(){
	//页面初始化
	//初始化资源状态
	$.ajax({
        url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=ResourceStatusEnum",
        type:'post',
        dataType:'json',
        success:function(data){
            render_resourceStatus(data);
        }
    })
    function render_resourceStatus(data){
    	var html = '<option value="">请选择</option>';
    	
        $.each(data.data,function(i,val){
            html+='<option value="'+val.name+'">'+val.desc+'</option>'
        })
        
        $('#resourceStatus').html(html);
    }
	
	//初始化系统类型
	$.ajax({
        url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=SystemTypeEnum",
        type:'post',
        dataType:'json',
        success:function(data){
            render_ownSystem(data);
        }
    })
    function render_ownSystem(data){
    	var html = '<option value="">请选择</option>';
    	
        $.each(data.data,function(i,val){
            html+='<option value="'+val.name+'">'+val.desc+'</option>'
        })
        
        $('#ownSystem').html(html);
        $('#ownSystem option:last').remove();
    }
	
	//加载外部js
	Menu.addmenu();
	 
	//当所属系统改变时
	//查询父节点信息
	$("#ownSystem").change(function(){
		var temp = $('#ownSystem').val();
		
		$.ajax({
	        url:"<%=basePath%>zz/msResourceManage/listResource.do?ownSystem="+temp+"&grade=1",
	        type:'post',
	        dataType:'json',
	        success:function(data){
	        
	        render_parentId(data);
	        }
	    })
	});
    function render_parentId(data){
    	var html = '<option value="">请选择</option>';
    	
        $.each(data.data,function(i,val){
            html+='<option value="'+val.id+'">'+val.name+'</option>'
        })
        
        $('#parentId').html(html);
    }
    //设置action
    $("#ownSystem").change(function(){
    	var ownSystem = $("#ownSystem").val();
    	
    	if(ownSystem == 'MasterStation'){
    		$("form:first").attr("action","<%=basePath %>zz/msResourceManage/add.do");
    	}else if(ownSystem == 'ServiceStation'){
    		$("form:first").attr("action","<%=basePath %>zz/ssResourceManage/add.do");
    	}else if(ownSystem == 'Supplier'){
    		$("form:first").attr("action","<%=basePath %>zz/suResourceManage/add.do");
    	}else{
    		$("form:first").attr("action","");
    	}
    		
    });
    
    //当parentId改变时
    //设置grade的值
     $("#parentId").change(function(){
    	if($("#parentId").val() == ''){
    		$("input[name=grade]").val(1);
    	}else{
    		$("input[name=grade]").val(2);
    	}
    });
    //toggle btn-添加模块url
        $("#parentId").change(function(){
    	var parentId = $(this).val();
    	
    	if(parentId == ''){
    		$("#add_url").hide();
    	}else{
    		$("#add_url").show();
    	}
    });
    
    //当添加完成返回页面时
     if("${exchangeData.callStatus}" == 'SUCCESS'){
         layer.alert('添加成功！', {
           skin: 'layui-layer-molv' //样式类名
           ,closeBtn: 0
         }, function(){
  	   	window.location.href = "<%=basePath %>zz/msResourceManage/list.do";
         });
     }
   //绑定表单验证
     Common.validator($("#form_menu"));
})
</script>
</body>
</html>