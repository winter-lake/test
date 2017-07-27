<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>订单列表</title>
<%@ include file="../common/share_static.jsp"%>
<link rel="stylesheet" href="<%=contextPath%>/css/zz/cs.css">
<link rel="stylesheet" href="<%=contextPath%>/css/zz/menu.css">


</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftNav.jsp"%>

		<!--右边开始-->
		<div class="k_right">
			<div class="content">
				<div class="z-num"
					style='width: 100%; height: 40px; line-height: 40px; text-indent: 25px; font-size: 13px; background: #eee'>
					<span class="text">应收金额合计 :</span> <em class="z-price"><font
						color="red" id="sumAmount"></font></em>
				</div>
				<!--查询等功能-->
				<form class="k_change j_search_form" method="post" action="">
					<b class="k-b"> <i>下单时间</i> <input type="text" name="startDate"
						id="startDate" placeholder="请输入开始时间"> <i>至</i> <input
						type="text" name="endDate" placeholder="请输入结束时间" id="endDate">
					</b> 
					<b class="k-b"> <i>总单号</i> <input type="text" name="bigOrderNo"
						id="bigOrderNo" placeholder="总订单编号">
					</b>
					<b class="k-b"> <i>子单号</i> <input type="text" name="orderNo"
						id="orderNo" placeholder="子订单编号">
					</b> <b class="k-b"> <i>手机号</i> <input type="text" name=phone
						placeholder="2343243" id="phone">
					</b> <b class="k-b"> <i>供应商</i> <select id="supplier"
						name="supplier"></select>
					</b> <b class="k-b"> <i>状态</i> <select id="order_status"
						placeholder="全部">

					</select>
					</b> <br />
					<div style="text-align: center;">
						<button type="button" class="j_search_btn btn btn-info">查询</button>
						<!-- 				<input type="button" class="Inquire j_search_btn" value="查询" style="top:60px">
 -->
						<button type="button" class="btn btn-info j_export_order_btn">导出订单</button>
					</div>

				</form>
				<!--table-->
				<table class="table table-bordered table-hover table-responsive"
					style="text-align: center" id="fwz_order_list">
					<thead style="background: #eee">
						<tr>
							<td><input type="checkbox" name="b" class="j_checkbox_all"></td>
							<td>编号</td>
							<td>子单号</td>
							<td>收货人</td>
							<td>联系方式</td>
							<td>支付方式</td>
							<td>订单金额</td>
							<td>实付金额</td>
							<td>订单状态</td>
							<td>下单时间</td>
							<td>完成时间</td>
							<td>供应商</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody>
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
			<ol class="breadcrumb" style="background: #fff">
				<li><a href="#">服务站</a></li>
				<li><a href="#">订单管理</a></li>
				<li><a href="#">订单列表</a></li>
			</ol>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp"%>
		<!--footer结束-->
	</div>
	<!--end-->
	<script type="text/javascript">
		seajs.use("fwz/order/list", function(view) {
			view.init();
		});
	</script>
</body>
</html>