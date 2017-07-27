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
					</b> <b class="k-b"> <i>订单号</i> <input type="text" name="orderNo"
						id="orderNo" placeholder="订单编号">
					</b> <b class="k-b"> <i>手机号</i> <input type="text" name=phone
						placeholder="2343243" id="phone">
					</b> <b class="k-b"> <i>服务站</i> <select id="service_station"
						name="service_station"></select>
					</b> <b class="k-b"> <i>供应商</i> <select id="supplier"
						name="supplier"></select>
					</b> <b class="k-b"> <i>状态</i> <select id="order_status"
						placeholder="全部">

					</select>
					</b> <input type="button" class="Inquire j_search_btn" value="查询"
						style="top: 60px">
				</form>
				<!--table-->
				<table class="table table-bordered" style="text-align: center"
					id="zz_order_list">
					<thead style="background: #eee">
						<tr>
							<!-- <td><input type="checkbox" name="b"></td> -->
							<td>编号</td>
							<td>订单号</td>
							<td>收货人</td>
							<td>联系方式</td>
							<td>支付方式</td>
							<td>订单金额</td>
							<td>应收金额</td>
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
				<li><a href="#">总站</a></li>
				<li><a href="#">订单管理</a></li>
				<li><a href="#">订单列表</a></li>
			</ol>
			<!-- 总站没有添加会员 -->
			<%-- <a href="<%=contextPath %>/gys/goods/gotoAddGoods.do" class="add_menu"><i class="icon-plus"></i>添加商品</a> --%>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp"%>
		<!--footer结束-->
	</div>
	<!--end-->
	<script type="text/javascript">
		seajs.use("zz/order/list", function(view) {
			view.init();
		});
	</script>
</body>
</html>