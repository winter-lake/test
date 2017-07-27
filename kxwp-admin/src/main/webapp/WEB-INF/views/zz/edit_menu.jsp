<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
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
			<form class="form_menu" method="post" action="">
				<input type="hidden" name="grade" value="1"/>
				
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 功能名称 :</span> <input type="text" value="${msResource.name }" disabled="disabled"  name="name"  placeholder="功能名称" data-required/></div>
				<div class="div"><span>功能描述 :</span> <textarea name="description" " id="description" cols="30" rows="10" placeholder="功能描述" data-required>${msResource.description }</textarea></div>
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 功能状态 :</span> <select name="resourceStatus" id="resourceStatus" data-required></select></div>
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 所属系统 :</span> <select name="ownSystem" id="ownSystem"  disabled="disabled" style="background: #EBEBE4" data-required></select></div>
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 父节点 :</span> <p><select name="parentId" id="parentId"  disabled="disabled" style="background: #EBEBE4" data-required><option value="" >请选择</option></select></p>&nbsp;&nbsp; <!-- <em class="icon-plus-sign-alt" id="add_son">(添加子菜单)</em> --></div>
				<div class="div">
					<span><i class="icon-asterisk tx_red"></i> URL :</span>
					<p>
						<input type="text" name="url" value="${msResource.url }"  disabled="disabled" data-required>
						<c:forEach items="${msResource.moduleUrls }" var="item">
							<b><input type="text" style="margin-top:14px;" name="moduleUrls" value="${item }" disabled="disabled" style="background: #EBEBE4">&nbsp;&nbsp;</b>
						</c:forEach>
					</p> &nbsp;&nbsp; 
					<!-- <em class="icon-plus-sign-alt" id="add_url" hidden="true">(添加模块URL)</em> -->
				</div>
				<div class="div"><input class="yes" value="确认" id="assure"><input type="reset" class="reset" value="重置" disabled="disabled" style="background: #EBEBE4"><input type="button" class="reset" value="返回" id="back" hidden="true"></div>
			</form>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">资源管理</a></li>
		  	<c:if test="${flag==view}">
		  	<li><a href="#">编辑资源</a></li>
		  	</c:if>
		  	<c:if test="${flag!=view}">
		  	<li><a href="#">查看资源</a></li>
		  	</c:if>
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
        $('#resourceStatus').val("${msResource.resourceStatus }");
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
        $('#ownSystem').val("${msResource.ownSystem }");
        
        //根据所属系统渲染父节点
        if("${msResource.ownSystem }" != ""){
        	$.ajax({
    	        url:"<%=basePath%>zz/msResourceManage/listResource.do?ownSystem=${msResource.ownSystem }&grade=1",
    	        type:'post',
    	        dataType:'json',
    	        success:function(data){
    	        
    	        render_parentId(data);
    	        }
    	    })
        }
    }
	//初始化页面元素
	if("${flag}" == 'view'){
		$("#resourceStatus").attr({ disabled: "disabled", style: "background: #EBEBE4" });
		$("#assure").attr({ disabled: "disabled", style: "background: #EBEBE4" });
		$("#description").attr({ disabled: "disabled", style: "background: #EBEBE4" });
		$("input[type=reset]").hide();
		$("#back").show();
	}
	
	$("#back").click(function(){
		window.location.href = "<%=basePath %>zz/msResourceManage/list.do";
	});
	
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
        $('#parentId').val("${msResource.parentId }");
    }
    //设置action
    $("#ownSystem").change(function(){
    	var ownSystem = $("#ownSystem").val();
    	
    	if(ownSystem == 'MasterStation'){
    		$("form:first").attr("action","<%=basePath %>zz/msResourceManage/add.do");
    	}else if(ownSystem == 'ServiceStation'){
    		$("form:first").attr("action","<%=basePath %>fwz/ssResourceManage/add.do");
    	}else if(ownSystem == 'Supplier'){
    		$("form:first").attr("action","<%=basePath %>gys/suResourceManage/add.do");
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
         layer.alert('修改成功！', {
           skin: 'layui-layer-molv' //样式类名
           ,closeBtn: 0
         }, function(){
  	   	window.location.href = "<%=basePath %>zz/msResourceManage/list.do";
         });
     }
    
    //ajax提交
    $("#assure").click(function(){
    	var msResources = [];
    	var msResource = {};
    	var ownSystem = "${msResource.ownSystem }";
    	var url=null;
    	if(ownSystem == 'MasterStation'){
    		url="<%=basePath %>zz/msResourceManage/modify.do";
    	}else if(ownSystem == 'ServiceStation'){
    		url="<%=basePath %>zz/ssResourceManage/modify.do";
    	}else if(ownSystem == 'Supplier'){
    		url="<%=basePath %>zz/suResourceManage/modify.do";
    	}else{
    		url="";
    	}
    	msResource.id = "${msResource.id }";
    	msResource.resourceStatus = $("#resourceStatus").val();
    	msResource.description=$("#description").val();
    	msResources.push(msResource);
    	console.dir(msResources);
		$.ajax({
	        url:url,
	        contentType:"application/json",
	        type:'post',
	        dataType:'json',
	        data:JSON.stringify(msResources),
	        success:function(data){
	        	if(data.callStatus == 'SUCCESS'){
	                layer.alert('修改成功！', {
	                    skin: 'layui-layer-molv' //样式类名
	                    ,closeBtn: 0
	                  }, function(){
	           	   		window.location.href = "<%=basePath %>zz/msResourceManage/list.do";
	                  });
	        	}
	        }
	    })
    });
})
</script>
</body>
</html>