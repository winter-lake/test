<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>品牌添加</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" href="/css/zz/brand_add.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp"%>
	<%@include file="../common/leftNav.jsp"%>
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<div class="add_gg">
				<form id="addppform">
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">品牌名称 :</span>
						<input type="text" id="brandName" class="validate[required]">
					</div>
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">品牌slogan :</span>
						<textarea id="describtion" class="validate[required]"></textarea>
					</div>
					<div class="a-d">
						<i class="star icon-asterisk tx_red" style="position:relative;top:60px"></i>
						<span class="s-p" style="position:relative;top:60px">图片略缩图 :</span>
						<h5>
								<div class="dis_inb j_main_pic_box">
									<label class="w_filebox">
										<img src="<%=contextPath %>/images/share/sctp.png">
										<input type="file" name="file" id="j_main_pic">
									</label>
									<input type="hidden" name="keyID" id="brandno" value="${BrandNo}">
									<input type="hidden" name="bucketName" value="brand">
								</div>
						</h5>
					</div>
				</form>
				<div class="a-d sub">
						<button type="button" class="submit" id="bansub">提交</button>
						<button class="back" id="comeback">返回</button>
				</div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">品牌管理</a></li>
		  	<li><a href="#">添加品牌</a></li>
		</ol>
		<a href="/zz/ssBrand/queryBrandInit.do" class="add_menu">品牌列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<script src="/js/zz/addbrand.js"></script>
</body>
</html>