<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="../common/share_static.jsp" %>
	<title>公告详情</title>
	<link rel="stylesheet" href="<%=ProjectConfig.cdn_host %>/css/announcement_detail.css">
</head>
<body>
	<div class="j_container">
		<%-- <header class="header">
			<%@include file="../common/back.jsp" %>
			<span>[${announcementResponse.serviceStationName}]${announcementResponse.announcementName }</span>
		</header> --%>
		<section class="content">
			<div class="list">
				<h4 class="title">[${announcementResponse.serviceStationName}]${announcementResponse.announcementName }</h4>
				<div class="d_box">
					<dl class="b_dl">
						<dt><img src="${announcementResponse.pictureUrl }" alt=""></dt>
						<dd>
							<p class="p_l">
								${announcementResponse.content }
							</p>
						</dd>
					</dl>
					<div class="time">
					<fmt:formatDate value="${announcementResponse.pushTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
</html>