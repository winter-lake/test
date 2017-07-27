<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>添加服务站</title>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/zz/fwz-add.css">
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
			<div class="fwz-add">
				<form id="add_fwz_form">
					<!--账户信息-->
					<div class="zhxx">
						<h4>账户信息</h4>
						<p class="input-group">
							<span class="input-key"><i class="icon-asterisk tx_red"></i> 负责人姓名 :</span><input type="text" name="fwz_admin_name" class="validate[required] input-value" placeholder="请输入姓名"/>
						</p>
						<p class="input-group">
							<span class="input-key"><i class="icon-asterisk tx_red"></i> 负责人手机号 :</span><input type="text" name="fwz_admin_mobile" placeholder="请输入11位手机号" class="validate[required,custom[mobile]] input-value"/>
						</p>
						<p class="input-group">
							<span class="input-key"><i class="icon-asterisk tx_red"></i> 密码 :</span><i>密码会以手机短信的方式发送到您的手机上</i>
						</p>
					</div>
					<!--服务信息-->
					<div class="fwxx">
						<h4>服务信息</h4>
						<p class="input-group">
							<span class="input-key"><i class="icon-asterisk tx_red"></i> 服务站名称 :</span><input class="input-value validate[required]" type="text" name="fwz_name" placeholder="易购超市大兴站"/>
						</p>
						<p class="input-group">
							<span class="input-key"><i></i>客服电话 :</span><input type="text" class="validate[required] validate[custom[phone]] input-value" name="service_phone" placeholder="请输入客服电话"/>
						</p>
						
						<div class="j_delivery_range">
							<p class="ml50">
								<span class="input-key mr5"><i class="icon-asterisk tx_red"></i> 服务区域 :</span>
								<select id="j_province" class="validate[required]"></select>
								<select id="j_city" class="validate[required]"></select>
							</p>
							<div class="label_list"></div>
						</div>
						<p class="input-group"><span class="input-key"><i class="icon-asterisk tx_red"></i> 平台使用费 :</span><input class="validate[required, onlyNumber] input-value" type="text" name="platformFee" placeholder="保留两位小数"> 元</p>
						<p class="input-group"><span class="input-key"><i class="icon-asterisk tx_red"></i> 保证金 :</span><input class="validate[required, onlyNumber] input-value" type="text" name="bzj" placeholder="保留两位小数"> 元</p>
						<p class="input-group">
							<button type="button" class="qr j_submit">确认</button>
							<button class="qx">取消</button>
						</p>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">服务站管理</a></li>
		  	<li><a href="#">添加服务站</a></li>
		</ol>
		<a href="<%=basePath%>zz/manager/fwz/listFWZ.do" class="add_menu">服务站列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>
<script type="text/javascript">
	seajs.use("zz/stop/add", function(view) {
		view.init();
	});
</script>	
</body>
</html>