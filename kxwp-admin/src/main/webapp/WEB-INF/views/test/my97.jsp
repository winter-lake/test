<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>my97日历测试</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/share/czsb.css">
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>
	<!--左边结束-->
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			时间范围:<input type="text" id="startDateTime" class="Wdate" />-<input type="text" id="endDateTime" class="Wdate" />
		</div>
	</div>
	
	<script>
	KXWP.set_start_end_time($("#startDateTime"),$("#endDateTime"),{
        endMaxToday:false,
        preDays:90,
    },true);
	</script>
</div>	
<!--end-->
</body>
</html>