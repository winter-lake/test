<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>角色查看</title>

<link rel="stylesheet" href="<%=basePath%>css/zz/role_detail.css">
<%@include file="../common/share_static.jsp"%>
<style type="text/css">
.row {
	margin-right: -15px;
	margin-left: 300px;
}
</style>
</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@include file="../common/header.jsp"%>
		<%@include file="../common/leftNav.jsp"%>
		<!--右边开始-->
		<div class="k_right">
			<div class="content">
				<h2 class="title">角色信息</h2>
				<div class="row">
					<div class="col-md-2">角色名称 :</div>
					<div class="col-md-4">${msRole.name }</div>
				</div>
				<div class="row">
					<div class="col-md-2">角色描述 :</div>
					<div class="col-md-4">${msRole.roleDescription }</div>
				</div>
				<h2 class="title">权限选择</h2>
				<!-- <div class="row">
				<div class="col-md-2"><input type="checkbox" name="b" disabled="disabled">全选</div>
				<div class="col-md-4"></div>
			</div> -->
				<c:forEach items="${newlist }" var="item" varStatus="status">
					<div class="row">
						<c:set var="iscontain" value="false" />
						<c:forEach var="item3" items="${msRole.msRoleResourceRelations}">
							<c:if test="${item3.resourceId eq item.id}">
								<c:set var="iscontain" value="true" />
							</c:if>
						</c:forEach>
						<c:if test="${iscontain == true}">
							<div class="col-md-2">
								<input disabled="disabled" type="checkbox" checked="checked"
									name="msResources[${status.index }].id" value="${item.id }">
								${item.name }
							</div>
						</c:if>
						<c:if test="${iscontain == false}">
							<div class="col-md-2">
								<input type="checkbox" disabled="disabled"
									name="msResources[${status.index }].id" value="${item.id }">
								${item.name }
							</div>
						</c:if>
						<div class="col-md-4">
							<c:forEach items="${item.msResources }" var="item2"
								varStatus="status2">
								<c:set var="iscontain0" value="false" />
								<c:forEach var="item4" items="${msRole.msRoleResourceRelations}">
									<c:if test="${item4.resourceId eq item2.id}">
										<c:set var="iscontain0" value="true" />
									</c:if>
								</c:forEach>
								<c:if test="${iscontain0 == true }">
									<input type="checkbox" disabled="disabled" checked="checked"
										name="msResources[${status.index }].msResources[${status2.index }].id"
										value="${item2.id }"> ${item2.name }
							</c:if>
								<c:if test="${iscontain0 == false }">
									<input type="checkbox" disabled="disabled"
										name="msResources[${status.index }].msResources[${status2.index }].id"
										value="${item2.id }"> ${item2.name }
							</c:if>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background: #fff">
				<li><a href="#">总站</a></li>
				<li><a href="#">角色管理</a></li>
				<li><a href="#">角色查看</a></li>
			</ol>
			<a href="<%=basePath%>zz/msRoleManage/list.do" class="add_menu"><i
				class="icon-plus"></i>角色列表</a>
		</div>
		<!--面包屑导航结束-->
		<%@include file="../common/footer.jsp"%>
	</div>
	<!--end-->
</body>
</html>