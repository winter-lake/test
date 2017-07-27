<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>频道添加</title>
	<%@include file="../common/share_static.jsp" %>
	<style type="text/css">
		.form-control {
		    display: block;
		    width: 50%;
		    height: 34px;
		    padding: 6px 12px;
		    font-size: 14px;
		    line-height: 1.428571429;
		    color: #555;
		    vertical-align: middle;
		    background-color: #fff;
		    background-image: none;
		    border: 1px solid #ccc;
		    border-radius: 4px;
		    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
		    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
		    -webkit-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
		    transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
		}
		
		.table {
		    width: 50%;
		    margin-bottom: 20px;
		}
	</style>
</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@include file="../common/header.jsp" %>
		<%@include file="../common/leftNav.jsp" %>
		<!--右边开始-->
		<div class="k_right">
				<form class="form-horizontal" role="form" style="margin-left: 15%;margin-top: 2%;" id="add_channel_form">
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">频道名称</label>
				    <div class="col-sm-10">
				      <select class="form-control" id="channelName" placeholder="频道名称" class="validate[required]"></select>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">平台</label>
				    <div class="col-sm-10" id="platformType">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">供应商 </label>
				    <div class="col-sm-10">
				      <select class="form-control" id="supplierId" placeholder="供应商"></select>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">选择活动的商品 </label>
				    <div class="col-sm-10">
				      <div class="table-responsive">
						 <table class="table table-bordered table-condensed">
					        <thead>
					          <tr class="active">
					            <th>商品名称</th>
					            <th>商品金额</th>
					            <th>商品特价</th>
					            <th>操作</th>
					          </tr>
					        </thead>
					        <tbody id="tbody">
					        </tbody>
					      </table>
				  		  <button type="button" class="btn btn-info" id="add">添加</button>
					  </div>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">频道描述 </label>
				    <div class="col-sm-10">
				      <textarea rows="6" cols="40" class="form-control" id="channelDesc" placeholder="频道描述"></textarea>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				     <button type="button" class="btn btn-info" id="submit">提交</button>
				     <button type="button" class="btn btn-default" style="margin-left: inherit;" id="back">返回</button>
				    </div>
				  </div>
				</form>
		</div>
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background:#fff">
			  	<li><a href="#">服务站</a></li>
			  	<li><a href="#">频道管理</a></li>
			  	<li><a href="#">添加频道</a></li>
			</ol>
			<a href="<%=contextPath %>/fwz/channel/listChannelInit.do" class="add_menu">频道列表</a>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp" %>
		<!--footer结束-->
	</div>	
	<!--end-->
	<script type="text/javascript">
		seajs.use("fwz/channel/add", function(view) {
			view.init();
		});
	</script>
</body>
</html>