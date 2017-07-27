<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>供应商查看商品详情</title>
<%@include file="../common/share_static.jsp"%>
<!--第三方end-->
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/gys/goods_detail.css">

</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@include file="../common/header.jsp"%>
		<%@include file="../common/leftNav.jsp"%>
		<!--左边结束-->
		<!--右边开始-->
		<div class="k_right">
			<div class="content">
				<div class="scrolltab">
					<ul class="ulBigPic">

						<c:forEach items="${goods_detail.photoList }" var="photo"
							varStatus="item_status">
							<c:if test="${item_status.index eq 1 }">
								<li class="liSelected">
									<span class="sPic">
									 <i class="iBigPic"><a href="javascript:;"><img alt="大图" src="${photo }" /></a></i>
									</span>
								</li>
							</c:if>
							
							<c:if test="${item_status.index ne 1 }">
								<li>
									<span class="sPic"> 
										<i class="iBigPic"><a href="javascript:;"><img alt="大图"src="${photo }" /></a></i>
									</span>
								</li>
							</c:if>
						</c:forEach>
					</ul>
					<!--ulBigPic end-->
					<div class="dSmallPicBox">
						<div class="dSmallPic">
							<ul class="ulSmallPic" style="width: 2646px; left: 0px"
								rel="stop">
								<c:forEach items="${goods_detail.photoList }" var="photo"
									varStatus="item_status">
									<c:if test="${item_status.index eq 1 }">
										<li>
											<span class="sPic">
												<img src="${photo }" alt="小图" />
											</span>
										</li>
									</c:if>

									<c:if test="${item_status.index ne 1 }">
										<li>
											<span class="sPic">
												<img src="${photo }" alt="小图" />
											</span>
										</li>
									</c:if>
								</c:forEach>

							</ul>
						</div>
						<span id="sLeftBtnB" class="sLeftBtnBBan"></span> <span
							id="sRightBtnB" class="sRightBtnB"></span>
					</div>
					<!--dSmallPicBox end-->
				</div>
				<div class="scroll-right">
					<div class="div_a">
						<h4>${goods_detail.goodsName }</h4>
						<p class="p-m">
							<span>品牌 :</span> <i>${goods_detail.brand_detail.brandName }</i>
						</p>
						<p class="p-m">
							<span>包装规格 :</span> <i>${goods_detail.packageSpecific }</i>
						</p>
						<p class="p-m">
							<span>保质期 :</span> <i>${goods_detail.shelfLife }</i>
						</p>
						<p class="p-m">
							<span>生产日期 :</span> <i>${goods_detail.productDate }</i>
						</p>
					</div>
					<div class="div_a">
						<p class="p-m">
							<span>价格 :</span> <ul class="g-g">
							<c:if test="${goods_detail.lotsPrice.name eq 'N' }"><li>${goods_detail.salePrice } 元</li></c:if> 
							 <c:if test="${goods_detail.lotsPrice.name eq 'Y' }">
							 <c:forEach items="${goods_detail.goodslotPriceList }" var="item">
							     <li>数量:${item.minQit }-${item.maxQit },价格:${item.lotPrice } 元</li>
							 </c:forEach>
							 </c:if>
							</ul>
						</p>
						<p class="p-m">
							<span>建议零售价 :</span> <i>${goods_detail.suggestRetailPrice }元</i>
						</p>
					</div>
					<div class="div_a">
						<p class="p-m">
							<span>起订量 :</span> <i>${goods_detail.minPurchased }</i>
						</p>
						<p class="p-m">
							<span>商品条形码 :</span> <i>${goods_detail.barcode }</i>
						</p>
						<p class="p-m">
							<span>退换货 :</span> <i>${goods_detail.return_policy_desc }</i>
						</p>
						<p class="p-m">
							<span>供应商 :</span> <i>${goods_detail.supplier_name }</i>
						</p>
					</div>
				</div>
				<c:if test="${reviews != null && fn:length(reviews) > 0}">
				<table class="table table-bordered" style="text-align:center">
				  	<thead style="background:#eee">
						<tr style="font-weight:bold">
						    <td>序号</td>
							<td>审核时间</td>
							<td>审核人</td>
							<td>审核结果</td>
							<td>审核说明</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${reviews }" var="item" varStatus="loop">
						  <tr>
						    <td>${loop.index+1 }</td>
							<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${item.reviewUserName }</td>
							<td>${item.afterReviewStatus }</td>
							<td>${item.describtion }</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:if>
			</div>
		</div>
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background: #fff">
				<li><a href="#">供应商</a></li>
				<li><a href="#">商品管理</a></li>
				<li><a href="#">商品详情</a></li>
			</ol>
			<a href="<%=contextPath %>/gys/goods/gotoListGoods.do" class="add_menu">商品列表</a>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp"%>
		<!--footer结束-->
	</div>
	<!--end-->
	<script src="<%=contextPath%>/js/share/goods_detail.js"></script>
</body>
</html>