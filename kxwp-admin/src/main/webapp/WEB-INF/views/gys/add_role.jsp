<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head> 
	<meta charset="UTF-8">
	<title>角色添加</title>
	
	<link rel="stylesheet" href="<%=basePath %>css/gys/common_role.css">
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
			<form class="add_form_role" id="form_menu" method="post" action="<%=basePath %>gys/suRoleManage/add.do">
				<div class="div">
					<span><i class="icon-asterisk tx_red"></i>角色名 :</span>
				    <input type="text" name="name" placeholder="角色名" class="validate[required]" style="width:250px;border:1px solid #eee;height:30px;" data-required>
				</div>
				<div class="div">
					<span>角色描述 :</span> 
				    <textarea name="roleDescription" cols="30" rows="10" placeholder="角色描述" style="width:250px;border:1px solid #eee" data-required></textarea>
				</div>
				<h4>权限选择</h4>
				<div class="div">
					<p class="b_em"><input type="checkbox" name="b"><i class="icon-asterisk tx_red"></i>全选</p>
				</div>
				<c:forEach items="${newlist }" var="item" varStatus="status">
					<div class="div">
						<p class="b_em"><input type="checkbox" name="suResources[${status.index }].id" value="${item.id }"> ${item.name }</p> 
						<c:forEach items="${item.suResources }" var="item2" varStatus="status2">
							<p><input type="checkbox" name="suResources[${status.index }].suResources[${status2.index }].id" value="${item2.id }"> ${item2.name }</p>
						</c:forEach>
					</div>
				</c:forEach>
				<div class="div">
					<input type="submit" id="yes" value="确认"><input type="reset" id="reset" value="重置">
				</div>
			</form>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">供应商</a></li>
		  	<li><a href="#">资源管理</a></li>
		  	<li><a href="#">添加资源</a></li>
		</ol>
		<a href="<%=basePath %>gys/suRoleManage/list.do" class="add_menu"><i class="icon-plus"></i>角色列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
<script>
$(document).ready(function() {
    //当添加完成返回页面时
    if("${exchangeData.callStatus}" == 'SUCCESS'){
        layer.alert('添加成功！', {
          skin: 'layui-layer-molv' //样式类名
          ,closeBtn: 0
        }, function(){
 	   	window.location.href = "<%=basePath %>gys/suRoleManage/list.do";
        });
    }
    
    //绑定表单验证
    $("#yes").click(function(){
  	  if($("input[name=name]").val() == ""){
  	        layer.alert('请填写角色名！', {
  	 	           skin: 'layui-layer-molv' //样式类名
  	 	           ,closeBtn: 0
  	 	     });
  	        
  	        return false;
  	  }
  	  
  	  if($(".b_em input[type=checkbox]:checked").size() == 0){
  	        layer.alert('请选择父级权限！', {
  	           skin: 'layui-layer-molv' //样式类名
  	           ,closeBtn: 0
  	         });
  	        
  	        return false;
  		}
    });
})
</script>
</body>
</html>










 