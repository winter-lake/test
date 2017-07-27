<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="../common/share_static.jsp" %>
	<title>[${ssAnnouncement.serviceStationName}]${ssAnnouncement.announcementName }</title>
	<link rel="stylesheet" href="<%=contextPath %>/css/fwz/announcement_detail.css">
</head>
<body>
	<div class="j_container">
		<header class="header">
			<a href="javascript:void(0)" class="back"></a>
		</header>
		<section class="content">
			<span>&nbsp;&nbsp;公告名称：[${ssAnnouncement.serviceStationName}]${ssAnnouncement.announcementName }</span>
			<div class="list">
				<div class="d_box">
					<dl class="b_dl">
						<dt>公告缩略图：<img src="${ssAnnouncement.pictureUrl }" alt=""></dt>
						<dd>
							<p class="p_l">
								公告内容：${ssAnnouncement.content }
							</p>
						</dd>
					</dl>
					<div class="time">
					<fmt:formatDate value="${ssAnnouncement.pushTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
</html>