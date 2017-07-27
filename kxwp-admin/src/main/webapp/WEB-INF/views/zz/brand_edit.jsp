<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>编辑品牌</title>
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
						<input type="text" id="brandName" value="${msBrand.brandName}">
					</div>
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">品牌slogan :</span>
						<textarea id="describtion">${msBrand.describtion}</textarea>
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
									<input type="hidden" name="keyID" id="brandno" value="${msBrand.brandNo}">
									<input type="hidden" name="bucketName" value="brand">
										<c:if test="${msBrand.photourl!=''}">
											<label class="w_view_pic" data-file_key="${filekey}">
												<img src="${msBrand.photourl}"><b class="del">X</b>
											</label>
										</c:if>
										<c:if test=" ${msBrand.photourl==''} " >
											<label class="w_view_pic">
												<img src="<%=contextPath %>/images/share/sctp.png">
											</label>
										</c:if>
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
		  	<li><a href="#">编辑品牌</a></li>
		</ol>
		<a href="/zz/ssBrand/queryBrandInit.do" class="add_menu">品牌列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>
<script type="text/javascript">
$(function(){	
	// 初始化上传图片 -- 主图
	Common.imageUpload(".j_main_pic_box");
	
	$("#bansub").click(function(){
		var MsBrand = {};
		MsBrand.brandName = $("#brandName").val();
		MsBrand.describtion = $("#describtion").val();
		MsBrand.brandNo = ${msBrand.brandNo};
		MsBrand.id = ${msBrand.id};
		if( $("#brandName").val()!="" && $("#describtion").val()!=""){
		$.ajax({
		    url:"/zz/ssBrand/modify.do",
		    type:'post',
		    contentType:'application/json',
		    dataType:'json',
		    data:JSON.stringify(MsBrand),
		    success:function(data){
		    	if(data.callStatus == 'SUCCESS'){
		    		Common.kxpw_tishi("修改品牌信息成功");
		    		window.location.href="/zz/ssBrand/queryBrandInit.do";
		    	}
		    	else{
		    		Common.kxpw_tishi("系统错误，品牌修改失败");
		    		return false;
		    	}
		    }
		})
		}else{
			Common.kxpw_tishi("请输入完整的表单");
			return false;
		}
	});
	$("#comeback").click(function(){
		window.location.href="/zz/ssBrand/queryBrandInit.do";
	});
});
</script>
</body>
</html>