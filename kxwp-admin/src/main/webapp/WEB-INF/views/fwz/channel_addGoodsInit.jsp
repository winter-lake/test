<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>商品添加</title>
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
		<!--右边开始-->
				<form class="form-horizontal" role="form" style="margin-left: 5%;margin-top: 2%;">
				  <input type="hidden" id="supplierID" value="${supplierID }"/>
				  <input type="hidden" id="itemType" value="GOODS"/>
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">商品名称</label>
				    <div class="col-sm-10">
				      <select class="form-control" id="goodsName" placeholder="商品名称" class="validate[required]"></select>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">商品金额</label>
				    <div class="col-sm-10" id="goodsPrice">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">商品特价 </label>
				    <div class="col-sm-10">
				      <input class="form-control" id="goodsPromotionPrice" placeholder="商品特价" class="validate[required]"></input>
				    </div>
				  </div>
				</form>
		</div>
		<!--右边结束-->
	<!--end-->
	<script type="text/javascript">
		seajs.use("fwz/channel/add", function(view) {
			view.init();
		});
	</script>
</body>
</html>