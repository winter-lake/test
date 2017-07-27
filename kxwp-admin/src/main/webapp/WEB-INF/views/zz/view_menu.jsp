<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>菜单详情</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="<%=contextPath %>/css/zz/menu_detail.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<h2 class="title">账户信息</h2>
			<div class="row">
			  <div class="col-md-2">功能名称 :</div>
			  <div class="col-md-4">${msResource.name }</div>
			</div>
			<div class="row">
			  <div class="col-md-2">功能描述 :</div>
			  <div class="col-md-4">${msResource.description }</div>
			</div>
			<div class="row">
			  <div class="col-md-2">功能状态 :</div>
			  <div class="col-md-4" id="resourceStatus"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">所属系统 :</div>
			  <div class="col-md-4" id="ownSystem"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">父节点 :</div>
			  <div class="col-md-4" id="parentId"></div>
			</div>
			<input type="hidden" name="grade" value="1"/>
			<div class="row">
					<div class="col-md-2">URL :</div>
			  		<div class="col-md-4">${msResource.url }</div><br/>
					<c:forEach items="${msResource.moduleUrls }" var="item">
						<div class="col-md-2"></div><div class="col-md-4">${item }</div><br/>
					</c:forEach>
			</div>
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
    	var result = '';
    	
        $.each(data.data,function(i,val){
        	if("${msResource.resourceStatus}" == val.name){
            	result=val.desc;
        	} 
        })
        
        $('#resourceStatus').html(result);
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
    	var result = '';
    	
        $.each(data.data,function(i,val){
        	if("${msResource.ownSystem}" == val.name){
        		result=val.desc;
        	}
        })
        
        $('#ownSystem').html(result);
        
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
	
	//查询父节点信息
    function render_parentId(data){
    	var result = '';
    	
        $.each(data.data,function(i,val){
        	if("${msResource.parentId }" == val.id){
        		result=val.name;
        	}
        })
        
        $('#parentId').html(result);
    }
})
</script>
</body>
</html>