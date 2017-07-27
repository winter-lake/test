<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>服务站列表</title>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/zz/fwz-lb.css">
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
			<!--查询等功能-->
			<form class="k_change" method="post" action="">
				<b class="k-b">
					<i>注册时间 :</i>
					<select><option>所属城市</option></select>
				</b>
				<b class="k-b">
					<i>服务站类型 :</i>
					<select><option value="">区级</option></select>
				</b>
				<b class="k-b">
					<i>服务站名称</i>
					<input type="text" name="" id="sjh">
				</b>
				<b class="k-b">
					<i>状态</i>
					<select id="zt">
						<option>全部</option>
						<option>待审核</option>
						<option>已启用</option>
						<option>已停用</option>
						<option>未通过</option>
						<option>未匹配</option>
					</select>
				</b>
				<input type="submit" class="Inquire" value="查询" id="cscx">
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center">
			  	<thead style="background:#eee">
					<tr>
						<td><input type="checkbox" name="b"></td>
						<td>编号</td>
						<td>姓名</td>
						<td>服务区域</td>
						<td>服务类型</td>
						<td>添加时间</td>
						<td>手机号</td>
						<td>状态</td>
						<td>平台使用费</td>
						<td>保证金</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="checkbox" name="a"></td>
						<td>1</td>
						<td>13613525453</td>
						<td>小鱼仔超市</td>
						<td>地盛南街9号1栋3层327快消互联</td>
						<td>大兴服务站</td>
						<td>刘涛</td>
						<td>13522438263</td>
						<td>和新</td>
						<td>2015-2-1</td>
						<td><a href="javascriptt:;" class="show">查看</a></td>
					</tr>
				</tbody>
			</table>
			<!--批量操作及分页-->
			<div class="batch">
				<!--分页-->
				<div class="paging">
					<div id="kkpager"></div>
				</div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">服务站管理</a></li>
		  	<li><a href="#">服务站列表</a></li>
		</ol>
		<a href="<%=basePath %>zz/manager/fwz/gotoAddFWZ.do" class="add_menu"><i class="icon-plus"></i>添加服务站</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->

<script>
$(document).ready(function() {
	Common.paging2();
	Common.date();
	Csmenu.render();
	$("#sel_menu2").select2({
		tags: true,
		maximumSelectionLength: 3, //最多可以选择的个数
		language: "zh-CN",
		ajax:{
			url:"/zz/supermarket/ajax/registerSupermarketAccount.do",
			dataType:'json',
			delay:250,
			data:function(param){
				console.log(param)
				return {
					q:param.term,
					page:param.page
				}
			},
			processResults: function (data, params) {
	            params.page = params.page || 1;

	            return {
	                results: data.items,
	                pagination: {
	                    more: (params.page * 10) < data.total_count
	                }
	            };
	        },
	        cache: true
		},
		escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
	    minimumInputLength: 1,
	    templateResult: formatRepoProvince, // omitted for brevity, see the source of this page
	    templateSelection: formatRepoProvince // omitted for brevity, see the source of this page
	})
	function formatRepoProvince(repo) {
	    if (repo.loading) return repo.text;
	    var markup = "<div>"+repo.name+"</div>";
	    return markup;
	}
})
</script>
</body>
</html>