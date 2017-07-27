<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>公告添加</title>
	<%@include file="../common/share_static.jsp" %>
	<!--第三方end-->
<link rel="stylesheet" href="<%=contextPath %>/css/zz/addGoods.css">
</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@include file="../common/header.jsp" %>
		<%@include file="../common/leftNav.jsp" %>
		<!--右边开始-->
		<div class="k_right">
			<div class="content">
				<form id="announcement_add_form">
					<div class="sptj">
						<h4>商品基本信息</h4>
						<div class="jbxx">
							<p>
								<span class="input_key"><i class="icon-asterisk tx_red"></i>公告名称 :</span><input type="text" id="announcementName" style="width: 261px;" class="validate[required]"/>
							</p>
							<p>
								<span class="input_key">公告描述 :</span><textarea rows="4" cols="40" id="announcementDesc"></textarea>
							</p>
							<div>
								<span class="input_key"><i class="icon-asterisk tx_red"></i>公告缩略图 :</span> <em>建议图片尺寸大小为800px*800px.jpg格式，图片的大小不能超过600KB</em>
								<h5>
									<div class="dis_inb j_main_pic_box">
										<label class="w_filebox">
											<img src="<%=contextPath %>/images/share/sctp.png">
											<input type="file" name="file" id="j_main_pic">
										</label>
										<input type="hidden" name="keyID" value="${announcementNo}">
										<input type="hidden" name="bucketName" value="announcement">
									</div>
								</h5>
							</div>
							<p>
								<span class="input_key"><i class="icon-asterisk tx_red"></i>发送时间 :</span><input type="text" id="pushTime"   class="validate[required]" style="width: 261px;" />
							</p>
							<p>
								<span class="input_key"><i class="icon-asterisk tx_red"></i>平台 :</span> <select  style="width: 261px;" id="platformType"></select>
							</p>
							<p>
								<span class="input_key"><i class="icon-asterisk tx_red"></i>公告内容 :</span><textarea rows="6" cols="40" id="content" class="validate[required]"></textarea>
							</p>
							<p>
								<button type="button" class="submit j_submit">提交</button>
								<button type="reset" class="reset">重置</button>
							</p>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background:#fff">
			  	<li><a href="#">服务站</a></li>
			  	<li><a href="#">公告管理</a></li>
			  	<li><a href="#">添加公告</a></li>
			</ol>
			<a href="<%=contextPath %>/fwz/SsAnnouncement/listInit.do" class="add_menu">公告列表</a>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp" %>
		<!--footer结束-->
	</div>	
	<!--end-->
	<script type="text/javascript">
		seajs.use("fwz/announcement/add", function(view) {
			view.init();
		});
	</script>
</body>
</html>