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
			<form class="form_number" method="post" id="form_menu" action="<%=basePath%>fwz/ssAccount/add.do">
				<h4>基本信息</h4>
				<div class="div"><span><i class="icon-asterisk tx_red"></i>姓名 :</span> <input name="name" type="text" placeholder="请输入姓名" class="validate[required]"></div>
				<div class="div"><span><i class="icon-asterisk tx_red"></i>绑定手机号 :</span> <input name="mobile" type="text" placeholder="请输入11位手机号" class="validate[required]"></div>
				<div class="div">
					<p class="input-group">
						<span class="input-key"><i class="icon-asterisk tx_red"></i> 密码 :</span><i>密码会以手机短信的方式发送到您的手机上</i>
					</p>
				</div>
				<h4>角色  <i class="icon-question-sign" style="cursor:pointer;font-size:22px;color:#2a9fc2" text="一个角色相当于一组权限的打包，勾选不同角色可为子账号授予响应的权限组，例如勾选“运营”角色后，就给子账号授予了运营人员所需的权限" id="hover"></i></h4>
				<div class="div">
				<div><i class="icon-asterisk tx_red"></i></div>
					<c:forEach items="${ssRoles }" var="item" varStatus="status">
						<span><input type="checkbox" name="ssRoles[${status.index }].id" value="${item.id }"> ${item.name }</span>
					</c:forEach>
				</div>
				<div style="margin-left:174px">
					<label style="cursor:pointer;color:#2a9fc2"><i class="icon-search" style="font-size:22px;"> </i>权限预览</label>
					<label style="cursor:pointer"><em style="color:#ffa200">展开</em> <i class="icon-chevron-down"></i></label>
					<p id="expand" style="width:300px;height:500px;">
					</p>
				</div>
				<div class="div"><input type="button" class="yes" value="确认"><input type="reset" class="reset" value="重置"></div>
			</form>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">服务站</a></li>
		  	<li><a href="#">账号管理</a></li>
		  	<li><a href="#">添加账号</a></li>
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
	$(".icon-chevron-down").click(function(){
		var ssRoles = [];
		$("input[type=checkbox]:checked").each(function(){
			var ssRole = {};
			ssRole.id = $(this).val();
			ssRoles.push(ssRole);
		});
		$.ajax({
			type:"POST",
			url:"/fwz/ssAccount/previewResourceInfo.do",
			dataType:"json",
			data:JSON.stringify(ssRoles),
			contentType: "application/json; charset=utf-8",
			success:function(response){
				var html = "<ul>";
				$.each(response, function(i, n){
					  html+='<li class="j_haschild module active"><a href="#"><i class="icon-sitemap tx_16 mr10"></i><span class="name">'+n.name+'</span></a>';
					  		html+='<ul>';
					  	$.each(n.ssResources, function(k, v){
					  		html+='<li class="end-li"><a href="#" data-path="#"><span class="icon-star-f mr5">'+v.name+'</span></a></li>';
					  	})
					  		html+='</ul>'; 
					  html+='</li>';
				});
				
				$("#expand").empty();
				$("#expand").append(html+"</ul>");
			}
		});
	});
	
	if("${exchangeData.callStatus}" == 'SUCCESS'){
		layer.alert('添加成功！', {
	           skin: 'layui-layer-molv' //样式类名
	           ,closeBtn: 0
	         }, function(){
	  	   	window.location.href = "<%=basePath%>fwz/ssAccount/list.do";
	         });
	}
	
	//绑定表单验证
    $(".yes").click(function(){
  	  if($("input[name=name]").val() == ""){
  	        layer.alert('请填写姓名！', {
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
  	  }else{
	  	  $.ajax({
	  		  type:'POST',
	  		  url:"/fwz/ssAccount/checkMobileIsUsed.do",
	  		  dataType:"json",
	  		  data:{mobile:$("input[name=mobile]").val()},
	  		  success:function(response){
	  			  if(response == true){
	  				layer.alert('该账号已被占用！', {
	  		  	           skin: 'layui-layer-molv' //样式类名
	  		  	           ,closeBtn: 0
	  		  	         });
	  				  return false;
	  			  }else{
	  				if($("span input[type=checkbox]:checked").size() == 0){
	  		  	        layer.alert('请选择角色！', {
	  		  	           skin: 'layui-layer-molv' //样式类名
	  		  	           ,closeBtn: 0
	  		  	         });
	  		  	        
	  		  	        return false;
	  		  		}else{
	  		  			$("form:first").submit();
	  		  		}
	  			  }
	  		  }
	  	  });
  	  }
    });
    Common.hovershow($("#hover"),$("#show"));
})
</script>
</body>
</html>