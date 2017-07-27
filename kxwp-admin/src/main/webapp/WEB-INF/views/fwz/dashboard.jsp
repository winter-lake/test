<%@page import="com.kxwp.common.constants.CommonRequestAttr"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>欢迎页</title>
	 <%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/share/hello.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<!--头部开始-->
	<%@ include file="../common/header.jsp" %>
	
	<!--头部结束-->
	<!--左边开始-->
	<%@ include file="../common/leftNav.jsp" %>
	<!--左边结束-->
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<div class="h_content">
				<h4>快消网批-产品介绍</h4>
				<div class="cp_img">
					<img src="<%=contextPath %>/images/share/hello.png">
				</div>
				<h4>快消网批-企业介绍</h4>
				<div class="qy_js">
					<p>
						<span>企业介绍：</span><i>企业理念：缘（缘分），源（资源），圆（圆满）</i>
					</p>
					<p>
						<span>企业使命：</span><i>帮助传统快消品企业拥抱互联网+转型升级</i>
					</p>
					<p>
						<span>企业简介：</span><i>北京快消网批科技发展有限公司（简称“快消互联集团”） 总部位于北京，起源于湖北。快消互联在快消品供应链及互联网领域潜心
						经营二十余载，目前已经成为一家快消品贸易、电子商务、移动互联网、便利店管理体系服务等为主营业务的大型集团化公司
						，在全国多省市设有子、分公司。公司定位于"专注快消、全球互联"，将以利用先进的互联网理念帮助传统快消品行业拥抱"互联网+"
						为己任，立志让快消品企业没有难做的生意。</i>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb hide">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">服务站</a></li>
		  	<li><a href="#">欢迎</a></li>
		</ol>
		<a href="number.html" class="add_menu"><i class="icon-plus"></i>账号列表</a>
	</div>
	<!--面包屑导航结束-->
	<!--footer开始-->
	 <%@include file="../common/footer.jsp" %>
	<!--footer结束-->
</div>	
<!--end-->
</body>
</html>
