<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>添加账号</title>
	
	<link rel="stylesheet" href="<%=basePath %>css/fwz/common_number.css">
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
			<form class="form_number" method="post" id="form_menu" action="<%=basePath%>fwz/ssAccount/modify.do">
				<input type="hidden" name="id" value="${serviceStationAccount.id }"/>
				<h4>基本信息</h4>
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 姓名 :</span> <input name="name" value="${serviceStationAccount.name }" class="validate[required]" disabled="disabled" type="text" placeholder="请输入姓名"></div>
				<input type="hidden" name="name" value="${serviceStationAccount.name }">
				<div class="div"><span><i class="icon-asterisk tx_red"></i> 绑定手机号 :</span> <input name="mobile" value="${serviceStationAccount.mobile }" class="validate[required]" disabled="disabled" type="text" placeholder="请输入11位手机号"></div>
				<input type="hidden" name="mobile" value="${serviceStationAccount.mobile }">
				<h4>角色  <i class="icon-question-sign" style="cursor:pointer;font-size:22px;color:#2a9fc2" text="一个角色相当于一组权限的打包，勾选不同角色可为子账号授予响应的权限组，例如勾选“运营”角色后，就给子账号授予了运营人员所需的权限" id="hover"></i></h4>
				<div class="div">
				<div><i class="icon-asterisk tx_red"></i></div>
					<c:forEach items="${ssRoles }" var="item" varStatus="status">
							<c:set var="iscontain" value="false" /> 
							<c:forEach var="item2" items="${serviceStationAccount.ssRoles}" >   
								<c:if test="${item2.id eq item.id}">     
									<c:set var="iscontain" value="true" />  
								</c:if> 
							</c:forEach>
							<c:if test="${iscontain == true}">
								<span><input type="checkbox" checked="checked"  name="ssRoles[${status.index }].id" value="${item.id }"> ${item.name }</span>
							</c:if>
							<c:if test="${iscontain == false}">
								<span><input type="checkbox"  name="ssRoles[${status.index }].id" value="${item.id }"> ${item.name }</span>
							</c:if>
					</c:forEach>
				</div>
				<div class="div"><input type="submit" class="yes" value="确认"><input type="reset" class="reset" value="重置"></div>
			</form>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">服务站</a></li>
		  	<li><a href="#">账号管理</a></li>
		  	<li><a href="#">修改账号</a></li>
		</ol>
		<a href="<%=basePath%>fwz/ssAccount/list.do" class="add_menu"><i class="icon-plus"></i>账号列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
	<div id="show" style="display:none"></div>
</div>	
<!--end-->
<script>
$(document).ready(function() {
	if("${exchangeData.callStatus}" == 'SUCCESS'){
		layer.alert('修改成功！', {
	           skin: 'layui-layer-molv' //样式类名
	           ,closeBtn: 0
	         }, function(){
	  	   	window.location.href = "<%=basePath%>fwz/ssAccount/list.do";
	         });
	}
	
	   //绑定表单验证
    $(".yes").click(function(){
  	  if($("input[name=name]").val() == ""){
  	        layer.alert('请填写角色名！', {
  	 	           skin: 'layui-layer-molv' //样式类名
  	 	           ,closeBtn: 0
  	 	     });
  	        
  	        return false;
  	  }
  	  if($("input[name=mobile]").val() == ""){
  	        layer.alert('请填写手机号！', {
  	 	           skin: 'layui-layer-molv' //样式类名
  	 	           ,closeBtn: 0
  	 	     });
  	        
  	        return false;
  	  }
  	  
  	  if(!$("input[name=mobile]").val().match(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/)){
    		layer.alert('请填写正确手机号！', {
  	           skin: 'layui-layer-molv' //样式类名
  	           ,closeBtn: 0
  	     });
         
         return false;
    	  }
  	  
  	  if($("span input[type=checkbox]:checked").size() == 0){
  	        layer.alert('请选择角色！', {
  	           skin: 'layui-layer-molv' //样式类名
  	           ,closeBtn: 0
  	         });
  	        
  	        return false;
  		}
    });
    Common.hovershow($("#hover"),$("#show"));
})
</script>
</body>
</html>