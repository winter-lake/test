<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>添加会员</title>
<%@ include file="../common/share_static.jsp"%>
<link rel="stylesheet" href="<%=basePath%>css/zz/ckcs.css">
<link rel="stylesheet" href="<%=basePath%>css/zz/menu.css">
</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftNav.jsp"%>

		<!--右边开始-->
		<div class="k_right">
			<div class="content">
				<form action="createSupermarket.do" method="post" class="cssh"
					id="form_menu">
					<div class="csck">
						<h4>账户信息</h4>
						<p>
							<span class="input_key">姓名</span> <input type="text" name="name">
						</p>
						<p>
							<span class="input_key">绑定手机号</span> <input type="text"
								name="accountMobile" class="validate[required]">
						</p>
						<p style="margin-left:340px;width:400px;">
							<input type="checkbox" id="sendrequired" checked="checked"> <input
								type="hidden" name="sendPassword" value="0" id="sendPassword" />选中后生成的密码将以短信的形式发送手机号中
						</p>
						<h4>超市信息</h4>
						<p>
							<span class="input_key">超市名称</span> <input type="text"
								name="supermarketName" class="validate[required]">
						</p>
						<p class="w_select_box">
							<span class="input_key">省</span> <select class="w_autosuggest"
								type="text" name="province" id="j_province"></select>
						</p>
						<p class="w_select_box">
							<span class="input_key">市</span> <select class="w_autosuggest"
								type="text" name="city" id="j_city"></select>
						</p>
						<p class="w_select_box">
							<span class="input_key">县</span> <select class="w_autosuggest"
								type="text" name="county" id="j_county"></select>
						</p>
						<p class="w_select_box">
							<span class="input_key">镇</span> <select class="w_autosuggest"
								type="text" name="town" id="j_town"></select>
						</p>
						<p>
							<span class="input_key">详细地址</span> <input type="text"
								name="adress" class="validate[required]">
						</p>
						<p hidden="true">
							<span class="input_key">收货人</span> <input type="text"
								name="receptionName" class="validate[required]">
						</p>
						<p hidden="true">
							<span class="input_key">联系方式</span> <input type="text"
								name="phone" class="validate[required]">
						</p>
						<p>
							<span class="input_key">业务员</span> <input type="text" name="">
						</p>
						<p>
							<span class="input_key">社会统一信用代码</span> <input type="text"
								name="licenseNum" class="validate[required]">
						</p>
						<p>
							<span class="input_key">营业执照扫描件</span> <i class="img"><img
								src="<%=basePath%>images/share/404.png" class="img"></i>
						</p>
						<p>
							<span class="input_key">身份证号</span> <input type="text" class="validate[required]"
								name="licenseNum">
						</p>
						<p>
							<span class="input_key">身份证扫描件</span> <i class="img"><img
								src="<%=basePath%>images/share/404.png" class="img"></i>
						</p>
						<p style="width:500px">
							<button type="submit" class="tgsq">添加</button>
							<input type="button" class="fh" value="返回"/>
						</p>
					</div>
				</form>
			</div>
		</div>
		<!--右边结束-->
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background: #fff">
				<li><a href="#">服务站</a></li>
				<li><a href="#">超市会员管理</a></li>
				<li><a href="#">添加超市</a></li>
			</ol>
			<!-- <a href="add_menu.html" class="add_menu"><i class="icon-plus"></i>添加会员</a> -->
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp"%>
		<!--footer结束-->
	</div>
	<!--end-->
	<script src="<%=contextPath%>/js/zz/csgl.js"></script>
	<script>
		$(".tgsq").click(function() {
			/* Common.validator('#form_menu');
			layer.confirm('确认添加？', {
				btn : [ '确定', '取消' ]
			//按钮
			}, function() {
				$("#form_menu").submit();
			}) */
			if(!Common.validator('#form_menu')){
				return;
			}else{
				layer.confirm('确认添加？', {
					btn : [ '确定', '取消' ]
				//按钮
				}, function() {
					$("#form_menu").submit();
				})
			}
		});

		$("#sendrequired").change(function() {
			if ($("#sendrequired").prop("checked")) {
				$("#sendPassword").val("1");
			} else {
				$("#sendPassword").val("0");
			}
		});
		
		$("input[name=name]").change(function(){
			$("input[name=receptionName]").val($(this).val());
		});
		$("input[name=accountMobile]").change(function(){
			$("input[name=phone]").val($(this).val());
		});
		
		$(".fh").click(function(){
			window.location.href="/fwz/supermarket/FWZManagerSupermarket.do";
		});
	</script>
</body>
</html>